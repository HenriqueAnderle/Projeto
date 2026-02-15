package controle.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import modelo.dao.contato.ContatoDAO;
import modelo.dao.contato.ContatoDAOImpl;
import modelo.dao.obra.ObraDAO;
import modelo.dao.obra.ObraDAOImpl;
import modelo.dao.usuario.UsuarioDAO;
import modelo.dao.usuario.UsuarioDAOImpl;
import modelo.entidade.contato.Contato;
import modelo.entidade.obra.Obra;
import modelo.entidade.pdfSolicitacao.PdfSolicitacao;
import modelo.entidade.solicitacao.Solicitacao;
import modelo.entidade.solicitacaoMateriaisServicos.SolicitacaoMateriaisServicos;
import modelo.entidade.usuario.Usuario;
import modelo.enumeracao.EtapaDaObra;
import modelo.enumeracao.Solicitante;
import modelo.enumeracao.Unidade;

@WebServlet(urlPatterns = {"/nova-solicitacao", "/inserir-solicitacao"})

@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 20 * 1024 * 1024)

public class SolicitacaoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ContatoDAO daoContato;
	private UsuarioDAO daoUsuario;
	private ObraDAO daoObra;
	
	public void init() {
		daoContato = new ContatoDAOImpl();
		daoUsuario = new UsuarioDAOImpl();
		daoObra = new ObraDAOImpl();
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		String action = request.getServletPath();

		try {
			
			switch (action) {
			
			case "/nova-solicitacao":
				mostrarFormularioNovaSolicitacao(request, response);
				break;
				
			case "/inserir-solicitacao":
				inserirSolicitacaoMateriaisServicos(request, response);
				break;			
				
			default:
				listarSolicitacoes(request, response);
				break;
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void mostrarFormularioNovaSolicitacao(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
		
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		sessao.removeAttribute("solicitacaoMateriaisServicos");
	    sessao.removeAttribute("solicitacoes");
		
		request.setAttribute("solicitante", Solicitante.values());
		
		request.setAttribute("etapaDaObra", EtapaDaObra.values());
		
		request.setAttribute("unidade", Unidade.values());
		
		List<Obra> obras = daoObra.recuperarObras(usuario);
		
		request.setAttribute("obras", obras);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("form-solicitacao.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void inserirSolicitacaoMateriaisServicos(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

	    HttpSession sessao = request.getSession();
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
	    usuario = daoUsuario.recuperarUsuario(usuario);
	    
	    // Verificar se é edição
	    SolicitacaoMateriaisServicos solicitacaoMateriaisServicos = (SolicitacaoMateriaisServicos) sessao.getAttribute("solicitacaoMateriaisServicos");
	    boolean isEdicao = (solicitacaoMateriaisServicos != null);
	    
	    if (!isEdicao) {
	        
	        LocalDate dataSolicitacao = LocalDate.now();
	        Obra obra = new Obra(request.getParameter("obra"));
	        Solicitante solicitante = Solicitante.valueOf(request.getParameter("solicitante"));
	        EtapaDaObra etapaDaObra = EtapaDaObra.valueOf(request.getParameter("etapaDaObra"));
	        
	        String previsaoParam = request.getParameter("previsaoDeChegada");
	        LocalDateTime previsaoChegada = null;

	        if (previsaoParam != null && !previsaoParam.isEmpty()) {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	            previsaoChegada = LocalDateTime.parse(previsaoParam, formatter);
	        }
	        
	        String observacao = request.getParameter("observacao");
	        boolean logo = Boolean.parseBoolean(request.getParameter("logo"));

	        solicitacaoMateriaisServicos = new SolicitacaoMateriaisServicos(obra, solicitante, etapaDaObra, null, dataSolicitacao, previsaoChegada, observacao, logo, usuario);
	        
	        sessao.setAttribute("solicitacaoMateriaisServicos", solicitacaoMateriaisServicos);
	    } else {
	        // EDIÇÃO - atualizar dados gerais
	        solicitacaoMateriaisServicos.setObra(new Obra(request.getParameter("obra")));
	        solicitacaoMateriaisServicos.setSolicitante(Solicitante.valueOf(request.getParameter("solicitante")));
	        solicitacaoMateriaisServicos.setEtapaDaObra(EtapaDaObra.valueOf(request.getParameter("etapaDaObra")));
	        
	        String previsaoParam = request.getParameter("previsaoDeChegada");

	        if (previsaoParam != null && !previsaoParam.isEmpty()) {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	            solicitacaoMateriaisServicos.setPrevisaoChegada(
	                LocalDateTime.parse(previsaoParam, formatter)
	            );
	        } else {
	            solicitacaoMateriaisServicos.setPrevisaoChegada(null);
	        }
	        
	        solicitacaoMateriaisServicos.setObservacao(request.getParameter("observacao"));
	        solicitacaoMateriaisServicos.setLogo(Boolean.parseBoolean(request.getParameter("logo")));
	    }

	    // Processar imagem (se nova)
	    Part imagemPart = request.getPart("imagem");
	    if (imagemPart != null && imagemPart.getSize() > 0) {
	    	
	    	long contador = isEdicao ? solicitacaoMateriaisServicos.getIdSolicitacao() : usuario.proximoRelatorio(usuario);
	    	String nomeArquivo = contador + "_imagem_" + Paths.get(imagemPart.getSubmittedFileName()).getFileName().toString();
	    	solicitacaoMateriaisServicos.setImagem("uploads/" + nomeArquivo);
	                           
	        String pastaUpload = getServletContext().getRealPath("/uploads");
	        new File(pastaUpload).mkdirs();
	        imagemPart.write(pastaUpload + File.separator + nomeArquivo);
	        solicitacaoMateriaisServicos.setImagem("uploads/" + nomeArquivo);
	    }

	    // === SOLICITAÇÕES ===
	    List<Solicitacao> solicitacoes = new ArrayList<>();
	    String[] materiaisServicos = request.getParameterValues("solicitacoes[].materialServico");
	    String[] unidades = request.getParameterValues("solicitacoes[].unidade");
	    String[] quantidades = request.getParameterValues("solicitacoes[].quantidade");
	    String[] descricoes = request.getParameterValues("solicitacoes[].descricao");

	    if (materiaisServicos != null && materiaisServicos.length > 0) {
	        for (int i = 0; i < materiaisServicos.length; i++) {
	            if (materiaisServicos[i] == null || materiaisServicos[i].trim().isEmpty()) continue;
	            
	            Solicitacao solicitacao = new Solicitacao();
	            solicitacao.setMaterialServico(materiaisServicos[i]);
	            solicitacao.setUnidade(Unidade.valueOf(unidades[i]));
	            solicitacao.setQuantidade(quantidades[i]);
	            solicitacao.setDescricao(descricoes[i]);
	            solicitacao.setSolicitacaoMateriaisServicos(solicitacaoMateriaisServicos);
	            solicitacoes.add(solicitacao);
	        }
	    }
	    
	    sessao.setAttribute("solicitacoes", solicitacoes);
	    
	    // === GERAR PDF ===
	    try {
	        PdfSolicitacao pdfService = new PdfSolicitacao();
	        byte[] pdf = pdfService.gerarSolicitacaoMateriaisServicos(solicitacaoMateriaisServicos, solicitacoes, getServletContext());
	        
	        request.getSession().setAttribute("pdfGerado", pdf);

	        List<Contato> contatosAdicionados = daoContato.recuperarContatosAdicionados(usuario);
	        List<Contato> contatosNaoAdicionados = daoContato.recuperarContatosNaoAdicionados(usuario);

	        request.setAttribute("contatosAdicionados", contatosAdicionados);
	        request.setAttribute("contatosNaoAdicionados", contatosNaoAdicionados);
	        request.setAttribute("solicitacaoMateriaisServicos", solicitacaoMateriaisServicos);
	        request.setAttribute("solicitacoes", solicitacoes);
	        
	        String tipo = "solicitacao";
	        sessao.setAttribute("tipo", tipo);
	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("form-email.jsp");
	        dispatcher.forward(request, response);

	    } catch (Exception e) {
	        throw new ServletException("Erro ao gerar PDF ou enviar e-mail", e);
	    }
	}
	
	private void listarSolicitacoes(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
	}
}
package controle.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import modelo.entidade.naoConformidade.NaoConformidade;
import modelo.entidade.obra.Obra;
import modelo.entidade.pdfRelatorioNaoConformidade.PdfRelatorioNaoConformidade;
import modelo.entidade.relatorio.Relatorio;
import modelo.entidade.usuario.Usuario;
import modelo.entidade.vistoriador.Vistoriador;
import modelo.enumeracao.CondicaoClimatica;
import modelo.enumeracao.EtapaDaObra;
import modelo.enumeracao.Pavimento;
import modelo.enumeracao.Tempo;
import modelo.enumeracao.TipoDeNaoConformidade;

@WebServlet(urlPatterns = {"/novo-relatorio", "/inserir-relatorio"})

@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 20 * 1024 * 1024)

public class RelatorioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ContatoDAO daoContato;
	private UsuarioDAO daoUsuario;
	private ObraDAO daoObra;
	
	public void init() {
		daoContato = new ContatoDAOImpl();
		daoUsuario = new UsuarioDAOImpl();
		daoObra = new ObraDAOImpl();

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
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
			
			case "/novo-relatorio":
				mostrarFormularioNovoRelatorio(request, response);
				break;
				
			case "/inserir-relatorio":
				inserirRelatorio(request, response);
				break;
				
			default:
				listarRelatorios(request, response);
				break;
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void mostrarFormularioNovoRelatorio(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		HttpSession sessao = request.getSession();
		
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
	    
	    sessao.removeAttribute("relatorio");
	    sessao.removeAttribute("vistoriadores");
	    sessao.removeAttribute("naoConformidades");
		
		request.setAttribute("condicaoClimatica", CondicaoClimatica.values());
		
		request.setAttribute("tempo", Tempo.values());
		
		request.setAttribute("pavimento", Pavimento.values());
		
		request.setAttribute("etapaDaObra", EtapaDaObra.values());
		
		request.setAttribute("tipoDeNaoConformidade", TipoDeNaoConformidade.values());
		
		List<Obra> obras = daoObra.recuperarObras(usuario);
		
		request.setAttribute("obras", obras);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("form-relatorio.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void inserirRelatorio(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

	    HttpSession sessao = request.getSession();
	    
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
	    
	    usuario = daoUsuario.recuperarUsuario(usuario);
	    
	    long contadorRelatorio = usuario.getContadorRelatorio();
	    
	    LocalDate dataRelatorio = LocalDate.now();
	    
	    Obra obra = new Obra(request.getParameter("obra"));
	    
	    String empresaExecutora = request.getParameter("empresaExecutora");
	    CondicaoClimatica condicaoClimatica = CondicaoClimatica.valueOf(request.getParameter("condicaoClimatica"));
	    Tempo tempo = Tempo.valueOf(request.getParameter("tempo"));
	    boolean condicao = Boolean.parseBoolean(request.getParameter("condicao"));

	    Relatorio relatorio = new Relatorio(contadorRelatorio, dataRelatorio, obra, empresaExecutora, condicaoClimatica, tempo, condicao, usuario);
	    
	    sessao = request.getSession();
	    sessao.setAttribute("relatorio", relatorio);
	    
	    List<Vistoriador> vistoriadores = new ArrayList<>();
	    
	    String[] nomeVistoriadores = request.getParameterValues("vistoriadores[].nome");
	    
	    if (nomeVistoriadores != null && nomeVistoriadores.length > 0) {
	        for (int i = 0; i < nomeVistoriadores.length; i++) {
	        	
	        	Vistoriador vistoriador = new Vistoriador();
	        	vistoriador.setNome(nomeVistoriadores[i]);
	        	vistoriador.setRelatorio(relatorio);
	            
	        	vistoriadores.add(vistoriador);
	        	
	        }
	    }
	    
	    sessao.setAttribute("vistoriadores", vistoriadores);

	    List<NaoConformidade> naoConformidades = new ArrayList<>();
	    
	    String[] pavimentos = request.getParameterValues("naoConformidades[].pavimento");
	    String[] etapas = request.getParameterValues("naoConformidades[].etapaDaObra");
	    String[] tipos = request.getParameterValues("naoConformidades[].tipoDeNaoConformidade");
	    String[] observacoes = request.getParameterValues("naoConformidades[].observacao");
	    String[] imagensExistentes = request.getParameterValues("naoConformidades[].imagemExistente");

	    Collection<Part> parts = request.getParts();

	    if (pavimentos != null && pavimentos.length > 0) {
	        for (int i = 0; i < pavimentos.length; i++) {
	            if (pavimentos[i] == null || pavimentos[i].trim().isEmpty()) continue;
	            
	            NaoConformidade naoConformidade = new NaoConformidade();
	            naoConformidade.setPavimento(Pavimento.valueOf(pavimentos[i]));
	            naoConformidade.setEtapaDaObra(EtapaDaObra.valueOf(etapas[i]));
	            naoConformidade.setTipoDeNaoConformidade(TipoDeNaoConformidade.valueOf(tipos[i]));
	            naoConformidade.setObservacao(observacoes[i]);
	            naoConformidade.setRelatorio(relatorio);

	            String nomeImagemFinal = null;

	            // Priorizar imagem existente
	            if (imagensExistentes != null && i < imagensExistentes.length && imagensExistentes[i] != null) {
	                nomeImagemFinal = imagensExistentes[i];
	            }

	            // Processar nova imagem se enviada
	            Part imagemPart = null;
	            int contadorImagens = 0;
	            for (Part part : parts) {
	                if ("naoConformidades[].imagem".equals(part.getName())) {
	                    if (contadorImagens == i) {
	                        imagemPart = part;
	                        break;
	                    }
	                    contadorImagens++;
	                }
	            }

	            if (imagemPart != null && imagemPart.getSize() > 0) {
	                String nomeArquivo = System.currentTimeMillis() + "_" +
	                        Paths.get(imagemPart.getSubmittedFileName()).getFileName().toString();
	                String uploadPath = getServletContext().getRealPath("/uploads");
	                File dir = new File(uploadPath);
	                if (!dir.exists()) dir.mkdirs();
	                imagemPart.write(uploadPath + File.separator + nomeArquivo);
	                nomeImagemFinal = nomeArquivo;
	            }

	            naoConformidade.setImagem(nomeImagemFinal);
	            naoConformidades.add(naoConformidade);
	        }
	    }
	    
	    sessao.setAttribute("naoConformidades", naoConformidades);
	    
	    try {
	    	
	    	PdfRelatorioNaoConformidade pdfService = new PdfRelatorioNaoConformidade();
	    	
	    	byte[] pdf = pdfService.gerarRelatorioNaoConformidade(usuario, relatorio, naoConformidades, vistoriadores, getServletContext());
	    	
	    	request.getSession().setAttribute("pdfGerado", pdf);

	    	List<Contato> contatosAdicionados = daoContato.recuperarContatosAdicionados(usuario);
	    	
	    	List<Contato> contatosNaoAdicionados = daoContato.recuperarContatosNaoAdicionados(usuario);

	    	request.setAttribute("contatosAdicionados", contatosAdicionados);
	    	
	    	request.setAttribute("contatosNaoAdicionados", contatosNaoAdicionados);
	    	
	    	String tipo = "relatorio";
	    	sessao.setAttribute("tipo", tipo);
	    	
	    	sessao = request.getSession();

	    	request.setAttribute("relatorio", sessao.getAttribute("relatorio"));
	    	request.setAttribute("vistoriadores", sessao.getAttribute("vistoriadores"));
	    	request.setAttribute("naoConformidades", sessao.getAttribute("naoConformidades"));
	    	
	        RequestDispatcher dispatcher = request.getRequestDispatcher("form-email.jsp");
			dispatcher.forward(request, response);

	    } catch (Exception e) {
	        throw new ServletException("Erro ao gerar PDF ou enviar e-mail", e);
	    }

	}
	
	private void listarRelatorios(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
	}
	
}

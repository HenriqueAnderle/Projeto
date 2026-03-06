package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.dao.contato.ContatoDAO;
import modelo.dao.contato.ContatoDAOImpl;
import modelo.dao.obra.ObraDAO;
import modelo.dao.obra.ObraDAOImpl;
import modelo.dao.usuario.UsuarioDAO;
import modelo.dao.usuario.UsuarioDAOImpl;
import modelo.entidade.contato.Contato;
import modelo.entidade.email.Email;
import modelo.entidade.obra.Obra;
import modelo.entidade.relatorio.Relatorio;
import modelo.entidade.solicitacaoMateriaisServicos.SolicitacaoMateriaisServicos;
import modelo.entidade.usuario.Usuario;
import modelo.enumeracao.CondicaoClimatica;
import modelo.enumeracao.EtapaDaObra;
import modelo.enumeracao.Pavimento;
import modelo.enumeracao.Solicitante;
import modelo.enumeracao.Tempo;
import modelo.enumeracao.TipoDeNaoConformidade;
import modelo.enumeracao.Unidade;

@WebServlet(urlPatterns = {"/novo-email", "/inserir-email", "/enviar-email-relatorio", "/enviar-email-solicitacao", "/voltar-form"})

public class EmailServlet extends HttpServlet {
	
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
			
			case "/novo-email":
				mostrarFormularioNovoEmail(request, response);
				break;
				
			case "/inserir-email":
				inserirEmail(request, response);
				break;
				
			case "/enviar-email-relatorio":
				enviarEmailRelatorio(request, response);
				break;
				
			case "/enviar-email-solicitacao":
				enviarEmailSolicitacao(request, response);
				break;
				
			case "/voltar-form":
				voltarFormulario(request, response);
				break;
				
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void mostrarFormularioNovoEmail(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("form-email.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void inserirEmail(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
		
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		String tipo = (String) sessao.getAttribute("tipo");
		
		List<String> destinatarios = new ArrayList<>();

        String[] emailDestinatarios = request.getParameterValues("destinatarios[].email");

        if (emailDestinatarios != null) {
            for (String email : emailDestinatarios) {
                try {
                    InternetAddress ia = new InternetAddress(email);
                    ia.validate();
                    
                    if (!destinatarios.contains(email)) {
                        destinatarios.add(email);
                    }
                    
                } catch (AddressException e) {
                    System.out.println("Email inválido: " + email);
                }
            }
        }
        
        List<Contato> contatosAdicionados = daoContato.recuperarContatosAdicionados(usuario);

        for (Contato contato : contatosAdicionados) {
            String email = contato.getEmail();

            try {
                InternetAddress ia = new InternetAddress(email);
                ia.validate();

                if (!destinatarios.contains(email)) {
                    destinatarios.add(email);
                }
                
            } catch (AddressException e) {
                System.out.println("Email inválido no contato: " + email);
            }
        }
        
	    sessao.setAttribute("destinatarios", destinatarios);
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/enviar-email-"+ tipo);
		dispatcher.forward(request, response);
		
	}
	
	@SuppressWarnings("unchecked")
	private void enviarEmailRelatorio(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
		
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		Usuario usuarioRecuperado = daoUsuario.recuperarUsuario(usuario);
				
		List<String> destinatarios = (List<String>) sessao.getAttribute("destinatarios");
		
		if (request.getParameter("emailRemetente") != null) {
			
			String emailUsuario = usuarioRecuperado.getEmail();
            		
			if (!destinatarios.contains(emailUsuario)) {
                destinatarios.add(emailUsuario);
            }
			
		}

		if (destinatarios == null || destinatarios.isEmpty()) {
		    
		    request.setAttribute("erroEmail", "Nenhum E-Mail foi Informado.");
		    
		    List<Contato> contatosAdicionados = daoContato.recuperarContatosAdicionados(usuario);
	    	
	    	List<Contato> contatosNaoAdicionados = daoContato.recuperarContatosNaoAdicionados(usuario);
	    	
	    	request.setAttribute("contatosAdicionados", contatosAdicionados);
	    	
	    	request.setAttribute("contatosNaoAdicionados", contatosNaoAdicionados);
		    
		    RequestDispatcher dispatcher = request.getRequestDispatcher("form-email.jsp");
		    dispatcher.forward(request, response);
		    return;
		}
		
	    long contadorRelatorio = (usuario.proximoRelatorio(usuario));
	    daoUsuario.atualizarUsuarioContadorRelatorio(usuario, contadorRelatorio);
                
        try {
		
        Email emailService = new Email();

        byte[] pdf = (byte[]) request.getSession().getAttribute("pdfGerado");

        String mensagem = "Relatório de Vistoria";
                
        emailService.enviarRelatorio(usuario, destinatarios, mensagem, pdf);
        
        List<Contato> contatosAdicionados = daoContato.recuperarContatosAdicionados(usuario);
        
        for (Contato contato : contatosAdicionados) {
            
        	boolean enviarEmail = false;
        	
        	daoContato.atualizarContatoEnviarEmail(contato, enviarEmail);

        }
        
        response.sendRedirect("home.jsp");
        
        } catch (Exception e) {
	        throw new ServletException("Erro ao gerar PDF ou enviar e-mail", e);
	    }
		
	}
	
	@SuppressWarnings("unchecked")
	private void enviarEmailSolicitacao(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
		
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		SolicitacaoMateriaisServicos solicitacao = (SolicitacaoMateriaisServicos) sessao.getAttribute("solicitacaoMateriaisServicos");
		
		Usuario usuarioRecuperado = daoUsuario.recuperarUsuario(usuario);
        
		List<String> destinatarios = (List<String>) sessao.getAttribute("destinatarios");

		if (request.getParameter("emailRemetente") != null) {
			
			String emailUsuario = usuarioRecuperado.getEmail();
            		
			if (!destinatarios.contains(emailUsuario)) {
                destinatarios.add(emailUsuario);
            }
			
		}
		
		if (destinatarios == null || destinatarios.isEmpty()) {
		    
		    request.setAttribute("erroEmail", "Nenhum E-Mail foi Informado.");
		    
		    List<Contato> contatosAdicionados = daoContato.recuperarContatosAdicionados(usuario);
	    	
	    	List<Contato> contatosNaoAdicionados = daoContato.recuperarContatosNaoAdicionados(usuario);
	    	
	    	request.setAttribute("contatosAdicionados", contatosAdicionados);
	    	
	    	request.setAttribute("contatosNaoAdicionados", contatosNaoAdicionados);
		    
		    RequestDispatcher dispatcher = request.getRequestDispatcher("form-email.jsp");
		    dispatcher.forward(request, response);
		    return;
		}
        
		boolean logo = solicitacao.getLogo();
		
		if(logo == true) {
		
		long contadorSolicitacao = (usuario.proximaSolicitacao(usuario));
	    daoUsuario.atualizarUsuarioContadorSolicitacao(usuario, contadorSolicitacao);
		
		}else {
			
			long contadorSolicitacao2 = (usuario.proximaSolicitacao2(usuario));
		    daoUsuario.atualizarUsuarioContadorSolicitacao2(usuario, contadorSolicitacao2);
			
		}
		
        try {
		
        Email emailService = new Email();

        byte[] pdf = (byte[]) request.getSession().getAttribute("pdfGerado");
        
        String mensagem = "Solicitação de Materiais e Serviços";
        
        logo = solicitacao.getLogo();
        
        if(logo == true) {
        	emailService.enviarRelatorio(usuario, destinatarios, mensagem, pdf);
        }
        if(logo == false) {
        	emailService.enviarSolicitacaoMannz(usuario, destinatarios, mensagem, pdf);
        }
        
        request.getSession().setAttribute("pdfGerado", pdf);
        
        List<Contato> contatosAdicionados = daoContato.recuperarContatosAdicionados(usuario);
        
        for (Contato contato : contatosAdicionados) {
            
        	boolean enviarEmail = false;
        	
        	daoContato.atualizarContatoEnviarEmail(contato, enviarEmail);

        }
        
        response.sendRedirect("home.jsp");
        
        } catch (Exception e) {
	        throw new ServletException("Erro ao gerar PDF ou enviar e-mail", e);
	    }
		
	}
	
	private void voltarFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    HttpSession sessao = request.getSession();
	    
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

	    Relatorio relatorio = (Relatorio) sessao.getAttribute("relatorio");
	    
	    SolicitacaoMateriaisServicos solicitacao = (SolicitacaoMateriaisServicos) sessao.getAttribute("solicitacaoMateriaisServicos");
	    
	    if(relatorio == null) {
	    	
	    	List<Obra> obras = daoObra.recuperarObras(usuario);
			
			request.setAttribute("obras", obras);
	    	
	    	request.setAttribute("solicitacaoMateriaisServicos", sessao.getAttribute("solicitacaoMateriaisServicos"));
		    request.setAttribute("solicitacoes", sessao.getAttribute("solicitacoes"));
	    	
	    	request.setAttribute("solicitante", Solicitante.values());
			request.setAttribute("etapaDaObra", EtapaDaObra.values());
			request.setAttribute("unidade", Unidade.values());
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("form-edit-solicitacao.jsp");
		    dispatcher.forward(request, response);
	    }
	    
	    if(solicitacao == null) {
	    
	    	List<Obra> obras = daoObra.recuperarObras(usuario);
			
			request.setAttribute("obras", obras);
	    	
		    request.setAttribute("relatorio", sessao.getAttribute("relatorio"));
		    request.setAttribute("vistoriadores", sessao.getAttribute("vistoriadores"));
		    request.setAttribute("naoConformidades", sessao.getAttribute("naoConformidades"));
	
		    request.setAttribute("condicaoClimatica", CondicaoClimatica.values());
		    request.setAttribute("tempo", Tempo.values());
		    request.setAttribute("pavimento", Pavimento.values());
		    request.setAttribute("etapaDaObra", EtapaDaObra.values());
		    request.setAttribute("tipoDeNaoConformidade", TipoDeNaoConformidade.values());
	
		    RequestDispatcher dispatcher = request.getRequestDispatcher("form-edit-relatorio.jsp");
		    dispatcher.forward(request, response);
	    }
	    
	    if(usuario == null) {
	    	
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		    dispatcher.forward(request, response);
	    	
	    }
	}
	
}

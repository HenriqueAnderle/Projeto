package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.dao.contato.ContatoDAO;
import modelo.dao.contato.ContatoDAOImpl;
import modelo.entidade.contato.Contato;
import modelo.entidade.usuario.Usuario;


@WebServlet(urlPatterns = {"/novo-contato", "/inserir-contato", "/deletar-contato", "/editar-contato", "/atualizar-contato", "/adicionar-contato", "/remover-contato", "/contatos"})

public class ContatoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ContatoDAO dao;
	
	public void init() {
		dao = new ContatoDAOImpl();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		String action = request.getServletPath();

		try {
			
			switch (action) {
			
			case "/novo-contato":
				mostrarFormularioNovoContato(request, response);
				break;
				
			case "/inserir-contato":
				inserirContato(request, response);
				break;
				
			case "/deletar-contato":
				deletarContato(request, response);
				break;
				
			case "/editar-contato":
				mostrarFormularioEditarContato(request, response);
				break;
				
			case "/atualizar-contato":
				atualizarContato(request, response);
				break;
				
			case "/adicionar-contato":
				adicionarContato(request, response);
				break;
				
			case "/remover-contato":
				removerContato(request, response);
				break;
				
			default:
				listarContatos(request, response);
				break;
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void mostrarFormularioNovoContato(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("form-contato.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void inserirContato(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		boolean enviarEmail = false;
		
		dao.inserirContato(new Contato(nome, email, enviarEmail, usuario));
						
		response.sendRedirect("contatos");
		
	}
	
	private void deletarContato(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idContato = Long.parseLong(request.getParameter("idContato"));
		
		Contato contato = dao.recuperarContato(new Contato(idContato));
		
		dao.deletarContato(contato);
		
		response.sendRedirect("contatos");
		
	}
	
	private void mostrarFormularioEditarContato(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idContato = Long.parseLong(request.getParameter("idContato"));
		
		Contato contato = dao.recuperarContato(new Contato(idContato));
		
		request.setAttribute("contato", contato);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		
	}
	
	private void atualizarContato(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idContato = Long.parseLong(request.getParameter("idContato"));
		
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		
		dao.atualizarContato(new Contato(idContato, nome, email));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		
	}
	
	private void adicionarContato(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		long idContato = Long.parseLong(request.getParameter("idContato"));
		
		Contato contato = dao.recuperarContato(new Contato(idContato));
		
		boolean enviarEmail = true;
		
		dao.atualizarContatoEnviarEmail(contato, enviarEmail);
		
		List<Contato> contatosAdicionados = dao.recuperarContatosAdicionados(usuario);
    	
    	List<Contato> contatosNaoAdicionados = dao.recuperarContatosNaoAdicionados(usuario);

    	request.setAttribute("contatosAdicionados", contatosAdicionados);
    	
    	request.setAttribute("contatosNaoAdicionados", contatosNaoAdicionados);
		
    	RequestDispatcher dispatcher = request.getRequestDispatcher("form-email.jsp");
	    dispatcher.forward(request, response);
		
		
	}
	
	private void removerContato(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		long idContato = Long.parseLong(request.getParameter("idContato"));
		
		Contato contato = dao.recuperarContato(new Contato(idContato));
		
		boolean enviarEmail = false;
		
		dao.atualizarContatoEnviarEmail(contato, enviarEmail);
		
		List<Contato> contatosAdicionados = dao.recuperarContatosAdicionados(usuario);
    	
    	List<Contato> contatosNaoAdicionados = dao.recuperarContatosNaoAdicionados(usuario);

    	request.setAttribute("contatosAdicionados", contatosAdicionados);
    	
    	request.setAttribute("contatosNaoAdicionados", contatosNaoAdicionados);
		
    	RequestDispatcher dispatcher = request.getRequestDispatcher("form-email.jsp");
	    dispatcher.forward(request, response);
		
	}
	
	private void listarContatos(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

	    List<Contato> contatos = dao.recuperarContatos(usuario);

	    request.setAttribute("contatos", contatos);

	    RequestDispatcher dispatcher = request.getRequestDispatcher("contatos.jsp");
	    dispatcher.forward(request, response);
		
	}
	
	
	
	
	
	
	
	
	
}

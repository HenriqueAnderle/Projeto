package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.dao.usuario.UsuarioDAO;
import modelo.dao.usuario.UsuarioDAOImpl;
import modelo.entidade.usuario.Usuario;
import org.mindrot.jbcrypt.BCrypt;


@WebServlet(urlPatterns = {"/novo-usuario", "/inserir-usuario", "/deletar-usuario", "/editar-usuario", "/atualizar-usuario", "/novo-login", "/logar-usuario", "/deslogar-usuario", "/voltar-home"})

public class UsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UsuarioDAO dao;
	
	public void init() {
		dao = new UsuarioDAOImpl();
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
			
			case "/novo-usuario":
				mostrarFormularioNovoUsuario(request, response);
				break;
				
			case "/inserir-usuario":
				inserirUsuario(request, response);
				break;
				
			case "/deletar-usuario":
				deletarUsuario(request, response);
				break;
				
			case "/editar-usuario":
				mostrarFormularioEditarUsuario(request, response);
				break;
				
			case "/atualizar-usuario":
				atualizarUsuario(request, response);
				break;
				
			case "/novo-login":
				mostrarFormularioLoginUsuario(request, response);
				break;
				
			case "/logar-usuario":
				logarUsuario(request, response);
				break;
				
			case "/deslogar-usuario":
				deslogarUsuario(request, response);
				break;
				
			case "/voltar-home":
				voltarHome(request, response);
				break;
				
			default:
				listarUsuarios(request, response);
				break;
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void mostrarFormularioNovoUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("form-usuario.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void inserirUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());
		
		dao.inserirUsuario(new Usuario(nome, email, senhaHash));
		
		response.sendRedirect("index.jsp");
		
	}
	
	private void deletarUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idUsuario = Long.parseLong(request.getParameter("idUsuario"));
		
		Usuario usuario = dao.recuperarUsuario(new Usuario(idUsuario));
		
		dao.deletarUsuario(usuario);
		
	}
	
	private void mostrarFormularioEditarUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idUsuario = Long.parseLong(request.getParameter("idUsuario"));
		
		Usuario usuario = dao.recuperarUsuario(new Usuario(idUsuario));
		
		request.setAttribute("usuario", usuario);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		
	}
	
	private void atualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idUsuario = Long.parseLong(request.getParameter("idUsuario"));
		
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		dao.atualizarUsuario(new Usuario(idUsuario, nome, email, senha));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		
	}
	
	private void mostrarFormularioLoginUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void logarUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
				
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		Usuario usuarioRecuperado = dao.recuperarUsuarioEmail(email);
		
		if(usuarioRecuperado != null) {
			
			if (BCrypt.checkpw(senha, usuarioRecuperado.getSenha())) {
				
				HttpSession sessao = request.getSession();
			    sessao.setAttribute("usuarioLogado", usuarioRecuperado);
			    
			    sessao.setMaxInactiveInterval(24 * 60 * 60);
				
			    response.sendRedirect("home.jsp");
			    return;
			}
		}
			
		RequestDispatcher dispatcher = request.getRequestDispatcher("/novo-login");
		dispatcher.forward(request, response);
			
		
	}
	
	private void deslogarUsuario(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		
		HttpSession sessao = request.getSession();
		
		if(sessao != null) {
			
			sessao.invalidate();
			
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
			
		}
	}
	
	private void voltarHome(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
	    
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
	    
	    if(usuario == null) {
	    
	    	response.sendRedirect("index.jsp");
	    }else {
	    	
	    	sessao.removeAttribute("relatorio");
	    	sessao.removeAttribute("vistoriadores");
	    	sessao.removeAttribute("naoConformidades");
	    	
	    	sessao.removeAttribute("solicitacaoMateriaisServicos");
	    	sessao.removeAttribute("solicitacoes");

	    	response.sendRedirect("home.jsp");
	    }
		
	}
	
	private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
	}
	
}

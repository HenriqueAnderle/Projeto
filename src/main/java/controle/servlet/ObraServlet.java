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

import modelo.dao.obra.ObraDAO;
import modelo.dao.obra.ObraDAOImpl;
import modelo.entidade.obra.Obra;
import modelo.entidade.usuario.Usuario;


@WebServlet(urlPatterns = {"/nova-obra", "/inserir-obra", "/deletar-obra", "/editar-obra", "/atualizar-obra", "/obras"})

public class ObraServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ObraDAO dao;
	
	public void init() {
		dao = new ObraDAOImpl();
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
			
			case "/nova-obra":
				mostrarFormularioNovaObra(request, response);
				break;
				
			case "/inserir-obra":
				inserirObra(request, response);
				break;
				
			case "/deletar-obra":
				deletarObra(request, response);
				break;
				
			case "/editar-obra":
				mostrarFormularioEditarObra(request, response);
				break;
				
			case "/atualizar-obra":
				atualizarObra(request, response);
				break;
				
			default:
				listarObras(request, response);
				break;
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void mostrarFormularioNovaObra(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("form-obra.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void inserirObra(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		String nome = request.getParameter("nome");
		
		dao.inserirObra(new Obra(nome, usuario));
						
		response.sendRedirect("obras");
		
	}
	
	private void deletarObra(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idObra = Long.parseLong(request.getParameter("idObra"));
		
		System.out.println(idObra);
		
		Obra obra = dao.recuperarObra(new Obra(idObra));
		
		System.out.println(obra);
		
		dao.deletarObra(obra);
		
		response.sendRedirect("obras");
		
	}
	
	private void mostrarFormularioEditarObra(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idObra = Long.parseLong(request.getParameter("idObra"));
		
		Obra obra = dao.recuperarObra(new Obra(idObra));
		
		request.setAttribute("obra", obra);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("form-obra.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void atualizarObra(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idObra = Long.parseLong(request.getParameter("idObra"));
		
		String nome = request.getParameter("nome");
		
		dao.atualizarObra(new Obra(idObra, nome));
		
		response.sendRedirect("obras");
		
	}
	
	private void listarObras(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession sessao = request.getSession();
	    Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

	    List<Obra> obras = dao.recuperarObras(usuario);

	    request.setAttribute("obras", obras);

	    RequestDispatcher dispatcher = request.getRequestDispatcher("obras.jsp");
	    dispatcher.forward(request, response);
		
	}
	
	
	
	
	
	
	
	
	
}

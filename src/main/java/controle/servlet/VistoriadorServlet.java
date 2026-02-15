package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.vistoriador.VistoriadorDAO;
import modelo.dao.vistoriador.VistoriadorDAOImpl;
import modelo.entidade.relatorio.Relatorio;
import modelo.entidade.vistoriador.Vistoriador;

@WebServlet(urlPatterns = {"/novo-vistoriador", "/inserir-vistoriador", "/deletar-vistoriador", "/editar-vistoriador", "/atualizar-vistoriador"})

public class VistoriadorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private VistoriadorDAO dao;
	
	public void init() {
		dao = new VistoriadorDAOImpl();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();

		try {
			
			switch (action) {
			
			case "/novo-vistoriador":
				mostrarFormularioNovoVistoriador(request, response);
				break;
				
			case "/inserir-vistoriador":
				inserirVistoriador(request, response);
				break;
				
			case "/deletar-vistoriador":
				deletarVistoriador(request, response);
				break;
				
			case "/editar-vistoriador":
				mostrarFormularioEditarVistoriador(request, response);
				break;
				
			case "/atualizar-vistoriador":
				atualizarVistoriador(request, response);
				break;
				
			default:
				listarVistoriadores(request, response);
				break;
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void mostrarFormularioNovoVistoriador(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("form-vistoriador.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void inserirVistoriador(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		String nome = request.getParameter("nome");
		Relatorio relatorio = new Relatorio(Long.parseLong(request.getParameter("relatorio")));
		
		dao.inserirVistoriador(new Vistoriador(nome, relatorio));
		
		response.sendRedirect("index.jsp");
		
	}
	
	private void deletarVistoriador(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idVistoriador = Long.parseLong(request.getParameter("idVistoriador"));
		
		Vistoriador vistoriador = dao.recuperarVistoriador(new Vistoriador(idVistoriador));
		
		dao.deletarVistoriador(vistoriador);
		
	}
	
	private void mostrarFormularioEditarVistoriador(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idVistoriador = Long.parseLong(request.getParameter("idVistoriador"));
		
		Vistoriador vistoriador = dao.recuperarVistoriador(new Vistoriador(idVistoriador));
		
		request.setAttribute("vistoriador", vistoriador);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		
	}
	
	private void atualizarVistoriador(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idVistoriador = Long.parseLong(request.getParameter("idVistoriador"));
		
		String nome = request.getParameter("nome");
		Relatorio relatorio = new Relatorio(Long.parseLong(request.getParameter("relatorio")));
		
		dao.atualizarVistoriador(new Vistoriador(idVistoriador, nome, relatorio));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		
	}
	
	private void listarVistoriadores(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
	}
	
	
	
	
	
	
	
	
	
}

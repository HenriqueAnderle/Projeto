package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.caixa.CaixaDAO;
import modelo.dao.caixa.CaixaDAOImpl;
import modelo.entidade.caixa.Caixa;
import modelo.entidade.emailEnviado.EmailEnviado;
import modelo.entidade.usuario.Usuario;


@WebServlet(urlPatterns = {"/nova-caixa", "/inserir-caixa", "/deletar-caixa" })

public class CaixaServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private CaixaDAO dao;
	
	public void init() {
		dao = new CaixaDAOImpl();
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
			
			case "/nova-caixa":
				mostrarFormularioNovaCaixa(request, response);
				break;
				
			case "/inserir-caixa":
				inserirCaixa(request, response);
				break;
				
			case "/deletar-caixa":
				deletarCaixa(request, response);
				break;
				
			default:
				listarCaixas(request, response);
				break;
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void mostrarFormularioNovaCaixa(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		
	}
	
	private void inserirCaixa(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		EmailEnviado emailEnviado = new EmailEnviado(Long.parseLong(request.getParameter("emailEnviado")));
		Usuario usuario = new Usuario(Long.parseLong(request.getParameter("usuario")));
		
		dao.inserirCaixa(new Caixa(emailEnviado, usuario));
		
	}
	
	private void deletarCaixa(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idCaixa = Long.parseLong(request.getParameter("idCaixa"));
		
		Caixa caixa = dao.recuperarCaixa(new Caixa(idCaixa));
		
		dao.deletarCaixa(caixa);
		
	}
	
	private void listarCaixas(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
	}
	
	
	
	
	
	
	
	
	
}

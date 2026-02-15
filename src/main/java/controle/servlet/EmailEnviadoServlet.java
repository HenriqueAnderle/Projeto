package controle.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.dao.emailEnviado.EmailEnviadoDAO;
import modelo.dao.emailEnviado.EmailEnviadoDAOImpl;
import modelo.entidade.emailEnviado.EmailEnviado;

@WebServlet(urlPatterns = {"/novo-emailEnviado", "/inserir-emailEnviado", "/deletar-emailEnviado", "/editar-emailEnviado", "/atualizar-emailEnviado"})

public class EmailEnviadoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private EmailEnviadoDAO dao;
	
	public void init() {
		dao = new EmailEnviadoDAOImpl();
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
			
			case "/novo-emailEnviado":
				mostrarFormularioNovoEmailEnviado(request, response);
				break;
				
			case "/inserir-emailEnviado":
				inserirEmailEnviado(request, response);
				break;
				
			case "/deletar-emailEnviado":
				deletarEmailEnviado(request, response);
				break;
				
			default:
				listarEmailsEnviados(request, response);
				break;
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void mostrarFormularioNovoEmailEnviado(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		
	}
	
	private void inserirEmailEnviado(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		String assunto = request.getParameter("assunto");
		String dataString = request.getParameter("dataEnvio");
		LocalDate dataEnvio = LocalDate.now();
		
		if (dataString != null && !dataString.trim().isEmpty()) {
			try {
				dataEnvio = LocalDate.parse(dataString);
			} catch (DateTimeParseException e) {
				System.err.println("Erro ao converter data: " + dataString);
			}
		}
		String destinatario = request.getParameter("destinatario");
		
		dao.inserirEmailEnviado(new EmailEnviado(assunto, dataEnvio, destinatario));
		
	}
	
	private void deletarEmailEnviado(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		long idEmailEnviado = Long.parseLong(request.getParameter("idEmailEnviado"));
		
		EmailEnviado emailEnviado = dao.recuperarEmailEnviado(new EmailEnviado(idEmailEnviado));
		
		dao.deletarEmailEnviado(emailEnviado);
		
	}
	
	private void listarEmailsEnviados(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
	}
	
	
	
	
	
	
	
	
	
}

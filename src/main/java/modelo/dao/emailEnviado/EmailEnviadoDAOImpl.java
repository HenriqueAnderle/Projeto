package modelo.dao.emailEnviado;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.entidade.emailEnviado.EmailEnviado;

public class EmailEnviadoDAOImpl implements EmailEnviadoDAO {

	public void inserirEmailEnviado(EmailEnviado emailEnviado) {
		
		Connection conexao = null;
		PreparedStatement insert = null;
		
		try {

			conexao = conectarBanco();
			insert = conexao.prepareStatement("INSERT INTO emailEnviado (assunto_emailEnviado, dataEnvio_emailEnviado, destinatario_emailEnviado) VALUES (?,?,?)");

			insert.setString(1, emailEnviado.getAssunto());
			
			LocalDate localDate = emailEnviado.getDataEnvio(); 
	        
	        if (localDate != null) {
	            insert.setDate(2, Date.valueOf(localDate));
	        } else {
	            insert.setNull(2, java.sql.Types.DATE); 
	        }
	        
	        insert.setString(3, emailEnviado.getDestinatario());

			insert.execute();

		} catch (SQLException erro) {
			erro.printStackTrace();
		}

		finally {

			try {

				if (insert != null)
					insert.close();

				if (conexao != null)
					conexao.close();

			} catch (SQLException erro) {

				erro.printStackTrace();
			}
		}
	}

	public void deletarEmailEnviado(EmailEnviado emailEnviado) {
		
		Connection conexao = null;
		PreparedStatement delete = null;

		try {

			conexao = conectarBanco();
			delete = conexao.prepareStatement("DELETE FROM emailEnviado WHERE id_emailEnviado = ?");

			delete.setLong(1, emailEnviado.getIdEmailEnviado());

			delete.execute();

		} catch (SQLException erro) {
			erro.printStackTrace();
		}

		finally {

			try {

				if (delete != null)
					delete.close();

				if (conexao != null)
					conexao.close();

			} catch (SQLException erro) {

				erro.printStackTrace();
			}
		}
		
	}

	public EmailEnviado recuperarEmailEnviado(EmailEnviado emailEnviado) {
		
		Connection conexao = null;
	    PreparedStatement consulta = null;
	    ResultSet resultado = null;
	    
	    EmailEnviado emailEnviadoRecuperado = null;

	    try {
	        conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM emailEnviado WHERE id_emailEnviado = ?");
	        
	        consulta.setLong(1, emailEnviado.getIdEmailEnviado());
	        
	        resultado = consulta.executeQuery();

	        if (resultado.next()) { 
	            
	        	long idEmailEnviado = resultado.getLong("id_emailEnviado");
	            String assunto = resultado.getString("assunto_emailEnviado");
	            LocalDate dataEnvio = resultado.getDate("dataEnvio_emailEnviado").toLocalDate();
	            String destinatorio = resultado.getString("destinatario_emailEnviado");
 
	            emailEnviadoRecuperado = new EmailEnviado(idEmailEnviado, assunto, dataEnvio, destinatorio);
	        }

	    } catch (SQLException erro) {
	        erro.printStackTrace();
	    } finally {
	        try {
	            if (resultado != null) resultado.close();
	            if (consulta != null) consulta.close();
	            if (conexao != null) conexao.close();
	        } catch (SQLException erro) {
	            erro.printStackTrace();
	        }
	    }

	    return emailEnviadoRecuperado;
	}

	public List<EmailEnviado> recuperarEmailsEnviados() {
		
		Connection conexao = null;
		Statement consulta = null;
		ResultSet resultado = null;

		List<EmailEnviado> emailsEnviados = new ArrayList<EmailEnviado>();

		try {

			conexao = conectarBanco();
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery("SELECT * FROM emailEnviado");

			while (resultado.next()) {

				long idEmailEnviado = resultado.getLong("id_emailEnviado");
				String assunto = resultado.getString("assunto_emailEnviado");
				LocalDate dataEnvio = resultado.getDate("dataEnvio_emailEnviado").toLocalDate();
				String destinatario = resultado.getString("destinatario_emailEnviado");

				emailsEnviados.add(new EmailEnviado(idEmailEnviado, assunto, dataEnvio, destinatario));
			}

		} catch (SQLException erro) {
			erro.printStackTrace();
		}

		finally {

			try {

				if (resultado != null)
					resultado.close();

				if (consulta != null)
					consulta.close();

				if (conexao != null)
					conexao.close();

			} catch (SQLException erro) {

				erro.printStackTrace();
			}
		}

		return emailsEnviados;
	}
	
	private Connection conectarBanco() throws SQLException {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {  
			
		}
		String url = System.getenv("DB_URL");
		String user = System.getenv("DB_USER");
		String password = System.getenv("DB_PASSWORD");

		Connection conexao = DriverManager.getConnection(url, user, password);
		
		return conexao;
	}

}

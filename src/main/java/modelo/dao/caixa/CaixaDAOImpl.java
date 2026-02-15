package modelo.dao.caixa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.entidade.caixa.Caixa;
import modelo.entidade.emailEnviado.EmailEnviado;
import modelo.entidade.usuario.Usuario;

public class CaixaDAOImpl implements CaixaDAO {

	public void inserirCaixa(Caixa caixa) {
		
		Connection conexao = null;
		PreparedStatement insert = null;
		
		try {

			conexao = conectarBanco();
			insert = conexao.prepareStatement("INSERT INTO caixa (id_email, id_usuario) VALUES (?,?)");

			insert.setLong(1, caixa.getEmailEnviado().getIdEmailEnviado());
			
			insert.setLong(2, caixa.getUsuario().getIdUsuario());

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
	

	public void deletarCaixa(Caixa caixa) {
		
		Connection conexao = null;
		PreparedStatement delete = null;

		try {

			conexao = conectarBanco();
			delete = conexao.prepareStatement("DELETE FROM caixa WHERE id_caixa = ?");

			delete.setLong(1, caixa.getIdCaixa());

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

	public Caixa recuperarCaixa(Caixa caixa) {
		
		Connection conexao = null;
	    PreparedStatement consulta = null;
	    ResultSet resultado = null;
	    
	    Caixa caixaRecuperada = null;

	    try {
	        conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM caixa WHERE id_caixa = ?");
	        
	        consulta.setLong(1, caixa.getIdCaixa());
	        
	        resultado = consulta.executeQuery();

	        if (resultado.next()) { 
	            
	        	long idCaixa = resultado.getLong("id_caixa");
	            EmailEnviado emailEnviado = new EmailEnviado(resultado.getLong("id_email"));
	            Usuario usuario = new Usuario(resultado.getLong("id_usuario"));
 
	            caixaRecuperada = new Caixa(idCaixa, emailEnviado, usuario);
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

	    return caixaRecuperada;
	}

	public List<Caixa> recuperarCaixas() {
		
		Connection conexao = null;
		Statement consulta = null;
		ResultSet resultado = null;

		List<Caixa> caixas = new ArrayList<Caixa>();

		try {

			conexao = conectarBanco();
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery("SELECT * FROM caixa");

			while (resultado.next()) {

				long idCaixa = resultado.getLong("id_caixa");
				EmailEnviado emailEnviado = new EmailEnviado(resultado.getLong("id_email"));
	            Usuario usuario = new Usuario(resultado.getLong("id_usuario"));

				caixas.add(new Caixa(idCaixa, emailEnviado, usuario));
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

		return caixas;
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

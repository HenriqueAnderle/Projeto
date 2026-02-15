package modelo.dao.contato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidade.contato.Contato;
import modelo.entidade.usuario.Usuario;

public class ContatoDAOImpl implements ContatoDAO {

	public void inserirContato(Contato contato) {
		
		Connection conexao = null;
		PreparedStatement insert = null;
		
		try {

			conexao = conectarBanco();
			insert = conexao.prepareStatement("INSERT INTO contato (nome_contato, email_contato, enviarEmail_contato, id_usuario) VALUES (?,?,?,?)");

			insert.setString(1, contato.getNome());
			insert.setString(2, contato.getEmail());
			insert.setBoolean(3, contato.getEnviarEmail());
			insert.setLong(4, contato.getUsuario().getIdUsuario());

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
	

	public void deletarContato(Contato contato) {
		
		Connection conexao = null;
		PreparedStatement delete = null;

		try {

			conexao = conectarBanco();
			delete = conexao.prepareStatement("DELETE FROM contato WHERE id_contato = ?");

			delete.setLong(1, contato.getIdContato());

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

	public void atualizarContato(Contato contato) {
		
		Connection conexao = null;
        PreparedStatement update = null;

        try {

            conexao = conectarBanco();
            update = conexao.prepareStatement("UPDATE contato SET nome_contato = ?, email_contato = ? WHERE id_contato = ?");
            
            update.setString(1, contato.getNome());
            update.setString(2, contato.getEmail());
            update.setLong(3, contato.getIdContato());

            update.execute();

        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        finally {
            try {
                if (update != null) update.close();
                if (conexao != null) conexao.close();
            } catch (SQLException erro) {
                erro.printStackTrace();
            }
        }
	}
	
	public void atualizarContatoEnviarEmail(Contato contato, boolean enviarEmail) {
		
		Connection conexao = null;
        PreparedStatement update = null;

        try {

            conexao = conectarBanco();
            update = conexao.prepareStatement("UPDATE contato SET enviarEmail_contato = ? WHERE id_contato = ?");
            
            update.setBoolean(1, enviarEmail);
            
            update.setLong(2, contato.getIdContato());

            update.execute();

        } catch (SQLException erro) {
            erro.printStackTrace();
        }

        finally {
            try {
                if (update != null) update.close();
                if (conexao != null) conexao.close();
            } catch (SQLException erro) {
                erro.printStackTrace();
            }
        }
	}

	public Contato recuperarContato(Contato contato) {
		
		Connection conexao = null;
	    PreparedStatement consulta = null;
	    ResultSet resultado = null;
	    
	    Contato contatoRecuperado = null;

	    try {
	        conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM contato WHERE id_contato = ?");
	        
	        consulta.setLong(1, contato.getIdContato());
	        
	        resultado = consulta.executeQuery();

	        if (resultado.next()) { 
	            
	        	long idContato = resultado.getLong("id_contato");
	            String nome = resultado.getString("nome_contato");
	            String email = resultado.getString("email_contato");
	            Usuario usuario = new Usuario(resultado.getLong("id_usuario"));
 
	            contatoRecuperado = new Contato(idContato, nome, email, usuario);
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

	    return contatoRecuperado;
	}

	public List<Contato> recuperarContatos(Usuario usuario) {
		
		Connection conexao = null;
		PreparedStatement consulta = null;
		ResultSet resultado = null;

		List<Contato> contatos = new ArrayList<Contato>();

		try {

			conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM contato WHERE id_usuario = ?");
	        
	        consulta.setLong(1, usuario.getIdUsuario());
	        
	        resultado = consulta.executeQuery();

			while (resultado.next()) {

				long idContato = resultado.getLong("id_contato");
				String nome = resultado.getString("nome_contato");
				String email = resultado.getString("email_contato");
				boolean enviarEmail = resultado.getBoolean("enviarEmail_contato");

				contatos.add(new Contato(idContato, nome, email, enviarEmail, usuario));
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

		return contatos;
	}
	
	public List<Contato> recuperarContatosAdicionados(Usuario usuario) {
		
		Connection conexao = null;
		PreparedStatement consulta = null;
		ResultSet resultado = null;

		List<Contato> contatosAdicionados = new ArrayList<Contato>();

		try {

			conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM contato WHERE id_usuario = ? AND enviarEmail_contato = 1");
	        
	        consulta.setLong(1, usuario.getIdUsuario());
	        
	        resultado = consulta.executeQuery();

			while (resultado.next()) {

				long idContato = resultado.getLong("id_contato");
				String nome = resultado.getString("nome_contato");
				String email = resultado.getString("email_contato");
				boolean enviarEmail = resultado.getBoolean("enviarEmail_contato");

				contatosAdicionados.add(new Contato(idContato, nome, email, enviarEmail, usuario));
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

		return contatosAdicionados;
	}
	
	public List<Contato> recuperarContatosNaoAdicionados(Usuario usuario) {
		
		Connection conexao = null;
		PreparedStatement consulta = null;
		ResultSet resultado = null;

		List<Contato> contatos = new ArrayList<Contato>();

		try {

			conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM contato WHERE id_usuario = ? AND enviarEmail_contato = 0");
	        
	        consulta.setLong(1, usuario.getIdUsuario());
	        
	        resultado = consulta.executeQuery();

			while (resultado.next()) {

				long idContato = resultado.getLong("id_contato");
				String nome = resultado.getString("nome_contato");
				String email = resultado.getString("email_contato");
				boolean enviarEmail = resultado.getBoolean("enviarEmail_contato");

				contatos.add(new Contato(idContato, nome, email, enviarEmail, usuario));
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

		return contatos;
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

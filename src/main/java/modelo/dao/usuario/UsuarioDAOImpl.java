package modelo.dao.usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.entidade.usuario.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	public void inserirUsuario(Usuario usuario) {
		
		Connection conexao = null;
		PreparedStatement insert = null;
		
		try {

			conexao = conectarBanco();
			insert = conexao.prepareStatement("INSERT INTO usuario (nome_usuario, email_usuario, senha_usuario, contadorRelatorio_usuario, contadorSolicitacao_usuario, contadorSolicitacao2_usuario) VALUES (?,?,?,?,?,?)");

			insert.setString(1, usuario.getNome());
			insert.setString(2, usuario.getEmail());
			insert.setString(3, usuario.getSenha());
			insert.setLong(4, usuario.getContadorRelatorio());
			insert.setLong(5, usuario.getContadorSolicitacao());
			insert.setLong(6, usuario.getContadorSolicitacao2());

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
	

	public void deletarUsuario(Usuario usuario) {
		
		Connection conexao = null;
		PreparedStatement delete = null;

		try {

			conexao = conectarBanco();
			delete = conexao.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");

			delete.setLong(1, usuario.getIdUsuario());

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

	public void atualizarUsuario(Usuario usuario) {
		
		Connection conexao = null;
        PreparedStatement update = null;

        try {

            conexao = conectarBanco();
            update = conexao.prepareStatement("UPDATE usuario SET nome_usuario = ?, email_usuario = ?, senha_usuario = ? WHERE id_usuario = ?");
            
            update.setString(1, usuario.getNome());
            update.setString(2, usuario.getEmail());
            update.setString(3, usuario.getSenha());
            update.setLong(4, usuario.getIdUsuario());

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
	
	public void atualizarUsuarioContadorRelatorio(Usuario usuario, long contadorRelatorio) {
		
		Connection conexao = null;
        PreparedStatement update = null;

        try {

            conexao = conectarBanco();
            update = conexao.prepareStatement("UPDATE usuario SET contadorRelatorio_usuario = ? WHERE id_usuario = ?");
            
            update.setLong(1, usuario.getContadorRelatorio());
            update.setLong(2, usuario.getIdUsuario());

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
	
	public void atualizarUsuarioContadorSolicitacao(Usuario usuario, long contadorSolicitacao) {
		
		Connection conexao = null;
        PreparedStatement update = null;

        try {

            conexao = conectarBanco();
            update = conexao.prepareStatement("UPDATE usuario SET contadorSolicitacao_usuario = ? WHERE id_usuario = ?");
            
            update.setLong(1, usuario.getContadorSolicitacao());
            update.setLong(2, usuario.getIdUsuario());

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
	
	public void atualizarUsuarioContadorSolicitacao2(Usuario usuario, long contadorSolicitacao2) {
		
		Connection conexao = null;
        PreparedStatement update = null;

        try {

            conexao = conectarBanco();
            update = conexao.prepareStatement("UPDATE usuario SET contadorSolicitacao2_usuario = ? WHERE id_usuario = ?");
            
            update.setLong(1, usuario.getContadorSolicitacao2());
            update.setLong(2, usuario.getIdUsuario());

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
	
	public void salvarRememberToken(long idUsuario, String token) throws SQLException {
	    
		Connection conexao = null;
        PreparedStatement update = null;
		
        try {
        
		String sql = "UPDATE usuario SET remember_token = ? WHERE id_usuario = ?";
	    
		conexao = conectarBanco();
		update = conexao.prepareStatement(sql);
	    
		update.setString(1, token);
		update.setLong(2, idUsuario);
		update.executeUpdate();
		
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
	
	public Usuario buscarPorRememberToken(String token) throws SQLException {

	    String sql = "SELECT * FROM usuario WHERE remember_token = ?";

	    try (Connection conexao = conectarBanco();
	         PreparedStatement stmt = conexao.prepareStatement(sql)) {

	        stmt.setString(1, token);

	        try (ResultSet rs = stmt.executeQuery()) {

	            if (rs.next()) {
	                return new Usuario(
	                        rs.getLong("id_usuario"),
	                        rs.getString("nome_usuario"),
	                        rs.getString("email_usuario"),
	                        rs.getString("senha_usuario"),
	                        rs.getLong("contadorRelatorio_usuario"),
	                        rs.getLong("contadorSolicitacao_usuario"),
	                        rs.getLong("contadorSolicitacao2_usuario")
	                );
	            }
	        }
	    }

	    return null;
	}
	
	public boolean existeUsuario(Usuario usuario) {
		
		Connection conexao = null;
		Statement consulta = null;
		ResultSet resultado = null;
		
		try {
	 
			conexao = conectarBanco();
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery("SELECT email_usuario, senha_usuario FROM usuario");
	 
			while (resultado.next()) {
	 
				String emailExistente = resultado.getString("email_usuario");
				String senhaExistente = resultado.getString("senha_usuario");
				
				if(emailExistente.equals(usuario.getEmail()) && senhaExistente.equals(usuario.getSenha())) {
					
					return true;
				}
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

		return false;
	}

	public Usuario recuperarUsuario(Usuario usuario) {
		
		Connection conexao = null;
	    PreparedStatement consulta = null;
	    ResultSet resultado = null;
	    
	    Usuario usuarioRecuperado = null;

	    try {
	        conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM usuario WHERE id_usuario = ?");
	        
	        consulta.setLong(1, usuario.getIdUsuario());
	        
	        resultado = consulta.executeQuery();

	        if (resultado.next()) { 
	            
	        	long idUsuario = resultado.getLong("id_usuario");
	            String nome = resultado.getString("nome_usuario");
	            String email = resultado.getString("email_usuario");
	            String senha = resultado.getString("senha_usuario");
	            long contadorRelatorio = resultado.getLong("contadorRelatorio_usuario");
	            long contadorSolicitacao = resultado.getLong("contadorSolicitacao_usuario");
	            long contadorSolicitacao2 = resultado.getLong("contadorSolicitacao2_usuario");
 
	            usuarioRecuperado = new Usuario(idUsuario, nome, email, senha, contadorRelatorio, contadorSolicitacao, contadorSolicitacao2);
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

	    return usuarioRecuperado;
	}
	
	public Usuario recuperarUsuarioEmail(String email) {
		
		Connection conexao = null;
	    PreparedStatement consulta = null;
	    ResultSet resultado = null;
	    
	    Usuario usuarioRecuperado = null;

	    try {
	        conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM usuario WHERE email_usuario = ?");
	        
	        consulta.setString(1, email);
	        
	        resultado = consulta.executeQuery();

	        if (resultado.next()) { 
	            
	        	long idUsuario = resultado.getLong("id_usuario");
	            String nome = resultado.getString("nome_usuario");
	            String senha = resultado.getString("senha_usuario");
	            long contadorRelatorio = resultado.getLong("contadorRelatorio_usuario");
	            long contadorSolicitacao = resultado.getLong("contadorSolicitacao_usuario");
	            long contadorSolicitacao2 = resultado.getLong("contadorSolicitacao2_usuario");
	            
	            usuarioRecuperado = new Usuario(idUsuario, nome, email, senha, contadorRelatorio, contadorSolicitacao, contadorSolicitacao2);
	            
	            return usuarioRecuperado;
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

	    return null;
	}
	
	public Usuario recuperarUsuarioLogin(Usuario usuario) {
		
		Connection conexao = null;
	    PreparedStatement consulta = null;
	    ResultSet resultado = null;
	    
	    Usuario usuarioRecuperado = null;

	    try {
	        conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM usuario WHERE email_usuario = ? AND senha_usuario = ?");
	        
	        consulta.setString(1, usuario.getEmail());
	        consulta.setString(2, usuario.getSenha());
	        
	        resultado = consulta.executeQuery();

	        if (resultado.next()) { 
	            
	        	long idUsuario = resultado.getLong("id_usuario");
	            String nome = resultado.getString("nome_usuario");
	            String email = resultado.getString("email_usuario");
	            String senha = resultado.getString("senha_usuario");
 
	            usuarioRecuperado = new Usuario(idUsuario, nome, email, senha);
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

	    return usuarioRecuperado;
	}

	public List<Usuario> recuperarUsuarios() {
		
		Connection conexao = null;
		Statement consulta = null;
		ResultSet resultado = null;

		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {

			conexao = conectarBanco();
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery("SELECT * FROM usuario");

			while (resultado.next()) {

				long idUsuario = resultado.getLong("id_usuario");
				String nome = resultado.getString("nome_usuario");
				String email = resultado.getString("email_usuario");
				String senha = resultado.getString("senha_usuario");

				usuarios.add(new Usuario(idUsuario, nome, email, senha));
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

		return usuarios;
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

package modelo.dao.obra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidade.obra.Obra;
import modelo.entidade.usuario.Usuario;

public class ObraDAOImpl implements ObraDAO {

	public void inserirObra(Obra obra) {
		
		Connection conexao = null;
		PreparedStatement insert = null;
		
		try {

			conexao = conectarBanco();
			insert = conexao.prepareStatement("INSERT INTO obra (nome_obra, id_usuario) VALUES (?,?)");

			insert.setString(1, obra.getNome());
			insert.setLong(2, obra.getUsuario().getIdUsuario());

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
	

	public void deletarObra(Obra obra) {
		
		Connection conexao = null;
		PreparedStatement delete = null;

		try {

			conexao = conectarBanco();
			delete = conexao.prepareStatement("DELETE FROM obra WHERE id_obra = ?");

			delete.setLong(1, obra.getIdObra());

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

	public void atualizarObra(Obra obra) {
		
		Connection conexao = null;
        PreparedStatement update = null;

        try {

            conexao = conectarBanco();
            update = conexao.prepareStatement("UPDATE obra SET nome_obra = ? WHERE id_obra = ?");
            
            update.setString(1, obra.getNome());
            update.setLong(2, obra.getIdObra());

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

	public Obra recuperarObra(Obra obra) {
		
		Connection conexao = null;
	    PreparedStatement consulta = null;
	    ResultSet resultado = null;
	    
	    Obra obraRecuperado = null;

	    try {
	        conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM obra WHERE id_obra = ?");
	        
	        consulta.setLong(1, obra.getIdObra());
	        
	        resultado = consulta.executeQuery();

	        if (resultado.next()) { 
	            
	        	long idObra = resultado.getLong("id_obra");
	            String nome = resultado.getString("nome_obra");
	            Usuario usuario = new Usuario(resultado.getLong("id_usuario"));
 
	            obraRecuperado = new Obra(idObra, nome, usuario);
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

	    return obraRecuperado;
	}

	public List<Obra> recuperarObras(Usuario usuario) {
		
		Connection conexao = null;
		PreparedStatement consulta = null;
		ResultSet resultado = null;

		List<Obra> obras = new ArrayList<Obra>();

		try {

			conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM obra WHERE id_usuario = ?");
	        
	        consulta.setLong(1, usuario.getIdUsuario());
	        
	        resultado = consulta.executeQuery();

			while (resultado.next()) {

				long idObra = resultado.getLong("id_obra");
				String nome = resultado.getString("nome_obra");

				obras.add(new Obra(idObra, nome, usuario));
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

		return obras;
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

package modelo.dao.vistoriador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.entidade.relatorio.Relatorio;
import modelo.entidade.vistoriador.Vistoriador;

public class VistoriadorDAOImpl implements VistoriadorDAO {

	public void inserirVistoriador(Vistoriador vistoriador) {
		
		Connection conexao = null;
		PreparedStatement insert = null;
		
		try {

			conexao = conectarBanco();
			insert = conexao.prepareStatement("INSERT INTO vistoriador (nome_vistoriador, id_relatorio) VALUES (?,?)");

			insert.setString(1, vistoriador.getNome());
			insert.setLong(2, vistoriador.getRelatorio().getIdRelatorio());

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
	

	public void deletarVistoriador(Vistoriador vistoriador) {
		
		Connection conexao = null;
		PreparedStatement delete = null;

		try {

			conexao = conectarBanco();
			delete = conexao.prepareStatement("DELETE FROM vistoriador WHERE id_vistoriador = ?");

			delete.setLong(1, vistoriador.getIdVistoriador());

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
	
	public void atualizarVistoriador(Vistoriador vistoriador) {
		
		Connection conexao = null;
        PreparedStatement update = null;

        try {

            conexao = conectarBanco();
            update = conexao.prepareStatement("UPDATE vistoriador SET nome_vistoriador = ?, id_relatorio = ? WHERE id_vistoriador = ?");
            
            update.setString(1, vistoriador.getNome());
            update.setLong(2, vistoriador.getRelatorio().getIdRelatorio());
            update.setLong(3, vistoriador.getIdVistoriador());

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

	public Vistoriador recuperarVistoriador(Vistoriador vistoriador) {
		
		Connection conexao = null;
	    PreparedStatement consulta = null;
	    ResultSet resultado = null;
	    
	    Vistoriador vistoriadorRecuperado = null;

	    try {
	        conexao = conectarBanco();
	        consulta = conexao.prepareStatement("SELECT * FROM vistoriador WHERE id_vistoriador = ?");
	        
	        consulta.setLong(1, vistoriador.getIdVistoriador());
	        
	        resultado = consulta.executeQuery();

	        if (resultado.next()) { 
	            
	        	long idVistoriador = resultado.getLong("id_vistoriador");
	            String nome = resultado.getString("nome_vistoriador");
	            Relatorio relatorio = new Relatorio(resultado.getLong("id_relatorio"));
 
	            vistoriadorRecuperado = new Vistoriador(idVistoriador, nome, relatorio);
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

	    return vistoriadorRecuperado;
	}

	public List<Vistoriador> recuperarVistoriadores() {
		
		Connection conexao = null;
		Statement consulta = null;
		ResultSet resultado = null;

		List<Vistoriador> vistoriadores = new ArrayList<Vistoriador>();

		try {

			conexao = conectarBanco();
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery("SELECT * FROM vistoriador");

			while (resultado.next()) {

				long idVistoriador = resultado.getLong("id_vistoriador");
	            String nome = resultado.getString("nome_vistoriador");
	            Relatorio relatorio = new Relatorio(resultado.getLong("id_relatorio"));

	            vistoriadores.add(new Vistoriador(idVistoriador, nome, relatorio));
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

		return vistoriadores;
	}
	
	public List<Vistoriador> recuperarVistoriadoresIdRelatorio(Relatorio relatorio) {
		
		Connection conexao = null;
		PreparedStatement consulta = null;
		ResultSet resultado = null;

		List<Vistoriador> vistoriadores = new ArrayList<Vistoriador>();

		try {

			conexao = conectarBanco();
	        
			consulta = conexao.prepareStatement("SELECT * FROM vistoriador WHERE id_relatorio = ?");
			
			consulta.setLong(1, relatorio.getIdRelatorio());
	        
			resultado = consulta.executeQuery();

			while (resultado.next()) {

				long idVistoriador = resultado.getLong("id_vistoriador");
	            String nome = resultado.getString("nome_vistoriador");

	            vistoriadores.add(new Vistoriador(idVistoriador, nome, relatorio));
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

		return vistoriadores;
	}
	
	private Connection conectarBanco() throws SQLException {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {  
			
		}
		String url = System.getenv("DB_URL");
		String user = System.getenv("DB_USER");
		String password = System.getenv("DB_PASSWORD");

		return DriverManager.getConnection(url, user, password);
	}
	

}

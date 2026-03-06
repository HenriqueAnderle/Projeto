package modelo.dao.usuario;

import java.sql.SQLException;
import java.util.List;

import modelo.entidade.usuario.Usuario;

public interface UsuarioDAO {

	void inserirUsuario(Usuario usuario);
	
	void deletarUsuario(Usuario usuario);
	
	void atualizarUsuario(Usuario usuario);
	
	void atualizarUsuarioContadorRelatorio(Usuario usuario, long contadorRelatorio);
	
	void atualizarUsuarioContadorSolicitacao(Usuario usuario, long contadorSolicitacao);

	void atualizarUsuarioContadorSolicitacao2(Usuario usuario, long contadorSolicitacao2);
	
	void salvarRememberToken(long idUsuario, String token) throws SQLException;
	
	Usuario buscarPorRememberToken(String token) throws SQLException;
	
	boolean existeUsuario(Usuario usuario);
	
	Usuario recuperarUsuario(Usuario usuario);
	
	Usuario recuperarUsuarioEmail(String email);
	
	Usuario recuperarUsuarioLogin(Usuario usuario);
	
	List<Usuario> recuperarUsuarios();

}

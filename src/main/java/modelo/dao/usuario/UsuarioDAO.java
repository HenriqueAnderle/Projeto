package modelo.dao.usuario;

import java.util.List;

import modelo.entidade.usuario.Usuario;

public interface UsuarioDAO {

	void inserirUsuario(Usuario usuario);
	
	void deletarUsuario(Usuario usuario);
	
	void atualizarUsuario(Usuario usuario);
	
	void atualizarUsuarioContadorRelatorio(Usuario usuario, long contadorRelatorio);
	
	boolean existeUsuario(Usuario usuario);
	
	Usuario recuperarUsuario(Usuario usuario);
	
	Usuario recuperarUsuarioEmail(String email);
	
	Usuario recuperarUsuarioLogin(Usuario usuario);
	
	List<Usuario> recuperarUsuarios();

}

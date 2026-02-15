package modelo.dao.contato;

import java.util.List;

import modelo.entidade.contato.Contato;
import modelo.entidade.usuario.Usuario;

public interface ContatoDAO {

	void inserirContato(Contato contato);
	
	void deletarContato(Contato contato);
	
	void atualizarContato(Contato contato);
	
	void atualizarContatoEnviarEmail(Contato contato, boolean enviarEmail);
	
	Contato recuperarContato(Contato contato);
	
	List<Contato> recuperarContatos(Usuario usuario);
	
	List<Contato> recuperarContatosAdicionados(Usuario usuario);
	
	List<Contato> recuperarContatosNaoAdicionados(Usuario usuario);

}

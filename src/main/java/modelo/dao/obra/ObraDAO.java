package modelo.dao.obra;

import java.util.List;

import modelo.entidade.obra.Obra;
import modelo.entidade.usuario.Usuario;

public interface ObraDAO {

	void inserirObra(Obra obra);
	
	void deletarObra(Obra obra);
	
	void atualizarObra(Obra obra);
		
	Obra recuperarObra(Obra obra);
	
	List<Obra> recuperarObras(Usuario usuario);

}

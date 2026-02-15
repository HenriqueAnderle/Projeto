package modelo.dao.vistoriador;

import java.util.List;

import modelo.entidade.relatorio.Relatorio;
import modelo.entidade.vistoriador.Vistoriador;

public interface VistoriadorDAO {

	void inserirVistoriador(Vistoriador vistoriador);
	
	void deletarVistoriador(Vistoriador vistoriador);
	
	void atualizarVistoriador(Vistoriador vistoriador);
		
	Vistoriador recuperarVistoriador(Vistoriador vistoriador);
	
	List<Vistoriador> recuperarVistoriadores();
	
	List<Vistoriador> recuperarVistoriadoresIdRelatorio(Relatorio relatorio);


}

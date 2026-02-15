package modelo.dao.caixa;

import java.util.List;

import modelo.entidade.caixa.Caixa;

public interface CaixaDAO {

	void inserirCaixa(Caixa caixa);
	
	void deletarCaixa(Caixa caixa);
	
	Caixa recuperarCaixa(Caixa caixa);
	
	List<Caixa> recuperarCaixas();

}

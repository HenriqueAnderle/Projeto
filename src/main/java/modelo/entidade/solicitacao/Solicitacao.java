package modelo.entidade.solicitacao;

import modelo.entidade.solicitacaoMateriaisServicos.SolicitacaoMateriaisServicos;
import modelo.enumeracao.Unidade;

public class Solicitacao {

	private String materialServico;
	private Unidade unidade;
	private String quantidade;
	private String descricao;
	private SolicitacaoMateriaisServicos solicitacaoMateriaisServicos;
	
	public Solicitacao(String materialServico, Unidade unidade, String quantidade, String descricao, SolicitacaoMateriaisServicos solicitacaoMateriaisServicos) {
		setMaterialServico(materialServico);
		setUnidade(unidade);
		setQuantidade(quantidade);
		setDescricao(descricao);
		setSolicitacaoMateriaisServicos(solicitacaoMateriaisServicos);
	}
	
	public Solicitacao() {
	}
	
	public String getMaterialServico() {
		return materialServico;
	}
	
	public void setMaterialServico(String materialServico) {
		this.materialServico = materialServico;
	}
	
	public Unidade getUnidade() {
		return unidade;
	}
	
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	public String getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public SolicitacaoMateriaisServicos getSolicitacaoMateriaisServicos() {
		return  solicitacaoMateriaisServicos;
	}
	
	public void setSolicitacaoMateriaisServicos(SolicitacaoMateriaisServicos solicitacaoMateriaisServicos) {
		this.solicitacaoMateriaisServicos = solicitacaoMateriaisServicos;
	}
}

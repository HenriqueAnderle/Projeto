package modelo.entidade.naoConformidade;

import modelo.entidade.relatorio.Relatorio;
import modelo.enumeracao.EtapaDaObra;
import modelo.enumeracao.Pavimento;
import modelo.enumeracao.TipoDeNaoConformidade;

public class NaoConformidade {

	private long idNaoConformidade;
	private Pavimento pavimento;
	private EtapaDaObra etapaDaObra;
	private TipoDeNaoConformidade tipoDeNaoConformidade;
	private String imagem;
	private String observacao;
	private Relatorio relatorio;
	
	public NaoConformidade(long idNaoConformidade, Pavimento pavimento, EtapaDaObra etapaDaObra, TipoDeNaoConformidade tipoDeNaoConformidade, String imagem, String observacao, Relatorio relatorio) {
		setIdNaoConformidade(idNaoConformidade);
		setPavimento(pavimento);
		setEtapaDaObra(etapaDaObra);
		setTipoDeNaoConformidade(tipoDeNaoConformidade);
		setImagem(imagem);
		setObservacao(observacao);
		setRelatorio(relatorio);
	}
	
	public NaoConformidade(long idNaoConformidade, Pavimento pavimento, EtapaDaObra etapaDaObra, TipoDeNaoConformidade tipoDeNaoConformidade, String observacao, Relatorio relatorio) {
		setIdNaoConformidade(idNaoConformidade);
		setPavimento(pavimento);
		setEtapaDaObra(etapaDaObra);
		setTipoDeNaoConformidade(tipoDeNaoConformidade);
		setObservacao(observacao);
		setRelatorio(relatorio);
	}
	
	public NaoConformidade(Pavimento pavimento, EtapaDaObra etapaDaObra, TipoDeNaoConformidade tipoDeNaoConformidade, String imagem, String observacao, Relatorio relatorio) {
		setPavimento(pavimento);
		setEtapaDaObra(etapaDaObra);
		setTipoDeNaoConformidade(tipoDeNaoConformidade);
		setImagem(imagem);
		setObservacao(observacao);
		setRelatorio(relatorio);
	}
	
	public NaoConformidade(Pavimento pavimento, EtapaDaObra etapaDaObra, TipoDeNaoConformidade tipoDeNaoConformidade, String observacao, Relatorio relatorio) {
		setPavimento(pavimento);
		setEtapaDaObra(etapaDaObra);
		setTipoDeNaoConformidade(tipoDeNaoConformidade);
		setObservacao(observacao);
		setRelatorio(relatorio);
	}
	
	public NaoConformidade(long idNaoConformidade) {
		setIdNaoConformidade(idNaoConformidade);
	}
	
	public NaoConformidade() {
		
	}
	
	public long getIdNaoConformidade() {
		return idNaoConformidade;
	}
	
	public void setIdNaoConformidade(long idNaoConformidade) {
		this.idNaoConformidade = idNaoConformidade;
	}
	
	public Pavimento getPavimento() {
		return pavimento;
	}
	
	public void setPavimento(Pavimento pavimento) {
		this.pavimento = pavimento;
	}
	
	public EtapaDaObra getEtapaDaObra() {
		return etapaDaObra;
	}
	
	public void setEtapaDaObra(EtapaDaObra etapaDaObra) {
		this.etapaDaObra = etapaDaObra;
	}
	
	public TipoDeNaoConformidade getTipoDeNaoConformidade() {
		return tipoDeNaoConformidade;
	}
	
	public void setTipoDeNaoConformidade(TipoDeNaoConformidade tipoDeNaoConformidade) {
		this.tipoDeNaoConformidade = tipoDeNaoConformidade;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Relatorio getRelatorio() {
		return relatorio;
	}
	
	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}
}

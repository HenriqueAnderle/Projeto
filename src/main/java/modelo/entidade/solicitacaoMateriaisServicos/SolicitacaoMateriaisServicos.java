package modelo.entidade.solicitacaoMateriaisServicos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import modelo.entidade.obra.Obra;
import modelo.entidade.usuario.Usuario;
import modelo.enumeracao.EtapaDaObra;
import modelo.enumeracao.Solicitante;

public class SolicitacaoMateriaisServicos {

	private long idSolicitacao;
	private Obra obra;
	private Solicitante solicitante;
	private EtapaDaObra etapaDaObra;
	private String imagem;
	private LocalDate dataSolicitacao;
	private LocalDateTime previsaoChegada;
	private String observacao;
	private boolean logo;
	private Usuario usuario;
	
	public SolicitacaoMateriaisServicos(long idSolicitacao, Obra obra, Solicitante solicitante, EtapaDaObra etapaDaObra, String imagem, LocalDate dataSolicitacao, LocalDateTime previsaoChegada, String observacao, boolean logo, Usuario usuario) {
		setIdSolicitacao(idSolicitacao);
		setObra(obra);
		setSolicitante(solicitante);
		setEtapaDaObra(etapaDaObra);
		setImagem(imagem);
		setDataSolicitacao(dataSolicitacao);
		setPrevisaoChegada(previsaoChegada);
		setObservacao(observacao);
		setLogo(logo);
		setUsuario(usuario);
	}
	
	public SolicitacaoMateriaisServicos(Obra obra, Solicitante solicitante, EtapaDaObra etapaDaObra, String imagem, LocalDate dataSolicitacao, LocalDateTime previsaoChegada, String observacao, boolean logo, Usuario usuario) {
		setObra(obra);
		setSolicitante(solicitante);
		setEtapaDaObra(etapaDaObra);
		setImagem(imagem);
		setDataSolicitacao(dataSolicitacao);
		setPrevisaoChegada(previsaoChegada);
		setObservacao(observacao);
		setLogo(logo);
		setUsuario(usuario);
	}
	
	public SolicitacaoMateriaisServicos(Obra obra) {
		setObra(obra);
	}
	
	public SolicitacaoMateriaisServicos(long idSolicitacao) {
		setIdSolicitacao(idSolicitacao);
	}
	
	public long getIdSolicitacao() {
		return idSolicitacao;
	}
	
	public void setIdSolicitacao(long idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}
	
	public Obra getObra() {
		return obra;
	}
	
	public void setObra(Obra obra) {
		this.obra = obra;
	}
	
	public Solicitante getSolicitante() {
		return solicitante;
	}
	
	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
	}
	
	public EtapaDaObra getEtapaDaObra() {
		return etapaDaObra;
	}
	
	public void setEtapaDaObra(EtapaDaObra etapaDaObra) {
		this.etapaDaObra = etapaDaObra;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public LocalDate getDataSolicitacao() {
		return dataSolicitacao;
	}
	
	public void setDataSolicitacao(LocalDate dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	
	public LocalDateTime getPrevisaoChegada() {
		return previsaoChegada;
	}
	
	public void setPrevisaoChegada(LocalDateTime previsaoChegada) {
		this.previsaoChegada = previsaoChegada;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public boolean getLogo() {
		return logo;
	}
	
	public void setLogo(boolean logo) {
		this.logo = logo;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}

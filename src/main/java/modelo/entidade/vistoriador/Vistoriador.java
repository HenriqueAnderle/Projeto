package modelo.entidade.vistoriador;

import modelo.entidade.relatorio.Relatorio;

public class Vistoriador {

	private long idVistoriador;
	private String nome;
	private Relatorio relatorio;
	
	public Vistoriador(long idVistoriador, String nome, Relatorio relatorio) {
		setIdVistoriador(idVistoriador);
		setNome(nome);
		setRelatorio(relatorio);
	}
	
	public Vistoriador(String nome, Relatorio relatorio) {
		setNome(nome);
		setRelatorio(relatorio);
	}
	
	public Vistoriador(long idVistoriador) {
		setIdVistoriador(idVistoriador);
	}
	
	public Vistoriador() {
	}
	
	public long getIdVistoriador() {
		return idVistoriador;
	}
	
	public void setIdVistoriador(long idVistoriador) {
		this.idVistoriador = idVistoriador;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Relatorio getRelatorio() {
		return relatorio;
	}
	
	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}
}

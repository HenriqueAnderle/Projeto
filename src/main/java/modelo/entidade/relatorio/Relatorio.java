package modelo.entidade.relatorio;

import java.time.LocalDate;

import modelo.entidade.obra.Obra;
import modelo.entidade.usuario.Usuario;
import modelo.enumeracao.CondicaoClimatica;
import modelo.enumeracao.Tempo;

public class Relatorio {
	
	private long idRelatorio;
	private  LocalDate dataRelatorio;
	private Obra obra;
	private String empresaExecutora;
	private CondicaoClimatica condicaoClimatica;
	private Tempo tempo;
	private boolean condicao;
	private Usuario usuario;
	
	public Relatorio(long idRelatorio, LocalDate dataRelatorio, Obra obra, String empresaExecutora, CondicaoClimatica condicaoClimatica, Tempo tempo, boolean condicao, Usuario usuario) {
		setIdRelatorio(idRelatorio);
		setDataRelatorio(dataRelatorio);
		setObra(obra);
		setEmpresaExecutora(empresaExecutora);
		setCondicaoClimatica(condicaoClimatica);
		setTempo(tempo);
		setCondicao(condicao);
		setUsuario(usuario);
	}
	
	public Relatorio(LocalDate dataRelatorio, Obra obra, String empresaExecutora, CondicaoClimatica condicaoClimatica, Tempo tempo, boolean condicao, Usuario usuario) {
		setDataRelatorio(dataRelatorio);
		setObra(obra);
		setEmpresaExecutora(empresaExecutora);
		setCondicaoClimatica(condicaoClimatica);
		setTempo(tempo);
		setCondicao(condicao);
		setUsuario(usuario);
	}
	
	public Relatorio(long idRelatorio) {
		setIdRelatorio(idRelatorio);
	}
	
	public long getIdRelatorio() {
		return idRelatorio;
	}
	
	public void setIdRelatorio(long idRelatorio) {
		this.idRelatorio = idRelatorio;
	}
	
	public LocalDate getDataRelatorio() {
		return dataRelatorio;
	}
	
	public void setDataRelatorio(LocalDate dataRelatorio) {
		this.dataRelatorio = dataRelatorio;
	}
	
	public Obra getObra() {
		return obra;
	}
	
	public void setObra(Obra obra) {
		this.obra = obra;
	}
	
	public String getEmpresaExecutora() {
		return empresaExecutora;
	}
	
	public void setEmpresaExecutora(String empresaExecutora) {
		this.empresaExecutora = empresaExecutora;
	}
	
	public CondicaoClimatica getCondicaoClimatica() {
		return condicaoClimatica;
	}
	
	public void setCondicaoClimatica(CondicaoClimatica condicaoClimatica) {
		this.condicaoClimatica = condicaoClimatica;
	}
	
	public Tempo getTempo() {
		return tempo;
	}
	
	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	}
	
	public boolean getCondicao() {
		return condicao;
	}
	
	public void setCondicao(boolean condicao) {
		this.condicao = condicao;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
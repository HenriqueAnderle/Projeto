package modelo.entidade.obra;

import modelo.entidade.usuario.Usuario;

public class Obra {

	private long idObra;
	private String nome;
	private Usuario usuario;
	
	public Obra(long idObra, String nome, Usuario usuario) {
		setIdObra(idObra);
		setNome(nome);
		setUsuario(usuario);
	}
	
	public Obra(long idObra, String nome) {
		setIdObra(idObra);
		setNome(nome);
	}
	
	public Obra(String nome, Usuario usuario) {
		setNome(nome);
		setUsuario(usuario);
	}
	
	public Obra(String nome) {
		setNome(nome);
	}
	
	public Obra(long idObra) {
		setIdObra(idObra);
	}
	
	public Obra() {
		
	}

	public long getIdObra() {
		return idObra;
	}
	
	public void setIdObra(long idObra) {
		this.idObra = idObra;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
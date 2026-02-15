package modelo.entidade.contato;

import modelo.entidade.usuario.Usuario;

public class Contato {

	private long idContato;
	private String nome;
	private String email;
	private boolean enviarEmail;
	private Usuario usuario;
	
	public Contato(long idContato, String nome, String email, boolean enviarEmail, Usuario usuario) {
		setIdContato(idContato);
		setNome(nome);
		setEmail(email);
		setEnviarEmail(enviarEmail);
		setUsuario(usuario);
	}
	
	public Contato(long idContato, String nome, String email, Usuario usuario) {
		setIdContato(idContato);
		setNome(nome);
		setEmail(email);
		setUsuario(usuario);
	}
	
	public Contato(long idContato, String nome, String email) {
		setIdContato(idContato);
		setNome(nome);
		setEmail(email);
	}
	
	public Contato(String nome, String email, Usuario usuario) {
		setNome(nome);
		setEmail(email);
		setUsuario(usuario);
	}
	
	public Contato(String nome, String email, boolean enviarEmail, Usuario usuario) {
		setNome(nome);
		setEmail(email);
		setEnviarEmail(enviarEmail);
		setUsuario(usuario);
	}
	
	public Contato(String email) {
		setEmail(email);
	}
	
	public Contato(long idContato) {
		setIdContato(idContato);
	}

	public long getIdContato() {
		return idContato;
	}
	
	public void setIdContato(long idContato) {
		this.idContato = idContato;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean getEnviarEmail() {
		return enviarEmail;
	}
	
	public void setEnviarEmail(boolean enviarEmail) {
		this.enviarEmail = enviarEmail;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

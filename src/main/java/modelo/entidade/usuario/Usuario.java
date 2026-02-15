package modelo.entidade.usuario;


public class Usuario {

	private long idUsuario;
	private String nome;
	private String email;
	private String senha;
	private long contadorRelatorio;
	
	public Usuario(long idUsuario, String nome, String email, String senha) {
		setIdUsuario(idUsuario);
		setNome(nome);
		setEmail(email);
		setSenha(senha);
	}
	
	public Usuario(long idUsuario, String nome, String email, String senha, long contadorRelatorio) {
		setIdUsuario(idUsuario);
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setContadorRelatorio(contadorRelatorio);
	}
	
	public Usuario(String nome, String email, String senha) {
		setNome(nome);
		setEmail(email);
		setSenha(senha);
	}
	
	public Usuario(String nome, String email, String senha, long contadorRelatorio) {
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setContadorRelatorio(contadorRelatorio);
	}
	
	public Usuario(String email, String senha) {
		setEmail(email);
		setSenha(senha);
	}
	
	public Usuario(long idUsuario) {
		setIdUsuario(idUsuario);
	}

	public long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
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
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public long getContadorRelatorio() {
		return contadorRelatorio;
	}
	
	public void setContadorRelatorio(long contadorRelatorio) {
		this.contadorRelatorio = contadorRelatorio;
	}
	
	public long proximoRelatorio(Usuario usuario) {
		
		contadorRelatorio = usuario.getContadorRelatorio();
		
		return ++contadorRelatorio;
	}
	
}

package modelo.entidade.caixa;

import modelo.entidade.emailEnviado.EmailEnviado;
import modelo.entidade.usuario.Usuario;

public class Caixa {

	private long idCaixa;
	private EmailEnviado emailEnviado;
	private Usuario usuario;
	
	public Caixa(long idCaixa, EmailEnviado emailEnviado, Usuario usuario) {
		setIdCaixa(idCaixa);
		setEmailEnviado(emailEnviado);
		setUsuario(usuario);
	}
	
	public Caixa(EmailEnviado emailEnviado, Usuario usuario) {
		setEmailEnviado(emailEnviado);
		setUsuario(usuario);
	}
	
	public Caixa(long idCaixa) {
		setIdCaixa(idCaixa);
	}
	
	public long getIdCaixa() {
		return idCaixa;
	}
	
	public void setIdCaixa(long idCaixa) {
		this.idCaixa = idCaixa;
	}
	
	public EmailEnviado getEmailEnviado() {
		return emailEnviado;
	}
	
	public void setEmailEnviado(EmailEnviado emailEnviado) {
		this.emailEnviado = emailEnviado;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
}

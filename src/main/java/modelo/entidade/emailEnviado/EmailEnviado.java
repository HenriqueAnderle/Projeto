package modelo.entidade.emailEnviado;

import java.time.LocalDate;

public class EmailEnviado {

	private long idEmailEnviado;
	private String assunto;
	private LocalDate dataEnvio;
	private String destinatario;
	
	public EmailEnviado(long idEmailEnviado, String assunto, LocalDate dataEnvio, String destinatario) {
		setIdEmailEnviado(idEmailEnviado);
		setAssunto(assunto);
		setDataEnvio(dataEnvio);
		setDestinatario(destinatario);
	}
	
	public EmailEnviado(String assunto, LocalDate dataEnvio, String destinatario) {
		setAssunto(assunto);
		setDataEnvio(dataEnvio);
		setDestinatario(destinatario);
	}
	
	public EmailEnviado(long idEmailEnviado) {
		setIdEmailEnviado(idEmailEnviado);
	}
	
	public long getIdEmailEnviado() {
		return idEmailEnviado;
	}
	
	public void setIdEmailEnviado(long idEmailEnviado) {
		this.idEmailEnviado = idEmailEnviado;
	}
	
	public String getAssunto() {
		return assunto;
	}
	
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	public LocalDate getDataEnvio() {
		return dataEnvio;
	}
	
	public void setDataEnvio(LocalDate dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	
	public String getDestinatario() {
		return destinatario;
	}
	
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
}

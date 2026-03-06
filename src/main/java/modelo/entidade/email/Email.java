package modelo.entidade.email;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import javax.mail.util.ByteArrayDataSource;

import modelo.entidade.usuario.Usuario;

import javax.mail.Multipart;

public class Email {

	public void enviarRelatorio(Usuario usuario, List<String> destinatarios, String mensagem, byte[] pdfBytes)
            throws MessagingException {
		
        String email = System.getenv("MAIL_IDHEA_ENGENHARIA");
        String senha = System.getenv("MAIL_SENHA_ENGENHARIA");
        
        if (email == null || senha == null) {throw new RuntimeException("Variáveis de ambiente MAIL_USUARIO ou MAIL_SENHA não definidas");}

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, senha);
                }
            });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));

        InternetAddress[] enderecos = new InternetAddress[destinatarios.size()];
        for (int i = 0; i < destinatarios.size(); i++) {
            enderecos[i] = new InternetAddress(destinatarios.get(i));
        }

        message.setRecipients(Message.RecipientType.TO, enderecos);
        message.setSubject(mensagem);

        MimeBodyPart texto = new MimeBodyPart();
        texto.setText(mensagem +" de " + usuario.getNome() + "\nPor favor, não responder a esse E-Mail");

        MimeBodyPart anexo = new MimeBodyPart();
        DataSource source =
                new ByteArrayDataSource(pdfBytes, "application/pdf");
        anexo.setDataHandler(new DataHandler(source));
        
        if(mensagem.equals("Relatório de Vistoria")) {
        	anexo.setFileName("relatorio_vistoria.pdf");
        }
        if(mensagem.equals("Solicitação de Materiais e Serviços")) {
        	anexo.setFileName("solicitacao_materiais_servicos.pdf");
        }
        
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(texto);
        multipart.addBodyPart(anexo);

        message.setContent(multipart);
        
        Transport.send(message);
    }
	
	public void enviarSolicitacaoMannz(Usuario usuario, List<String> destinatarios, String mensagem, byte[] pdfBytes)
            throws MessagingException {

        String email = System.getenv("MAIL_IDHEA_MANNZ");
        String senha = System.getenv("MAIL_SENHA_MANNZ");
        
        if (email == null || senha == null) {throw new RuntimeException("Variáveis de ambiente MAIL_USUARIO ou MAIL_SENHA não definidas");}

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, senha);
                }
            });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));

        InternetAddress[] enderecos = new InternetAddress[destinatarios.size()];
        for (int i = 0; i < destinatarios.size(); i++) {
            enderecos[i] = new InternetAddress(destinatarios.get(i));
        }

        message.setRecipients(Message.RecipientType.TO, enderecos);
        message.setSubject(mensagem);

        MimeBodyPart texto = new MimeBodyPart();
        texto.setText(mensagem +" de " + usuario.getNome() + "\nPor favor, não responder a esse E-Mail");

        MimeBodyPart anexo = new MimeBodyPart();
        DataSource source =
                new ByteArrayDataSource(pdfBytes, "application/pdf");
        anexo.setDataHandler(new DataHandler(source));
        
        if(mensagem.equals("Relatório de Vistoria")) {
        	anexo.setFileName("relatorio_vistoria.pdf");
        }
        if(mensagem.equals("Solicitação de Materiais e Serviços")) {
        	anexo.setFileName("solicitacao_materiais_servicos.pdf");
        }
        
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(texto);
        multipart.addBodyPart(anexo);

        message.setContent(multipart);
        
        Transport.send(message);
    }
}
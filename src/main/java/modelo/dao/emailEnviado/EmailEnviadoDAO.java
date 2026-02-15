package modelo.dao.emailEnviado;

import java.util.List;

import modelo.entidade.emailEnviado.EmailEnviado;

public interface EmailEnviadoDAO {

	void inserirEmailEnviado(EmailEnviado emailEnviado);
	
	void deletarEmailEnviado(EmailEnviado emailEnviado);
		
	EmailEnviado recuperarEmailEnviado(EmailEnviado emailEnviado);
	
	List<EmailEnviado> recuperarEmailsEnviados();

}

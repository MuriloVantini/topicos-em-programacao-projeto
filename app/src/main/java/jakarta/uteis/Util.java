package jakarta.uteis;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class Util {
	
	public void adicionarMensagem(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, 
				mensagem, mensagem);
		facesContext.addMessage(null, m);
	}

}

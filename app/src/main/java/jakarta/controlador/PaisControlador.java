package jakarta.controlador;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@SessionScoped
@Named
public class PaisControlador implements Serializable {

	private static final long serialVersionUID = 1L;

	public String prepararTelaCadastro() {
		return "cadastrarpais.xhtml";
	}

}

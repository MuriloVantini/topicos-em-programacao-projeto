package jakarta.controlador;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.dao.ReinoDao;
import jakarta.beans.Reino;


@SessionScoped
@Named
public class ReinoControlador implements Serializable {

	private static final long serialVersionUID = 1L;

	private Reino reino;
	private List<Reino> reinos;

	
	public String prepararTelaConsulta() {
		ReinoDao reinoDao = new ReinoDao();
		reinos = reinoDao.pesquisar();
		return "consultarreino.xhtml";
	}
	
	public String prepararTelaCadastro() {
		Reino novoReino = new Reino();
		return "cadastrarreino.xhtml";
	}

	public String gravar() {
		ReinoDao reinoDao = new ReinoDao();
		reinoDao.gravar(reino);
		return "menuprincipal.xhtml";
	}
}

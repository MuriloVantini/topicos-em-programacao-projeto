package jakarta.controlador;

import java.io.Serializable;
import java.util.List;

import jakarta.beans.Pais;
import jakarta.dao.PaisDao;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.uteis.Util;

@SessionScoped
@Named
public class PaisControlador implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pais novoPais;
	private List<Pais> paises;
	
	public void excluir(Pais p) {
		PaisDao dao = new PaisDao();
		dao.remover(p);
		paises = dao.pesquisar();
	}

	public String prepararTelaConsulta() {
		PaisDao dao = new PaisDao();
		paises = dao.pesquisar();
		return "consultarpais.xhtml";
	}
	
	public String voltar() {
		return "menuprincipal.xhtml";
	}

	public String prepararTelaCadastro() {
		novoPais = new Pais();
		return "cadastrarpais.xhtml";
	}

	public String gravar() {
		if (!validarDados()) {
			return null;
		}
		PaisDao dao = new PaisDao();
		dao.cadastrar(novoPais);
		new Util().adicionarMensagem("Pais cadastrado com sucesso");
		return "menuprincipal.xhtml";
	}

	private boolean validarDados() {
		PaisDao dao = new PaisDao();
		if (dao.existe(novoPais)) {
			new Util().adicionarMensagem("Pais existente!");
			return false;
		}
		return true;
	}

	public Pais getNovoPais() {
		return novoPais;
	}

	public void setNovoPais(Pais novoPais) {
		this.novoPais = novoPais;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}
}

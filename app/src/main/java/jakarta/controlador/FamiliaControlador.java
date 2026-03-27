package jakarta.controlador;

import java.io.Serializable;
import java.util.List;

import jakarta.beans.Familia;
import jakarta.beans.Reino;
import jakarta.dao.FamiliaDao;
import jakarta.dao.ReinoDao;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.uteis.Util;

@SessionScoped
@Named
public class FamiliaControlador implements Serializable {

	private static final long serialVersionUID = 1L;

	private Familia novaFamilia;
	private List<Familia> familias;
	private List<Reino> reinos;
	private Integer idSelecionadoReino;

	public void excluir(Familia f) {
		FamiliaDao dao = new FamiliaDao();
		dao.remover(f);
		familias = dao.pesquisar();
	}

	public String prepararTelaConsulta() {
		FamiliaDao dao = new FamiliaDao();
		familias = dao.pesquisar();
		return "consultarfamilia.xhtml";
	}

	public String voltar() {
		return "menuprincipal.xhtml";
	}

	public String prepararTelaCadastro() {
		novaFamilia = new Familia();
		reinos = new ReinoDao().pesquisar();
		return "cadastrarfamilia.xhtml";
	}

	public String gravar() {
		if (!validarDados()) {
			return null;
		}
		FamiliaDao dao = new FamiliaDao();
		Reino r = new ReinoDao().getReino(idSelecionadoReino);
		novaFamilia.setReino(r);
		dao.cadastrar(novaFamilia);
		new Util().adicionarMensagem("Familia cadastrada com sucesso");
		return "menuprincipal.xhtml";
	}

	private boolean validarDados() {
		FamiliaDao dao = new FamiliaDao();
		if (dao.existe(novaFamilia)) {
			new Util().adicionarMensagem("Familia existente!");
			return false;
		}
		return true;
	}

	public Familia getNovaFamilia() {
		return novaFamilia;
	}

	public void setNovaFamilia(Familia novaFamilia) {
		this.novaFamilia = novaFamilia;
	}

	public List<Familia> getFamilias() {
		return familias;
	}

	public void setFamilias(List<Familia> familias) {
		this.familias = familias;
	}

	public List<Reino> getReinos() {
		return reinos;
	}

	public void setReinos(List<Reino> reinos) {
		this.reinos = reinos;
	}

	public Integer getIdSelecionadoReino() {
		return idSelecionadoReino;
	}

	public void setIdSelecionadoReino(Integer idSelecionadoReino) {
		this.idSelecionadoReino = idSelecionadoReino;
	}

}

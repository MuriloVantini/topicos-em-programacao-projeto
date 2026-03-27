package jakarta.controlador;

import java.io.Serializable;
import java.util.List;

import jakarta.beans.Especie;
import jakarta.beans.Familia;
import jakarta.dao.EspecieDao;
import jakarta.dao.FamiliaDao;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.uteis.Util;

@SessionScoped
@Named
public class EspecieControlador implements Serializable {

	private static final long serialVersionUID = 1L;

	private Especie novaEspecie;
	private List<Especie> especies;
	private List<Familia> familias;
	private Integer idSelecionadoFamilia;

	public void excluir(Especie e) {
		EspecieDao dao = new EspecieDao();
		dao.remover(e);
		especies = dao.pesquisar();
	}

	public String prepararTelaConsulta() {
		EspecieDao dao = new EspecieDao();
		especies = dao.pesquisar();
		return "consultarespecie.xhtml";
	}

	public String voltar() {
		return "menuprincipal.xhtml";
	}

	public String prepararTelaCadastro() {
		novaEspecie = new Especie();
		familias = new FamiliaDao().pesquisar();
		return "cadastrarespecie.xhtml";
	}

	public String gravar() {
		if (!validarDados()) {
			return null;
		}
		EspecieDao dao = new EspecieDao();
		// busca a familia selecionada
		Familia f = familias.stream()
				.filter(fam -> fam.getId().equals(idSelecionadoFamilia))
				.findFirst().orElse(null);
		novaEspecie.setFamilia(f);
		dao.cadastrar(novaEspecie);
		new Util().adicionarMensagem("Especie cadastrada com sucesso");
		return "menuprincipal.xhtml";
	}

	private boolean validarDados() {
		EspecieDao dao = new EspecieDao();
		if (dao.existe(novaEspecie)) {
			new Util().adicionarMensagem("Especie existente!");
			return false;
		}
		return true;
	}

	public Especie getNovaEspecie() {
		return novaEspecie;
	}

	public void setNovaEspecie(Especie novaEspecie) {
		this.novaEspecie = novaEspecie;
	}

	public List<Especie> getEspecies() {
		return especies;
	}

	public void setEspecies(List<Especie> especies) {
		this.especies = especies;
	}

	public List<Familia> getFamilias() {
		return familias;
	}

	public void setFamilias(List<Familia> familias) {
		this.familias = familias;
	}

	public Integer getIdSelecionadoFamilia() {
		return idSelecionadoFamilia;
	}

	public void setIdSelecionadoFamilia(Integer idSelecionadoFamilia) {
		this.idSelecionadoFamilia = idSelecionadoFamilia;
	}

}

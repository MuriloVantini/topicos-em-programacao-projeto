package jakarta.controlador;

import java.io.Serializable;
import java.util.List;

import jakarta.beans.Reino;
import jakarta.dao.ReinoDao;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.uteis.Util;

@SessionScoped
@Named
public class ReinoControlador implements Serializable {

	private static final long serialVersionUID = 1L;

	private Reino novoReino;
	private List<Reino> reinos;

	public void excluir(Reino r) {
		ReinoDao dao = new ReinoDao();
		dao.remover(r);
		reinos = dao.pesquisar();
	}

	public String prepararTelaConsulta() {
		ReinoDao dao = new ReinoDao();
		this.reinos = dao.pesquisar();
		return "consultarreino.xhtml";
	}

	public String voltar() {
		return "menuprincipal.xhtml";
	}

	public String prepararTelaCadastro() {
		novoReino = new Reino();
		return "cadastrarreino.xhtml";
	}

	public String gravar() {
		if (!validarDados()) {
			return null;
		}
		ReinoDao dao = new ReinoDao();
		dao.cadastrar(novoReino);
		new Util().adicionarMensagem("Reino cadastrado com sucesso");
		return "menuprincipal.xhtml";
	}

	private boolean validarDados() {
		ReinoDao dao = new ReinoDao();
		if (dao.existe(novoReino)) {
			new Util().adicionarMensagem("Reino existente!");
			return false;
		}
		return true;
	}

	public Reino getNovoReino() {
		return novoReino;
	}

	public void setNovoReino(Reino novoReino) {
		this.novoReino = novoReino;
	}

	public List<Reino> getReinos() {
		return reinos;
	}

	public boolean getExibirTitulo() {
		return reinos != null && !reinos.isEmpty();
	}

	public void setReinos(List<Reino> reinos) {
		this.reinos = reinos;
	}
}

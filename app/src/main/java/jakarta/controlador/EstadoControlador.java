package jakarta.controlador;

import java.io.Serializable;
import java.util.List;

import jakarta.beans.Estado;
import jakarta.beans.Pais;
import jakarta.dao.EstadoDao;
import jakarta.dao.PaisDao;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.uteis.Util;

@SessionScoped
@Named
public class EstadoControlador implements Serializable {

	private static final long serialVersionUID = 1L;

	private Estado novoEstado;
	private List<Estado> estados;
	private List<Pais> paises;
	private Integer idSelecionadoPais;

	public void excluir(Estado e) {
		EstadoDao dao = new EstadoDao();
		dao.remover(e);
		estados = dao.pesquisar();
	}

	public String prepararTelaConsulta() {
		EstadoDao dao = new EstadoDao();
		estados = dao.pesquisar();
		return "consultarestado.xhtml";
	}

	public String voltar() {
		return "menuprincipal.xhtml";
	}

	public String prepararTelaCadastro() {
		novoEstado = new Estado();
		paises = new PaisDao().pesquisar();
		return "cadastrarestado.xhtml";
	}

	public String gravar() {
		if (!validarDados()) {
			return null;
		}
		EstadoDao dao = new EstadoDao();
		Pais p = new PaisDao().getPais(idSelecionadoPais);
		novoEstado.setPais(p);
		dao.cadastrar(novoEstado);
		new Util().adicionarMensagem("Estado cadastrado com sucesso");
		return "menuprincipal.xhtml";
	}

	private boolean validarDados() {
		EstadoDao dao = new EstadoDao();
		if (dao.existe(novoEstado)) {
			new Util().adicionarMensagem("Estado existente!");
			return false;
		}
		return true;
	}

	public Estado getNovoEstado() {
		return novoEstado;
	}

	public void setNovoEstado(Estado novoEstado) {
		this.novoEstado = novoEstado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	public Integer getIdSelecionadoPais() {
		return idSelecionadoPais;
	}

	public void setIdSelecionadoPais(Integer idSelecionadoPais) {
		this.idSelecionadoPais = idSelecionadoPais;
	}

}

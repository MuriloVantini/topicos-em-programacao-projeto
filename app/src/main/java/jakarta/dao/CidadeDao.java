package jakarta.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Cidade;

public class CidadeDao {

	private static List<Cidade> cidades = new ArrayList<Cidade>();

	public void cadastrar(Cidade cid) {
		cidades.add(cid);
	}

	public List<Cidade> pesquisar() {
		return cidades;
	}

	public void remover(Cidade cid) {
		cidades.remove(cid);
	}
}

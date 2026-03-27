package jakarta.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Reino;

public class ReinoDao {

	private static List<Reino> reinos = new ArrayList<Reino>();

	public boolean existe(Reino r) {
		return reinos.contains(r);
	}

	public Reino getReino(Integer id) {
		for (Reino r : reinos) {
			if (r.getId().equals(id)) {
				return r;
			}
		}
		return null;
	}

	public void cadastrar(Reino r) {
		reinos.add(r);
	}

	public List<Reino> pesquisar() {
		return reinos;
	}

	public void remover(Reino r) {
		reinos.remove(r);
	}
}

package jakarta.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Reino;

public class ReinoDao {

	private static List<Reino> reinos = new ArrayList<Reino>();

	public void gravar(Reino r) {
		reinos.add(r);
	}

	public List<Reino> pesquisar() {
		return reinos;
	}

	public void remover(Reino r) {
		reinos.remove(r);
	}
}

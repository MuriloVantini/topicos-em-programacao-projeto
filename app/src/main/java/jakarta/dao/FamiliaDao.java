package jakarta.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Familia;

public class FamiliaDao {

	private static List<Familia> familias = new ArrayList<Familia>();

	public void gravar(Familia f) {
		familias.add(f);
	}

	public List<Familia> pesquisar() {
		return familias;
	}

	public void remover(Familia f) {
		familias.remove(f);
	}
}

package jakarta.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Especie;

public class EspecieDao {

	private static List<Especie> especies = new ArrayList<Especie>();

	public void gravar(Especie esp) {
		especies.add(esp);
	}

	public List<Especie> pesquisar() {
		return especies;
	}

	public void remover(Especie esp) {
		especies.remove(esp);
	}
}

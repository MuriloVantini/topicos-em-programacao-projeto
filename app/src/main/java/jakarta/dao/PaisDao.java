package jakarta.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Pais;

public class PaisDao {

	private static List<Pais> paises = new ArrayList<Pais>();

	public void cadastrar(Pais p) {
		paises.add(p);
	}

	public List<Pais> pesquisar() {
		return paises;
	}

	public void remover(Pais p) {
		paises.remove(p);
	}
}

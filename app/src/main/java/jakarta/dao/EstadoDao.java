package jakarta.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.beans.Estado;

public class EstadoDao {

	private static List<Estado> estados = new ArrayList<Estado>();

	public void cadastrar(Estado e) {
		estados.add(e);
	}

	public List<Estado> pesquisar() {
		return estados;
	}

	public void remover(Estado e) {
		estados.remove(e);
	}
}

package jakarta.beans;

import java.util.Objects;

public class Pais {
	
	private Integer id;
	
	private String nome;

	private boolean possuiExercito;
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		return Objects.equals(id, other.id);
	}

	public String getDescricaoExercito() {
		return this.possuiExercito ? "Sim" : "Não";
	}

	public boolean getPossuiExercito(){
		return this.possuiExercito;
	}

	public void setPossuiExercito(boolean hasExercito) {
		this.possuiExercito = hasExercito;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}

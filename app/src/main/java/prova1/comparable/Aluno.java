package prova1.comparable;

public class Aluno implements Comparable<Aluno> {
    private String nome;
    private Integer idade;

    public Aluno(String nome, Integer idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return this.nome;
    }

    public Integer getIdade() {
        return this.idade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    @Override
    public int compareTo(Aluno o) {
        return idade.compareTo(o.getIdade());
    }
}

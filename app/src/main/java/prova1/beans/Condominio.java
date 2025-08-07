package prova1.beans;

import java.util.ArrayList;

public class Condominio {
    private Integer codigo;
    private String nome;
    // (1 condomínio tem várias casas) Relacionamento 1-N com Casa
    private ArrayList<Casa> casas = new ArrayList<>();
    // (1 condomínio tem vários moradores) Relacionamento 1-N com Morador
    private ArrayList<Morador> moradores = new ArrayList<>();

    @Override
    public String toString() {
        String result = "Condominio [codigo=" + codigo + ", nome=" + nome + ", casas=[";
        for (Casa casa : casas) {
            result += "{codigo=" + casa.getCodigo() + ", nome=" + casa.getNome() + "}, ";
        }
        result += "], moradores=[";
        for (Morador morador : moradores) {
            result += "{codigo=" + morador.getCodigo() + ", nome=" + morador.getNome() + "}, ";
        }
        result += "]]";
        return result;
    }

    public void associarCasa(Casa casa) {
        casas.add(casa);
        casa.setCondominio(this);
    }

    public void associarMorador(Morador morador) {
        moradores.add(morador);
        morador.setCondominio(this);
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Casa> getCasas() {
        return casas;
    }

    public void setCasas(ArrayList<Casa> casas) {
        this.casas = casas;
    }

    public ArrayList<Morador> getMoradores() {
        return moradores;
    }

    public void setMoradores(ArrayList<Morador> moradores) {
        this.moradores = moradores;
    }
}

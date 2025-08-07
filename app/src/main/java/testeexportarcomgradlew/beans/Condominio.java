package testeexportarcomgradlew.beans;

import java.util.ArrayList;
import java.util.List;

public class Condominio {
    private Integer codigo;
    private String nome;
    private List<Casa> casas = new ArrayList<>();
    private List<Morador> moradores = new ArrayList<>();

    @Override
    public String toString() {
        String result = "Condominio [codigo=" + codigo + ", nome=" + nome + ", casas=[";
        for (Casa casa : casas) {
            result += "{codigo=" + casa.getCodigo() + ", nome=" + casa.getNome() + "}, ";
        }
        result += "], moradores=[";
        for (Morador morador : moradores) {
            result += "{codigo=" + morador.getCodigo() + ", nome=" + morador.getNome() +
                    "}, ";
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

    public List<Casa> getCasas() {
        return casas;
    }

    public void setCasas(List<Casa> casas) {
        this.casas = casas;
    }

    public List<Morador> getMoradores() {
        return moradores;
    }

    public void setMoradores(List<Morador> moradores) {
        this.moradores = moradores;
    }

    // @Override
    // public String toString() {
    // return "Condominio { codigo=" + codigo + ", nome='" + nome + "' }";
    // }

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
}

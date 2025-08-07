package testeexportarcomgradlew.beans;

import java.util.ArrayList;
import java.util.List;

public class Casa {

    private Integer codigo;
    private String nome;
    private Condominio condominio;
    private List<Morador> moradores = new ArrayList<Morador>();
    

    public void associarMorador(Morador morador) {
        moradores.add(morador);
        morador.setCasa(this);
    }

    @Override
    public String toString() {
        String result = "Casa [codigo=" + codigo
                + ", nome=" + nome
                + ", condominio=" + (condominio != null ? condominio.getNome() : "null")
                + ", moradores=[";
        for (Morador morador : moradores) {
            result += "{codigo=" + morador.getCodigo() + ", nome=" + morador.getNome() + "}, ";
        }
        result += "]]";
        return result;
    }

    // @Override
    // public String toString() {
    //     return "Casa { codigo=" + codigo + ", nome='" + nome + "' }";
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

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public List<Morador> getMoradores() {
        return moradores;
    }

    public void setMoradores(List<Morador> moradores) {
        this.moradores = moradores;
    }

}

package prova1.beans;

import java.util.ArrayList;

public class Casa {

    private Integer codigo;
    private String nome;
    // Cada casa pertence a um condomínio
    private Condominio condominio;
    // Relacionamento N-N com Morador: uma casa pode ter vários moradores; e um morador pode ter várias casas
    //ou
    //* Relacionamento 1-N: uma Casa possui vários Moradores.
    private ArrayList<Morador> moradores = new ArrayList<Morador>();
    

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

    public ArrayList<Morador> getMoradores() {
        return moradores;
    }

    public void setMoradores(ArrayList<Morador> moradores) {
        this.moradores = moradores;
    }

}

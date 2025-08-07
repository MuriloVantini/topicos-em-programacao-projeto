package prova1.beans;

public class Morador {
    private Integer codigo;

    private String nome;

    // Cada morador é inscrito em 1 condomínio
    private Condominio condominio;
    
    // Relacionamento N-N com Casa: um morador pode ter várias casas; e uma casa pode ter vários moradores
    // private ArrayList<Casa> casas = new ArrayList<>();

    //* Relacionamento N-1: cada Morador pertence a uma única Casa.
    private Casa casa;

    @Override
    public String toString() {
        return "Morador [codigo=" + codigo 
                + ", nome=" + nome 
                + ", condominio=" + (condominio != null ? condominio.getNome() : "null")
                + ", casa=" + (casa != null ? casa.getNome() : "null") + "]";
    }

    //relacionamento (N:M)
    // @Override
    // public String toString() {
    //     String result = "Morador [codigo=" + codigo
    //             + ", nome=" + nome
    //             + ", condominio=" + (condominio != null ? condominio.getNome() : "null")
    //             + ", casas=[";
    //     for (Casa casa : casas) {
    //         result += "{codigo=" + casa.getCodigo() + ", nome=" + casa.getNome() + "}, ";
    //     }
    //     result += "]]";
    //     return result;
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

    public Casa getCasa() {
        return casa;
    }
    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    //(relacionamento N:M)
    // public ArrayList<Casa> getCasas() {
    //     return casas;
    // }

    // public void setCasas(ArrayList<Casa> casas) {
    //     this.casas = casas;
    // }

}

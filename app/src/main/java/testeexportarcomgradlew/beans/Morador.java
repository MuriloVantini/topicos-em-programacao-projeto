package testeexportarcomgradlew.beans;

public class Morador {
    private Integer codigo;
    private String nome;
    private Condominio condominio;
    private Casa casa;

    @Override
    public String toString() {
        return "Morador [codigo=" + codigo
                + ", nome=" + nome
                + ", condominio=" + (condominio != null ? condominio.getNome() : "null")
                + ", casa=" + (casa != null ? casa.getNome() : "null") + "]";
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

    // @Override
    // public String toString() {
    //     return "Morador { codigo=" + codigo + ", nome='" + nome + "' }";
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

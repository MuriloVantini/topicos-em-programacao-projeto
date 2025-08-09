package testeexportarcomgradlew.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import testeexportarcomgradlew.beans.Casa;
import testeexportarcomgradlew.beans.Condominio;
import testeexportarcomgradlew.beans.Morador;
import testeexportarcomgradlew.dao.CasaDao;
import testeexportarcomgradlew.dao.CondominioDao;
import testeexportarcomgradlew.dao.MoradorDao;

public class RelacionamentoService {

    private CasaDao casaDao;
    private CondominioDao condominioDao;
    private MoradorDao moradorDao;

    public RelacionamentoService(Connection connection) throws SQLException {
        this.casaDao = new CasaDao(connection);
        this.condominioDao = new CondominioDao(connection);
        this.moradorDao = new MoradorDao(connection);

        // Garante que as tabelas existam
        this.condominioDao.createTable();
        this.casaDao.createTable();
        this.moradorDao.createTable();
    }

    public void executarTestesDeRelacionamento() throws SQLException {
        // Busca ou cria um condomínio padrão (id=1)
        Condominio condominio = condominioDao.getById(1);
        if (condominio == null) {
            condominio = new Condominio();
            condominio.setCodigo(1);
            condominio.setNome("Condomínio A");
            condominioDao.insert(condominio);
        }

        // Busca ou cria uma casa padrão (id=1)
        Casa casa = casaDao.getById(1);
        if (casa == null) {
            casa = new Casa();
            casa.setCodigo(1);
            casa.setNome("Casa 1");
            casa.setCondominio(condominio);
            casaDao.insert(casa);
        }
        casa.setNome("Casa Azul");
        casa.setCondominio(condominio);
        casaDao.update(casa);

        // Busca ou cria um morador padrão (id=1)
        Morador morador = moradorDao.getById(1);
        if (morador == null) {
            morador = new Morador();
            morador.setCodigo(1);
            morador.setNome("Morador 1");
            morador.setCondominio(condominio);
            morador.setCasa(casa);
            moradorDao.insert(morador);
        }
        morador.setNome("João Silva");
        morador.setCondominio(condominio);
        morador.setCasa(casa);
        moradorDao.update(morador);

        System.out.println("=== CONDOMINIOS ===");
        List<Condominio> condominios = this.getCondominiosCompletos();

        for (Condominio c : condominios) {
            System.out.println(c.toString());
        }

        System.out.println("=== TESTE TUDO JUNTO ===");
        List<Condominio> condominiosComTudoRelacionado = this.getTodasEntidadesRelacionadas();
        for (Condominio condominioInteiro : condominiosComTudoRelacionado) {
            System.out.println(condominioInteiro.toString());

            for (Casa casaInteira : condominioInteiro.getCasas()) {
                System.out.println(casaInteira.toString());
                
                for (Morador m : casaInteira.getMoradores()) {
                    System.out.println(m.toString());
                }
            }
        }
    }

    public List<Condominio> getCondominiosCompletos() throws SQLException {
        List<Condominio> condominios = condominioDao.getAll();

        for (Condominio condominio : condominios) {
            List<Casa> casas = casaDao.getCasasByCondominioId(condominio.getCodigo());
            condominio.setCasas(casas);
            // igual foreach casa + condominio.getCasas().add(casa) Só adiciona na lista, sem definir relacionamento do Objeto CASA, já que eu não vou usar ele aqui
            
            List<Morador> moradores = moradorDao.getMoradoresByCondominioId(condominio.getCodigo());
            condominio.setMoradores(moradores);
        }

        return condominios;
    }

    private List<Condominio> getTodasEntidadesRelacionadas() throws SQLException {
        List<Condominio> condominios = condominioDao.getAll();

        for (Condominio condominio : condominios) {
            List<Casa> casas = casaDao.getCasasByCondominioId(condominio.getCodigo());

            for (Casa casa : casas) {
                condominio.associarCasa(casa);

                List<Morador> moradoresDaCasa = moradorDao.getMoradoresByCasaId(casa.getCodigo());
                for (Morador morador : moradoresDaCasa) {
                    casa.associarMorador(morador);
                    condominio.associarMorador(morador);

                }
            }
        }

        return condominios;
    }
}
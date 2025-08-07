package testeexportarcomgradlew.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import testeexportarcomgradlew.beans.Casa;
import testeexportarcomgradlew.beans.Condominio;
import testeexportarcomgradlew.beans.Morador;
import testeexportarcomgradlew.dao.BaseDao;
import testeexportarcomgradlew.dao.CasaDao;
import testeexportarcomgradlew.dao.CondominioDao;
import testeexportarcomgradlew.dao.MoradorDao;

public class MoradorService extends BaseService<Morador> {

    private CasaDao casaDao;
    private CondominioDao condominioDao;

    public MoradorService(Connection connection) throws SQLException {
        super(connection);
        this.casaDao = new CasaDao(connection);
        this.condominioDao = new CondominioDao(connection);
    }

    @Override
    protected BaseDao<Morador> createDao(Connection connection) {
        return new MoradorDao(connection);
    }

    @Override
    protected Morador createTestEntity() throws SQLException {
        Condominio condominio = condominioDao.getById(1);
        Casa casa = casaDao.getById(1);

        Morador morador = new Morador();
        morador.setCodigo(getTestEntityId());
        morador.setNome("Morador de Teste");
        morador.setCondominio(condominio);
        morador.setCasa(casa);
        return morador;
    }

    @Override
    protected Integer getTestEntityId() {
        return 2;
    }

    @Override
    protected void updateTestEntity(Morador entity) {
        entity.setNome("Morador Atualizado");
    }

    @Override
    protected void testarListagem() throws SQLException {
        List<Morador> moradores = dao.getAll();

        for (Morador morador : moradores) {
            Condominio condominio = condominioDao.getById(morador.getCondominio().getCodigo());
            morador.setCondominio(condominio);

            Casa casa = casaDao.getById(morador.getCasa().getCodigo());
            casa.setCondominio(condominio);
            morador.setCasa(casa);
        }

        System.out.println("=== LISTAGEM ===");
        for (Morador morador : moradores) {
            System.out.println(morador.toString());
        }
    }
}
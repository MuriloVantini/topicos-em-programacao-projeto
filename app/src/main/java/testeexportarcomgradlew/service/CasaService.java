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

public class CasaService extends BaseService<Casa> {

    private CondominioDao condominioDao;
    private MoradorDao moradorDao;

    public CasaService(Connection connection) throws SQLException {
        super(connection);
        this.condominioDao = new CondominioDao(connection);
        this.moradorDao = new MoradorDao(connection);
    }

    @Override
    protected BaseDao<Casa> createDao(Connection connection) {
        return new CasaDao(connection);
    }

    @Override
    protected Casa createTestEntity() throws SQLException {
        Condominio condominio = condominioDao.getById(1);

        Casa casa = new Casa();
        casa.setCodigo(getTestEntityId());
        casa.setNome("Casa Vermelha");
        casa.setCondominio(condominio);
        return casa;
    }

    @Override
    protected Integer getTestEntityId() {
        return 2;
    }

    @Override
    protected void updateTestEntity(Casa entity) {
        entity.setNome("Casa Atualizada");
    }

    @Override
    protected void testarListagem() throws SQLException {
        List<Casa> casas = dao.getAll();

        for (Casa casa : casas) {
            Condominio condominio = condominioDao.getById(casa.getCondominio().getCodigo());
            casa.setCondominio(condominio);

            List<Morador> moradores = moradorDao.getMoradoresByCasaId(casa.getCodigo());
            casa.setMoradores(moradores);
        }

        System.out.println("=== LISTAGEM ===");
        for (Casa casa : casas) {
            System.out.println(casa.toString());
        }
    }
}
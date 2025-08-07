package testeexportarcomgradlew.service;

import java.sql.Connection;
import java.sql.SQLException;

import testeexportarcomgradlew.beans.Condominio;
import testeexportarcomgradlew.dao.BaseDao;
import testeexportarcomgradlew.dao.CondominioDao;

public class CondominioService extends BaseService<Condominio> {

    public CondominioService(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected BaseDao<Condominio> createDao(Connection connection) {
        return new CondominioDao(connection);
    }

    @Override
    protected Condominio createTestEntity() {
        Condominio condominio = new Condominio();
        condominio.setCodigo(getTestEntityId());
        condominio.setNome("Condominio Amaarelo");
        return condominio;
    }

    @Override
    protected Integer getTestEntityId() {
        return 2;
    }

    @Override
    protected void updateTestEntity(Condominio entity) {
        entity.setNome("Condominio Atualizado");
    }
}
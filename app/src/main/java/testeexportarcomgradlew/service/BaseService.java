package testeexportarcomgradlew.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import testeexportarcomgradlew.dao.BaseDao;

public abstract class BaseService<T> {

    protected Connection cnn;
    protected BaseDao<T> dao;

    public BaseService(Connection cnn) throws SQLException {
        this.cnn = cnn;
        this.dao = createDao(cnn);
        this.dao.createTable();
    }

    protected abstract BaseDao<T> createDao(Connection connection);

    protected abstract T createTestEntity() throws SQLException;

    protected abstract Integer getTestEntityId();

    protected abstract void updateTestEntity(T entity);

    public void executarTestes() throws SQLException {
        this.testarListagem();

        this.testarInsercao();
        this.testarListagem();

        this.testarAtualizacao();
        this.testarListagem();

        this.testarExclusao();
        this.testarListagem();
    }

    protected void testarInsercao() throws SQLException {
        T entity = createTestEntity();
        dao.insert(entity);
        System.out.println("Entidade inserida com sucesso: " + entity.toString());
    }

    protected void testarListagem() throws SQLException {
        List<T> entities = dao.getAll();
        System.out.println("=== LISTAGEM ===");
        for (T entity : entities) {
            System.out.println(entity.toString());
        }
    }

    protected void testarAtualizacao() throws SQLException {
        T entity = dao.getById(getTestEntityId());
        if (entity != null) {
            updateTestEntity(entity);
            dao.update(entity);
            System.out.println("Entidade atualizada com sucesso: " + entity.toString());
        } else {
            System.out.println("Entidade não encontrada para atualização");
        }
    }

    protected void testarExclusao() throws SQLException {
        T entity = dao.getById(getTestEntityId());
        if (entity != null) {
            dao.delete(entity);
            System.out.println("Entidade excluida com sucesso: codigo " + getTestEntityId());
        } else {
            System.out.println("Entidade não encontrada para exclusão");
        }
    }
}
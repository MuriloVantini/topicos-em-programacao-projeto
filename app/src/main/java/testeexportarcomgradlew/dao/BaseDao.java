package testeexportarcomgradlew.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao<T> {
    
    protected Connection cnn;
    
    public BaseDao(Connection cnn) {
        this.cnn = cnn;
    }
    
    public abstract void createTable() throws SQLException;
    public abstract String getTableName();
    public abstract String getInsertSql();
    public abstract String getUpdateSql();
    public abstract void setInsertParameters(PreparedStatement ps, T entity) throws SQLException;
    public abstract void setUpdateParameters(PreparedStatement ps, T entity) throws SQLException;
    public abstract T mapResultSet(ResultSet rs) throws SQLException;
    public abstract Integer getCodigo(T entity);
    
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + getTableName();
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }
    
    public List<T> getAll() throws SQLException {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " ORDER BY codigo";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            entities.add(mapResultSet(rs));
        }

        rs.close();
        ps.close();
        return entities;
    }
    
    public T getById(int codigo) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE codigo = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();

        T entity = null;
        while (rs.next()) {
            entity = mapResultSet(rs);
        }

        rs.close();
        ps.close();
        return entity;
    }
    
    public void insert(T entity) throws SQLException {
        T existingEntity = getById(getCodigo(entity));

        if (existingEntity != null) {
            throw new SQLException("Entidade duplicada: codigo " + getCodigo(entity));
        } else {
            PreparedStatement ps = cnn.prepareStatement(getInsertSql());
            setInsertParameters(ps, entity);
            ps.executeUpdate();
            ps.close();
        }
    }
    
    public void update(T entity) throws SQLException {
        PreparedStatement ps = cnn.prepareStatement(getUpdateSql());
        setUpdateParameters(ps, entity);
        ps.executeUpdate();
        ps.close();
    }
    
    public void delete(T entity) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE codigo = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, getCodigo(entity));
        ps.executeUpdate();
        ps.close();
    }
}
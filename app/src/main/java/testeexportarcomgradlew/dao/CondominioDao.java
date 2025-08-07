package testeexportarcomgradlew.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import testeexportarcomgradlew.beans.Condominio;

public class CondominioDao extends BaseDao<Condominio> {

    public CondominioDao(Connection cnn) {
        super(cnn);
    }

    @Override
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS condominio (codigo INT PRIMARY KEY, nome VARCHAR(255) NOT NULL)";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public String getTableName() {
        return "condominio";
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO condominio VALUES (?, ?)";
    }

    @Override
    public String getUpdateSql() {
        return "UPDATE condominio SET nome = ? WHERE codigo = ?";
    }

    @Override
    public void setInsertParameters(PreparedStatement ps, Condominio condominio) throws SQLException {
        ps.setInt(1, condominio.getCodigo());
        ps.setString(2, condominio.getNome());
    }

    @Override
    public void setUpdateParameters(PreparedStatement ps, Condominio condominio) throws SQLException {
        ps.setString(1, condominio.getNome());
        ps.setInt(2, condominio.getCodigo());
    }

    @Override
    public Condominio mapResultSet(ResultSet rs) throws SQLException {
        Condominio condominio = new Condominio();
        condominio.setCodigo(rs.getInt("codigo"));
        condominio.setNome(rs.getString("nome"));
        return condominio;
    }

    @Override
    public Integer getCodigo(Condominio condominio) {
        return condominio.getCodigo();
    }
}
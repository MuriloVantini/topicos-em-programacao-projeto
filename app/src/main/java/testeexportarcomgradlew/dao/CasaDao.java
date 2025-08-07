package testeexportarcomgradlew.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import testeexportarcomgradlew.beans.Casa;
import testeexportarcomgradlew.beans.Condominio;

public class CasaDao extends BaseDao<Casa> {

    public CasaDao(Connection cnn) {
        super(cnn);
    }

    @Override
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS casa (" +
                "codigo INT PRIMARY KEY, " +
                "nome VARCHAR(255) NOT NULL, " +
                "condominio_id INT, " +
                "FOREIGN KEY (condominio_id) REFERENCES condominio(codigo))";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public String getTableName() {
        return "casa";
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO casa VALUES (?, ?, ?)";
    }

    @Override
    public String getUpdateSql() {
        return "UPDATE casa SET nome = ?, condominio_id = ? WHERE codigo = ?";
    }

    @Override
    public void setInsertParameters(PreparedStatement ps, Casa casa) throws SQLException {
        ps.setInt(1, casa.getCodigo());
        ps.setString(2, casa.getNome());
        ps.setInt(3, casa.getCondominio().getCodigo());
    }

    @Override
    public void setUpdateParameters(PreparedStatement ps, Casa casa) throws SQLException {
        ps.setString(1, casa.getNome());
        ps.setInt(2, casa.getCondominio().getCodigo());
        ps.setInt(3, casa.getCodigo());
    }

    @Override
    public Casa mapResultSet(ResultSet rs) throws SQLException {
        Casa casa = new Casa();
        casa.setCodigo(rs.getInt("codigo"));
        casa.setNome(rs.getString("nome"));
        
        Condominio condominio = new Condominio();
        condominio.setCodigo(rs.getInt("condominio_id"));
        casa.setCondominio(condominio);
        
        return casa;
    }

    @Override
    public Integer getCodigo(Casa casa) {
        return casa.getCodigo();
    }

    public List<Casa> getCasasByCondominioId(Integer condominioId) throws SQLException {
        List<Casa> casas = new ArrayList<>();
        String sql = "SELECT * FROM casa WHERE condominio_id = ? ORDER BY codigo";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, condominioId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            casas.add(mapResultSet(rs));
        }

        rs.close();
        ps.close();
        return casas;
    }
}
package testeexportarcomgradlew.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import testeexportarcomgradlew.beans.Casa;
import testeexportarcomgradlew.beans.Condominio;
import testeexportarcomgradlew.beans.Morador;

public class MoradorDao extends BaseDao<Morador> {

    public MoradorDao(Connection cnn) {
        super(cnn);
    }

    @Override
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS morador (" +
                "codigo INT PRIMARY KEY, " +
                "nome VARCHAR(255) NOT NULL, " +
                "casa_id INT, " +
                "condominio_id INT, " +
                "FOREIGN KEY (casa_id) REFERENCES casa(codigo), " +
                "FOREIGN KEY (condominio_id) REFERENCES condominio(codigo))";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public String getTableName() {
        return "morador";
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO morador VALUES (?, ?, ?, ?)";
    }

    @Override
    public String getUpdateSql() {
        return "UPDATE morador SET nome = ?, casa_id = ?, condominio_id = ? WHERE codigo = ?";
    }

    @Override
    public void setInsertParameters(PreparedStatement ps, Morador morador) throws SQLException {
        ps.setInt(1, morador.getCodigo());
        ps.setString(2, morador.getNome());
        ps.setInt(3, morador.getCasa().getCodigo());
        ps.setInt(4, morador.getCondominio().getCodigo());
    }

    @Override
    public void setUpdateParameters(PreparedStatement ps, Morador morador) throws SQLException {
        ps.setString(1, morador.getNome());
        ps.setInt(2, morador.getCasa().getCodigo());
        ps.setInt(3, morador.getCondominio().getCodigo());
        ps.setInt(4, morador.getCodigo());
    }

    @Override
    public Morador mapResultSet(ResultSet rs) throws SQLException {
        Morador morador = new Morador();
        morador.setCodigo(rs.getInt("codigo"));
        morador.setNome(rs.getString("nome"));
        
        Casa casa = new Casa();
        casa.setCodigo(rs.getInt("casa_id"));
        morador.setCasa(casa);
        
        Condominio condominio = new Condominio();
        condominio.setCodigo(rs.getInt("condominio_id"));
        morador.setCondominio(condominio);
        
        return morador;
    }

    @Override
    public Integer getCodigo(Morador morador) {
        return morador.getCodigo();
    }

    public List<Morador> getMoradoresByCondominioId(Integer condominioId) throws SQLException {
        List<Morador> moradores = new ArrayList<>();
        String sql = "SELECT * FROM morador WHERE condominio_id = ? ORDER BY codigo";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, condominioId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            moradores.add(mapResultSet(rs));
        }

        rs.close();
        ps.close();
        return moradores;
    }

    public List<Morador> getMoradoresByCasaId(Integer casaId) throws SQLException {
        List<Morador> moradores = new ArrayList<>();
        String sql = "SELECT * FROM morador WHERE casa_id = ? ORDER BY codigo";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, casaId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            moradores.add(mapResultSet(rs));
        }

        rs.close();
        ps.close();
        return moradores;
    }
}
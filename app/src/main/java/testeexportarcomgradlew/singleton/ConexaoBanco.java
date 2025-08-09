package testeexportarcomgradlew.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class ConexaoBanco {
    
    //apenas a própria classe pode construir si mesma
    private static Connection cnn;
    private static String jdbcUrl;

    //não poderão dar new ComponenteX fora daqui
    private ConexaoBanco() {

    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (cnn == null) {
            String driver = "org.h2.Driver";
            // Usar caminho relativo válido tanto localmente quanto no Docker
            // O diretório será /app/ferramentas dentro do container (mapeado pelo docker-compose)
            File dir = new File("./ferramentas");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String url = "jdbc:h2:file:./ferramentas/banco;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE";
            jdbcUrl = url;
            String user = "sa";
            String password = "";
            Class.forName(driver);
            cnn = DriverManager.getConnection(url, user, password);
        }
        return cnn;
    }

    public static String getJdbcUrl() {
        return jdbcUrl;
    }
}

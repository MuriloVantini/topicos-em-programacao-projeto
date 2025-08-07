package testeexportarcomgradlew.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    
    //apenas a própria classe pode construir si mesma
    private static Connection cnn;

    //não poderão dar new ComponenteX fora daqui
    private ConexaoBanco() {

    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (cnn == null) {
            String driver = "org.h2.Driver";
            String url = "jdbc:h2:file:C:\\Users\\Murilo Carazato\\Documents\\Spring Projects\\testeExportarComGradlew\\ferramentas\\banco";
            String user = "sa";
            String password = "";
            Class.forName(driver);
            cnn = DriverManager.getConnection(url, user, password);
        }
        return cnn;
    }
}

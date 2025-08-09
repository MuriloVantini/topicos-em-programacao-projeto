package testeexportarcomgradlew;

import java.sql.Connection;
import java.sql.SQLException;

import testeexportarcomgradlew.service.CasaService;
import testeexportarcomgradlew.service.CondominioService;
import testeexportarcomgradlew.service.MoradorService;
import testeexportarcomgradlew.service.RelacionamentoService;
import testeexportarcomgradlew.singleton.ConexaoBanco;

/*
 * o pom.xml é pra quando é construido em maven; no meu caso é o app\build.gradle
 * para baixar os pacotes= ./gradlew build
 * 
 * para rodar a interface do h2= java -jar ferramentas\h2-2.3.232.jar
 * eu coloquei a url jdbc= jdbc:h2:file:C:/Users/Murilo Carazato/Documents/Spring Projects/testeExportarComGradlew/ferramentas/banco
 * 
 * para rodar: ./gradlew run
 */
public class App {

	public static void main(String[] args) {
		Connection connection = null;
		try {
			connection = ConexaoBanco.getConnection();

			// Log informativo do banco H2 utilizado
			System.out.println("[INFO] H2 JDBC URL: " + ConexaoBanco.getJdbcUrl());

			CondominioService condominioService = new CondominioService(connection);
			CasaService casaService = new CasaService(connection);
			MoradorService moradorService = new MoradorService(connection);
			RelacionamentoService relacionamentoService = new RelacionamentoService(connection);

			// condominioService.executarTestes();
			// casaService.executarTestes();
			// moradorService.executarTestes();

			relacionamentoService.executarTestesDeRelacionamento();

			connection.close();

		} catch (ClassNotFoundException e) {
			System.err.println("Driver do banco não encontrado: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Erro de conexão com banco: " + e.getMessage());
		}

	}
}

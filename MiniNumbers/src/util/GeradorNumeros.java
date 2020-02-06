package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 *  Classe com metodo responsavel pela geracao dos numeros e persistencia do mesmo no banco.
 */
public class GeradorNumeros {
	
	private static Logger LOGGER = LogManager.getLogger("Alimentação do banco de dados");

	/*
	 * Gera a sequencia de numeros.
	 */
	public void geraNumeros() throws IOException, SQLException, ClassNotFoundException {

		StringBuilder sequencia;
	
		Connection con = ConnectionFactory.getInstance();
		con.setAutoCommit(false);
		Statement stmt = con.createStatement();
		LOGGER.info("Início do processo de preparação do banco de dados");
		int a, b, c;
		a = 0;
		b = a;
		c = b;
		int aux = 0;
		long list = 0;
		for (a = 1; a < 87; a++) {
			for (b = 2; b < 88; b++) {
				for (c = 3; c < 89; c++) {
					if (aux == 1000) {
						// Hora de persistir no banco.
						stmt.executeBatch();
						con.commit();
						stmt.clearBatch();
						aux = 0;
					}
					sequencia = new StringBuilder();
					sequencia.append("INSERT INTO SEQUENCIAS (SEQUENCIA) VALUES ('").append(String.valueOf(a))
							.append(",").append(String.valueOf(b)).append(",").append(String.valueOf(c)).append("')");
					stmt.addBatch(sequencia.toString());
					aux++;
					list++;
				}
			}
		}
		stmt.executeBatch();
		con.commit();
		stmt.clearBatch();
		con.close();
		LOGGER.info("Término do processo de preparação do banco de dados");
		LOGGER.info("Número de linhas inseridas: "+list);
	}

}

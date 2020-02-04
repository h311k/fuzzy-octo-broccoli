package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 *  Classe com metodo responsavel pela geracao dos numeros e persistencia do mesmo no banco.
 */
public class GeradorNumeros {

	/*
	 * Gera a sequencia de numeros.
	 */
	public void geraNumeros() throws IOException, SQLException, ClassNotFoundException {

		StringBuilder sequencia;

		Connection con = DriverManager
				.getConnection("jdbc:sqlserver://localhost:1433;databaseName=numbers;user=numbers;password=19650917");
		con.setAutoCommit(false);
		Statement stmt = con.createStatement();

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
		System.out.println("Número de linhas!!!!! " + list);
	}

}

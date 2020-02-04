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

		int a, b, c, d, e, f, g, h, i, j, l, m, n, o, p;
		int aux = 0;
		int list = aux;
		for (a = 1; a < 87; a++) {
			for (b = 2; b < 88; b++) {
				for (c = 3; c < 89; c++) {
					for (d = 4; d < 90; d++) {
						for (e = 5; e < 91; e++) {
							for (f = 6; f < 92; f++) {
								for (g = 7; g < 93; g++) {
									for (h = 8; h < 94; h++) {
										for (i = 9; i < 95; i++) {
											for (j = 10; j < 96; j++) {
												for (l = 11; l < 97; l++) {
													for (m = 12; m < 98; m++) {
														for (n = 13; n < 99; n++) {
															for (o = 14; o < 100; o++) {
																for (p = 15; p < 101; p++) {
																	if (aux == 1000) {
																		// Hora de persistir no banco.
																		stmt.executeBatch();
																		con.commit();
																		stmt.clearBatch();
																		aux = 0;																		
																	}
																	sequencia = new StringBuilder();
																	sequencia.append(
																			"INSERT INTO SEQUENCIAS (SEQUENCIA) VALUES ('")
																			.append(String.valueOf(a)).append(",")
																			.append(String.valueOf(b)).append(",")
																			.append(String.valueOf(c)).append(",")
																			.append(String.valueOf(d)).append(",")
																			.append(String.valueOf(e)).append(",")
																			.append(String.valueOf(f)).append(",")
																			.append(String.valueOf(g)).append(",")
																			.append(String.valueOf(h)).append(",")
																			.append(String.valueOf(i)).append(",")
																			.append(String.valueOf(j)).append(",")
																			.append(String.valueOf(l)).append(",")
																			.append(String.valueOf(m)).append(",")
																			.append(String.valueOf(n)).append(",")
																			.append(String.valueOf(o)).append(",")
																			.append(String.valueOf(p)).append("')");

																	stmt.addBatch(sequencia.toString());
																	aux++;
																	list++;
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		stmt.executeBatch();
		con.commit();
		stmt.clearBatch();
		con.close();
		System.out.println("Número de linhas!!!!! "+list);
	}

}

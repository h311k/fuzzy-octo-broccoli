package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.paukov.combinatorics3.Generator;

public class GeradorCombinacoes {
	
	int i=0;
	StringBuilder sequencia = null;
	
	private static Logger LOGGER = LogManager.getLogger("Processo de exclusão");

	public void geraCombinacoes() throws IOException {
		
		LOGGER.info("Início do processo de exclusão");
		LOGGER.info("Lendo a sequência de 50 números");
		String[] numeros = Propriedades.getProp().getProperty("prop.numbers.sequencia").split(",");
		try {
			Connection con = ConnectionFactory.getInstance();
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
			Generator.combination(numeros)
			.simple(15)
			.stream().forEach(item -> {
				try {
					if(i==1000) {
						stmt.executeBatch();
						con.commit();
						stmt.clearBatch();
						
					}
					sequencia = new StringBuilder();
					sequencia.append("DELETE FROM SEQUENCIAS WHERE SEQUENCIA='").append(String.join(",", item)).append("'");
					stmt.addBatch(sequencia.toString());
					i++;
				} catch(SQLException e) {
					System.out.println(e);
				}
				
			});
			stmt.executeBatch();
			con.commit();
			stmt.clearBatch();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM sequencias");
			rs.next();
			LOGGER.info("Total de linhas no banco após a exclusão: "+rs.getLong(1));
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}

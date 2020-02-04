package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import org.paukov.combinatorics3.Generator;

public class GeradorCombinacoes {
	
	int i=0;
	StringBuilder sequencia = null;

	public void geraCombinacoes(String valores) {
		String inicioAtividade = String.valueOf(LocalDateTime.now());
		System.out.println("Início da atividade: "
				+ inicioAtividade);
		String[] numeros = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" }; // aqui pode ser qualquer objeto que implemente Comparable
		
		try {
			Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=numbers;user=numbers;password=19650917");
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
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}

package main;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import util.GeradorCombinacoes;
import util.GeradorNumeros;

public class Numbers {
	
	static String TERMINO_ATIVIDADE = "";
	
	static String TERMINO_EXCLUSAO = "";

	private static Logger LOGGER = LogManager.getLogger("Execução da atividade");
	
	public static void main(String[] args) throws IOException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		String inicioAtividade = LocalDateTime.now().format(formatter);
		
		LOGGER.info("Início da atividade");
		
		Thread t = new Thread(() -> {
			try {				
				GeradorNumeros gn = new GeradorNumeros();
				gn.geraNumeros();
			} catch (IOException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		});
		t.start();

		new Thread(() -> {
			int i = 0;
			while (t.isAlive()) {
				switch (i) {
				case 0:
					System.out.print("\033[2J");
					System.out.println("Início do processo de geração do banco: "+inicioAtividade);
					System.out.print("Executando");
					break;
				case 1:
					System.out.print("\033[2J");
					System.out.println("Início do processo de geração do banco: "+inicioAtividade);
					System.out.print("Executando.");
					break;
				case 2:
					System.out.print("\033[2J");
					System.out.println("Início do processo de geração do banco: "+inicioAtividade);
					System.out.print("Executando..");
					break;

				case 3:
					System.out.print("\033[2J");
					System.out.println("Início do processo de geração do banco: "+inicioAtividade);
					System.out.print("Executando...");
					break;
				case 4:
					i=0;
					break;
				}
				i++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			TERMINO_ATIVIDADE = LocalDateTime.now().format(formatter);
			System.out.print("\033[2J");
			System.out.println("Término da criação do banco: "+TERMINO_ATIVIDADE);
			String inicioExclusao = LocalDateTime.now().format(formatter);
			System.out.println("Banco de dados criado! inciciando processo de exclusão.");
			Thread exlcusao = new Thread(() -> {
				GeradorCombinacoes gc = new GeradorCombinacoes();
				try {
					gc.geraCombinacoes(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			exlcusao.start();
			new Thread(()->{
				int j = 0;
				while (exlcusao.isAlive()) {
					switch (j) {
					case 0:
						System.out.print("\033[2J");
						System.out.println("Início do processo de geração do banco: "+inicioAtividade);
						System.out.println("Início do processo de exclusão: "+inicioExclusao);
						System.out.print("Executando");
						break;
					case 1:
						System.out.print("\033[2J");
						System.out.println("Início do processo de geração do banco: "+inicioAtividade);
						System.out.println("Início do processo de exclusão: "+inicioExclusao);
						System.out.print("Executando.");
						break;
					case 2:
						System.out.print("\033[2J");
						System.out.println("Início do processo de geração do banco: "+inicioAtividade);
						System.out.println("Início do processo de exclusão: "+inicioExclusao);
						System.out.print("Executando..");
						break;

					case 3:
						System.out.print("\033[2J");
						System.out.println("Início do processo de geração do banco: "+inicioAtividade);
						System.out.println("Início do processo de exclusão: "+inicioExclusao);
						System.out.print("Executando...");
						break;
					case 4:
						j=0;
						break;
					}
					j++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				LOGGER.info("Conclusão da atividade");
				System.out.print("\033[2J");
				System.out.println("Início do processo de geração do banco: "+inicioAtividade);
				System.out.println("Término do processo de geração do banco: "+TERMINO_ATIVIDADE);
				System.out.println("Início do processo de exclusão: "+inicioExclusao);
				System.out.println("Término do processo de exclusão: "+LocalDateTime.now().format(formatter));
				System.out.println("Processo concluído com sucesso");
				System.exit(0);
			}).start(); 
		}).start();
	}

}

package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private static Connection INSTANCIA;
	
	private ConnectionFactory() {
		
	}
	
	public static synchronized Connection getInstance() throws SQLException {
		if(INSTANCIA==null||INSTANCIA.isClosed()) {
			try {
				String host=Propriedades.getProp().getProperty("prop.database.host");
				String port=Propriedades.getProp().getProperty("prop.database.port");
				String name = Propriedades.getProp().getProperty("prop.database.name");
				String user=Propriedades.getProp().getProperty("prop.database.user");
				String password=Propriedades.getProp().getProperty("prop.database.password");
				INSTANCIA=DriverManager
						.getConnection("jdbc:sqlserver://"+host+":"+port+";databaseName="+name+";user="+user+";password="+password);
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}	
		}
		return INSTANCIA;
	}

}

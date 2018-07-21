package demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

@RestController
public class DemoController {

    private final String url = "jdbc:postgresql://k8s-demo-db.c7b9juowbyq0.eu-west-1.rds.amazonaws.com:5432/demo";
    private final String user = "mpay";
    private final String password = "MPaymentsDB";

    @RequestMapping("/")
    public String index() {
	String str = "k8s-demo app is up and running!<br><br>";
	try {
            DatabaseMetaData dbMeta = connect().getMetaData();
            str = str + "Connected to the PostgreSQL server successfully:<br><b>URL:</b> " + dbMeta.getURL() + "<br><b>User name:</b> " + dbMeta.getUserName() + "<br><b>Product name:</b> " + dbMeta.getDatabaseProductName() + "<br><b>Version:</b> " + dbMeta.getDatabaseProductVersion() + "<br><b>Driver:</b> " + dbMeta.getDriverName();
	} catch (SQLException e) {
            str = str + "No SQL connection: " + e.getMessage();
        }
	return str;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
 
        return conn;
    }
}

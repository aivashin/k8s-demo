package demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
public class DemoController {

    private final String url = "jdbc:postgresql://k8s-demo.c7b9juowbyq0.eu-west-1.rds.amazonaws.com:5432/demo";
    private final String user = "mapy";
    private final String password = "MPaymentDB";

    @RequestMapping("/")
    public String index() {
        Connection conn = connect();
        return "k8s-demo app is up and running!!!!!!!!!!!!!!!!!!!";
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
 
        return conn;
    }
}

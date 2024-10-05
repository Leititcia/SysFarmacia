package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBancoDeDados {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        // "jdbc:mysql://"+prop.getProperty("db.host")+":"+prop.getProperty("db.port")+"/"+prop.getProperty("db.database");
        URL = "jdbc:mysql://127.0.0.1:3306/farmadb";
        USER = "root";
        PASSWORD = "abacate";
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            //System.out.println("Conexão feita!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver JDBC não encontrado.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar!");
        }
        return connection;
    }
}

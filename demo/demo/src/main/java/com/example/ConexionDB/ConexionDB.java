package com.example.ConexionDB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Instancia única de la clase
    private static ConexionDB instancia = null;
    // Objeto de conexión a la base de datos
    private Connection connection = null;
    // Credenciales y URL de la base de datos
    private String url = "jdbc:postgresql://localhost:5432/SebasMarket?useUnicode=true&characterEncoding=UTF-8";
    private String user = "postgres"; // Considera usar mecanismos más seguros para manejar contraseñas
    private String password = "123";

    // Constructor privado para evitar instanciación directa
    private ConexionDB() {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos PostgreSQL!");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos PostgreSQL");
            e.printStackTrace();
        }
    }

    // Método público estático para obtener la instancia
    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    // Método para obtener la conexión
    public Connection getConnection() {
        return this.connection;
    }

    // Añade un método para cerrar la conexión si es necesario
    public void cerrarConexion() {
        if (this.connection != null) {
            try {
                this.connection.close();
                System.out.println("Conexión cerrada con éxito.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

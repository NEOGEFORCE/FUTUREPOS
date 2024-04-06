package com.example.Inventario.InsertarProductoCommand;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.SQLException;

import com.example.Inventario.Command;

public class InsertarProductoCommand implements Command {
    private Connection connection;
    private Scanner scanner;
    private String nombreUsuario;

    public InsertarProductoCommand(Connection connection, Scanner scanner, String nombreUsuario) {
        this.connection = connection;
        this.scanner = scanner;
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public void execute() {
        try {
            // Aquí llamas directamente al método insertarProducto de la clase Productos
            Productos.insertarProducto(connection, scanner, nombreUsuario);
        } catch (SQLException e) {
            // Podrías tener una lógica más sofisticada para manejar diferentes tipos de excepciones de SQL.
            throw new RuntimeException("Error accessing database", e);
        }
    }
}
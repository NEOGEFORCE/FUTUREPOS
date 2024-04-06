package com.example.Inventario.EliminarProductoCommand;

import java.sql.Connection;
import java.util.Scanner;
import com.example.Inventario.Command; // Asegúrate de que este import sea correcto.

// Importa la clase Eliminar si está en otro paquete, si no, no es necesario.
//import com.example.Inventario.Eliminar; 

public class EliminarProductoCommand implements Command {

    private Connection connection;
    private Scanner scanner;
    private String nombreUsuario;

    public EliminarProductoCommand(Connection connection, Scanner scanner, String nombreUsuario) {
        this.connection = connection;
        this.scanner = scanner;
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public void execute() {
        // Asume que EliminarProducto es una clase con un método estático EliminarProducto.
        // Si el método no es estático, necesitarás instanciar la clase EliminarProducto.
        Eliminar.eliminarProducto(connection, scanner);
    }
}
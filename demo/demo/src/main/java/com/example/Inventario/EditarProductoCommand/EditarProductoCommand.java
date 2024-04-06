package com.example.Inventario.EditarProductoCommand;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.example.Inventario.Command;

public class EditarProductoCommand implements Command {
    private Connection connection;
    private Scanner scanner;
    private String nombreUsuario;

    public EditarProductoCommand(Connection connection, Scanner scanner, String nombreUsuario) {
        this.connection = connection;
        this.scanner = scanner;
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public void execute() throws SQLException {
        Editar.EditarProducto(connection, scanner);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}

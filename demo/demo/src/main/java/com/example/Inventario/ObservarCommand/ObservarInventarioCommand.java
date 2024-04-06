package com.example.Inventario.ObservarCommand;

import com.example.Inventario.Command;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ObservarInventarioCommand implements Command {
    private Connection connection;
    private Scanner scanner;
    private String nombreUsuario;

    public ObservarInventarioCommand(Connection connection, Scanner scanner, String nombreUsuario) {
        this.connection = connection;
        this.scanner = scanner;
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public void execute() throws SQLException {
        Observar.observarInventario(connection, scanner, nombreUsuario);
    }
}
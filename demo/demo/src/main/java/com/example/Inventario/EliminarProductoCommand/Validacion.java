package com.example.Inventario.EliminarProductoCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Validacion {

    public static boolean confirmarAccion(Scanner scanner, String pregunta, Connection connection, long codigoBarras) throws SQLException {
        System.out.print(pregunta + " (Sí/No): ");
        String respuesta = scanner.nextLine().trim();
        if (respuesta.equalsIgnoreCase("Sí") || respuesta.equalsIgnoreCase("Si")) {
            return true;
        } else if (respuesta.equalsIgnoreCase("No")) {
            DespuesEliminar.mostrarMenuDespuesEliminar(connection, scanner, String.valueOf(codigoBarras)); // Convertir a String
            return false;
        } else {
            System.out.println("Respuesta no válida. Por favor, responda 'Sí' o 'No'.");
            return confirmarAccion(scanner, pregunta, connection, codigoBarras);
        }
    }

    public static boolean existeProductoPorCodigoBarras(Connection connection, long codigoBarras) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Productos WHERE codigo_barras = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, codigoBarras);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}
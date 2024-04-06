package com.example.Inventario.EliminarProductoCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MostrarProducto {
    public static void mostrarProductos(Connection connection) throws SQLException {
        String sql = "SELECT codigo_barras, nombre FROM Productos ORDER BY nombre ASC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            System.out.println("================================================================================");
            System.out.printf("%-20s | %-50s\n", 
                             "Código de Barras",  "Nombre del Producto"); // Encabezado
            System.out.println("================================================================================");
            while (rs.next()) {
                long codigoBarras = rs.getLong("codigo_barras");
                String nombreProducto = rs.getString("nombre");
                System.out.printf("%-20d | %-50s\n", codigoBarras, nombreProducto); // Alineación de los datos
            }
            System.out.println("================================================================================");
        }
    }
}
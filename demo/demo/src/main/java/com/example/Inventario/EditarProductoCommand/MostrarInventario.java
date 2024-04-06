package com.example.Inventario.EditarProductoCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MostrarInventario {
    public static void MostrarInventario (Connection connection, Scanner scanner, String nombreUsuario) throws SQLException {
        // Ajusta la consulta para incluir el nombre del usuario que registró el producto en lugar del ID
        String sql = "SELECT P.codigo_barras, P.nombre, P.categoria, P.cantidad, P.precio_compra, P.precio_venta, P.proveedor, U.nombre AS nombre_usuario_registrador "
                   + "FROM Productos P "
                   + "JOIN Usuarios U ON P.usuario_registro = U.id_usuario "
                   + "ORDER BY P.nombre ASC;";
                
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

                System.out.println("\nInventario:");
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                // Ajusta el encabezado para incluir el nombre del usuario registrador
                System.out.printf("%-20s | %-47s | %-30s | %-10s | %-15s | %-15s | %-20s | %-30s\n", 
                                  "Código de Barras", "Nombre", "Categoría", "Cantidad", "Precio Compra", "Precio Venta", "Proveedor", "Registrador");
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                
                while (rs.next()) {
                    // Obtiene los valores de la consulta, incluyendo el nombre del usuario registrador
                    String codigoBarras = rs.getString("codigo_barras");
                    String nombreProducto = rs.getString("nombre");
                    String categoria = rs.getString("categoria");
                    int cantidad = rs.getInt("cantidad"); // Usando cantidad en lugar de stock
                    double precioCompra = rs.getDouble("precio_compra");
                    double precioVenta = rs.getDouble("precio_venta");
                    String proveedor = rs.getString("proveedor");
                    String nombreUsuarioRegistrador = rs.getString("nombre_usuario_registrador"); // Obtiene el nombre del usuario registrador
                
                    // Ajusta la impresión para incluir el nombre del usuario registrador
                    System.out.printf("%-20s | %-47s | %-30s | %-10d | $%-14.2f | $%-14.2f | %-20s | %-30s\n", 
                                      codigoBarras, nombreProducto, categoria, cantidad, precioCompra, precioVenta, proveedor, nombreUsuarioRegistrador);
                }
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            }
            

    }
}

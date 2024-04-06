package com.example.Inventario.EliminarProductoCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.example.Inventario.EliminarProductoCommand.MostrarProducto;



public class Eliminar {
    

    public static void eliminarProducto(Connection connection, Scanner scanner) {

        try {
            MostrarProducto.mostrarProductos(connection);
                
            boolean productoEncontrado = false;
            while (!productoEncontrado) {
                System.out.println("Ingrese el código de barras del producto que desea eliminar:");
                String codigoBarras = scanner.nextLine();
                
                if (existeProductoPorCodigoBarras(connection, codigoBarras)) {
                    productoEncontrado = true;
                    if (Validacion.confirmarAccion(scanner, "¿Está seguro de que desea eliminar este producto?", connection, 0)) {
                        eliminarProductoPorCodigoBarras(connection, codigoBarras);
                        System.out.println("Producto eliminado correctamente.");
                        DespuesEliminar.mostrarMenuDespuesEliminar(connection, scanner, codigoBarras);
                        
                    } else {
                        System.out.println("Eliminación cancelada.");
                    }
                } else {
                    System.out.println("No se encontró el producto con el código de barras proporcionado. Intente nuevamente.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Se produjo un error: " + e.getMessage());
        }
    }

    private static boolean existeProductoPorCodigoBarras(Connection connection, String codigoBarras) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Productos WHERE codigo_barras = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, codigoBarras);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true si existe algún producto con ese código de barras
                }
            }
        }
        return false; // Retorna false si no hay productos con ese código de barras
    }

    private static void eliminarProductoPorCodigoBarras(Connection connection, String codigoBarras) throws SQLException {
        String sqlEliminarProducto = "DELETE FROM Productos WHERE codigo_barras = ?";
        try (PreparedStatement pstmtEliminarProducto = connection.prepareStatement(sqlEliminarProducto)) {
            pstmtEliminarProducto.setString(1, codigoBarras);
            pstmtEliminarProducto.executeUpdate();
;
        }
    }
}
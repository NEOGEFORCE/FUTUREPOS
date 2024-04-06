package com.example.Inventario.EditarProductoCommand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
    
public class Nombre {
    public static void editarNombreProducto(Connection connection, Scanner scanner, int idProducto) {
        try {
            System.out.print("Ingrese el nuevo nombre del producto: ");
            String nuevoNombreProducto = scanner.next();
    
            String sql = "UPDATE Productos SET nombre = ? WHERE codigo_barras = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nuevoNombreProducto);
            pstmt.setInt(2, idProducto);
            int rowsAffected = pstmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Nombre del producto actualizado correctamente para el producto con ID " + idProducto);
                DespuesEdicion.menuDespuesEdicion(connection, scanner);
            } else {
                System.out.println("No se pudo actualizar el nombre del producto.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el nombre del producto: " + e.getMessage());
            e.printStackTrace();
        }
    }
    }
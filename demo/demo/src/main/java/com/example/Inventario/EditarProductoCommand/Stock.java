package com.example.Inventario.EditarProductoCommand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Stock {
    public static void editarStock(Connection connection, Scanner scanner, int idProducto) {
    try {
        int nuevoStock = -1;

        // Validación de entrada para asegurarse de que solo se ingresen números mayores o iguales a 0
        while (true) {
            System.out.print("Ingrese el nuevo stock del producto: ");
            String input = scanner.next();
            try {
                nuevoStock = Integer.parseInt(input);
                if (nuevoStock < 0) {
                    throw new NumberFormatException();
                }
                break; // Si se puede convertir a int y es mayor o igual a 0, salir del bucle
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido para el stock.");
            }
        }

        String sql = "UPDATE Productos SET stock = ? WHERE codigo_barras = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, nuevoStock);
        pstmt.setInt(2, idProducto);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Stock del producto actualizado correctamente para el producto con ID " + idProducto);
            DespuesEdicion.menuDespuesEdicion(connection, scanner);
        } else {
            System.out.println("No se pudo actualizar el stock del producto.");
        }
    } catch (SQLException e) {
        System.out.println("Error al actualizar el stock del producto: " + e.getMessage());
        e.printStackTrace();
    }
}
}

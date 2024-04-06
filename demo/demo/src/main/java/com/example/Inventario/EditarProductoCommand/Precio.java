package com.example.Inventario.EditarProductoCommand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Precio {
    public static void editarPrecio(Connection connection, Scanner scanner, int idProducto) {
    try {
        double nuevoPrecio = -1;

        // Validación de entrada para asegurarse de que solo se ingresen números mayores o iguales a 0
        while (true) {
            System.out.print("Ingrese el nuevo precio del producto: ");
            String input = scanner.next();
            try {
                nuevoPrecio = Double.parseDouble(input);
                if (nuevoPrecio < 0) {
                    throw new NumberFormatException();
                }
                break; // Si se puede convertir a double y es mayor o igual a 0, salir del bucle
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido para el precio.");
            }
        }

        String sql = "UPDATE Productos SET precio = ? WHERE codigo_barras = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setDouble(1, nuevoPrecio);
        pstmt.setInt(2, idProducto);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Precio del producto actualizado correctamente para el producto con ID " + idProducto);
            DespuesEdicion.menuDespuesEdicion(connection, scanner);
        } else {
            System.out.println("No se pudo actualizar el precio del producto.");
        }
    } catch (SQLException e) {
        System.out.println("Error al actualizar el precio del producto: " + e.getMessage());
        e.printStackTrace();
    }
}
}


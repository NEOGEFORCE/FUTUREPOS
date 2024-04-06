package com.example.Inventario.InsertarProductoCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {
    public static boolean productoYaExiste(Connection connection, String nombreProducto) {
        String sql = "SELECT COUNT(*) FROM Productos WHERE nombre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombreProducto);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // retorna true si hay al menos un producto con ese nombre
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia del producto: " + e.getMessage());
        }
        return false; // retorna false si no hay productos con ese nombre o hay un error
    }
    
    
    public static boolean validarCaracteres(String input) {
        // Ahora excluimos las letras 'Ñ' y 'ñ' con la expresión regular.
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9- ]+$");
        return pattern.matcher(input).matches();
    }



    public static String obtenerDatoValido(Scanner scanner, String mensaje, String error) {
        String dato;
        do {
            System.out.print(mensaje);
            dato = scanner.nextLine();
            if (!validarCaracteres(dato)) {
                System.out.println(error);
            }
        } while (!validarCaracteres(dato));
        return dato;
    }


    public static boolean validarNombreProducto(String nombreProducto) {
        // Validar que el nombre solo contenga caracteres alfanuméricos y espacios
        // y que no contenga Ñ o ñ.
        Pattern pattern = Pattern.compile("^[A-Za-z0-9 ]+$");
        Matcher matcher = pattern.matcher(nombreProducto);
        return matcher.matches() && !nombreProducto.contains("Ñ") && !nombreProducto.contains("ñ");
    }
    

    
    @SuppressWarnings("unused")
    public static boolean confirmarAccion(Scanner scanner, String pregunta) {
        String respuesta;
        do {
            System.out.print(pregunta + " (Sí/No): ");
            respuesta = scanner.nextLine();
        } while (!respuesta.equalsIgnoreCase("SI") && !respuesta.equalsIgnoreCase("NO"));
        return respuesta.equalsIgnoreCase("SI");
    }

    
    
    public static boolean verificarExistenciaProducto(String nombreProducto, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Productos WHERE nombre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombreProducto);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // true si existe algún producto con ese nombre
                }
            }
        }
        return false;
    }
    public static int siguienteIdDisponible(Connection connection) throws SQLException {
        int siguienteId = 1;
        String sql = "SELECT id_producto FROM Productos ORDER BY id_producto";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int idActual = rs.getInt("id_producto");
            if (idActual != siguienteId) {
                return siguienteId; // Devuelve el siguiente ID disponible encontrado
            }
            siguienteId++;
        }   

        // Si todos los IDs están ocupados secuencialmente, devuelve el próximo ID después del máximo actual
        return siguienteId;
    }

    public static int obtenerIdUsuario(Connection connection, String nombreUsuario) throws SQLException {
        String sql = "SELECT id_usuario FROM Usuarios WHERE nombre = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, nombreUsuario);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id_usuario");
        } else {
            throw new SQLException("No se encontró el usuario con el nombre proporcionado.");
        }
    }
    

    public static boolean codigoBarrasYaExiste(Connection connection, String codigoBarras) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Productos WHERE codigo_barras = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, codigoBarras);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si hay al menos un producto con ese código de barras
            }
        }
        return false; // Retorna false si no hay productos con ese código de barras o hay un error
    }

    public static boolean validarCodigoBarras(String codigoBarras, Connection connection) throws SQLException {
        // Verifica que el código de barras no esté vacío y que contenga solo números
        if (codigoBarras.trim().isEmpty() || !codigoBarras.matches("\\d+")) {
            System.out.println("El código de barras puede solo contener dígitos numéricos.");
            return false;
        }
        
        // Verifica la longitud del código de barras
        if (codigoBarras.length() < 8 || codigoBarras.length() > 13) {
            System.out.println("El código de barras debe tener entre 8 y 13 dígitos.");
            return false;
        }
        
        // Verifica que el código de barras sea único en la base de datos
        if (codigoBarrasYaExiste(connection, codigoBarras)) {
            System.out.println("Este código de barras ya existe. Ingrese un código diferente.");
            return false;
        }

        // Si todas las validaciones son correctas
        return true;
    }

    
}

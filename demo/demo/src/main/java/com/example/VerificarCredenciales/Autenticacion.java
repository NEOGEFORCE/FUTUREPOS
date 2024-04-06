package com.example.VerificarCredenciales;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;


public class Autenticacion {
    public static boolean verificarCredenciales(Connection connection, String nombreUsuario, String contraseña) throws SQLException {
        String sql = "SELECT contraseña_hash FROM Usuarios WHERE nombre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String contraseñaHash = rs.getString("contraseña_hash");
                return BCrypt.checkpw(contraseña, contraseñaHash);
            }
        }
        return false;
    }
}
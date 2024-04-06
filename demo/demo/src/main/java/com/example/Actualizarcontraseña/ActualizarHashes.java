package com.example.Actualizarcontraseña;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class ActualizarHashes {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/SebasMarket";
        String usuarioDB = "postgres";
        String contraseñaDB = "123";

        try (Connection connection = DriverManager.getConnection(url, usuarioDB, contraseñaDB)) {
            // Desactiva auto-commit para manejar transacciones manualmente
            connection.setAutoCommit(false);

            try (PreparedStatement selectStmt = connection.prepareStatement("SELECT id_usuario, contraseña FROM Usuarios WHERE contraseña_hash IS NULL");
                 ResultSet resultSet = selectStmt.executeQuery()) {

                while (resultSet.next()) {
                    String contraseñaPlana = resultSet.getString("contraseña");
                    String hash = BCrypt.hashpw(contraseñaPlana, BCrypt.gensalt());
                    int id = resultSet.getInt("id_usuario");

                    // Prepara la consulta de actualización
                    try (PreparedStatement updateStmt = connection.prepareStatement("UPDATE Usuarios SET contraseña_hash = ? WHERE id_usuario = ?")) {
                        updateStmt.setString(1, hash);
                        updateStmt.setInt(2, id);
                        updateStmt.executeUpdate();
                    }
                }
                // Si todo fue exitoso, realiza commit de las transacciones
                connection.commit();
            } catch (SQLException e) {
                // En caso de error, revierte las transacciones
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.example.Inventario.EditarProductoCommand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validaciones {
    public static boolean existenciaProducto(Connection connection, int idProducto) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM Productos WHERE codigo_barras = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, idProducto);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt("count");
        return count == 1;
    }
    }
    
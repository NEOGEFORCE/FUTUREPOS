package com.example.Inventario;

import java.sql.SQLException;

public interface Command {
    void execute() throws SQLException;
}
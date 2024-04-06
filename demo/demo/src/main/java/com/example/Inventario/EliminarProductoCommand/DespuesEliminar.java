package com.example.Inventario.EliminarProductoCommand;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.example.Inventario.MenuPrincipal;

public class DespuesEliminar {
    public static void mostrarMenuDespuesEliminar(Connection connection, Scanner scanner, String nombreUsuario) throws SQLException {
        boolean opcionValida = false;
        while (!opcionValida) {
            System.out.println("¿Qué deseas hacer?");
            System.out.println("1. Eliminar otro producto");
            System.out.println("2. Volver al Menú Principal");
            System.out.println("3. Salir del Sistema");
            System.out.print("Por favor, elige una opción: ");
            String opcion = scanner.next();
            switch (opcion) {
                case "1":
                    Eliminar.eliminarProducto(connection, scanner);
                    opcionValida = true;
                    break;
                case "2":
                    new MenuPrincipal(connection, scanner, nombreUsuario).mostrarMenuPrincipal();
                    opcionValida = true;
                    break;
                case "3":
                    System.out.println("Saliendo del sistema...");
                    System.exit(0); 
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                    break;
            }
        }
    }
}

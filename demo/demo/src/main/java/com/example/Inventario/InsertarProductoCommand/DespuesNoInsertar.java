package com.example.Inventario.InsertarProductoCommand;
import com.example.Inventario.MenuPrincipal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DespuesNoInsertar {
    
    public static void mostrarMenuDespuesNoInsertarProducto(Connection connection, Scanner scanner, String nombreUsuario) throws SQLException {
        boolean opcionValida = false;
        while (!opcionValida) {
            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1. Volver al Menú Principal");
            System.out.println("2. Salir del Sistema");
            System.out.print("Por favor, elige una opción: ");
            
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    new MenuPrincipal(connection, scanner, nombreUsuario).mostrarMenuPrincipal();
                    opcionValida = true;
                    break;
                case "2":
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
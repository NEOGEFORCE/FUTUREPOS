package com.example.Inventario.EditarProductoCommand;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.example.Inventario.RealizarVentasCommand.Ventas;

public class DespuesEdicion {
    public static void menuDespuesEdicion(Connection connection, Scanner scanner) throws SQLException {
        int opcionDespuesEdicion;
        do {
            System.out.println("\n¿Qué deseas hacer después de editar?");
            System.out.println("1. Seguir editando");
            System.out.println("2. Volver al Menú Principal");
            System.out.println("3. Salir del Sistema");
            System.out.print("Por favor, elige una opción: ");
    
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido.");
                System.out.print("Por favor, elige una opción: ");
                scanner.next(); // Limpiar el buffer de entrada
            }
            opcionDespuesEdicion = scanner.nextInt();
            if (opcionDespuesEdicion < 1 || opcionDespuesEdicion > 3) {
                System.out.println("Opción no válida. Por favor, elija una opción del 1 al 3.");
            }
        } while (opcionDespuesEdicion < 1 || opcionDespuesEdicion > 3);
    
        switch (opcionDespuesEdicion) {
            case 1:
                Editar.EditarProducto(connection, scanner); // Seguir editando
                break;
            case 2:
            MostrarInventario.MostrarInventario(connection, scanner, null); // Volver al Menú Principal
                break;
            case 3:
                System.out.println("Saliendo del sistema..."); // Salir del Sistema
                System.exit(0);
                break;
        }
    }
    }
    
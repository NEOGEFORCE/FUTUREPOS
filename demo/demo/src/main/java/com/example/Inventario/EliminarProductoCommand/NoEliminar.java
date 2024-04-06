package com.example.Inventario.EliminarProductoCommand;
import java.util.Scanner;

public class NoEliminar {
    
    public static void mostrarMenuDespuesNoSeguro(Scanner scanner) {
        boolean opcionValida = false;
        while (!opcionValida) {
            System.out.println("¿Qué deseas hacer?");
            System.out.println("1. Volver al Menú Principal");
            System.out.println("2. Salir del Sistema");
            System.out.print("Por favor, elige una opción: ");
            String opcion = scanner.next();

            switch (opcion) {
                case "1":
                    // Volver al menú principal (código aquí)
                    opcionValida = true;
                    break;
                case "2":
                    System.out.println("Saliendo del sistema...");
                    System.exit(0); // Salir del sistema
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                    break;
            }
        }
    }
}

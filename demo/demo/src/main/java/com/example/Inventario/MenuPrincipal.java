package com.example.Inventario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import com.example.Inventario.ObservarCommand.ObservarInventarioCommand;
import com.example.Inventario.EditarProductoCommand.EditarProductoCommand;
import com.example.Inventario.EliminarProductoCommand.EliminarProductoCommand;
import com.example.Inventario.InsertarProductoCommand.InsertarProductoCommand;
public class MenuPrincipal {
    private Map<Integer, Command> commands = new HashMap<>();
    private Scanner scanner;
    private String nombreUsuario;

    public MenuPrincipal(Connection connection, Scanner scanner, String nombreUsuario) throws SQLException {
        if (connection == null || scanner == null) {
            throw new IllegalArgumentException("La conexión y el scanner no deben ser nulos.");
        }
        this.scanner = scanner;
        this.nombreUsuario = nombreUsuario;
        commands.put(1, new ObservarInventarioCommand(connection, scanner, nombreUsuario));
        commands.put(2, new InsertarProductoCommand(connection, scanner, nombreUsuario));
        commands.put(3, new EliminarProductoCommand(connection, scanner, nombreUsuario));
        commands.put(4, new EditarProductoCommand(connection, scanner, nombreUsuario));

        int opcion;
        do {
            System.out.println("\nMenú Principal");
            System.out.println("1. Observar inventario");
            System.out.println("2. Insertar producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Editar Producto");
            System.out.println("5. Realizar Venta");
            System.out.println("6. Gestionar  Usuarios");
            System.out.println("7. Salir del sistema");
            System.out.print("Elige una opción: ");
            opcion = leerOpcionValida();

            if (opcion >= 1 && opcion <= 6) {
                Command command = commands.get(opcion);
                if (command != null) {
                    command.execute();
                }
            } else if (opcion == 7) {
                System.out.println("Saliendo del sistema...");
            } else {
                System.out.println("Opción no válida.");
            }
        } while (opcion != 7);
    }

    private int leerOpcionValida() {
        int opcion = -1;
        while (opcion == -1) {
            String input = scanner.nextLine();
            try {
                // Verificar que el input no tenga ceros al principio si tiene más de un caracter
                if (input.startsWith("0") && input.length() > 1) {
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                    continue; // Salta a la siguiente iteración del bucle
                }
                opcion = Integer.parseInt(input);
                // Verificar que la opción esté dentro del rango permitido
                if (opcion < 1 || opcion > 7) {
                    System.out.println("Opción no válida, intenta nuevamente.");
                    opcion = -1; // Reiniciar la opción ya que no es válida
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida, ingresa un número.");
            }
        }
        return opcion;
    }

   

    public void mostrarMenuPrincipal() {
        throw new UnsupportedOperationException("Unimplemented method 'mostrarMenuPrincipal'");
    }
}
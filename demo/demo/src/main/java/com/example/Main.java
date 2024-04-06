package com.example;
import com.example.ConexionDB.ConexionDB;
import com.example.VerificarCredenciales.Autenticacion;
import com.example.Inventario.MenuPrincipal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in);
             Connection connection = ConexionDB.getInstancia().getConnection()) {

            System.out.println("Bienvenido a Future POS");

            boolean esValido = false;
            for (int intentos = 0; intentos < 3 && !esValido; intentos++) { // Permite hasta 3 intentos de inicio de sesión.
                String nombreUsuario;
                do {
                    System.out.print("Ingrese nombre de usuario: ");
                    nombreUsuario = scanner.nextLine();

                    if (!validarNombreUsuario(nombreUsuario)) {
                        System.out.println("El nombre de usuario no puede contener el caracter 'Ñ' 'ñ'. Por favor, intente de nuevo.");
                        nombreUsuario = null; // Reiniciar nombreUsuario para repetir el ciclo
                    }
                } while (nombreUsuario == null);

                System.out.print("Ingrese contraseña: ");
                String contraseña = scanner.nextLine();

                esValido = Autenticacion.verificarCredenciales(connection, nombreUsuario, contraseña);

                if (esValido) {
                    System.out.println("Usuario autenticado con éxito.");
                    MenuPrincipal menu = new MenuPrincipal(connection, scanner, nombreUsuario); // Asegúrate de que esta línea está pasando el objeto 'scanner' correctamente.
                    menu.mostrarMenuPrincipal();
                } else if (intentos < 2) { // Si aún quedan intentos, informa al usuario.
                    System.out.println("Las credenciales no son válidas. Por favor, intente de nuevo.");
                }
            }

            if (!esValido) { // Si después de 3 intentos no se autentica, cierra la aplicación.
                System.out.println("Se han agotado los intentos de inicio de sesión. La aplicación se cerrará.");
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }

    private static boolean validarNombreUsuario(String nombreUsuario) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9- ]+$");
        return pattern.matcher(nombreUsuario).matches();
    }
}
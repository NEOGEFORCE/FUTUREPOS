package com.example.Inventario.EditarProductoCommand;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Editar {
   
    public static void EditarProducto(Connection connection, Scanner scanner) throws SQLException {
        
        String nombreUsuario = scanner.nextLine();
        MostrarInventario.MostrarInventario(connection, scanner, nombreUsuario);

        try {
            System.out.println("\nEditar Producto:");

            int idProducto;
            boolean idValido = false;
            do {
                // Solicitar ID del producto a editar
                System.out.print("Ingrese el codigo de barras del producto que desea editar: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Por favor, ingrese un número válido para el ID del producto.");
                    System.out.print("Ingrese el ID del producto que desea editar: ");
                    scanner.next(); // Limpiar el buffer de entrada
                }
                idProducto = scanner.nextInt();

                // Verificar si el producto existe
                if (!Validaciones.existenciaProducto(connection, idProducto)) {
                    System.out.println("El producto con ID " + idProducto + " no existe.");
                } else {
                    idValido = true;
                }
            } while (!idValido);

            // Limpiar el buffer de entrada
            scanner.nextLine();

            // Menú para seleccionar qué campo editar
            int opcionCampo;
            do {
                System.out.println("\nSeleccione qué campo desea editar:");
                System.out.println("1. Código de Barras");
                System.out.println("2. Nombre del Producto");
                System.out.println("3. Precio");
                System.out.println("4. Stock");
                System.out.print("Ingrese el número de la opción: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Por favor, ingrese un número válido.");
                    System.out.print("Ingrese el número de la opción: ");
                    scanner.next(); // Limpiar el buffer de entrada
                }
                opcionCampo = scanner.nextInt();
                if (opcionCampo < 1 || opcionCampo > 4) {
                    System.out.println("Opción no válida. Por favor, elija una opción del 1 al 4.");
                }
            } while (opcionCampo < 1 || opcionCampo > 4);

            // Limpiar el buffer de entrada
            scanner.nextLine();

            // Desplegar el nuevo menú para eliminar o cambiar información
            int opcionEliminarCambiar;
            do {
                System.out.println("\n¿Desea eliminar o cambiar la información?");
                System.out.println("1. Eliminar información.");
                System.out.println("2. Cambiar información.");
                System.out.print("Por favor, elija una opción: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Por favor, ingrese un número válido.");
                    System.out.print("Por favor, elija una opción: ");
                    scanner.next(); // Limpiar el buffer de entrada
                }
                opcionEliminarCambiar = scanner.nextInt();
                if (opcionEliminarCambiar != 1 && opcionEliminarCambiar != 2) {
                    System.out.println("Opción no válida. Por favor, elija 1 para eliminar o 2 para cambiar la información.");
                }
            } while (opcionEliminarCambiar != 1 && opcionEliminarCambiar != 2);

            switch (opcionCampo) {
                case 1:
                    if (opcionEliminarCambiar == 2) {
                        // Código de barras
                        String nuevoCodigoBarras = "";
                        while (true) {
                            System.out.print("Ingrese el nuevo código de barras: ");
                            nuevoCodigoBarras = scanner.next();
                            if (!nuevoCodigoBarras.matches("\\d+")) {
                                System.out.println("Código de barras no válido. Por favor, ingreselo nuevamente.");
                            } else {
                                // Verificar si el código de barras ya existe
                                PreparedStatement ps = connection.prepareStatement("SELECT * FROM productos WHERE codigo_barras = ?");
                                ps.setString(1, nuevoCodigoBarras);
                                ResultSet rs = ps.executeQuery();
                                if (rs.next()) {
                                    System.out.println("El código de barras ya existe. Por favor, ingrese un código de barras diferente.");
                                } else {
                                    break;
                                }
                            }
                        }

                        String sql = "UPDATE Productos SET codigo_barras = ? WHERE codigo_barras = ?";
                        PreparedStatement pstmt = connection.prepareStatement(sql);
                        pstmt.setString(1, nuevoCodigoBarras);
                        pstmt.setInt(2, idProducto);
                        int rowsAffected = pstmt.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Código de barras actualizado correctamente para el producto con ID " + idProducto);
                            DespuesEdicion.menuDespuesEdicion(connection, scanner);
                        } else {
                            System.out.println("No se pudo actualizar el código de barras.");
                        }
                    } 
                      
                case 2:
                    if (opcionEliminarCambiar == 2) {
                        Nombre.editarNombreProducto(connection, scanner, idProducto);
                    } 
                    break;
                case 3:
                    if (opcionEliminarCambiar == 2) {
                        Precio.editarPrecio(connection, scanner, idProducto);
                    } 
                    break;
                case 4:
                    if (opcionEliminarCambiar == 2) {
                        Stock.editarStock(connection, scanner, idProducto);
                    } 
                    break;
                default:
                    System.out.println("Opción no válida.");
                    return;
            }

        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            scanner.next(); // Limpiar el buffer de entrada
        } catch (SQLException e) {
            System.out.println("Error al editar el producto: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

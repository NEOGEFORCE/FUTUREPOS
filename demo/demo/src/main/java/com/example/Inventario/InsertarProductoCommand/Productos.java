package com.example.Inventario.InsertarProductoCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Productos {
    private static final int MAX_NOMBRE_LONGITUD = 100; // Longitud máxima del nombre del producto.




public static void insertarProducto(Connection connection, Scanner scanner, String nombreUsuario) throws SQLException {
    while (true) {
        String codigoBarras = obtenerCodigoBarras(scanner, connection);
        String nombreProducto = obtenerNombreProductoUnico(scanner, connection);
        double precioCompra = obtenerPrecioCompra(scanner); // se obtiene el precio de compra
        double precioVenta = obtenerPrecioVenta(scanner, precioCompra); // se calcula el precio de venta con el porcentaje de ganancia
        int cantidad = obtenerCantidadValida(scanner);
        String tipoProducto = obtenerTipoProducto(scanner);
        String nombreProveedor = obtenerNombreProveedorUnico(scanner, connection);

        if (Validaciones.confirmarAccion(scanner, "¿Está seguro de que desea agregar este producto? ")) {
            int idUsuario = Validaciones.obtenerIdUsuario(connection, nombreUsuario);

            // Asegúrate de que el nombre de la tabla y las columnas coincidan con tu esquema de base de datos.
            String sqlProducto = "INSERT INTO Productos (codigo_barras, nombre, precio_compra, precio_venta, cantidad, categoria, proveedor, usuario_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtProducto = connection.prepareStatement(sqlProducto)) {
                pstmtProducto.setString(1, codigoBarras);
                pstmtProducto.setString(2, nombreProducto);
                pstmtProducto.setDouble(3, precioCompra);
                pstmtProducto.setDouble(4, precioVenta);
                pstmtProducto.setInt(5, cantidad);
                pstmtProducto.setString(6, tipoProducto);
                pstmtProducto.setString(7, nombreProveedor);
                pstmtProducto.setInt(8, idUsuario);

                int filasAfectadasProducto = pstmtProducto.executeUpdate();
                if (filasAfectadasProducto > 0) {
                    System.out.println("Producto insertado con éxito.");
                    DespuesInsertar.mostrarOpcionesDespuesInsertarProducto(connection, scanner, nombreUsuario);
                    break;
                } else {
                    System.out.println("No se pudo insertar el producto.");
                }
            }
        } else {
            System.out.println("Inserción cancelada por el usuario.");
            DespuesNoInsertar.mostrarMenuDespuesNoInsertarProducto(connection, scanner, nombreUsuario);
            break;
        }
    }
}

private static String obtenerCodigoBarras(Scanner scanner, Connection connection) throws SQLException {
    String codigoBarras;
    boolean esValido;
    do {
        System.out.print("Ingrese el código de barras del producto: ");
        codigoBarras = scanner.nextLine().trim();
        esValido = Validaciones.validarCodigoBarras(codigoBarras, connection);
        // Si esValido es falso, el bucle se repetirá mostrando los mensajes de error de las validaciones.
    } while (!esValido);
    return codigoBarras;
}

public static String obtenerNombreProductoUnico(Scanner scanner, Connection connection) throws SQLException {
    String nombreProducto;
    do {
        System.out.print("Ingrese Nombre del producto: ");
        nombreProducto = scanner.nextLine().trim();
        // Validación para excluir la letra 'ñ' en cualquier posición y contexto
        if (!nombreProducto.matches("^[a-zA-Z0-9 ]+$")) {
            System.out.println("El nombre del producto no es válido. No se aceptan la letra 'ñ', caracteres especiales o números al inicio o final.");
            continue;
        }
        // Resto de validaciones
        if (nombreProducto.length() > MAX_NOMBRE_LONGITUD) {
            System.out.println("El nombre del producto no puede exceder los " + MAX_NOMBRE_LONGITUD + " caracteres.");
            continue;
        }
        if (Validaciones.productoYaExiste(connection, nombreProducto)) {
            System.out.println("Este producto ya existe. Por favor, ingrese otro nombre.");
            continue;
        }
        break; // Si pasa todas las validaciones, sale del bucle
    } while (true); // Repite hasta que se ingrese un nombre válido
    return nombreProducto;
}


private static double obtenerPrecioCompra(Scanner scanner) {
    double precioCompra;
    do {
        System.out.print("Ingrese el precio de compra del producto: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Error: Por favor ingrese un numero valido, no se permiten caracteres.");
            scanner.next(); // descarta la entrada incorrecta
            System.out.print("Ingrese el precio de compra del producto: ");
        }
        precioCompra = scanner.nextDouble();
        if (precioCompra < 0) {
            System.out.println("El precio de compra no puede ser negativo.");
        }
    } while (precioCompra < 0);
    scanner.nextLine(); // limpia el buffer del scanner
    return precioCompra;
}

private static double obtenerPrecioVenta(Scanner scanner, double precioCompra) {
    double porcentajeGanancia;

    // Solicitar el porcentaje de ganancia
    System.out.print("Ingrese el porcentaje de ganancia para el producto : ");
    porcentajeGanancia = scanner.nextDouble();
    scanner.nextLine(); // Limpiar el buffer del scanner

    // Calcular el precio de venta sin IVA
    double precioVentaSinIVA = precioCompra * (1 + porcentajeGanancia / 100);

    // Preguntar si el precio incluye IVA
    boolean incluyeIVA = incluyeIVAPregunta(scanner);

    double precioFinalConIVA;
    if (incluyeIVA) {
        precioFinalConIVA = precioVentaSinIVA;
        System.out.printf("El precio de venta con un %,.2f%% de ganancia ya incluye IVA y es de $%,.2f.\n", porcentajeGanancia, precioFinalConIVA);
    } else {
        double precioIVA = calcularIVA(scanner, precioVentaSinIVA);
        precioFinalConIVA = precioVentaSinIVA + precioIVA;
    }

    return precioFinalConIVA;
}

private static boolean incluyeIVAPregunta(Scanner scanner) {
    while (true) {
        System.out.print("¿El precio de venta incluye IVA? (Sí/No): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        if (respuesta.equals("sí") || respuesta.equals("si")) {
            return true;
        } else if (respuesta.equals("no")) {
            return false;
        } else {
            System.out.println("Por favor, responde 'Sí' o 'No'.");
        }
    }
}

private static double calcularIVA(Scanner scanner, double precioSinIVA) {
    System.out.print("Ingrese el porcentaje de IVA para el producto: ");
    double porcentajeIVA = scanner.nextDouble();
    scanner.nextLine(); // Limpiar el buffer del scanner

    double precioIVA = precioSinIVA * (porcentajeIVA / 100);
    System.out.printf("El IVA a aplicar sobre $%,.2f es de $%,.2f, resultando en un precio final de $%,.2f.\n",
            precioSinIVA, precioIVA, precioSinIVA + precioIVA);
    return precioIVA;
}

private static int obtenerCantidadValida(Scanner scanner) {
    int cantidad = -1; // Inicialización con un valor por defecto
    do {
        System.out.print("Ingrese la cantidad de producto en stock: ");
        String input = scanner.nextLine().trim();
        if (input.matches("0[0-9]+")) { // Se ajustó la expresión regular para Java
            System.out.println("Error: No ingrese ceros al principio.");
            continue; // Continúa con la siguiente iteración del bucle
        }
        try {
            cantidad = Integer.parseInt(input);
            if (cantidad < 1) {
                System.out.println("La cantidad debe ser un número positivo mayor que cero.");
                cantidad = -1; // Establece cantidad a -1 para que el bucle continue
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor ingrese un número entero válido para la cantidad.");
            // No es necesario asignar -1 aquí ya que se hace al principio del método
        }
    } while (cantidad < 1);
    return cantidad;
}

private static String obtenerTipoProducto(Scanner scanner) {
    System.out.println("Seleccione la categoría del producto:");
    System.out.println("1. Mecato");
    System.out.println("2. Bebidas");
    System.out.println("3. Bebidas alcohólicas");
    System.out.println("4. Aseo");
    System.out.println("5. Granos");
    System.out.println("6. Verduras");
    System.out.println("7. Lácteos");

    String[] categorias = {"Mecato", "Bebidas", "Bebidas alcohólicas", "Aseo", "Granos", "Verduras", "Lácteos"};
    int categoriaNumero;

    while (true) {
        System.out.print("Seleccione la categoría del producto (1-7): ");
        String input = scanner.nextLine().trim(); // Lee la línea completa y elimina espacios extra

        if (input.isEmpty()) {
            System.out.println("Error: Ingrese una opcion valida.");
            continue; // Solicita nuevamente la categoría si no se ingresó ninguna.
        }

        // Verifica que el input no tenga ceros a la izquierda ni sea un número negativo.
        if (input.matches("^[0]+[0-9]*$") || input.matches("^-[0-9]+$")) {
            System.out.println("Error: No ingrese ceros al principio ni números negativos.");
            continue; // Continuar con el siguiente intento.
        }

        try {
            categoriaNumero = Integer.parseInt(input);
            if (categoriaNumero >= 1 && categoriaNumero <= categorias.length) {
                return categorias[categoriaNumero - 1]; // Retorna la categoría si es válida.
            } else {
                System.out.println("Categoría no encontrada. Por favor, digite un número entre 1 y " + categorias.length + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor, digite un número válido para la categoría."); // Manejo de error si no es un número.
        }
    }
    // No es necesario incluir un return aquí, porque el bucle while(true) garantiza que el método no finalizará sin un retorno válido.
}

private static String obtenerNombreProveedorUnico(Scanner scanner, Connection connection) throws SQLException {
    String nombreProveedor;
    do {
        System.out.print("Ingrese Nombre del proveedor: ");
        nombreProveedor = scanner.nextLine().trim();
        // Validación para excluir la letra 'ñ' en cualquier posición y contexto
        if (nombreProveedor.matches(".[ñÑ].")) {
            System.out.println("El nombre del proveedor no puede contener la letra 'ñ'. Por favor, ingrese otro nombre.");
            continue;
        }
        // Resto de validaciones
        // Agrega aquí tus validaciones adicionales si es necesario
        break; // Si pasa todas las validaciones, sale del bucle
    } while (true); // Repite hasta que se ingrese un nombre válido
    return nombreProveedor;
}   
  }

// Resto de métodos...


    

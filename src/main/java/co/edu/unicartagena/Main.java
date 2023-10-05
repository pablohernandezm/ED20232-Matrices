package co.edu.unicartagena;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Main {
    /**
     * Scanner para leer datos de la consola
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Método principal: Ejecuta el menú principal del programa.
     *
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        do {
            // Limpiar la vista
            cleanConsole();

            System.out.print("""
                    Menu
                    1. Sumas de filas y columnas
                    2. Operaciones aritméticas en una matriz.
                    3. Ordenando los elementos de una matriz.
                    4. Rotación de matrices.
                    0. Salir
                                        
                    Opción:""");
            try {
                var option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 0 -> System.exit(0);

                    case 1 -> sumaDeFilasYColumnas();

                    case 2 -> operaciones();

                    case 3 -> ordenarElementos();

                    case 4 -> rotarMatrices();

                    default -> System.out.println("Opción no válida");
                }

                System.out.println("\nPresione enter para continuar...");
                sc.nextLine();

            } catch (InputMismatchException e) {
                sc.nextLine();

                if (!handleInputError("La opción ingresada no es válida.",
                        "¿Desea volver al menú principal?")) {
                    System.exit(0);
                }
            }
        } while (true);

    }

    /**
     * Método para manejar errores de entrada.
     *
     * @param message Mensaje a mostrar al usuario.
     * @return true si el usuario desea corregir los datos, false en caso contrario.
     */
    private static boolean handleInputError(String error, String message) {
        System.out.println(error);
        return getConfirmation(message);
    }

    /**
     * Pregunta al usuario si desea continuar con una determinada operación.
     *
     * @param message Mensaje a mostrar al usuario
     * @return true si el usuario desea continuar, false en caso contrario.
     */
    private static boolean getConfirmation(String message) {
        System.out.printf("%s Si(s) - No(otro): ", message);
        var confirmation = sc.nextLine();
        return confirmation.equalsIgnoreCase("s") || confirmation.equalsIgnoreCase("si");
    }

    /**
     * Limpiar la consola si es posible. Imprimir saltos de línea en caso de estar en un entorno emulado o sistemas que no soporten estos procesos.
     */
    private static void cleanConsole() {
        try {
            // if os is windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // else assume unix or linux
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }


        } catch (Exception ignore) {
        } finally {
            System.out.print("\n".repeat(100));
        }
    }

    /**
     * Método para obtener los datos de la matriz nxm.
     *
     * @param n Número de filas
     * @param m Número de columnas
     * @return Matriz de tipo entero.
     */
    private static int[][] obtenerDatos(int n, int m, boolean excludeZero) {
        if (n <= 0 || m <= 0)
            throw new IllegalArgumentException("Las dimensiones de la matriz no pueden ser negativas o cero.");

        int[][] matriz = new int[n][m];

        // Llenar la matriz
        for (int i = 0; i < n; i++) {
            var messageError = "¿Desea corregir la fila %d?".formatted(i);

            System.out.printf("Ingrese los %d datos(m) de la fila n=%d separados por espacios: ", m, i);
            var row = sc.nextLine().split(" ");

            // Validar la cantidad de datos por fila
            if (row.length > m) {
                if (handleInputError(String.format("\nLa fila %d tiene más datos de los esperados(%d de %d).\n", i, row.length, m),
                        messageError)) {
                    --i;
                    continue;
                } else {
                    return null;
                }

            } else if (row.length < m) {
                if (handleInputError(String.format("\nLa fila %d tiene menos datos de los esperados(%d de %d).\n", i, row.length, m),
                        messageError)) {
                    --i;
                } else {
                    return null;
                }
            }

            // Validar que los datos sean números
            for (int j = 0; j < n; j++) {
                messageError = "¿Desea corregir la fila %d?".formatted(i);

                try {
                    matriz[i][j] = Integer.parseInt(row[j]);

                    // Validar que los datos sean enteros positivos o negativos
                    if (matriz[i][j] == 0 && excludeZero) {
                        if (handleInputError("\nLa matriz solo puede contener números enteros positivos o negativos. El cero no está permitido.\n",
                                messageError)) {
                            --i;
                        } else {
                            return null;
                        }
                    }
                } catch (NumberFormatException ignore) {
                    if (handleInputError(String.format("\nEl valor %s no es un número válido.\n", row[j]), messageError)) {
                        --i;
                    } else {
                        return null;
                    }
                }
            }
        }

        return matriz;
    }

    /**
     * Método para generar una matriz aleatoria.
     *
     * @param n   dimensión 'n' de la matriz 'nxm'.
     * @param m   dimensión 'm' de la matriz 'nxm'.
     * @param min valor mínimo de los datos de la matriz.
     * @param max valor máximo de los datos de la matriz.
     * @return Matriz de tipo entero.
     */
    private static int[][] generarMatrizAleatoria(int n, int m, int min, int max, boolean excludeZero, boolean includeNegatives) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("""
                    Las dimensiones de la matriz no pueden ser negativas o cero.
                    Valores ingresados:
                    n: %d.
                    m: %d.
                    """.formatted(n, m));
        } else if (min > max) {
            throw new IllegalArgumentException("""
                    El valor mínimo no puede ser mayor al valor máximo.
                    Valor mínimo ingresado: %d.
                    Valor máximo ingresado: %d.
                    """.formatted(min, max));
        }

        int[][] matrizAleatoria = new int[n][m];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                var aleatorio = random.nextInt(max - min) + min;

                if (excludeZero && aleatorio == 0) {
                    --j;
                    continue;
                }

                if (includeNegatives && random.nextBoolean()) {
                    aleatorio *= -1;
                }

                matrizAleatoria[i][j] = aleatorio;
            }
        }

        return matrizAleatoria;
    }

    /**
     * Método para preguntar al usuario si desea generar los datos aleatoriamente o ingresarlos manualmente.
     *
     * @param n   dimension 'n' de la matriz 'nxm'.
     * @param m   dimension 'm' de la matriz 'nxm'.
     * @param min valor mínimo de los datos de la matriz.
     * @param max valor máximo de los datos de la matriz.
     * @return Optional con la matriz generada o vacío si el usuario cancela la operación.
     */
    private static Optional<int[][]> preguntarFormaDeLlenado(int n, int m, int min, int max, boolean excludeZero, boolean includeNegatives) {
        int[][] matriz = null;
        menu:
        do {
            System.out.print("""
                    Puede generar los datos aleatoriamente o ingresarlos manualmente.
                    ¿Qué desea hacer?
                    1. Generar datos aleatoriamente.
                    2. Ingresar datos manualmente.
                    0. Cancelar operación.
                                    
                    Opción:""");
            try {
                var option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 1 -> matriz = generarMatrizAleatoria(n, m, min, max, excludeZero, includeNegatives);

                    case 2 -> matriz = obtenerDatos(n, m, excludeZero);

                    case 0 -> {
                        break menu;
                    }

                    default -> System.out.println("\nOpción no válida.\n\n");
                }
            } catch (InputMismatchException ignored) {
                sc.nextLine();

                if (!handleInputError("Opción no válida. Las opciones se seleccionan con valores numéricos.",
                        "¿Desea volver a seleccionar una opción?")) {
                    break;
                } else {
                    cleanConsole();
                }
            }

        } while (matriz == null);

        return Optional.ofNullable(matriz);
    }

    /**
     * Método para ejecutar la operación de suma de filas y columnas según las especificaciones del problema.
     */
    private static void sumaDeFilasYColumnas() {
        cleanConsole();

        // Valores aleatorios generados entre 5 y 10 para 'n' y 'm'.
        int n = (int) Math.floor(Math.random() * 5 + 5);
        int m = (int) Math.floor(Math.random() * 5 + 5);

        System.out.printf("""
                SUMA DE FILAS Y COLUMNAS
                Dimensiones generadas:
                    n: %d
                    m: %d
                    
                """, n, m);
        var obtenida = preguntarFormaDeLlenado(n, m, 5, 10, false, false);
        obtenida.ifPresent(value -> {
            Matriz matriz = new Matriz(value);
            System.out.println(matriz.procesarSuma());
        });
    }

    /**
     * Método para ejecutar las operaciones aritméticas a la matriz según las especificaciones del problema.
     */
    private static void operaciones() {
        cleanConsole();
        System.out.print("""
                OPERACIONES ARITMÉTICAS
                Al ingresar los datos de la matriz, se realizarán las siguientes operaciones:
                    1. Suma de los elementos de la diagonal principal.
                    2. Multiplicación de los elementos de la diagonal secundaria.
                    3. División entre la suma y la multiplicación anteriores.
                                
                Ingrese el tamaño n de la matriz nxn.
                n:""");

        try {
            var n = sc.nextInt();
            System.out.println("\n");

            var obtenida = preguntarFormaDeLlenado(n, n, -50, 50, true, true);

            obtenida.ifPresent(Main::procesarOperaciones);

        } catch (InputMismatchException ignored) {
            if (handleInputError("El valor que ingresaste para n no es válido.",
                    "¿Desea volver a ingresar el valor de n?")) {
                operaciones();
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * Método para procesar las operaciones aritméticas a la matriz según las especificaciones del problema.
     *
     * @param value Matriz de tipo entero.
     */
    private static void procesarOperaciones(int[][] value) {
        cleanConsole();
        MatrizCuadrada matriz = new MatrizCuadrada(value);

        System.out.printf("""
                        MATRIZ GENERADA
                        %s
                                        
                                        
                        DIAGONAL PRINCIPAL
                        %s
                                        
                        Suma de los elementos de la diagonal principal: %d.
                                        
                                        
                        DIAGONAL SECUNDARIA
                        %s
                                        
                        Producto de los elementos de la diagonal secundaria: %d.
                                        
                        DIVISIÓN ENTRE LA SUMA Y EL PRODUCTO DE LAS DIAGONALES
                        %s = %.4f
                        """,
                matriz,
                matriz.getDiagonalPrincipal(), matriz.getSumaDiagonalPrincipal(),
                matriz.getDiagonalSecundaria(), matriz.getProductoDiagonalSecundaria(),
                matriz.getSumaDiagonalPrincipal() + "/" + matriz.getProductoDiagonalSecundaria(),
                matriz.getSumaDiagonalPrincipal() / (double) matriz.getProductoDiagonalSecundaria());
    }

    /**
     * Método para ejecutar la operación de ordenar los elementos de la matriz según las especificaciones del problema.
     */
    private static void ordenarElementos() {
        //TODO: Implementar
        System.out.println("ingrese el tamaño de la matriz cuadrada:\n");
        int Size_Matriz = sc.nextInt();
        int matriz[][];
        
        matriz = generarMatrizAleatoria(Size_Matriz, Size_Matriz, 25, 75, true, false);
        
        System.out.println("matriz generada: ");
        for (int i = 0; i <= Size_Matriz-1; i++) {
            for (int j = 0; j <= Size_Matriz-1; j++) {
                System.out.print("["+matriz[i][j]+"]");
                
            }
            System.out.println();
        }
    }

    /**
     * Método para ejecutar la operación de rotación de matrices según las especificaciones del problema.
     */
    private static void rotarMatrices() {
        //TODO: Implementar
    }

}


package co.edu.unicartagena;

import java.util.InputMismatchException;
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
            clearScreen();

            System.out.print("""
                    Menu
                    1. Sumas de filas y columnas
                    2. Operaciones aritméticas en una matriz.
                    3. Ordenando los elementos de una matriz.
                    4. Rotación de matrices.
                    0. Salir
                                        
                    Opción:""");
            try{
                var option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 0 -> System.exit(0);

                    case 1 -> sumaDeFilasYColumnas();

                    case 2 -> operacionesAritmeticas();

                    case 3 -> ordenarElementos();

                    case 4 -> rotacionDeMatrices();

                    default -> System.out.println("Opción no válida");
                }

                System.out.println("\nPresione enter para continuar...");
                sc.nextLine();

            }catch (InputMismatchException e){
                sc.nextLine();

                if (!handleInputError("La opción ingresada no es válida.",
                        "¿Desea volver al menú principal?")){
                    System.exit(0);
                }
            }
        } while (true);

    }

    /**
     * Método para obtener los datos de la matriz.
     *
     * @return Matriz cuadrada de tipo entero.
     */
    private static int[][] obtenerDatosCuadrada(int size) throws IllegalArgumentException {
        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño de la matriz no puede ser negativo o cero.");
        }

        return obtenerDatos(size, size);
    }

    /**
     * Método para obtener los datos de la matriz nxm.
     *
     * @param n Número de filas
     * @param m Número de columnas
     * @return Matriz de tipo entero.
     */
    private static int[][] obtenerDatos(int n, int m) {
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
                    if (matriz[i][j] == 0) {
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
    private static void clearScreen() {
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
            System.out.print("\n".repeat(50));
        }
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
    private static int[][] generarMatrizAleatoria(int n, int m, int min, int max) {
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

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrizAleatoria[i][j] = (int) Math.floor((Math.random() * (max - min)) + min);
            }
        }

        return matrizAleatoria;
    }

    /**
     * Método para ejecutar la operación de suma de filas y columnas según las especificaciones del problema.
     */
    private static void sumaDeFilasYColumnas() {
        clearScreen();
        Matriz matriz;

        // Valores aleatorios generados entre 5 y 10 para 'n' y 'm'.
        int n = (int) Math.floor(Math.random() * 5 + 5);
        int m = (int) Math.floor(Math.random() * 5 + 5);

        System.out.printf("""
                SUMA DE FILAS Y COLUMNAS
                Dimensiones generadas:
                    n: %d
                    m: %d
                    
                """, n, m);

        menu:
        do {
            System.out.print("""
                    Puede generar los datos aleatoriamente o ingresarlos manualmente.
                    ¿Qué desea hacer?
                    1. Generar datos aleatoriamente.
                    2. Ingresar datos manualmente.
                    0. Cancelar operación.
                                    
                    Opción:""");
            try{
                var option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 1 -> {
                        matriz = new Matriz(generarMatrizAleatoria(n, m, 5, 10));
                        System.out.println(matriz.procesarSuma());
                        break menu;
                    }

                    case 2 -> {
                        var base = obtenerDatos(n, m);
                        if (base != null) {
                            matriz = new Matriz(base);
                            System.out.println(matriz.procesarSuma());
                            break menu;
                        }

                        return;
                    }

                    case 0 -> {
                        return;
                    }

                    default -> System.out.println("\nOpción no válida.\n\n");
                }
            } catch (InputMismatchException ignored){
                sc.nextLine();

                if (!handleInputError("Opción no válida. Las opciones se seleccionan con valores numéricos.",
                        "¿Desea volver a seleccionar una opción?")){
                    break;
                } else {
                    clearScreen();
                }
            }

        } while (true);
    }

    /**
     * Método para ejecutar las operaciones aritméticas a la matriz según las especificaciones del problema.
     */
    private static void operacionesAritmeticas() {

    }

    /**
     * Método para ejecutar la operación de ordenar los elementos de la matriz según las especificaciones del problema.
     */
    private static void ordenarElementos() {
        //TODO: Implementar
    }

    /**
     * Método para ejecutar la operación de rotación de matrices según las especificaciones del problema.
     */
    private static void rotacionDeMatrices() {
        //TODO: Implementar
    }

}


package co.edu.unicartagena;
/*
;==========================================
; NOMBRE:                   CÓDIGO
; PABLO HERNÁNDEZ MELÉNDEZ  0221910052
; JHOY CASTRO CASANOVA      0221910044
; GABRIEL LARA MONTIEL      0222110057
;==========================================
*/

import co.edu.unicartagena.Extra.Pointer;

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
     * @return true si el usuario desea corregir los datos, false en caso
     * contrario.
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
     * Limpiar la consola si es posible. Imprimir saltos de línea en caso de
     * estar en un entorno emulado o sistemas que no soporten estos procesos.
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
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Las dimensiones de la matriz no pueden ser negativas o cero.");
        }

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
     * Método para preguntar al usuario si desea generar los datos
     * aleatoriamente o ingresarlos manualmente.
     *
     * @param n   dimension 'n' de la matriz 'nxm'.
     * @param m   dimension 'm' de la matriz 'nxm'.
     * @param min valor mínimo de los datos de la matriz.
     * @param max valor máximo de los datos de la matriz.
     * @return Optional con la matriz generada o vacío si el usuario cancela la
     * operación.
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
     * Método para ejecutar la operación de suma de filas y columnas según las
     * especificaciones del problema.
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
     * Método para ejecutar las operaciones aritméticas a la matriz según las
     * especificaciones del problema.
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
            }
        }
    }

    /**
     * Método para procesar las operaciones aritméticas a la matriz según las
     * especificaciones del problema.
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
     * Método para ejecutar la operación de ordenar los elementos de la matriz
     * según las especificaciones del problema.
     */
    private static void ordenarElementos() {
        cleanConsole();

        try {
            System.out.print("""
                    ORDENANDO LOS ELEMENTOS DE UNA MATRIZ
                    Luego de generar los elementos de la matriz nxn se imprimirán:
                        1. La matriz original.
                        2. La matriz con los elementos por debajo de la diagonal ordenados de menor a mayor.
                        3. La matriz con los elementos por encima de la diagonal ordenados de mayor a menor.
                                                                       
                    Ingrese el tamaño n de la matriz nxn.
                    n:""");
            int n = sc.nextInt();
            sc.nextLine();

            int[][] matriz;

            // se genera la matriz
            matriz = generarMatrizAleatoria(n, n, 25, 75, false, false);

            // Número de elementos arriba o abajo de la diagonal
            int nout = 0;
            for (int i = 0; i < n; i++) {
                nout += i;
            }

            // Vectores de elementos no diagonales
            Pointer[] vectorSuperiorDP = new Pointer[nout];
            Pointer[] vectorInferiorDP = new Pointer[nout];


            // Llenar los vectores
            int countSupDP = 0;
            int countInfDP = 0;
            for (int i = 0; i <= n - 1; i++) {
                for (int j = 0; j <= n - 1; j++) {
                    if (j > i) {
                        vectorSuperiorDP[countSupDP] = new Pointer(matriz[i][j], i, j);
                        countSupDP++;
                    }

                    if (j < i) {
                        vectorInferiorDP[countInfDP] = new Pointer(matriz[i][j], i, j);
                        countInfDP++;
                    }
                }
            }

            // Ordenar los vectores
            ordenarAscendente(vectorInferiorDP);
            ordenarDescendiente(vectorSuperiorDP);

            // Matriz Original
            MatrizCuadrada original = new MatrizCuadrada(matriz);

            System.out.printf("""
                    MATRIZ ORIGINAL
                                        
                    %s
                                        
                    """, original);

            original.replace(vectorInferiorDP);
            System.out.printf("""
                    ELEMENTOS POR DEBAJO DE LA DIAGONAL ORDENADOS DE MENOR A MAYOR
                                        
                    %s
                                        
                    """, original);

            original.replace(vectorSuperiorDP);
            System.out.printf("""
                    ELEMENTOS POR ARRIBA DE LA DIAGONAL ORDENADOS DE MAYOR A MENOR
                                        
                    %s
                                        
                    """, original);

        } catch (InputMismatchException ignored) {
            if (handleInputError("El valor que ingresaste para el tamaño de la matriz no es válido.",
                    "¿Desea volver a ingresar el valor de n?")) {
                ordenarElementos();
            }
        }
    }

    /**
     * Método para ordenar los elementos de un vector de menor a mayor
     *
     * @param pointers vector de tipo Pointer.
     */
    public static void ordenarAscendente(Pointer[] pointers) {
        for (int i = 0; i < pointers.length; i++) {
            for (int j = 0; j < pointers.length - 1; j++) {
                if (pointers[j].getValue() > pointers[j + 1].getValue()) {
                    int temp = pointers[j].getValue();

                    pointers[j].setValue(pointers[j + 1].getValue());
                    pointers[j + 1].setValue(temp);
                }
            }
        }
    }

    /**
     * Método para ordenar los elementos de un vector de mayor a menor
     *
     * @param pointers vector de tipo Pointer.
     */
    public static void ordenarDescendiente(Pointer[] pointers) {
        for (int i = 0; i < pointers.length; i++) {
            for (int j = 0; j < pointers.length - 1; j++) {
                if (pointers[j].getValue() < pointers[j + 1].getValue()) {
                    int temp = pointers[j].getValue();

                    pointers[j].setValue(pointers[j + 1].getValue());
                    pointers[j + 1].setValue(temp);
                }
            }
        }
    }

    /**
     * Método para ejecutar la operación de rotación de matrices según las
     * especificaciones del problema.
     */
     private static void rotarMatrices() {


        // Pedir al usuario el tamaño de la matriz
        System.out.print("Ingrese el tamaño de la matriz (n): ");
        int n = sc.nextInt();

        if (n < 2 || n > 10) {
            System.out.println("Tamaño de matriz no válido. Debe estar entre 2 y 10.");
            return;
        }

        // Crear y llenar la matriz con valores aleatorios del 5 al 25
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = (int) (Math.random() * 21 + 5); // Números aleatorios entre 5 y 25
            }
        }

        // Mostrar la matriz original
        System.out.println("Matriz Original:");
        printMatrix(matrix);

        // Pedir al usuario el anillo a rotar
        System.out.print("Ingrese el número del anillo a rotar (1..." + (n / 2) + "): ");
        int anillo = sc.nextInt();
        if (anillo < 1 || anillo > (n / 2)) {
            System.out.println("Número de anillo no válido.");
            return;
        }

        // Pedir la dirección de rotación
        System.out.print("Ingrese la dirección de rotación (izquierda o derecha): ");
        String direccion = sc.next();
        if (!direccion.equals("izquierda") && !direccion.equals("derecha")) {
            System.out.println("Dirección no válida.");
            return;
        }

        // Pedir la cantidad de grados a rotar
        System.out.print("Ingrese la cantidad de grados a rotar (90, 180, 270): ");
        int grados = sc.nextInt();
        if (grados != 90 && grados != 180 && grados != 270) {
            System.out.println("Cantidad de grados no válida.");
            return;
        }

        // Guardar las partes superior, derecha, abajo e izquierda del anillo
        int[] top = new int[n - 2 * anillo + 2];
        int[] right = new int[n - 2 * anillo + 2];
        int[] bottom = new int[n - 2 * anillo + 2];
        int[] left = new int[n - 2 * anillo + 2];

        int idx = 0;
        for (int i = anillo - 1; i < n - anillo + 1; i++) {
            top[idx] = matrix[anillo - 1][i];
            right[idx] = matrix[i][n - anillo];
            bottom[idx] = matrix[n - anillo][n - i - 1];
            left[idx] = matrix[n - i - 1][anillo - 1];
            idx++;
        }

        if (direccion.equalsIgnoreCase("derecha")){
            switch (grados) {
                case 90: // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matrix[anillo - 1][i] = left[idx];
                        matrix[i][n - anillo] = top[idx];
                        matrix[n - anillo][n - i - 1] = right[idx];
                        matrix[n - i - 1][anillo - 1] = bottom[idx];
                        idx++;
                    }
                    break;
                case 180:
                    // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matrix[anillo - 1][i] = bottom[idx];
                        matrix[i][n - anillo] = left[idx];
                        matrix[n - anillo][n - i - 1] = top[idx];
                        matrix[n - i - 1][anillo - 1] = right[idx];
                        idx++;
                    }
                    break;
                case 270:
                    // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matrix[anillo - 1][i] = right[idx];
                        matrix[i][n - anillo] = bottom[idx];
                        matrix[n - anillo][n - i - 1] = left[idx];
                        matrix[n - i - 1][anillo - 1] = top[idx];
                        idx++;
                    }
            }
        }

        if (direccion.equalsIgnoreCase("izquierda")){
            switch (grados) {
                case 90: // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matrix[anillo - 1][i] = right[idx];
                        matrix[i][n - anillo] = bottom[idx];
                        matrix[n - anillo][n - i - 1] = left[idx];
                        matrix[n - i - 1][anillo - 1] = top[idx];
                        idx++;
                    }
                    break;
                case 180:
                    // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matrix[anillo - 1][i] = bottom[idx];
                        matrix[i][n - anillo] = left[idx];
                        matrix[n - anillo][n - i - 1] = top[idx];
                        matrix[n - i - 1][anillo - 1] = right[idx];
                        idx++;
                    }
                    break;
                case 270:
                    // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matrix[anillo - 1][i] = left[idx];
                        matrix[i][n - anillo] = top[idx];
                        matrix[n - anillo][n - i - 1] = right[idx];
                        matrix[n - i - 1][anillo - 1] = bottom[idx];
                        idx++;
                    }
            }
        }

        // Mostrar la matriz resultante
        System.out.println("Matriz Resultante:");
        printMatrix(matrix);
        return;
    }

    // Función para imprimir una matriz
    static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }

}

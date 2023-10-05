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
                    case 0 ->
                        System.exit(0);

                    case 1 ->
                        sumaDeFilasYColumnas();

                    case 2 ->
                        operaciones();

                    case 3 ->
                        ordenarElementos();

                    case 4 ->
                        rotarMatrices();

                    default ->
                        System.out.println("Opción no válida");
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
     * @param n dimensión 'n' de la matriz 'nxm'.
     * @param m dimensión 'm' de la matriz 'nxm'.
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
     * @param n dimension 'n' de la matriz 'nxm'.
     * @param m dimension 'm' de la matriz 'nxm'.
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
                    case 1 ->
                        matriz = generarMatrizAleatoria(n, m, min, max, excludeZero, includeNegatives);

                    case 2 ->
                        matriz = obtenerDatos(n, m, excludeZero);

                    case 0 -> {
                        break menu;
                    }

                    default ->
                        System.out.println("\nOpción no válida.\n\n");
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
            } else {
                System.exit(0);
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
        //TODO: Implementar

        //generalidades
        System.out.println("ingrese el tamaño de la matriz cuadrada:\n");
        int Size_Matriz = sc.nextInt();

        int matriz[][];

        //se genera la matriz
        matriz = generarMatrizAleatoria(Size_Matriz, Size_Matriz, 25, 75, true, false);

//----------------------------------------------------------------------------------------------------
//apartir de aqui se trabajara en exclusiva con la diagonal principal
        //creacion de vectores fugaces
        int VectorSuperiorDP[] = new int[100];
        int VectorInferiorDP[] = new int[100];

        //inician en 0 con 100 posiciones
        for (int i = 0; i < 100; i++) {
            VectorSuperiorDP[i] = 0;
            VectorInferiorDP[i] = 0;

        }

        //iteradores para vectores fugaces
        int countSupDP = 0;
        int countInfDP = 0;


        /*este for tiene varias funciones
         *1. mostrar la matriz generada
         *2.llenar el vector momentaneo referente a los elementos arriba de la diagonal principal
         *3.llenar el vector momentaneo referente a los elementos abajo de la diagonal principal
         */
        System.out.println("matriz generada: ");

        for (int i = 0; i <= Size_Matriz - 1; i++) {
            for (int j = 0; j <= Size_Matriz - 1; j++) {

                //llena vector superior de diagonal principal
                if (j > i) {

                    VectorSuperiorDP[countSupDP] = matriz[i][j];
                    countSupDP++;
                }

                //llena vector inferior de diagonal principal
                if (j < i) {

                    VectorInferiorDP[countInfDP] = matriz[i][j];
                    countInfDP++;
                }

                //muestra la matriz generada
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }

        System.out.println("\n");

        int copia1[] = new int[countSupDP + 1];
        int copia2[] = new int[countInfDP + 1];

        for (int i = 0; i <= countSupDP - 1; i++) {
            copia1[i] = VectorSuperiorDP[i];
        }

        for (int i = 0; i <= countInfDP - 1; i++) {
            copia2[i] = VectorInferiorDP[i];
        }

        ordenar_menor_A_mayor(copia2);
        ordenar_mayor_a_menor(copia1);

        System.out.println("arriba de la diagonal principal ordenada de mayor a menor:");

        for (int i = 0; i <= copia1.length - 1; i++) {
            if (copia1[i] != 0) {
                System.out.print("[" + copia1[i] + "]");
            }
        }
        System.out.println();

        System.out.println("abajo de la diagonal principal ordenada de menor a mayor:");
        for (int i = 0; i <= copia2.length - 1; i++) {
            if (copia2[i] != 0) {
                System.out.print("[" + copia2[i] + "]");
            }
        }

        System.out.println("\n");

        int contar1 = 0;
        int contar2 = 1;
        int matriz_dp[][] = new int[Size_Matriz][Size_Matriz];
        System.out.println("matriz resultante generada segun requisitos previos con respecto a la diagonal principal:");

        for (int i = 0; i <= Size_Matriz - 1; i++) {
            for (int j = 0; j <= Size_Matriz - 1; j++) {

                if (j == i) {
                    matriz_dp[i][j] = matriz[i][j];
                }
                if (j > i && copia1[contar1] != 0) {

                    matriz_dp[i][j] = copia1[contar1];
                    contar1++;
                }
                if (j < i && copia2[contar2] != 0) {

                    matriz_dp[i][j] = copia2[contar2];
                    contar2++;
                }
                if(j==i){
                   System.out.print("(" + matriz_dp[i][j] + ")"); 
                }else{
                System.out.print("[" + matriz_dp[i][j] + "]");
                }
            }
            System.out.println();
        }

        //----------------------------------------------------------------------------------------------------
        //apartir de aqui se trabajara con la diagonal secundaria
        int matriz_Espejo[][] = new int[Size_Matriz][Size_Matriz];

        for (int i = 0; i <= Size_Matriz - 1; i++) {

            int contador_inverso = Size_Matriz - 1;

            for (int j = 0; j <= Size_Matriz - 1; j++) {

                matriz_Espejo[i][j] = matriz[i][contador_inverso];
                contador_inverso--;
                
            }

        }

        //creada la matriz espejo, se hace lo mismo que con la diagonal principal
        //creacion de vectores fugaces
        int VectorSuperiorDS[] = new int[100];
        int VectorInferiorDS[] = new int[100];

        //inician en 0 con 100 posiciones
        for (int i = 0; i < 100; i++) {
            VectorSuperiorDS[i] = 0;
            VectorInferiorDS[i] = 0;

        }

        //iteradores para vectores fugaces
        int countSupDS = 0;
        int countInfDS = 0;


        /*este for tiene 2 funciones
         *1.llenar el vector momentaneo referente a los elementos arriba de la diagonal secundaria
         *2.llenar el vector momentaneo referente a los elementos abajo de la diagonal secundaria
         */
        for (int i = 0; i <= Size_Matriz - 1; i++) {
            for (int j = 0; j <= Size_Matriz - 1; j++) {

                //llena vector superior de diagonal secundaria
                if (j > i) {

                    VectorSuperiorDS[countSupDS] = matriz_Espejo[i][j];
                    countSupDS++;
                }

                //llena vector inferior de diagonal principal
                if (j < i) {

                    VectorInferiorDS[countInfDS] = matriz_Espejo[i][j];
                    countInfDS++;
                }

            }
        }

        System.out.println("\n");

        int copia3[] = new int[countSupDS + 1];
        int copia4[] = new int[countInfDS + 1];

        for (int i = 0; i <= countSupDS - 1; i++) {
            copia3[i] = VectorSuperiorDS[i];
        }

        for (int i = 0; i <= countInfDS - 1; i++) {
            copia4[i] = VectorInferiorDS[i];
        }

        ordenar_menor_A_mayor(copia4);
        ordenar_mayor_a_menor(copia3);

        System.out.println("arriba de la diagonal secundaria ordenada de mayor a menor:");
        for (int i = 0; i <= copia3.length - 1; i++) {
            if (copia3[i] != 0) {
                System.out.print("[" + copia3[i] + "]");
            }
        }
        System.out.println();

        System.out.println("abajo de la diagonal secundaria ordenada de menor a mayor:");
        for (int i = 0; i <= copia4.length - 1; i++) {
            if (copia4[i] != 0) {
                System.out.print("[" + copia4[i] + "]");
            }
        }

        System.out.println("\n");

        int contar3 = 0;
        int contar4 = 1;
        int matriz_ds[][] = new int[Size_Matriz][Size_Matriz];

        System.out.println("matriz resultante generada segun requisitos previos con respecto a la diagonal secundaria:");
        for (int i = 0; i <= Size_Matriz - 1; i++) {
            for (int j = 0; j <= Size_Matriz - 1; j++) {
 
                if (j > i && copia3[contar3] != 0) {

                    matriz_ds[i][j] = copia3[contar3];
                    contar3++;
                }else if (j < i && copia4[contar4] != 0) {

                    matriz_ds[i][j] = copia4[contar4];
                    contar4++;
                }else if(j == i){
                    matriz_ds[i][j] = matriz_Espejo[i][j];
                }
  
            }           
        }
        
        int matriz_Espejo_del_espejo[][] = new int[Size_Matriz][Size_Matriz];

        for (int i = 0; i <= Size_Matriz - 1; i++) {

            int contador_inverso = Size_Matriz - 1;

            for (int j = 0; j <= Size_Matriz - 1; j++) {

                matriz_Espejo_del_espejo[i][j] = matriz_ds[i][contador_inverso];
                contador_inverso--;
                System.out.print("["+matriz_Espejo_del_espejo[i][j] + "]");
            }
            System.out.println();
        }

    }

    /*
     *metodo de ordenamiento de menor a mayor
     */
    public static void ordenar_menor_A_mayor(int[] arr) {
        int n = arr.length;
        boolean intercambiado;

        for (int i = 0; i < n - 1; i++) {
            intercambiado = false;

            // Últimos i elementos ya están ordenados, así que no es necesario revisarlos
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Intercambiar arr[j] y arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    intercambiado = true;
                }
            }

            // Si no hubo intercambios en esta pasada, el arreglo ya está ordenado
            if (!intercambiado) {
                break;
            }
        }
    }

    /*
     *metodo de ordenamiento de mayor a menor
     */
    public static void ordenar_mayor_a_menor(int[] arr) {
        int n = arr.length;
        boolean intercambiado;

        for (int i = 0; i < n - 1; i++) {
            intercambiado = false;

            // Últimos i elementos ya están ordenados, así que no es necesario revisarlos
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    // Intercambiar arr[j] y arr[j+1] para orden descendente
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    intercambiado = true;
                }
            }

            // Si no hubo intercambios en esta pasada, el arreglo ya está ordenado
            if (!intercambiado) {
                break;
            }
        }
    }

    /**
     * Método para ejecutar la operación de rotación de matrices según las
     * especificaciones del problema.
     */
    private static void rotarMatrices() {
        //TODO: Implementar
    }

}

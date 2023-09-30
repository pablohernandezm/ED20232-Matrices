package co.edu.unicartagena;

import java.util.Scanner;

public class Main {
    /**
     * Scanner para leer datos de la consola
     */
    private static final Scanner sc = new Scanner(System.in);
    private static final int SPACING = 8;
    private static int[][] matriz = {{1, 2}, {3, 4}};

    /**
     * Método principal: Ejecuta el menú principal del programa.
     *
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        do {
            System.out.print("""
                    Menu
                    1. Llenar la matriz
                    2. Sumas de filas y columnas
                    0. Salir
                                        
                    Opción:""");
            var option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 0 -> {
                    System.out.println("Adios");
                    System.exit(0);
                }

                case 2 -> {
                    try{
                        System.out.println(getSumMatriz());
                    } catch (NullPointerException e){
                        System.out.println(e.getMessage());
                    }
                }

                default -> {
                    System.out.println("Opción no válida");
                    sc.nextLine();
                }
            }

            // Pequeña pausa para que el usuario pueda leer el resultado
            sc.nextLine();

            // Limpiar la vista
            clearScreen();
        } while (true);

    }

    /**
     * Revisa si la matriz ha sido inicializada
     * @throws NullPointerException Si la matriz no ha sido inicializada
     */
    private static void checkMatriz() throws NullPointerException{
        if (matriz.length == 0) {
            throw new NullPointerException("La matriz no ha sido inicializada. Llene la matriz antes de continuar con la operación.");
        }
    }

    public static String getStringMatriz() throws NullPointerException{
        checkMatriz();

        StringBuilder matrizText = new StringBuilder();

        for (int[] fila : matriz) {
            StringBuilder filaText = new StringBuilder();
            for (int dato : fila) {
                filaText.append(dato).append(" ");
            }

            matrizText.append(filaText).append("\n");
        }

        return matrizText.toString();
    }

    public static String getSumMatriz() throws NullPointerException{
        checkMatriz();

        StringBuilder matrizText = new StringBuilder();
        var specialSpacing = 14;

        // Llenar el encabezado
        var headerFormat = "Filas/Columnas %s %"+specialSpacing+"s\n";
        var header = new StringBuilder();
        var headerHeaders = new StringBuilder();

        for (int i = 0; i < matriz.length; i++) {
            headerHeaders.append(String.format(" %" + SPACING + "d ", i));
        }

        header.append(String.format(headerFormat, headerHeaders, "Suma"));

        // Agregar el encabezado a la matriz
        matrizText.append(header);

        // Llenar el cuerpo de la matriz
        for (int i=0; i<matriz.length; i++) {

            // Indice de la fila
            StringBuilder filaText = new StringBuilder(String.format("%"+specialSpacing+"d ", i));

            // Obtención de valores
            for (int dato : matriz[i]) {
                var datoText = String.format(" %"+SPACING+"d ", dato);

                filaText.append(datoText);
            }

            matrizText.append(filaText);

            // Obtener la suma de la fila
            matrizText.append(getRowSum(i, specialSpacing)).append("\n");
        }

        // Llenar el pie de la matriz con la suma de las columnas
        var footer = String.format("%"+specialSpacing+"s %s %"+specialSpacing+"s", "Suma", getColSums(), "---");
        // Adjuntar el pie de la matriz
        matrizText.append(footer);

        // Retornar la matriz
        return matrizText.toString();
    }

    /**
     * Obtiene la suma de las columnas de la matriz
     * @return Suma de las columnas de la matriz
     */
    private static String getColSums(){
        StringBuilder colSums = new StringBuilder();
        for (int i = 0; i < matriz.length; i++) {
            int sum = 0;
            for (int[] fila : matriz) {
                sum += fila[i];
            }
            colSums.append(String.format(" %" + SPACING + "d ", sum));
        }

        return colSums.toString();
    }

    /**
     * Obtiene la suma de los elementos de la fila i
     * @param i Índice de la fila
     * @param dataWidth Ancho o espacio que ocuparán los dentro del String resultante.
     * @return Suma de los elementos de la fila i
     */
    private static String getRowSum(int i, int dataWidth){
        StringBuilder rowSum = new StringBuilder();
        int sum = 0;
        for (int dato : matriz[i]) {
            sum += dato;
        }

        rowSum.append(String.format(" %" + dataWidth + "d ", sum));

        return rowSum.toString();
    }

    /**
     * Limpiar la consola si es posible. Imprimir saltos de línea en caso de estar en un entorno emulado o sistemas que no soporten estos procesos.
     */
    private static void clearScreen(){
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

}


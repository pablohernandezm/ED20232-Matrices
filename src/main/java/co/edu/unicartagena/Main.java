package co.edu.unicartagena;

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
        Matriz matriz = new Matriz();

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

                case 1 -> {
                    var datos = obtenerDatos();
                    if (datos == null){
                        continue;
                    }

                    try{
                        matriz = new Matriz(datos);

                        System.out.println("\nMatriz creada con éxito");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 2 -> {
                   try {
                       System.out.println(matriz.procesarSuma());
                   } catch (NullPointerException e){
                       System.out.println(e.getMessage());
                   }
                }

                default -> {
                    System.out.println("Opción no válida");
                }
            }

            // Pequeña pausa para que el usuario pueda leer el resultado
            sc.nextLine();

            // Limpiar la vista
            clearScreen();
        } while (true);

    }

    /**
     * Método para obtener los datos de la matriz.
     * @return Matriz cuadrada de tipo entero.
     */
    private static int[][] obtenerDatos(){
        System.out.print("\nIngrese la dimensión de la matriz cuadrada: ");
        var size = sc.nextInt();
        sc.nextLine();

        // Manejar dimensiones negativas
        if (size<0){
            if (handleInputError("\nEl tamaño de la matriz no puede ser negativo.\n")){
                return obtenerDatos();
            }

            return null;
        }

        int[][] matriz = new int[size][size];

        // Llenar la matriz
        for (int i = 0; i < matriz.length; i++) {
            System.out.printf("Ingrese los datos de la fila %d separados por espacios: ", i);
            var row = sc.nextLine().split(" ");

            // Validar la cantidad de datos por fila
            if (row.length>size){
                if (handleInputError(String.format("\nLa fila %d tiene más datos de los esperados.\n", i))){
                    --i;
                    continue;
                } else {
                    return null;
                }

            } else if (row.length<size){
                if (handleInputError(String.format("\nLa fila %d tiene menos datos de los esperados(%d de %d).\n", i, row.length, size))){
                    --i ;
                } else {
                    return null;
                }
            }

            // Validar que los datos sean números
            for (int j = 0; j < matriz.length; j++) {
                try{
                    matriz[i][j] = Integer.parseInt(row[j]);

                    // Validar que los datos sean enteros positivos o negativos
                    if (matriz[i][j] == 0){
                        if (handleInputError("\nLa matriz solo puede contener números enteros positivos o negativos. El cero no está permitido.\n")){
                            --i ;
                        }else{
                            return null;
                        }
                    }
                } catch (NumberFormatException ignore){
                    if (handleInputError(String.format("\nEl valor %s no es un número válido.\n", row[j]))){
                       --i ;
                    }else{
                        return null;
                    }
                }
            }
        }

       return matriz;
    }

    private static boolean handleInputError(String message){
        System.out.println(message);
        return getConfirmation("¿Desea volver a ingresar los datos erróneos?");
    }

    /**
     * Pregunta al usuario si desea continuar con una determinada operación.
     * @param message Mensaje a mostrar al usuario
     * @return true si el usuario desea continuar, false en caso contrario.
     */
    private static boolean getConfirmation(String message){
        System.out.printf("%s (s): ", message);
        var confirmation = sc.nextLine();
        return confirmation.equalsIgnoreCase("s");
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


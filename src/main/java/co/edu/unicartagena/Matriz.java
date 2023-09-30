package co.edu.unicartagena;

import java.util.Objects;

public class Matriz {
    /**
     * Espacio entre los datos de la matriz
     */
    private static final int SPACING = 8;
    /**
     * Matriz de datos
     */
    private final int[][] matriz;

    /**
     * Constructor de la clase por defecto.
     */
    public Matriz(){
        matriz = null;
    }

    /**
     * Constructor de la clase
     * @param matriz Matriz cuadrada de tipo T.
     * @throws IllegalArgumentException Si la matriz no es cuadrada o está vacía.
     */
    public Matriz(int[][] matriz) throws IllegalArgumentException {
        if (matriz.length == 0){
            throw new IllegalArgumentException("La matriz no puede ser vacía");
        }

        for (int[] row : matriz) {
            if (matriz.length != row.length) {
                throw new IllegalArgumentException("La matriz debe ser cuadrada");
            }
        }

        this.matriz = matriz;
    }

    /**
     * Método para obtener la matriz.
     * @return Matriz de tipo T.
     */
    public int[][] getMatriz() {
        return matriz;
    }

    /**
     * Método para obtener la dimensión de la matriz.
     * @return Dimensión de la matriz.
     */
    public int getDimension(){return this.matriz == null? 0 : this.matriz.length;}

    /**
     * Método para procesar la suma de filas y columna de la matriz.
     * @return Matriz procesada en forma de String con las sumas de filas y columnas.
     */
    public String procesarSuma() throws NullPointerException {
        if (matriz == null){
            throw new NullPointerException("La matriz no tiene valores. Asegúrese de llenarla para continuar con la operación");
        }

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
                var datoText = String.format(" %"+SPACING+"s ", dato);

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
    private String getColSums(){
        StringBuilder colSums = new StringBuilder();
        for (int i = 0; i < Objects.requireNonNull(matriz).length; i++) {
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
    private String getRowSum(int i, int dataWidth){
        StringBuilder rowSum = new StringBuilder();
        int sum = 0;
        for (int dato : Objects.requireNonNull(matriz)[i]) {
            sum += dato;
        }

        rowSum.append(String.format(" %" + dataWidth + "d ", sum));

        return rowSum.toString();
    }
}
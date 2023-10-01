package co.edu.unicartagena;

import java.util.IllegalFormatException;
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
     * Constructor de la clase
     *
     * @param matriz Matriz de tipo entero.
     * @throws IllegalArgumentException Si la matriz está vacía.
     */
    public Matriz(int[][] matriz) throws IllegalArgumentException {
        if (matriz.length == 0) {
            throw new IllegalArgumentException("La matriz no puede ser vacía");
        }

        this.matriz = matriz;
    }

    /**
     * Método recibir el string de la matriz.
     *
     * @return matriz en forma de String.
     */
    @Override
    public String toString() {
        return handleToString(llenarEncabezado() + "\n");
    }

    private String handleToString(String header) {
        StringBuilder matrizText = new StringBuilder();
        var specialSpacing = 14;

        matrizText.append(header);

        // Llenar el cuerpo de la matriz
        for (int i = 0; i < Objects.requireNonNull(matriz).length; i++) {

            // Indice de la fila
            StringBuilder filaText = llenarFila(specialSpacing, i);

            matrizText.append(filaText);

            // Obtener la suma de la fila
            matrizText.append(getRowSum(i, specialSpacing)).append("\n");
        }

        return matrizText.toString();
    }

    private String handleToString(String header, String footer) {
        return handleToString(header) + footer;
    }

    /**
     * Método para llenar una determinada fila de la matriz, con el formato adecuado y sus índices.
     *
     * @param indexSpacing Espacio que ocupará el índice de la fila.
     * @param index        Índice de la fila a llenar.
     * @return Fila de la matriz en forma de StringBuilder.
     */
    private StringBuilder llenarFila(int indexSpacing, int index) {
        // Indice de la fila
        StringBuilder filaText = new StringBuilder(String.format("%" + indexSpacing + "d ", index));

        // Obtención de valores
        for (int dato : Objects.requireNonNull(matriz)[index]) {
            var datoText = String.format(" %" + SPACING + "s ", dato);

            filaText.append(datoText);
        }

        return filaText;
    }

    /**
     * Método para llenar el encabezado de la matriz.
     *
     * @return Encabezado de la matriz en forma de StringBuilder.
     */
    private StringBuilder llenarEncabezado() {
        // Llenar el encabezado
        var headerFormat = "Filas/Columnas %s";
        var header = new StringBuilder();
        var headerHeaders = new StringBuilder();

        for (int i = 0; i < Objects.requireNonNull(matriz)[0].length; i++) {
            headerHeaders.append(String.format(" %" + SPACING + "d ", i));
        }

        header.append(String.format(headerFormat, headerHeaders));

        return header;
    }

    /**
     * Método para llenar el encabezado de la matriz recibiendo un parámetro adicional (última columna).
     *
     * @param adicional       Parámetro adicional a agregar al encabezado.
     * @param adicionalFormat Formato del parámetro adicional.
     * @return Encabezado de la matriz en forma de StringBuilder.
     */
    private StringBuilder llenarEncabezado(String adicional, String adicionalFormat) {
        StringBuilder header = llenarEncabezado();

        header.append(" ");

        try {
            adicional = String.format(adicionalFormat, adicional);
        } catch (IllegalFormatException ignored) {
        }

        return header.append(adicional);
    }

    /**
     * Método para procesar la suma de filas y columna de la matriz.
     *
     * @return Matriz procesada en forma de String con las sumas de filas y columnas.
     */
    public String procesarSuma() throws NullPointerException {
        var specialSpacing = 14;

        // Llenar el encabezado
        var header = llenarEncabezado("Suma", "%" + specialSpacing + "s").append("\n");

        // Llenar el pie de la matriz con la suma de las columnas
        var footer = String.format("%" + specialSpacing + "s %s %" + specialSpacing + "s", "Suma", getColSums(), "---");

        // Completar la matriz


        // Retornar la matriz
        return handleToString(header.toString(), footer);
    }

    /**
     * Obtiene la suma de las columnas de la matriz
     *
     * @return Suma de las columnas de la matriz
     */
    private String getColSums() {
        StringBuilder colSums = new StringBuilder();
        for (int i = 0; i < Objects.requireNonNull(matriz)[0].length; i++) {
            int sum = 0;
            for (int[] col : matriz) {
                sum += col[i];
            }
            colSums.append(String.format(" %" + SPACING + "d ", sum));
        }

        return colSums.toString();
    }

    /**
     * Obtiene la suma de los elementos de la fila i
     *
     * @param i         Índice de la fila
     * @param dataWidth Ancho o espacio que ocuparán los dentro del String resultante.
     * @return Suma de los elementos de la fila i
     */
    private String getRowSum(int i, int dataWidth) {
        StringBuilder rowSum = new StringBuilder();
        int sum = 0;
        for (int dato : Objects.requireNonNull(matriz)[i]) {
            sum += dato;
        }

        rowSum.append(String.format(" %" + dataWidth + "d ", sum));

        return rowSum.toString();
    }

}

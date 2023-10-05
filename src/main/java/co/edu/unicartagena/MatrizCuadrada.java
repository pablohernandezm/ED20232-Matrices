package co.edu.unicartagena;

import co.edu.unicartagena.Extra.Pointer;

import java.util.Objects;

public class MatrizCuadrada extends Matriz {
    private int sumaDiagonalPrincipal;
    private long productoDiagonalSecundaria;

    /**
     * Constructor de la clase
     *
     * @param matriz Matriz de tipo entero.
     * @throws IllegalArgumentException Si la matriz está vacía.
     */
    public MatrizCuadrada(int[][] matriz) throws IllegalArgumentException {
        super(matriz);
        this.sumaDiagonalPrincipal = 0;
        this.productoDiagonalSecundaria = 1;

        procesarMatriz();
    }

    private void procesarMatriz() {
        for (int i = 0; i < matriz.length; i++) {
            this.sumaDiagonalPrincipal += matriz[i][i];
            this.productoDiagonalSecundaria *= matriz[i][matriz.length - 1 - i];
        }
    }

    /**
     * Obtiene la suma de los elementos de la diagonal principal de la matriz.
     *
     * @return Suma de los elementos de la diagonal principal de la matriz.
     */
    public int getSumaDiagonalPrincipal() {
        return sumaDiagonalPrincipal;
    }

    /**
     * Obtiene el producto de los elementos de la diagonal secundaria de la matriz.
     *
     * @return Producto de los elementos de la diagonal secundaria de la matriz.
     */
    public long getProductoDiagonalSecundaria() {
        return productoDiagonalSecundaria;
    }

    /**
     * Método para obtener la diagonal principal de la matriz en forma de String.
     *
     * @return Diagonal principal de la matriz en forma de String.
     */
    public String getDiagonalPrincipal() {
        StringBuilder str = new StringBuilder();
        str.append(llenarEncabezado()).append("\n");

        for (int i = 0; i < Objects.requireNonNull(matriz).length; i++) {
            str.append(String.format("%" + specialSpacing + "d ", i));

            for (int j = 0; j < matriz[i].length; j++) {
                var datoFormat = " %" + SPACING + "s ";

                if (i != j) {
                    str.append(String.format(datoFormat, 0));
                } else {
                    str.append(String.format(datoFormat, matriz[i][j]));
                }
            }

            str.append("\n");
        }

        return str.toString();
    }

    /**
     * Método para obtener la diagonal secundaria de la matriz en forma de String.
     *
     * @return Diagonal secundaria de la matriz en forma de String.
     */
    public String getDiagonalSecundaria() {
        StringBuilder ds = new StringBuilder();
        ds.append(llenarEncabezado()).append("\n");

        for (int i = 0; i < Objects.requireNonNull(matriz).length; i++) {
            ds.append(String.format("%" + specialSpacing + "d ", i));

            for (int j = 0; j < matriz[i].length; j++) {
                var datoFormat = " %" + SPACING + "s ";

                if (i + j != matriz.length - 1) {
                    ds.append(String.format(datoFormat, 0));
                } else {
                    ds.append(String.format(datoFormat, matriz[i][j]));
                }
            }

            ds.append("\n");
        }

        return ds.toString();
    }

    /**
     * Método para reemplazar los valores de una matriz de acuerdo a una lista de punteros.
     *
     * @param pointers Lista de punteros.
     */
    public void replace(Pointer[] pointers) {
        for (Pointer pointer : pointers) {
            matriz[pointer.getX()][pointer.getY()] = pointer.getValue();
        }
    }

    /**
     * Método para rotar un anillo de la matriz.
     * @param anillo Anillo a rotar.
     * @param direction Dirección de la rotación.
     * @param grados Grados de la rotación.
     */
    public void rotarAnillo(int anillo, String direction, int grados) {
        var n = matriz.length;

        // Guardar las partes superior, derecha, abajo e izquierda del anillo
        int[] top = new int[n - 2 * anillo + 2];
        int[] right = new int[n - 2 * anillo + 2];
        int[] bottom = new int[n - 2 * anillo + 2];
        int[] left = new int[n - 2 * anillo + 2];

        int idx = 0;
        for (int i = anillo - 1; i < n - anillo + 1; i++) {
            top[idx] = matriz[anillo - 1][i];
            right[idx] = matriz[i][n - anillo];
            bottom[idx] = matriz[n - anillo][n - i - 1];
            left[idx] = matriz[n - i - 1][anillo - 1];
            idx++;
        }

        if (direction.equalsIgnoreCase("derecha")) {
            switch (grados) {
                case 90: // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matriz[anillo - 1][i] = left[idx];
                        matriz[i][n - anillo] = top[idx];
                        matriz[n - anillo][n - i - 1] = right[idx];
                        matriz[n - i - 1][anillo - 1] = bottom[idx];
                        idx++;
                    }
                    break;
                case 180:
                    // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matriz[anillo - 1][i] = bottom[idx];
                        matriz[i][n - anillo] = left[idx];
                        matriz[n - anillo][n - i - 1] = top[idx];
                        matriz[n - i - 1][anillo - 1] = right[idx];
                        idx++;
                    }
                    break;
                case 270:
                    // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matriz[anillo - 1][i] = right[idx];
                        matriz[i][n - anillo] = bottom[idx];
                        matriz[n - anillo][n - i - 1] = left[idx];
                        matriz[n - i - 1][anillo - 1] = top[idx];
                        idx++;
                    }
            }
        }

        if (direction.equalsIgnoreCase("izquierda")) {
            switch (grados) {
                case 90: // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matriz[anillo - 1][i] = right[idx];
                        matriz[i][n - anillo] = bottom[idx];
                        matriz[n - anillo][n - i - 1] = left[idx];
                        matriz[n - i - 1][anillo - 1] = top[idx];
                        idx++;
                    }
                    break;
                case 180:
                    // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matriz[anillo - 1][i] = bottom[idx];
                        matriz[i][n - anillo] = left[idx];
                        matriz[n - anillo][n - i - 1] = top[idx];
                        matriz[n - i - 1][anillo - 1] = right[idx];
                        idx++;
                    }
                    break;
                case 270:
                    // Restaurar las partes en la matriz
                    idx = 0;
                    for (int i = anillo - 1; i < n - anillo + 1; i++) {
                        matriz[anillo - 1][i] = left[idx];
                        matriz[i][n - anillo] = top[idx];
                        matriz[n - anillo][n - i - 1] = right[idx];
                        matriz[n - i - 1][anillo - 1] = bottom[idx];
                        idx++;
                    }
            }
        }
    }

}

package co.edu.unicartagena;

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


}

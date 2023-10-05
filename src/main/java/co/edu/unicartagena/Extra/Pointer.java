package co.edu.unicartagena.Extra;

/**
 * Clase para representar un puntero en una matriz.
 */
public class Pointer {
    /**
     * Valor en la posición del puntero.
     */
    private int value;

    /**
     * Posición en 'x' del puntero.
     */
    private final int x;

    /**
     * Posición en 'y' del puntero.
     */
    private final int y;

    /**
     * Constructor de la clase.
     * @param value Valor en la posición del puntero.
     * @param x Posición en 'x' del puntero.
     * @param y Posición en 'y' del puntero.
     */
    public Pointer(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;

        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("La posición %s no puede ser negativa".formatted(x<0?"x":"y"));
        }
    }

    /**
     * Obtiene el valor en la posición del puntero.
     * @return Valor en la posición del puntero.
     */
    public int getValue() {
        return value;
    }

    /**
     * Obtiene el valor en la posición del puntero.
     * @param value Valor en la posición del puntero.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Obtiene la posición 'x' del puntero.
     * @return Posición 'x' del puntero.
     */
    public int getX(){ return x; }

    /**
     * Obtiene la posición 'y' del puntero.
     * @return Posición 'y' del puntero.
     */
    public int getY(){ return y; }
}
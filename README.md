# Taller 4 - Matrices
## Equipo de trabajo
- [Pablo José Hernández meléndez](https://github.com/pablohernandezm)
- [Jhoy Luis Castro Casanova](https://www.linkedin.com/in/jhoy-luis-castro-casanova-061142249/)
- [Gabriel David Lara Montiel](https://www.linkedin.com/in/gabriel-david-lara-montiel-367933288/)


## Suma de filas y columnas
Diseñe un algoritmo que lea una matriz de ```n x m``` (n y m generados aleatoriamente en el rango de 5 y 10) y calcule la suma de los elementos de cada fila y cada columna. Por ejemplo:

| Filas/Columnas | 0  | 1  | 2  | Suma |
|----------------|----|----|----|------|
| 0              | 5  | 5  | 8  | 18   |
| 1              | 9  | 6  | 7  | 22   |
| Suma           | 14 | 11 | 15 | ---  |

### Restricciones
- Los valores de ``m`` y ``n`` deben ser generados aleatoriamente entre 5 y 10. ✅
- El programa debe ser capaz de generar las sumas de filas y columnas ✅

## Operaciones aritméticas en una matriz
Diseñe un algoritmo que cumpla con las siguientes características:
- La matriz debe ser cuadrada. ✅
- Se debe solicitar al usuario la dimensión de la matriz. ✅
- La matriz debe contener únicamente números enteros **positivos** o **negativos**, el número ``0`` no está incluido. ✅
- Se debe imprimir: 
  - La suma de los elementos de la diagonal principal. ✅
  - La multiplicación de los elementos de la diagonal secundaria. ❌
  - La división entre la suma y la multiplicación anteriores. ❌

## Ordenando los elementos de una matriz
Dada una matriz cuadrada ``n x n`` se debe evaluar una solución de acuerdo a las siguientes restricciones:
- El usuario debe ingresar el tamaño de la matriz. ❌
- Cada posición de la matriz debe almacenar un número generado aleatoriamente entre 25 y 75. ❌
- Se deben imprimir:
    - La matriz original. ❌
    - Los elementos por debajo de la diagonal principal, ordenados de menor a mayor. ❌ 
    - Los elementos por encima de la diagonal principal, ordenados de mayor a menor. ❌

## Rotación de matrices
Diseñe un algoritmo que permita la creación de una matriz ``n x n`` que cumpla con las siguientes restricciones:
- El usuario debe ingresar el tamaño de la matriz. ❌
- El tamaño de la matriz debe ser mayor o igual a 2 y menor o igual a 10. ❌
- La matriz debe llenarse con valores aleatorios del 5 al 25. ❌

Note que al organizar la matriz cuadrada se ve un patrón de anillos únicos en la matriz (``x``, ``y``, ``z``).

|     |   |   |   |   |   |   |     |
|-----|---|---|---|---|---|---|-----|
|     | x | x | x | x | x | x |     |
|     | x | y | y | y | y | x |     |
|     | x | y | z | z | y | x |     |
|     | x | y | z | z | y | x |     |
|     | x | y | y | y | y | x |     |
|     | x | x | x | x | x | x |     |
|     |   |   |   |   |   |   |     |

Entonces, el algoritmo debe:
- Preguntar al usuario qué anillo quiere rotar (1...5). ❌
- Preguntar al usuario en qué dirección quiere rotar el anillo (izquierda o derecha). ❌
- Preguntar al usuario a cuántos grados quiere rotar el anillo (90, 180, 270). ❌
- Por cada rotación imprimir en pantalla la matriz resultante. ❌
- Describir matemáticamente la complejidad de la solución. ❌
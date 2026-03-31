import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {


        List<Integer> numeros = Arrays.asList(2, 5, 8, 1, 9, 12, 7, 7, 10, 15, 4);
        List<String> nombres = Arrays.asList("Carlos", "Ana", "Francisco", "Luis", "Florencia");
        List<String> palabras = Arrays.asList("sol", "luna", "estrella", "cielo", "mar");

        // 1. Filtrar números pares: Utiliza filter para obtener solo los números pares en una nueva lista
        List<Integer> pares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("1. Pares: " + pares);

        // 2. Transformar una lista de nombres a mayúsculas: Usa map para convertir cada nombre
        List<String> mayusculas = nombres.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("2. Mayúsculas: " + mayusculas);

        // 3. Ordenar una lista de números: Usa sorted para ordenar de menor a mayor
        List<Integer> ordenados = numeros.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("3. Ordenados: " + ordenados);

        // 4. Contar elementos mayores a un valor dado: Usa filter y count para valores mayores que 7
        long mayoresQueSiete = numeros.stream()
                .filter(n -> n > 7)
                .count();
        System.out.println("4. Mayores que 7: " + mayoresQueSiete);

        // 5. Obtener los primeros 5 elementos de una lista: Usa limit
        List<Integer> primerosCinco = numeros.stream()
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("5. Primeros 5: " + primerosCinco);

        // 6. Convertir una lista de palabras en su longitud: Usa map
        List<Integer> longitudes = palabras.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("6. Longitud de palabras: " + longitudes);

        // 7. Concatenar nombres con una separación: Usa reduce para concatenarlos separados por comas
        String nombresConcatenados = nombres.stream()
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        System.out.println("7. Nombres concatenados: " + nombresConcatenados);

        // 8. Eliminar duplicados de una lista: Usa distinct
        List<Integer> sinDuplicados = numeros.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("8. Sin duplicados: " + sinDuplicados);

        // 9. Obtener los 3 números más grandes de una lista: Usa sorted y limit
        List<Integer> tresMasGrandes = numeros.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("9. Los 3 más grandes: " + tresMasGrandes);

        // 10. Agrupar palabras por su longitud: Usa Collectors.groupingBy
        Map<Integer, List<String>> palabrasPorLongitud = palabras.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("10. Palabras por longitud: " + palabrasPorLongitud);

        // 11. Encontrar el producto de todos los números de una lista: Usa reduce
        int producto = numeros.stream()
                .reduce(1, (a, b) -> a * b);
        System.out.println("11. Producto de los números: " + producto);

        // 12. Obtener el nombre más largo de una lista: Usa reduce
        String nombreMasLargo = nombres.stream()
                .reduce((n1, n2) -> n1.length() > n2.length() ? n1 : n2)
                .orElse("");
        System.out.println("12. Nombre más largo: " + nombreMasLargo);

        // 13. Convertir una lista de enteros en una cadena separada por guiones: Usa map y Collectors.joining
        String numerosConGuiones = numeros.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("-"));
        System.out.println("13. Números con guiones: " + numerosConGuiones);

        // 14. Agrupar una lista de números en pares e impares: Usa Collectors.partitioningBy
        // true representa los pares, false los impares.
        Map<Boolean, List<Integer>> paresEImpares = numeros.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("14. Pares e impares: " + paresEImpares);

        // 15. Obtener la suma de los cuadrados de los números impares: Usa filter, map y reduce
        int sumaCuadradosImpares = numeros.stream()
                .filter(n -> n % 2 != 0)
                .map(n -> n * n)
                .reduce(0, Integer::sum);
        System.out.println("15. Suma de los cuadrados de los impares: " + sumaCuadradosImpares);
    }
}
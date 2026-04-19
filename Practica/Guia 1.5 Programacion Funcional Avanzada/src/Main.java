import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Producto> inventario = cargarProductos();

        //1 Filtrado y Transformación
        //○ Obtener una lista con los nombres y precios de los productos de la categoría
        //"Electrónica" cuyo precio sea mayor a 1000, ordenados de mayor a menor
        //precio

        List<String> productosElectronicos = inventario.stream()
                // 1. Filtramos por categoría y precio
                .filter(p -> p.getCategoria().equals("Electrónica") && p.getPrecio() > 1000)
                // 2. Ordenamos por precio de mayor a menor
                .sorted(Comparator.comparing(Producto::getPrecio).reversed())
                // 3. Transformamos el objeto Producto a un String con el formato deseado
                .map(p -> p.getNombre() + " - $" + p.getPrecio())
                // 4. Recolectamos todo en una nueva lista
                .collect(Collectors.toList());
        System.out.println(productosElectronicos);

        //2 Reducción de Datos
        //○ Calcular el precio promedio de los productos de la categoría "Hogar", pero
        //solo considerando aquellos con stock disponible

        double precioPromedio = inventario.stream()
                .filter(p -> p.getCategoria().equals("Hogar") && p.getStock() > 0)
                .mapToDouble(Producto::getPrecio)
                .average()
                .orElse(0.0);

        //3 Producto mas caro
        //○ Obtener el producto más caro de cada categoría y devolver un mapa con la
        //categoría como clave y el producto más caro como valor.

        Map<String, Optional<Producto>> productoMasCaro = inventario.stream()
                .collect(Collectors.groupingBy(
                        Producto::getCategoria,
                        Collectors.maxBy(Comparator.comparing(Producto::getPrecio))
                ));
        System.out.println(productoMasCaro);

        //4 Uso de Optionals
        //○ Encontrar el producto de la categoría "Deportes" con stock mayor a 10
        //unidades, obtener su nombre en minúsculas y devolverlo dentro de un
        //Optional. Mostrarlo o si no existe, mostrar “Producto Inexistente”

        Optional<String> productoDeportivo = inventario.stream().
                filter(p->p.getCategoria().equals("Deportes") && p.getStock()>10)
                .map(p->p.getNombre().toLowerCase())
                .findFirst();
        System.out.println(productoDeportivo.orElse("Producto Inexistente"));

        //5 Producto Mas Barato
        //○ Encontrar el producto mas barato calculando el valor total de todas las
        //unidades en stock (Precio * stock). Devolver un Opcional con el producto. En
        //caso de que no exista, lanzar una excepción.

        Producto productoMasBarato = inventario.stream()
                .min(Comparator.comparingDouble(p -> p.getStock() * p.getPrecio()))
                .orElseThrow(RuntimeException::new);
        System.out.println(productoMasBarato.getNombre());

        //6 



    }

    public static List<Producto> cargarProductos() {
        return List.of(
                new Producto("Laptop", 1500, "Electrónica", 5),
                new Producto("Smartphone", 800, "Electrónica", 10),
                new Producto("Televisor", 1200, "Electrónica", 3),
                new Producto("Heladera", 2000, "Hogar", 2),
                new Producto("Microondas", 500, "Hogar", 8),
                new Producto("Silla", 150, "Muebles", 12),
                new Producto("Mesa", 300, "Muebles", 7),
                new Producto("Zapatillas", 100, "Deportes", 15),
                new Producto("Pelota", 50, "Deportes", 20),
                new Producto("Bicicleta", 500, "Deportes", 5),
                new Producto("Libro", 30, "Librería", 50),
                new Producto("Cuaderno", 10, "Librería", 100),
                new Producto("Lámpara", 80, "Hogar", 30),
                new Producto("Cafetera", 250, "Hogar", 6),
                new Producto("Auriculares", 120, "Electrónica", 14),
                new Producto("Teclado", 90, "Electrónica", 9),
                new Producto("Mouse", 60, "Electrónica", 18),
                new Producto("Monitor", 700, "Electrónica", 4),
                new Producto("Cama", 800, "Muebles", 2),
                new Producto("Sofá", 1000, "Muebles", 3),
                new Producto("Espejo", 120, "Hogar", 12),
                new Producto("Ventilador", 150, "Hogar", 7),
                new Producto("Patines", 180, "Deportes", 5),
                new Producto("Raqueta", 220, "Deportes", 6),
                new Producto("Taza", 15, "Hogar", 40)

        );
    }
}
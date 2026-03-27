import Manager.GestorPagos;
import Model.*;
import Exception.PagoFallidoException;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        GestorPagos<Pago> miGestor = new GestorPagos<>();

        try {
            // Tarjeta válida
            miGestor.agregarPago(new TarjetaCredito(1500, "Juan Perez", "1234-5678", LocalDate.of(2025, 12, 31)));

            // Tarjeta vencida (fallará al procesar)
            miGestor.agregarPago(new TarjetaCredito(3000, "Ana Lopez", "9876-5432", LocalDate.of(2022, 1, 1)));

            // PayPal
            miGestor.agregarPago(new PayPal(800, "Carlos Gomez", "carlos@gmail.com"));

            // Efectivo
            miGestor.agregarPago(new Efectivo(500, "Maria Torres"));

            // Criptomoneda
            miGestor.agregarPago(new Criptomoneda(2000, "Luis Diaz", "1A2B3C4D5E6F7G8H9I0J1K2L3M4N5O6P", "Bitcoin"));

        } catch (PagoFallidoException e) {
            System.out.println("Error al registrar pago: " + e.getMessage());
        }

        System.out.println("--- PROCESANDO PAGOS ---");
        miGestor.procesarTodosLosPagos();

        System.out.println("\n--- EXPORTANDO A JSON ---");
        System.out.println(miGestor.exportarJSON());
    }
}
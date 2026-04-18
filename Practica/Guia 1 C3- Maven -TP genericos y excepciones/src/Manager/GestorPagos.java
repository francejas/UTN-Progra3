package Manager;

import Exception.PagoFallidoException;
import Model.Pago;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class GestorPagos<T extends Pago> {
    private List<T> listaPagos;

    public GestorPagos() {
        this.listaPagos = new ArrayList<>();
    }

    public void agregarPago(T pago) {
        listaPagos.add(pago);
    }

    public void procesarTodosLosPagos() {
        for (T pago : listaPagos) {
            try {
                pago.procesar();
            } catch (PagoFallidoException e) {
                System.err.println("Fallo al procesar el pago de " + pago.getTitular() + ": " + e.getMessage());
            }
        }
    }

    public String exportarJSON() {
        JSONArray array = new JSONArray();
        for (T pago : listaPagos) {
            array.put(pago.toJSONObject());
        }
        return array.toString(2); // indentado con 2 espacios
    }

    public List<T> getListaPagos() {
        return listaPagos;
    }
}

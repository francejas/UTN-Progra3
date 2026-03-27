package Model;
import Exception.PagoFallidoException;
import org.json.JSONObject;
import java.time.LocalDate;


public class TarjetaCredito extends PagoFisico{
    private String numeroTarjeta;
    private LocalDate fechaVencimiento;

    public TarjetaCredito(double monto, String titular, String numeroTarjeta, LocalDate fechaVencimiento) throws PagoFallidoException {
        super(monto, titular);
        this.numeroTarjeta = numeroTarjeta;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public void procesar() throws PagoFallidoException {
        if(fechaVencimiento.isBefore(LocalDate.now())){
            throw new PagoFallidoException("Error: La tarjeta de credito esta vencida.");
        }
        System.out.println("Procesando pago con tarjeta por $" +monto);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject obj = super.toJSONObject();
        obj.put("numeroTarjeta", numeroTarjeta);
        obj.put("fechaVencimiento", fechaVencimiento.toString());
        return obj;
    }
}

package Model;

import Exception.PagoFallidoException;
import org.json.JSONObject;

public abstract class Pago {
    protected double monto;
    protected String titular;

    public Pago(double monto, String titular) throws PagoFallidoException {
        if (monto <= 0) {
            throw new PagoFallidoException("El monto debe ser mayor a 0.");
        }
        this.monto = monto;
        this.titular = titular;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public abstract void procesar() throws PagoFallidoException;

    // Campos base comunes a todos los pagos
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("tipo", this.getClass().getSimpleName());
        obj.put("titular", titular);
        obj.put("monto", monto);
        return obj;
    }
}

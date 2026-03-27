package Model;
import Exception.PagoFallidoException;
import org.json.JSONObject;

public class PayPal extends PagoVirtual{

    private String emailCuenta;

    public PayPal(double monto, String titular, String emailCuenta) throws PagoFallidoException {
        super(monto, titular);
        this.emailCuenta = emailCuenta;
    }

    public String getEmailCuenta() {
        return emailCuenta;
    }

    public void setEmailCuenta(String emailCuenta) {
        this.emailCuenta = emailCuenta;
    }

    @Override
    public void procesar() throws PagoFallidoException {
        if (emailCuenta==null || !emailCuenta.contains("@")){
            throw new PagoFallidoException("Error: La cuenta de PayPal no tiene un formato de correo válido.");
        }
        System.out.println("Procesando pago vía PayPal por $" + monto + " desde la cuenta " + emailCuenta);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject obj = super.toJSONObject();
        obj.put("emailCuenta", emailCuenta);
        return obj;
    }
}

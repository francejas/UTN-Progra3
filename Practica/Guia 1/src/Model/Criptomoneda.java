package Model;
import Exception.PagoFallidoException;
import org.json.JSONObject;

public class Criptomoneda extends PagoVirtual{
    private String direccionBilletera;
    private String tipoCripto;


    public Criptomoneda(double monto, String titular, String direccionBilletera, String tipoCripto) throws PagoFallidoException {
        super(monto, titular);
        this.direccionBilletera = direccionBilletera;
        this.tipoCripto = tipoCripto;
    }

    public String getDireccionBilletera() {
        return direccionBilletera;
    }

    public void setDireccionBilletera(String direccionBilletera) {
        this.direccionBilletera = direccionBilletera;
    }

    public String getTipoCripto() {
        return tipoCripto;
    }

    public void setTipoCripto(String tipoCripto) {
        this.tipoCripto = tipoCripto;
    }

    @Override
    public void procesar() throws PagoFallidoException {
        if(direccionBilletera==null||direccionBilletera.length()<20){
            throw new PagoFallidoException("Error: La dirección de la billetera cripto es inválida o demasiado corta.");
        }
        System.out.println("Procesando pago de $" + monto + " usando " + tipoCripto + " hacia la billetera " + direccionBilletera);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject obj = super.toJSONObject();
        obj.put("tipoCripto", tipoCripto);
        obj.put("direccionBilletera", direccionBilletera);
        return obj;
    }
}

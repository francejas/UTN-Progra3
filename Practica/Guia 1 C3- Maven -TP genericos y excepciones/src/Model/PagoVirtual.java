package Model;
import Exception.PagoFallidoException;
public abstract class PagoVirtual extends Pago{
    public PagoVirtual(double monto, String titular) throws PagoFallidoException {
        super(monto, titular);
    }


}

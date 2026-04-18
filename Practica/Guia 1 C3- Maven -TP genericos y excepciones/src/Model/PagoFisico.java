package Model;
import Exception.PagoFallidoException;

public abstract class PagoFisico extends Pago{
    public PagoFisico(double monto, String titular) throws PagoFallidoException {
        super(monto, titular);
    }


}

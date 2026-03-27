package Model;
import Exception.PagoFallidoException;

public class Efectivo extends PagoFisico{

    public Efectivo(double monto, String titular) throws PagoFallidoException {
        super(monto, titular);
    }

    @Override
    public void procesar() throws PagoFallidoException {
        System.out.println("Procesando pago en efectivo por $" + monto + " a nombre de " + titular);
    }
}

package co.edu.udea.certificacion.prueba.tasks;

import net.serenitybdd.screenplay.Tasks;
import co.edu.udea.certificacion.prueba.interactions.ConfirmarLaCuenta;
import co.edu.udea.certificacion.prueba.interactions.ObtenerElEnlaceDeVerificacion;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class VerificarCuentaWeb implements Task {

    private final CuentaPrueba cuenta;
    private final boolean permitirReenvio;

    public VerificarCuentaWeb(CuentaPrueba cuenta, boolean permitirReenvio) {
        this.cuenta = cuenta;
        this.permitirReenvio = permitirReenvio;
    }

    @Override
    @Step("{0} verifica su cuenta con el correo recibido")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ObtenerElEnlaceDeVerificacion.para(cuenta, permitirReenvio),
                ConfirmarLaCuenta.de(cuenta));
    }

    /** Para los casos que comprueban el registro: exige el correo sin pedir otro. */
    public static VerificarCuentaWeb de(CuentaPrueba cuenta) {
        return Tasks.instrumented(VerificarCuentaWeb.class, cuenta, false);
    }

    /** Para las precondiciones: permite un único reenvío por el formulario visible. */
    public static VerificarCuentaWeb paraPrepararCuenta(CuentaPrueba cuenta) {
        return Tasks.instrumented(VerificarCuentaWeb.class, cuenta, true);
    }
}

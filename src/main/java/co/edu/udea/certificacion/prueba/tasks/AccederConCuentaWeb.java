package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.ConfirmarElIngreso;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class AccederConCuentaWeb implements Task {

    private final DatosEscenario datos;
    private final CuentaPrueba cuenta;

    public AccederConCuentaWeb(DatosEscenario datos, CuentaPrueba cuenta) {
        this.datos = datos;
        this.cuenta = cuenta;
    }

    @Override
    @Step("{0} accede a su panel")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                IniciarSesionWeb.con(cuenta.email, cuenta.clave),
                ConfirmarElIngreso.de(datos, cuenta));
    }

    public static AccederConCuentaWeb de(DatosEscenario datos, CuentaPrueba cuenta) {
        return Tasks.instrumented(AccederConCuentaWeb.class, datos, cuenta);
    }
}

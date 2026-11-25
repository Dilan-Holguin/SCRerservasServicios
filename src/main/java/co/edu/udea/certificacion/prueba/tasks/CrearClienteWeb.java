package co.edu.udea.certificacion.prueba.tasks;

import net.serenitybdd.screenplay.Tasks;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class CrearClienteWeb implements Task {

    private final DatosEscenario datos;
    private final boolean adicional;

    public CrearClienteWeb(DatosEscenario datos, boolean adicional) {
        this.datos = datos;
        this.adicional = adicional;
    }

    @Override
    @Step("{0} crea y verifica un cliente")
    public <T extends Actor> void performAs(T actor) {
        CuentaPrueba cuenta = datos.nuevoCliente(adicional);
        actor.attemptsTo(
                RegistrarCuentaWeb.con(cuenta),
                VerificarCuentaWeb.paraPrepararCuenta(cuenta),
                AccederConCuentaWeb.de(datos, cuenta));
    }

    public static CrearClienteWeb principal(DatosEscenario datos) {
        return Tasks.instrumented(CrearClienteWeb.class, datos, false);
    }

    public static CrearClienteWeb adicional(DatosEscenario datos) {
        return Tasks.instrumented(CrearClienteWeb.class, datos, true);
    }
}

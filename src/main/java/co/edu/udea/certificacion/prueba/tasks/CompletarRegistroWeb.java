package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirElFormularioDeRegistro;
import co.edu.udea.certificacion.prueba.interactions.CerrarSesionSiEstaAbierta;
import co.edu.udea.certificacion.prueba.interactions.LlenarElFormularioDeRegistro;
import co.edu.udea.certificacion.prueba.interactions.PulsarCrearCuenta;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.models.FormularioRegistro;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

/** Camino de excepción: envía el formulario y NO espera la confirmación. */
public class CompletarRegistroWeb implements Task {

    private final CuentaPrueba cuenta;
    private final String defecto;

    public CompletarRegistroWeb(CuentaPrueba cuenta, String defecto) {
        this.cuenta = cuenta;
        this.defecto = defecto;
    }

    @Override
    @Step("{0} intenta registrarse con datos inválidos")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                CerrarSesionSiEstaAbierta.enElMenu(),
                AbrirElFormularioDeRegistro.deCuenta(),
                LlenarElFormularioDeRegistro.con(FormularioRegistro.conDefecto(cuenta, defecto)),
                PulsarCrearCuenta.enElFormulario());
    }

    public static CompletarRegistroWeb con(CuentaPrueba cuenta, String defecto) {
        return Tasks.instrumented(CompletarRegistroWeb.class, cuenta, defecto);
    }
}

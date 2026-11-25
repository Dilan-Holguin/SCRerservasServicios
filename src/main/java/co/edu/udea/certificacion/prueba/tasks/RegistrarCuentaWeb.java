package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirElFormularioDeRegistro;
import co.edu.udea.certificacion.prueba.interactions.CerrarSesionSiEstaAbierta;
import co.edu.udea.certificacion.prueba.interactions.ConfirmarElRegistro;
import co.edu.udea.certificacion.prueba.interactions.LlenarElFormularioDeRegistro;
import co.edu.udea.certificacion.prueba.interactions.PulsarCrearCuenta;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.models.FormularioRegistro;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class RegistrarCuentaWeb implements Task {

    private final CuentaPrueba cuenta;

    public RegistrarCuentaWeb(CuentaPrueba cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    @Step("{0} registra una cuenta desde el formulario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                CerrarSesionSiEstaAbierta.enElMenu(),
                AbrirElFormularioDeRegistro.deCuenta(),
                LlenarElFormularioDeRegistro.con(FormularioRegistro.completo(cuenta)),
                PulsarCrearCuenta.enElFormulario(),
                ConfirmarElRegistro.de(cuenta));
    }

    public static RegistrarCuentaWeb con(CuentaPrueba cuenta) {
        return Tasks.instrumented(RegistrarCuentaWeb.class, cuenta);
    }
}

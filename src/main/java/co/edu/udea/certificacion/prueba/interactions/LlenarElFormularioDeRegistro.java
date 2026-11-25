package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.FormularioRegistro;
import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

public class LlenarElFormularioDeRegistro implements Interaction {

    private final FormularioRegistro formulario;

    public LlenarElFormularioDeRegistro(FormularioRegistro formulario) {
        this.formulario = formulario;
    }

    @Override
    @Step("{0} llena el formulario de registro")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(ReservaYa.rol(formulario.rol)),
                Enter.theValue(formulario.nombre).into(ReservaYa.NOMBRE),
                Enter.theValue(formulario.email).into(ReservaYa.EMAIL),
                EscribirClave.sinExponerla(formulario.clave));
        if (formulario.nombreServicio != null) {
            actor.attemptsTo(Enter.theValue(formulario.nombreServicio).into(ReservaYa.NOMBRE_SERVICIO));
        }
        if (formulario.elegirCategoria) {
            actor.attemptsTo(Esperar.presente(ReservaYa.PRIMERA_CATEGORIA),
                    SelectFromOptions.byIndex(1).from(ReservaYa.CATEGORIA));
        }
        if (formulario.duracion != null) {
            actor.attemptsTo(Enter.theValue(formulario.duracion).into(FlujosUi.campoRegistro("servicios[0].duracionMinutos")));
        }
        if (formulario.capacidad != null) {
            actor.attemptsTo(Enter.theValue(formulario.capacidad).into(FlujosUi.campoRegistro("servicios[0].capacidad")));
        }
        PausaDeDemostracion.hacer();
    }

    public static LlenarElFormularioDeRegistro con(FormularioRegistro formulario) {
        return Tasks.instrumented(LlenarElFormularioDeRegistro.class, formulario);
    }
}

package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class AbrirElFormularioDeIngreso implements Interaction {

    @Override
    @Step("{0} abre el formulario de ingreso")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/ingresar"));
        PausaDeDemostracion.hacer();
    }

    public static AbrirElFormularioDeIngreso deSesion() {
        return Tasks.instrumented(AbrirElFormularioDeIngreso.class);
    }
}

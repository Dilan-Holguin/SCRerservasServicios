package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class AbrirElPanelProtegido implements Interaction {

    @Override
    @Step("{0} intenta abrir el panel del cliente sin sesión")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/panel/cliente"), Esperar.visible(ReservaYa.INGRESAR));
        PausaDeDemostracion.hacer();
    }

    public static AbrirElPanelProtegido delCliente() {
        return Tasks.instrumented(AbrirElPanelProtegido.class);
    }
}

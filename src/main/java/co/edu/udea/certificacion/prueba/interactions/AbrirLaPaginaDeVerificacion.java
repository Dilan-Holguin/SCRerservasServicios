package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class AbrirLaPaginaDeVerificacion implements Interaction {

    private final String token;

    public AbrirLaPaginaDeVerificacion(String token) {
        this.token = token;
    }

    @Override
    @Step("{0} abre la página de verificación del enlace")
    public <T extends Actor> void performAs(T actor) {
        BrowseTheWeb.as(actor).getDriver().get(ConfiguracionUi.webUrl() + "/verificacion?token=" + token);
        PausaDeDemostracion.hacer();
    }

    public static AbrirLaPaginaDeVerificacion conToken(String token) {
        return Tasks.instrumented(AbrirLaPaginaDeVerificacion.class, token);
    }
}

package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public final class AbrirVerificacionWeb {
    private AbrirVerificacionWeb() { }
    public static Performable conToken(String token) {
        return Task.where("{0} abre la página de verificación del enlace guardado", actor ->
                BrowseTheWeb.as(actor).getDriver().get(ConfiguracionUi.webUrl() + "/verificacion?token=" + token));
    }
}

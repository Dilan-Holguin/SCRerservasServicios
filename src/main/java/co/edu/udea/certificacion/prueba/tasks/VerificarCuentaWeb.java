package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.LeerCorreoVerificacion;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class VerificarCuentaWeb {
    private VerificarCuentaWeb() { }
    public static Performable de(CuentaPrueba cuenta) {
        return Task.where("{0} verifica su cuenta con el correo recibido", actor -> {
            actor.attemptsTo(LeerCorreoVerificacion.de(cuenta.email));
            String token = actor.recall(LeerCorreoVerificacion.TOKEN);
            try {
                BrowseTheWeb.as(actor).getDriver().get(ConfiguracionUi.webUrl() + "/verificacion?token=" + token);
                actor.attemptsTo(WaitUntil.the(ReservaYa.CUENTA_VERIFICADA, isVisible()).forNoMoreThan(15).seconds());
                cuenta.verificada = true;
            } finally { actor.forget(LeerCorreoVerificacion.TOKEN); }
        });
    }
}

package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public final class SolicitarReenvioVerificacionWeb {
    private SolicitarReenvioVerificacionWeb() { }
    public static Performable paraPreparacion(String email) {
        return Task.where("{0} solicita una vez el reenvío para completar los datos del escenario",
                Open.url(ConfiguracionUi.webUrl() + "/verificacion"),
                WaitUntil.the(ReservaYa.EMAIL, isVisible()).forNoMoreThan(15).seconds(),
                Enter.theValue(email).into(ReservaYa.EMAIL),
                Click.on(ReservaYa.REENVIAR_VERIFICACION),
                WaitUntil.the(ReservaYa.RESPUESTA_REENVIO, isVisible()).forNoMoreThan(15).seconds());
    }
}

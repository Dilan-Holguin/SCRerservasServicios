package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotVisible;

public final class CerrarSesionWeb {
    private CerrarSesionWeb() { }
    public static Performable siExiste() {
        return Task.where("{0} cierra su sesión mediante el menú", actor -> {
            if (ReservaYa.CERRAR_SESION.resolveFor(actor).isVisible()) actor.attemptsTo(
                    Click.on(ReservaYa.CERRAR_SESION),
                    WaitUntil.the(ReservaYa.CERRAR_SESION, isNotVisible()).forNoMoreThan(15).seconds());
        });
    }
}

package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public final class ReservarTurnoWeb {
    private ReservarTurnoWeb() { }
    public static Performable para(int servicio, String fecha, String hora) {
        return Task.where("{0} reserva un turno del servicio",
                Open.url(ConfiguracionUi.webUrl() + "/servicios/" + servicio + "?desde=" + fecha + "&hasta=" + fecha),
                WaitUntil.the(ReservaYa.turno(hora), isClickable()).forNoMoreThan(15).seconds(),
                Click.on(ReservaYa.turno(hora)));
    }
}

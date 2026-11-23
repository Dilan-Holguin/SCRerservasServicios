package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.FijarHora;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class CrearAgendaWeb {
    private CrearAgendaWeb() { }
    public static Performable para(int servicio, int dia, String desde, String hasta) {
        return Task.where("{0} publica su agenda semanal",
                Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=agenda"),
                WaitUntil.the(ReservaYa.SELECTOR_SERVICIO, isVisible()).forNoMoreThan(15).seconds(),
                SelectFromOptions.byValue(String.valueOf(servicio)).from(ReservaYa.SELECTOR_SERVICIO),
                Click.on(ReservaYa.dia(dia)), FijarHora.a(ReservaYa.HORA_INICIO, desde), FijarHora.a(ReservaYa.HORA_FIN, hasta),
                Click.on(ReservaYa.CREAR_BLOQUES));
    }
}

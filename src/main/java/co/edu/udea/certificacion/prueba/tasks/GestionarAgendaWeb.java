package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.FijarHora;
import co.edu.udea.certificacion.prueba.userinterfaces.*;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.util.List;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public final class GestionarAgendaWeb {
    private GestionarAgendaWeb() { }
    public static Performable abrir(int servicio) {
        return Task.where("{0} abre la agenda de su servicio",
                Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=agenda"),
                WaitUntil.the(ReservaYa.SELECTOR_SERVICIO, isVisible()).forNoMoreThan(15).seconds(),
                SelectFromOptions.byValue(String.valueOf(servicio)).from(ReservaYa.SELECTOR_SERVICIO));
    }
    public static Performable crear(int servicio, List<Integer> dias, String desde, String hasta) {
        return Task.where("{0} publica bloques en los días elegidos", actor -> {
            actor.attemptsTo(abrir(servicio));
            for (int dia : dias) actor.attemptsTo(Click.on(ReservaYa.dia(dia)));
            actor.attemptsTo(FijarHora.a(ReservaYa.HORA_INICIO, desde), FijarHora.a(ReservaYa.HORA_FIN, hasta),
                    Click.on(ReservaYa.CREAR_BLOQUES));
        });
    }
    public static Performable editar(String desde, String hasta) {
        return Task.where("{0} edita el bloque original",
                WaitUntil.the(FlujosUi.EDITAR_BLOQUE, isClickable()).forNoMoreThan(15).seconds(),
                Click.on(FlujosUi.EDITAR_BLOQUE),
                FijarHora.a(FlujosUi.EDICION_INICIO, desde), FijarHora.a(FlujosUi.EDICION_FIN, hasta),
                Click.on(FlujosUi.boton("Guardar cambios")));
    }
    public static Performable eliminar() {
        return Task.where("{0} solicita eliminar el bloque original",
                WaitUntil.the(FlujosUi.ELIMINAR_BLOQUE, isClickable()).forNoMoreThan(15).seconds(),
                Click.on(FlujosUi.ELIMINAR_BLOQUE));
    }
    public static Performable resolverDialogo(String accion) {
        return Task.where("{0} decide sobre las reservas afectadas: " + accion,
                WaitUntil.the(FlujosUi.modal(accion), isClickable()).forNoMoreThan(15).seconds(),
                Click.on(FlujosUi.modal(accion)));
    }
}

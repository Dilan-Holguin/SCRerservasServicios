package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.userinterfaces.*;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public final class GestionarRecursosWeb {
    private GestionarRecursosWeb() { }
    public static Performable abrir() {
        return Task.where("{0} abre la sección de recursos",
                Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=recursos"),
                WaitUntil.the(FlujosUi.RECURSO_NOMBRE, isVisible()).forNoMoreThan(15).seconds());
    }
    public static Performable crear(String nombre, boolean conTipo) {
        return Task.where("{0} publica el recurso", completar(nombre, conTipo), Click.on(FlujosUi.boton("Crear recurso")));
    }
    public static Performable completar(String nombre, boolean conTipo) {
        return Task.where("{0} registra un recurso mediante el formulario", actor -> {
            actor.attemptsTo(abrir(), Enter.theValue(nombre).into(FlujosUi.RECURSO_NOMBRE));
            if (conTipo) actor.attemptsTo(
                    WaitUntil.the(FlujosUi.RECURSO_TIPO_CARGADO, isPresent()).forNoMoreThan(15).seconds(),
                    SelectFromOptions.byIndex(1).from(FlujosUi.RECURSO_TIPO));
        });
    }
    public static Performable asignar(int servicio, String nombre, boolean seleccionar) {
        return Task.where("{0} guarda los recursos requeridos por su servicio", actor -> {
            actor.attemptsTo(abrir(), WaitUntil.the(FlujosUi.marcarRecurso(nombre), isPresent()).forNoMoreThan(15).seconds(),
                    SelectFromOptions.byValue(String.valueOf(servicio)).from(FlujosUi.RECURSO_SERVICIO));
            var opcion = FlujosUi.marcarRecurso(nombre).resolveFor(actor);
            if (opcion.isSelected() != seleccionar) actor.attemptsTo(Click.on(FlujosUi.marcarRecurso(nombre)));
            actor.attemptsTo(Click.on(FlujosUi.boton("Guardar asignación")),
                    WaitUntil.the(FlujosUi.aviso(seleccionar ? "1 recurso(s)" : "Se quitaron todos"), isVisible()).forNoMoreThan(15).seconds());
        });
    }
}

package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

public class GuardarLaAsignacionDelRecurso implements Interaction {

    private final int servicio;
    private final String nombre;
    private final boolean seleccionar;

    public GuardarLaAsignacionDelRecurso(int servicio, String nombre, boolean seleccionar) {
        this.servicio = servicio;
        this.nombre = nombre;
        this.seleccionar = seleccionar;
    }

    @Override
    @Step("{0} guarda la asignación del recurso")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Esperar.presente(FlujosUi.marcarRecurso(nombre)),
                SelectFromOptions.byValue(String.valueOf(servicio)).from(FlujosUi.RECURSO_SERVICIO));
        if (FlujosUi.marcarRecurso(nombre).resolveFor(actor).isSelected() != seleccionar) {
            actor.attemptsTo(Click.on(FlujosUi.marcarRecurso(nombre)));
        }
        actor.attemptsTo(
                Click.on(FlujosUi.boton("Guardar asignación")),
                Esperar.visible(FlujosUi.aviso(seleccionar ? "1 recurso(s)" : "Se quitaron todos")));
        PausaDeDemostracion.hacer();
    }

    public static GuardarLaAsignacionDelRecurso para(int servicio, String nombre, boolean seleccionar) {
        return Tasks.instrumented(GuardarLaAsignacionDelRecurso.class, servicio, nombre, seleccionar);
    }
}

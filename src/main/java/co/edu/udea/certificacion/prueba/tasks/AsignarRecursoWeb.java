package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirLaSeccionDeRecursos;
import co.edu.udea.certificacion.prueba.interactions.GuardarLaAsignacionDelRecurso;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class AsignarRecursoWeb implements Task {

    private final int servicio;
    private final String nombre;
    private final boolean seleccionar;

    public AsignarRecursoWeb(int servicio, String nombre, boolean seleccionar) {
        this.servicio = servicio;
        this.nombre = nombre;
        this.seleccionar = seleccionar;
    }

    @Override
    @Step("{0} guarda los recursos de su servicio")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                AbrirLaSeccionDeRecursos.delPanel(),
                GuardarLaAsignacionDelRecurso.para(servicio, nombre, seleccionar));
    }

    public static AsignarRecursoWeb al(int servicio, String nombre, boolean seleccionar) {
        return Tasks.instrumented(AsignarRecursoWeb.class, servicio, nombre, seleccionar);
    }
}

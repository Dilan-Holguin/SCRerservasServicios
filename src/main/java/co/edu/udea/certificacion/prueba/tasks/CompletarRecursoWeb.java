package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirLaSeccionDeRecursos;
import co.edu.udea.certificacion.prueba.interactions.LlenarElFormularioDeRecurso;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class CompletarRecursoWeb implements Task {

    private final String nombre;
    private final boolean conTipo;

    public CompletarRecursoWeb(String nombre, boolean conTipo) {
        this.nombre = nombre;
        this.conTipo = conTipo;
    }

    @Override
    @Step("{0} llena el formulario de un recurso")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                AbrirLaSeccionDeRecursos.delPanel(),
                LlenarElFormularioDeRecurso.con(nombre, conTipo));
    }

    public static CompletarRecursoWeb con(String nombre, boolean conTipo) {
        return Tasks.instrumented(CompletarRecursoWeb.class, nombre, conTipo);
    }
}

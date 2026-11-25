package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;

public class LlenarElFormularioDeRecurso implements Interaction {

    private final String nombre;
    private final boolean conTipo;

    public LlenarElFormularioDeRecurso(String nombre, boolean conTipo) {
        this.nombre = nombre;
        this.conTipo = conTipo;
    }

    @Override
    @Step("{0} llena el formulario del recurso")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Enter.theValue(nombre).into(FlujosUi.RECURSO_NOMBRE));
        if (conTipo) {
            actor.attemptsTo(
                    Esperar.presente(FlujosUi.RECURSO_TIPO_CARGADO),
                    SelectFromOptions.byIndex(1).from(FlujosUi.RECURSO_TIPO));
        }
        PausaDeDemostracion.hacer();
    }

    public static LlenarElFormularioDeRecurso con(String nombre, boolean conTipo) {
        return Tasks.instrumented(LlenarElFormularioDeRecurso.class, nombre, conTipo);
    }
}

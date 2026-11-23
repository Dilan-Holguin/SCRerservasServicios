package co.edu.udea.certificacion.prueba.support;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

public final class EscenarioUi {
    private EscenarioUi() { }
    public static Actor actor() { return OnStage.theActorInTheSpotlight(); }
    public static DatosEscenario datos() { return actor().recall(DatosEscenario.CLAVE); }
}

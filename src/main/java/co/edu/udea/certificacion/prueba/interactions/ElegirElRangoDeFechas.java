package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class ElegirElRangoDeFechas implements Interaction {

    private final String desde;
    private final String hasta;

    public ElegirElRangoDeFechas(String desde, String hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    @Step("{0} elige el rango de fechas")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(FijarFecha.en(FlujosUi.HASTA, hasta), FijarFecha.en(FlujosUi.DESDE, desde));
        PausaDeDemostracion.hacer();
    }

    public static ElegirElRangoDeFechas entre(String desde, String hasta) {
        return Tasks.instrumented(ElegirElRangoDeFechas.class, desde, hasta);
    }
}

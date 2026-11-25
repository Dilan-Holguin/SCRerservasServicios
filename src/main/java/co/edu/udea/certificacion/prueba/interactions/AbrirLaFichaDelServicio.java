package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class AbrirLaFichaDelServicio implements Interaction {

    private final int servicio;
    private final String desde;
    private final String hasta;

    public AbrirLaFichaDelServicio(int servicio, String desde, String hasta) {
        this.servicio = servicio;
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    @Step("{0} abre la ficha pública del servicio")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/servicios/" + servicio + "?desde=" + desde + "&hasta=" + hasta));
        PausaDeDemostracion.hacer();
    }

    public static AbrirLaFichaDelServicio entre(int servicio, String desde, String hasta) {
        return Tasks.instrumented(AbrirLaFichaDelServicio.class, servicio, desde, hasta);
    }
}

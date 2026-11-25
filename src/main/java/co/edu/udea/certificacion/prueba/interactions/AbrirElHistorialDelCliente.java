package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class AbrirElHistorialDelCliente implements Interaction {

    private final String nombreServicio;

    public AbrirElHistorialDelCliente(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    @Override
    @Step("{0} abre su historial de reservas")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(ConfiguracionUi.webUrl() + "/panel/cliente"),
                Esperar.visible(ReservaYa.reservaDelServicio(nombreServicio)));
        PausaDeDemostracion.hacer();
    }

    public static AbrirElHistorialDelCliente mostrando(String nombreServicio) {
        return Tasks.instrumented(AbrirElHistorialDelCliente.class, nombreServicio);
    }
}

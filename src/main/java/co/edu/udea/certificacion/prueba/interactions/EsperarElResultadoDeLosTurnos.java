package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.FlujosUi;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.TiempoDeEspera;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EsperarElResultadoDeLosTurnos implements Interaction {

    @Override
    @Step("{0} espera el resultado de los turnos")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Esperar.visible(FlujosUi.DESDE));
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        new WebDriverWait(driver, TiempoDeEspera.maximo()).until(d ->
                !d.findElements(By.cssSelector("[role='grid']")).isEmpty()
                || FlujosUi.SIN_TURNOS.resolveFor(actor).isCurrentlyVisible()
                || ReservaYa.ALERTA.resolveFor(actor).isCurrentlyVisible());
    }

    public static EsperarElResultadoDeLosTurnos enLaFicha() {
        return Tasks.instrumented(EsperarElResultadoDeLosTurnos.class);
    }
}

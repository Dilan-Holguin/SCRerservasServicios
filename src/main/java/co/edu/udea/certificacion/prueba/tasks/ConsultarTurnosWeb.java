package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.userinterfaces.*;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public final class ConsultarTurnosWeb {
    private ConsultarTurnosWeb() { }
    public static Performable de(int servicio, String desde, String hasta) {
        return Task.where("{0} consulta la ficha pública y espera su disponibilidad",
                Open.url(ConfiguracionUi.webUrl() + "/servicios/" + servicio + "?desde=" + desde + "&hasta=" + hasta),
                WaitUntil.the(FlujosUi.DESDE, isVisible()).forNoMoreThan(15).seconds(),
                esperarResultado());
    }
    public static Performable esperarResultado() {
        return Task.where("{0} espera el resultado de los turnos", actor -> {
            org.openqa.selenium.WebDriver driver = net.serenitybdd.screenplay.abilities.BrowseTheWeb.as(actor).getDriver();
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(d ->
                    !d.findElements(org.openqa.selenium.By.cssSelector("[role='grid']")).isEmpty()
                    || FlujosUi.SIN_TURNOS.resolveFor(actor).isCurrentlyVisible()
                    || ReservaYa.ALERTA.resolveFor(actor).isCurrentlyVisible());
        });
    }
}

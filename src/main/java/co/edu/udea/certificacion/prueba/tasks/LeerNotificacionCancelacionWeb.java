package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.userinterfaces.Mailpit;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WindowType;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** Contrato de producto pendiente; se activa expresamente con @pendiente_producto. */
public final class LeerNotificacionCancelacionWeb {
    private LeerNotificacionCancelacionWeb() { }
    public static Performable de(DatosEscenario datos) {
        return Task.where("{0} consulta el aviso de cancelación en la interfaz de Mailpit", actor -> {
            var driver = BrowseTheWeb.as(actor).getDriver();
            String anterior = driver.getWindowHandle();
            driver.switchTo().newWindow(WindowType.TAB);
            try {
                actor.attemptsTo(Open.url(ConfiguracionUi.correoUrl()),
                        WaitUntil.the(Mailpit.BUSQUEDA, isVisible()).forNoMoreThan(30).seconds(),
                        Enter.theValue("to:" + datos.cliente.email + " subject:cancel").into(Mailpit.BUSQUEDA).thenHit(Keys.ENTER),
                        WaitUntil.the(Mailpit.mensaje(datos.cliente.email), isVisible()).forNoMoreThan(30).seconds(),
                        Click.on(Mailpit.mensaje(datos.cliente.email)));
                var cuerpo = org.openqa.selenium.By.cssSelector("#nav-plain-text");
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(d -> {
                    var textos = d.findElements(cuerpo);
                    return !textos.isEmpty() && textos.get(0).isDisplayed()
                            && textos.get(0).getText().contains(datos.proveedor.servicio)
                            && textos.get(0).getText().contains("#" + datos.reserva);
                });
            } finally { driver.close(); driver.switchTo().window(anterior); }
        });
    }
}

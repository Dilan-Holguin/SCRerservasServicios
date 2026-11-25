package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.userinterfaces.Mailpit;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import co.edu.udea.certificacion.prueba.utils.TiempoDeEspera;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Contrato de producto pendiente; se activa expresamente con @pendiente_producto. */
public class BuscarElAvisoDeCancelacion implements Interaction {

    private final DatosEscenario datos;

    public BuscarElAvisoDeCancelacion(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} busca en Mailpit el aviso de cancelación")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        String anterior = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        try {
            actor.attemptsTo(
                    Open.url(ConfiguracionUi.correoUrl()),
                    Esperar.correoVisible(Mailpit.BUSQUEDA),
                    Enter.theValue("to:" + datos.cliente.email + " subject:cancel").into(Mailpit.BUSQUEDA).thenHit(Keys.ENTER),
                    Esperar.correoVisible(Mailpit.mensaje(datos.cliente.email)),
                    Click.on(Mailpit.mensaje(datos.cliente.email)));
            new WebDriverWait(driver, TiempoDeEspera.maximo()).until(d -> {
                var textos = d.findElements(Mailpit.CUERPO);
                return !textos.isEmpty() && textos.get(0).isDisplayed()
                        && textos.get(0).getText().contains(datos.proveedor.servicio)
                        && textos.get(0).getText().contains("#" + datos.reserva);
            });
            PausaDeDemostracion.hacer();
        } finally {
            driver.close();
            driver.switchTo().window(anterior);
        }
    }

    public static BuscarElAvisoDeCancelacion de(DatosEscenario datos) {
        return Tasks.instrumented(BuscarElAvisoDeCancelacion.class, datos);
    }
}

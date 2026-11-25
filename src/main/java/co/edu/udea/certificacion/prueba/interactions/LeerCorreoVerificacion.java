package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import co.edu.udea.certificacion.prueba.utils.TiempoDeEspera;
import co.edu.udea.certificacion.prueba.userinterfaces.Mailpit;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.*;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.WebDriverWait;
import net.serenitybdd.annotations.Step;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/** Busca el destinatario exacto en Mailpit web, sin consultar su servicio REST. */
public class LeerCorreoVerificacion implements Interaction {
    public static final String TOKEN = "tokenCorreo";
    public static final String ENCONTRADO = "correoVerificacionEncontrado";
    private final String email;
    private final boolean obligatorio;
    public LeerCorreoVerificacion(String email, boolean obligatorio) {
        this.email = email;
        this.obligatorio = obligatorio;
    }
    public static LeerCorreoVerificacion de(String email) {
        return instrumented(LeerCorreoVerificacion.class, email, true);
    }
    /** Ausencia tolerada únicamente para decidir si una precondición necesita reenvío. */
    public static LeerCorreoVerificacion buscarParaPreparacion(String email) {
        return instrumented(LeerCorreoVerificacion.class, email, false);
    }

    @Override @Step("{0} busca el correo de verificación del destinatario en Mailpit")
    public <T extends Actor> void performAs(T actor) {
        var driver = BrowseTheWeb.as(actor).getDriver();
        String original = driver.getWindowHandle();
        actor.forget(TOKEN);
        actor.remember(ENCONTRADO, false);
        driver.switchTo().newWindow(WindowType.TAB);
        try {
            boolean encontrado = false;
            // La segunda búsqueda vuelve a cargar la bandeja y consulta de nuevo el filtro.
            // No depende únicamente de que el websocket actualice los resultados abiertos.
            for (int intento = 0; intento < 2 && !encontrado; intento++) {
                actor.attemptsTo(Open.url(ConfiguracionUi.correoUrl()),
                        Esperar.correoVisible(Mailpit.BUSQUEDA),
                        Enter.theValue("to:" + email).into(Mailpit.BUSQUEDA).thenHit(Keys.ENTER));
                try {
                    new WebDriverWait(driver, TiempoDeEspera.correo())
                            .ignoring(StaleElementReferenceException.class)
                            .until(d -> d.findElements(Mailpit.selectorMensaje(email))
                                    .stream().anyMatch(e -> e.isDisplayed()));
                    encontrado = true;
                } catch (TimeoutException sinMensaje) {
                    // Ausencia real en esta consulta; el siguiente intento actualiza la UI.
                }
            }
            if (!encontrado) {
                if (obligatorio) throw new AssertionError(
                        "Mailpit no mostró el correo de verificación de " + email
                        + " tras dos búsquedas de "
                        + TiempoDeEspera.correo().toSeconds() + " segundos. Revisar el envío SMTP y la bandeja local.");
                return;
            }
            actor.attemptsTo(Click.on(Mailpit.mensaje(email)));
            if (Mailpit.TEXTO.resolveFor(actor).isCurrentlyVisible()) actor.attemptsTo(Click.on(Mailpit.TEXTO));
            actor.attemptsTo(Esperar.visible(Mailpit.ENLACE));
            String query = URI.create(Mailpit.ENLACE.resolveFor(actor).getAttribute("href")).getRawQuery();
            String token = Arrays.stream(query.split("&")).filter(p -> p.startsWith("token="))
                    .map(p -> URLDecoder.decode(p.substring(6), StandardCharsets.UTF_8))
                    .findFirst().orElseThrow(() -> new IllegalStateException("Correo sin enlace de verificación"));
            actor.remember(TOKEN, token);
            actor.remember(ENCONTRADO, true);
            PausaDeDemostracion.hacer();
        } finally {
            driver.close();
            driver.switchTo().window(original);
        }
    }
}

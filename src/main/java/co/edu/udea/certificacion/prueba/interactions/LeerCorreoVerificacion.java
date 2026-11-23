package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.Mailpit;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.Keys;
import net.serenitybdd.annotations.Step;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** Lee exclusivamente el correo de la cuenta actual mediante la interfaz de Mailpit. */
public class LeerCorreoVerificacion implements Interaction {
    public static final String TOKEN = "tokenCorreo";
    private final String email;
    public LeerCorreoVerificacion(String email) { this.email = email; }
    public static LeerCorreoVerificacion de(String email) { return instrumented(LeerCorreoVerificacion.class, email); }

    @Override @Step("{0} consulta su correo de verificación en Mailpit")
    public <T extends Actor> void performAs(T actor) {
        var driver = BrowseTheWeb.as(actor).getDriver();
        String original = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        try {
            actor.attemptsTo(Open.url(ConfiguracionUi.correoUrl()),
                    WaitUntil.the(Mailpit.BUSQUEDA, isVisible()).forNoMoreThan(30).seconds(),
                    Enter.theValue("to:" + email).into(Mailpit.BUSQUEDA).thenHit(Keys.ENTER),
                    WaitUntil.the(Mailpit.mensaje(email), isVisible()).forNoMoreThan(30).seconds(),
                    Click.on(Mailpit.mensaje(email)));
            if (Mailpit.TEXTO.resolveFor(actor).isVisible()) actor.attemptsTo(Click.on(Mailpit.TEXTO));
            actor.attemptsTo(WaitUntil.the(Mailpit.ENLACE, isVisible()).forNoMoreThan(15).seconds());
            String query = URI.create(Mailpit.ENLACE.resolveFor(actor).getAttribute("href")).getRawQuery();
            String token = Arrays.stream(query.split("&")).filter(p -> p.startsWith("token="))
                    .map(p -> URLDecoder.decode(p.substring(6), StandardCharsets.UTF_8))
                    .findFirst().orElseThrow(() -> new IllegalStateException("Correo sin enlace de verificación"));
            actor.remember(TOKEN, token);
        } finally {
            driver.close();
            driver.switchTo().window(original);
        }
    }
}

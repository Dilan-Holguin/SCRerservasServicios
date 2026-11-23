package co.edu.udea.certificacion.prueba.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.annotations.Step;
import org.openqa.selenium.JavascriptExecutor;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/** Edición del control date visible, con los mismos eventos DOM que usa React. */
public class FijarFecha implements Interaction {
    private final Target campo;
    private final String fecha;
    public FijarFecha(Target campo, String fecha) { this.campo = campo; this.fecha = fecha; }
    public static FijarFecha en(Target campo, String fecha) { return instrumented(FijarFecha.class, campo, fecha); }
    @Override @Step("{0} cambia la fecha del control visible")
    public <T extends Actor> void performAs(T actor) {
        ((JavascriptExecutor) BrowseTheWeb.as(actor).getDriver()).executeScript("""
                const input = arguments[0];
                Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set.call(input, arguments[1]);
                input.dispatchEvent(new Event('input', {bubbles:true}));
                input.dispatchEvent(new Event('change', {bubbles:true}));
                """, campo.resolveFor(actor), fecha);
    }
}

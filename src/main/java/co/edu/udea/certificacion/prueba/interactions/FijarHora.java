package co.edu.udea.certificacion.prueba.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;

/** El input time depende del formato regional del sistema; se emite el evento de edición. */
public class FijarHora implements Interaction {

    private final Target campo;
    private final String hora;

    public FijarHora(Target campo, String hora) {
        this.campo = campo;
        this.hora = hora;
    }

    @Override
    @Step("{0} fija la hora en el control")
    public <T extends Actor> void performAs(T actor) {
        ((JavascriptExecutor) BrowseTheWeb.as(actor).getDriver()).executeScript("""
                const input = arguments[0];
                Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set.call(input, arguments[1]);
                input.dispatchEvent(new Event('input', {bubbles: true}));
                input.dispatchEvent(new Event('change', {bubbles: true}));
                """, campo.resolveFor(actor), hora);
    }

    public static FijarHora a(Target campo, String hora) {
        return Tasks.instrumented(FijarHora.class, campo, hora);
    }
}

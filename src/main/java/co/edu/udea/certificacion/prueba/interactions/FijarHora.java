package co.edu.udea.certificacion.prueba.interactions;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;

/** El input time depende del formato regional del sistema; se emite el evento de edición. */
public final class FijarHora {
    private FijarHora() { }
    public static Performable a(Target campo, String hora) {
        return Task.where("{0} fija " + campo + " a " + hora, actor -> {
            JavascriptExecutor navegador = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
            navegador.executeScript("""
                const input = arguments[0];
                Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set.call(input, arguments[1]);
                input.dispatchEvent(new Event('input', {bubbles: true}));
                input.dispatchEvent(new Event('change', {bubbles: true}));
                """, campo.resolveFor(actor), hora);
        });
    }
}

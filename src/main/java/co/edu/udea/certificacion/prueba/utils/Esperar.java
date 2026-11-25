package co.edu.udea.certificacion.prueba.utils;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

/**
 * Esperas por condición: terminan apenas se cumple y fallan al llegar al máximo.
 * Se usa forNoMoreThan(long).seconds(): en Serenity 4.1.0 la variante con Duration revisa una sola vez.
 */
public final class Esperar {

    private Esperar() {
    }

    public static Performable visible(Target elemento) {
        return WaitUntil.the(elemento, isVisible()).forNoMoreThan(segundos()).seconds();
    }

    public static Performable noVisible(Target elemento) {
        return WaitUntil.the(elemento, isNotVisible()).forNoMoreThan(segundos()).seconds();
    }

    public static Performable clicable(Target elemento) {
        return WaitUntil.the(elemento, isClickable()).forNoMoreThan(segundos()).seconds();
    }

    public static Performable presente(Target elemento) {
        return WaitUntil.the(elemento, isPresent()).forNoMoreThan(segundos()).seconds();
    }

    public static Performable deshabilitado(Target elemento) {
        return WaitUntil.the(elemento, isNotEnabled()).forNoMoreThan(segundos()).seconds();
    }

    public static Performable correoVisible(Target elemento) {
        return WaitUntil.the(elemento, isVisible()).forNoMoreThan(TiempoDeEspera.correo().toSeconds()).seconds();
    }

    private static long segundos() {
        return TiempoDeEspera.maximo().toSeconds();
    }
}

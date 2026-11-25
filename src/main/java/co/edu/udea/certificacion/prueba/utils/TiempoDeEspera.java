package co.edu.udea.certificacion.prueba.utils;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;

import java.time.Duration;

/** Lee de serenity.conf cuánto se espera como máximo. Es el único lugar que conoce esos tiempos. */
public final class TiempoDeEspera {

    private static final String PROPIEDAD_PANTALLA = "webdriver.wait.for.timeout";
    private static final String PROPIEDAD_CORREO = "mailpit.wait.timeout";
    private static final int PANTALLA_POR_DEFECTO = 15000;
    private static final int CORREO_POR_DEFECTO = 30000;

    private TiempoDeEspera() {
    }

    /** Máximo para que aparezca o cambie un elemento de ReservaYa. */
    public static Duration maximo() {
        return leer(PROPIEDAD_PANTALLA, PANTALLA_POR_DEFECTO);
    }

    /** Máximo para que Mailpit muestre un correo. */
    public static Duration correo() {
        return leer(PROPIEDAD_CORREO, CORREO_POR_DEFECTO);
    }

    private static Duration leer(String propiedad, int porDefecto) {
        int milisegundos = EnvironmentSpecificConfiguration
                .from(SystemEnvironmentVariables.currentEnvironmentVariables())
                .getOptionalInteger(propiedad)
                .orElse(porDefecto);
        return Duration.ofMillis(milisegundos);
    }
}

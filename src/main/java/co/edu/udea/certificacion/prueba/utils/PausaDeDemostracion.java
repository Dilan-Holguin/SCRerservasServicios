package co.edu.udea.certificacion.prueba.utils;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;

/**
 * Pausa fija solo para que el público alcance a ver cada paso. No sirve para sincronizar:
 * para eso está Esperar. Es el único lugar del proyecto con Thread.sleep.
 */
public final class PausaDeDemostracion {

    private static final String PROPIEDAD = "demo.pause.milliseconds";

    private PausaDeDemostracion() {
    }

    public static void hacer() {
        int milisegundos = EnvironmentSpecificConfiguration
                .from(SystemEnvironmentVariables.currentEnvironmentVariables())
                .getOptionalInteger(PROPIEDAD)
                .orElse(0);
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

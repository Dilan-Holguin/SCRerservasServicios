package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.LeerCorreoVerificacion;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class VerificarCuentaWeb {
    private VerificarCuentaWeb() { }
    /** Para los casos que comprueban el registro: exige el correo sin solicitar otro. */
    public static Performable de(CuentaPrueba cuenta) { return verificar(cuenta, false); }

    /** Para las precondiciones: permite un único reenvío por el formulario visible. */
    public static Performable paraPrepararCuenta(CuentaPrueba cuenta) { return verificar(cuenta, true); }

    private static Performable verificar(CuentaPrueba cuenta, boolean recuperarPrecondicion) {
        return Task.where("{0} verifica su cuenta con el correo recibido", actor -> {
            if (recuperarPrecondicion) {
                actor.attemptsTo(LeerCorreoVerificacion.buscarParaPreparacion(cuenta.email));
                if (!Boolean.TRUE.equals(actor.recall(LeerCorreoVerificacion.ENCONTRADO))) {
                    actor.attemptsTo(SolicitarReenvioVerificacionWeb.paraPreparacion(cuenta.email),
                            LeerCorreoVerificacion.de(cuenta.email));
                }
            } else {
                actor.attemptsTo(LeerCorreoVerificacion.de(cuenta.email));
            }
            String token = actor.recall(LeerCorreoVerificacion.TOKEN);
            try {
                BrowseTheWeb.as(actor).getDriver().get(ConfiguracionUi.webUrl() + "/verificacion?token=" + token);
                actor.attemptsTo(WaitUntil.the(ReservaYa.CUENTA_VERIFICADA, isVisible()).forNoMoreThan(15).seconds());
                cuenta.verificada = true;
            } finally {
                actor.forget(LeerCorreoVerificacion.TOKEN);
                actor.forget(LeerCorreoVerificacion.ENCONTRADO);
            }
        });
    }
}

package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class ConfirmarLaCuenta implements Interaction {

    private final CuentaPrueba cuenta;

    public ConfirmarLaCuenta(CuentaPrueba cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    @Step("{0} confirma su cuenta con el enlace")
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall(LeerCorreoVerificacion.TOKEN);
        try {
            actor.attemptsTo(AbrirLaPaginaDeVerificacion.conToken(token),
                    Esperar.visible(ReservaYa.CUENTA_VERIFICADA));
            cuenta.verificada = true;
        } finally {
            actor.forget(LeerCorreoVerificacion.TOKEN);
            actor.forget(LeerCorreoVerificacion.ENCONTRADO);
        }
    }

    public static ConfirmarLaCuenta de(CuentaPrueba cuenta) {
        return Tasks.instrumented(ConfirmarLaCuenta.class, cuenta);
    }
}

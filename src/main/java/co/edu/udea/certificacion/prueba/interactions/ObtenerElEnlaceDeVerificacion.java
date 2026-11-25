package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class ObtenerElEnlaceDeVerificacion implements Interaction {

    private final CuentaPrueba cuenta;
    private final boolean permitirReenvio;

    public ObtenerElEnlaceDeVerificacion(CuentaPrueba cuenta, boolean permitirReenvio) {
        this.cuenta = cuenta;
        this.permitirReenvio = permitirReenvio;
    }

    @Override
    @Step("{0} obtiene el enlace de verificación recibido")
    public <T extends Actor> void performAs(T actor) {
        if (!permitirReenvio) {
            actor.attemptsTo(LeerCorreoVerificacion.de(cuenta.email));
            return;
        }
        actor.attemptsTo(LeerCorreoVerificacion.buscarParaPreparacion(cuenta.email));
        if (!Boolean.TRUE.equals(actor.recall(LeerCorreoVerificacion.ENCONTRADO))) {
            actor.attemptsTo(PedirElReenvio.alCorreo(cuenta.email), LeerCorreoVerificacion.de(cuenta.email));
        }
    }

    public static ObtenerElEnlaceDeVerificacion para(CuentaPrueba cuenta, boolean permitirReenvio) {
        return Tasks.instrumented(ObtenerElEnlaceDeVerificacion.class, cuenta, permitirReenvio);
    }
}

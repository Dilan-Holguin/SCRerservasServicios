package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

public class EscribirClave implements Interaction {

    private final String clave;

    public EscribirClave(String clave) {
        this.clave = clave;
    }

    @Override
    @Step("{0} escribe su contraseña")
    public <T extends Actor> void performAs(T actor) {
        var campo = ReservaYa.CLAVE.resolveFor(actor);
        campo.clear();
        campo.sendKeys(clave);
    }

    public static EscribirClave sinExponerla(String clave) {
        return Tasks.instrumented(EscribirClave.class, clave);
    }
}

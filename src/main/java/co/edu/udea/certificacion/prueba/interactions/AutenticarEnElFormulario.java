package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.utils.PausaDeDemostracion;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

public class AutenticarEnElFormulario implements Interaction {

    private final String email;
    private final String clave;

    public AutenticarEnElFormulario(String email, String clave) {
        this.email = email;
        this.clave = clave;
    }

    @Override
    @Step("{0} escribe sus credenciales e ingresa")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Esperar.visible(ReservaYa.EMAIL),
                Enter.theValue(email).into(ReservaYa.EMAIL),
                EscribirClave.sinExponerla(clave),
                Click.on(ReservaYa.INGRESAR));
        PausaDeDemostracion.hacer();
    }

    public static AutenticarEnElFormulario con(String email, String clave) {
        return Tasks.instrumented(AutenticarEnElFormulario.class, email, clave);
    }
}

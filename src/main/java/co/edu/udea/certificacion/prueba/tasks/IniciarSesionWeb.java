package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.AbrirElFormularioDeIngreso;
import co.edu.udea.certificacion.prueba.interactions.AutenticarEnElFormulario;
import co.edu.udea.certificacion.prueba.interactions.CerrarSesionSiEstaAbierta;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class IniciarSesionWeb implements Task {

    private final String email;
    private final String clave;

    public IniciarSesionWeb(String email, String clave) {
        this.email = email;
        this.clave = clave;
    }

    @Override
    @Step("{0} inicia sesión desde el navegador")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                CerrarSesionSiEstaAbierta.enElMenu(),
                AbrirElFormularioDeIngreso.deSesion(),
                AutenticarEnElFormulario.con(email, clave));
    }

    public static IniciarSesionWeb con(String email, String clave) {
        return Tasks.instrumented(IniciarSesionWeb.class, email, clave);
    }
}

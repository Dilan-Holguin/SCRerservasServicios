package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.EscribirClave;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class IniciarSesionWeb {
    private IniciarSesionWeb() { }
    public static Performable con(String email, String clave) {
        return Task.where("{0} inicia sesión desde el navegador",
                CerrarSesionWeb.siExiste(), Open.url(ConfiguracionUi.webUrl() + "/ingresar"),
                WaitUntil.the(ReservaYa.EMAIL, isVisible()).forNoMoreThan(15).seconds(),
                Enter.theValue(email).into(ReservaYa.EMAIL), EscribirClave.sinExponerla(clave),
                Click.on(ReservaYa.INGRESAR));
    }
}

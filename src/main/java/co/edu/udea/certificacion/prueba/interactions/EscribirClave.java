package co.edu.udea.certificacion.prueba.interactions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

public final class EscribirClave {
    private EscribirClave() { }
    public static Performable sinExponerla(String clave) {
        return Task.where("{0} escribe su contraseña", actor -> {
            var campo = ReservaYa.CLAVE.resolveFor(actor);
            campo.clear();
            campo.sendKeys(clave);
        });
    }
}

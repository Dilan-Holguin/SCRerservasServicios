package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public final class CancelarReservaWeb {
    private CancelarReservaWeb() { }
    public static Performable conConfirmacion(int id, boolean confirmar) {
        return Task.where("{0} solicita cancelar la reserva " + id + (confirmar ? " y confirma" : " y decide conservarla"),
                WaitUntil.the(ReservaYa.cancelar(id), isClickable()).forNoMoreThan(15).seconds(),
                Click.on(ReservaYa.cancelar(id)),
                WaitUntil.the(ReservaYa.CONFIRMAR_CANCELACION, isClickable()).forNoMoreThan(10).seconds(),
                Click.on(confirmar ? ReservaYa.CONFIRMAR_CANCELACION : ReservaYa.CONSERVAR_RESERVA));
    }
}

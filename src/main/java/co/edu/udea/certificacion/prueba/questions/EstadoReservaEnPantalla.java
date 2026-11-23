package co.edu.udea.certificacion.prueba.questions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import net.serenitybdd.screenplay.Question;

public final class EstadoReservaEnPantalla {
    private EstadoReservaEnPantalla() { }
    public static Question<Boolean> es(int id, String estado) {
        return actor -> ReservaYa.estadoReserva(id, estado).resolveFor(actor).isVisible();
    }
}

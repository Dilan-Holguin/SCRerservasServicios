package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import co.edu.udea.certificacion.prueba.questions.DatosEnPantalla;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class ConsultarReservaWeb {
    private ConsultarReservaWeb() { }
    public static Performable delCliente(DatosEscenario datos) {
        return Task.where("{0} encuentra su reserva en el historial del cliente", actor -> {
            actor.attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/panel/cliente"),
                    WaitUntil.the(ReservaYa.reservaDelServicio(datos.proveedor.servicio), isVisible()).forNoMoreThan(15).seconds());
            datos.reserva = actor.asksFor(DatosEnPantalla.idReserva(datos.proveedor.servicio));
        });
    }
    public static Performable recargando(DatosEscenario datos) {
        return Task.where("{0} vuelve a consultar el historial al cargar la página completa", actor -> {
            // Otra Task puede obtener el ID o cambiar de rol antes de ejecutar esta.
            // Construir los Targets aquí evita capturar el valor inicial #0.
            if (datos.reserva <= 0) {
                throw new IllegalStateException("Primero debe leerse el identificador de la reserva en el historial.");
            }
            actor.attemptsTo(
                    Open.url(ConfiguracionUi.webUrl() + ("PROVEEDOR".equals(datos.rolActual)
                            ? "/panel/proveedor?seccion=reservas" : "/panel/cliente")),
                    WaitUntil.the(ReservaYa.tarjeta(datos.reserva), isVisible()).forNoMoreThan(15).seconds());
        });
    }
    public static Performable disponibilidad(DatosEscenario datos) {
        return Task.where("{0} consulta los turnos en la ficha pública", actor -> actor.attemptsTo(
                Open.url(ConfiguracionUi.webUrl() + "/servicios/" + datos.servicio + "?desde=" + datos.fecha + "&hasta=" + datos.fecha),
                WaitUntil.the(ReservaYa.turno("09:00"), isVisible()).forNoMoreThan(15).seconds()));
    }
}

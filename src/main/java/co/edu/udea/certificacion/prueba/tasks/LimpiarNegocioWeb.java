package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.models.*;
import co.edu.udea.certificacion.prueba.userinterfaces.*;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.util.LinkedHashSet;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public final class LimpiarNegocioWeb {
    private LimpiarNegocioWeb() { }
    public static Performable de(DatosEscenario datos) {
        return Task.where("{0} retira las reservas y servicios creados por el escenario mediante la UI", actor -> {
            CuentaPrueba proveedor = datos.proveedor != null ? datos.proveedor
                    : datos.cuenta != null && datos.cuenta.rol.equals("PROVEEDOR") ? datos.cuenta : null;
            if (proveedor == null || !proveedor.verificada) return;
            actor.attemptsTo(IniciarSesionWeb.con(proveedor.email, proveedor.clave),
                    WaitUntil.the(ReservaYa.panel("PROVEEDOR"), isVisible()).forNoMoreThan(15).seconds());
            var reservas = new LinkedHashSet<>(datos.reservasCreadas);
            if (datos.reserva != 0) reservas.add(datos.reserva);
            for (int id : reservas) {
                actor.attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=reservas"),
                        WaitUntil.the(ReservaYa.tarjeta(id), isVisible()).forNoMoreThan(15).seconds());
                if (ReservaYa.estadoReserva(id, "CONFIRMADA").resolveFor(actor).isCurrentlyVisible())
                    actor.attemptsTo(CancelarReservaWeb.conConfirmacion(id, true),
                            WaitUntil.the(ReservaYa.cancelacionConfirmada(id), isVisible()).forNoMoreThan(15).seconds());
            }
            var servicios = new LinkedHashSet<String>();
            servicios.add(proveedor.servicio);
            servicios.addAll(datos.serviciosExtra.values());
            for (String nombre : servicios) {
                actor.attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=servicios"),
                        WaitUntil.the(ReservaYa.fichaServicio(nombre), isVisible()).forNoMoreThan(15).seconds());
                if (ReservaYa.inactivar(nombre).resolveFor(actor).isCurrentlyVisible())
                    actor.attemptsTo(Click.on(ReservaYa.inactivar(nombre)),
                            WaitUntil.the(ReservaYa.CONFIRMAR_INACTIVACION, isClickable()).forNoMoreThan(15).seconds(),
                            Click.on(ReservaYa.CONFIRMAR_INACTIVACION),
                            WaitUntil.the(ReservaYa.servicioInactivo(nombre), isVisible()).forNoMoreThan(15).seconds());
            }
        });
    }
}

package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.CancelarSiSigueConfirmada;
import co.edu.udea.certificacion.prueba.interactions.InactivarElServicio;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class LimpiarNegocioWeb implements Task {

    private final DatosEscenario datos;

    public LimpiarNegocioWeb(DatosEscenario datos) {
        this.datos = datos;
    }

    @Override
    @Step("{0} retira las reservas y servicios del escenario")
    public <T extends Actor> void performAs(T actor) {
        CuentaPrueba proveedor = datos.proveedorPorLimpiar();
        if (proveedor == null) {
            return;
        }
        actor.attemptsTo(AccederConCuentaWeb.de(datos, proveedor));
        for (int reserva : datos.reservasPorLimpiar()) {
            actor.attemptsTo(CancelarSiSigueConfirmada.laReserva(reserva));
        }
        for (String servicio : datos.serviciosPorLimpiar(proveedor)) {
            actor.attemptsTo(InactivarElServicio.siSigueActivo(servicio));
        }
    }

    public static LimpiarNegocioWeb de(DatosEscenario datos) {
        return Tasks.instrumented(LimpiarNegocioWeb.class, datos);
    }
}

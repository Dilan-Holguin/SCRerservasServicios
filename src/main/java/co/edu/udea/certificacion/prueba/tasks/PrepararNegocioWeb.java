package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.interactions.ConfirmarLaCreacionDelBloque;
import co.edu.udea.certificacion.prueba.interactions.GuardarElServicioPrincipal;
import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.models.DatosEscenario;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class PrepararNegocioWeb implements Task {

    private final DatosEscenario datos;
    private final int dias;
    private final boolean conAgenda;
    private final boolean conCliente;

    public PrepararNegocioWeb(DatosEscenario datos, int dias, boolean conAgenda, boolean conCliente) {
        this.datos = datos;
        this.dias = dias;
        this.conAgenda = conAgenda;
        this.conCliente = conCliente;
    }

    @Override
    @Step("{0} prepara un negocio mediante los formularios")
    public <T extends Actor> void performAs(T actor) {
        CuentaPrueba proveedor = datos.nuevoNegocio(dias);
        actor.attemptsTo(
                RegistrarCuentaWeb.con(proveedor),
                VerificarCuentaWeb.paraPrepararCuenta(proveedor),
                AccederConCuentaWeb.de(datos, proveedor),
                GuardarElServicioPrincipal.en(datos));
        if (conAgenda) {
            actor.attemptsTo(
                    CrearAgendaWeb.para(datos.servicio, datos.diaDeLaAgenda(), "08:00", "12:00"),
                    ConfirmarLaCreacionDelBloque.enPantalla());
        }
        if (conCliente) {
            actor.attemptsTo(CrearClienteWeb.principal(datos));
        }
    }

    public static PrepararNegocioWeb para(DatosEscenario datos, int dias, boolean conAgenda, boolean conCliente) {
        return Tasks.instrumented(PrepararNegocioWeb.class, datos, dias, conAgenda, conCliente);
    }
}

package co.edu.udea.certificacion.prueba.tasks;

import co.edu.udea.certificacion.prueba.models.*;
import co.edu.udea.certificacion.prueba.questions.DatosEnPantalla;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public final class FlujoReservaWeb {
    private FlujoReservaWeb() { }
    public static Performable sesion(DatosEscenario datos, CuentaPrueba cuenta) {
        return Task.where("{0} accede como " + cuenta.rol, actor -> {
            actor.attemptsTo(IniciarSesionWeb.con(cuenta.email, cuenta.clave),
                    WaitUntil.the(ReservaYa.panel(cuenta.rol), isVisible()).forNoMoreThan(15).seconds());
            datos.cuenta = cuenta;
            datos.rolActual = cuenta.rol;
        });
    }
    public static Performable crearCliente(DatosEscenario datos, boolean adicional) {
        return Task.where("{0} crea y verifica un cliente independiente", actor -> {
            CuentaPrueba cuenta = new CuentaPrueba("CLIENTE");
            if (adicional) datos.otroCliente = cuenta; else datos.cliente = cuenta;
            actor.attemptsTo(RegistrarCuentaWeb.con(cuenta), VerificarCuentaWeb.paraPrepararCuenta(cuenta), sesion(datos, cuenta));
        });
    }
    public static Performable reservar(DatosEscenario datos, int servicio, String nombre) {
        return Task.where("{0} reserva el turno de las nueve", actor -> {
            actor.attemptsTo(ReservarTurnoWeb.para(servicio, datos.fecha.toString(), "09:00"),
                    WaitUntil.the(ReservaYa.RESERVA_CONFIRMADA, isVisible()).forNoMoreThan(15).seconds(), guardar(datos, nombre));
        });
    }
    public static Performable guardar(DatosEscenario datos, String nombre) {
        return Task.where("{0} consulta y guarda el identificador de su reserva", actor -> {
            actor.attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/panel/cliente"),
                    WaitUntil.the(ReservaYa.reservaDelServicio(nombre), isVisible()).forNoMoreThan(15).seconds());
            datos.reserva = actor.asksFor(DatosEnPantalla.idReserva(nombre));
            datos.reservasCreadas.add(datos.reserva);
            actor.attemptsTo(WaitUntil.the(ReservaYa.estadoReserva(datos.reserva, "CONFIRMADA"), isVisible()).forNoMoreThan(15).seconds());
        });
    }
    public static Performable preparada(DatosEscenario datos, String rol) {
        return Task.where("{0} prepara una reserva por la interfaz", actor -> {
            actor.attemptsTo(PrepararNegocioWeb.para(datos, 10, true, true));
            // El servicio y el proveedor existen después de ejecutar la preparación.
            actor.attemptsTo(reservar(datos, datos.servicio, datos.proveedor.servicio));
            if (rol.equals("PROVEEDOR")) actor.attemptsTo(sesion(datos, datos.proveedor),
                    ConsultarReservaWeb.recargando(datos));
        });
    }
}

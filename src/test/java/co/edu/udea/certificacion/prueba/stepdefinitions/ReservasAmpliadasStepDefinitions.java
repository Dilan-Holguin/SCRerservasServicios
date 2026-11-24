package co.edu.udea.certificacion.prueba.stepdefinitions;

import co.edu.udea.certificacion.prueba.interactions.FijarFecha;
import co.edu.udea.certificacion.prueba.tasks.*;
import co.edu.udea.certificacion.prueba.questions.*;
import co.edu.udea.certificacion.prueba.userinterfaces.*;
import co.edu.udea.certificacion.prueba.utils.ConfiguracionUi;
import io.cucumber.java.en.*;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static co.edu.udea.certificacion.prueba.support.EscenarioUi.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;
import static org.hamcrest.Matchers.*;

public class ReservasAmpliadasStepDefinitions {
    @Given("una reserva UI independiente para el rol {string}")
    public void reserva(String rol) { actor().attemptsTo(FlujoReservaWeb.preparada(datos(), rol)); }
    @When("abre su servicio desde el catalogo y consulta el dia preparado")
    public void catalogo() {
        actor().attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/"),
                WaitUntil.the(FlujosUi.fichaCatalogo(datos().proveedor.servicio), isClickable()).forNoMoreThan(15).seconds(),
                Click.on(FlujosUi.fichaCatalogo(datos().proveedor.servicio)),
                WaitUntil.the(FlujosUi.DESDE, isVisible()).forNoMoreThan(15).seconds(),
                FijarFecha.en(FlujosUi.HASTA, datos().fecha.toString()), FijarFecha.en(FlujosUi.DESDE, datos().fecha.toString()),
                WaitUntil.the(ReservaYa.turno("09:00"), isVisible()).forNoMoreThan(15).seconds());
    }
    @Then("la ficha refleja el rango elegido y ocho turnos con cupo")
    public void rangoCorrecto() {
        actor().should(seeThat(ControlesVisibles.url(), allOf(
                containsString("desde=" + datos().fecha), containsString("hasta=" + datos().fecha))),
                eventually(seeThat(InformacionVisible.cantidadTurnos(), equalTo(8))),
                seeThat(InformacionVisible.cupos("09:00"), equalTo("1 cupo libre")));
    }
    @Then("la disponibilidad publica refleja el nuevo bloque de diez a catorce")
    public void turnosEditados() {
        actor().attemptsTo(ConsultarTurnosWeb.de(datos().servicio, datos().fecha.toString(), datos().fecha.toString()),
                WaitUntil.the(ReservaYa.turno("10:00"), isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(ControlesVisibles.turnoAusente("09:00"), is(true)),
                seeThat(InformacionVisible.cantidadTurnos(), equalTo(8)));
    }
    @When("consulta la disponibilidad del dia de la agenda eliminada")
    public void turnosEliminados() {
        actor().attemptsTo(ConsultarTurnosWeb.de(datos().servicio, datos().fecha.toString(), datos().fecha.toString()));
    }
    @When("consulta la ficha con un rango de fechas invertido")
    public void rangoInvertido() {
        actor().attemptsTo(ConsultarTurnosWeb.de(datos().servicio, datos().fecha.plusDays(1).toString(), datos().fecha.toString()));
    }
    @Then("la ficha informa que la fecha inicial es posterior a la final")
    public void errorRango() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(Text.of(ReservaYa.ALERTA), containsString("La fecha inicial no puede ser posterior a la final")));
    }
    @When("amplia la consulta a {int} dias inclusivos desde el control de fechas")
    public void amplitud(int dias) {
        actor().attemptsTo(ConsultarTurnosWeb.de(datos().servicio, datos().fecha.toString(), datos().fecha.toString()),
                FijarFecha.en(FlujosUi.HASTA, datos().fecha.plusDays(dias - 1).toString()));
    }
    @Then("la ficha rechaza el rango excedido y muestra el limite en la ayuda")
    public void rangoExcedido() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
        actor().should(
                seeThat(Text.of(ReservaYa.ALERTA), allOf(containsString("No se pudo consultar la disponibilidad"),
                        containsString("La solicitud contiene datos inválidos"))),
                seeThat(Text.of(FlujosUi.DISPONIBILIDAD), containsString("El rango máximo de consulta es de 31 días")),
                seeThat(ControlesVisibles.fechasDelRango(datos().fecha.toString(), datos().fecha.plusDays(31).toString()), is(true)),
                seeThat(ControlesVisibles.url(), allOf(containsString("desde=" + datos().fecha),
                        containsString("hasta=" + datos().fecha.plusDays(31)))),
                eventually(seeThat(InformacionVisible.cantidadTurnos(), equalTo(0))));
    }
    @Then("la consulta de treinta y un dias ofrece los cinco dias semanales preparados")
    public void rangoLimite() {
        actor().attemptsTo(WaitUntil.the(FlujosUi.css("cinco días disponibles", "[role='grid'][aria-rowcount='5']"),
                isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(InformacionVisible.cantidadTurnos(), equalTo(40)),
                seeThat(ControlesVisibles.url(), containsString("hasta=" + datos().fecha.plusDays(30))));
    }
    @When("consulta un dia que no pertenece a la agenda semanal")
    public void fueraDeAgenda() {
        String fecha = datos().fecha.plusDays(1).toString();
        actor().attemptsTo(ConsultarTurnosWeb.de(datos().servicio, fecha, fecha));
    }
    @Then("la ficha no ofrece turnos fuera de la agenda")
    public void sinTurnos() {
        actor().attemptsTo(WaitUntil.the(FlujosUi.SIN_TURNOS, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(InformacionVisible.cantidadTurnos(), equalTo(0)));
    }
    @Given("un turno UI cuyo ultimo cupo ya fue reservado")
    public void turnoLleno() {
        actor().attemptsTo(FlujoReservaWeb.preparada(datos(), "CLIENTE"), FlujoReservaWeb.crearCliente(datos(), true));
    }
    @When("otro cliente actualiza la ficha del servicio ocupado")
    public void consultarOcupado() {
        actor().attemptsTo(ConsultarTurnosWeb.de(datos().servicio, datos().fecha.toString(), datos().fecha.toString()));
    }
    @Then("el turno de las nueve no se ofrece y el siguiente sigue disponible")
    public void ocultarOcupado() {
        actor().should(eventually(seeThat(ControlesVisibles.turnoAusente("09:00"), is(true))));
        actor().attemptsTo(WaitUntil.the(ReservaYa.turno("09:30"), isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(InformacionVisible.cantidadTurnos(), equalTo(7)));
    }
    @When("cierra sesion y selecciona el turno como visitante")
    public void seleccionarAnonimo() {
        actor().attemptsTo(CerrarSesionWeb.siExiste(),
                ReservarTurnoWeb.para(datos().servicio, datos().fecha.toString(), "09:00"));
    }
    @Then("el ingreso solicita autenticarse para confirmar el turno elegido")
    public void pedirIngreso() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.INGRESAR, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(Text.of(FlujosUi.aviso("Ingresa para confirmar la reserva")), containsString("turno seleccionado")));
    }
    @When("ingresa conservando el destino del turno seleccionado")
    public void continuar() { actor().attemptsTo(IngresarConDestinoWeb.con(datos().cliente)); }
    @Then("la reserva pendiente queda confirmada y aparece en el historial")
    public void reservaContinuada() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.RESERVA_CONFIRMADA, isVisible()).forNoMoreThan(15).seconds(),
                FlujoReservaWeb.guardar(datos(), datos().proveedor.servicio));
    }
    @When("consulta y pulsa un turno desde su cuenta de proveedor")
    public void reservarComoProveedor() {
        actor().attemptsTo(ConsultarTurnosWeb.de(datos().servicio, datos().fecha.toString(), datos().fecha.toString()),
                Click.on(ReservaYa.turno("09:00")));
    }
    @Then("la ficha explica que solo el cliente reserva y el proveedor no tiene reservas nuevas")
    public void proveedorSinReserva() {
        actor().should(seeThat(Text.of(FlujosUi.aviso("solo las cuentas de cliente")), containsString("Tu cuenta es de proveedor")),
                seeThat(Question.about("no se muestra una confirmación de reserva")
                        .answeredBy(a -> ReservaYa.RESERVA_CONFIRMADA.resolveAllFor(a).stream().noneMatch(e -> e.isCurrentlyVisible())), is(true)));
        actor().attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/panel/proveedor?seccion=reservas"),
                WaitUntil.the(FlujosUi.xpath("historial vacío", "//main//*[normalize-space()='Todavía no hay reservas']"), isVisible()).forNoMoreThan(15).seconds());
    }
    @When("filtra canceladas y despliega los cambios de su reserva")
    public void filtrarHistorial() {
        actor().attemptsTo(ConsultarReservaWeb.recargando(datos()),
                SelectFromOptions.byValue("CANCELADA").from(FlujosUi.FILTRO_ESTADO),
                WaitUntil.the(ReservaYa.estadoReserva(datos().reserva, "CANCELADA"), isVisible()).forNoMoreThan(15).seconds(),
                Click.on(FlujosUi.historial(datos().reserva)));
    }
    @Then("el historial identifica al cliente como autor de la cancelacion y su fecha")
    public void autorCliente() {
        actor().should(seeThat(ControlesVisibles.auditoriaCancelacion(datos().reserva, datos().cliente.nombre, "CLIENTE"), is(true)));
    }
    @When("despliega los cambios de la reserva cancelada por el proveedor")
    public void desplegarProveedor() {
        actor().attemptsTo(ConsultarReservaWeb.recargando(datos()), Click.on(FlujosUi.historial(datos().reserva)));
    }
    @Then("el historial identifica al proveedor como autor de la cancelacion y su fecha")
    public void autorProveedor() {
        actor().should(seeThat(ControlesVisibles.auditoriaCancelacion(datos().reserva, datos().proveedor.nombre, "PROVEEDOR"), is(true)));
    }
    @Then("la reserva cancelada no ofrece otra accion de cancelacion")
    public void noRepetirCancelacion() {
        actor().attemptsTo(ConsultarReservaWeb.recargando(datos()));
        actor().should(seeThat(ControlesVisibles.cancelacionSinBoton(datos().reserva), is(true)));
    }
    @Then("el cliente recibe un correo que identifica la reserva cancelada")
    public void notificacion() { actor().attemptsTo(LeerNotificacionCancelacionWeb.de(datos())); }
}

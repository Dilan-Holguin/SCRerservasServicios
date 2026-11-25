package co.edu.udea.certificacion.prueba.stepdefinitions;

import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.models.*;
import co.edu.udea.certificacion.prueba.questions.*;
import co.edu.udea.certificacion.prueba.tasks.*;
import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.questions.Text;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

/** Traduce Gherkin a acciones y preguntas Screenplay; las precondiciones también usan la UI. */
public class UiStepDefinitions {
    private DatosEscenario datos;
    private Actor actor;

    @Before("@ui") public void preparar() {
        OnStage.setTheStage(new OnlineCast());
        actor = OnStage.theActorCalled("Usuario de reservas");
        actor.can(BrowseTheWeb.with(Serenity.getDriver()));
        datos = new DatosEscenario();
        actor.remember(DatosEscenario.CLAVE, datos);
    }

    @After("@ui") public void limpiar(Scenario escenario) {
        try {
            if (datos != null) actor.attemptsTo(LimpiarNegocioWeb.de(datos));
        } catch (RuntimeException | AssertionError e) {
            if (!escenario.isFailed()) throw e;
            escenario.log("No se pudo completar la limpieza UI: " + e.getClass().getSimpleName());
        } finally {
            if (actor != null) {
                var navegar = actor.abilityTo(BrowseTheWeb.class);
                if (navegar != null) net.serenitybdd.core.eventbus.Broadcaster.getEventBus().unregister(navegar);
            }
            OnStage.drawTheCurtain();
            Serenity.getWebdriverManager().closeCurrentDrivers();
        }
    }

    @Given("un visitante con datos nuevos de tipo {string}")
    public void visitante(String rol) { datos.cuenta = new CuentaPrueba(rol); }

    @When("crea su cuenta desde el formulario web")
    public void registrar() { actor.attemptsTo(RegistrarCuentaWeb.con(datos.cuenta)); }

    @Then("ve la confirmacion del registro con su correo y rol")
    public void registroVisible() {
        actor.should(seeThat(InformacionVisible.registro(), allOf(
                containsString(datos.cuenta.email), containsString(datos.cuenta.rol), containsString("PENDIENTE_VERIFICACION"))));
    }

    @When("abre en el frontend el enlace recibido por correo")
    public void verificar() { actor.attemptsTo(VerificarCuentaWeb.de(datos.cuenta)); }

    @When("ingresa desde el formulario con su clave correcta")
    public void ingresar() {
        actor.attemptsTo(IniciarSesionWeb.con(datos.cuenta.email, datos.cuenta.clave));
        datos.rolActual = datos.cuenta.rol;
    }

    @Then("llega al panel web de {string}")
    public void panel(String rol) {
        actor.attemptsTo(Esperar.visible(ReservaYa.panel(rol)));
        actor.should(seeThat(Text.of(ReservaYa.panel(rol)), equalTo(rol.equals("CLIENTE") ? "Mis reservas" : "Mi negocio")));
    }

    @Given("un cliente web con cuenta {string}")
    public void cuenta(String estado) {
        datos.cuenta = new CuentaPrueba("CLIENTE");
        actor.attemptsTo(RegistrarCuentaWeb.con(datos.cuenta));
        if (estado.equals("verificada")) actor.attemptsTo(VerificarCuentaWeb.de(datos.cuenta));
    }

    @When("ingresa desde el formulario con una clave equivocada")
    public void claveErronea() {
        actor.attemptsTo(IniciarSesionWeb.con(datos.cuenta.email, "Clave-equivocada-2026"));
    }

    @Then("ve un aviso de ingreso {string} y permanece en el formulario")
    public void errorVisible(String tipo) {
        actor.attemptsTo(Esperar.visible(ReservaYa.ALERTA));
        actor.should(seeThat(Text.of(ReservaYa.ALERTA),
                containsString(tipo.equals("sin verificar") ? "Abre el enlace" : "Revisa el correo")),
                seeThat(InformacionVisible.formularioIngreso(), is(true)));
    }

    @Given("un proveedor web con servicio {string}")
    public void proveedor(String estadoAgenda) {
        actor.attemptsTo(PrepararNegocioWeb.para(datos, 10, estadoAgenda.equals("con agenda"), false));
    }

    @When("publica un bloque semanal de ocho a doce desde su panel")
    public void crearAgenda() {
        actor.attemptsTo(CrearAgendaWeb.para(datos.servicio, datos.fecha.getDayOfWeek().getValue(), "08:00", "12:00"));
    }

    @Then("ve el bloque publicado y ocho turnos disponibles en la ficha web")
    public void verificarAgenda() {
        actor.attemptsTo(Esperar.visible(ReservaYa.BLOQUE_CREADO));
        actor.should(seeThat(InformacionVisible.agendaOriginal(), is(true)));
        actor.attemptsTo(ConsultarDisponibilidadWeb.de(datos));
        actor.should(seeThat(InformacionVisible.cantidadTurnos(), equalTo(8)));
    }

    @When("intenta publicar un bloque solapado desde su panel")
    public void solapamiento() {
        actor.attemptsTo(CrearAgendaWeb.para(datos.servicio, datos.fecha.getDayOfWeek().getValue(), "09:00", "11:00"));
    }

    @Then("ve el error de agenda y se conserva el bloque original")
    public void verificarSolapamiento() {
        actor.attemptsTo(Esperar.visible(ReservaYa.ALERTA));
        actor.should(seeThat(Text.of(ReservaYa.ALERTA), containsString("solap")),
                seeThat(InformacionVisible.agendaOriginal(), is(true)));
        actor.attemptsTo(ConsultarDisponibilidadWeb.de(datos));
        actor.should(seeThat(InformacionVisible.cantidadTurnos(), equalTo(8)));
    }

    @Given("un cliente web y un turno disponible dentro de {int} dias")
    public void turno(int dias) { actor.attemptsTo(PrepararNegocioWeb.para(datos, dias, true, true)); }

    @When("elige el turno de las nueve desde la disponibilidad web")
    public void reservar() {
        actor.attemptsTo(ReservarTurnoWeb.para(datos.servicio, datos.fecha.toString(), "09:00"));
    }

    @Then("ve la reserva confirmada y guardada en su historial")
    public void reservaConfirmada() {
        actor.attemptsTo(Esperar.visible(ReservaYa.RESERVA_CONFIRMADA),
                ConsultarReservaDelCliente.de(datos), RecargarHistorialWeb.de(datos));
        actor.should(seeThat(EstadoReservaEnPantalla.es(datos.reserva, "CONFIRMADA"), is(true)));
    }

    @Given("una reserva web confirmada dentro de {int} dias para {string}")
    public void prepararReserva(int dias, String rol) {
        turno(dias);
        reservar();
        reservaConfirmada();
        if (rol.equals("PROVEEDOR")) {
            datos.cuenta = datos.proveedor;
            ingresar();
            panel("PROVEEDOR");
            actor.attemptsTo(RecargarHistorialWeb.de(datos));
        }
    }

    @When("cancela la reserva desde su panel y confirma")
    public void cancelar() { actor.attemptsTo(CancelarReservaWeb.conConfirmacion(datos.reserva, true)); }

    @When("abre la cancelacion y decide conservar la reserva")
    public void conservar() { actor.attemptsTo(CancelarReservaWeb.conConfirmacion(datos.reserva, false)); }

    @Then("la reserva web aparece {string} y persiste al recargar el historial")
    public void estadoPersistido(String estado) {
        if (estado.equals("CANCELADA")) actor.attemptsTo(
                Esperar.visible(ReservaYa.cancelacionConfirmada(datos.reserva)));
        else actor.attemptsTo(Esperar.noVisible(ReservaYa.DIALOGO));
        actor.attemptsTo(RecargarHistorialWeb.de(datos));
        actor.should(seeThat(EstadoReservaEnPantalla.es(datos.reserva, estado), is(true)));
    }

    @Then("el cupo vuelve a estar disponible")
    public void cupoLiberado() {
        actor.attemptsTo(ConsultarDisponibilidadWeb.de(datos));
        actor.should(seeThat(InformacionVisible.cupos("09:00"), equalTo("1 cupo libre")));
    }

    @Then("la interfaz explica el plazo y no ofrece cancelar")
    public void plazo() {
        actor.should(seeThat(InformacionVisible.plazoSinCancelar(datos.reserva), is(true)));
        actor.attemptsTo(RecargarHistorialWeb.de(datos));
        actor.should(seeThat(EstadoReservaEnPantalla.es(datos.reserva, "CONFIRMADA"), is(true)),
                seeThat(InformacionVisible.plazoSinCancelar(datos.reserva), is(true)));
    }
}

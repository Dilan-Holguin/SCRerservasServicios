package co.edu.udea.certificacion.prueba.stepdefinitions;

import co.edu.udea.certificacion.prueba.tasks.*;
import co.edu.udea.certificacion.prueba.questions.ControlesVisibles;
import co.edu.udea.certificacion.prueba.userinterfaces.*;
import io.cucumber.java.en.*;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import java.util.List;
import static co.edu.udea.certificacion.prueba.support.EscenarioUi.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;
import static org.hamcrest.Matchers.*;

public class AgendaAmpliadaStepDefinitions {
    @When("publica desde la UI un bloque de {string} a {string}")
    public void publicar(String desde, String hasta) {
        actor().attemptsTo(GestionarAgendaWeb.crear(datos().servicio, List.of(datos().fecha.getDayOfWeek().getValue()), desde, hasta));
    }
    @When("intenta crear la agenda sin seleccionar dias")
    public void sinDias() {
        actor().attemptsTo(GestionarAgendaWeb.crear(datos().servicio, List.of(), "08:00", "12:00"));
    }
    @Then("la agenda rechaza el cambio y continua sin bloques")
    public void agendaInvalida() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(Text.of(ReservaYa.ALERTA), containsString("No se pudo aplicar el cambio")));
        actor().attemptsTo(GestionarAgendaWeb.abrir(datos().servicio),
                WaitUntil.the(FlujosUi.SIN_AGENDA, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(ControlesVisibles.bloques(), equalTo(0)));
    }
    @When("publica un mismo rango para dos dias distintos")
    public void variosDias() {
        int dia = datos().fecha.getDayOfWeek().getValue();
        actor().attemptsTo(GestionarAgendaWeb.crear(datos().servicio, List.of(dia, dia % 7 + 1), "08:00", "12:00"));
    }
    @Then("la agenda conserva un bloque por cada dia elegido")
    public void dosBloques() {
        actor().attemptsTo(WaitUntil.the(FlujosUi.aviso("Se crearon 2 bloques"), isVisible()).forNoMoreThan(15).seconds(),
                GestionarAgendaWeb.abrir(datos().servicio));
        actor().should(eventually(seeThat(ControlesVisibles.bloques(), equalTo(2))));
        var locale = java.util.Locale.forLanguageTag("es");
        String primero = datos().fecha.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, locale);
        String segundo = datos().fecha.plusDays(1).getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, locale);
        actor().should(seeThat(ControlesVisibles.textoBloques(), allOf(
                containsString(primero.substring(1)), containsString(segundo.substring(1)),
                containsString("08:00"), containsString("12:00"))));
    }
    @Given("una agenda UI con reserva confirmada en el bloque original")
    public void agendaConReserva() {
        actor().attemptsTo(FlujoReservaWeb.preparada(datos(), "PROVEEDOR"));
        actor().attemptsTo(GestionarAgendaWeb.abrir(datos().servicio));
    }
    @When("edita el bloque original de {string} a {string}")
    public void editar(String desde, String hasta) { actor().attemptsTo(GestionarAgendaWeb.editar(desde, hasta)); }
    @Then("el bloque editado persiste de {string} a {string}")
    public void cambioPersistido(String desde, String hasta) {
        actor().attemptsTo(WaitUntil.the(FlujosUi.aviso("Bloque actualizado"), isVisible()).forNoMoreThan(15).seconds(),
                GestionarAgendaWeb.abrir(datos().servicio),
                WaitUntil.the(FlujosUi.bloque(desde, hasta), isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(ControlesVisibles.bloques(), equalTo(1)));
    }
    @Then("el dialogo informa la reserva afectada sin aplicar el cambio")
    public void advertir() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.DIALOGO, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(Text.of(ReservaYa.DIALOGO), allOf(
                containsString("Este cambio afecta reservas confirmadas"), containsString("Reserva #" + datos().reserva))));
    }
    @When("desiste del cambio de agenda")
    public void desistir() { actor().attemptsTo(GestionarAgendaWeb.resolverDialogo("Cancelar")); }
    @When("confirma la edicion a pesar de las reservas afectadas")
    public void forzarEdicion() { actor().attemptsTo(GestionarAgendaWeb.resolverDialogo("Aplicar de todos modos")); }
    @Then("el bloque original permanece publicado de ocho a doce")
    public void original() {
        actor().attemptsTo(GestionarAgendaWeb.abrir(datos().servicio),
                WaitUntil.the(FlujosUi.bloque("08:00", "12:00"), isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(ControlesVisibles.bloques(), equalTo(1)));
    }
    @Then("la reserva afectada permanece confirmada en el historial")
    public void reservaConservada() {
        actor().attemptsTo(ConsultarReservaWeb.recargando(datos()),
                WaitUntil.the(ReservaYa.estadoReserva(datos().reserva, "CONFIRMADA"), isVisible()).forNoMoreThan(15).seconds());
    }
    @Then("la edicion invalida muestra error y conserva el horario original")
    public void errorEdicion() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(Text.of(ReservaYa.ALERTA), containsString("No se pudo aplicar el cambio")));
        original();
    }
    @Given("la agenda incluye ademas un bloque de catorce a dieciseis")
    public void bloqueAdicional() {
        actor().attemptsTo(CrearAgendaWeb.para(datos().servicio, datos().fecha.getDayOfWeek().getValue(), "14:00", "16:00"),
                WaitUntil.the(ReservaYa.BLOQUE_CREADO, isVisible()).forNoMoreThan(15).seconds());
    }
    @Then("la edicion solapada conserva los dos bloques originales")
    public void solapada() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(Text.of(ReservaYa.ALERTA), containsString("solap")));
        actor().attemptsTo(GestionarAgendaWeb.abrir(datos().servicio),
                WaitUntil.the(FlujosUi.bloque("14:00", "16:00"), isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(ControlesVisibles.bloques(), equalTo(2)),
                seeThat(ControlesVisibles.textoBloques(), allOf(containsString("08:00"), containsString("12:00"), containsString("14:00"), containsString("16:00"))));
    }
    @When("solicita eliminar el bloque original desde su agenda")
    public void eliminar() { actor().attemptsTo(GestionarAgendaWeb.eliminar()); }
    @When("confirma eliminar el bloque a pesar de sus reservas")
    public void forzarBorrado() { actor().attemptsTo(GestionarAgendaWeb.resolverDialogo("Eliminar de todos modos")); }
    @Then("la agenda confirma la eliminacion y queda sin bloques al recargar")
    public void eliminado() {
        actor().attemptsTo(WaitUntil.the(FlujosUi.aviso("Bloque eliminado"), isVisible()).forNoMoreThan(15).seconds(),
                GestionarAgendaWeb.abrir(datos().servicio),
                WaitUntil.the(FlujosUi.SIN_AGENDA, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(ControlesVisibles.bloques(), equalTo(0)));
    }
}

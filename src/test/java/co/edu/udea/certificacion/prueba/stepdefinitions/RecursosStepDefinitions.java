package co.edu.udea.certificacion.prueba.stepdefinitions;

import co.edu.udea.certificacion.prueba.utils.Esperar;
import co.edu.udea.certificacion.prueba.tasks.*;
import co.edu.udea.certificacion.prueba.userinterfaces.*;
import io.cucumber.java.en.*;
import net.serenitybdd.screenplay.questions.Text;
import static co.edu.udea.certificacion.prueba.support.EscenarioUi.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class RecursosStepDefinitions {
    @When("crea un recurso UI con nombre unico y tipo disponible")
    public void crearRecurso() {
        datos().nombreRecurso = "Recurso " + java.util.UUID.randomUUID();
        actor().attemptsTo(CrearRecursoWeb.con(datos().nombreRecurso, true),
                Esperar.visible(FlujosUi.recurso(datos().nombreRecurso)));
    }
    @Then("el recurso aparece activo en el listado despues de recargar")
    public void recursoPersistido() {
        actor().attemptsTo(AbrirRecursosWeb.delPanel(),
                Esperar.visible(FlujosUi.recurso(datos().nombreRecurso)));
        actor().should(seeThat(Text.of(FlujosUi.recurso(datos().nombreRecurso)), allOf(containsString(datos().nombreRecurso), containsString("Activo"))));
    }
    @Given("un recurso UI activo del proveedor para asignar")
    public void recursoPrevio() { crearRecurso(); }
    @When("asigna su recurso al servicio desde el panel")
    public void asignar() { actor().attemptsTo(AsignarRecursoWeb.al(datos().servicio, datos().nombreRecurso, true)); }
    @Then("ve la confirmacion de un recurso asignado")
    public void asignado() {
        actor().should(seeThat(Text.of(FlujosUi.aviso("1 recurso(s)")), containsString("El servicio quedó con 1 recurso(s) asignado(s).")));
    }
    @When("completa un recurso sin {string}")
    public void incompleto(String faltante) {
        datos().nombreRecurso = "Recurso " + java.util.UUID.randomUUID();
        actor().attemptsTo(CompletarRecursoWeb.con(faltante.equals("nombre") ? "" : datos().nombreRecurso, !faltante.equals("tipo")));
    }
    @Then("la interfaz mantiene deshabilitada la creacion del recurso")
    public void deshabilitado() {
        actor().attemptsTo(Esperar.deshabilitado(FlujosUi.boton("Crear recurso")));
    }
    @Given("dos servicios UI con un recurso compartido ocupado a las nueve")
    public void compartidos() { actor().attemptsTo(PrepararRecursosCompartidosWeb.para(datos())); }
    @When("el segundo cliente consulta el segundo servicio en el mismo horario")
    public void consultarSegundo() {
        actor().attemptsTo(ConsultarTurnosWeb.de(datos().servicioSecundario, datos().fecha.toString(), datos().fecha.toString()));
    }
    @When("el cliente original cancela la reserva que ocupa el recurso")
    public void liberar() {
        actor().attemptsTo(AccederConCuentaWeb.de(datos(), datos().cliente), RecargarHistorialWeb.de(datos()),
                CancelarReservaWeb.conConfirmacion(datos().reserva, true),
                Esperar.visible(ReservaYa.cancelacionConfirmada(datos().reserva)),
                AccederConCuentaWeb.de(datos(), datos().otroCliente));
    }
    @Then("el segundo cliente puede reservar el recurso liberado desde la ficha")
    public void reservarRecurso() {
        actor().attemptsTo(ReservarYGuardarWeb.en(datos(), datos().servicioSecundario, datos().serviciosExtra.get(datos().servicioSecundario)));
    }
    @When("retira todos los recursos asignados al servicio")
    public void desasignar() { actor().attemptsTo(AsignarRecursoWeb.al(datos().servicio, datos().nombreRecurso, false)); }
    @Then("ve la confirmacion de recursos retirados")
    public void retirados() {
        actor().should(seeThat(Text.of(FlujosUi.aviso("Se quitaron todos")), containsString("Se quitaron todos los recursos del servicio.")));
    }
}

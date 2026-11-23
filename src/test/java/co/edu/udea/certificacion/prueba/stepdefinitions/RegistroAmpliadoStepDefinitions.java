package co.edu.udea.certificacion.prueba.stepdefinitions;

import co.edu.udea.certificacion.prueba.models.CuentaPrueba;
import co.edu.udea.certificacion.prueba.interactions.LeerCorreoVerificacion;
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
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;
import static org.hamcrest.Matchers.*;

public class RegistroAmpliadoStepDefinitions {
    @Given("una cuenta UI de tipo {string} con correo ocupado")
    public void duplicada(String rol) {
        datos().cuenta = new CuentaPrueba(rol);
        if (rol.equals("PROVEEDOR")) datos().proveedor = datos().cuenta;
        actor().attemptsTo(RegistrarCuentaWeb.con(datos().cuenta), VerificarCuentaWeb.de(datos().cuenta));
    }
    @When("envia el registro web con {string}")
    public void registroInvalido(String defecto) {
        actor().attemptsTo(CompletarRegistroWeb.con(datos().cuenta, defecto), Click.on(ReservaYa.CREAR_CUENTA));
    }
    @Then("el registro muestra correo duplicado y conserva el formulario")
    public void correoDuplicado() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(Text.of(ReservaYa.ALERTA), containsString("ya tiene cuenta")),
                seeThat(ControlesVisibles.formularioRegistro(), is(true)));
    }
    @Then("el registro marca el campo {string} como invalido")
    public void campoInvalido(String campo) {
        actor().attemptsTo(WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds(),
                WaitUntil.the(FlujosUi.errorCampo(campo), isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(ControlesVisibles.formularioRegistro(), is(true)));
    }
    @Then("el registro exige al menos un servicio")
    public void servicioRequerido() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(Text.of(ReservaYa.ALERTA), containsString("Completa al menos un servicio")),
                seeThat(ControlesVisibles.formularioRegistro(), is(true)));
    }
    @When("realiza cinco ingresos fallidos y luego usa la clave correcta")
    public void bloquear() {
        for (int intento = 0; intento < 5; intento++) {
            actor().attemptsTo(IniciarSesionWeb.con(datos().cuenta.email, "Incorrecta-2026"),
                    WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
            actor().should(seeThat(ControlesVisibles.mensajeCredenciales(), containsString("incorrectos")));
        }
        actor().attemptsTo(IniciarSesionWeb.con(datos().cuenta.email, datos().cuenta.clave));
    }
    @Then("el ingreso informa bloqueo temporal por quince minutos")
    public void bloqueoVisible() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(Text.of(ReservaYa.ALERTA), containsString("bloqueada temporalmente")),
                seeThat(Text.of(ReservaYa.ALERTA), containsString("15 minutos")),
                seeThat(InformacionVisible.formularioIngreso(), is(true)));
    }
    @When("abre un enlace web de verificacion invalido")
    public void enlaceInvalido() { actor().attemptsTo(AbrirVerificacionWeb.conToken("enlace-ui-invalido")); }
    @Then("la verificacion muestra error y ofrece reenviar el correo")
    public void errorVerificacion() {
        actor().attemptsTo(WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds(),
                WaitUntil.the(FlujosUi.boton("Reenviar verificación"), isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(Text.of(ReservaYa.ALERTA), containsString("No pudimos verificar la cuenta")));
    }
    @When("guarda el enlace de verificacion recibido")
    public void guardarEnlace() {
        actor().attemptsTo(LeerCorreoVerificacion.de(datos().cuenta.email));
        datos().tokenVerificacion = actor().recall(LeerCorreoVerificacion.TOKEN);
        actor().forget(LeerCorreoVerificacion.TOKEN);
    }
    @When("vuelve a abrir el enlace de verificacion ya utilizado")
    public void reutilizarEnlace() { actor().attemptsTo(AbrirVerificacionWeb.conToken(datos().tokenVerificacion)); }
    @When("solicita el reenvio desde la pagina de verificacion")
    public void reenviar() {
        actor().attemptsTo(Open.url(ConfiguracionUi.webUrl() + "/verificacion"),
                WaitUntil.the(ReservaYa.EMAIL, isVisible()).forNoMoreThan(15).seconds(),
                Enter.theValue(datos().cuenta.email).into(ReservaYa.EMAIL),
                Click.on(FlujosUi.boton("Reenviar verificación")));
    }
    @Then("ve la respuesta generica de reenvio y recibe otro enlace")
    public void reenvioConfirmado() {
        actor().attemptsTo(WaitUntil.the(FlujosUi.css("respuesta de reenvío", "section[aria-labelledby='titulo-reenvio'] [role='status']"),
                isVisible()).forNoMoreThan(15).seconds());
        actor().should(seeThat(ControlesVisibles.correoMensaje(), not(isEmptyOrNullString())));
        actor().attemptsTo(LeerCorreoVerificacion.de(datos().cuenta.email));
        String nuevo = actor().recall(LeerCorreoVerificacion.TOKEN);
        actor().should(seeThat(Question.about("el correo contiene un enlace renovado")
                .answeredBy(a -> nuevo != null && !nuevo.equals(datos().tokenVerificacion)), is(true)));
        actor().forget(LeerCorreoVerificacion.TOKEN);
    }
    @When("compara el rechazo de una clave errada con el de un correo inexistente")
    public void compararMensajes() {
        actor().attemptsTo(IniciarSesionWeb.con(datos().cuenta.email, "Incorrecta-2026"),
                WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
        datos().mensajeIngreso = actor().asksFor(ControlesVisibles.mensajeCredenciales());
        actor().attemptsTo(IniciarSesionWeb.con(new CuentaPrueba("CLIENTE").email, "Incorrecta-2026"),
                WaitUntil.the(ReservaYa.ALERTA, isVisible()).forNoMoreThan(15).seconds());
    }
    @Then("ambos rechazos muestran el mismo mensaje generico")
    public void mensajeGenerico() {
        actor().should(seeThat(ControlesVisibles.mensajeCredenciales(), equalTo(datos().mensajeIngreso)),
                seeThat(InformacionVisible.formularioIngreso(), is(true)));
    }
    @When("cierra sesion e intenta volver directamente al panel del cliente")
    public void salir() {
        actor().attemptsTo(CerrarSesionWeb.siExiste(), Open.url(ConfiguracionUi.webUrl() + "/panel/cliente"),
                WaitUntil.the(ReservaYa.INGRESAR, isVisible()).forNoMoreThan(15).seconds());
    }
    @Then("el panel protegido exige iniciar sesion otra vez")
    public void exigirSesion() {
        actor().should(seeThat(InformacionVisible.formularioIngreso(), is(true)));
    }
}

package co.edu.udea.certificacion.prueba.questions;

import co.edu.udea.certificacion.prueba.userinterfaces.*;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import java.time.Instant;

public final class ControlesVisibles {
    private ControlesVisibles() { }
    public static Question<Integer> bloques() {
        return Question.about("cantidad de bloques publicados").answeredBy(actor -> FlujosUi.BLOQUES.resolveAllFor(actor).size());
    }
    public static Question<String> textoBloques() {
        return Question.about("días y rangos de los bloques").answeredBy(actor ->
                String.join(" | ", FlujosUi.BLOQUES.resolveAllFor(actor).stream().map(e -> e.getText()).toList()));
    }
    public static Question<Boolean> turnoAusente(String hora) {
        return Question.about("turno " + hora + " ausente de la oferta").answeredBy(actor ->
                ReservaYa.turno(hora).resolveAllFor(actor).stream().noneMatch(e -> e.isCurrentlyVisible()));
    }
    public static Question<Boolean> cancelacionSinBoton(int id) {
        return Question.about("reserva cancelada sin otra opción de cancelar").answeredBy(actor ->
                ReservaYa.estadoReserva(id, "CANCELADA").resolveFor(actor).isVisible()
                && ReservaYa.cancelar(id).resolveAllFor(actor).isEmpty());
    }
    public static Question<Boolean> auditoriaCancelacion(int id, String nombre, String rol) {
        return Question.about("cambio de estado con autor y fecha").answeredBy(actor -> {
            var cambio = FlujosUi.cambioCancelado(id).resolveFor(actor);
            String titulo = cambio.findElement(By.cssSelector(".linea-tiempo__titulo")).getText();
            String autor = cambio.findElement(By.cssSelector(".linea-tiempo__autor")).getText();
            String fecha = cambio.findElement(By.cssSelector("time")).getAttribute("datetime");
            return titulo.contains("CONFIRMADA") && titulo.contains("CANCELADA")
                    && autor.contains(nombre) && autor.contains(rol) && Instant.parse(fecha) != null;
        });
    }
    public static Question<Boolean> fechasDelRango(String desde, String hasta) {
        return Question.about("fechas visibles del rango de 32 días").answeredBy(actor ->
                desde.equals(FlujosUi.DESDE.resolveFor(actor).getDomProperty("value"))
                && hasta.equals(FlujosUi.HASTA.resolveFor(actor).getDomProperty("value")));
    }
    public static Question<String> url() {
        return Question.about("dirección de la página").answeredBy(actor -> BrowseTheWeb.as(actor).getDriver().getCurrentUrl());
    }
    public static Question<String> mensajeCredenciales() {
        return Question.about("mensaje genérico de credenciales").answeredBy(actor ->
                BrowseTheWeb.as(actor).getDriver().findElement(By.cssSelector("[role='alert'] .estado__detalle")).getText());
    }
    public static Question<Boolean> formularioRegistro() {
        return Question.about("permanencia en registro").answeredBy(actor ->
                ReservaYa.CREAR_CUENTA.resolveFor(actor).isVisible() && BrowseTheWeb.as(actor).getDriver().getCurrentUrl().endsWith("/registro"));
    }
    public static Question<String> correoMensaje() {
        return Question.about("mensaje de reenvío").answeredBy(actor ->
                BrowseTheWeb.as(actor).getDriver().findElement(By.cssSelector("section[aria-labelledby='titulo-reenvio'] [role='status']")).getText());
    }
}

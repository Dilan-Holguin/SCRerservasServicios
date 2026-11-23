package co.edu.udea.certificacion.prueba.questions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

public final class InformacionVisible {
    private InformacionVisible() { }
    public static Question<String> registro() {
        return Question.about("confirmación del registro").answeredBy(actor ->
                BrowseTheWeb.as(actor).getDriver().findElement(By.tagName("main")).getText());
    }
    public static Question<Boolean> formularioIngreso() {
        return Question.about("permanencia en el formulario de ingreso").answeredBy(actor ->
                ReservaYa.INGRESAR.resolveFor(actor).isVisible()
                && BrowseTheWeb.as(actor).getDriver().getCurrentUrl().endsWith("/ingresar"));
    }
    public static Question<Boolean> agendaOriginal() {
        return Question.about("único bloque original de 08:00 a 12:00").answeredBy(actor -> {
            var bloques = BrowseTheWeb.as(actor).getDriver().findElements(By.cssSelector(".lista-bloques .bloque__texto"));
            return bloques.size() == 1 && bloques.get(0).getText().contains("08:00") && bloques.get(0).getText().contains("12:00");
        });
    }
    public static Question<Integer> cantidadTurnos() {
        return Question.about("cantidad de turnos ofrecidos").answeredBy(actor ->
                BrowseTheWeb.as(actor).getDriver().findElements(By.cssSelector("[role='grid'] button")).size());
    }
    public static Question<String> cupos(String hora) {
        return Question.about("cupos visibles del turno " + hora).answeredBy(actor ->
                ReservaYa.turno(hora).resolveFor(actor).findElement(By.cssSelector(".turno__cupos")).getText());
    }
    public static Question<Boolean> plazoSinCancelar(int id) {
        return Question.about("explicación de cinco días y ausencia del botón de cancelación").answeredBy(actor -> {
            var tarjeta = ReservaYa.tarjeta(id).resolveFor(actor);
            return tarjeta.getText().contains("5 días")
                    && tarjeta.findElements(By.xpath(".//button[normalize-space()='Cancelar reserva']")).isEmpty();
        });
    }
}

package co.edu.udea.certificacion.prueba.questions;

import co.edu.udea.certificacion.prueba.userinterfaces.ReservaYa;
import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.By;

public final class DatosEnPantalla {
    private DatosEnPantalla() { }
    public static Question<Integer> idServicio(String nombre) {
        return Question.about("identificador de la ficha pública del servicio").answeredBy(actor -> {
            String enlace = ReservaYa.fichaServicio(nombre).resolveFor(actor).getAttribute("href");
            return Integer.parseInt(enlace.substring(enlace.lastIndexOf('/') + 1));
        });
    }
    public static Question<Integer> idReserva(String servicio) {
        return Question.about("identificador mostrado en el historial").answeredBy(actor -> {
            String texto = ReservaYa.reservaDelServicio(servicio).resolveFor(actor)
                    .findElement(By.xpath(".//li[span[normalize-space()='Reserva']]/span[contains(@class,'atributos__valor')]")).getText();
            return Integer.parseInt(texto.replace("#", "").trim());
        });
    }
}

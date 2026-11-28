package co.edu.udea.certificacion.prueba.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class Mailpit {
    private Mailpit() { }
    public static final Target BUSQUEDA = Target.the("buscar el correo de la cuenta")
            .located(By.cssSelector("input[aria-label*='Search'], input[placeholder*='Search']"));
    public static final Target TEXTO = Target.the("pestaña de texto del correo")
            .located(By.xpath("//*[@role='tab' and normalize-space()='Text']"));
    public static final Target ENLACE = Target.the("enlace de verificación del correo")
            .located(By.cssSelector("#nav-plain-text a[href*='token=']"));
    public static final By CUERPO = By.cssSelector("#nav-plain-text");
    public static By selectorMensaje(String email) {
        return By.xpath("//a[starts-with(@href,'/view/') and contains(.,'" + email + "')]");
    }
    public static Target mensaje(String email) {
        return Target.the("correo de verificación de la cuenta actual")
                .located(selectorMensaje(email));
    }
}

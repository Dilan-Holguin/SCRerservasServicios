package co.edu.udea.certificacion.prueba.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Controles de la ampliación; no contiene acciones ni acceso a infraestructura. */
public final class FlujosUi {
    private FlujosUi() { }
    public static Target css(String nombre, String selector) { return Target.the(nombre).located(By.cssSelector(selector)); }
    public static Target xpath(String nombre, String selector) { return Target.the(nombre).located(By.xpath(selector)); }
    public static Target boton(String texto) { return xpath(texto, "//button[normalize-space()='" + texto + "']"); }
    public static Target modal(String texto) { return xpath(texto, "//*[@role='dialog']//button[normalize-space()='" + texto + "']"); }
    public static Target campo(String seccion, String etiqueta) {
        return xpath(etiqueta, "//section[@aria-labelledby='" + seccion + "']//label[normalize-space()='" + etiqueta + "']/following-sibling::*[self::input or self::select]");
    }
    public static Target campoRegistro(String nombre) { return css(nombre, "[name='" + nombre + "']"); }
    public static Target errorCampo(String nombre) { return css("validación de " + nombre, "[name='" + nombre + "'][aria-invalid='true']"); }
    public static final Target BLOQUES = css("bloques definidos", ".lista-bloques .bloque__texto");
    public static final Target EDITAR_BLOQUE = xpath("editar bloque original", "(//div[@class='bloque'][p[contains(.,'08:00') and contains(.,'12:00')]]//button[starts-with(normalize-space(),'Editar el bloque')])[1]");
    public static final Target ELIMINAR_BLOQUE = xpath("eliminar bloque original", "(//div[@class='bloque'][p[contains(.,'08:00') and contains(.,'12:00')]]//button[starts-with(normalize-space(),'Eliminar el bloque')])[1]");
    public static final Target EDITAR_DESDE = css("inicio de edición", "form.bloque--edicion input[type='time']:first-of-type");
    public static final Target EDICION_INICIO = xpath("inicio del bloque editado", "(//form[contains(@class,'bloque--edicion')]//input[@type='time'])[1]");
    public static final Target EDICION_FIN = xpath("fin del bloque editado", "(//form[contains(@class,'bloque--edicion')]//input[@type='time'])[2]");
    public static final Target SIN_AGENDA = xpath("agenda vacía", "//main//*[normalize-space()='Este servicio aún no tiene agenda']");
    public static final Target DESDE = css("fecha desde", "#rango-desde");
    public static final Target HASTA = css("fecha hasta", "#rango-hasta");
    public static final Target DISPONIBILIDAD = css("disponibilidad", "[aria-labelledby='titulo-disponibilidad']");
    public static final Target SIN_RESERVAS = xpath("historial vacío", "//main//*[normalize-space()='Todavía no hay reservas']");
    public static final Target SIN_TURNOS = xpath("sin turnos", "//main//*[normalize-space()='No hay turnos disponibles en este rango']");
    public static final Target FILTRO_ESTADO = css("filtro de estado del historial", "#filtro-estado");
    public static final Target RECURSO_NOMBRE = campo("titulo-recursos", "Nombre del recurso");
    public static final Target RECURSO_TIPO = campo("titulo-recursos", "Tipo de recurso");
    public static final Target RECURSO_TIPO_CARGADO = xpath("tipo disponible", "(//section[@aria-labelledby='titulo-recursos']//select)[1]/option[2]");
    public static final Target RECURSO_SERVICIO = campo("titulo-recursos", "Servicio");
    public static final Target SERVICIO_NOMBRE = campo("titulo-servicios", "Nombre del servicio");
    public static final Target SERVICIO_CATEGORIA = campo("titulo-servicios", "Categoría");
    public static final Target CATEGORIA_CARGADA = xpath("categoría disponible", "//section[@aria-labelledby='titulo-servicios']//select/option[2]");
    public static Target recurso(String nombre) { return xpath("recurso propio", "//ul[contains(@class,'lista-recursos')]/li[span[normalize-space()='" + nombre + "']]"); }
    public static Target marcarRecurso(String nombre) { return xpath("selección del recurso propio", "//section[@aria-labelledby='titulo-recursos']//label[span[normalize-space()='" + nombre + "']]/input[@type='checkbox']"); }
    public static Target fichaCatalogo(String nombre) { return xpath("ficha del servicio en catálogo", "//article//h3/a[normalize-space()='" + nombre + "']"); }
    public static Target aviso(String texto) { return xpath("aviso " + texto, "//main//*[@role='status' and contains(.,'" + texto + "')]"); }
    public static Target bloque(String desde, String hasta) { return xpath("bloque " + desde + " a " + hasta, "//p[contains(@class,'bloque__texto') and contains(.,'" + desde + "') and contains(.,'" + hasta + "')]"); }
    public static Target historial(int reserva) { return xpath("desplegar cambios de la reserva", "//article[.//span[normalize-space()='#" + reserva + "']]//summary"); }
    public static Target cambioCancelado(int reserva) { return xpath("cambio a cancelada", "//article[.//span[normalize-space()='#" + reserva + "']]//li[contains(@class,'linea-tiempo__paso')][.//p[contains(@class,'linea-tiempo__titulo')]/span[normalize-space()='CANCELADA']]"); }
}

package co.edu.udea.certificacion.prueba.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Localizadores basados en nombres de campo, etiquetas y regiones de la interfaz real. */
public final class ReservaYa {
    private ReservaYa() { }
    public static final Target NOMBRE = css("nombre completo", "input[name='nombreCompleto']");
    public static final Target EMAIL = css("correo", "input[name='email']");
    public static final Target CLAVE = css("contraseña", "input[name='password']");
    public static final Target NOMBRE_SERVICIO = css("nombre del servicio", "input[name='servicios[0].nombre']");
    public static final Target CATEGORIA = css("categoría del servicio", "select[name='servicios[0].idCategoria']");
    public static final Target CUENTA_CREADA = xpath("confirmación del registro", "//h1[normalize-space()='Cuenta creada']");
    public static final Target CUENTA_VERIFICADA = xpath("confirmación de verificación", "//*[@role='status']//p[normalize-space()='Cuenta verificada']");
    public static final Target ALERTA = css("error presentado al usuario", "main [role='alert']");
    public static final Target CREAR_CUENTA = xpath("crear cuenta", "//form//button[@type='submit' and normalize-space()='Crear cuenta']");
    public static final Target INGRESAR = xpath("ingresar", "//form//button[@type='submit' and normalize-space()='Ingresar']");
    public static final Target SELECTOR_SERVICIO = css("servicio de la agenda", "section[aria-labelledby='titulo-agenda'] select");
    public static final Target CREAR_BLOQUES = xpath("crear bloques", "//button[@type='submit' and normalize-space()='Crear bloques']");
    public static final Target HORA_INICIO = xpath("hora inicial", "//form[contains(@class,'formulario--bloque')]//label[normalize-space()='Hora de inicio']/following-sibling::input");
    public static final Target HORA_FIN = xpath("hora final", "//form[contains(@class,'formulario--bloque')]//label[normalize-space()='Hora de fin']/following-sibling::input");
    public static final Target BLOQUE_CREADO = xpath("aviso de agenda creada", "//*[@role='status'][contains(.,'Se creó 1 bloque de agenda.')]");
    public static final Target RESERVA_CONFIRMADA = xpath("aviso de reserva confirmada", "//*[@role='status'][contains(.,'Reserva confirmada para el')]");
    public static final Target DIALOGO = css("confirmación de cancelación", "[role='dialog']");
    public static final Target CONFIRMAR_CANCELACION = xpath("confirmar cancelación", "//*[@role='dialog']//button[normalize-space()='Sí, cancelar la reserva']");
    public static final Target CONSERVAR_RESERVA = xpath("conservar reserva", "//*[@role='dialog']//button[normalize-space()='No, conservarla']");

    public static final Target CERRAR_SESION = xpath("cerrar sesión", "//button[normalize-space()='Cerrar sesión']");
    public static final Target PRIMERA_CATEGORIA = css("primera categoría disponible", "select[name='servicios[0].idCategoria'] option:nth-child(2)");
    public static final Target CONFIRMAR_INACTIVACION = xpath("confirmar inactivación", "//*[@role='dialog']//button[normalize-space()='Sí, inactivarlo']");
    public static Target fichaServicio(String nombre) { return xpath("ficha pública del servicio", "//article[.//h4[normalize-space()='" + nombre + "']]//a[contains(@href,'/servicios/')]"); }
    public static Target reservaDelServicio(String nombre) { return xpath("reserva del servicio del escenario", "//article[.//h3[normalize-space()='" + nombre + "']]"); }
    public static Target inactivar(String nombre) { return xpath("inactivar el servicio del escenario", "//article[.//h4[normalize-space()='" + nombre + "']]//button[normalize-space()='Inactivar servicio']"); }
    public static Target servicioInactivo(String nombre) { return xpath("servicio inactivo", "//article[.//h4[normalize-space()='" + nombre + "']]//span[normalize-space()='INACTIVO']"); }

    public static Target rol(String rol) { return css("rol " + rol, "input[name='rol'][value='" + rol + "']"); }
    public static Target opcionCategoria(int id) { return css("categoría cargada",
            "select[name='servicios[0].idCategoria'] option[value='" + id + "']"); }
    public static Target panel(String rol) { return xpath("panel de " + rol,
            "//h1[normalize-space()='" + (rol.equals("CLIENTE") ? "Mis reservas" : "Mi negocio") + "']"); }
    public static Target dia(int dia) { return xpath("día de la semana " + dia,
            "(//section[@aria-labelledby='titulo-agenda']//input[@type='checkbox'])[" + dia + "]"); }
    public static Target turno(String hora) { return xpath("turno " + hora,
            "//*[@role='grid']//button[span[contains(@class,'turno__hora') and normalize-space()='" + hora + "']]"); }
    public static Target tarjeta(int id) { return xpath("reserva " + id,
            "//article[.//span[normalize-space()='#" + id + "']]"); }
    public static Target estadoReserva(int id, String estado) { return xpath("reserva " + id + " en estado " + estado,
            "//article[.//span[normalize-space()='#" + id + "']]//span[contains(@class,'insignia') and normalize-space()='" + estado + "']"); }
    public static Target cancelar(int id) { return xpath("cancelar reserva " + id,
            "//article[.//span[normalize-space()='#" + id + "']]//button[normalize-space()='Cancelar reserva']"); }
    public static Target cancelacionConfirmada(int id) { return xpath("cancelación confirmada " + id,
            "//*[@role='status'][contains(.,'La reserva #" + id + " de ') and contains(.,'quedó cancelada')]"); }

    private static Target css(String nombre, String selector) { return Target.the(nombre).located(By.cssSelector(selector)); }
    private static Target xpath(String nombre, String selector) { return Target.the(nombre).located(By.xpath(selector)); }
}

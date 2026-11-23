package co.edu.udea.certificacion.prueba.utils;

public final class ConfiguracionUi {
    private ConfiguracionUi() { }
    public static String webUrl() { return valor("reservaya.web.url", "RESERVAYA_WEB_URL", "http://localhost:5173"); }
    public static String correoUrl() { return valor("mailpit.web.url", "MAILPIT_WEB_URL", "http://127.0.0.1:8025"); }
    private static String valor(String propiedad, String variable, String defecto) {
        return System.getProperty(propiedad, System.getenv().getOrDefault(variable, defecto)).replaceAll("/+$", "");
    }
}

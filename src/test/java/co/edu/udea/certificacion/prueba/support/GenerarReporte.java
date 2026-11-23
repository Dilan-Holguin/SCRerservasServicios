package co.edu.udea.certificacion.prueba.support;

import net.thucydides.core.reports.html.HtmlAggregateStoryReporter;
import java.io.File;

/** Usa el motor de reportes de la misma versión de Serenity que ejecuta las pruebas. */
public final class GenerarReporte {
    private GenerarReporte() { }
    public static void main(String[] args) throws Exception {
        File salida = new File(args[0]);
        var reporter = new HtmlAggregateStoryReporter("ReservaYa - E2E UI");
        reporter.setOutputDirectory(salida);
        reporter.setProjectDirectory(new File(".").getCanonicalPath());
        reporter.generateReportsForTestResultsFrom(salida);
        System.out.println("Reporte Serenity: " + new File(salida, "index.html").getAbsolutePath());
    }
}

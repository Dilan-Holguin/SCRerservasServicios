package co.edu.udea.certificacion.prueba.models;

/** Lo que se va a escribir en el registro. Un valor null significa "no tocar ese campo". */
public final class FormularioRegistro {
    public final String rol;
    public final String nombre;
    public final String email;
    public final String clave;
    public final String nombreServicio;
    public final boolean elegirCategoria;
    public final String duracion;
    public final String capacidad;

    private FormularioRegistro(CuentaPrueba cuenta, String defecto) {
        boolean proveedor = cuenta.rol.equals("PROVEEDOR");
        boolean conServicio = proveedor && !defecto.equals("sin servicio");
        rol = cuenta.rol;
        nombre = defecto.equals("nombre vacío") ? "" : cuenta.nombre;
        email = switch (defecto) {
            case "correo vacío" -> "";
            case "correo inválido" -> "correo-invalido";
            default -> cuenta.email;
        };
        clave = defecto.equals("clave corta") ? "12345678901" : cuenta.clave;
        nombreServicio = conServicio ? cuenta.servicio : null;
        elegirCategoria = conServicio && !defecto.equals("sin categoría");
        duracion = proveedor && defecto.equals("duración inválida") ? "0" : null;
        capacidad = proveedor && defecto.equals("capacidad inválida") ? "0" : null;
    }

    public static FormularioRegistro completo(CuentaPrueba cuenta) {
        return new FormularioRegistro(cuenta, "");
    }

    public static FormularioRegistro conDefecto(CuentaPrueba cuenta, String defecto) {
        return new FormularioRegistro(cuenta, defecto);
    }
}

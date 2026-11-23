# Estado de validación

Fecha de actualización: 23 de noviembre de 2026.

- Base: 11 escenarios con etiqueta `@base11`. Antes de la ampliación se observó compilación y dry-run correctos. Una ejecución del agente obtuvo 6 aprobados y un fallo en reserva, con 4 sin resultado; el diagnóstico quedó interrumpido por desconexión. El equipo está ejecutándolos desde su consola. No se sustituye ese resultado por una suposición de éxito.
- Ampliación: 48 escenarios escritos, con Tasks, Questions, Targets y step definitions. No se ejecutaron pruebas ni dry-run de esta ampliación, a petición del usuario.
- Compilación Java: verificada con `compileTestJava -x test -x reporteSerenity`, usando una carpeta temporal. Esta comprobación no ejecuta escenarios ni valida su comportamiento.
- Selección normal: 58 escenarios, excluyendo el contrato de notificación.
- Pendiente del producto: E2E-052, correo de cancelación del proveedor. Está implementada la comprobación por Mailpit UI, pero falta el envío en la aplicación.
- Los casos deben registrar el resultado real del equipo antes de presentarse como aprobados.

Comandos: `ejecutar-pruebas.ps1 -Grupo base11`, `-Grupo nuevas`, `-Grupo ui` y `-Grupo pendientes`.

GitHub puede guardar avances y escenarios sin ejecutar; el commit y la documentación deben distinguir implementación de validación. Los reportes y cachés no forman parte de la entrega versionada.

## Corrección del identificador #0 en reservas

El usuario reportó el mismo fallo en los cinco casos base de reservas/cancelaciones: se buscaba una tarjeta con el identificador #0.

La causa estaba en construir `ConsultarReservaWeb.recargando(datos)` antes de ejecutar `delCliente(datos)`: los argumentos de `attemptsTo` se construyen antes de ejecutar sus Tasks y el selector conservaba el valor inicial. La lectura del ID y del rol se trasladó al cuerpo ejecutable de la Task. Si no existe un ID válido, ahora se informa la precondición faltante.

Se separaron también las acciones dependientes en `FlujoReservaWeb.preparada`, `PrepararRecursosCompartidosWeb` y la preparación de agenda con reserva, para leer los servicios y la fecha después de crearlos.

Corrección aplicada en código; pendiente de repetición desde la consola del usuario. No se ejecutaron pruebas para validar esta corrección.

# Estado de validación

Fecha de actualización: 24 de noviembre de 2026.

- Base: 11 escenarios con etiqueta `@base11`, aprobados según la ejecución reportada por el usuario. Los cinco casos de reservas/cancelaciones pasaron después de corregir la captura anticipada del ID #0; los otros seis ya estaban aprobados. Esta confirmación procede de la consola del usuario, no de una nueva ejecución del agente.
- Ampliación: el usuario ejecutó los 47 escenarios seleccionables. El último reporte Cucumber registra 45 aprobados y 2 fallidos: E2E-042 (rango mayor de 31 días) y E2E-056 (correo ausente en la preparación del proveedor). Se ajustó el código; ambos quedan pendientes de repetición. E2E-052 sigue separado como pendiente del producto. El agente no ejecutó pruebas ni dry-run.
- Compilación Java previa: verificada con `compileTestJava -x test -x reporteSerenity`, usando una carpeta temporal. La compilación de los últimos ajustes de E2E-042 y E2E-056 queda pendiente de confirmar; su inspección estática no encontró pasos sin asociar. No se ejecutaron escenarios ni dry-run para estos ajustes.
- Selección normal: 58 escenarios, excluyendo el contrato de notificación.
- Pendiente del producto: E2E-052, correo de cancelación del proveedor. Está implementada la comprobación por Mailpit UI, pero falta el envío en la aplicación.
- Los casos deben registrar el resultado real del equipo antes de presentarse como aprobados.

Comandos: `ejecutar-pruebas.ps1 -Grupo base11`, `-Grupo nuevas`, `-Grupo ui` y `-Grupo pendientes`.

GitHub puede guardar avances y escenarios sin ejecutar; el commit y la documentación deben distinguir implementación de validación. Los reportes y cachés no forman parte de la entrega versionada.

## Corrección del identificador #0 en reservas

El usuario reportó el mismo fallo en los cinco casos base de reservas/cancelaciones: se buscaba una tarjeta con el identificador #0.

La causa estaba en construir `ConsultarReservaWeb.recargando(datos)` antes de ejecutar `delCliente(datos)`: los argumentos de `attemptsTo` se construyen antes de ejecutar sus Tasks y el selector conservaba el valor inicial. La lectura del ID y del rol se trasladó al cuerpo ejecutable de la Task. Si no existe un ID válido, ahora se informa la precondición faltante.

Se separaron también las acciones dependientes en `FlujoReservaWeb.preparada`, `PrepararRecursosCompartidosWeb` y la preparación de agenda con reserva, para leer los servicios y la fecha después de crearlos.

Corrección aplicada en código. El usuario confirmó que los cinco escenarios afectados pasaron al repetirlos desde su consola. El agente solo comprobó la compilación y no volvió a ejecutar pruebas.

## Ajustes de E2E-042 y E2E-056

- E2E-042: la captura muestra fechas del 4 de diciembre de 2026 al 4 de enero de 2027 (32 días inclusivos). La UI presenta «No se pudo consultar la disponibilidad / La solicitud contiene datos inválidos» y publica el límite de 31 días en el texto de ayuda. El caso comprueba ambos textos en sus regiones, las fechas visibles, su URL y la ausencia de turnos. No exige un detalle de campo que la UI no muestra.
- E2E-056: la captura de Mailpit muestra «No results» para el destinatario exacto; el paso del recurso sin tipo todavía no se ejecutaba. No se confirmó la causa del correo ausente. La búsqueda ahora espera hasta 30 segundos, actualiza la bandeja y repite una vez. Para preparar cuentas de negocio se permite además un único reenvío desde la UI, seguido de la lectura obligatoria del mensaje y la verificación real del enlace. Si sigue sin llegar, el caso falla y señala el correo/SMTP; nunca se omite la verificación.
- Los casos que prueban el correo del registro usan la modalidad estricta y no solicitan un reenvío automáticamente. La recuperación de una precondición no se cuenta como prueba de entrega del correo original.

Estos ajustes requieren repetición por el usuario. La inspección de reportes y la compilación no equivalen a una nueva ejecución aprobada.

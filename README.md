# ReservaYa: automatización E2E de UI

Proyecto generado con el scaffolding `io.github.jumosqu12.screenplayarchitecture:2.0.1`, organizado con Screenplay, Java, Serenity BDD, Cucumber y Selenium.

Hay **59 escenarios escritos**: 11 del avance inicial y 48 de ampliación. El grupo normal selecciona 58; el correo de cancelación está marcado como contrato pendiente del producto.

**Estado de validación (24 de noviembre de 2026):** los 11 escenarios originales están aprobados según la ejecución reportada por el usuario, incluidos los cinco casos de reservas/cancelaciones después de corregir el selector `#0`. El usuario ejecutó los 47 casos seleccionables de la ampliación: 45 aprobados y 2 fallidos (E2E-042 y E2E-056). Se ajustó el código y falta repetir esos dos. La compilación correcta no equivale a escenarios aprobados.

## Ejecutar en local

Inicia ReservaYa según su README: frontend en http://localhost:5173, backend en http://localhost:8082, PostgreSQL y Mailpit con Docker. La UI de Mailpit debe estar disponible en http://127.0.0.1:8025. Se requiere JDK 21, Gradle en PATH y Chrome.

La automatización usa el frontend y Mailpit web. Los datos se preparan mediante formularios, sin clientes REST, SQL ni sesiones inyectadas.

Desde PowerShell en este repositorio:

```powershell
# Los 11 escenarios originales
.\ejecutar-pruebas.ps1 -Grupo base11 

# Solo la ampliación ejecutable (47 casos)
.\ejecutar-pruebas.ps1 -Grupo nuevas 

# Toda la UI ejecutable (58 casos)
.\ejecutar-pruebas.ps1 -Grupo ui 

# Solo el contrato de correo pendiente del producto
.\ejecutar-pruebas.ps1 -Grupo pendientes -SinVentana
```

`criticas` y `todas` también excluyen `@pendiente_producto`; `smoke` selecciona los cinco casos base marcados así. `validar` hace dry-run de los 59 y comprueba las asociaciones de Gherkin; no valida comportamiento.

Ejecutar por HU o escenario:

```powershell
gradle --no-daemon test '-Dcucumber.filter.tags=@ui and @HU-05 and not @pendiente_producto' '-Dheadless.mode=true'
gradle --no-daemon test '-Dcucumber.filter.tags=@E2E-032' '-Dheadless.mode=true'
```

Configurar URLs:

```powershell
$env:RESERVAYA_WEB_URL = 'http://localhost:5173'
$env:MAILPIT_WEB_URL = 'http://127.0.0.1:8025'
```

También se admiten `reservaya.web.url` y `mailpit.web.url` como propiedades Java. Las pruebas van en serie para limitar memoria y preservar el aislamiento.

## Screenplay

- `features/ui`: escenarios, etiquetas de HU, CP, TC y criterios locales.
- `stepdefinitions`: pasos separados por registro/autenticación, agenda, reservas y recursos.
- Actor `Usuario de reservas`, creado con `OnStage` y `OnlineCast`, con habilidad `BrowseTheWeb`.
- `tasks`: acciones de negocio, precondiciones reutilizables y limpieza de datos propios.
- `interactions`: contraseña, controles de hora/fecha y consulta de correo web.
- `questions`: comprobaciones y lectura de identificadores visibles.
- `userinterfaces`: Targets de ReservaYa y Mailpit.
- `models`: cuentas con UUID y estado independiente por escenario.
- `utils`: configuración de las URLs web.

Las Tasks compuestas con `Task.where` también son Screenplay. El scaffolding prepara la estructura y Screenplay organiza los comportamientos; no hay que regenerar el proyecto para añadir casos.

## Datos y correo

Cada escenario tiene cuentas, servicios y recursos con UUID. Las fechas son relativas a Bogotá. Se cambia de rol con el menú de cerrar sesión y el formulario de ingreso.

Mailpit se consulta en una pestaña auxiliar filtrando por destinatario. Para verificar la cuenta se conserva el token recibido y se abre la página `/verificacion` del frontend, porque el enlace del correo local apunta al servidor.

La búsqueda de correo se actualiza una vez si el mensaje no aparece en 30 segundos. Solo para preparar cuentas de los escenarios de negocio se permite un reenvío por la UI si las dos búsquedas no encuentran el mensaje; sigue siendo obligatorio recibir el correo y verificarlo. Los casos que prueban el correo original del registro no usan ese reenvío automático.

Los casos de cancelación recargan el historial y consultan disponibilidad en la ficha. Los de agenda verifican la persistencia al cargar de nuevo la sección. La limpieza cancela las reservas propias y deja inactivos los servicios creados. La aplicación no dispone de UI para eliminar cuentas, correos ni recursos.

El caso `@E2E-052 @pendiente_producto` espera una notificación por correo de cancelación que todavía no envía la aplicación. Se mantiene separado y documentado; no es un caso aprobado ni una comprobación sustituida por un éxito artificial.

## Resultados, trazabilidad y GitHub

- Serenity: `build/reports/serenity/index.html`
- JUnit: `build/reports/tests/test/index.html`
- Cucumber: `build/reports/cucumber/resultados.json`
- [Matriz de trazabilidad](docs/trazabilidad-ui.md)
- [Estado de validación](docs/estado-validacion.md)

Los archivos de `docs/gherkin-ui-original` son referencia histórica, fuera del runner. Los TC-UI y CA-UI son identificadores locales; no son numeración oficial del profesor.

La ampliación tiene 45 casos aprobados según el último reporte del usuario y dos ajustes pendientes de repetición. Un commit de escenarios implementados no equivale a una validación aprobada. Los 11 casos base fueron reportados como aprobados por el usuario y ese avance puede subirse indicando su alcance; la ampliación debe identificar por separado su estado de validación. No subas reportes, cachés, `.env` ni credenciales personales: los archivos generados y `.env` están ignorados.

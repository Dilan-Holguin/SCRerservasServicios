# ReservaYa: automatización E2E de UI

Este repositorio contiene las pruebas de ReservaYa realizadas desde el navegador. Los escenarios recorren los formularios y paneles del frontend, que consume el backend real. La preparación de cuentas, servicios, agendas y reservas también pasa por la interfaz; la suite no contiene pruebas REST ni prepara datos mediante SQL o sesiones inyectadas.

El proyecto se generó con el scaffolding `io.github.jumosqu12.screenplayarchitecture:2.0.1`. Sobre esa estructura se implementaron los comportamientos con Screenplay, Java, Serenity BDD, Cucumber y Selenium WebDriver.

**Última validación: 25 de noviembre de 2026.** Después de refactorizar las tareas, interacciones y esperas, se ejecutaron y aprobaron los **58 escenarios de la suite UI normal**. Hay **59 escenarios escritos**: 11 del avance inicial y 48 de ampliación. E2E-052 está marcado como `@pendiente_producto` y queda fuera de esos 58 porque espera un correo de cancelación que la aplicación todavía no envía.

La corrida se hizo contra [ReservasServicios](https://github.com/Satshman/ReservasServicios) en el commit [`27ac566`](https://github.com/Satshman/ReservasServicios/commit/27ac5661687aeeaf17daf837d100bf0fc5d8a0fe): 58 escenarios aprobados, 218 pasos aprobados, 0 fallos y una duración de 19 min 43 s. El reporte navegable de esa ejecución queda en `build/reports/serenity/index.html`.

La evidencia PDF corresponde a la ejecución previa a la refactorización. Se conserva como registro histórico; no debe presentarse como evidencia del código actual.

## Proyecto bajo prueba

Esta suite valida **ReservaYa**, que se encuentra en un repositorio separado:

- Repositorio: https://github.com/Satshman/ReservasServicios
- Commit validado: [`27ac566`](https://github.com/Satshman/ReservasServicios/commit/27ac5661687aeeaf17daf837d100bf0fc5d8a0fe)
- Hash completo: `27ac5661687aeeaf17daf837d100bf0fc5d8a0fe`
- Instrucciones de arranque: [README de ReservaYa](https://github.com/Satshman/ReservasServicios#readme)
- Servicios esperados: frontend `5173`, backend `8082`, Mailpit `8025` y PostgreSQL.

> La suite no incluye el código de la aplicación ni la levanta. El resultado reportado corresponde al commit indicado; si la aplicación cambia, vuelve a ejecutar al menos el smoke y actualiza la evidencia.

## Herramientas y entorno

| Herramienta | Función en el proyecto |
|---|---|
| Java | Implementar tareas, interacciones, preguntas y pasos. `build.gradle` configura compatibilidad de código con Java 17; para el entorno local se recomienda JDK 21. |
| Gradle | Resolver dependencias, compilar, ejecutar el runner y generar el reporte. El repositorio incluye `gradlew`/`gradlew.bat` y el wrapper 9.8.0. |
| Serenity BDD 4.1.0 | Integrar Screenplay, administrar WebDriver y registrar resultados y evidencias. |
| Cucumber | Vincular los escenarios Gherkin con las definiciones de pasos Java. |
| JUnit 4.13.2 | Ejecutar `Runner` con `CucumberWithSerenity`. |
| Selenium y Chrome | Navegar por ReservaYa y por la interfaz web de Mailpit. |
| Mailpit | Recibir y consultar los correos del entorno local. |

Antes de ejecutar, inicia ReservaYa siguiendo el README de la aplicación:

| Servicio | Dirección local |
|---|---|
| Frontend | http://localhost:5173 |
| Backend | http://localhost:8082 |
| Mailpit web | http://127.0.0.1:8025 |
| PostgreSQL y servidor de correo | Disponibles según el Docker Compose del producto. |

El navegador necesita acceso al frontend y a Mailpit. Aunque no se hacen solicitudes REST desde el código de pruebas, el backend, la base de datos y el correo deben estar funcionando para completar los recorridos.

## Cómo se construyó la automatización

Los flujos se expresaron en archivos `.feature`, con precondiciones, acciones y resultados observables. Se priorizaron registro e ingreso, publicación de agendas, disponibilidad, reservas y cancelaciones. Después se ampliaron las validaciones de formularios, edición y eliminación de agendas, restricciones de fechas y recursos compartidos.

Cada paso de Gherkin se conecta con una definición Java. Esta delega las acciones de negocio a tareas reutilizables y comprueba las respuestas mediante preguntas de Screenplay o lecturas de elementos visibles. Así, varios escenarios pueden reutilizar el registro de una cuenta o la creación de una agenda sin copiar toda la navegación.

### Organización con Screenplay

El paquete Java principal es `co.edu.udea.certificacion.prueba`. En la tabla, `...` representa ese paquete dentro de cada árbol Java.

| Ubicación | Responsabilidad y ejemplos |
|---|---|
| `src/test/resources/features/ui` | Escenarios Gherkin con etiquetas de selección y trazabilidad. |
| `src/test/java/.../runners/Runner.java` | Entrada JUnit que descubre las features e integra Cucumber con Serenity. |
| `src/test/java/.../stepdefinitions` | Traducción de pasos a acciones y comprobaciones, separada por registro, agenda, reservas y recursos. |
| `src/main/java/.../tasks` | Acciones como `RegistrarCuentaWeb`, `CrearAgendaWeb`, `ReservarTurnoWeb` y `CancelarReservaWeb`, además de preparación y limpieza. |
| `src/main/java/.../interactions` | Operaciones concretas sobre controles y correo, como `LlenarElFormularioDeRegistro`, `SeleccionarElTurno` y `LeerCorreoVerificacion`. |
| `src/main/java/.../questions` | Lectura del estado observable, como `InformacionVisible`, `EstadoReservaEnPantalla` y `DatosEnPantalla`. |
| `src/main/java/.../userinterfaces` | Localizadores `Target` de ReservaYa y Mailpit, incluidos elementos parametrizados por servicio o reserva. |
| `src/main/java/.../models` | Datos propios del escenario: cuentas, fechas, servicios y reservas creadas. |
| `src/main/java/.../utils` | Direcciones web, tiempos de espera y pausa de demostración. |
| `src/test/java/.../support` | Acceso al actor y sus datos, y generación del reporte HTML. |

Las clases de plantilla que conserva el scaffolding no implican integraciones adicionales. Por ejemplo, la suite no usa el paquete `integrations` para consumir una API. El scaffolding proporciona la estructura inicial; los casos nuevos se agregan sin regenerar el proyecto.

Antes de cada escenario UI, un hook configura `OnStage` con `OnlineCast`, crea al actor **Usuario de reservas** y le asigna `BrowseTheWeb` con el driver de Serenity. También guarda una instancia nueva de `DatosEscenario` en la memoria del actor. Cada tarea e interacción es una clase que implementa `Task` o `Interaction` y se crea con `Tasks.instrumented`.

Por ejemplo, al reservar un turno, `PrepararNegocioWeb` registra las cuentas necesarias y crea el servicio y la agenda desde sus formularios. Luego `ReservarTurnoWeb` abre la ficha pública y elige el turno. La confirmación se comprueba en el historial; `DatosEnPantalla` obtiene el identificador que muestra la reserva para las consultas posteriores. El identificador se lee de la interfaz y no se supone un valor fijo.

### Localizadores y sincronización

Los `Target` se concentran en `ReservaYa`, `FlujosUi` y `Mailpit`. Se emplea XPath para relacionar etiquetas, textos y contenedores, y localizar la tarjeta de una reserva o un servicio concreto. Esto permite corregir un selector en su definición compartida.

La espera implícita de WebDriver está desactivada. Antes de continuar se espera una condición de la pantalla (visible, clicable, presente) mediante `utils/Esperar`. Los tiempos no se escriben en el código; se definen en `src/test/resources/serenity.conf`:

| Propiedad (milisegundos) | Valor | Para qué sirve | Quién la lee |
|---|---|---|---|
| `webdriver.wait.for.timeout` | 15000 | Máximo para que un elemento aparezca o cambie | `utils/TiempoDeEspera.maximo()` |
| `mailpit.wait.timeout` | 30000 | Máximo para que llegue un correo | `utils/TiempoDeEspera.correo()` |
| `demo.pause.milliseconds` | 0 | Pausa fija entre acciones, solo para exponer | `utils/PausaDeDemostracion` |

Cualquiera se puede cambiar para una ejecución, por ejemplo `./gradlew test '-Ddemo.pause.milliseconds=2000'`. La pausa de demostración está incluida en 33 de las 51 interacciones; no se aplica a todas.

Reglas de capas: las tareas solo llaman interacciones u otras tareas; `Click`, `Enter`, `Open` y las esperas van en las interacciones. Chrome se configura con una ventana de 1440 × 1000 y lenguaje `es-CO`.

Para los controles nativos de fecha y hora se utilizan interacciones específicas. En `FijarFecha`, Selenium ejecuta JavaScript sobre el campo de la página para asignar el valor y emitir los eventos `input` y `change` que procesa React. Esta operación modifica el control del frontend y no crea datos directamente en el servidor.

### Datos, aislamiento y limpieza

Cada escenario genera cuentas, nombres de servicios y recursos con UUID para evitar colisiones con ejecuciones anteriores. Las fechas de negocio son relativas al día actual en `America/Bogota`. El cambio entre cliente y proveedor se realiza mediante cierre de sesión e ingreso por los formularios.

La ejecución se mantiene en serie con `maxParallelForks = 1` y un heap de 256 MB para el proceso de pruebas. Esto limita el consumo de memoria; el aislamiento entre casos depende de sus datos propios y su preparación independiente.

Al terminar, el hook `@After` intenta cancelar las reservas creadas e inactivar los servicios del escenario mediante la UI. Después cierra los drivers y el escenario de Screenplay. Si la limpieza falla tras un escenario exitoso, el error se propaga; si el escenario ya había fallado, la incidencia se registra para conservar el fallo original.

Esta limpieza tiene el alcance que permite el producto: la UI no ofrece eliminación de cuentas, correos ni recursos. Por eso pueden quedar registros de prueba en el entorno local. No se necesita que un escenario anterior haya pasado para preparar el siguiente.

### Verificación de correos

Mailpit se consulta en una pestaña auxiliar y se filtra por el destinatario generado para el escenario. Para verificar la cuenta se conserva el token recibido y se abre la página `/verificacion` del frontend, porque el enlace del correo local apunta al servidor.

La búsqueda de correo se actualiza una vez si el mensaje no aparece en 30 segundos. Solo para preparar cuentas de los escenarios de negocio se permite un reenvío desde la UI si las dos búsquedas no encuentran el mensaje. Sigue siendo obligatorio recibir el correo y verificarlo. Los escenarios que comprueban el correo original del registro no usan ese reenvío automático.

### Qué se comprueba

Las aserciones revisan mensajes, controles, estados y datos visibles. En reservas y cancelaciones también se recarga el historial y se consulta la disponibilidad para comprobar que el cambio se conserva y el cupo se libera. Los casos de agenda vuelven a cargar la sección para revisar la persistencia.

Estos recorridos ejercitan la integración entre frontend y backend desde la experiencia del usuario. Su alcance está limitado a lo observable en esos flujos: no comprueban por separado todos los contratos HTTP, la seguridad o la concurrencia del servidor.

## Etiquetas y trazabilidad

| Etiqueta | Uso |
|---|---|
| `@ui` | Identifica la suite de navegador. |
| `@E2E-007` | Identificador único de un escenario automatizado. |
| `@HU-07` | Historia de usuario cubierta. Puede haber varias HU asociadas. |
| `@CP-07`, `@TC-025-HP` | Referencias de CP y TC de los documentos fuente. |
| `@TC-UI-...`, `@CA-UI-...` | Identificadores locales para casos adicionales y comprobaciones de aceptación. |
| `@base11`, `@ampliacion` | Separan el avance inicial de la ampliación. |
| `@critica`, `@smoke` | Seleccionan los grupos de prioridad definidos en las features. |
| `@positivo`, `@negativo` | Describen el tipo de escenario. |
| `@pendiente_producto` | Separa el contrato de correo pendiente de implementación. |

Por ejemplo, E2E-007 relaciona la creación de una reserva con HU-07, CP-07, TC-025-HP y CA-UI-08. Los códigos CA-UI y TC-UI son locales, no una numeración oficial del profesor. Las precondiciones reutilizadas para crear cuentas o agendas no se cuentan como nuevos casos aprobados de esas HU.

La [matriz de trazabilidad](docs/trazabilidad-ui.md) detalla las asociaciones. Los archivos de `docs/gherkin-ui-original` son referencia histórica y están fuera del runner.

## Ejecutar en local

Con los servicios del producto iniciados, usa el wrapper incluido.

### Linux o macOS

```bash
# Suite completa ejecutable: 58 escenarios
./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@ui and not @pendiente_producto'

# Smoke: 5 escenarios
./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@ui and @smoke'

# Base inicial: 11 escenarios
./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@ui and @base11'

# Solo ampliación ejecutable: 47 escenarios
./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@ui and @ampliacion and not @pendiente_producto'

# Un solo escenario
./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@E2E-032'
```

Sin `-Dheadless.mode=true` se abre Chrome para observar la ejecución. Para correr sin ventana:

```bash
./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@ui and @smoke' \
  '-Dheadless.mode=true'
```

Para una demostración con pausas:

```bash
./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@ui and @smoke' \
  '-Ddemo.pause.milliseconds=1000'
```

### Windows PowerShell

```powershell
# Scripts de apoyo; usan el wrapper si está disponible
.\ejecutar-pruebas.ps1 -Grupo ui
.\ejecutar-pruebas.ps1 -Grupo ui -SinVentana
.\ejecutar-pruebas.ps1 -Grupo base11
.\ejecutar-pruebas.ps1 -Grupo nuevas
.\ejecutar-pruebas.ps1 -Grupo smoke -SinVentana

# O directamente con el wrapper
.\gradlew.bat --no-daemon test --console=plain `
  '-Dcucumber.filter.tags=@ui and not @pendiente_producto'
```

El script comprueba `JAVA_HOME`; si no apunta a un JDK válido, intenta localizar un JDK 21 de Eclipse Adoptium. `criticas` es el grupo predeterminado; `ui`, `criticas` y `todas` excluyen `@pendiente_producto`.

Para revisar las asociaciones entre Gherkin y pasos sin realizar los recorridos, o ejecutar por separado el contrato pendiente:

```bash
# Dry-run de los 59 escenarios; no valida comportamiento
./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@ui' \
  '-Dcucumber.execution.dry-run=true'

# Fuera de la suite normal: depende de una función pendiente del producto
./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@ui and @pendiente_producto' \
  '-Dheadless.mode=true'
```

### Configurar las direcciones

```powershell
$env:RESERVAYA_WEB_URL = 'http://localhost:5173'
$env:MAILPIT_WEB_URL = 'http://127.0.0.1:8025'
```

También se admiten las propiedades Java `reservaya.web.url` y `mailpit.web.url`. La prioridad es propiedad Java, variable de entorno y dirección local predeterminada. La URL del backend se configura en el producto; la automatización accede a él a través del frontend.

## Reportes y evidencia

La tarea `test` ejecuta `Runner`, escribe el JSON de Cucumber y registra los resultados de Serenity. Antes de la ejecución limpia el directorio de resultados de Serenity. Al finalizar, `reporteSerenity` usa `HtmlAggregateStoryReporter`, mediante `GenerarReporte`, para construir el HTML con la misma versión de Serenity que ejecuta las pruebas. La configuración guarda capturas de pantalla ante fallos.

| Resultado | Ubicación |
|---|---|
| Reporte HTML de Serenity | `build/reports/serenity/index.html` |
| Reporte de pruebas de Gradle/JUnit | `build/reports/tests/test/index.html` |
| Resultados Cucumber | `build/reports/cucumber/resultados.json` |
| Evidencia histórica | [Reporte PDF del 24 de noviembre de 2026](docs/evidencias/Reporte_Serenity_2026-11-24.pdf) |

El PDF conserva el resumen de la ejecución previa a la refactorización. Es una exportación documental; el detalle navegable de pasos de la corrida actual se consulta en el HTML local. Una ejecución posterior no actualiza automáticamente este PDF.

El [registro de validación](docs/estado-validacion.md) documenta la corrida post-refactor: 58/58 escenarios, 218/218 pasos y 0 fallos.

## Archivos que se versionan

Se versionan el código, las features, la configuración compartida y la documentación, incluido el PDF en `docs/evidencias/`. Los directorios generados `build/`, `bin/`, `target/`, las cachés, los logs y `.env` están ignorados. No deben agregarse credenciales personales ni todo el reporte HTML generado al repositorio.

Para ampliar la suite, agrega el escenario y sus etiquetas, reutiliza las tareas existentes o incorpora las nuevas acciones y preguntas en su capa correspondiente, actualiza la trazabilidad y valida el comportamiento con la aplicación local. La compilación o el dry-run por sí solos no equivalen a una prueba aprobada.

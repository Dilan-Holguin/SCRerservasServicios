# Estado de validación

Fecha de actualización: 25 de noviembre de 2026.

## Validación posterior al refactor

- La suite refactorizada se ejecutó contra la aplicación en el commit `27ac566`.
- Resultado: **58/58 escenarios aprobados**, **218/218 pasos aprobados**, **0 fallos**.
- Duración: **19 min 43 s**.
- Base: **11/11** escenarios aprobados.
- Ampliación: **47/47** escenarios ejecutables aprobados.
- Smoke: **5/5** escenarios aprobados.
- Dry-run: **59 escenarios y 222 pasos asociados**.
- Compilación Java: correcta.
- El escenario `E2E-052` sigue excluido por ser `@pendiente_producto`: espera un correo de cancelación que la aplicación todavía no envía.

La validación se hizo sobre el directorio refactorizado. Después se aplicaron los mismos archivos al repositorio original; **no se volvieron a ejecutar pruebas en el original**. La repetición manual queda a cargo del equipo.

## Refactor aplicado

- Las tareas se dividieron en responsabilidades más pequeñas.
- Los `Click`, `Enter`, `Open` y las esperas se movieron a `interactions`.
- Las esperas se centralizaron en `utils/Esperar` y `utils/TiempoDeEspera`.
- Se agregó `utils/PausaDeDemostracion` y la propiedad `demo.pause.milliseconds`; la pausa está en 33 de las 51 interacciones, incluidas las dos búsquedas en Mailpit.
- Se redujeron las esperas explícitas de 101 a 7 apariciones.
- Se agregó el wrapper de Gradle 9.8.0.

## Configuración de ejecución

```conf
webdriver.wait.for.timeout = 15000
mailpit.wait.timeout = 30000
demo.pause.milliseconds = 0
```

Comandos principales:

```bash
./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@ui and not @pendiente_producto'

./gradlew --no-daemon test --console=plain \
  '-Dcucumber.filter.tags=@ui and @smoke' \
  '-Ddemo.pause.milliseconds=1000'
```

## Evidencia

- Reporte HTML de la corrida post-refactor: `build/reports/serenity/index.html`.
- Reporte PDF histórico del 24 de noviembre: `docs/evidencias/Reporte_Serenity_2026-11-24.pdf`.
- El PDF corresponde a la ejecución previa a la refactorización; no debe presentarse como evidencia del código actual.
- Matriz de trazabilidad: `docs/trazabilidad-ui.md`.

## Historial de correcciones

- Se corrigió la lectura anticipada del identificador `#0` en los escenarios de reservas.
- E2E-042 valida el mensaje real de rango máximo, el límite de 31 días en la ayuda y la ausencia de turnos.
- E2E-056 espera el correo, actualiza Mailpit y permite un reenvío controlado solo para precondiciones.
- Los casos que prueban el correo original del registro no usan el reenvío automático.

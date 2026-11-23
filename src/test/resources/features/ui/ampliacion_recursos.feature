@ui @critica @ampliacion @HU-09 @CP-09
Feature: Gestion de recursos y disponibilidad compartida desde la UI

  @E2E-053 @CA-UI-54 @TC-036 @positivo
  Scenario: Crear un recurso y consultarlo activo
    Given un proveedor web con servicio "sin agenda"
    When crea un recurso UI con nombre unico y tipo disponible
    Then el recurso aparece activo en el listado despues de recargar

  @E2E-054 @CA-UI-55 @TC-033 @positivo
  Scenario: Asignar un recurso activo al servicio propio
    Given un proveedor web con servicio "sin agenda"
    And un recurso UI activo del proveedor para asignar
    When asigna su recurso al servicio desde el panel
    Then ve la confirmacion de un recurso asignado

  @E2E-055 @CA-UI-56 @TC-UI-009-01 @negativo
  Scenario: Impedir la creacion de un recurso sin nombre
    Given un proveedor web con servicio "sin agenda"
    When completa un recurso sin "nombre"
    Then la interfaz mantiene deshabilitada la creacion del recurso

  @E2E-056 @CA-UI-57 @TC-UI-009-02 @negativo
  Scenario: Impedir la creacion de un recurso sin tipo
    Given un proveedor web con servicio "sin agenda"
    When completa un recurso sin "tipo"
    Then la interfaz mantiene deshabilitada la creacion del recurso

  @E2E-057 @CA-UI-58 @HU-07 @CP-07 @TC-027 @TC-034 @negativo
  Scenario: No ofrecer dos servicios con el mismo recurso ocupado
    Given dos servicios UI con un recurso compartido ocupado a las nueve
    When el segundo cliente consulta el segundo servicio en el mismo horario
    Then el turno de las nueve no se ofrece y el siguiente sigue disponible

  @E2E-058 @CA-UI-59 @HU-07 @CP-07 @TC-035 @positivo
  Scenario: Volver a reservar el recurso compartido despues de cancelar
    Given dos servicios UI con un recurso compartido ocupado a las nueve
    When el cliente original cancela la reserva que ocupa el recurso
    And el segundo cliente consulta el segundo servicio en el mismo horario
    Then el segundo cliente puede reservar el recurso liberado desde la ficha

  @E2E-059 @CA-UI-60 @TC-UI-009-03 @positivo
  Scenario: Retirar los recursos asignados al servicio
    Given un proveedor web con servicio "sin agenda"
    And un recurso UI activo del proveedor para asignar
    When asigna su recurso al servicio desde el panel
    And retira todos los recursos asignados al servicio
    Then ve la confirmacion de recursos retirados

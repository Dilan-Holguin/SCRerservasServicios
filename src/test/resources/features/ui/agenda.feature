@base11
@ui @critica @HU-04 @CP-04
Feature: Publicar la agenda desde el panel del proveedor

  @E2E-005 @TC-014-HP @CA-UI-06 @positivo @smoke
  Scenario: Crear un bloque semanal y generar turnos
    Given un proveedor web con servicio "sin agenda"
    When publica un bloque semanal de ocho a doce desde su panel
    Then ve el bloque publicado y ocho turnos disponibles en la ficha web

  @E2E-006 @TC-015 @CA-UI-07 @negativo
  Scenario: Informar el solapamiento sin modificar la agenda
    Given un proveedor web con servicio "con agenda"
    When intenta publicar un bloque solapado desde su panel
    Then ve el error de agenda y se conserva el bloque original

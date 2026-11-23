@base11
@ui @critica
Feature: Reservar y cancelar desde el navegador

  @E2E-007 @HU-07 @CP-07 @TC-025-HP @CA-UI-08 @positivo @smoke
  Scenario: Reservar desde la disponibilidad y consultar la confirmacion
    Given un cliente web y un turno disponible dentro de 10 dias
    When elige el turno de las nueve desde la disponibilidad web
    Then ve la reserva confirmada y guardada en su historial

  @E2E-008 @HU-08 @CP-08 @TC-029-HP @TC-032 @CA-UI-09 @positivo @smoke
  Scenario: Cancelar como cliente y liberar el cupo
    Given una reserva web confirmada dentro de 10 dias para "CLIENTE"
    When cancela la reserva desde su panel y confirma
    Then la reserva web aparece "CANCELADA" y persiste al recargar el historial
    And el cupo vuelve a estar disponible

  @E2E-009 @HU-08 @CP-08 @TC-UI-008-01 @CA-UI-10 @negativo
  Scenario: Conservar la reserva al desistir en el dialogo
    Given una reserva web confirmada dentro de 10 dias para "CLIENTE"
    When abre la cancelacion y decide conservar la reserva
    Then la reserva web aparece "CONFIRMADA" y persiste al recargar el historial

  @E2E-010 @HU-08 @CP-08 @TC-030 @CA-UI-11 @negativo
  Scenario: Explicar la restriccion de anticipacion antes de cancelar
    Given una reserva web confirmada dentro de 2 dias para "CLIENTE"
    Then la interfaz explica el plazo y no ofrece cancelar

  @E2E-011 @HU-15 @CP-08 @TC-031 @CA-UI-12 @positivo
  Scenario: El proveedor cancela una reserva cercana
    Given una reserva web confirmada dentro de 2 dias para "PROVEEDOR"
    When cancela la reserva desde su panel y confirma
    Then la reserva web aparece "CANCELADA" y persiste al recargar el historial
    And el cupo vuelve a estar disponible

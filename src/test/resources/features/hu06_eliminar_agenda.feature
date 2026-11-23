@HU-06 @CP-06 @ux
Feature: Eliminar bloques de agenda
  Como proveedor quiero quitar bloques sin perder las reservas existentes.

  Background:
    Given que ingreso como proveedor verificado con un servicio activo
    And abro la sección "Agenda" de mi negocio

  @TC-022 @positivo
  Scenario: Eliminar un bloque sin reservas
    Given que mi servicio tiene un bloque sin reservas futuras
    When pulso eliminar ese bloque
    Then veo "Bloque eliminado"
    And el bloque ya no figura en "Bloques definidos"

  @TC-023 @negativo
  Scenario: Solicitar confirmación antes de eliminar un bloque con reservas
    Given que mi servicio tiene un bloque con una reserva futura confirmada
    When pulso eliminar ese bloque
    Then veo el diálogo "Este cambio afecta reservas confirmadas"
    And la reserva afectada aparece en el diálogo
    And el bloque continúa publicado mientras no confirme

  @TC-024 @positivo
  Scenario: Conservar la reserva al confirmar la eliminación del bloque
    Given que está abierto el diálogo para eliminar un bloque con una reserva confirmada
    When pulso "Eliminar de todos modos"
    Then veo "Bloque eliminado"
    And la reserva afectada sigue confirmada

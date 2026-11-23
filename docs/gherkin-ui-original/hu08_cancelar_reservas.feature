# CP-08. Los documentos de Sprint 1 marcaban esta HU como pendiente; la web actual permite cancelarla.
@HU-08 @CP-08 @ux
Feature: Cancelar reservas e inspeccionar su historial
  Como cliente quiero cancelar mis reservas con la anticipación permitida.

  @TC-029 @positivo
  Scenario: Cancelar una reserva del cliente con suficiente anticipación
    Given que ingreso como cliente con una reserva confirmada que comienza en más de cinco días
    When abro "Mis reservas" y pulso "Cancelar reserva" en esa reserva
    Then veo el diálogo "Cancelar la reserva"
    When pulso "Sí, cancelar la reserva"
    Then veo un aviso de cancelación
    And la reserva figura con estado "CANCELADA"

  @TC-030 @negativo
  Scenario: Explicar el plazo de cancelación al cliente
    Given que ingreso como cliente con una reserva confirmada que comienza en menos de cinco días
    When abro "Mis reservas"
    Then veo la explicación de la anticipación mínima de cinco días
    And esa reserva no muestra el botón "Cancelar reserva"

  @TC-031 @positivo
  Scenario: Conservar la reserva al cerrar la confirmación
    Given que ingreso como cliente con una reserva confirmada que comienza en más de cinco días
    When abro "Mis reservas" y pulso "Cancelar reserva" en esa reserva
    And pulso "No, conservarla"
    Then la reserva sigue con estado "CONFIRMADA"

  @TC-032 @positivo
  Scenario: Consultar el historial de una reserva cancelada
    Given que ingreso como cliente con una reserva cancelada
    When abro "Mis reservas" y filtro por estado "CANCELADA"
    And despliego los cambios de esa reserva
    Then veo su estado "CANCELADA" y el cambio registrado en el historial

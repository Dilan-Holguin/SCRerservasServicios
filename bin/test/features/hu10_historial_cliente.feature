@HU-10 @ux
Feature: Consultar el historial de reservas del cliente
  Como cliente quiero ver los cambios de mis reservas y filtrar mis resultados.

  @TC-041 @positivo
  Scenario: Consultar una reserva y sus cambios de estado
    Given que ingreso como cliente con una reserva propia cancelada y otra confirmada
    When abro "Mis reservas" y despliego los cambios de la reserva cancelada
    Then veo el cambio de "CONFIRMADA" a "CANCELADA"
    And veo quién realizó el cambio y cuándo ocurrió

  @TC-042 @positivo
  Scenario: Filtrar reservas por estado
    Given que ingreso como cliente con una reserva propia cancelada y otra confirmada
    When abro "Mis reservas" y filtro por estado "CANCELADA"
    Then veo mi reserva cancelada
    And no veo mi reserva confirmada

  @TC-043 @positivo
  Scenario: Filtrar reservas por rango de fechas
    Given que ingreso como cliente con una reserva propia dentro del rango y otra fuera del rango
    When abro "Mis reservas" y filtro por las fechas del rango preparado
    Then veo la reserva dentro del rango
    And no veo la reserva fuera del rango

  @TC-044 @borde
  Scenario: Consultar el historial sin reservas
    Given que ingreso como un cliente nuevo sin reservas
    When abro "Mis reservas"
    Then veo el estado vacío de mis reservas

@HU-14
Feature: Consultar el historial de reservas del negocio
  Como proveedor quiero revisar y filtrar las reservas de mis servicios.

  @TC-058 @positivo @ux
  Scenario: Ver las reservas de mis servicios con sus cambios
    Given que ingreso como proveedor con reservas de dos servicios propios
    When abro la sección "Reservas" de mi panel
    And despliego los cambios de una reserva
    Then veo las reservas de ambos servicios
    And veo quién cambió el estado de la reserva y cuándo ocurrió

  @TC-059 @positivo @ux
  Scenario: Filtrar por servicio y estado
    Given que ingreso como proveedor con reservas confirmadas y canceladas de dos servicios propios
    When abro la sección "Reservas" de mi panel
    And filtro por el primer servicio y por estado "CANCELADA"
    Then veo solo reservas canceladas del primer servicio

  @TC-060 @positivo @ux
  Scenario: Filtrar reservas por fecha
    Given que ingreso como proveedor con una reserva propia dentro del rango y otra fuera del rango
    When abro la sección "Reservas" de mi panel y filtro por las fechas del rango preparado
    Then veo la reserva dentro del rango
    And no veo la reserva fuera del rango

  @TC-061 @borde @ux
  Scenario: Consultar un negocio sin reservas
    Given que ingreso como proveedor con un servicio propio sin reservas
    When abro la sección "Reservas" de mi panel
    Then veo "Todavía no hay reservas"

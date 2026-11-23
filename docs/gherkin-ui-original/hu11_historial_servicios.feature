@HU-11
Feature: Consultar cambios de los servicios del proveedor
  Como proveedor quiero revisar quién cambió la oferta de mis servicios y cuándo.

  @TC-045 @positivo @ux
  Scenario: Consultar la creación de un servicio propio
    Given que ingreso como proveedor con un servicio propio creado para este escenario
    When abro la sección "Cambios" de mi panel
    Then veo "Servicio creado" para ese servicio
    And veo quién hizo el cambio y cuándo ocurrió

  @TC-046 @positivo @ux
  Scenario: Ver el historial de cambios de agenda, recursos y estado
    Given que ingreso como proveedor con un servicio propio cuyo historial incluye cambios de agenda, recursos y estado
    When abro la sección "Cambios" de mi panel
    And filtro por ese servicio
    Then veo los cambios "Bloque de agenda creado", "Recursos asignados" y "Estado del servicio cambiado"
    And veo sus valores anteriores y nuevos cuando corresponda

  @TC-047 @positivo @ux
  Scenario: Filtrar los cambios por servicio
    Given que ingreso como proveedor con dos servicios propios y cambios registrados en ambos
    When abro la sección "Cambios" de mi panel
    And filtro por el primero de mis servicios
    Then veo los cambios del primer servicio
    And no veo los cambios del segundo servicio

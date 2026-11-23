@HU-13 @api
Feature: Generar reportes de desempeño de servicios
  Como proveedor quiero comparar reservas y cancelaciones de mis servicios.

  @TC-054 @positivo
  Scenario: Ordenar servicios por reservas y calcular cancelaciones
    Given un proveedor autenticado con dos servicios propios y reservas aisladas en el periodo preparado
    And el primer servicio tiene más reservas y una cancelación, y el segundo tiene menos reservas
    When solicita GET "/api/v1/reportes/servicios" con desde y hasta del periodo preparado
    Then recibe el estado HTTP 200
    And "servicios" aparece de mayor a menor según "totalReservas"
    And cada servicio muestra "reservasCanceladas" y "tasaCancelacion" acordes a las reservas preparadas
    And "servicioMasSolicitado" identifica al primer servicio

  @TC-055 @borde
  Scenario: Informar que no hay servicio más solicitado cuando no hay reservas
    Given un proveedor autenticado con un servicio propio sin reservas
    When solicita GET "/api/v1/reportes/servicios" con un periodo sin reservas
    Then recibe el estado HTTP 200
    And "servicioMasSolicitado" es nulo
    And el servicio muestra "totalReservas" igual a 0

  @TC-056 @negativo
  Scenario: Rechazar fechas invertidas
    Given un proveedor autenticado con un servicio propio
    When solicita GET "/api/v1/reportes/servicios" con hasta anterior a desde
    Then recibe el estado HTTP 400 y el código "VALIDACION_FALLIDA"

  @TC-057 @negativo
  Scenario: Impedir pedir el reporte de otro proveedor
    Given un proveedor autenticado y un idProveedor de otro negocio
    When solicita GET "/api/v1/reportes/servicios" con el idProveedor ajeno
    Then recibe el estado HTTP 403 y el código "ACCESO_DENEGADO"

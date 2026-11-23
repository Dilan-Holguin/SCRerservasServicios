@HU-12 @api
Feature: Generar reportes de ocupación por día o bloque horario
  Como proveedor quiero medir la ocupación de mis servicios en un periodo.

  @TC-049 @positivo
  Scenario: Calcular ocupación diaria con reservas confirmadas
    Given un proveedor autenticado con un servicio propio, agenda y turnos futuros preparados
    And el servicio tiene una reserva confirmada y un turno libre en el día consultado
    When solicita GET "/api/v1/reportes/ocupacion" con idServicio, desde y hasta de ese día y desglose "DIA"
    Then recibe el estado HTTP 200
    And el reporte muestra una reserva confirmada, la capacidad preparada y su porcentaje de ocupación calculado
    And el desglose "porDia" contiene el día consultado

  @TC-050 @positivo
  Scenario: Desglosar ocupación por bloques horarios
    Given un proveedor autenticado con un servicio propio, dos bloques y una reserva confirmada en el primero
    When solicita GET "/api/v1/reportes/ocupacion" con el periodo preparado y desglose "BLOQUE_HORARIO"
    Then recibe el estado HTTP 200
    And "porBloqueHorario" distingue los bloques y sus reservas confirmadas
    And "porDia" está vacío

  @TC-051 @borde
  Scenario: No contar reservas canceladas como ocupación
    Given un proveedor autenticado con un servicio propio, un turno futuro y una reserva cancelada en ese turno
    When solicita GET "/api/v1/reportes/ocupacion" para ese servicio y día
    Then recibe el estado HTTP 200
    And "totalReservasConfirmadas" es 0
    And "porcentajeOcupacionGlobal" es 0

  @TC-052 @negativo
  Scenario: Rechazar un rango de fechas invertido
    Given un proveedor autenticado con un servicio propio
    When solicita GET "/api/v1/reportes/ocupacion" con hasta anterior a desde
    Then recibe el estado HTTP 400 y el código "VALIDACION_FALLIDA"

  @TC-053 @negativo
  Scenario: Impedir reportes de servicios ajenos
    Given un proveedor autenticado y un servicio de otro proveedor
    When solicita GET "/api/v1/reportes/ocupacion" con el idServicio ajeno y fechas válidas
    Then recibe el estado HTTP 403 y el código "ACCESO_DENEGADO"

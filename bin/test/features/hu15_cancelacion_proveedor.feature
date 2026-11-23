@HU-15
Feature: Cancelar reservas de clientes como proveedor
  Como proveedor quiero cancelar reservas de mis servicios y liberar su cupo.

  @TC-063 @positivo @ux
  Scenario: Cancelar una reserva futura sin exigir cinco días de anticipación
    Given que ingreso como proveedor con una reserva confirmada de mi servicio que comienza en menos de cinco días
    When abro la sección "Reservas" de mi panel y pulso "Cancelar reserva"
    Then veo el diálogo "Cancelar la reserva del cliente"
    When pulso "Sí, cancelar la reserva"
    Then la reserva figura con estado "CANCELADA"
    And veo el aviso de que su cupo y recursos volvieron a estar disponibles

  @TC-064 @positivo @ux
  Scenario: Dejar constancia de quién canceló la reserva
    Given que ingreso como proveedor con una reserva propia cancelada por este proveedor
    When abro la sección "Reservas" de mi panel y despliego los cambios de esa reserva
    Then veo el cambio de "CONFIRMADA" a "CANCELADA"
    And veo al proveedor como autor y la fecha del cambio

  @TC-065 @positivo @ux
  Scenario: Conservar la reserva si desisto de cancelarla
    Given que ingreso como proveedor con una reserva futura confirmada de mi servicio
    When abro la sección "Reservas" de mi panel y pulso "Cancelar reserva"
    And pulso "No, conservarla"
    Then la reserva sigue con estado "CONFIRMADA"

  @TC-066 @negativo @api
  Scenario: Impedir cancelar reservas de un negocio ajeno
    Given un proveedor autenticado y una reserva de un servicio de otro proveedor
    When solicita POST "/api/v1/reservas/{idReserva}/cancelacion" con el identificador de esa reserva
    Then recibe el estado HTTP 403 y el código "ACCESO_DENEGADO"

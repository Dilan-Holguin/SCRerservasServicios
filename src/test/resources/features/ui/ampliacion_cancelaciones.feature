@ui @critica @ampliacion 
Feature: Trazabilidad y decisiones de cancelacion desde la UI

  @E2E-048 @CA-UI-49 @HU-08 @CP-08 @TC-032 @positivo
  Scenario: Identificar al cliente en el historial de la cancelacion
    Given una reserva UI independiente para el rol "CLIENTE"
    When cancela la reserva desde su panel y confirma
    Then la reserva web aparece "CANCELADA" y persiste al recargar el historial
    When filtra canceladas y despliega los cambios de su reserva
    Then el historial identifica al cliente como autor de la cancelacion y su fecha

  @E2E-049 @CA-UI-50 @HU-08 @CP-08 @TC-UI-008-02 @negativo
  Scenario: No ofrecer una segunda cancelacion al cliente
    Given una reserva UI independiente para el rol "CLIENTE"
    When cancela la reserva desde su panel y confirma
    Then la reserva web aparece "CANCELADA" y persiste al recargar el historial
    And la reserva cancelada no ofrece otra accion de cancelacion

  @E2E-050 @CA-UI-51 @HU-15 @CP-UI-15 @TC-UI-015-01 @positivo
  Scenario: Identificar al proveedor en el historial de la cancelacion
    Given una reserva UI independiente para el rol "PROVEEDOR"
    When cancela la reserva desde su panel y confirma
    Then la reserva web aparece "CANCELADA" y persiste al recargar el historial
    When despliega los cambios de la reserva cancelada por el proveedor
    Then el historial identifica al proveedor como autor de la cancelacion y su fecha

  @E2E-051 @CA-UI-52 @HU-15 @CP-UI-15 @TC-UI-015-02 @negativo
  Scenario: Conservar la reserva cuando el proveedor desiste
    Given una reserva UI independiente para el rol "PROVEEDOR"
    When abre la cancelacion y decide conservar la reserva
    Then la reserva web aparece "CONFIRMADA" y persiste al recargar el historial

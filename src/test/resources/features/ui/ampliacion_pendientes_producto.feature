@ui @critica @ampliacion @HU-15 @CP-UI-15 @pendiente_producto
Feature: Contrato de notificacion de cancelacion pendiente del producto

  @E2E-052 @CA-UI-53 @TC-UI-015-03 @positivo
  Scenario: Avisar al cliente por correo de la cancelacion del proveedor
    Given una reserva UI independiente para el rol "PROVEEDOR"
    When cancela la reserva desde su panel y confirma
    Then la reserva web aparece "CANCELADA" y persiste al recargar el historial
    And el cliente recibe un correo que identifica la reserva cancelada

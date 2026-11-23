# CP-02. La categoría debe existir en el catálogo visible.
@HU-02 @CP-02 @ux
Feature: Registro de proveedores
  Como visitante quiero registrar mi negocio con un servicio inicial.

  Background:
    Given que abro la página de creación de cuenta
    And selecciono el tipo de cuenta "Proveedor"

  @TC-005 @positivo
  Scenario: Registrar un proveedor con un servicio
    When completo los datos del proveedor con un correo nuevo y contraseña válida
    And completo un servicio de 30 minutos con una categoría disponible
    And pulso "Crear cuenta"
    Then veo la confirmación "Cuenta creada"
    And veo el rol "PROVEEDOR" y el estado "PENDIENTE_VERIFICACION"

  @TC-006 @negativo
  Scenario: Rechazar un proveedor sin servicios
    When completo los datos del proveedor con un correo nuevo y contraseña válida
    And dejo vacío el servicio inicial
    And pulso "Crear cuenta"
    Then veo la indicación de completar al menos un servicio
    And sigo en el formulario de registro

  @TC-007 @negativo
  Scenario: Rechazar un correo de proveedor duplicado
    Given que existe una cuenta con el correo del proveedor
    When completo los datos del proveedor y un servicio válido con ese correo
    And pulso "Crear cuenta"
    Then veo un aviso de que ese correo ya tiene cuenta
    And sigo en el formulario de registro

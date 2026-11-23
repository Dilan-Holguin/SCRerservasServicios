# CP-01. Los correos nuevos deben ser únicos en cada ejecución.
@HU-01 @CP-01 @ux
Feature: Registro de clientes
  Como visitante quiero crear una cuenta de cliente para reservar servicios.

  Background:
    Given que abro la página de creación de cuenta
    And selecciono el tipo de cuenta "Cliente"

  @TC-001 @positivo
  Scenario: Crear una cuenta de cliente
    When completo el nombre, un correo nuevo y una contraseña válida
    And pulso "Crear cuenta"
    Then veo la confirmación "Cuenta creada"
    And veo el correo, el rol "CLIENTE" y el estado "PENDIENTE_VERIFICACION"
    And veo la indicación de revisar mi correo para verificar la cuenta

  @TC-002 @negativo
  Scenario: Rechazar un correo ya registrado
    Given que existe una cuenta con el correo que voy a usar
    When completo el registro de cliente con ese correo
    And pulso "Crear cuenta"
    Then veo un aviso de que ese correo ya tiene cuenta
    And sigo en el formulario de registro

  @TC-003 @negativo
  Scenario: Rechazar una contraseña de menos de 12 caracteres
    When completo el registro con un correo nuevo y una contraseña de 11 caracteres
    And pulso "Crear cuenta"
    Then veo la validación de la contraseña
    And sigo en el formulario de registro

  @TC-004 @negativo
  Scenario: Rechazar un registro sin correo
    When completo el nombre y la contraseña sin escribir un correo
    And pulso "Crear cuenta"
    Then veo un error asociado al correo electrónico
    And sigo en el formulario de registro

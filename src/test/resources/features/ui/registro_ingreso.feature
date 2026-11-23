@base11
@ui @critica
Feature: Registro y acceso desde el navegador

  @E2E-001 @HU-01 @HU-03 @CP-01 @CP-03 @TC-001-HP @TC-009-HP @CA-UI-01 @CA-UI-03 @smoke
  Scenario: Registrarse como cliente, verificar el correo e ingresar al panel
    Given un visitante con datos nuevos de tipo "CLIENTE"
    When crea su cuenta desde el formulario web
    Then ve la confirmacion del registro con su correo y rol
    When abre en el frontend el enlace recibido por correo
    And ingresa desde el formulario con su clave correcta
    Then llega al panel web de "CLIENTE"

  @E2E-002 @HU-02 @HU-03 @CP-02 @CP-03 @TC-005-HP @TC-009-HP @CA-UI-02 @CA-UI-03 @smoke
  Scenario: Registrarse como proveedor, verificar el correo e ingresar al panel
    Given un visitante con datos nuevos de tipo "PROVEEDOR"
    When crea su cuenta desde el formulario web
    Then ve la confirmacion del registro con su correo y rol
    When abre en el frontend el enlace recibido por correo
    And ingresa desde el formulario con su clave correcta
    Then llega al panel web de "PROVEEDOR"

  @E2E-003 @HU-03 @CP-03 @TC-010 @CA-UI-04 @negativo
  Scenario: Informar credenciales equivocadas sin salir del formulario
    Given un cliente web con cuenta "verificada"
    When ingresa desde el formulario con una clave equivocada
    Then ve un aviso de ingreso "incorrecto" y permanece en el formulario

  @E2E-004 @HU-03 @CP-03 @TC-012 @CA-UI-05 @negativo
  Scenario: Solicitar la verificacion de una cuenta pendiente
    Given un cliente web con cuenta "pendiente"
    When ingresa desde el formulario con su clave correcta
    Then ve un aviso de ingreso "sin verificar" y permanece en el formulario

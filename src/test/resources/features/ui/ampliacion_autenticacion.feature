@ui @critica @ampliacion @HU-03 @CP-03
Feature: Seguridad y verificacion de cuenta desde la UI

  @E2E-022 @CA-UI-23 @TC-011 @seguridad
  Scenario: Bloquear el ingreso correcto despues de cinco claves incorrectas
    Given un cliente web con cuenta "verificada"
    When realiza cinco ingresos fallidos y luego usa la clave correcta
    Then el ingreso informa bloqueo temporal por quince minutos

  @E2E-023 @CA-UI-24 @TC-UI-003-01 @negativo
  Scenario: Mostrar error de enlace de verificacion invalido
    When abre un enlace web de verificacion invalido
    Then la verificacion muestra error y ofrece reenviar el correo

  @E2E-024 @CA-UI-25 @TC-UI-003-02 @negativo
  Scenario: Rechazar la reutilizacion del enlace de verificacion
    Given un cliente web con cuenta "pendiente"
    When guarda el enlace de verificacion recibido
    And abre en el frontend el enlace recibido por correo
    And vuelve a abrir el enlace de verificacion ya utilizado
    Then la verificacion muestra error y ofrece reenviar el correo

  @E2E-025 @CA-UI-26 @TC-UI-003-03 @positivo
  Scenario: Reenviar verificacion y activar la cuenta con el nuevo correo
    Given un cliente web con cuenta "pendiente"
    When guarda el enlace de verificacion recibido
    And solicita el reenvio desde la pagina de verificacion
    Then ve la respuesta generica de reenvio y recibe otro enlace
    When abre en el frontend el enlace recibido por correo
    And ingresa desde el formulario con su clave correcta
    Then llega al panel web de "CLIENTE"

  @E2E-026 @CA-UI-27 @TC-010 @TC-UI-003-04 @seguridad
  Scenario: No revelar si el correo existe en los rechazos de ingreso
    Given un cliente web con cuenta "verificada"
    When compara el rechazo de una clave errada con el de un correo inexistente
    Then ambos rechazos muestran el mismo mensaje generico

  @E2E-027 @CA-UI-28 @TC-UI-003-05 @seguridad
  Scenario: Exigir autenticacion despues de cerrar sesion
    Given un cliente web con cuenta "verificada"
    When ingresa desde el formulario con su clave correcta
    Then llega al panel web de "CLIENTE"
    When cierra sesion e intenta volver directamente al panel del cliente
    Then el panel protegido exige iniciar sesion otra vez

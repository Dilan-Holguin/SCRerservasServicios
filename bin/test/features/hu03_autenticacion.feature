# CP-03. Las cuentas de prueba verificadas o pendientes se preparan antes de ejecutar.
@HU-03 @CP-03 @ux
Feature: Verificación de cuenta e inicio de sesión
  Como usuario quiero acceder al panel correspondiente a mi cuenta verificada.

  @TC-067 @positivo
  Scenario: Verificar una cuenta mediante el enlace del correo
    Given que tengo una cuenta pendiente y un enlace de verificación vigente
    When abro el enlace de verificación en el navegador
    Then veo "Cuenta verificada"
    And puedo ir a iniciar sesión

  @TC-068 @negativo
  Scenario: Mostrar el error de un enlace de verificación inválido
    When abro la página de verificación con un token inválido
    Then veo "No pudimos verificar la cuenta"
    And veo la opción "Reenviar verificación"

  @TC-069 @positivo
  Scenario: Solicitar nuevamente el correo de verificación
    Given que abro la página de verificación
    When escribo el correo de una cuenta pendiente
    And pulso "Reenviar verificación"
    Then veo una respuesta de la solicitud de reenvío

  @TC-009 @positivo
  Scenario Outline: Ingresar con una cuenta verificada
    Given que tengo una cuenta verificada de tipo "<rol>"
    When ingreso con su correo y contraseña
    Then llego al panel "<panel>"

    Examples:
      | rol       | panel       |
      | CLIENTE   | Mis reservas |
      | PROVEEDOR | Mi negocio  |

  @TC-010 @negativo
  Scenario: Rechazar credenciales incorrectas sin revelar si existe el correo
    Given que abro la página de ingreso
    When escribo un correo y una contraseña incorrectos
    And pulso "Ingresar"
    Then veo un error de credenciales inválidas
    And el mensaje no revela si el correo existe

  @TC-011 @seguridad
  Scenario: Avisar del bloqueo tras cinco intentos fallidos
    Given que existe una cuenta verificada de prueba sin intentos fallidos
    When intento ingresar cinco veces con una contraseña incorrecta
    Then veo un aviso de bloqueo por 15 minutos
    And la contraseña correcta tampoco permite ingresar durante el bloqueo

  @TC-012 @negativo
  Scenario: Impedir el ingreso de una cuenta sin verificar
    Given que existe una cuenta pendiente de verificación
    When ingreso con sus credenciales válidas
    Then veo la indicación de verificar la cuenta
    And veo el botón "Reenviar correo de verificación"

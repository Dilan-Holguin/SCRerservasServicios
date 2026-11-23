@ui @critica @ampliacion @HU-07 @CP-07
Feature: Disponibilidad y reglas adicionales de reserva desde la UI

  @E2E-040 @CA-UI-41 @TC-UI-007-01 @positivo
  Scenario: Abrir desde el catalogo y cambiar las fechas de disponibilidad
    Given un cliente web y un turno disponible dentro de 10 dias
    When abre su servicio desde el catalogo y consulta el dia preparado
    Then la ficha refleja el rango elegido y ocho turnos con cupo

  @E2E-041 @CA-UI-42 @TC-UI-007-02 @negativo
  Scenario: Mostrar validacion de fechas invertidas
    Given un cliente web y un turno disponible dentro de 10 dias
    When consulta la ficha con un rango de fechas invertido
    Then la ficha informa que la fecha inicial es posterior a la final

  @E2E-042 @CA-UI-43 @TC-UI-007-03 @negativo
  Scenario: Rechazar consultas de mas de treinta y un dias
    Given un cliente web y un turno disponible dentro de 10 dias
    When amplia la consulta a 32 dias inclusivos desde el control de fechas
    Then la ficha informa que el rango maximo es de treinta y un dias

  @E2E-043 @CA-UI-44 @TC-028 @negativo
  Scenario: No ofrecer horarios fuera de la agenda
    Given un cliente web y un turno disponible dentro de 10 dias
    When consulta un dia que no pertenece a la agenda semanal
    Then la ficha no ofrece turnos fuera de la agenda

  @E2E-044 @CA-UI-45 @TC-026 @negativo
  Scenario: Ocultar un turno cuando otro cliente ocupa el ultimo cupo
    Given un turno UI cuyo ultimo cupo ya fue reservado
    When otro cliente actualiza la ficha del servicio ocupado
    Then el turno de las nueve no se ofrece y el siguiente sigue disponible

  @E2E-045 @CA-UI-46 @TC-UI-007-04 @positivo
  Scenario: Retomar el turno elegido por un visitante despues de ingresar
    Given un cliente web y un turno disponible dentro de 10 dias
    When cierra sesion y selecciona el turno como visitante
    Then el ingreso solicita autenticarse para confirmar el turno elegido
    When ingresa conservando el destino del turno seleccionado
    Then la reserva pendiente queda confirmada y aparece en el historial

  @E2E-046 @CA-UI-47 @TC-UI-007-05 @seguridad
  Scenario: No crear reservas desde una cuenta de proveedor
    Given un proveedor web con servicio "con agenda"
    When consulta y pulsa un turno desde su cuenta de proveedor
    Then la ficha explica que solo el cliente reserva y el proveedor no tiene reservas nuevas

  @E2E-047 @CA-UI-48 @TC-UI-007-06 @borde
  Scenario: Aceptar el limite de treinta y un dias inclusivos
    Given un cliente web y un turno disponible dentro de 10 dias
    When amplia la consulta a 31 dias inclusivos desde el control de fechas
    Then la consulta de treinta y un dias ofrece los cinco dias semanales preparados

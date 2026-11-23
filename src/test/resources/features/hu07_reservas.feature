# CP-07. Se requieren turnos futuros y clientes de prueba distintos por escenario.
@HU-07 @CP-07 @ux
Feature: Consultar disponibilidad y crear reservas
  Como cliente quiero elegir un turno libre y confirmar mi reserva.

  @TC-071 @positivo
  Scenario: Consultar los turnos de un servicio activo
    Given que el catálogo contiene un servicio activo con turnos futuros
    When abro su ficha desde el catálogo
    Then veo la sección "Disponibilidad"
    And veo turnos con la hora y el número de cupos libres

  @TC-072 @positivo
  Scenario: Cambiar el rango de fechas consultado
    Given que estoy en la ficha de un servicio activo
    When ajusto las fechas "Desde" y "Hasta" a un rango válido de hasta 31 días
    Then veo la disponibilidad correspondiente al rango elegido
    And el rango elegido queda reflejado en la dirección de la página

  @TC-073 @negativo
  Scenario: Mostrar un rango de fechas invertido
    Given que estoy en la ficha de un servicio activo
    When elijo una fecha "Desde" posterior a "Hasta"
    Then veo "La fecha inicial no puede ser posterior a la final"

  @TC-025 @positivo
  Scenario: Reservar un turno disponible como cliente
    Given que ingreso como cliente verificado
    And un servicio activo ofrece un turno futuro con cupo
    When abro la ficha del servicio y selecciono ese turno
    Then veo "Reserva confirmada"
    And encuentro la reserva en "Mis reservas" con estado "CONFIRMADA"

  @TC-074 @positivo
  Scenario: Continuar la reserva después de iniciar sesión
    Given que no he iniciado sesión
    And un servicio activo ofrece un turno futuro con cupo
    When selecciono ese turno en la ficha del servicio
    Then llego a la página "Ingresar" con el aviso para confirmar el turno seleccionado
    When ingreso con una cuenta de cliente verificada
    Then regreso al servicio y veo la reserva confirmada

  @TC-026 @negativo
  Scenario: No mostrar un turno después de ocupar su último cupo
    Given que un servicio tiene un turno futuro con un único cupo disponible
    When otro cliente reserva el último cupo
    And actualizo la disponibilidad del servicio
    Then el turno ocupado ya no aparece entre los turnos disponibles

  @TC-075 @negativo
  Scenario: Impedir que un proveedor reserve desde su cuenta
    Given que ingreso como proveedor verificado
    When abro la ficha de un servicio con turnos libres
    Then veo que solo las cuentas de cliente pueden reservar turnos

@HU-05 @CP-05 @ux
Feature: Editar bloques de agenda
  Como proveedor quiero modificar mi agenda sin perder de vista las reservas afectadas.

  Background:
    Given que ingreso como proveedor verificado con un servicio activo
    And abro la sección "Agenda" de mi negocio

  @TC-018 @positivo
  Scenario: Editar un bloque sin reservas
    Given que mi servicio tiene un bloque sin reservas futuras
    When pulso editar el bloque y fijo un nuevo rango válido
    And pulso "Guardar cambios"
    Then veo "Bloque actualizado"
    And el nuevo horario aparece en "Bloques definidos"

  @TC-019 @negativo
  Scenario: Advertir sobre reservas afectadas por un cambio
    Given que mi servicio tiene un bloque con una reserva futura confirmada
    When edito el bloque con un rango que deja fuera esa reserva
    And pulso "Guardar cambios"
    Then veo el diálogo "Este cambio afecta reservas confirmadas"
    And veo la reserva afectada

  @TC-020 @positivo
  Scenario: Confirmar un cambio que afecta una reserva
    Given que el diálogo muestra una reserva afectada por la edición
    When pulso "Aplicar de todos modos"
    Then veo "Bloque actualizado"
    And la reserva afectada sigue confirmada

  @TC-021 @negativo
  Scenario: Rechazar un rango inválido al editar
    Given que mi servicio tiene un bloque sin reservas futuras
    When edito el bloque con una hora de fin anterior a la hora de inicio
    And pulso "Guardar cambios"
    Then veo un error de validación
    And el bloque mantiene el rango anterior

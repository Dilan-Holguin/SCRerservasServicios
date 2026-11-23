# CP-09. El conflicto requiere dos servicios del mismo proveedor que compartan recurso y horario.
@HU-09 @CP-09 @ux
Feature: Gestionar y respetar los recursos compartidos
  Como proveedor quiero asociar recursos a servicios sin asignarlos dos veces al mismo tiempo.

  @TC-036 @positivo
  Scenario: Crear y consultar un recurso
    Given que ingreso como proveedor verificado
    When abro la sección "Recursos" de mi negocio
    And creo un recurso con nombre nuevo y un tipo disponible
    Then veo el recurso en "Mis recursos" con estado "Activo"

  @TC-033 @positivo
  Scenario: Asociar un recurso al servicio antes de reservar
    Given que ingreso como proveedor con un recurso activo y un servicio propio
    When abro la sección "Recursos" de mi negocio
    And selecciono el servicio y marco el recurso
    And pulso "Guardar asignación"
    Then veo la confirmación de que el recurso quedó asignado al servicio

  @TC-034 @negativo
  Scenario: Evitar reservar dos servicios que necesitan el mismo recurso a la misma hora
    Given que dos servicios activos comparten un recurso y tienen turnos a la misma hora
    And que un cliente ya reservó el primer servicio para ese horario
    When otro cliente intenta reservar el segundo servicio a la misma hora
    Then veo "No se pudo crear la reserva"
    And veo el recurso ocupado entre los motivos del rechazo

  @TC-035 @positivo
  Scenario: Recuperar la disponibilidad del recurso tras cancelar
    Given que una reserva futura ocupa el recurso compartido
    When el cliente cancela esa reserva dentro del plazo permitido
    And otro cliente consulta el segundo servicio en ese horario
    Then puede ver y reservar el turno que utiliza ese recurso

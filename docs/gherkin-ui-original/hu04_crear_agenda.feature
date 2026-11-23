# CP-04. Cada escenario usa un servicio propio y horarios de prueba aislados.
@HU-04 @CP-04 @ux
Feature: Crear bloques de agenda
  Como proveedor quiero publicar mi disponibilidad semanal.

  Background:
    Given que ingreso como proveedor verificado con un servicio activo
    And abro la sección "Agenda" de mi negocio

  @TC-014 @positivo
  Scenario: Crear un bloque semanal válido
    When selecciono mi servicio y un día sin bloques
    And fijo la hora de inicio y la hora de fin para cubrir la duración del servicio
    And pulso "Crear bloques"
    Then veo el aviso de creación
    And el nuevo bloque aparece en "Bloques definidos"

  @TC-017 @positivo
  Scenario: Crear bloques en varios días
    When selecciono dos días sin bloques para mi servicio
    And fijo un rango horario válido
    And pulso "Crear bloques"
    Then veo un bloque definido por cada día elegido

  @TC-015 @negativo
  Scenario: Rechazar un bloque que se superpone
    Given que mi servicio ya tiene un bloque en un día de la semana
    When intento crear otro bloque superpuesto para el mismo día
    Then veo un error de solapamiento
    And el listado mantiene el bloque original

  @TC-016 @negativo
  Scenario: Rechazar un rango cuya hora final es anterior a la inicial
    When selecciono un día y fijo una hora de fin anterior a la hora de inicio
    And pulso "Crear bloques"
    Then veo un error que impide crear el bloque

  @negativo
  @TC-070 @negativo
  Scenario: Rechazar un bloque más corto que la duración del servicio
    When fijo un bloque de menos minutos que la duración de mi servicio
    And pulso "Crear bloques"
    Then veo un error que impide crear el bloque

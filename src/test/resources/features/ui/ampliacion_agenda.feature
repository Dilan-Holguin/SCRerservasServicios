@ui @critica @ampliacion @HU-04 @CP-04
Feature: Limites y multiples dias de agenda desde la UI

  @E2E-028 @CA-UI-29 @TC-016 @negativo
  Scenario: Rechazar hora final anterior a la inicial
    Given un proveedor web con servicio "sin agenda"
    When publica desde la UI un bloque de "12:00" a "08:00"
    Then la agenda rechaza el cambio y continua sin bloques

  @E2E-029 @CA-UI-30 @TC-UI-004-01 @negativo
  Scenario: Rechazar un bloque mas corto que la duracion del servicio
    Given un proveedor web con servicio "sin agenda"
    When publica desde la UI un bloque de "08:00" a "08:15"
    Then la agenda rechaza el cambio y continua sin bloques

  @E2E-030 @CA-UI-31 @TC-UI-004-02 @negativo
  Scenario: Exigir al menos un dia para publicar agenda
    Given un proveedor web con servicio "sin agenda"
    When intenta crear la agenda sin seleccionar dias
    Then la agenda rechaza el cambio y continua sin bloques

  @E2E-031 @CA-UI-32 @TC-017 @positivo
  Scenario: Publicar y conservar bloques en dos dias distintos
    Given un proveedor web con servicio "sin agenda"
    When publica un mismo rango para dos dias distintos
    Then la agenda conserva un bloque por cada dia elegido

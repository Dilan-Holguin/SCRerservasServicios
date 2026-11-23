@ui @critica @ampliacion @HU-05 @CP-05
Feature: Editar agenda respetando las reservas existentes

  @E2E-032 @CA-UI-33 @TC-018 @positivo
  Scenario: Editar un bloque sin reservas y conservar el cambio
    Given un proveedor web con servicio "con agenda"
    When edita el bloque original de "10:00" a "14:00"
    Then el bloque editado persiste de "10:00" a "14:00"
    And la disponibilidad publica refleja el nuevo bloque de diez a catorce

  @E2E-033 @CA-UI-34 @TC-019 @TC-UI-005-01 @negativo
  Scenario: Advertir las reservas afectadas y desistir de la edicion
    Given una agenda UI con reserva confirmada en el bloque original
    When edita el bloque original de "10:00" a "14:00"
    Then el dialogo informa la reserva afectada sin aplicar el cambio
    When desiste del cambio de agenda
    Then el bloque original permanece publicado de ocho a doce
    And la reserva afectada permanece confirmada en el historial

  @E2E-034 @CA-UI-35 @TC-020 @positivo
  Scenario: Confirmar una edicion que deja fuera una reserva
    Given una agenda UI con reserva confirmada en el bloque original
    When edita el bloque original de "10:00" a "14:00"
    Then el dialogo informa la reserva afectada sin aplicar el cambio
    When confirma la edicion a pesar de las reservas afectadas
    Then el bloque editado persiste de "10:00" a "14:00"
    And la disponibilidad publica refleja el nuevo bloque de diez a catorce
    And la reserva afectada permanece confirmada en el historial

  @E2E-035 @CA-UI-36 @TC-021 @negativo
  Scenario: Rechazar una edicion con rango invertido
    Given un proveedor web con servicio "con agenda"
    When edita el bloque original de "12:00" a "08:00"
    Then la edicion invalida muestra error y conserva el horario original

  @E2E-036 @CA-UI-37 @TC-UI-005-02 @negativo
  Scenario: Rechazar una edicion que se solapa con otro bloque
    Given un proveedor web con servicio "con agenda"
    And la agenda incluye ademas un bloque de catorce a dieciseis
    When edita el bloque original de "15:00" a "17:00"
    Then la edicion solapada conserva los dos bloques originales

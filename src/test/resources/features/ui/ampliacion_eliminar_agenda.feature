@ui @critica @ampliacion @HU-06 @CP-06
Feature: Eliminar agenda respetando las reservas existentes

  @E2E-037 @CA-UI-38 @TC-022 @positivo
  Scenario: Eliminar un bloque sin reservas
    Given un proveedor web con servicio "con agenda"
    When solicita eliminar el bloque original desde su agenda
    Then la agenda confirma la eliminacion y queda sin bloques al recargar
    When consulta la disponibilidad del dia de la agenda eliminada
    Then la ficha no ofrece turnos fuera de la agenda

  @E2E-038 @CA-UI-39 @TC-023 @TC-UI-006-01 @negativo
  Scenario: Conservar el bloque al desistir de eliminarlo con reservas
    Given una agenda UI con reserva confirmada en el bloque original
    When solicita eliminar el bloque original desde su agenda
    Then el dialogo informa la reserva afectada sin aplicar el cambio
    When desiste del cambio de agenda
    Then el bloque original permanece publicado de ocho a doce
    And la reserva afectada permanece confirmada en el historial

  @E2E-039 @CA-UI-40 @TC-024 @positivo
  Scenario: Confirmar eliminacion conservando la reserva afectada
    Given una agenda UI con reserva confirmada en el bloque original
    When solicita eliminar el bloque original desde su agenda
    Then el dialogo informa la reserva afectada sin aplicar el cambio
    When confirma eliminar el bloque a pesar de sus reservas
    Then la agenda confirma la eliminacion y queda sin bloques al recargar
    When consulta la disponibilidad del dia de la agenda eliminada
    Then la ficha no ofrece turnos fuera de la agenda
    And la reserva afectada permanece confirmada en el historial

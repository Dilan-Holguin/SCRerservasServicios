@ui @critica @ampliacion 
Feature: Validaciones adicionales del registro web

  @E2E-012 @CA-UI-13 @HU-01 @CP-01 @TC-002 @negativo
  Scenario: Rechazar el correo de cliente ya registrado
    Given una cuenta UI de tipo "CLIENTE" con correo ocupado
    When envia el registro web con "correo duplicado"
    Then el registro muestra correo duplicado y conserva el formulario

  @E2E-013 @CA-UI-14 @HU-01 @CP-01 @TC-003 @negativo
  Scenario: Rechazar clave de once caracteres
    Given un visitante con datos nuevos de tipo "CLIENTE"
    When envia el registro web con "clave corta"
    Then el registro marca el campo "password" como invalido

  @E2E-014 @CA-UI-15 @HU-01 @CP-01 @TC-004 @negativo
  Scenario: Rechazar correo vacio
    Given un visitante con datos nuevos de tipo "CLIENTE"
    When envia el registro web con "correo vacío"
    Then el registro marca el campo "email" como invalido

  @E2E-015 @CA-UI-16 @HU-01 @CP-01 @TC-UI-001-01 @negativo
  Scenario: Rechazar formato invalido de correo
    Given un visitante con datos nuevos de tipo "CLIENTE"
    When envia el registro web con "correo inválido"
    Then el registro marca el campo "email" como invalido

  @E2E-016 @CA-UI-17 @HU-01 @CP-01 @TC-UI-001-02 @negativo
  Scenario: Rechazar nombre vacio
    Given un visitante con datos nuevos de tipo "CLIENTE"
    When envia el registro web con "nombre vacío"
    Then el registro marca el campo "nombreCompleto" como invalido

  @E2E-017 @CA-UI-18 @HU-02 @CP-02 @TC-007 @negativo
  Scenario: Rechazar el correo de proveedor ya registrado
    Given una cuenta UI de tipo "PROVEEDOR" con correo ocupado
    When envia el registro web con "correo duplicado"
    Then el registro muestra correo duplicado y conserva el formulario

  @E2E-018 @CA-UI-19 @HU-02 @CP-02 @TC-006 @negativo
  Scenario: Exigir un servicio inicial al proveedor
    Given un visitante con datos nuevos de tipo "PROVEEDOR"
    When envia el registro web con "sin servicio"
    Then el registro exige al menos un servicio

  @E2E-019 @CA-UI-20 @HU-02 @CP-02 @TC-UI-002-01 @negativo
  Scenario: Exigir categoria del servicio inicial
    Given un visitante con datos nuevos de tipo "PROVEEDOR"
    When envia el registro web con "sin categoría"
    Then el registro marca el campo "servicios[0].idCategoria" como invalido

  @E2E-020 @CA-UI-21 @HU-02 @CP-02 @TC-UI-002-02 @negativo
  Scenario: Rechazar duracion nula del servicio inicial
    Given un visitante con datos nuevos de tipo "PROVEEDOR"
    When envia el registro web con "duración inválida"
    Then el registro marca el campo "servicios[0].duracionMinutos" como invalido

  @E2E-021 @CA-UI-22 @HU-02 @CP-02 @TC-UI-002-03 @negativo
  Scenario: Rechazar capacidad nula del servicio inicial
    Given un visitante con datos nuevos de tipo "PROVEEDOR"
    When envia el registro web con "capacidad inválida"
    Then el registro marca el campo "servicios[0].capacidad" como invalido

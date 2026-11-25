# Trazabilidad de los escenarios UI

Fuentes: documentos del proyecto «3. HU Definición de Casos de Prueba.md» y «HU-gherkin-plataforma-reservas.md». Los CP y TC provienen de esos documentos; las etiquetas E2E y CA-UI se definen en este repositorio. Los documentos de Sprint 1 ubicaban la cancelación del proveedor dentro de CP-08; la implementación actual la identifica como HU-15.

La suite anterior tenía 10 declaraciones de escenario y 11 ejecuciones, porque el registro era un Scenario Outline con dos ejemplos. Ahora son 11 Scenario explícitos, sin aumentar el alcance.

| Escenario | HU comprobadas | CP | TC de referencia | Criterios UI locales |
|---|---|---|---|---|
| E2E-001 Registro de cliente, correo e ingreso | HU-01, HU-03 | CP-01, CP-03 | TC-001-HP, TC-009-HP | CA-UI-01, CA-UI-03 |
| E2E-002 Registro de proveedor, correo e ingreso | HU-02, HU-03 | CP-02, CP-03 | TC-005-HP, TC-009-HP | CA-UI-02, CA-UI-03 |
| E2E-003 Clave incorrecta | HU-03 | CP-03 | TC-010 | CA-UI-04 |
| E2E-004 Cuenta pendiente | HU-03 | CP-03 | TC-012 | CA-UI-05 |
| E2E-005 Crear agenda | HU-04 | CP-04 | TC-014-HP | CA-UI-06 |
| E2E-006 Solapamiento de agenda | HU-04 | CP-04 | TC-015 | CA-UI-07 |
| E2E-007 Crear reserva | HU-07 | CP-07 | TC-025-HP | CA-UI-08 |
| E2E-008 Cancelar como cliente | HU-08 | CP-08 | TC-029-HP, TC-032 | CA-UI-09 |
| E2E-009 Conservar en el diálogo | HU-08 | CP-08 | TC-UI-008-01 (nuevo, local) | CA-UI-10 |
| E2E-010 Restricción de anticipación | HU-08 | CP-08 | TC-030 (adaptado) | CA-UI-11 |
| E2E-011 Cancelar como proveedor | HU-15 | CP-08 (referencia histórica) | TC-031 (parcial) | CA-UI-12 |

Las precondiciones que registran usuarios y crean agendas no se cuentan como nuevos casos de cobertura de esas HU.

## Criterios observables definidos para esta suite

Los documentos fuente no asignan códigos a los criterios de aceptación. Estos CA-UI son identificadores locales para las comprobaciones automatizadas, no una numeración oficial del profesor.

| Etiqueta | Comprobación observable |
|---|---|
| CA-UI-01 | Registro de cliente muestra correo, rol y estado pendiente. |
| CA-UI-02 | Registro de proveedor con servicio y categoría muestra correo, rol y estado pendiente. |
| CA-UI-03 | Verificación con el correo recibido permite ingresar al panel correspondiente. |
| CA-UI-04 | Clave incorrecta muestra aviso genérico y mantiene el formulario. |
| CA-UI-05 | Cuenta sin verificar muestra instrucción de verificación e impide entrar al panel. |
| CA-UI-06 | Bloque semanal de 08:00 a 12:00 aparece en agenda y ofrece ocho turnos de 30 minutos. |
| CA-UI-07 | Un bloque solapado muestra error y conserva un único bloque original y sus ocho turnos. |
| CA-UI-08 | Reservar el turno disponible muestra confirmación y estado CONFIRMADA al recargar el historial. |
| CA-UI-09 | Cancelación confirmada del cliente persiste como CANCELADA en historial y libera un cupo en la ficha. |
| CA-UI-10 | Desistir en el diálogo conserva CONFIRMADA tras recargar. |
| CA-UI-11 | A dos días de la reserva se explica la anticipación de cinco días, no se ofrece cancelar y persiste CONFIRMADA. |
| CA-UI-12 | El proveedor cancela a dos días de la reserva, persiste CANCELADA y se libera un cupo. |

## Límites de las equivalencias

- TC-009-HP: se valida acceso al panel; no se inspecciona el JWT.
- TC-030: se valida la restricción visible antes de enviar una cancelación; no se fuerza una petición para comprobar el rechazo del servidor.
- TC-031: se valida cancelación y liberación de cupo; falta comprobar la notificación de cancelación al cliente. El tag es una referencia parcial.
- TC-032: se valida el estado cancelado en el historial; no se inspecciona el registro interno de auditoría.
- TC-UI-008-01 es un caso nuevo de la interfaz que no tenía TC propio en los documentos.


## Ampliación escrita a petición del equipo

Se añadieron **48 escenarios**, con E2E-012 a E2E-059. Hay **59 escenarios en total: 58 seleccionados por el grupo UI normal y un contrato pendiente del producto**. La suite refactorizada se ejecutó el 25 de noviembre de 2026 contra la aplicación en el commit `27ac566`: **58/58 escenarios aprobados, 218/218 pasos y 0 fallos**, incluidos E2E-042 y E2E-056. El detalle está en el estado de validación.

Los CP y TC numéricos son referencias a los casos del plan original; `TC-UI-...`, `CP-UI-15`, `E2E-...` y `CA-UI-...` son identificadores locales. La numeración local no sustituye una aprobación del profesor.

| Área añadida | Casos | Estado |
|---|---:|---|
| Validaciones adicionales del registro web | 10 | Aprobado en la corrida post-refactor |
| Seguridad y verificacion de cuenta desde la UI | 6 | Aprobado en la corrida post-refactor |
| Limites y multiples dias de agenda desde la UI | 4 | Aprobado en la corrida post-refactor |
| Editar agenda respetando las reservas existentes | 5 | Aprobado en la corrida post-refactor |
| Eliminar agenda respetando las reservas existentes | 3 | Aprobado en la corrida post-refactor |
| Disponibilidad y reglas adicionales de reserva desde la UI | 8 | Aprobado en la corrida post-refactor |
| Trazabilidad y decisiones de cancelacion desde la UI | 4 | Aprobado en la corrida post-refactor |
| Gestion de recursos y disponibilidad compartida desde la UI | 7 | Aprobado en la corrida post-refactor |
| Contrato de notificacion de cancelacion pendiente del producto | 1 | Contrato pendiente del producto |

| Escenario | Comportamiento | HU | CP | TC de referencia o local | Criterio UI local |
|---|---|---|---|---|---|
| E2E-012 | Rechazar el correo de cliente ya registrado | HU-01 | CP-01 | TC-002 | CA-UI-13 |
| E2E-013 | Rechazar clave de once caracteres | HU-01 | CP-01 | TC-003 | CA-UI-14 |
| E2E-014 | Rechazar correo vacio | HU-01 | CP-01 | TC-004 | CA-UI-15 |
| E2E-015 | Rechazar formato invalido de correo | HU-01 | CP-01 | TC-UI-001-01 | CA-UI-16 |
| E2E-016 | Rechazar nombre vacio | HU-01 | CP-01 | TC-UI-001-02 | CA-UI-17 |
| E2E-017 | Rechazar el correo de proveedor ya registrado | HU-02 | CP-02 | TC-007 | CA-UI-18 |
| E2E-018 | Exigir un servicio inicial al proveedor | HU-02 | CP-02 | TC-006 | CA-UI-19 |
| E2E-019 | Exigir categoria del servicio inicial | HU-02 | CP-02 | TC-UI-002-01 | CA-UI-20 |
| E2E-020 | Rechazar duracion nula del servicio inicial | HU-02 | CP-02 | TC-UI-002-02 | CA-UI-21 |
| E2E-021 | Rechazar capacidad nula del servicio inicial | HU-02 | CP-02 | TC-UI-002-03 | CA-UI-22 |
| E2E-022 | Bloquear el ingreso correcto despues de cinco claves incorrectas | HU-03 | CP-03 | TC-011 | CA-UI-23 |
| E2E-023 | Mostrar error de enlace de verificacion invalido | HU-03 | CP-03 | TC-UI-003-01 | CA-UI-24 |
| E2E-024 | Rechazar la reutilizacion del enlace de verificacion | HU-03 | CP-03 | TC-UI-003-02 | CA-UI-25 |
| E2E-025 | Reenviar verificacion y activar la cuenta con el nuevo correo | HU-03 | CP-03 | TC-UI-003-03 | CA-UI-26 |
| E2E-026 | No revelar si el correo existe en los rechazos de ingreso | HU-03 | CP-03 | TC-010, TC-UI-003-04 | CA-UI-27 |
| E2E-027 | Exigir autenticacion despues de cerrar sesion | HU-03 | CP-03 | TC-UI-003-05 | CA-UI-28 |
| E2E-028 | Rechazar hora final anterior a la inicial | HU-04 | CP-04 | TC-016 | CA-UI-29 |
| E2E-029 | Rechazar un bloque mas corto que la duracion del servicio | HU-04 | CP-04 | TC-UI-004-01 | CA-UI-30 |
| E2E-030 | Exigir al menos un dia para publicar agenda | HU-04 | CP-04 | TC-UI-004-02 | CA-UI-31 |
| E2E-031 | Publicar y conservar bloques en dos dias distintos | HU-04 | CP-04 | TC-017 | CA-UI-32 |
| E2E-032 | Editar un bloque sin reservas y conservar el cambio | HU-05 | CP-05 | TC-018 | CA-UI-33 |
| E2E-033 | Advertir las reservas afectadas y desistir de la edicion | HU-05 | CP-05 | TC-019, TC-UI-005-01 | CA-UI-34 |
| E2E-034 | Confirmar una edicion que deja fuera una reserva | HU-05 | CP-05 | TC-020 | CA-UI-35 |
| E2E-035 | Rechazar una edicion con rango invertido | HU-05 | CP-05 | TC-021 | CA-UI-36 |
| E2E-036 | Rechazar una edicion que se solapa con otro bloque | HU-05 | CP-05 | TC-UI-005-02 | CA-UI-37 |
| E2E-037 | Eliminar un bloque sin reservas | HU-06 | CP-06 | TC-022 | CA-UI-38 |
| E2E-038 | Conservar el bloque al desistir de eliminarlo con reservas | HU-06 | CP-06 | TC-023, TC-UI-006-01 | CA-UI-39 |
| E2E-039 | Confirmar eliminacion conservando la reserva afectada | HU-06 | CP-06 | TC-024 | CA-UI-40 |
| E2E-040 | Abrir desde el catalogo y cambiar las fechas de disponibilidad | HU-07 | CP-07 | TC-UI-007-01 | CA-UI-41 |
| E2E-041 | Mostrar validacion de fechas invertidas | HU-07 | CP-07 | TC-UI-007-02 | CA-UI-42 |
| E2E-042 | Rechazar consultas de mas de treinta y un dias | HU-07 | CP-07 | TC-UI-007-03 | CA-UI-43 |
| E2E-043 | No ofrecer horarios fuera de la agenda | HU-07 | CP-07 | TC-028 | CA-UI-44 |
| E2E-044 | Ocultar un turno cuando otro cliente ocupa el ultimo cupo | HU-07 | CP-07 | TC-026 | CA-UI-45 |
| E2E-045 | Retomar el turno elegido por un visitante despues de ingresar | HU-07 | CP-07 | TC-UI-007-04 | CA-UI-46 |
| E2E-046 | No crear reservas desde una cuenta de proveedor | HU-07 | CP-07 | TC-UI-007-05 | CA-UI-47 |
| E2E-047 | Aceptar el limite de treinta y un dias inclusivos | HU-07 | CP-07 | TC-UI-007-06 | CA-UI-48 |
| E2E-048 | Identificar al cliente en el historial de la cancelacion | HU-08 | CP-08 | TC-032 | CA-UI-49 |
| E2E-049 | No ofrecer una segunda cancelacion al cliente | HU-08 | CP-08 | TC-UI-008-02 | CA-UI-50 |
| E2E-050 | Identificar al proveedor en el historial de la cancelacion | HU-15 | CP-UI-15 | TC-UI-015-01 | CA-UI-51 |
| E2E-051 | Conservar la reserva cuando el proveedor desiste | HU-15 | CP-UI-15 | TC-UI-015-02 | CA-UI-52 |
| E2E-053 | Crear un recurso y consultarlo activo | HU-09 | CP-09 | TC-036 | CA-UI-54 |
| E2E-054 | Asignar un recurso activo al servicio propio | HU-09 | CP-09 | TC-033 | CA-UI-55 |
| E2E-055 | Impedir la creacion de un recurso sin nombre | HU-09 | CP-09 | TC-UI-009-01 | CA-UI-56 |
| E2E-056 | Impedir la creacion de un recurso sin tipo | HU-09 | CP-09 | TC-UI-009-02 | CA-UI-57 |
| E2E-057 | No ofrecer dos servicios con el mismo recurso ocupado | HU-09, HU-07 | CP-09, CP-07 | TC-027, TC-034 | CA-UI-58 |
| E2E-058 | Volver a reservar el recurso compartido despues de cancelar | HU-09, HU-07 | CP-09, CP-07 | TC-035 | CA-UI-59 |
| E2E-059 | Retirar los recursos asignados al servicio | HU-09 | CP-09 | TC-UI-009-03 | CA-UI-60 |
| E2E-052 | Avisar al cliente por correo de la cancelacion del proveedor | HU-15 | CP-UI-15 | TC-UI-015-03 | CA-UI-53 |

| Criterio | Resultado observable esperado |
|---|---|
| CA-UI-13 | el registro muestra correo duplicado y conserva el formulario. |
| CA-UI-14 | el registro marca el campo "password" como invalido. |
| CA-UI-15 | el registro marca el campo "email" como invalido. |
| CA-UI-16 | el registro marca el campo "email" como invalido. |
| CA-UI-17 | el registro marca el campo "nombreCompleto" como invalido. |
| CA-UI-18 | el registro muestra correo duplicado y conserva el formulario. |
| CA-UI-19 | el registro exige al menos un servicio. |
| CA-UI-20 | el registro marca el campo "servicios[0].idCategoria" como invalido. |
| CA-UI-21 | el registro marca el campo "servicios[0].duracionMinutos" como invalido. |
| CA-UI-22 | el registro marca el campo "servicios[0].capacidad" como invalido. |
| CA-UI-23 | el ingreso informa bloqueo temporal por quince minutos. |
| CA-UI-24 | la verificacion muestra error y ofrece reenviar el correo. |
| CA-UI-25 | la verificacion muestra error y ofrece reenviar el correo. |
| CA-UI-26 | ve la respuesta generica de reenvio y recibe otro enlace; llega al panel web de "CLIENTE". |
| CA-UI-27 | ambos rechazos muestran el mismo mensaje generico. |
| CA-UI-28 | llega al panel web de "CLIENTE"; el panel protegido exige iniciar sesion otra vez. |
| CA-UI-29 | la agenda rechaza el cambio y continua sin bloques. |
| CA-UI-30 | la agenda rechaza el cambio y continua sin bloques. |
| CA-UI-31 | la agenda rechaza el cambio y continua sin bloques. |
| CA-UI-32 | la agenda conserva un bloque por cada dia elegido. |
| CA-UI-33 | el bloque editado persiste de "10:00" a "14:00"; la disponibilidad publica refleja el nuevo bloque de diez a catorce. |
| CA-UI-34 | el dialogo informa la reserva afectada sin aplicar el cambio; el bloque original permanece publicado de ocho a doce; la reserva afectada permanece confirmada en el historial. |
| CA-UI-35 | el dialogo informa la reserva afectada sin aplicar el cambio; el bloque editado persiste de "10:00" a "14:00"; la disponibilidad publica refleja el nuevo bloque de diez a catorce; la reserva afectada permanece confirmada en el historial. |
| CA-UI-36 | la edicion invalida muestra error y conserva el horario original. |
| CA-UI-37 | la edicion solapada conserva los dos bloques originales. |
| CA-UI-38 | la agenda confirma la eliminacion y queda sin bloques al recargar; la ficha no ofrece turnos fuera de la agenda. |
| CA-UI-39 | el dialogo informa la reserva afectada sin aplicar el cambio; el bloque original permanece publicado de ocho a doce; la reserva afectada permanece confirmada en el historial. |
| CA-UI-40 | el dialogo informa la reserva afectada sin aplicar el cambio; la agenda confirma la eliminacion y queda sin bloques al recargar; la ficha no ofrece turnos fuera de la agenda; la reserva afectada permanece confirmada en el historial. |
| CA-UI-41 | la ficha refleja el rango elegido y ocho turnos con cupo. |
| CA-UI-42 | la ficha informa que la fecha inicial es posterior a la final. |
| CA-UI-43 | la ficha rechaza el rango excedido y muestra el limite en la ayuda. |
| CA-UI-44 | la ficha no ofrece turnos fuera de la agenda. |
| CA-UI-45 | el turno de las nueve no se ofrece y el siguiente sigue disponible. |
| CA-UI-46 | el ingreso solicita autenticarse para confirmar el turno elegido; la reserva pendiente queda confirmada y aparece en el historial. |
| CA-UI-47 | la ficha explica que solo el cliente reserva y el proveedor no tiene reservas nuevas. |
| CA-UI-48 | la consulta de treinta y un dias ofrece los cinco dias semanales preparados. |
| CA-UI-49 | la reserva web aparece "CANCELADA" y persiste al recargar el historial; el historial identifica al cliente como autor de la cancelacion y su fecha. |
| CA-UI-50 | la reserva web aparece "CANCELADA" y persiste al recargar el historial; la reserva cancelada no ofrece otra accion de cancelacion. |
| CA-UI-51 | la reserva web aparece "CANCELADA" y persiste al recargar el historial; el historial identifica al proveedor como autor de la cancelacion y su fecha. |
| CA-UI-52 | la reserva web aparece "CONFIRMADA" y persiste al recargar el historial. |
| CA-UI-53 | la reserva web aparece "CANCELADA" y persiste al recargar el historial; el cliente recibe un correo que identifica la reserva cancelada. |
| CA-UI-54 | el recurso aparece activo en el listado despues de recargar. |
| CA-UI-55 | ve la confirmacion de un recurso asignado. |
| CA-UI-56 | la interfaz mantiene deshabilitada la creacion del recurso. |
| CA-UI-57 | la interfaz mantiene deshabilitada la creacion del recurso. |
| CA-UI-58 | el turno de las nueve no se ofrece y el siguiente sigue disponible. |
| CA-UI-59 | el segundo cliente puede reservar el recurso liberado desde la ficha. |
| CA-UI-60 | ve la confirmacion de recursos retirados. |

## Límites y pendientes actuales

- E2E-042 verifica un rango de 32 días inclusivos rechazado con un mensaje genérico de datos inválidos, el límite de 31 días explicado en la ayuda y la ausencia de turnos. La UI actual no muestra el detalle de validación de campo dentro del error.
- La búsqueda en Mailpit tiene dos intentos acotados y actualiza el filtro. Solo las precondiciones de negocio pueden solicitar una vez el reenvío desde la UI; la lectura y verificación del correo siguen siendo obligatorias. Los casos de correo original del registro conservan la modalidad estricta.

- La preparación sigue siendo exclusivamente UI: formularios de registro, correo leído en Mailpit web, ingreso, creación de servicios, agenda y reservas. No se incorporaron clientes REST.
- El rechazo por recurso ocupado se comprueba como ausencia del turno en el segundo servicio, conservando otros horarios libres. La disponibilidad actual ya filtra recursos ocupados. TC-027/034 son referencias adaptadas; no se fuerza una petición desde una pantalla desactualizada para obtener un código de error.
- TC-028 se adapta a no ofrecer turnos fuera de agenda; no se fabrica una reserva en un horario inexistente.
- La asignación de recursos se comprueba con el aviso visible y con los casos de conflicto y liberación. La pantalla no vuelve a cargar las casillas asignadas al cambiar de servicio; no se atribuye cobertura a esa visualización.
- E2E-052 / CA-UI-53 comprueba un correo de cancelación con servicio e identificador de reserva. Está marcado `@pendiente_producto`: `NotificacionPort` y `SmtpNotificacionAdapter` del producto únicamente exponen/envían verificación de cuenta (y el puerto contempla MFA), sin notificación de cancelación. No se modificó el producto.
- Los 15 minutos de bloqueo se comprueban en el aviso y rechazando la clave correcta inmediatamente después de cinco fallos; no se espera quince minutos ni se cambia el reloj.
- No se simula la caducidad de 24 horas del correo, la desactivación administrativa de cuentas, JWT, rendimiento (TC-013), acceso forzado a servicios ajenos ni concurrencia. No hay controles de UI para preparar algunas de esas condiciones dentro de este alcance.
- No se declara cobertura completa de todos los criterios de las HU ni aprobación por ejecución. Las HU-10, HU-11, HU-12, HU-13 y HU-14 siguen sin escenarios dedicados; los historiales usados aquí verifican únicamente las cancelaciones de HU-08/15.
- Las cuentas, mensajes y recursos de prueba quedan en el entorno local: no hay UI para eliminarlos. La limpieza cancela las reservas propias que se hayan registrado y deja inactivos todos los servicios creados por el escenario.

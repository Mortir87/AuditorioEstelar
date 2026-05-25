# Proyecto Intermodular
Proyecto final Intermodular de 2º DAM

## Setup Local con Docker

### Requisitos
- Docker y Docker Compose instalados

### Arrancar
```bash
docker-compose up -d --build
```

### Servicios
| Servicio | URL | Credenciales |
|----------|-----|--------------|
| API PHP | http://localhost:8080 | - |
| MySQL | localhost:3306 | root / root |
| Base de datos | teatro_proyecto | - |

### Detener
```bash
docker-compose down
```

### Reiniciar con datos limpios
```bash
docker-compose down -v && docker-compose up -d
```

### Probar la API
Importar `AuditorioEstelar-API.postman_collection.json` en Postman y usar la variable `baseUrl` según el entorno.

---

Integrantes:
- Jose Andres
- Aitor
- ??

**Lenguaje:**
- Java en Android Studio

**BBDD:**
- A decidir entre MySQL y Posiblemente BBDD de Objetos(investigar) posiblemente PostgreSQL o MongoDB
- Servidor Localhost. cada uno su base de datos identica en estructura

## Ideas Principales
- Ejemplo de aplicacion Auditorio Nacional de Madrid (programacion)
- Auditorio de conciertos, venta de entradas, trabajando un carrito de compra
- Controlando aforo y reservas.
- Correo y password, sin doble factor ni encriptacion en BBDD.
- Cada concierto debe tener cartel publicitario (img objeto=?) fecha y hora
- (OBLIGATORIO) un concierto contara con 2 fechas.
- Boton de compra de entradas, (Opcional) que sea flotante.

### Venta de entradas tendra:
** Plano donde e identifica cada butaca con 2 zonas **
- Ejemplo:
  [13 14 15 16]
  [09 10 11 12]  anfiteatro
  —---------
  [5 6 7 8]  patio de butaca
  [1 2 3 4]
 ----  Escenario
  Hay que realizar un diseño de patio de butacas.

** Estados de asiento **
- Disponible
- Vendido
- Seleccionado
- No disponible

### A tener en cuenta:
- Precios de zonas
- Venta en carrito, poder comprar más de una butaca y totalizar la venta, guardar BBDD
- Control de aforo al realizar la venta, y guardar en BBDD
- Tickets en PDF
- Modo administrador (reiniciar teatro)
- Historial de ventas

## Pantallas

## Flujo de pantallas



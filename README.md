![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![PHP](https://img.shields.io/badge/PHP-777BB4?style=for-the-badge&logo=php&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)

## Auditorio Estelar

Aplicación móvil Android desarrollada como proyecto final del ciclo formativo de Desarrollo de Aplicaciones Multiplataforma (DAM).
Es una plataforma de gestión y venta de entradas para conciertos y eventos culturales, inspirada en el funcionamiento de plataformas reales como el Teatro Real de Madrid.
La aplicación implementa una arquitectura cliente-servidor completa, permitiendo a los usuarios explorar conciertos, consultar sesiones, seleccionar butacas en tiempo real y gestionar reservas desde dispositivos Android.

## Interfaz de la Aplicación (UI/UX)

<details>
<summary>📱 Haz clic aquí para desplegar las capturas de pantalla de la App</summary>
<br>

A continuación se muestran capturas reales del flujo de compra y reserva de butacas desde el dispositivo Android:

|                            Login y Registro                            |                     Cartelera Dinámica                      |                         Reserva de Butacas                          |                       ️ Ticket Digital & QR                        |
|:----------------------------------------------------------------------:|:-----------------------------------------------------------:|:-------------------------------------------------------------------:|:------------------------------------------------------------------:|
| <img src="/imgPreview/loginPrev.png" width="200" alt="Login"> | <img src="/imgPreview/progPrev.png" width="200" alt="Home"> | <img src="/imgPreview/butacasPrev.png" width="200" alt="Butacas"> | <img src="/imgPreview/entradaPrev.png" width="200" alt="Ticket"> |

*Nota: Las capturas muestran el comportamiento real de los componentes Material Design y el renderizado interactivo de la matriz de asientos de las sesiones.*

</details>
## Características principales
- Registro, login y perfil
- Cartelera dinámica de conciertos
- Calendario interactivo de eventos
- Selección visual de butacas por zonas
- Sistema de reservas conectado a base de datos
- Generación automática de tickets PDF
- Integración de códigos QR
- API REST desarrollada en PHP
- Backend desplegado mediante Docker
- Exposición segura mediante Cloudflare Tunnel
- Distribución mediante APK Android


## Arquitectura

El proyecto sigue una arquitectura cliente-servidor dividida en tres capas:

### 🔄 Flujo de Comunicación e Infraestructura

```text
[ Dispositivo Android ] 
       │  (HTTP / JSON mediante Retrofit)
       ▼
[ Internet (Dominio Público) ]
       │
       ▼
[ Cloudflare Tunnel (Proxy Seguro) ]
       │
       ▼
[ Raspberry Pi 4 (Host Local) ] ──► [ Docker Compose ]
                                          │
                                          ├──► Container 1: Apache + PHP 8.2 (API)
                                          └──► Container 2: MariaDB (Database)
```

### Cliente Android
- Java 11
- Android Studio
- Fragments + navegación centralizada
- Material Design
- Retrofit para comunicación HTTP

### Backend REST
- PHP 8 + Apache
- API RESTful
- JSON como formato de intercambio

### Persistencia
- MariaDB / MySQL
- Modelo relacional normalizado
- Integridad referencial mediante claves foráneas

## Stack tecnológico
| Categoría | Tecnologías |
|---|---|
| Frontend | Java, XML, Material Design |
| Backend | PHP, Apache |
| Base de datos | MariaDB / MySQL |
| Comunicación | Retrofit, Gson |
| Infraestructura | Docker, Docker Compose |
| Servidor | Raspberry Pi 4 |
| Seguridad | Cloudflare Tunnel |
| Diseño UI/UX | Figma |
| Testing | Postman |
| Control de versiones | Git + GitHub |

## Instalación y despliegue

### Clonar repositorio
```bash
git clone https://github.com/Mortir87/AuditorioEstelar.git
cd AuditorioEstelar
```

### Base de datos
El script SQL completo se encuentra en:
```text
    /database/auditorio_estelar.sql
```

Este archivo incluye:
- Creación de tablas
- Relaciones y claves foráneas
- Datos de prueba (menos usuarios y reservas)
- Conciertos demo
- Sesiones futuras automáticas
- Generación de butacas por sesión

---

### Docker Compose
El backend se ejecuta mediante Docker Compose.

Servicios incluidos:
- Apache + PHP 8.2
- MariaDB

Ejecutar:
```bash
docker compose up -d
```

La API REST quedará disponible en:
```text
http://localhost:8080
```

---

### Cloudflare Tunnel (Opcional)

Para exponer temporalmente el backend a internet:

```bash
nohup cloudflared tunnel --url http://localhost:8080 > tunel.log 2>&1 &
```

Obtener URL pública:

```bash
cat tunel.log
```

Detener túnel:

```bash
sudo pkill cloudflared
```

---

### Configuración Android

Modificar la URL base de Retrofit en el cliente Android para utilizar:
- localhost
- o la URL pública de Cloudflare Tunnel

---

### Ejecutar aplicación

Abrir el proyecto con Android Studio y ejecutar en:
- Emulador Android
- Dispositivo físico

## Funcionalidades implementadas

### Gestión de usuarios
- Registro y autenticación
- Persistencia de sesión
- Historial de reservas

### Gestión de conciertos
- Cartelera dinámica
- Visualización detallada de eventos
- Sistema de sesiones

### Sistema de reservas
- Selección interactiva de butacas
- Gestión de estados en tiempo real
- Confirmación de compra

### Tickets digitales
- Generación de PDF
- Código QR integrado
- Descarga y visualización local



## Seguridad & Clean Code

* **Arquitectura Limpia:** Separación estricta de responsabilidades (Separation of Concerns). El cliente Android delega toda la lógica de negocio al backend en PHP a través de interfaces de Retrofit limpias.
* **Prevención de Inyecciones (SQLi):** Uso categórico de Sentencias Preparadas (Prepared Statements) con PDO en todas las interacciones con la base de datos.
* **Cifrado de Alta Seguridad:** Gestión de credenciales de usuarios utilizando algoritmos de hashing seguros nativos de PHP (`password_hash` con BCRYPT).
* **Aislamiento de Entorno:** Uso de Docker para asegurar que las variables de entorno de la base de datos no queden expuestas en el código fuente.


## Control de Calidad & Testing (QA)

Para asegurar la robustez del software y evitar regresiones, el proyecto pasó por un ciclo de pruebas funcionales e integradas:

* **API Testing (Postman):** Se diseñó una colección de pruebas en Postman para validar los endpoints de la API RESTful. Se verificaron códigos de estado HTTP (200, 400, 404, 500), estructuras de respuestas JSON mediante esquemas, y flujos de autenticación.
* **Pruebas de Integridad de Datos:** Validación de restricciones de claves foráneas y triggers en MariaDB al cancelar o duplicar reservas de butacas en tiempo real.
* **UI & Carga Local:** Validación en emuladores con diferentes densidades de píxeles para asegurar la adaptabilidad de la cuadrícula interactiva de las butacas con Material Design.



## Futuras mejoras

- Pasarela de pago real
- Panel administrador
- Notificaciones push
- Internacionalización
- Recomendaciones mediante IA
- Realidad virtual y recorridos inmersivos

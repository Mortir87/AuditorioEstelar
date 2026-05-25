![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![PHP](https://img.shields.io/badge/PHP-777BB4?style=for-the-badge&logo=php&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)

## Auditorio Estelar

Aplicación móvil Android desarrollada como proyecto final del ciclo formativo de Desarrollo de Aplicaciones Multiplataforma (DAM).
Es una plataforma de gestión y venta de entradas para conciertos y eventos culturales, inspirada en el funcionamiento de plataformas reales como el Teatro Real de Madrid.
La aplicación implementa una arquitectura cliente-servidor completa, permitiendo a los usuarios explorar conciertos, consultar sesiones, seleccionar butacas en tiempo real y gestionar reservas desde dispositivos Android.


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



## Seguridad

- Contraseñas cifradas mediante `password_hash()`
- Validación cliente-servidor
- Prepared Statements contra SQL Injection
- Gestión segura de sesiones
- Exposición segura del backend con Cloudflare tunnels



## Testing

Se realizaron pruebas funcionales incrementales para validar:

- Autenticación
- Navegación
- Comunicación API REST
- Gestión de reservas
- Generación de PDF y QR
- Integridad de datos



## Futuras mejoras

- Pasarela de pago real
- Panel administrador
- Notificaciones push
- Internacionalización
- Recomendaciones mediante IA
- Realidad virtual y recorridos inmersivos

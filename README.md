# Proyect-002-SQA-System-Qhatu-
Sistema web empresarial para la gestión inteligente de inventario, ventas y abastecimiento en minimarkets. Desarrollado en Java Spring Boot + SQL Server con arquitectura empresarial. Proyecto del curso Arquitectura Empresarial - Universidad Autónoma del Perú.

# 🏪 QHATU SmartStock

> Plataforma web para la gestión inteligente de inventario, ventas y abastecimiento en minimarkets.

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![SQL Server](https://img.shields.io/badge/SQL%20Server-2022-blue?logo=microsoftsqlserver)](https://www.microsoft.com/sql-server)
[![Docker](https://img.shields.io/badge/Docker-ready-blue?logo=docker)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📖 Descripción

**QHATU SmartStock** es una plataforma web empresarial desarrollada en **Java Spring Boot** y **SQL Server** que digitaliza la gestión de inventario, ventas y abastecimiento en minimarkets de barrio del Perú. El proyecto surge de la necesidad de aplicar los principios de **Arquitectura Empresarial** para transformar digitalmente un negocio real (QHATU), eliminando los registros manuales y las pérdidas por mermas o quiebres de stock.

Incluye módulos de productos, punto de venta (POS) con descuento automático de inventario, alertas de stock crítico, comprobantes de pago imprimibles y un dashboard gerencial con indicadores en tiempo real.

**Proyecto desarrollado para el curso de Arquitectura Empresarial — Universidad Autónoma del Perú.**

---

## 🎯 Características principales

| Módulo | Descripción |
|---|---|
| 🔐 **Autenticación** | Login con Spring Security y roles (ADMIN / CAJERO) |
| 📊 **Dashboard** | KPIs en tiempo real: productos, ventas del día, ingresos y stock crítico |
| 📦 **Gestión de productos** | CRUD completo con badges de estado (OK / Bajo / Sin stock) |
| 🛒 **Punto de venta (POS)** | Registro de ventas con cálculo del total en vivo y descuento automático de inventario |
| 🚨 **Alertas de stock** | Identificación automática de productos con stock ≤ stock mínimo |
| 🧾 **Comprobantes** | Tickets imprimibles con el detalle de cada venta |
| 📅 **Historial de ventas** | Consulta de ventas del día con acceso directo al comprobante |

---

## 🛠️ Stack tecnológico

### Backend
- **Java 17**
- **Spring Boot 3.2.5** (Web, Data JPA, Security)
- **Hibernate 6.4**
- **Lombok**
- **Maven**

### Base de datos
- **SQL Server 2022** (ejecutado en Docker)

### Frontend
- **Thymeleaf** (motor de plantillas)
- **HTML5 / CSS3 / JavaScript vanilla**
- Diseño responsive con sidebar, cards y badges

### DevOps
- **Docker** para SQL Server
- **Git / GitHub** para control de versiones

---

## 📐 Arquitectura

El proyecto sigue una arquitectura **en capas** alineada con el marco de Arquitectura Empresarial:

```
┌──────────────────────────────────────────┐
│   CAPA DE PRESENTACIÓN (Thymeleaf)       │
│   HTML + CSS + JS                        │
├──────────────────────────────────────────┤
│   CAPA DE NEGOCIO (Controllers/Services) │
│   Spring Boot                            │
├──────────────────────────────────────────┤
│   CAPA DE PERSISTENCIA (Repositories)    │
│   Spring Data JPA / Hibernate            │
├──────────────────────────────────────────┤
│   CAPA DE DATOS (SQL Server 2022)        │
│   Entidades: Usuario, Producto,          │
│   Venta, DetalleVenta                    │
└──────────────────────────────────────────┘
```

Modelo de datos:
- `Usuario` (1) ─── (N) `Venta`
- `Venta` (1) ─── (N) `DetalleVenta`
- `Producto` (1) ─── (N) `DetalleVenta`

---

## 🚀 Cómo ejecutar el proyecto

### Requisitos previos
- **Java 17** ([descargar](https://adoptium.net/))
- **Maven 3.9+** ([descargar](https://maven.apache.org/download.cgi))
- **Docker Desktop** ([descargar](https://www.docker.com/products/docker-desktop/))

### Paso 1 — Clonar el repositorio

```bash
git clone https://github.com/TU_USUARIO/qhatu-smartstock.git
cd qhatu-smartstock
```

### Paso 2 — Levantar SQL Server con Docker

```bash
docker run -e "ACCEPT_EULA=Y" \
  -e "MSSQL_SA_PASSWORD=Qhatu#2024!Seguro" \
  -e "MSSQL_PID=Developer" \
  -p 1433:1433 \
  --name qhatu-sqlserver \
  -d mcr.microsoft.com/mssql/server:2022-latest
```

Espera ~30 segundos y crea la base de datos:

```bash
docker exec -it qhatu-sqlserver \
  /opt/mssql-tools18/bin/sqlcmd \
  -S localhost -U sa -P "Qhatu#2024!Seguro" -C \
  -Q "CREATE DATABASE QhatuSmartStock;"
```

### Paso 3 — Configurar credenciales

Verifica que `src/main/resources/application.properties` tenga:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=QhatuSmartStock;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=Qhatu#2024!Seguro
```

### Paso 4 — Ejecutar la aplicación

```bash
mvn spring-boot:run
```

Abre el navegador en **http://localhost:8080/login**

### Credenciales de demo

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `admin123` | ADMIN |
| `cajero` | `cajero123` | CAJERO |

> Si es la primera vez, inserta los datos iniciales con:
> ```bash
> docker exec -it qhatu-sqlserver /opt/mssql-tools18/bin/sqlcmd \
>   -S localhost -U sa -P "Qhatu#2024!Seguro" -C -d QhatuSmartStock \
>   -Q "INSERT INTO usuario (username, password, rol) VALUES ('admin', 'admin123', 'ADMIN');"
> ```

---

## 📸 Capturas de pantalla

### Pantalla de login

<img width="920" height="470" alt="image" src="https://github.com/user-attachments/assets/0dd119b5-3ffa-42e5-8030-5cf850f7be43" />

### Dashboard gerencial
<img width="902" height="469" alt="image" src="https://github.com/user-attachments/assets/dcdde760-dd55-4f70-a93b-8d65e5a25336" />

### Punto de venta (POS)
<img width="905" height="473" alt="image" src="https://github.com/user-attachments/assets/002754d7-bda1-4b22-a449-9ac2e2038b54" />

### Comprobante de pago
<img width="896" height="475" alt="image" src="https://github.com/user-attachments/assets/ade98565-95d0-4b50-88ac-b2e03d750e07" />

---

## 📂 Estructura del proyecto

```
qhatu-smartstock/
├── src/
│   ├── main/
│   │   ├── java/com/qhatu/smartstock/
│   │   │   ├── config/          # Configuración de Spring Security
│   │   │   ├── controller/      # Controladores MVC
│   │   │   ├── entity/          # Entidades JPA
│   │   │   ├── repository/      # Repositorios Spring Data
│   │   │   ├── service/         # Lógica de negocio
│   │   │   └── SmartStockApplication.java
│   │   └── resources/
│   │       ├── static/css/      # Estilos
│   │       ├── templates/       # Vistas Thymeleaf
│   │       └── application.properties
│   └── test/
├── docs/
│   ├── bpmn/                    # Diagramas de procesos
│   └── screenshots/             # Capturas
├── pom.xml
└── README.md
```

---

## 🧪 Casos de uso principales

1. **Registrar una venta:** el cajero selecciona productos, ingresa cantidades, el sistema calcula el total en vivo, descuenta stock automáticamente y genera un comprobante imprimible.
2. **Control de stock crítico:** el dashboard alerta cuando un producto llega al stock mínimo para evitar quiebres.
3. **Administrar el catálogo:** el administrador puede crear, editar y eliminar productos con validación de integridad referencial.
4. **Consultar historial:** acceso a todas las ventas del día con opción de ver el comprobante.

---

<p align="center">
  <strong>QHATU SmartStock</strong> — Gestión inteligente para minimarkets 🏪
</p>

# Sistema de Gestión Bancaria
Aplicación de consola bancaria construida con **Java 21 puro + JDBC + MySQL**, sin frameworks, aplicando los patrones de diseño **Singleton**, **DAO** y **MVC**.

---

## Descripción

Sistema bancario por consola que permite registrar usuarios, gestionar cuentas bancarias y realizar operaciones financieras con transacciones JDBC atómicas.

**Funcionalidades:**

- Registro e inicio de sesión con contraseñas hasheadas con BCrypt
- CRUD de cuentas bancarias: ahorro, corriente y nómina
- Depósito, retiro y transferencia entre cuentas
- Historial de transacciones por cuenta
- Validaciones de negocio: saldo suficiente, montos mínimos de apertura, cuentas activas
- Soft delete en lugar de eliminación real en base de datos

---

## Tecnologías

| Tecnología | Versión | Uso |
|---|---------|---|
| Java | 21      | Lenguaje principal |
| MySQL | 8+      | Base de datos |
| Maven | 3.8+    | Gestión de dependencias |
| mysql-connector-j | 9.6.0   | Driver JDBC |
| jbcrypt | 0.4     | Hash de contraseñas |
| dotenv-java | 3.2.0   | Variables de entorno |

---

## Arquitectura

El proyecto implementa **MVC** estricto con tres capas bien separadas:

```
View  ──→  Controller  ──→  Service  ──→  DAO  ──→  MySQL
  ↑             │               │
  └─────────────┘               └── lógica de negocio
  solo imprime       no imprime      no sabe de consola
  y lee              ni valida       ni de JDBC directo
```

---

## Patrones de diseño

### Singleton — `DatabaseConnection`

Garantiza que exista **una sola instancia** de la configuración de conexión en toda la aplicación. Todos los DAO obtienen sus conexiones a través de este punto único.

```java
ConexionDB.getInstance().getConnection();
```

**Por qué no guarda la conexión sino la configuración:** las conexiones JDBC se vuelven inválidas si están inactivas, y las transferencias necesitan su propia conexión con `commit`/`rollback` independiente.

---

### DAO — Data Access Object

Separa el acceso a datos del resto de la lógica. Cada entidad tiene:

- Una **interfaz** que define el contrato (`UsuarioDAO`, `CuentaDAO`, `TransaccionDAO`)
- Una **implementación JDBC** que contiene todo el SQL (`UsuarioDAOImpl`, etc.)

El `Service` solo conoce la interfaz, nunca la implementación. Esto permite cambiar MySQL por PostgreSQL sin tocar nada fuera de `dao/impl/`.

---

### MVC — Model View Controller

| Capa | Responsabilidad | Lo que NO hace |
|---|---|---|
| **Model** | POJOs puros + enums del dominio | No conoce BD ni consola |
| **View** | Imprime menús, lee input del usuario | No llama a Services |
| **Controller** | Orquesta View ↔ Service | No imprime, no valida |
| **Service** | Lógica de negocio y validaciones | No sabe que existe una consola |

---

## Reglas de arquitectura

Estas reglas se respetan en todo el código y pueden verificarse buscando en el proyecto:

| Regla | Verificación |
|---|---|
| La View nunca llama a un Service | No existe `import com.banco.service` en ningún archivo de `view/` |
| El Controller nunca imprime | No existe `System.out.println` en ningún archivo de `controller/` |
| El Service no conoce la consola | No existe `import com.banco.view` en ningún archivo de `service/` |
| El SQL solo vive en `dao/impl/` | No existe ninguna `String` con `SELECT`, `INSERT`, `UPDATE` fuera de `dao/impl/` |
| Nunca `double` para dinero | Todos los montos son `BigDecimal` |
| Siempre `try-with-resources` en JDBC | Toda apertura de `Connection` usa `try (Connection con = ...)` |
| Soft delete siempre | No existe `DELETE FROM` en ningún DAOImpl |

--- 

# REDES SOCIALES

- **LinkedIn**:  https://www.linkedin.com/in/aldordzvn/ 
- **Github**:  https://github.com/Aldordzvn
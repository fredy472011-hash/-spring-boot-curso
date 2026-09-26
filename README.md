# Spring Boot — proyectos de aprendizaje

Cinco proyectos construidos siguiendo el curso *Spring Boot & Spring Framework de cero a experto* de Andrés Guzmán, más un frontend propio.

**Stack:** Java 21 · Spring Boot 3.5 · Spring Data JPA · Hibernate · MySQL 8 · Maven

---

## Proyectos

| Proyecto | Qué contiene |
|---|---|
| **springboot-crud** | API REST completa con JPA y MySQL, más un frontend en HTML/JS |
| **springboot-di** | Inyección de dependencias con varias implementaciones de un mismo repositorio |
| **springboot-difactura** | DI aplicada a un modelo de facturación (cliente, factura, ítems) |
| **springboot-error** | Manejo centralizado de excepciones con `@ExceptionHandler` |
| **springboot-jpa** | Entidades, repositorios y consultas con Spring Data JPA |

---

## springboot-crud

El más completo. API REST de productos con arquitectura en capas:

```
entities/Product.java              entidad JPA mapeada a la tabla products
repositories/ProductRepository     interfaz de Spring Data
services/ProductService            interfaz del servicio
services/ProductServiceImpl        implementación
controllers/ProductController      endpoints REST
```

### Endpoints

| Método | Ruta | Respuesta |
|---|---|---|
| `GET` | `/productos/get` | Lista todos los productos |
| `GET` | `/productos/get/{id}` | Un producto, o `404` si no existe |
| `POST` | `/productos/post` | Crea uno y devuelve `201 Created` |
| `PUT` | `/productos/put/{id}` | Modifica uno, o `404` |
| `DELETE` | `/productos/delete/{id}` | Elimina uno, o `404` |

Ejemplo de cuerpo para `POST` y `PUT`:

```json
{
  "name": "Teclado mecanico",
  "price": 350000,
  "description": "Para uso diario"
}
```

El `id` no se envía: lo genera la base de datos.

### Frontend

En `src/main/resources/static/` hay un formulario que consume la API con `fetch`.
Spring lo sirve automáticamente en la raíz: `http://localhost:8090`

---

## Cómo levantarlo

**Requisitos:** JDK 21 y MySQL 8 corriendo en `localhost:3306`.

```bash
# 1. Crear la base
mysql -u root -p -e "CREATE DATABASE db_jpa_crud;"

# 2. Levantar la aplicación
cd springboot-crud
./mvnw spring-boot:run
```

Queda disponible en **http://localhost:8090**

Hibernate crea la tabla `products` en el primer arranque.

---

## Configuración

Las credenciales **no están escritas en el código**. Se leen de variables de
entorno, con un valor por defecto para desarrollo local:

```properties
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASSWORD:root}
```

Para usar otras credenciales, basta definirlas antes de arrancar:

```bash
# Windows (cmd)
set DB_USER=mi_usuario
set DB_PASSWORD=mi_clave

# Linux / macOS
export DB_USER=mi_usuario
export DB_PASSWORD=mi_clave
```

Así el artefacto desplegable no contiene ningún secreto, y cada ambiente
aporta el suyo desde afuera.

---

## Notas

- Cada proyecto es independiente, con su propio `pom.xml` y su Maven wrapper (`mvnw`).
- `springboot-crud` corre en el puerto **8090**; los demás usan el 8080 por defecto.
- El SQL generado por Hibernate se imprime en consola (`spring.jpa.show-sql=true`),
  útil para ver qué consulta produce cada operación.

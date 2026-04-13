# Arquitectura del Back-End (Spring Boot)

Version Bassline:
![Bassline](./readMeResources/Bassline.png)
["Pump up the Bassline" -Shirako](https://open.spotify.com/intl-es/track/0vnzk4L0JyMODSqLp3pHwo?si=b5ee35a74cd14969)
Este proyecto sigue la estructura estándar de capas de Spring Boot para mantener el código organizado y escalable:

```
src/
 └── main/
     └── java/com/uade/tpo/e_commerce3/
         ├── controller/
         ├── dto/
         ├── exception/
         ├── manager/
         ├── model/
         ├── repository/
         └── service/
```

* **Model / Entity:** Representa la estructura de los datos y su mapeo con las tablas de la base de datos. Es la base de los objetos con los que trabajamos.
* **Repository:** La capa de acceso a datos. Se encarga de la comunicación directa con la base de datos (operaciones CRUD).
* **Service:** Aquí reside la lógica de negocio. Es el "cerebro" que procesa la información antes de guardarla o enviarla al cliente.
* **Controller:** El punto de entrada de la aplicación. Se encarga de administrar los endpoints (REST API), recibir las peticiones HTTP y devolver las respuestas.

---

## Herramientas de Prueba

El proyecto fue testeado utilizando la extensión REST Client de Huachao Mao (VS Code). 
Puedes encontrar el archivo `test.http` en la carpeta raíz para replicar las pruebas de forma rápida.

## Cómo ejecutar el proyecto

1. Asegúrate de tener instalado Java 17 y Maven.
2. Entra en la carpeta e-commerce3 y ejecuta el siguiente comando en la terminal:
    <p>Para Linux / macOS</p>
    <pre>  ./mvnw spring-boot:run</pre>
    <p>Para Windows (PowerShell o CMD)</p>
    <pre>  .\mvnw.cmd spring-boot:run windows</pre>

3. Alternativamente, puedes usar la extensión de Spring Boot en VS Code haciendo clic en el icono generado en la barra lateral.

---

## Tablas de comunicacion con BD
### Endpoints de la API (Productos)

| Método | Endpoint | Descripción | Cuerpo / Parámetros | Estado Exitoso |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/api/productos` | Obtiene la lista de todos los productos. | Ninguno | `200 OK` |
| `POST` | `/api/productos` | Crea un nuevo producto. | `JSON (Producto)` | `201 Created` |
| `PUT` | `/api/productos/{id}` | Actualiza un producto existente. | `ID` + `JSON (Datos)` | `200 OK` |
| `DELETE` | `/api/productos/{id}` | Elimina un producto por su ID. | `ID` | `204 No Content` |


### Endpoints de la API (Usuarios & Auth)

| Método | Endpoint | Descripción | Cuerpo (JSON) | Respuestas HTTP |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/api/usuarios` | Lista todos los usuarios. | Ninguno | `200 OK` |
| `POST` | `/api/usuarios` | Registra un nuevo usuario. | `Usuario` | `201 Created`, `409 Conflict` |
| `PUT` | `/api/usuarios/{id}` | Actualiza datos de un usuario. | `Usuario` | `200 OK`, `404 Not Found` |
| `DELETE` | `/api/usuarios/{id}`| Elimina un usuario por ID. | Ninguno | `204 No Content` |
| `POST` | `/api/usuarios/login`| Autenticación de usuario. | `LoginRequest` | `200 OK`, `401 Unauthorized` |

### Endpoints de la API (Carrito de Compras)

| Método | Endpoint | Descripción | Parámetros de Ruta | Estado Exitoso |
| :--- | :--- | :--- | :--- | :--- |
| `GET` | `/api/carritos` | Lista todos los carritos del sistema. | Ninguno | `200 OK` |
| `GET` | `/api/carritos/{id}` | Obtiene el detalle de un carrito específico. | `id` (Carrito) | `200 OK` |
| `POST` | `/api/carritos` | Inicializa un nuevo carrito. | Ninguno | `201 Created` |
| `POST` | `/api/carritos/{cId}/productos/{pId}` | Agrega un producto al carrito. | `cId`, `pId` | `200 OK` |
| `POST` | `/api/carritos/{id}/checkout` | Realiza el checkout, descuenta stock y vacía el carrito. | `id` (Carrito) | `200 Ok` |
| `DELETE` | `/api/carritos/{cId}/productos/{pId}` | Quita un producto del carrito. | `cId`, `pId` | `200 OK` |
| `DELETE` | `/api/carritos/{id}` | Elimina el carrito por completo. | `id` (Carrito) | `204 No Content` |

### Endpoints de la API (Perfil)

| Método | Endpoint | Descripción | Parámetros de Ruta | Estado Exitoso |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/perfiles/usuario/{usuarioId}` | Crea un nuevo perfil asociado a un usuario. | `usuarioId` | `200 OK` |
| `GET` | `/api/perfiles/{id}` | Obtiene el detalle (DTO) de un perfil por su ID. | `id` (Perfil) | `200 OK` |
| `GET` | `/api/perfiles` | Lista todos los perfiles registrados. | Ninguno | `200 OK` |
| `PUT` | `/api/perfiles/{id}` | Actualiza la información de un perfil existente. | `id` (Perfil) | `200 OK` |
| `DELETE` | `/api/perfiles/{id}` | Elimina un perfil del sistema. | `id` (Perfil) | `204 No Content` |
| `GET` | `/api/perfiles/usuario/{id}` | Obtiene el perfil asociado a un ID de usuario específico. | `id` (Usuario) | `200 OK` |

## Grafico visual para el back-end:

![Ecosistema General](./readMeResources/ecosistema%20general.png)
![Producto Ecosistema](./readMeResources/producto%20ecosistema.png)
![Usuario Ecosistema](./readMeResources/usuario%20ecosistema.png)

## Créditos y Agradecimientos

- **[Spring Boot](https://spring.io/)**: Por proporcionar el framework robusto y ágil sobre el que se construye esta API.
- **[Spring Initializr](https://start.spring.io/)**: Por la estructura base del proyecto.
- **[Maven](https://maven.apache.org/)**: Por la gestión de dependencias y la automatización de la construcción del proyecto.
- **Huachao Mao**: Por la excelente extensión **[REST Client](https://github.com/Huachao/vscode-restclient)** de VS Code que facilitó las pruebas de los endpoints.
- **Comunidad de Spring en Español**: Por la documentación y tutoriales que guiaron el desarrollo.
- **[MySQL Workbench](https://www.mysql.com/products/workbench/)**:Utilizado para el modelado de datos, diseño de diagramas E-R y la ejecución de scripts SQL de manera profesional.
- **[XAMPP](https://www.apachefriends.org/es/download.html)**: Por proveer el entorno local de MySQL que garantizó la estabilidad de la base de datos y la persistencia de los datos durante el desarrollo.


# Informacion de contacto

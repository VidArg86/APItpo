# Arquitectura del Back-End (Spring Boot)

Este proyecto sigue la estructura estándar de capas de Spring Boot para mantener el código organizado y escalable:

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

    ./mvnw spring-boot:run linux
    .\mvnw.cmd spring-boot:run windows

3. Alternativamente, puedes usar la extensión de Spring Boot en VS Code haciendo clic en el icono generado en la barra lateral.


## Créditos

Agradecimientos a Huachao Mao por su extensión para el testeo de los endpoints.
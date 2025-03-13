# TICKET SERVICE API

Este proyecto es una API para la gestión de tickets sencillos, desarrollada
con SPRING BOOT Y REACTOR para manejar programación reactiva

## Requisitos previos

Antes de ejecutar el proyecto, debes tener instalado:
* JAVA 17
* MAVEN
* MYSQL (para la base de datos)

### Flujo

1. primero es necesario clonar el repositorio de github
2. ejecuta el script SQL que está adjunto via correo
3. La base de datos será creada cuando se ejecute el Script SQL, puedes usar
    cualquier gestor de base de datos (phpmyadmin, laragon, dbeaver, etc.).
4. Puedes ejecutar la API en tu IDE preferido (Neatbeans, Intellidea, VS code, etc.), 
    la API será levantada con exito.
5. Puedes usar postman para probar los endpoint (se adjunta documentacion POSTMAN).
6. La API fue creada coN base en arquitectura HEXAGONAL, REACTIVA (spring webflux) y GRAPHQL.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.3/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.3/maven-plugin/build-image.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/3.4.3/reference/web/reactive.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.4.3/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring for GraphQL](https://docs.spring.io/spring-boot/3.4.3/reference/web/spring-graphql.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.3/reference/using/devtools.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.3/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a GraphQL service](https://spring.io/guides/gs/graphql-server/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


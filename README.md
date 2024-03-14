# Cleverpy Backend Java Assessment

## ¿Cómo arrancarlo?

La aplicación está dockerizada, y, por tanto, el equipo donde se instala debe tener instalado docker. Los pasos a seguir
serían los siguientes:

1. Abrimos un terminal y navegamos al demo de la aplicación donde tenemos el contenido de nuestro proyecto.
2. Generamos el artefacto de nuestro aplicación a través del comando: <u>.\mvnw clean package</u>
3. Navegamos a la raíz del proyecto, donde tenemos nuestro fichero docker-compose.yml
4. Y mediante el comando <u>docker-compose up</u> creamos las imágenes correspondientes y levantamos nuestro proyecto

## ¿Cómo probarlo?

Una vez hayamos arrancado el proyecto, se levantará en el puerto 8080 del equipo.
Lo ideal es probarlo a través de:
- Postman. adjunto una colección donde se pueden consultar todos los servicios expuestos en el api.
  ([adrian_villaseco_backend_cleverpy.postman_collection.json](demo%2Fadrian_villaseco_backend_cleverpy.postman_collection.json)

- Swagger. http://localhost:8080/swagger-ui/index.html

Se ha implementado autorización y autenticación a través de JWT. En postman ya está automatizado el uso del token
en cada llamada. Sin embargo en Swagger habría que realizar una petición al servicio de /auth/login para poder consumir
el resto de servicios en función del rol. Actualmente existen 3: ADMIN, EMPLEADO y AMIGO.

* ADMIN: tiene acceso a todos los recursos
* EMPLEADO: tiene acceso a realizar peticiones sobre los recursos de peliculasseries y puntuaciones.
* AMIGO: solo tiene acceso al recurso de peliculasseries.

## Enunciado

Bienvenido a la prueba técnica para backend de Cleverpy Machine Learning.

Antes de comenzar es importante que tengas instalado docker y docker-compose en la máquina donde vas a realizar esta prueba ya que los necesitarás para levantar la base de datos que hay en el fichero docker-compose de este repositorio.
Puedes ver cómo instalar docker y docker-compose [aquí](https://docs.docker.com/compose/install/)

### En qué consiste la prueba
Tu objetivo es construir desde cero una API Rest la cual sea **funcional** y cumpla con los **requisitos mínimos** que se indican en el apartado de requisitos.

### Funcionalidad
En las jornadas de Team building de Cleverpy nos gusta proponer películas o series para ver y posteriormente comentar con los compañeros a la hora del café si nos ha gustado o no, el compañero que proponga la película o serie que más haya gustado tiene el café gratis durante una semana.

Dado que somos bastantes competitivos (y hay cafés en juego) no podemos fiarnos de la palabra de cada uno sino que necesitamos un sistema para persistir las películas que vamos proponiendo y la nota final que se le ha dado. El sistema es muy simple, tendrá que gestionar Películas y Series además de Empleados y un sistema de votos.

El API tiene que estar pensada para que una persona (de ahora en adelante el implantador) sea la encargada de registrar la película o serie propuesta y qué puntuación le ha pueesto cada uno de los empleados. 

Al registrar una película o serie tienen que figurar como mínimo los siguientes datos (puedes añadir todos los que tú quieras, tienes un ejemplo de los datos que puedes incluir en [esta](https://www.themoviedb.org/documentation/api) API pública) :
  - Año de la película o serie
  - Director
  - Género
  - Temporadas (en caso de ser serie)
  - Duración (en caso de ser película)

Además de esos datos, también debe quedar registrado en la Película o Serie:
  - Qué empleado la ha propuesto
  - La nota media que le han dado los empleados (suponiendo que se ha hablado previamente, solo hay que almacenar el resultado)
  - Qué empleado ha hecho de implantador (el empleado que ha registrado esa película en el sistema)
  - El día y la hora en la que se ha registrado la película en el sistema.

En cuanto a los empleados, solo necesitamos almacenar (aunque puedes añadir todos los campos extras que quieras):
  - nombre
  - email
  - si es frontend o backend
  - edad

A parte de registrar peliculas y demás también necesitaremos que el API sea capaz de mostrar la información almacenada para cuando tengamos dudas de a quién le sale el café gratis. Para ello, el API tendrá que incluir endpoints que permitan:
  - Mostrar todos los empleados almacenados.
  - Mostrar todas las películas almacenadas.
  - Mostrar todas las series almacenadas.
  - Mostrar todas las películas y series almacenadas.
  - Mostrar todas las Pelíuclas / series almacenadas filtradas por genero, puntuación y/o año.
  - Mostrar qúe empleado ha propuesto la mejor película (la puntuada más alto) y qué película es

Como somos humanos y nos podemos equivocar, el API también debe permitir que podamos actualizar, borrar y añadir registros de todo tipo (peliculas, empleados, etc).

**Importante**: La funcionalidad está abierta a interpretación. Esto significa que puedes darle tu toque personal añadiendo algún endpoint que creas importante o algún sistema de votos de los empleados algo más complejo. Lo importante es que tenga la esencia básica que se pide.
Mucha suerte! Si tienes alguna duda puedes dejar un comentario en el repositorio y lo responderemos por ahí.


### Requisitos

#### Obligatorios
- Uso de Java 8+
- Uso del framework Spring boot.
- Uso de la base de datos PostgreSQL que proporcionamos en el docker-compose.yml
- Uso de Hibernate / JPA
- Uso de la librería Lombok
- Implementación de tests unitarios con JUnit
- Hacer commits descriptivos y frecuentes

#### Extras
- Implementación de tests de integración
- Implementación de sistema de autenticación usando JWT
- Implementación de Swagger para documentar tu api
- Uso de la metodología GitFlow
- Uso de docker para crear un contenedor de la aplicación
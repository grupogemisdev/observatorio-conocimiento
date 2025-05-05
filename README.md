# Observatorio de Conocimiento API


## Comenzando 🚀

_Estas instrucciones te permitirán tener el proyecto en funcionamiento en tu máquina local
para propósitos de desarrollo y pruebas._

### Pre-requisitos 📋

_Qué cosas necesitás tener antes de arrancar_

* JDK 21
* Docker

### Instalación 🔧

``` shell
> docker compose up
```
_Esto va a levantar la BD local. Asegurate de que el puerto y las credenciales coincidan
con las del archivo [application-local.properties](src/main/resources/application-local.yml)_

Para luego eliminar el contenedor y borrar el volumen
``` shell
> docker compose down -v
```

Para levantar una BD con Docker pero independiente de este proyecto:
``` shell
> docker run --name somepostgres -e POSTGRES_PASSWORD=root -e POSTGRES_USER=observatorio_conocimiento -e POSTGRES_DB=observatorio_conocimiento -p 5432:5432 -d postgres:17-alpine
```

Si todo salió bien, iniciá la app con perfil local y las variables de entorno correspondientes
``` shell
> ./gradlew bootLocal
```
## Ejecutando las pruebas ⚙️

Cargar un archivo JSON de organizaciones como este: [organizaciones-test.json](src/test/resources/organizaciones-test.json)
``` shell
> curl -X POST "http://localhost:8080/upload/organizaciones-json" -H "Content-Type: multipart/form-data" -F "file=@src/test/resources/organizaciones-test.json"
```

Consultar los datos por API REST
``` shell
> curl -X GET "http://localhost:8080/api/organizaciones" -H "Accept: application/json"
```


## Despliegue 📦

Construir el contenedor de la aplicación
``` shell
> docker build -t observatorio-conocimiento-api .
```

## Hecho con 🛠️

* Spring Boot
* Spring Web (API REST)
* Spring Data JPA (persistencia en MySQL)
* Spring Batch (carga de datos desde archivo JSON)

# Taller3
 
Link al repositorio: https://github.com/siraglez/Taller3.git

Este proyecto es una aplicación Android que utiliza `Jetpack Compose` para su interfaz de usuario, implementando tanto almacenamiento interno como externo y navega entre actividades. A continuación, se explica el funcionamiento del código principal.

## Estructura del Proyecto

### 1. `MainActivity`

`MainActivity` es la actividad principal de la aplicación que maneja el almacenamiento en los dispositivos, tanto en almacenamiento interno como externo, y utiliza una pantalla interactiva creada con Compose.

#### Almacenamiento Externo

- Si el dispositivo tiene Android 10 (API 29) o superior, la aplicación escribe datos en el almacenamiento externo sin necesidad de permisos especiales.
- En versiones anteriores, se solicita el permiso `WRITE_EXTERNAL_STORAGE` para escribir en el almacenamiento externo.
- Los datos se guardan en el directorio de documentos públicos (`DIRECTORY_DOCUMENTS`) y se leen posteriormente para verificar que se hayan escrito correctamente.

#### Almacenamiento Interno

- Los datos también se almacenan en el almacenamiento interno del dispositivo utilizando el archivo `datos_internos.txt`.
- Estos datos se leen desde el almacenamiento interno y se registran en el log.

#### Pantalla Principal

La pantalla principal (`MainScreen`) muestra un saludo basado en la hora actual del día (mañana, tarde o noche) y un botón para navegar a una actividad secundaria (`ActividadPrincipal`).

### 2. `AlmacenamientoUtils`

Este objeto proporciona funciones utilitarias para el almacenamiento de datos en el dispositivo:

- **Funciones de almacenamiento interno:**
  - `guardarEnAlmacenamientoInterno`: Guarda un archivo de texto en el almacenamiento interno del dispositivo.
  - `leerDesdeAlmacenamientoInterno`: Lee el contenido de un archivo de texto almacenado internamente.
  
- **Funciones de almacenamiento externo:**
  - `guardarEnAlmacenamientoExterno`: Guarda un archivo de texto en el almacenamiento externo del dispositivo.
  - `leerDesdeAlmacenamientoExterno`: Lee el contenido de un archivo de texto desde el almacenamiento externo.

### 3. `ActividadPrincipal`

`ActividadPrincipal` es una actividad secundaria que permite a los usuarios ingresar y guardar un nombre en `SharedPreferences` o en una base de datos SQLite.

#### Funcionalidades:

- **Almacenamiento en SharedPreferences**:
  - Permite al usuario guardar su nombre en las preferencias compartidas y luego mostrarlo en pantalla.

- **Almacenamiento en SQLite**:
  - Utiliza `DatabaseHelper` para insertar y recuperar nombres desde una base de datos local SQLite.
  
- **Navegación**:
  - El usuario puede navegar a una pantalla de configuración mediante el botón "Ir a Configuración".

- **Tarea en Segundo Plano**:
  - Implementa una tarea en segundo plano que simula una operación de red, actualizando un progreso mientras se ejecuta.

### 4. Composables

#### `MainScreen`

Este composable define la UI de la pantalla principal que incluye:
- Un saludo basado en la hora actual.
- Un botón para navegar a la actividad `ActividadPrincipal`.

#### `ActividadPrincipalScreen`

La interfaz de la actividad secundaria incluye:
- Un campo de texto para ingresar el nombre.
- Botones para guardar el nombre en `SharedPreferences` y SQLite.
- Una lista de nombres guardados en la base de datos SQLite.
- Un botón para navegar a la configuración.
- Un botón para iniciar una tarea en segundo plano que muestra el progreso.

### 5. Dependencias

- `Jetpack Compose`: Para la creación de interfaces de usuario declarativas.
- `SQLite`: Para el almacenamiento persistente en una base de datos local.
- `SharedPreferences`: Para el almacenamiento simple de datos clave-valor.
- `Almacenamiento Externo`: Para guardar y leer archivos en el almacenamiento externo del dispositivo.

## Funcionalidades Clave

1. **Almacenamiento en Android**: La aplicación maneja datos tanto en almacenamiento interno como externo.
2. **Uso de `Jetpack Compose`**: La interfaz gráfica está construida con componentes de Compose.
3. **Navegación entre Actividades**: La aplicación implementa la navegación entre varias actividades de forma eficiente.
4. **Manejo de Permisos**: Solicita permisos en tiempo de ejecución para versiones anteriores a Android 10.


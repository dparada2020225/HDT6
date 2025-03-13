# Proyecto Pokémon Map

Este proyecto es una aplicación Java que gestiona información de Pokémon utilizando diferentes implementaciones del interfaz `Map` de Java Collections Framework para la materia CC2016 – Algoritmos y Estructura de Datos (Universidad del Valle de Guatemala).

![Pokémon Logo](https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/International_Pok%C3%A9mon_logo.svg/1200px-International_Pok%C3%A9mon_logo.svg.png)

## Características

- Carga de datos desde un archivo CSV de Pokémon
- Selección en tiempo de ejecución del tipo de Map a utilizar (HashMap, TreeMap, LinkedHashMap)
- Interfaz gráfica para facilitar la interacción con el usuario
- Operaciones para:
  - Agregar Pokémon a la colección personal
  - Mostrar datos detallados de un Pokémon
  - Listar la colección personal ordenada por tipo
  - Listar todos los Pokémon ordenados por tipo
  - Buscar Pokémon por habilidad

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── pokemonmap/
│   │       ├── data/         # Clases de datos
│   │       │   ├── Pokemon.java
│   │       │   ├── PokemonCollection.java
│   │       │   └── PokemonData.java
│   │       ├── factory/      # Patrón Factory para Map
│   │       │   ├── MapFactory.java
│   │       │   └── MapType.java
│   │       ├── gui/          # Interfaz gráfica
│   │       │   └── PokemonGUI.java
│   │       ├── util/         # Utilidades (lector CSV)
│   │       │   └── CSVReader.java
│   │       └── Main.java     # Clase principal
│   └── resources/
│       └── pokemon_data_pokeapi.csv  # Datos de Pokémon
└── test/
    └── java/
        └── pokemonmap/
            └── data/
                └── PokemonCollectionTest.java  # Pruebas unitarias
```

## Justificación de decisiones técnicas

### Patrón Factory para implementaciones de Map

Se ha implementado el patrón Factory (`MapFactory`) para poder seleccionar en tiempo de ejecución qué implementación de Map utilizar:

```java
public static Map<String, Pokemon> createMap(MapType mapType) {
    switch (mapType) {
        case HASH_MAP:
            return new HashMap<>();
        case TREE_MAP:
            return new TreeMap<>();
        case LINKED_HASH_MAP:
            return new LinkedHashMap<>();
        default:
            throw new IllegalArgumentException("Tipo de mapa no soportado");
    }
}
```

Cada implementación tiene ventajas y desventajas:

- **HashMap**: Ofrece búsquedas en O(1) pero sin garantías de orden
- **TreeMap**: Mantiene las claves ordenadas a costa de operaciones más lentas O(log n)
- **LinkedHashMap**: Combina la eficiencia de HashMap con mantenimiento del orden de inserción

Este diseño permite cambiar fácilmente la implementación subyacente sin modificar el código cliente, siguiendo el principio de inversión de dependencias.

### Elección de colección para los Pokémon del usuario

Para almacenar la colección de Pokémon del usuario, se ha utilizado un `HashSet` por las siguientes razones:

1. **Eficiencia**: Las operaciones de consulta (`contains`) son O(1), lo que permite verificar rápidamente si un Pokémon ya está en la colección.
2. **Sin duplicados**: Por definición del ejercicio, no se permiten Pokémon repetidos en la colección del usuario, lo que coincide con la naturaleza de Set.
3. **Simplicidad**: Solo necesitamos almacenar nombres de Pokémon (String) que ya existen en la colección principal.

La implementación en `PokemonCollection` muestra esta decisión:

```java
public class PokemonCollection {
    private Set<String> userPokemons;
    private PokemonData allPokemonData;

    public PokemonCollection(PokemonData allPokemonData) {
        this.allPokemonData = allPokemonData;
        this.userPokemons = new HashSet<>();
    }
    // ...
}
```

### Cálculo de complejidad para la operación #4 (Listar Pokémon por tipo)

La operación #4 consiste en mostrar todos los Pokémon ordenados por tipo1. La complejidad temporal de esta operación depende de la implementación de Map utilizada:

#### Caso 1: HashMap

```java
public List<Pokemon> getAllPokemonsSortedByType1() {
    return allPokemons.values().stream()
            .sorted((p1, p2) -> p1.getType1().compareTo(p2.getType1()))
            .collect(Collectors.toList());
}
```

Análisis paso a paso:
1. Obtener todos los valores del HashMap: O(n)
2. Convertir a stream: O(1)
3. Ordenar usando `sorted()`: O(n log n) - Utilizando un algoritmo de ordenamiento tipo merge sort o similar
4. Recolectar a una lista: O(n)

**Complejidad total**: O(n log n) donde n es el número de Pokémon, siendo el ordenamiento el paso dominante.

#### Caso 2: TreeMap

Si usamos TreeMap, pero la clave sigue siendo el nombre del Pokémon, la complejidad sigue siendo O(n log n) porque todavía necesitamos ordenar los valores por tipo1.

Si hubiéramos diseñado la estructura para usar el tipo1 como clave primaria (lo cual no era apropiado para los requisitos del problema donde necesitamos acceder por nombre), la complejidad sería O(n) ya que TreeMap mantiene sus elementos ordenados por clave.

#### Caso 3: LinkedHashMap

Similar al HashMap, la complejidad es O(n log n), con la única diferencia que los elementos se recorren en orden de inserción antes de ordenarlos.

## Pruebas Unitarias

Se han implementado dos pruebas unitarias principales:

1. **Test de adición de Pokémon**: Verifica que:
   - Se puedan agregar Pokémon existentes
   - No se permitan agregar Pokémon duplicados
   - No se permitan agregar Pokémon inexistentes

2. **Test de ordenamiento por tipo**: Verifica que al obtener los Pokémon del usuario ordenados por tipo1, se retornen en el orden alfabético correcto según el tipo.

Estas pruebas están implementadas con JUnit 5 en la clase `PokemonCollectionTest`.

## Carga de datos

La aplicación implementa múltiples estrategias para cargar el archivo CSV:

1. Desde el classpath (recursos del proyecto)
2. Desde una ruta por defecto
3. Búsqueda recursiva en el directorio de ejecución
4. Como último recurso, permite al usuario seleccionar manualmente el archivo

Esto mejora la experiencia de usuario evitando que tenga que seleccionar manualmente el archivo en la mayoría de los casos.

## Instalación y Ejecución

1. Clona el repositorio:
   ```
   git clone [URL-del-repositorio]
   ```

2. Asegúrate de tener el archivo CSV `pokemon_data_pokeapi.csv` en la carpeta `src/main/resources/`

3. Compila el proyecto (si usas Maven):
   ```
   mvn clean compile
   ```

4. Ejecuta la aplicación:
   ```
   java -cp target/classes pokemonmap.Main
   ```

## Autor

Denil José Parada Cabrera - 24761
Universidad del Valle de Guatemala
Semestre I - 2025
CC2016 – Algoritmos y Estructura de Datos
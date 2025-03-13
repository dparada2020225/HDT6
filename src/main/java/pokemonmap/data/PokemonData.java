/**
 * Universidad del Valle de Guatemala
 * Departamento de Ciencia de la Computación
 * Programación Orientada a Objetos
 * 
 * Autor: Denil José Parada Cabrera - 24761
 * Fecha: 12/03/2025
 * Descripción: Clase que gestiona la colección principal de Pokémon utilizando Map.
 *              Proporciona operaciones de búsqueda, consulta y filtrado de Pokémon.
 */
package pokemonmap.data;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import pokemonmap.factory.MapType;
import pokemonmap.factory.MapFactory;

/**
 * Gestiona la colección principal de todos los Pokémon disponibles en el sistema.
 * Utiliza el patrón Factory para crear diferentes implementaciones de Map según
 * se requiera (HashMap, TreeMap, LinkedHashMap).
 */
public class PokemonData {
    private Map<String, Pokemon> allPokemons;
    private MapType mapType;

    /**
     * Constructor que inicializa la colección de Pokémon con el tipo de Map especificado.
     * 
     * @param mapType El tipo de Map a utilizar (HASH_MAP, TREE_MAP, LINKED_HASH_MAP)
     */
    public PokemonData(MapType mapType) {
        this.mapType = mapType;
        this.allPokemons = MapFactory.createMap(mapType);
    }

    /**
     * Agrega un Pokémon a la colección principal.
     * 
     * @param pokemon El Pokémon a agregar
     */
    public void addPokemon(Pokemon pokemon) {
        allPokemons.put(pokemon.getName(), pokemon);
    }

    /**
     * Obtiene un Pokémon por su nombre.
     * 
     * @param name El nombre del Pokémon a buscar
     * @return El Pokémon encontrado o null si no existe
     */
    public Pokemon getPokemon(String name) {
        return allPokemons.get(name);
    }

    /**
     * Verifica si un Pokémon existe en la colección por su nombre.
     * 
     * @param name El nombre del Pokémon a buscar
     * @return true si el Pokémon existe, false en caso contrario
     */
    public boolean containsPokemon(String name) {
        return allPokemons.containsKey(name);
    }

    /**
     * Obtiene todos los Pokémon ordenados por su tipo primario.
     * 
     * @return Lista de Pokémon ordenados alfabéticamente por tipo primario
     */
    public List<Pokemon> getAllPokemonsSortedByType1() {
        return allPokemons.values().stream()
                .sorted((p1, p2) -> p1.getType1().compareTo(p2.getType1()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los Pokémon sin aplicar ordenamiento.
     * 
     * @return Colección de todos los Pokémon disponibles
     */
    public Collection<Pokemon> getAllPokemons() {
        return allPokemons.values();
    }

    /**
     * Busca Pokémon que tengan una habilidad específica.
     * 
     * @param ability La habilidad a buscar
     * @return Lista de Pokémon que tienen la habilidad especificada
     */
    public List<Pokemon> getPokemonsByAbility(String ability) {
        return allPokemons.values().stream()
                .filter(pokemon -> pokemon.hasAbility(ability))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el número total de Pokémon en la colección.
     * 
     * @return Cantidad de Pokémon en la colección
     */
    public int size() {
        return allPokemons.size();
    }

    /**
     * Obtiene el tipo de Map utilizado para almacenar los Pokémon.
     * 
     * @return El tipo de Map utilizado
     */
    public MapType getMapType() {
        return mapType;
    }
}
/**
 * Universidad del Valle de Guatemala
 * Departamento de Ciencia de la Computación
 * Programación Orientada a Objetos
 * 
 * Autor: Denil José Parada Cabrera - 24761
 * Fecha: 12/03/2025
 * Descripción: Clase que gestiona la colección personal de Pokémon del usuario.
 *              Utiliza HashSet para almacenar los nombres de los Pokémon del usuario
 *              y proporciona métodos para manipular esta colección.
 */
package pokemonmap.data;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import pokemonmap.factory.MapType;
import pokemonmap.factory.MapFactory;

/**
 * Gestiona la colección personal de Pokémon del usuario.
 * Almacena referencias a los Pokémon que el usuario ha decidido
 * agregar a su colección personal.
 */
public class PokemonCollection {
    private Set<String> userPokemons;
    private PokemonData allPokemonData;

    /**
     * Constructor que inicializa la colección del usuario.
     * 
     * @param allPokemonData Referencia a la colección principal de Pokémon
     */
    public PokemonCollection(PokemonData allPokemonData) {
        this.allPokemonData = allPokemonData;
        // Usamos HashSet para la colección del usuario porque:
        // 1. Las operaciones de verificación (contains) son O(1)
        // 2. No permitimos duplicados, que es un requisito del problema
        // 3. No necesitamos mantener un orden específico para esta colección
        this.userPokemons = new HashSet<>();
    }

    /**
     * Agrega un Pokémon a la colección del usuario.
     * 
     * @param name Nombre del Pokémon a agregar
     * @return true si se agregó correctamente, false si ya existe en la colección o
     *         si no existe en la colección principal
     */
    public boolean addPokemon(String name) {
        if (!allPokemonData.containsPokemon(name)) {
            return false; // El Pokémon no existe en los datos
        }
        
        if (userPokemons.contains(name)) {
            return false; // El Pokémon ya está en la colección del usuario
        }
        
        userPokemons.add(name);
        return true;
    }

    /**
     * Obtiene todos los Pokémon de la colección del usuario ordenados por tipo primario.
     * 
     * @return Lista de Pokémon ordenados por tipo primario
     */
    public List<Pokemon> getUserPokemonsSortedByType1() {
        return userPokemons.stream()
                .map(name -> allPokemonData.getPokemon(name))
                .sorted((p1, p2) -> p1.getType1().compareTo(p2.getType1()))
                .collect(Collectors.toList());
    }

    /**
     * Verifica si un Pokémon está en la colección del usuario.
     * 
     * @param name Nombre del Pokémon a verificar
     * @return true si el Pokémon está en la colección, false en caso contrario
     */
    public boolean containsPokemon(String name) {
        return userPokemons.contains(name);
    }

    /**
     * Obtiene el número de Pokémon en la colección del usuario.
     * 
     * @return Cantidad de Pokémon en la colección
     */
    public int size() {
        return userPokemons.size();
    }
}
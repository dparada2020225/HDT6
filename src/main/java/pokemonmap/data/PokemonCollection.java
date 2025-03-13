package pokemonmap.data;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import pokemonmap.factory.MapType;
import pokemonmap.factory.MapFactory;

public class PokemonCollection {
    private Set<String> userPokemons;
    private PokemonData allPokemonData;

    public PokemonCollection(PokemonData allPokemonData) {
        this.allPokemonData = allPokemonData;
        // Usamos HashSet para la colección del usuario porque:
        // 1. Las operaciones de verificación (contains) son O(1)
        // 2. No permitimos duplicados, que es un requisito del problema
        // 3. No necesitamos mantener un orden específico para esta colección
        this.userPokemons = new HashSet<>();
    }

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

    public List<Pokemon> getUserPokemonsSortedByType1() {
        return userPokemons.stream()
                .map(name -> allPokemonData.getPokemon(name))
                .sorted((p1, p2) -> p1.getType1().compareTo(p2.getType1()))
                .collect(Collectors.toList());
    }

    public boolean containsPokemon(String name) {
        return userPokemons.contains(name);
    }

    public int size() {
        return userPokemons.size();
    }
}
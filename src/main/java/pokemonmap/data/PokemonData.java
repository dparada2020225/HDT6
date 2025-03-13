package pokemonmap.data;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import pokemonmap.factory.MapType;
import pokemonmap.factory.MapFactory;

public class PokemonData {
    private Map<String, Pokemon> allPokemons;
    private MapType mapType;

    public PokemonData(MapType mapType) {
        this.mapType = mapType;
        this.allPokemons = MapFactory.createMap(mapType);
    }

    public void addPokemon(Pokemon pokemon) {
        allPokemons.put(pokemon.getName(), pokemon);
    }

    public Pokemon getPokemon(String name) {
        return allPokemons.get(name);
    }

    public boolean containsPokemon(String name) {
        return allPokemons.containsKey(name);
    }

    public List<Pokemon> getAllPokemonsSortedByType1() {
        return allPokemons.values().stream()
                .sorted((p1, p2) -> p1.getType1().compareTo(p2.getType1()))
                .collect(Collectors.toList());
    }

    // Método nuevo para obtener todos los Pokémon sin ordenar
    public Collection<Pokemon> getAllPokemons() {
        return allPokemons.values();
    }

    public List<Pokemon> getPokemonsByAbility(String ability) {
        return allPokemons.values().stream()
                .filter(pokemon -> pokemon.hasAbility(ability))
                .collect(Collectors.toList());
    }

    public int size() {
        return allPokemons.size();
    }

    public MapType getMapType() {
        return mapType;
    }
}
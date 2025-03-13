package test.java.pokemonmap.data;
import pokemonmap.data.PokemonData;
import pokemonmap.data.PokemonCollection;
import pokemonmap.data.Pokemon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonmap.factory.MapType;
import java.util.List;

public class PokemonCollectionTest {
    private PokemonData pokemonData;
    private PokemonCollection userCollection;

    @BeforeEach
    public void setUp() {
        // Configurar los datos para las pruebas
        pokemonData = new PokemonData(MapType.HASH_MAP);
        
        // Agregar algunos Pokémon de prueba
        pokemonData.addPokemon(new Pokemon("Pikachu", 25, "Electric", "", 
                               "Mouse Pokémon", 0.4, 6.0, 
                               "Static, Lightning-rod", 1, "No"));
        
        pokemonData.addPokemon(new Pokemon("Charizard", 6, "Fire", "Flying", 
                               "Flame Pokémon", 1.7, 90.5, 
                               "Blaze, Solar-power", 1, "No"));
        
        pokemonData.addPokemon(new Pokemon("Bulbasaur", 1, "Grass", "Poison", 
                               "Seed Pokémon", 0.7, 6.9, 
                               "Overgrow, Chlorophyll", 1, "No"));
        
        // Crear la colección del usuario
        userCollection = new PokemonCollection(pokemonData);
    }
    
    @Test
    public void testAddPokemon() {
        // Verificar que podemos agregar un Pokémon existente
        assertTrue(userCollection.addPokemon("Pikachu"));
        assertEquals(1, userCollection.size());
        
        // Verificar que no podemos agregar un Pokémon que ya tenemos
        assertFalse(userCollection.addPokemon("Pikachu"));
        assertEquals(1, userCollection.size());
        
        // Verificar que no podemos agregar un Pokémon que no existe
        assertFalse(userCollection.addPokemon("MissingNo"));
        assertEquals(1, userCollection.size());
    }
    
    @Test
    public void testGetUserPokemonsSortedByType1() {
        // Agregar algunos Pokémon
        userCollection.addPokemon("Pikachu");     // Electric
        userCollection.addPokemon("Charizard");   // Fire
        userCollection.addPokemon("Bulbasaur");   // Grass
        
        // Obtener la lista ordenada
        List<Pokemon> sortedPokemons = userCollection.getUserPokemonsSortedByType1();
        
        // Verificar el orden: Electric, Fire, Grass (orden alfabético)
        assertEquals(3, sortedPokemons.size());
        assertEquals("Electric", sortedPokemons.get(0).getType1());
        assertEquals("Fire", sortedPokemons.get(1).getType1());
        assertEquals("Grass", sortedPokemons.get(2).getType1());
    }
}
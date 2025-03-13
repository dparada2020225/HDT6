/**
 * Universidad del Valle de Guatemala
 * Departamento de Ciencia de la Computación
 * Programación Orientada a Objetos
 * 
 * Autor: Denil José Parada Cabrera - 24761
 * Fecha: 12/03/2025
 * Descripción: Clase de prueba para PokemonCollection.
 *              Verifica la funcionalidad de la colección de Pokémon del usuario
 *              mediante pruebas unitarias con JUnit.
 */
package test.java.pokemonmap.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonmap.factory.MapType;
import pokemonmap.data.Pokemon;
import pokemonmap.data.PokemonCollection;
import pokemonmap.data.PokemonData;
import java.util.List;

/**
 * Pruebas unitarias para la clase PokemonCollection.
 * Verifica el correcto funcionamiento de los métodos de la colección
 * de Pokémon del usuario.
 */
public class PokemonCollectionTest {
    private PokemonData pokemonData;
    private PokemonCollection userCollection;

    /**
     * Configuración inicial antes de cada prueba.
     * Crea datos de prueba con tres Pokémon diferentes.
     */
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
    
    /**
     * Prueba la funcionalidad de agregar Pokémon a la colección del usuario.
     * Verifica que:
     * - Se pueden agregar Pokémon que existen en la colección principal
     * - No se pueden agregar Pokémon duplicados
     * - No se pueden agregar Pokémon que no existen en la colección principal
     */
    @Test
    public void testAddPokemon() {
        // Verificar que podemos agregar un Pokémon existente
        assertTrue(userCollection.addPokemon("Pikachu"), "Debería poder agregar un Pokémon existente");
        assertEquals(1, userCollection.size(), "La colección debería tener 1 Pokémon");
        
        // Verificar que no podemos agregar un Pokémon que ya tenemos
        assertFalse(userCollection.addPokemon("Pikachu"), "No debería poder agregar un Pokémon duplicado");
        assertEquals(1, userCollection.size(), "La colección debería seguir teniendo 1 Pokémon");
        
        // Verificar que no podemos agregar un Pokémon que no existe
        assertFalse(userCollection.addPokemon("MissingNo"), "No debería poder agregar un Pokémon inexistente");
        assertEquals(1, userCollection.size(), "La colección debería seguir teniendo 1 Pokémon");
    }
    
    /**
     * Prueba la funcionalidad de ordenar los Pokémon por tipo primario.
     * Verifica que los Pokémon se ordenen correctamente por orden alfabético
     * de su tipo primario.
     */
    @Test
    public void testGetUserPokemonsSortedByType1() {
        // Agregar algunos Pokémon
        userCollection.addPokemon("Pikachu");     // Electric
        userCollection.addPokemon("Charizard");   // Fire
        userCollection.addPokemon("Bulbasaur");   // Grass
        
        // Obtener la lista ordenada
        List<Pokemon> sortedPokemons = userCollection.getUserPokemonsSortedByType1();
        
        // Verificar el orden: Electric, Fire, Grass (orden alfabético)
        assertEquals(3, sortedPokemons.size(), "La colección debería tener 3 Pokémon");
        assertEquals("Electric", sortedPokemons.get(0).getType1(), "El primer Pokémon debería ser de tipo Electric");
        assertEquals("Fire", sortedPokemons.get(1).getType1(), "El segundo Pokémon debería ser de tipo Fire");
        assertEquals("Grass", sortedPokemons.get(2).getType1(), "El tercer Pokémon debería ser de tipo Grass");
    }
    
    /**
     * Prueba la funcionalidad de verificar si un Pokémon está en la colección.
     * Verifica que el método containsPokemon funcione correctamente.
     */
    @Test
    public void testContainsPokemon() {
        // Agregar un Pokémon a la colección
        userCollection.addPokemon("Pikachu");
        
        // Verificar que el Pokémon está en la colección
        assertTrue(userCollection.containsPokemon("Pikachu"), "Debería encontrar el Pokémon en la colección");
        
        // Verificar que otro Pokémon no está en la colección
        assertFalse(userCollection.containsPokemon("Charizard"), "No debería encontrar un Pokémon que no fue agregado");
        
        // Verificar con un Pokémon inexistente
        assertFalse(userCollection.containsPokemon("MissingNo"), "No debería encontrar un Pokémon inexistente");
    }
}
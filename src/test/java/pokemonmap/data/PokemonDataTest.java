/**
 * Universidad del Valle de Guatemala
 * Departamento de Ciencia de la Computación
 * Programación Orientada a Objetos
 * 
 * Autor: Denil José Parada Cabrera - 24761
 * Fecha: 12/03/2025
 * Descripción: Clase de prueba para PokemonData.
 *              Verifica la funcionalidad de la colección principal de Pokémon
 *              mediante pruebas unitarias con JUnit.
 */
package test.java.pokemonmap.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pokemonmap.factory.MapType;
import pokemonmap.data.Pokemon;
import pokemonmap.data.PokemonData;
import java.util.List;
import java.util.Collection;

/**
 * Pruebas unitarias para la clase PokemonData.
 * Verifica el correcto funcionamiento de los métodos de la colección
 * principal de Pokémon.
 */
public class PokemonDataTest {
    private PokemonData pokemonData;

    /**
     * Configuración inicial antes de cada prueba.
     * Crea datos de prueba con tres Pokémon diferentes.
     */
    @BeforeEach
    public void setUp() {
        // Usar HashMap para las pruebas
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
    }
    
    /**
     * Prueba la funcionalidad de agregar y obtener Pokémon.
     * Verifica que se puedan agregar Pokémon y recuperarlos por nombre.
     */
    @Test
    public void testAddAndGetPokemon() {
        // Verificar que podemos obtener Pokémon agregados
        Pokemon pikachu = pokemonData.getPokemon("Pikachu");
        assertNotNull(pikachu, "Debería encontrar a Pikachu");
        assertEquals("Pikachu", pikachu.getName(), "El nombre debe coincidir");
        assertEquals("Electric", pikachu.getType1(), "El tipo debe coincidir");
        
        // Verificar que no podemos obtener Pokémon no agregados
        Pokemon mewtwo = pokemonData.getPokemon("Mewtwo");
        assertNull(mewtwo, "No debería encontrar a Mewtwo");
        
        // Agregar un nuevo Pokémon
        Pokemon meowth = new Pokemon("Meowth", 52, "Normal", "", 
                                   "Scratch Cat Pokémon", 0.4, 4.2, 
                                   "Pickup, Technician", 1, "No");
        pokemonData.addPokemon(meowth);
        
        // Verificar que ahora podemos obtenerlo
        Pokemon foundMeowth = pokemonData.getPokemon("Meowth");
        assertNotNull(foundMeowth, "Debería encontrar a Meowth después de agregarlo");
        assertEquals("Meowth", foundMeowth.getName(), "El nombre debe coincidir");
    }
    
    /**
     * Prueba la funcionalidad de verificar si un Pokémon existe en la colección.
     * Verifica que el método containsPokemon funcione correctamente.
     */
    @Test
    public void testContainsPokemon() {
        // Verificar Pokémon existentes
        assertTrue(pokemonData.containsPokemon("Pikachu"), "Debería encontrar a Pikachu");
        assertTrue(pokemonData.containsPokemon("Charizard"), "Debería encontrar a Charizard");
        assertTrue(pokemonData.containsPokemon("Bulbasaur"), "Debería encontrar a Bulbasaur");
        
        // Verificar Pokémon no existentes
        assertFalse(pokemonData.containsPokemon("Mewtwo"), "No debería encontrar a Mewtwo");
        assertFalse(pokemonData.containsPokemon(""), "No debería encontrar una cadena vacía");
    }
    
    /**
     * Prueba la funcionalidad de ordenar los Pokémon por tipo primario.
     * Verifica que los Pokémon se ordenen correctamente por orden alfabético
     * de su tipo primario.
     */
    @Test
    public void testGetAllPokemonsSortedByType1() {
        List<Pokemon> sortedPokemons = pokemonData.getAllPokemonsSortedByType1();
        
        // Verificar que tenemos todos los Pokémon
        assertEquals(3, sortedPokemons.size(), "Deberíamos tener 3 Pokémon");
        
        // Verificar el orden: Electric, Fire, Grass (orden alfabético)
        assertEquals("Electric", sortedPokemons.get(0).getType1(), "El primer Pokémon debería ser de tipo Electric");
        assertEquals("Fire", sortedPokemons.get(1).getType1(), "El segundo Pokémon debería ser de tipo Fire");
        assertEquals("Grass", sortedPokemons.get(2).getType1(), "El tercer Pokémon debería ser de tipo Grass");
    }
    
    /**
     * Prueba la funcionalidad de obtener todos los Pokémon sin ordenar.
     * Verifica que se obtengan todos los Pokémon de la colección.
     */
    @Test
    public void testGetAllPokemons() {
        Collection<Pokemon> allPokemons = pokemonData.getAllPokemons();
        
        // Verificar que tenemos todos los Pokémon
        assertEquals(3, allPokemons.size(), "Deberíamos tener 3 Pokémon");
        
        // Verificar que contiene los Pokémon esperados (sin importar el orden)
        boolean hasPikachu = false;
        boolean hasCharizard = false;
        boolean hasBulbasaur = false;
        
        for (Pokemon pokemon : allPokemons) {
            if (pokemon.getName().equals("Pikachu")) hasPikachu = true;
            if (pokemon.getName().equals("Charizard")) hasCharizard = true;
            if (pokemon.getName().equals("Bulbasaur")) hasBulbasaur = true;
        }
        
        assertTrue(hasPikachu, "La colección debería contener a Pikachu");
        assertTrue(hasCharizard, "La colección debería contener a Charizard");
        assertTrue(hasBulbasaur, "La colección debería contener a Bulbasaur");
    }
    
    /**
     * Prueba la funcionalidad de buscar Pokémon por habilidad.
     * Verifica que se encuentren los Pokémon que tienen una habilidad específica.
     */
    @Test
    public void testGetPokemonsByAbility() {
        // Buscar Pokémon con habilidad "Static"
        List<Pokemon> staticPokemons = pokemonData.getPokemonsByAbility("Static");
        assertEquals(1, staticPokemons.size(), "Debería encontrar 1 Pokémon con habilidad Static");
        assertEquals("Pikachu", staticPokemons.get(0).getName(), "Debería ser Pikachu");
        
        // Buscar Pokémon con habilidad "Blaze"
        List<Pokemon> blazePokemons = pokemonData.getPokemonsByAbility("Blaze");
        assertEquals(1, blazePokemons.size(), "Debería encontrar 1 Pokémon con habilidad Blaze");
        assertEquals("Charizard", blazePokemons.get(0).getName(), "Debería ser Charizard");
        
        // Buscar Pokémon con habilidad inexistente
        List<Pokemon> nonePokemons = pokemonData.getPokemonsByAbility("Intimidate");
        assertEquals(0, nonePokemons.size(), "No debería encontrar Pokémon con esa habilidad");
        
        // Búsqueda no sensible a mayúsculas/minúsculas
        List<Pokemon> caseInsensitivePokemons = pokemonData.getPokemonsByAbility("static");
        assertEquals(1, caseInsensitivePokemons.size(), "Debería encontrar 1 Pokémon con habilidad static (minúsculas)");
    }
    
    /**
     * Prueba la funcionalidad de obtener el tamaño de la colección.
     * Verifica que el método size devuelva el número correcto de Pokémon.
     */
    @Test
    public void testSize() {
        assertEquals(3, pokemonData.size(), "Deberíamos tener 3 Pokémon inicialmente");
        
        // Agregar un nuevo Pokémon
        Pokemon meowth = new Pokemon("Meowth", 52, "Normal", "", 
                                   "Scratch Cat Pokémon", 0.4, 4.2, 
                                   "Pickup, Technician", 1, "No");
        pokemonData.addPokemon(meowth);
        
        assertEquals(4, pokemonData.size(), "Deberíamos tener 4 Pokémon después de agregar uno");
    }
}
/**
 * Universidad del Valle de Guatemala
 * Departamento de Ciencia de la Computación
 * Programación Orientada a Objetos
 * 
 * Autor: Denil José Parada Cabrera - 24761
 * Fecha: 12/03/2025
 * Descripción: Clase utilitaria para leer archivos CSV que contienen información de Pokémon.
 *              Proporciona métodos para cargar datos desde archivos o recursos del classpath.
 */
package pokemonmap.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;
import pokemonmap.data.Pokemon;
import pokemonmap.data.PokemonData;

/**
 * Clase utilitaria para leer y procesar archivos CSV que contienen información de Pokémon.
 * Ofrece métodos para cargar datos desde archivos en el sistema o recursos del classpath.
 */
public class CSVReader {
    
    /**
     * Lee Pokémon desde un archivo CSV en el sistema de archivos.
     * 
     * @param filePath Ruta al archivo CSV
     * @param pokemonData Objeto PokemonData donde se cargarán los datos
     * @return El objeto PokemonData con los datos cargados
     * @throws IOException si ocurre un error al leer el archivo
     */
    public static PokemonData readPokemonsFromCSV(String filePath, PokemonData pokemonData) throws IOException {
        Path path = Paths.get(filePath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            return processCSV(br, pokemonData);
        }
    }
    
    /**
     * Lee Pokémon desde un recurso en el classpath.
     * Si no encuentra el recurso, intenta buscarlo como un archivo en el sistema.
     * 
     * @param resourcePath Ruta al recurso
     * @param pokemonData Objeto PokemonData donde se cargarán los datos
     * @return El objeto PokemonData con los datos cargados
     * @throws IOException si ocurre un error al leer el recurso o archivo
     */
    public static PokemonData readPokemonsFromResource(String resourcePath, PokemonData pokemonData) throws IOException {
        // Intentar cargar el recurso desde el classpath
        InputStream is = CSVReader.class.getClassLoader().getResourceAsStream(resourcePath);
        
        if (is == null) {
            // Si no se encuentra en el classpath, intentar como ruta relativa
            Path path = Paths.get(resourcePath);
            if (Files.exists(path)) {
                return readPokemonsFromCSV(resourcePath, pokemonData);
            } else {
                throw new IOException("No se pudo encontrar el archivo: " + resourcePath);
            }
        }
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return processCSV(br, pokemonData);
        }
    }
    
    /**
     * Procesa un archivo CSV y carga los datos en el objeto PokemonData.
     * 
     * @param br BufferedReader con el contenido del CSV
     * @param pokemonData Objeto PokemonData donde se cargarán los datos
     * @return El objeto PokemonData con los datos cargados
     * @throws IOException si ocurre un error al leer el archivo
     */
    private static PokemonData processCSV(BufferedReader br, PokemonData pokemonData) throws IOException {
        String line = br.readLine(); // Leer encabezados
        
        while ((line = br.readLine()) != null) {
            String[] values = parseCsvLine(line);
            
            if (values.length >= 10) {
                try {
                    Pokemon pokemon = new Pokemon(
                        values[0], // name
                        Integer.parseInt(values[1]), // pokedexNumber
                        values[2], // type1
                        values[3], // type2
                        values[4], // classification
                        Double.parseDouble(values[5]), // height
                        Double.parseDouble(values[6]), // weight
                        values[7], // abilities
                        Integer.parseInt(values[8]), // generation
                        values[9]  // legendaryStatus
                    );
                    
                    pokemonData.addPokemon(pokemon);
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear valores numéricos en la línea: " + line);
                    // Opcionalmente, puedes lanzar la excepción o continuarla
                    // throw e;
                }
            }
        }
        
        return pokemonData;
    }
    
    /**
     * Parsea una línea de texto CSV en un array de valores.
     * Maneja correctamente campos entrecomillados y comas dentro de los campos.
     * 
     * @param line Línea de texto CSV a parsear
     * @return Array con los valores extraídos de la línea
     */
    private static String[] parseCsvLine(String line) {
        // Implementación simple de parser CSV
        // Considera usar una biblioteca como OpenCSV para un código de producción
        if (line == null) return new String[0];
        
        // Manejar campos entrecomillados y comas dentro de los campos
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();
        java.util.List<String> fields = new java.util.ArrayList<>();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        
        fields.add(field.toString());
        return fields.toArray(new String[0]);
    }
}
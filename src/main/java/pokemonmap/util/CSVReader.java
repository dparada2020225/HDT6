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

public class CSVReader {
    public static PokemonData readPokemonsFromCSV(String filePath, PokemonData pokemonData) throws IOException {
        Path path = Paths.get(filePath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            return processCSV(br, pokemonData);
        }
    }
    
    // Método para cargar desde recursos (classpath)
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
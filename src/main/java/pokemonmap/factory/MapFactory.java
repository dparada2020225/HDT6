/**
 * Universidad del Valle de Guatemala
 * Departamento de Ciencia de la Computación
 * Programación Orientada a Objetos
 * 
 * Autor: Denil José Parada Cabrera - 24761
 * Fecha: 12/03/2025
 * Descripción: Implementación del patrón Factory para crear diferentes tipos de Map.
 *              Permite seleccionar entre HashMap, TreeMap y LinkedHashMap en tiempo de ejecución.
 */
package pokemonmap.factory;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import pokemonmap.data.Pokemon;

/**
 * Factory para crear diferentes implementaciones de Map.
 * Implementa el patrón de diseño Factory Method para proporcionar
 * diferentes implementaciones de Map según se requiera.
 */
public class MapFactory {
    
    /**
     * Crea una implementación específica de Map basada en el tipo solicitado.
     * 
     * @param mapType El tipo de Map que se desea crear (HASH_MAP, TREE_MAP, LINKED_HASH_MAP)
     * @return Un nuevo Map vacío del tipo especificado
     * @throws IllegalArgumentException si el tipo de mapa no es soportado
     */
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
}
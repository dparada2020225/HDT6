package pokemonmap.factory;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import pokemonmap.data.Pokemon;

public class MapFactory {
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
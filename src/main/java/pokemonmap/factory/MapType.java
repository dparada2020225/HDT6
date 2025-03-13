package pokemonmap.factory;

public enum MapType {
    HASH_MAP(1, "HashMap"),
    TREE_MAP(2, "TreeMap"),
    LINKED_HASH_MAP(3, "LinkedHashMap");

    private final int value;
    private final String name;

    MapType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static MapType fromValue(int value) {
        for (MapType type : MapType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de Map inválido: " + value);
    }
}

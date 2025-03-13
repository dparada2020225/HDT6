/**
 * Universidad del Valle de Guatemala
 * Departamento de Ciencia de la Computación
 * Programación Orientada a Objetos
 * 
 * Autor: Denil José Parada Cabrera - 24761
 * Fecha: 12/03/2025
 * Descripción: Enumeración que define los tipos de Map disponibles para su uso
 *              en la aplicación. Proporciona métodos para convertir entre valores
 *              numéricos y enums.
 */
package pokemonmap.factory;

/**
 * Enumeración que define los diferentes tipos de Map que se pueden utilizar en la aplicación.
 * Cada tipo tiene un valor numérico y un nombre asociado para facilitar la selección
 * por parte del usuario.
 */
public enum MapType {
    /**
     * HashMap: implementación basada en tablas hash. Ofrece tiempo de acceso O(1)
     * pero no garantiza orden.
     */
    HASH_MAP(1, "HashMap"),
    
    /**
     * TreeMap: implementación basada en árboles Red-Black. Mantiene las claves
     * ordenadas con tiempo de acceso O(log n).
     */
    TREE_MAP(2, "TreeMap"),
    
    /**
     * LinkedHashMap: implementación que combina HashMap y lista enlazada.
     * Mantiene el orden de inserción con tiempo de acceso O(1).
     */
    LINKED_HASH_MAP(3, "LinkedHashMap");

    private final int value;
    private final String name;

    /**
     * Constructor para los elementos de la enumeración.
     * 
     * @param value Valor numérico asociado al tipo de Map
     * @param name Nombre descriptivo del tipo de Map
     */
    MapType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Obtiene el valor numérico asociado al tipo de Map.
     * 
     * @return El valor numérico
     */
    public int getValue() {
        return value;
    }

    /**
     * Obtiene el nombre descriptivo del tipo de Map.
     * 
     * @return El nombre descriptivo
     */
    public String getName() {
        return name;
    }

    /**
     * Convierte un valor numérico a su correspondiente elemento de la enumeración.
     * 
     * @param value El valor numérico a convertir
     * @return El elemento de la enumeración correspondiente
     * @throws IllegalArgumentException si el valor no corresponde a ningún elemento
     */
    public static MapType fromValue(int value) {
        for (MapType type : MapType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de Map inválido: " + value);
    }
}
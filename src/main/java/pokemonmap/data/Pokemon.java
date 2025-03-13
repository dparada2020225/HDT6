/**
 * Universidad del Valle de Guatemala
 * Departamento de Ciencia de la Computación
 * Programación Orientada a Objetos
 * 
 * Autor: Denil José Parada Cabrera - 24761
 * Fecha: 12/03/2025
 * Descripción: Clase que representa un Pokémon con sus atributos básicos como nombre, tipo, 
 *              habilidades, etc. Incluye métodos para obtener información del Pokémon.
 */
package pokemonmap.data;

/**
 * Representa un Pokémon con todos sus atributos característicos.
 * Esta clase almacena la información básica de cada Pokémon como su nombre,
 * tipo, estadísticas, habilidades y clasificación.
 */
public class Pokemon {
    private String name;
    private int pokedexNumber;
    private String type1;
    private String type2;
    private String classification;
    private double height;
    private double weight;
    private String abilities;
    private int generation;
    private String legendaryStatus;

    /**
     * Constructor que inicializa un nuevo Pokémon con todos sus atributos.
     * 
     * @param name Nombre del Pokémon
     * @param pokedexNumber Número en la Pokédex
     * @param type1 Tipo primario
     * @param type2 Tipo secundario (puede ser vacío)
     * @param classification Clasificación del Pokémon
     * @param height Altura en metros
     * @param weight Peso en kilogramos
     * @param abilities Lista de habilidades separadas por comas
     * @param generation Generación a la que pertenece
     * @param legendaryStatus Indica si es legendario o no
     */
    public Pokemon(String name, int pokedexNumber, String type1, String type2, 
                  String classification, double height, double weight, 
                  String abilities, int generation, String legendaryStatus) {
        this.name = name;
        this.pokedexNumber = pokedexNumber;
        this.type1 = type1;
        this.type2 = type2;
        this.classification = classification;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.generation = generation;
        this.legendaryStatus = legendaryStatus;
    }

    /**
     * Obtiene el nombre del Pokémon.
     * 
     * @return El nombre del Pokémon
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el número de Pokédex del Pokémon.
     * 
     * @return El número de Pokédex
     */
    public int getPokedexNumber() {
        return pokedexNumber;
    }

    /**
     * Obtiene el tipo primario del Pokémon.
     * 
     * @return El tipo primario
     */
    public String getType1() {
        return type1;
    }

    /**
     * Obtiene el tipo secundario del Pokémon.
     * 
     * @return El tipo secundario o cadena vacía si no tiene
     */
    public String getType2() {
        return type2;
    }

    /**
     * Obtiene la clasificación del Pokémon.
     * 
     * @return La clasificación
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Obtiene la altura del Pokémon en metros.
     * 
     * @return La altura en metros
     */
    public double getHeight() {
        return height;
    }

    /**
     * Obtiene el peso del Pokémon en kilogramos.
     * 
     * @return El peso en kilogramos
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Obtiene las habilidades del Pokémon.
     * 
     * @return Lista de habilidades separadas por comas
     */
    public String getAbilities() {
        return abilities;
    }

    /**
     * Obtiene la generación a la que pertenece el Pokémon.
     * 
     * @return El número de generación
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Obtiene el estado legendario del Pokémon.
     * 
     * @return "Yes" si es legendario, "No" si no lo es
     */
    public String getLegendaryStatus() {
        return legendaryStatus;
    }

    /**
     * Genera una representación en forma de texto con todos los atributos del Pokémon.
     * 
     * @return Cadena con los datos del Pokémon formateados
     */
    @Override
    public String toString() {
        return "Nombre: " + name + "\n" +
               "Número de Pokédex: " + pokedexNumber + "\n" +
               "Tipo primario: " + type1 + "\n" +
               "Tipo secundario: " + (type2 == null || type2.isEmpty() ? "N/A" : type2) + "\n" +
               "Clasificación: " + classification + "\n" +
               "Altura (m): " + height + "\n" +
               "Peso (kg): " + weight + "\n" +
               "Habilidades: " + abilities + "\n" +
               "Generación: " + generation + "\n" +
               "Estado legendario: " + legendaryStatus;
    }

    /**
     * Verifica si el Pokémon tiene una habilidad específica.
     * 
     * @param ability La habilidad a buscar
     * @return true si el Pokémon tiene la habilidad, false en caso contrario
     */
    public boolean hasAbility(String ability) {
        return abilities.toLowerCase().contains(ability.toLowerCase());
    }
}
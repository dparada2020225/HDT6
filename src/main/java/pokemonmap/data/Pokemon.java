package pokemonmap.data;

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

    // Getters
    public String getName() {
        return name;
    }

    public int getPokedexNumber() {
        return pokedexNumber;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public String getClassification() {
        return classification;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getAbilities() {
        return abilities;
    }

    public int getGeneration() {
        return generation;
    }

    public String getLegendaryStatus() {
        return legendaryStatus;
    }

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

    public boolean hasAbility(String ability) {
        return abilities.toLowerCase().contains(ability.toLowerCase());
    }
}
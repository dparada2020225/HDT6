package pokemonmap;

import pokemonmap.gui.PokemonGUI;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new PokemonGUI().setVisible(true);
        });
    }
}

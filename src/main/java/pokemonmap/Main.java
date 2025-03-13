/**
 * Universidad del Valle de Guatemala
 * Departamento de Ciencia de la Computación
 * Programación Orientada a Objetos
 * 
 * Autor: Denil José Parada Cabrera - 24761
 * Fecha: 12/03/2025
 * Descripción: Clase principal que inicia la aplicación de gestión de Pokémon.
 *              Lanza la interfaz gráfica en el hilo de eventos de Swing.
 */
package pokemonmap;

import pokemonmap.gui.PokemonGUI;

/**
 * Clase principal que contiene el método main para iniciar la aplicación.
 * Crea y muestra la interfaz gráfica en el hilo de eventos de Swing.
 */
public class Main {
    
    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Iniciar la interfaz gráfica en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            new PokemonGUI().setVisible(true);
        });
    }
}
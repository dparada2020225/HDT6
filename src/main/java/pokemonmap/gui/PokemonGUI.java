/**
 * Universidad del Valle de Guatemala
 * Departamento de Ciencia de la Computación
 * Programación Orientada a Objetos
 * 
 * Autor: Denil José Parada Cabrera - 24761
 * Fecha: 12/03/2025
 * Descripción: Interfaz gráfica de usuario para la aplicación de gestión de Pokémon.
 *              Permite seleccionar el tipo de Map, cargar datos desde un archivo CSV
 *              y realizar las operaciones requeridas sobre la colección de Pokémon.
 */
package pokemonmap.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import pokemonmap.data.Pokemon;
import pokemonmap.data.PokemonCollection;
import pokemonmap.data.PokemonData;
import pokemonmap.factory.MapType;
import pokemonmap.util.CSVReader;

/**
 * Interfaz gráfica de usuario para la aplicación de gestión de Pokémon.
 * Proporciona una interfaz amigable para interactuar con la colección de Pokémon,
 * permitiendo cargar datos, buscar información y gestionar una colección personal.
 */
public class PokemonGUI extends JFrame {
    // Constantes para colores
    private static final Color HEADER_COLOR = new Color(43, 87, 151); // Azul más oscuro para el header
    private static final Color BACKGROUND_COLOR = new Color(240, 242, 245); // Gris claro para el fondo
    private static final Color BUTTON_BORDER_COLOR = new Color(33, 77, 141); // Azul más oscuro para bordes
    private static final Color OUTPUT_BORDER_COLOR = new Color(255, 211, 0); // Amarillo para el borde del output

    // Componentes de la interfaz
    private PokemonData pokemonData;
    private PokemonCollection userCollection;
    private JTextArea outputTextArea;
    private JComboBox<String> mapTypeComboBox;
    private JTextField inputTextField;
    private JButton loadDataButton;
    private JButton addPokemonButton;
    private JButton showPokemonButton;
    private JButton showUserCollectionButton;
    private JButton showAllPokemonsButton;
    private JButton findByAbilityButton;
    private JButton showAllAvailablePokemonsButton; // Botón para mostrar todos los disponibles
    private JLabel statusLabel;
    private JPanel headerPanel;
    
    // Ruta por defecto al archivo CSV
    private static final String DEFAULT_CSV_PATH = "src/main/resources/pokemon_data_pokeapi.csv";

    /**
     * Constructor para la interfaz gráfica. Inicializa todos los componentes
     * y configura el layout de la ventana principal.
     */
    public PokemonGUI() {
        // Configuración básica de la ventana
        setTitle("Gestor de Pokémon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        try {
            // Establecer Look and Feel del sistema para que se vea más nativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al establecer Look and Feel: " + e.getMessage());
        }

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Panel de cabecera con título
        headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel central que contiene los controles, botones y área de texto
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        
        // Panel de controles
        JPanel controlPanel = createControlPanel();
        centerPanel.add(controlPanel, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel buttonPanel = createButtonPanel();
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Panel de resultado con área de texto
        JPanel outputPanel = createOutputPanel();
        centerPanel.add(outputPanel, BorderLayout.SOUTH);

        // Panel de estado (pie de página)
        JPanel statusPanel = createStatusPanel();
        
        // Agregar paneles al panel principal
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        // Agregar panel principal a la ventana
        getContentPane().add(mainPanel);
        
        // Configurar eventos
        setupEventHandlers();
    }
    
    /**
     * Crea el panel de cabecera con el título de la aplicación.
     * 
     * @return Panel de cabecera configurado
     */
    private JPanel createHeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(900, 50));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Gestor de Pokémon");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    /**
     * Crea el panel de control con selector de Map, botón de carga y campo de texto.
     * 
     * @return Panel de control configurado
     */
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(BACKGROUND_COLOR);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        
        // Selector de tipo de Map
        JLabel mapTypeLabel = new JLabel("Tipo de Map:");
        mapTypeLabel.setForeground(Color.BLACK);
        
        mapTypeComboBox = new JComboBox<>(new String[]{"HashMap", "TreeMap", "LinkedHashMap"});
        mapTypeComboBox.setPreferredSize(new Dimension(150, 30));
        
        // Botón para cargar datos
        loadDataButton = new JButton("Cargar Datos");
        loadDataButton.setPreferredSize(new Dimension(120, 30));
        loadDataButton.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER_COLOR));
        
        // Campo de texto para entrada
        JLabel inputLabel = new JLabel("Nombre/Habilidad:");
        inputLabel.setForeground(Color.BLACK);
        
        inputTextField = new JTextField(20);
        inputTextField.setPreferredSize(new Dimension(200, 30));
        
        // Agregar componentes al panel
        controlPanel.add(mapTypeLabel);
        controlPanel.add(mapTypeComboBox);
        controlPanel.add(loadDataButton);
        controlPanel.add(inputLabel);
        controlPanel.add(inputTextField);
        
        // Separador horizontal
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setPreferredSize(new Dimension(880, 1));
        separator.setForeground(Color.LIGHT_GRAY);
        
        JPanel separatorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        separatorPanel.setBackground(BACKGROUND_COLOR);
        separatorPanel.add(separator);
        
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(BACKGROUND_COLOR);
        wrapperPanel.add(controlPanel, BorderLayout.CENTER);
        wrapperPanel.add(separatorPanel, BorderLayout.SOUTH);
        
        return wrapperPanel;
    }
    
    /**
     * Crea el panel de botones con las acciones principales.
     * 
     * @return Panel de botones configurado
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        // Crear botones
        addPokemonButton = createStyledButton("Agregar a Colección");
        showPokemonButton = createStyledButton("Mostrar Datos");
        showUserCollectionButton = createStyledButton("Mi Colección");
        showAllPokemonsButton = createStyledButton("Todos por Tipo");
        findByAbilityButton = createStyledButton("Buscar por Habilidad");
        
        // Desactivar botones hasta que se carguen los datos
        addPokemonButton.setEnabled(false);
        showPokemonButton.setEnabled(false);
        showUserCollectionButton.setEnabled(false);
        showAllPokemonsButton.setEnabled(false);
        findByAbilityButton.setEnabled(false);
        
        // Agregar botones al panel
        buttonPanel.add(addPokemonButton);
        buttonPanel.add(showPokemonButton);
        buttonPanel.add(showUserCollectionButton);
        buttonPanel.add(showAllPokemonsButton);
        buttonPanel.add(findByAbilityButton);
        
        return buttonPanel;
    }
    
    /**
     * Crea un botón estilizado con el texto especificado.
     * 
     * @param text Texto a mostrar en el botón
     * @return Botón estilizado
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(170, 30));
        button.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER_COLOR, 1));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        return button;
    }
    
    /**
     * Crea el panel de salida con área de texto para mostrar resultados.
     * 
     * @return Panel de salida configurado
     */
    private JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout(0, 5));
        outputPanel.setBackground(BACKGROUND_COLOR);
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel resultLabel = new JLabel("Resultado:");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setPreferredSize(new Dimension(880, 340));
        scrollPane.setBorder(BorderFactory.createLineBorder(OUTPUT_BORDER_COLOR, 1));
        
        outputPanel.add(resultLabel, BorderLayout.NORTH);
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        
        return outputPanel;
    }
    
    /**
     * Crea el panel de estado (pie de página) con información y botón adicional.
     * 
     * @return Panel de estado configurado
     */
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(Color.LIGHT_GRAY);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Etiqueta de estado a la izquierda
        statusLabel = new JLabel("Listo para comenzar. Selecciona un tipo de Map y carga los datos.");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        
        // Botón para mostrar todos los Pokémon disponibles a la derecha
        showAllAvailablePokemonsButton = new JButton("Mostrar todos los Pokémon disponibles");
        showAllAvailablePokemonsButton.setEnabled(false);
        
        // Panel para contener el botón y mantenerlo alineado a la derecha
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.setBackground(Color.LIGHT_GRAY);
        buttonContainer.add(showAllAvailablePokemonsButton);
        
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(buttonContainer, BorderLayout.EAST);
        
        return statusPanel;
    }
    
    
    
    /**
     * Configura los manejadores de eventos para todos los componentes interactivos.
     */
    private void setupEventHandlers() {
        // Cargar datos
        loadDataButton.addActionListener(e -> {
            int selectedIndex = mapTypeComboBox.getSelectedIndex();
            MapType mapType = MapType.fromValue(selectedIndex + 1);
            
            outputTextArea.setText("Cargando datos con " + mapType.getName() + "...\n");
            
            // Ejecutar en un hilo separado para no bloquear la UI
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        loadPokemonDataAutomatically(mapType);
                    } catch (Exception ex) {
                        throw ex;
                    }
                    return null;
                }
                
                @Override
                protected void done() {
                    try {
                        get(); // Esto lanzará la excepción si ocurrió durante doInBackground
                        outputTextArea.append("Datos cargados correctamente. " + 
                                             pokemonData.size() + " Pokémon disponibles.\n");
                        statusLabel.setText("Datos cargados: " + pokemonData.size() + " Pokémon disponibles");
                        
                        // Habilitar botones individualmente en lugar de usar toggleButtonsEnabled
                        addPokemonButton.setEnabled(true);
                        showPokemonButton.setEnabled(true);
                        showUserCollectionButton.setEnabled(true);
                        showAllPokemonsButton.setEnabled(true);
                        findByAbilityButton.setEnabled(true);
                        showAllAvailablePokemonsButton.setEnabled(true);
                        
                        mapTypeComboBox.setEnabled(false);
                        loadDataButton.setEnabled(false);
                    } catch (Exception ex) {
                        outputTextArea.append("Error al cargar datos: " + ex.getMessage() + "\n");
                        statusLabel.setText("Error al cargar datos");
                        ex.printStackTrace();
                        
                        // Si falla la carga automática, ofrecer selección manual
                        int option = JOptionPane.showConfirmDialog(PokemonGUI.this, 
                            "No se pudo cargar el archivo CSV automáticamente. ¿Desea seleccionarlo manualmente?",
                            "Error de carga", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                        
                        if (option == JOptionPane.YES_OPTION) {
                            try {
                                loadPokemonDataManually(mapType);
                            } catch (Exception ex2) {
                                outputTextArea.append("Error en carga manual: " + ex2.getMessage() + "\n");
                                statusLabel.setText("Error en carga manual");
                                ex2.printStackTrace();
                            }
                        }
                    }
                }
            };
            
            worker.execute();
        });
        
        // Agregar Pokémon a la colección del usuario
        addPokemonButton.addActionListener(e -> {
            String pokemonName = inputTextField.getText().trim();
            if (pokemonName.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, ingrese un nombre de Pokémon.", 
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!pokemonData.containsPokemon(pokemonName)) {
                JOptionPane.showMessageDialog(this, 
                    "El Pokémon '" + pokemonName + "' no existe en los datos.", 
                    "Pokémon no encontrado", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (userCollection.containsPokemon(pokemonName)) {
                JOptionPane.showMessageDialog(this, 
                    "El Pokémon '" + pokemonName + "' ya está en tu colección.", 
                    "Pokémon duplicado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            boolean added = userCollection.addPokemon(pokemonName);
            if (added) {
                outputTextArea.append("Pokémon '" + pokemonName + "' agregado a tu colección.\n");
                statusLabel.setText("Pokémon agregado a tu colección");
                
                // Mostrar un mensaje de éxito
                JOptionPane.showMessageDialog(this, 
                    "¡" + pokemonName + " ha sido agregado a tu colección!", 
                    "Pokémon capturado", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Mostrar datos de un Pokémon
        showPokemonButton.addActionListener(e -> {
            String pokemonName = inputTextField.getText().trim();
            if (pokemonName.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, ingrese un nombre de Pokémon.", 
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Pokemon pokemon = pokemonData.getPokemon(pokemonName);
            if (pokemon == null) {
                JOptionPane.showMessageDialog(this, 
                    "El Pokémon '" + pokemonName + "' no existe en los datos.", 
                    "Pokémon no encontrado", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Mostrar los datos del Pokémon
            StringBuilder sb = new StringBuilder();
            sb.append("Nombre: ").append(pokemon.getName()).append("\n");
            sb.append("Número Pokédex: ").append(pokemon.getPokedexNumber()).append("\n");
            sb.append("Tipo Primario: ").append(pokemon.getType1()).append("\n");
            String type2 = pokemon.getType2() == null || pokemon.getType2().isEmpty() ? "N/A" : pokemon.getType2();
            sb.append("Tipo Secundario: ").append(type2).append("\n");
            sb.append("Clasificación: ").append(pokemon.getClassification()).append("\n");
            sb.append("Altura (m): ").append(pokemon.getHeight()).append("\n");
            sb.append("Peso (kg): ").append(pokemon.getWeight()).append("\n");
            sb.append("Habilidades: ").append(pokemon.getAbilities()).append("\n");
            sb.append("Generación: ").append(pokemon.getGeneration()).append("\n");
            sb.append("Estado Legendario: ").append(pokemon.getLegendaryStatus()).append("\n");
            
            outputTextArea.setText(sb.toString());
            statusLabel.setText("Mostrando datos de " + pokemonName);
        });
        
        // Mostrar colección del usuario ordenada por tipo
        showUserCollectionButton.addActionListener(e -> {
            List<Pokemon> userPokemons = userCollection.getUserPokemonsSortedByType1();
            
            if (userPokemons.isEmpty()) {
                outputTextArea.setText("Tu colección está vacía.\n");
                statusLabel.setText("Colección vacía");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Tu colección ordenada por tipo primario:\n\n");
            
            String currentType = "";
            
            for (Pokemon pokemon : userPokemons) {
                // Si cambia el tipo, mostrar el nuevo tipo como encabezado
                if (!currentType.equals(pokemon.getType1())) {
                    currentType = pokemon.getType1();
                    sb.append("\n").append(currentType).append(":\n");
                }
                
                sb.append("- ").append(pokemon.getName()).append("\n");
            }
            
            outputTextArea.setText(sb.toString());
            statusLabel.setText("Mostrando tu colección de " + userPokemons.size() + " Pokémon");
        });
        
        // Mostrar todos los Pokémon ordenados por tipo
        showAllPokemonsButton.addActionListener(e -> {
            List<Pokemon> allPokemons = pokemonData.getAllPokemonsSortedByType1();
            
            StringBuilder sb = new StringBuilder();
            sb.append("Todos los Pokémon ordenados por tipo primario:\n\n");
            
            String currentType = "";
            
            for (Pokemon pokemon : allPokemons) {
                // Si cambia el tipo, mostrar el nuevo tipo como encabezado
                if (!currentType.equals(pokemon.getType1())) {
                    currentType = pokemon.getType1();
                    sb.append("\n").append(currentType).append(":\n");
                }
                
                sb.append("- ").append(pokemon.getName()).append("\n");
            }
            
            outputTextArea.setText(sb.toString());
            statusLabel.setText("Mostrando " + allPokemons.size() + " Pokémon ordenados por tipo");
        });
        
        // Buscar Pokémon por habilidad
        findByAbilityButton.addActionListener(e -> {
            String ability = inputTextField.getText().trim();
            if (ability.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, ingrese una habilidad.", 
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            List<Pokemon> matchingPokemons = pokemonData.getPokemonsByAbility(ability);
            
            if (matchingPokemons.isEmpty()) {
                outputTextArea.setText("No se encontraron Pokémon con la habilidad '" + ability + "'.\n");
                statusLabel.setText("No se encontraron Pokémon con esa habilidad");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Pokémon con la habilidad '").append(ability).append("':\n\n");
            
            for (Pokemon pokemon : matchingPokemons) {
                sb.append("- ").append(pokemon.getName()).append("\n");
            }
            
            outputTextArea.setText(sb.toString());
            statusLabel.setText("Se encontraron " + matchingPokemons.size() + " Pokémon con la habilidad '" + ability + "'");
        });
        
        // Configurar el botón para mostrar todos los Pokémon disponibles
        if (showAllAvailablePokemonsButton != null) {
            showAllAvailablePokemonsButton.addActionListener(e -> {
                if (pokemonData == null || pokemonData.size() == 0) {
                    outputTextArea.setText("No hay datos de Pokémon cargados.\n");
                    return;
                }
                
                StringBuilder sb = new StringBuilder();
                sb.append("Listado completo de todos los Pokémon disponibles:\n\n");
                
                // Obtener todos los Pokémon (no ordenados, en el orden del Map)
                int count = 0;
                for (Pokemon pokemon : pokemonData.getAllPokemons()) {
                    count++;
                    sb.append(String.format("%3d. %-15s - Tipo: %-10s - Pokédex: #%d\n", 
                              count, pokemon.getName(), pokemon.getType1(), pokemon.getPokedexNumber()));
                }
                
                outputTextArea.setText(sb.toString());
                statusLabel.setText("Mostrando lista completa de " + pokemonData.size() + " Pokémon");
            });
        }
        
        // Acción al presionar Enter en el campo de texto
        inputTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Si los botones están habilitados, ejecutar la acción de mostrar datos
                    if (showPokemonButton.isEnabled()) {
                        showPokemonButton.doClick();
                    }
                }
            }
        });
    }
    
    /**
     * Carga automáticamente los datos de Pokémon desde varias ubicaciones posibles.
     * 
     * @param mapType El tipo de Map a utilizar
     * @throws IOException si no se puede cargar el archivo CSV
     */
    private void loadPokemonDataAutomatically(MapType mapType) throws IOException {
        pokemonData = new PokemonData(mapType);
        
        // Primero intentamos cargar desde el classpath
        try {
            pokemonData = CSVReader.readPokemonsFromResource("pokemon_data_pokeapi.csv", pokemonData);
            userCollection = new PokemonCollection(pokemonData);
            return;
        } catch (IOException e) {
            // Si falla, intentamos con la ruta por defecto
            File defaultFile = new File(DEFAULT_CSV_PATH);
            if (defaultFile.exists()) {
                pokemonData = CSVReader.readPokemonsFromCSV(DEFAULT_CSV_PATH, pokemonData);
                userCollection = new PokemonCollection(pokemonData);
                return;
            }
            
            // Si aún falla, intentamos buscar en el directorio actual y subdirectorios
            File currentDir = new File(".");
            File csvFile = findCsvFile(currentDir, "pokemon_data_pokeapi.csv");
            
            if (csvFile != null) {
                pokemonData = CSVReader.readPokemonsFromCSV(csvFile.getAbsolutePath(), pokemonData);
                userCollection = new PokemonCollection(pokemonData);
                return;
            }
            
            throw new IOException("No se pudo encontrar el archivo CSV automáticamente");
        }
    }
    
    /**
     * Busca recursivamente un archivo CSV en un directorio y sus subdirectorios.
     * 
     * @param dir Directorio donde buscar
     * @param fileName Nombre del archivo a buscar
     * @return El archivo encontrado o null si no se encuentra
     */
    private File findCsvFile(File dir, String fileName) {
        if (dir == null || !dir.isDirectory()) return null;
        
        File[] files = dir.listFiles();
        if (files == null) return null;
        
        for (File file : files) {
            if (file.isFile() && file.getName().equals(fileName)) {
                return file;
            } else if (file.isDirectory()) {
                File found = findCsvFile(file, fileName);
                if (found != null) return found;
            }
        }
        
        return null;
    }
    
    /**
     * Carga manualmente los datos de Pokémon a través de un selector de archivos.
     * 
     * @param mapType El tipo de Map a utilizar
     * @throws IOException si no se puede cargar el archivo CSV
     */
    private void loadPokemonDataManually(MapType mapType) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV de Pokémon");
        
        int result = fileChooser.showOpenDialog(this);
        
        if (result != JFileChooser.APPROVE_OPTION) {
            throw new IOException("No se seleccionó ningún archivo");
        }
        
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        
        // Crear las instancias de datos
        pokemonData = new PokemonData(mapType);
        pokemonData = CSVReader.readPokemonsFromCSV(filePath, pokemonData);
        userCollection = new PokemonCollection(pokemonData);
        
        outputTextArea.append("Datos cargados correctamente desde " + filePath + ". " + 
                             pokemonData.size() + " Pokémon disponibles.\n");
        statusLabel.setText("Datos cargados: " + pokemonData.size() + " Pokémon disponibles");
        
        // Habilitar botones individualmente en lugar de usar toggleButtonsEnabled
        addPokemonButton.setEnabled(true);
        showPokemonButton.setEnabled(true);
        showUserCollectionButton.setEnabled(true);
        showAllPokemonsButton.setEnabled(true);
        findByAbilityButton.setEnabled(true);
        showAllAvailablePokemonsButton.setEnabled(true);
        
        mapTypeComboBox.setEnabled(false);
        loadDataButton.setEnabled(false);
    }
    
    /**
     * Método principal para iniciar la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PokemonGUI().setVisible(true);
        });
    }
}
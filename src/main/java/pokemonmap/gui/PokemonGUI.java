package pokemonmap.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import pokemonmap.data.Pokemon;
import pokemonmap.data.PokemonCollection;
import pokemonmap.data.PokemonData;
import pokemonmap.factory.MapType;
import pokemonmap.util.CSVReader;

public class PokemonGUI extends JFrame {
    // Constantes para colores
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 245);
    private static final Color HEADER_COLOR = new Color(49, 92, 168); // Azul Pokémon
    private static final Color ACCENT_COLOR = new Color(254, 203, 0);  // Amarillo Pokémon
    private static final Color BUTTON_COLOR = new Color(65, 105, 225); // Azul real
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(50, 50, 50);

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
    private JLabel statusLabel;
    private JPanel headerPanel;
    
    // Ruta por defecto al archivo CSV
    private static final String DEFAULT_CSV_PATH = "src/main/resources/pokemon_data_pokeapi.csv";

    public PokemonGUI() {
        // Configuración básica de la ventana
        setTitle("Gestor de Pokémon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setBackground(BACKGROUND_COLOR);
        
        try {
            // Establecer Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Personalizar componentes UI
            customizeUIComponents();
        } catch (Exception e) {
            System.err.println("Error al establecer Look and Feel: " + e.getMessage());
        }

        // Configurar icono de la aplicación si está disponible
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(classLoader.getResource("pokeball.png")));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono: " + e.getMessage());
        }

        // Panel principal con un BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de cabecera con logo
        setupHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel central que contiene los controles y botones
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BACKGROUND_COLOR);

        // Panel de controles
        JPanel controlPanel = createControlPanel();
        centerPanel.add(controlPanel, BorderLayout.NORTH);

        // Panel de botones
        JPanel buttonPanel = createButtonPanel();
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        // Área de texto para la salida con scroll
        JPanel outputPanel = createOutputPanel();
        centerPanel.add(outputPanel, BorderLayout.SOUTH);

        // Panel de estado
        JPanel statusPanel = createStatusPanel();
        
        // Agregar todo al panel principal
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        // Agregar panel principal a la ventana
        getContentPane().add(mainPanel);
        
        // Configurar eventos
        setupEventHandlers();
    }
    
    private void customizeUIComponents() {
        // Personalizar componentes UI globalmente
        UIManager.put("Button.background", BUTTON_COLOR);
        UIManager.put("Button.foreground", BUTTON_TEXT_COLOR);
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 12));
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("TextArea.font", new Font("Monospaced", Font.PLAIN, 13));
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("ComboBox.font", new Font("SansSerif", Font.PLAIN, 13));
    }
    
    private void setupHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Gestor de Pokémon");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(classLoader.getResource("pokemon_logo.png")));
            Image image = logoIcon.getImage().getScaledInstance(200, 74, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(image));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(logoLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            // Si no se puede cargar el logo, usar el título de texto
            headerPanel.add(titleLabel, BorderLayout.CENTER);
        }
    }
    
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(BACKGROUND_COLOR);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, ACCENT_COLOR),
            BorderFactory.createEmptyBorder(5, 10, 10, 10)
        ));
        
        // Selector de tipo de Map
        JLabel mapTypeLabel = new JLabel("Tipo de Map:");
        mapTypeLabel.setForeground(TEXT_COLOR);
        
        mapTypeComboBox = new JComboBox<>(new String[]{"HashMap", "TreeMap", "LinkedHashMap"});
        mapTypeComboBox.setPreferredSize(new Dimension(150, 30));
        mapTypeComboBox.setToolTipText("Selecciona la implementación de Map a utilizar");
        
        // Botón para cargar datos
        loadDataButton = createStyledButton("Cargar Datos", "Carga los datos de Pokémon desde el archivo CSV");
        
        // Campo de texto para entrada
        JLabel inputLabel = new JLabel("Nombre/Habilidad:");
        inputLabel.setForeground(TEXT_COLOR);
        
        inputTextField = new JTextField(20);
        inputTextField.setPreferredSize(new Dimension(200, 30));
        inputTextField.setToolTipText("Introduce el nombre del Pokémon o la habilidad a buscar");
        
        // Agregar componentes al panel
        controlPanel.add(mapTypeLabel);
        controlPanel.add(mapTypeComboBox);
        controlPanel.add(loadDataButton);
        controlPanel.add(inputLabel);
        controlPanel.add(inputTextField);
        
        return controlPanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Crear botones con estilo
        addPokemonButton = createStyledButton("Agregar a Colección", "Agrega el Pokémon especificado a tu colección");
        showPokemonButton = createStyledButton("Mostrar Datos", "Muestra todos los datos del Pokémon especificado");
        showUserCollectionButton = createStyledButton("Mi Colección", "Muestra tu colección ordenada por tipo");
        showAllPokemonsButton = createStyledButton("Todos los Pokémon", "Muestra todos los Pokémon ordenados por tipo");
        findByAbilityButton = createStyledButton("Buscar por Habilidad", "Busca Pokémon con la habilidad especificada");
        
        // Desactivar botones hasta que se carguen los datos
        toggleButtonsEnabled(false);
        
        // Agregar botones al panel
        buttonPanel.add(addPokemonButton);
        buttonPanel.add(showPokemonButton);
        buttonPanel.add(showUserCollectionButton);
        buttonPanel.add(showAllPokemonsButton);
        buttonPanel.add(findByAbilityButton);
        
        return buttonPanel;
    }
    
    private JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        outputPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel resultLabel = new JLabel("Resultado:");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        resultLabel.setForeground(TEXT_COLOR);
        
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputTextArea.setBackground(Color.WHITE);
        outputTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setPreferredSize(new Dimension(850, 350));
        scrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1));
        
        outputPanel.add(resultLabel, BorderLayout.NORTH);
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        
        return outputPanel;
    }
    
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusPanel.setBackground(new Color(230, 230, 230));
        
        statusLabel = new JLabel("Listo para comenzar. Selecciona un tipo de Map y carga los datos.");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        
        statusPanel.add(statusLabel, BorderLayout.WEST);
        
        return statusPanel;
    }
    
    private JButton createStyledButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BUTTON_COLOR.darker(), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setToolTipText(tooltip);
        
        // Efectos al pasar el ratón
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_COLOR.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(BUTTON_COLOR.darker());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }
    
    private void toggleButtonsEnabled(boolean enabled) {
        addPokemonButton.setEnabled(enabled);
        showPokemonButton.setEnabled(enabled);
        showUserCollectionButton.setEnabled(enabled);
        showAllPokemonsButton.setEnabled(enabled);
        findByAbilityButton.setEnabled(enabled);
    }
    
    private void setupEventHandlers() {
        // Cargar datos
        loadDataButton.addActionListener(e -> {
            int selectedIndex = mapTypeComboBox.getSelectedIndex();
            MapType mapType = MapType.fromValue(selectedIndex + 1);
            
            outputTextArea.setText("Cargando datos con " + mapType.getName() + "...\n");
            statusLabel.setText("Cargando datos...");
            
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
                        toggleButtonsEnabled(true);
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
                
                // Mostrar un mensaje de éxito con una imagen del tipo del Pokémon si está disponible
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
            
            // Mostrar los datos con formato mejorado
            StringBuilder sb = new StringBuilder();
            sb.append("┌─────────────────────────────────────────────┐\n");
            sb.append("│             DATOS DE POKÉMON                │\n");
            sb.append("├─────────────────────────────────────────────┤\n");
            sb.append(String.format("│ Nombre: %-35s │\n", pokemon.getName()));
            sb.append(String.format("│ Número Pokédex: %-28d │\n", pokemon.getPokedexNumber()));
            sb.append(String.format("│ Tipo Primario: %-30s │\n", pokemon.getType1()));
            String type2 = pokemon.getType2() == null || pokemon.getType2().isEmpty() ? "N/A" : pokemon.getType2();
            sb.append(String.format("│ Tipo Secundario: %-28s │\n", type2));
            sb.append(String.format("│ Clasificación: %-30s │\n", pokemon.getClassification()));
            sb.append(String.format("│ Altura (m): %-32.1f │\n", pokemon.getHeight()));
            sb.append(String.format("│ Peso (kg): %-33.1f │\n", pokemon.getWeight()));
            sb.append(String.format("│ Habilidades: %-31s │\n", pokemon.getAbilities()));
            sb.append(String.format("│ Generación: %-32d │\n", pokemon.getGeneration()));
            sb.append(String.format("│ Estado Legendario: %-26s │\n", pokemon.getLegendaryStatus()));
            sb.append("└─────────────────────────────────────────────┘\n");
            
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
            sb.append("┌─────────────────────────────────────────────┐\n");
            sb.append("│        TU COLECCIÓN POR TIPO PRIMARIO       │\n");
            sb.append("├─────────────────────────────────────────────┤\n");
            sb.append("│ Nombre                 │ Tipo Primario      │\n");
            sb.append("├────────────────────────┼────────────────────┤\n");
            
            String currentType = "";
            
            for (Pokemon pokemon : userPokemons) {
                // Si cambia el tipo, agregar un separador
                if (!currentType.equals(pokemon.getType1())) {
                    currentType = pokemon.getType1();
                    if (!pokemon.equals(userPokemons.get(0))) {
                        sb.append("├────────────────────────┼────────────────────┤\n");
                    }
                }
                
                sb.append(String.format("│ %-22s │ %-18s │\n", 
                          pokemon.getName(), pokemon.getType1()));
            }
            
            sb.append("└────────────────────────┴────────────────────┘\n");
            
            outputTextArea.setText(sb.toString());
            statusLabel.setText("Mostrando tu colección de " + userPokemons.size() + " Pokémon");
        });
        
        // Mostrar todos los Pokémon ordenados por tipo
        showAllPokemonsButton.addActionListener(e -> {
            List<Pokemon> allPokemons = pokemonData.getAllPokemonsSortedByType1();
            
            StringBuilder sb = new StringBuilder();
            sb.append("┌─────────────────────────────────────────────┐\n");
            sb.append("│        TODOS LOS POKÉMON POR TIPO           │\n");
            sb.append("├─────────────────────────────────────────────┤\n");
            sb.append("│ Nombre                 │ Tipo Primario      │\n");
            sb.append("├────────────────────────┼────────────────────┤\n");
            
            String currentType = "";
            int count = 0;
            
            for (Pokemon pokemon : allPokemons) {
                // Si cambia el tipo, agregar un encabezado para el nuevo tipo
                if (!currentType.equals(pokemon.getType1())) {
                    currentType = pokemon.getType1();
                    if (count > 0) {
                        sb.append("├────────────────────────┼────────────────────┤\n");
                    }
                }
                
                sb.append(String.format("│ %-22s │ %-18s │\n", 
                          pokemon.getName(), pokemon.getType1()));
                count++;
            }
            
            sb.append("└────────────────────────┴────────────────────┘\n");
            
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
            sb.append("┌─────────────────────────────────────────────┐\n");
            sb.append(String.format("│ POKÉMON CON LA HABILIDAD: %-19s │\n", ability.toUpperCase()));
            sb.append("├─────────────────────────────────────────────┤\n");
            sb.append("│ Nombre                 │ Tipo Primario      │\n");
            sb.append("├────────────────────────┼────────────────────┤\n");
            
            for (Pokemon pokemon : matchingPokemons) {
                sb.append(String.format("│ %-22s │ %-18s │\n", 
                          pokemon.getName(), pokemon.getType1()));
            }
            
            sb.append("└────────────────────────┴────────────────────┘\n");
            
            outputTextArea.setText(sb.toString());
            statusLabel.setText("Se encontraron " + matchingPokemons.size() + " Pokémon con la habilidad '" + ability + "'");
        });
        
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
    
    // Método para cargar los datos automáticamente desde la ruta por defecto
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
    
    // Método para buscar recursivamente un archivo CSV
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
    
    // Método para cargar los datos manualmente a través de un selector de archivos
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
        toggleButtonsEnabled(true);
        mapTypeComboBox.setEnabled(false);
        loadDataButton.setEnabled(false);
    }
    
    public static void main(String[] args) {
        // Establecer aspecto visual antes de crear la ventana
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.err.println("Error al establecer Look and Feel: " + ex.getMessage());
            }
        }
        
        SwingUtilities.invokeLater(() -> {
            PokemonGUI gui = new PokemonGUI();
            gui.setVisible(true);
        });
    }
}
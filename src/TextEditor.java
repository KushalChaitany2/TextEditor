import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class TextEditor implements ActionListener {
    JFrame frame;
    private JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu;
    private JMenuItem newWindowItem, openFileItem, saveFileItem;
    private JMenuItem cutItem, copyItem, pasteItem, selectAllItem, closeItem;

    public TextEditor() {
        frame = new JFrame("Text Editor");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Initialize Text Area
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // The structure of the application's UI involves a panel that contains the
        // scroll pane and the text area
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Add borders inside the panel

        // create a scroll pane with vertical and horizontal scroll bars as needed
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // add the scroll pane to the panel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add panel to layout
        frame.add(panel, BorderLayout.CENTER);

        // Initialize Menu Bar
        menuBar = new JMenuBar();

        // File Menu
        fileMenu = new JMenu("File");
        newWindowItem = new JMenuItem("New Window");
        openFileItem = new JMenuItem("Open File");
        saveFileItem = new JMenuItem("Save File");

        newWindowItem.addActionListener(this);
        openFileItem.addActionListener(this);
        saveFileItem.addActionListener(this);

        fileMenu.add(newWindowItem);
        fileMenu.add(openFileItem);
        fileMenu.add(saveFileItem);

        // Edit Menu
        editMenu = new JMenu("Edit");
        cutItem = new JMenuItem("Cut");
        copyItem = new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        selectAllItem = new JMenuItem("Select All");
        closeItem = new JMenuItem("Close");

        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);
        selectAllItem.addActionListener(this);
        closeItem.addActionListener(this);

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.addSeparator();
        editMenu.add(selectAllItem);
        editMenu.addSeparator();
        editMenu.add(closeItem);

        // Add Menus to Menu Bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        // Set Menu Bar
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == newWindowItem) {
            SwingUtilities.invokeLater(() -> {
                new TextEditor();
            });
        }

        // The first step in the open file function is to open the file chooser
        if (actionEvent.getSource() == openFileItem) {
            JFileChooser fileChooser = new JFileChooser();
            int chooseOption = fileChooser.showOpenDialog(frame);

            if (chooseOption == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    // The text contained in the file will be copied and pasted into the text area
                    StringBuilder fileContent = new StringBuilder();
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        fileContent.append(scanner.nextLine()).append("\n");
                    }
                    scanner.close();
                    textArea.setText(fileContent.toString());
                    frame.setTitle(file.getName() + " - Text Editor");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        // To implement the save file functionality, a new "if" statement is created...
        if (actionEvent.getSource() == saveFileItem) {
            // A file chooser is initialized to select the file path and name
            JFileChooser fileChooser = new JFileChooser();

            // using the show save dialog method, which displays a save button as the
            // approve option
            int chooseOption = fileChooser.showSaveDialog(frame);

            if (chooseOption == JFileChooser.APPROVE_OPTION) {
                // A new file is created with the chosen directory path and file name using the
                // File object.
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                try {
                    // The text from the text area is saved to the new file
                    BufferedWriter outFile = new BufferedWriter(new FileWriter(file));
                    outFile.write(textArea.getText());
                    outFile.close();
                    frame.setTitle(file.getName() + " - Text Editor");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (actionEvent.getSource() == cutItem) {
            textArea.cut();
        }
        if (actionEvent.getSource() == copyItem) {
            textArea.copy();
        }
        if (actionEvent.getSource() == pasteItem) {
            textArea.paste();
        }
        if (actionEvent.getSource() == selectAllItem) {
            textArea.selectAll();
        }
        if (actionEvent.getSource() == closeItem) {
            frame.dispose(); // Close current window
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            TextEditor textEditor = new TextEditor();
            textEditor.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}

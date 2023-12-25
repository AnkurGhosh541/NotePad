import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyFrame extends JFrame implements ActionListener, KeyListener {

    JTextArea textArea;
    JLabel charCount;

    public MyFrame() {
        setTitle("Note Pad");
        setSize(600, 400);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
    }

    private void init() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("file");
        JMenu editMenu = new JMenu("edit");

        JMenuItem newItem = new JMenuItem("new");
        JMenuItem openItem = new JMenuItem("open");
        JMenuItem saveItem = new JMenuItem("save");
        JMenuItem exitItem = new JMenuItem("exit");

        JMenuItem cutItem = new JMenuItem("cut");
        JMenuItem copyItem = new JMenuItem("copy");
        JMenuItem pasteItem = new JMenuItem("paste");

        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        menuBar.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        add(menuBar, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.addKeyListener(this);
        JScrollPane mainArea = new JScrollPane(textArea);
        mainArea.setBorder(BorderFactory.createEmptyBorder(1,5,1,5));
        add(mainArea, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        charCount = new JLabel("characters: 0    words: 0");
        bottomPanel.add(charCount);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        MyFrame f = new MyFrame();
        f.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        String str = textArea.getText();
        int chrCount = str.replaceAll("\n", "").length();
        int wordCount = 1;
        String strippedStr = str.trim();
        char prev = '\0';
        for (int i = 0; i < strippedStr.length(); i++) {
            char current = strippedStr.charAt(i);
            if (current == ' ' || (current == '\n' && prev != '\n')) wordCount++;
            prev = current;
        }
        charCount.setText("characters: " + chrCount + "    words: " + wordCount);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equalsIgnoreCase("new")) {
            textArea.setText("");
            charCount.setText("characters: 0    words: 0");
        } else if (cmd.equalsIgnoreCase("open")) {
            openFile();
        } else if (cmd.equalsIgnoreCase("save")) {
            saveFile();
        } else if (cmd.equalsIgnoreCase("exit")) {
            exit();
        } else if (cmd.equalsIgnoreCase("cut")) {
            textArea.cut();
        } else if (cmd.equalsIgnoreCase("copy")) {
            textArea.copy();
        } else if (cmd.equalsIgnoreCase("paste")) {
            textArea.paste();
        }

    }

    private void openFile() {
        JFileChooser fc = new JFileChooser();
        int optionChosed = fc.showOpenDialog(this);

        if (optionChosed == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();

            FileInputStream fis;
            try {
                fis = new FileInputStream(f);
                byte[] b = fis.readAllBytes();
                textArea.setText(new String(b));
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        JFileChooser fc = new JFileChooser();
        int optionChosed = fc.showSaveDialog(this);

        if (optionChosed == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(f);
                byte[] b = textArea.getText().getBytes();
                fos.write(b);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void exit() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to exit ?", "warning", JOptionPane.YES_NO_OPTION);
        if(choice == 0) System.exit(1);
    }

}


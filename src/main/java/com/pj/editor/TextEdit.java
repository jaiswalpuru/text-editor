package com.pj.editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEdit extends JFrame implements ActionListener {

    private static JTextArea area;
    private static JFrame frame;

    public TextEdit() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame("proEdit");
        area = new JTextArea();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(area);
        frame.setSize(new Dimension(640, 480));
        frame.setVisible(true);
        editorInit();
    }

    public void editorInit() {

        JMenuBar menuBar = new JMenuBar();
        JMenu jMenu = new JMenu("File");

        JMenuItem menuItemNew = new JMenuItem("New");
        JMenuItem menuItemOpen = new JMenuItem("Open");
        JMenuItem menuItemSave = new JMenuItem("Save");
        JMenuItem menuItemQuit = new JMenuItem("Quit");

        menuItemNew.addActionListener(this);
        menuItemOpen.addActionListener(this);
        menuItemSave.addActionListener(this);
        menuItemQuit.addActionListener(this);

        menuBar.add(jMenu);
        jMenu.add(menuItemNew);
        jMenu.add(menuItemOpen);
        jMenu.add(menuItemSave);
        jMenu.add(menuItemQuit);

        frame.setJMenuBar(menuBar);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionEvent = e.getActionCommand();
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Select the file to open");

        switch (actionEvent.toLowerCase()) {
            case "new" -> {
                area.setText("");
            }
            case "open" -> {
                StringBuilder sb = new StringBuilder();
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File f = new File(jfc.getSelectedFile().getAbsolutePath());
                    try {
                        FileInputStream fr = new FileInputStream(f);
                        BufferedInputStream br = new BufferedInputStream(fr);
                        int in = 0;
                        while (in != -1) {
                            in = br.read();
                            sb.append((char)in);
                        }
                        area.setText(sb.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            case "save" -> {
                jfc.showSaveDialog(null);
                try {
                    File f = new File(jfc.getSelectedFile().getAbsolutePath());
                    FileWriter fw = new FileWriter(f);
                    fw.write(area.getText());
                    fw.close();
                } catch(FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "file not found");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "error while reading the file");
                }
            }
            case "quit" -> {
                System.exit(0);
            }
            default -> throw new IllegalStateException("Unexpected value: " + actionEvent.toLowerCase());
        }
    }
}

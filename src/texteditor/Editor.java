/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import javax.swing.plaf.metal.*;

public class Editor extends JFrame implements ActionListener {
    JTextArea ta;
    JFrame f;

    Editor() {
        f = new JFrame("Text Editor");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
        }

        ta = new JTextArea();
        ta.setLineWrap(true);

        JMenuBar bar = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenuItem newfile = new JMenuItem("New");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem print = new JMenuItem("Print");

        newfile.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        print.addActionListener(this);

        m1.add(newfile);
        m1.add(open);
        m1.add(save);
        m1.add(print);

        JMenu m2 = new JMenu("Edit");
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);

        m2.add(cut);
        m2.add(copy);
        m2.add(paste);

        JMenuItem m3 = new JMenuItem("close");
        m3.addActionListener(this);

        bar.add(m1);
        bar.add(m2);
        bar.add(m3);

        JScrollPane sp = new JScrollPane(ta);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        f.setJMenuBar(bar);
        f.add(sp);
        f.setSize(500, 500);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String option = e.getActionCommand();
        switch (option) {
            case "Cut":
                ta.cut();
                break;
            case "Copy":
                ta.copy();
                break;
            case "Paste":
                ta.paste();
                break;
            case "Save": {
                JFileChooser j = new JFileChooser("f: ");
                int r = j.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    File file = new File(j.getSelectedFile().getAbsolutePath());
                    try {
                        FileWriter wr = new FileWriter(file, false);
                        try (BufferedWriter w = new BufferedWriter(wr)) {
                            w.write(ta.getText());
                            w.flush();
                        }
                    } catch (IOException evt) {
                        JOptionPane.showMessageDialog(f, evt.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(f, "the user cancelled the operation");
                }
                break;
            }
            case "Print":
                try {
                    ta.print();
                } catch (PrinterException evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
                break;
            case "Open": {
                JFileChooser j = new JFileChooser("f:");
                int r = j.showOpenDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                    File file = new File(j.getSelectedFile().getAbsolutePath());
                    try {
                        String s1, s2 = "";
                        FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr);
                        while ((s1 = br.readLine()) != null) {
                            s2 = s2 + s1 + "\n";
                        }
                        br.close();
                        ta.setText(s2);
                    } catch (IOException evt) {
                        JOptionPane.showMessageDialog(f, evt.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(f, "the user cancelled the operation");
                }
                break;
            }
            case "New":
                ta.setText("");
                break;
            case "close":
                f.setVisible(false);
                System.exit(0);
            default:
                break;
        }
    }
}

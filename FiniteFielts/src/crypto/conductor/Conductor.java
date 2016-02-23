package crypto.conductor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import crypto.polynom.Polynom;
import crypto.time.measurement.TimeIt;
//-----------------------------------------------------------------------------------------



public class conductor extends JFrame {
	
    // main-Methode
    public static void main(String[] args)
    {
        // Erzeugung eines neuen Dialoges
        JDialog meinJDialog = new JDialog();
        meinJDialog.setTitle("JPanel");
        meinJDialog.setSize(800,600);
 
        // JPanel erzeugung
        JPanel Generatorpolynom = new JPanel();
        JPanel field = new JPanel();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 800;
        gbc.gridy = 600;
        gbc.weightx = 1;
        
        JButton Generator = new JButton("Generator");      
        
        JTextArea XEingabe = new JTextArea("X eingabe");
        XEingabe.setBounds(50,150,10,20);
        
        JTextArea PEingabe = new JTextArea("P eingabe");
        XEingabe.setBounds(50,150,100,20);
        
        JTextArea NEingabe = new JTextArea("N eingabe");
        XEingabe.setBounds(50,150,100,20);
        
        JTextField Ausgabe = new JTextField();
        //XEingabe.setBounds(50,150,100,20);
        
 
        // JPanels Generator 
        
        Generatorpolynom.add(Generator);
        Generatorpolynom.add(XEingabe);
        Generatorpolynom.add(NEingabe);
        Generatorpolynom.add(PEingabe);
        Generatorpolynom.add(Ausgabe, gbc);
        
        // Knopfaction
        
        Generator.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int n=Integer.parseInt(NEingabe.getText());
				//int p=Integer.parseInt(PEingabe.getText());
				int x=Integer.parseInt(XEingabe.getText());
				
				int p=5;
				//_Feld.setText(Polynom.createXGeneratingPolynome(p, n, x).get(0).toString());
				String s =String.valueOf(Polynom.createXGeneratingPolynome(p, n, x));
				Ausgabe.setText(s);
			}
		});
        
    
        
        // Erzeugung eines JTabbedPane-Objektes
        JTabbedPane tabpane = new JTabbedPane
            (JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT );
 
        // Hier werden die JPanels als Registerkarten hinzugefügt
        tabpane.addTab("Generatorpolynom", Generatorpolynom);
        tabpane.addTab("Feld", field);
        
 
        // JTabbedPane wird unserem Dialog hinzugefügt
        meinJDialog.add(tabpane);
        // Wir lassen unseren Dialog anzeigen
        meinJDialog.setVisible(true);
    }
}

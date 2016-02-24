package crypto.conductor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import crypto.polynom.Polynom;

public class Conductor extends JFrame {

	private static final long serialVersionUID = 3532667781001479319L;

	private static List<Polynom> _polynome;

	public static void main(String[] args) {
		// Erzeugung eines neuen Dialoges
		JFrame meinJFrame = new JFrame();
		meinJFrame.setTitle("JPanel");
		meinJFrame.setSize(500, 400);
		meinJFrame.setResizable(false);

		// JPanel erzeugung

		JPanel Generatorpolynom = new JPanel();
		Generatorpolynom.setLayout(null);

		JPanel field = new JPanel();

		JLabel genlabel = new JLabel("Grad:");
		genlabel.setBounds(40, 15, 100, 25);
		JTextArea NEingabe = new JTextArea();
		NEingabe.setBounds(40, 35, 65, 22);

		JLabel plabel = new JLabel("Primzahl:");
		plabel.setBounds(160, 15, 100, 25);
		JTextArea PEingabe = new JTextArea();
		PEingabe.setBounds(160, 35, 55, 22);

		JLabel xlabel = new JLabel("Anzahl:");
		xlabel.setBounds(280, 15, 100, 20);
		JTextArea XEingabe = new JTextArea();
		XEingabe.setBounds(280, 35, 45, 22);

		JLabel auslabel = new JLabel("Anzeige der Generierungspolynome");
		auslabel.setBounds(40, 100, 250, 20);
		JEditorPane ausgabe = new JEditorPane();
		ausgabe.setBounds(40, 120, 290, 50);

		JButton Generator = new JButton("Erzeuge Genpoly");
		Generator.setBounds(340, 35, 100, 20);

		JScrollPane editorScrollPane = new JScrollPane(ausgabe);
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		editorScrollPane.setBounds(40, 120, 290, 100);
		editorScrollPane.setPreferredSize(new Dimension(10, 200));
		editorScrollPane.setMinimumSize(new Dimension(10, 50));
		// JPanels Generator

		Generatorpolynom.add(Generator);
		Generatorpolynom.add(XEingabe);
		Generatorpolynom.add(NEingabe);
		Generatorpolynom.add(PEingabe);
		// Generatorpolynom.add(ausgabe);
		Generatorpolynom.add(genlabel);
		Generatorpolynom.add(plabel);
		Generatorpolynom.add(xlabel);
		Generatorpolynom.add(auslabel);
		Generatorpolynom.add(editorScrollPane);

		// Knopfaction

		Generator.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int n = Integer.parseInt(NEingabe.getText());
				int p = Integer.parseInt(PEingabe.getText());
				int x = Integer.parseInt(XEingabe.getText());

				_polynome = Polynom.createXGeneratingPolynome(p, n, x);

				StringBuilder b = new StringBuilder();
				for (Polynom poly : _polynome) {
					// b.append(poly.toString());
					b.append(poly.toString());
					b.append(" = ");
					b.append(poly.toHtmlString());
					b.append("<br>");
				}

				ausgabe.setContentType("text/html");
				ausgabe.setText(b.toString() + "<html></html>");

			}
		});

		// Erzeugung eines JTabbedPane-Objektes1
		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		// Hier werden die JPanels als Registerkarten hinzugef�gt
		tabpane.addTab("Generatorpolynom", Generatorpolynom);
		tabpane.addTab("Feld", field);

		// JTabbedPane wird unserem Dialog hinzugef�gt
		meinJFrame.add(tabpane);
		// Wir lassen unseren Dialog anzeigen
		meinJFrame.setVisible(true);
	}

}

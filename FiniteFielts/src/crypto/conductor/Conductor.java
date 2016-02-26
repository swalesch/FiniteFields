package crypto.conductor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
		meinJFrame.setTitle("Irreduzibles Polynom");
		meinJFrame.setSize(470, 500);
		meinJFrame.setResizable(false);

		// JPanel erzeugung

		JPanel Generatorpolynom = new JPanel();
		Generatorpolynom.setLayout(null);

		JPanel field = new JPanel();
		field.setLayout(null);

		// ----------------------------------------------------Generatorpolynom
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
		// --------------------------------------------------------------------------field

		JButton run = new JButton("Run");
		run.setBounds(250, 37, 100, 20);
		JLabel eingabepoly = new JLabel("Polynomauswahl");
		eingabepoly.setBounds(70, 15, 100, 25);
		JComboBox polyein = new JComboBox();
		polyein.setBounds(70, 35, 150, 25);

		JScrollPane koerper = new JScrollPane();
		koerper.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JLabel allkoerper = new JLabel("Alle Elemente des Körpers");
		allkoerper.setBounds(100, 80, 150, 20);
		koerper.setBounds(100, 100, 250, 70);
		koerper.setPreferredSize(new Dimension(10, 200));
		koerper.setMinimumSize(new Dimension(10, 50));

		JScrollPane addtable = new JScrollPane();
		addtable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JLabel labaddtable = new JLabel("Additionstabelle");
		labaddtable.setBounds(100, 190, 150, 20);
		addtable.setBounds(100, 210, 250, 70);
		addtable.setPreferredSize(new Dimension(10, 200));
		addtable.setMinimumSize(new Dimension(10, 50));

		JScrollPane multable = new JScrollPane();
		multable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JLabel labmultable = new JLabel("Multiplikationstabelle");
		labmultable.setBounds(100, 300, 150, 20);
		multable.setBounds(100, 320, 250, 70);
		multable.setPreferredSize(new Dimension(10, 200));
		multable.setMinimumSize(new Dimension(10, 50));

		field.add(polyein);
		field.add(eingabepoly);
		field.add(allkoerper);
		field.add(koerper);
		field.add(addtable);
		field.add(labaddtable);
		field.add(labmultable);
		field.add(multable);

		field.add(run);

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

		// Hier werden die JPanels als Registerkarten hinzugefï¿½gt
		tabpane.addTab("Generatorpolynom", Generatorpolynom);
		tabpane.addTab("Feld", field);

		// JTabbedPane wird unserem Dialog hinzugefï¿½gt
		meinJFrame.add(tabpane);
		// Wir lassen unseren Dialog anzeigen
		meinJFrame.setVisible(true);
	}

}

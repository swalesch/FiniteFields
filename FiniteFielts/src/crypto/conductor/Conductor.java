package crypto.conductor;

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

import crypto.field.Field;
import crypto.polynom.Polynom;

public class Conductor extends JFrame {

	private static final long serialVersionUID = 3532667781001479319L;

	private static JLabel _eingabeGradLabel;
	private static JLabel _eingabePrimzahlLabelGeneratingPolynom;
	private static JLabel _eingabeMaxAnzahlLabel;
	private static JLabel _eingabeErzeugungspolynomLable;
	private static JLabel _eingabePrimzahlLableField;
	private static JLabel _ausgabeGeneriertePolynomeLabel;
	private static JLabel _ausgabeAlleElementeKoerperLable;
	private static JLabel _ausgabeAdditionsTabelleLable;
	private static JLabel _ausgabeMultiplikationsTabelleLable;
	private static JTextArea _eingabeGrad;
	private static JTextArea _eingabePrimzahlGeneratingPolynom;
	private static JTextArea _eingabeMaxAnzahl;
	private static JTextArea _eingabePrimzahlField;
	private static JEditorPane _ausgabeGeneriertePolynome;
	private static JEditorPane _ausgabeMultiplikationsTabelle;
	private static JEditorPane _ausgabeAdditionsTabelle;
	private static JEditorPane _ausgabeAlleElementeKoerper;
	private static JComboBox<String> _eingabeErzeugungspolynom;
	private static JButton _erzeugePolynomeButton;
	private static JButton _erzeugeKoerperButton;
	private static List<Polynom> _polynome;
	private static Field _field;

	public static void main(String[] args) {
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("Irreduzibles Polynom");
		mainFrame.setSize(800, 600);
		mainFrame.setResizable(false);

		JTabbedPane allTabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		allTabs.addTab("Generatorpolynom", createGeneratorPolynomTab());
		allTabs.addTab("Feld", createFieldTab());

		mainFrame.add(allTabs);
		mainFrame.setVisible(true);
	}

	private static ActionListener createField() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String generatingPoly = (String) _eingabeErzeugungspolynom.getSelectedItem();
				generatingPoly = generatingPoly.substring(1, generatingPoly.length() - 1);
				String[] polynomStringNumbers = generatingPoly.split(",");
				Integer[] polynomNumbers = new Integer[polynomStringNumbers.length];
				for (int i = 0; i < polynomStringNumbers.length; i++) {
					polynomNumbers[i] = Integer.parseInt(polynomStringNumbers[i]);
				}
				int p = Integer.parseInt(_eingabePrimzahlField.getText());
				_field = Field.createField(Polynom.createPolyFromArray(polynomNumbers, p));

				_ausgabeAlleElementeKoerper.setText(_field.getAllElementsAsString());
				_ausgabeAdditionsTabelle.setText(_field.getAddTableAsString().replace("\n", "<br>"));
				_ausgabeMultiplikationsTabelle.setText(_field.getMultiTableAsHtmlString());
			}
		};
	}

	private static ActionListener createPolynom() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int p = Integer.parseInt(_eingabePrimzahlGeneratingPolynom.getText());
				int n = Integer.parseInt(_eingabeGrad.getText());
				int x = Integer.parseInt(_eingabeMaxAnzahl.getText());

				_polynome = Polynom.createXGeneratingPolynome(p, n, x);

				_ausgabeGeneriertePolynome.setText(getPolysHtmlString());

				_polynome.forEach(ele -> _eingabeErzeugungspolynom.addItem(ele.toString()));
				_eingabePrimzahlField.setText(String.valueOf(_polynome.get(0).getModulo()));
			}

		};
	}

	private static String getPolysHtmlString() {
		StringBuilder b = new StringBuilder();
		for (Polynom poly : _polynome) {
			b.append(poly.toString());
			b.append(" = ");
			b.append(poly.toHtmlString());
			b.append("<br>");
		}
		return b.toString();
	}

	private static JPanel createFieldTab() {
		JPanel field = new JPanel();
		field.setLayout(null);

		_erzeugeKoerperButton = new JButton("Erzeuge Körper");
		_erzeugeKoerperButton.setBounds(280, 35, 100, 20);
		_erzeugeKoerperButton.addActionListener(createField());
		field.add(_erzeugeKoerperButton);

		_eingabeErzeugungspolynomLable = new JLabel("Polynomauswahl");
		_eingabeErzeugungspolynomLable.setBounds(40, 15, 100, 25);
		_eingabeErzeugungspolynom = new JComboBox<String>();
		_eingabeErzeugungspolynom.setBounds(40, 35, 150, 25);
		_eingabeErzeugungspolynom.setEditable(true);
		field.add(_eingabeErzeugungspolynomLable);
		field.add(_eingabeErzeugungspolynom);

		_eingabePrimzahlLableField = new JLabel("Primzahl:");
		_eingabePrimzahlLableField.setBounds(200, 15, 100, 25);
		_eingabePrimzahlField = new JTextArea();
		_eingabePrimzahlField.setBounds(200, 35, 55, 22);
		field.add(_eingabePrimzahlLableField);
		field.add(_eingabePrimzahlField);

		_ausgabeAlleElementeKoerperLable = new JLabel("Alle Elemente des Körpers");
		_ausgabeAlleElementeKoerperLable.setBounds(100, 80, 150, 20);
		_ausgabeAlleElementeKoerper = new JEditorPane();
		_ausgabeAlleElementeKoerper.setContentType("text/html");
		JScrollPane scrollPaneElementeField = new JScrollPane(_ausgabeAlleElementeKoerper);
		scrollPaneElementeField.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneElementeField.setBounds(40, 100, 700, 70);
		field.add(_ausgabeAlleElementeKoerperLable);
		field.add(scrollPaneElementeField);

		_ausgabeAdditionsTabelleLable = new JLabel("Additionstabelle");
		_ausgabeAdditionsTabelleLable.setBounds(100, 190, 150, 20);
		_ausgabeAdditionsTabelle = new JEditorPane();
		_ausgabeAdditionsTabelle.setContentType("text/html");
		JScrollPane scrollPaneAdditionsTabelle = new JScrollPane(_ausgabeAdditionsTabelle);
		scrollPaneAdditionsTabelle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneAdditionsTabelle.setBounds(40, 210, 700, 140);
		field.add(_ausgabeAdditionsTabelleLable);
		field.add(scrollPaneAdditionsTabelle);

		_ausgabeMultiplikationsTabelleLable = new JLabel("Multiplikationstabelle");
		_ausgabeMultiplikationsTabelleLable.setBounds(100, 360, 150, 20);
		_ausgabeMultiplikationsTabelle = new JEditorPane();
		_ausgabeMultiplikationsTabelle.setContentType("text/html");
		JScrollPane scrollPaneMultiplikationsTable = new JScrollPane(_ausgabeMultiplikationsTabelle);
		scrollPaneMultiplikationsTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneMultiplikationsTable.setBounds(40, 380, 700, 140);
		field.add(_ausgabeMultiplikationsTabelleLable);
		field.add(scrollPaneMultiplikationsTable);

		return field;
	}

	private static JPanel createGeneratorPolynomTab() {
		JPanel generatorPolynomPanel = new JPanel();
		generatorPolynomPanel.setLayout(null);

		_erzeugePolynomeButton = new JButton("Erzeuge Genpoly");
		_erzeugePolynomeButton.setBounds(340, 35, 100, 20);
		_erzeugePolynomeButton.addActionListener(createPolynom());
		generatorPolynomPanel.add(_erzeugePolynomeButton);

		_eingabeGradLabel = new JLabel("Grad:");
		_eingabeGradLabel.setBounds(40, 15, 100, 25);
		_eingabeGrad = new JTextArea();
		_eingabeGrad.setBounds(40, 35, 65, 22);
		generatorPolynomPanel.add(_eingabeGradLabel);
		generatorPolynomPanel.add(_eingabeGrad);

		_eingabePrimzahlLabelGeneratingPolynom = new JLabel("Primzahl:");
		_eingabePrimzahlLabelGeneratingPolynom.setBounds(160, 15, 100, 25);
		_eingabePrimzahlGeneratingPolynom = new JTextArea();
		_eingabePrimzahlGeneratingPolynom.setBounds(160, 35, 55, 22);
		generatorPolynomPanel.add(_eingabePrimzahlLabelGeneratingPolynom);
		generatorPolynomPanel.add(_eingabePrimzahlGeneratingPolynom);

		_eingabeMaxAnzahlLabel = new JLabel("Anzahl:");
		_eingabeMaxAnzahlLabel.setBounds(280, 15, 100, 20);
		_eingabeMaxAnzahl = new JTextArea();
		_eingabeMaxAnzahl.setBounds(280, 35, 45, 22);
		generatorPolynomPanel.add(_eingabeMaxAnzahlLabel);
		generatorPolynomPanel.add(_eingabeMaxAnzahl);

		_ausgabeGeneriertePolynomeLabel = new JLabel("Anzeige der Generierungspolynome");
		_ausgabeGeneriertePolynomeLabel.setBounds(40, 100, 250, 20);
		_ausgabeGeneriertePolynome = new JEditorPane();
		_ausgabeGeneriertePolynome.setContentType("text/html");
		JScrollPane scrollPaneForAnzeigeGenerierungsPolynome = new JScrollPane(_ausgabeGeneriertePolynome);
		scrollPaneForAnzeigeGenerierungsPolynome.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneForAnzeigeGenerierungsPolynome.setBounds(40, 120, 400, 150);
		generatorPolynomPanel.add(_ausgabeGeneriertePolynomeLabel);
		generatorPolynomPanel.add(scrollPaneForAnzeigeGenerierungsPolynome);

		return generatorPolynomPanel;
	}

}

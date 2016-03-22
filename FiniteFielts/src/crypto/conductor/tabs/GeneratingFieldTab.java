package crypto.conductor.tabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import crypto.conductor.Conductor;
import crypto.field.Field;
import crypto.polynom.Polynom;

public class GeneratingFieldTab {

    protected static JLabel _eingabeErzeugungspolynomLable;
    protected static JLabel _eingabePrimzahlLableField;
    protected static JLabel _ausgabeAlleElementeKoerperLable;
    protected static JLabel _ausgabeAdditionsTabelleLable;
    protected static JLabel _ausgabeMultiplikationsTabelleLable;
    protected static JButton _erzeugeKoerperButton;
    protected static JComboBox<String> _eingabeErzeugungspolynom;
    protected static JEditorPane _ausgabeMultiplikationsTabelle;
    protected static JEditorPane _ausgabeAdditionsTabelle;
    protected static JEditorPane _ausgabeAlleElementeKoerper;
    protected static JTextArea _eingabePrimzahlField;

    public static Field _field;

    public static JPanel createFieldTab() {
        JPanel field = new JPanel();
        field.setLayout(null);

        _erzeugeKoerperButton = new JButton("create Field");
        _erzeugeKoerperButton.setBounds(280, 35, 100, 20);
        _erzeugeKoerperButton.addActionListener(createField());
        field.add(_erzeugeKoerperButton);

        _eingabeErzeugungspolynomLable = new JLabel("Polynomselect");
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

        _ausgabeAlleElementeKoerperLable = new JLabel("All elements from field");
        _ausgabeAlleElementeKoerperLable.setBounds(100, 80, 150, 20);
        _ausgabeAlleElementeKoerper = new JEditorPane();
        _ausgabeAlleElementeKoerper.setContentType("text/html");
        JScrollPane scrollPaneElementeField = new JScrollPane(_ausgabeAlleElementeKoerper);
        scrollPaneElementeField.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneElementeField.setBounds(40, 100, 700, 70);
        field.add(_ausgabeAlleElementeKoerperLable);
        field.add(scrollPaneElementeField);

        _ausgabeAdditionsTabelleLable = new JLabel("Add Table");
        _ausgabeAdditionsTabelleLable.setBounds(100, 190, 150, 20);
        _ausgabeAdditionsTabelle = new JEditorPane();
        _ausgabeAdditionsTabelle.setContentType("text/html");
        JScrollPane scrollPaneAdditionsTabelle = new JScrollPane(_ausgabeAdditionsTabelle);
        scrollPaneAdditionsTabelle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneAdditionsTabelle.setBounds(40, 210, 700, 140);
        field.add(_ausgabeAdditionsTabelleLable);
        field.add(scrollPaneAdditionsTabelle);

        _ausgabeMultiplikationsTabelleLable = new JLabel("Multiply Table");
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

    private static ActionListener createField() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(Conductor._mainFrame, "Illegal argument \n" + ex.getMessage(),
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }
}

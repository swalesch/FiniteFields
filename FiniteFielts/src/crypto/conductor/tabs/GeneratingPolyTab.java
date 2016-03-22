package crypto.conductor.tabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import crypto.conductor.Conductor;
import crypto.polynom.Polynom;

public class GeneratingPolyTab {

    private static JLabel _eingabeGradLabel;

    private static JLabel _eingabeMaxAnzahlLabel;

    private static JLabel _ausgabeGeneriertePolynomeLabel;

    private static JTextArea _eingabeGrad;
    private static JTextArea _eingabePrimzahlGeneratingPolynom;
    private static JTextArea _eingabeMaxAnzahl;
    private static JLabel _eingabePrimzahlLabelGeneratingPolynom;
    private static JEditorPane _ausgabeGeneriertePolynome;

    private static JButton _erzeugePolynomeButton;

    private static List<Polynom> _polynome;

    public static JPanel createGeneratorPolynomTab() {
        JPanel generatorPolynomPanel = new JPanel();
        generatorPolynomPanel.setLayout(null);

        _erzeugePolynomeButton = new JButton("create genpoly.");
        _erzeugePolynomeButton.setBounds(340, 35, 100, 20);
        _erzeugePolynomeButton.addActionListener(createPolynom());
        generatorPolynomPanel.add(_erzeugePolynomeButton);

        _eingabeGradLabel = new JLabel("degree n:");
        _eingabeGradLabel.setBounds(40, 15, 100, 25);
        _eingabeGrad = new JTextArea();
        _eingabeGrad.setBounds(40, 35, 65, 22);
        generatorPolynomPanel.add(_eingabeGradLabel);
        generatorPolynomPanel.add(_eingabeGrad);

        _eingabePrimzahlLabelGeneratingPolynom = new JLabel("prim number p:");
        _eingabePrimzahlLabelGeneratingPolynom.setBounds(160, 15, 100, 25);
        _eingabePrimzahlGeneratingPolynom = new JTextArea();
        _eingabePrimzahlGeneratingPolynom.setBounds(160, 35, 55, 22);
        generatorPolynomPanel.add(_eingabePrimzahlLabelGeneratingPolynom);
        generatorPolynomPanel.add(_eingabePrimzahlGeneratingPolynom);

        _eingabeMaxAnzahlLabel = new JLabel("max count:");
        _eingabeMaxAnzahlLabel.setBounds(280, 15, 100, 20);
        _eingabeMaxAnzahl = new JTextArea();
        _eingabeMaxAnzahl.setBounds(280, 35, 45, 22);
        generatorPolynomPanel.add(_eingabeMaxAnzahlLabel);
        generatorPolynomPanel.add(_eingabeMaxAnzahl);

        _ausgabeGeneriertePolynomeLabel = new JLabel("created polynoms");
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

    private static ActionListener createPolynom() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String primNumber = _eingabePrimzahlGeneratingPolynom.getText().trim();
                String grad = _eingabeGrad.getText().trim();
                String maxCount = _eingabeMaxAnzahl.getText().trim();
                if (isNumber(primNumber) && isNumber(grad) && isNumber(grad)) {
                    int p = Integer.parseInt(primNumber);
                    int n = Integer.parseInt(grad);
                    int x = Integer.parseInt(maxCount);

                    try {
                        _polynome = Polynom.createXGeneratingPolynome(p, n, x);
                        _ausgabeGeneriertePolynome.setText(getPolysHtmlString());
                        _polynome.forEach(ele -> GeneratingFieldTab._eingabeErzeugungspolynom.addItem(ele.toString()));
                        GeneratingFieldTab._eingabePrimzahlField.setText(String.valueOf(_polynome.get(0).getModulo()));
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(Conductor._mainFrame, "Illegal argument \n" + ex.getMessage(),
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
    }

    private static boolean isNumber(String number) {
        return number.matches("\\d+");
    }

    private static String getPolysHtmlString() {
        StringBuilder polynomsAsHtml = new StringBuilder();
        for (Polynom poly : _polynome) {
            polynomsAsHtml.append(poly.toString());
            polynomsAsHtml.append(" = ");
            polynomsAsHtml.append(poly.toHtmlString());
            polynomsAsHtml.append("<br>");
        }
        return polynomsAsHtml.toString();
    }

}

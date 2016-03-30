package crypto.conductor.tabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import crypto.conductor.Conductor;

public class GeneratingECCTab {

    private static JLabel _ellipticCurveParameter;
    private static JLabel _ellipticCurveALable;
    private static JSpinner _ellipticCurveA;
    private static JLabel _ellipticCurveBLable;
    private static JSpinner _ellipticCurveB;

    private static JLabel _ellipticCurvePrimLabel;
    private static JTextArea _ellipticCurvePrim;

    private static JLabel _ellipticCurvePoint;
    private static JLabel _curvePointXLabel;
    private static JSpinner _curvePointX;
    private static JLabel _curvePointYLabel;
    private static JSpinner _curvePointY;
    private static JLabel _ellipticCurveLabel;
    private static JLabel _keyPublicAliceLabel;
    private static JLabel _keyPrivateAliceLabel;
    private static JLabel _keyPublicBobLabel;
    private static JLabel _keyPrivateBobLabel;
    private static JLabel _keySharedLabel;
    private static JLabel _ellipticCurve;
    private static JLabel _keyPublicAlice;
    private static JLabel _keyPrivateAlice;
    private static JLabel _keyPublicBob;
    private static JLabel _keyPrivateBob;
    private static JLabel _keyShared;
    private static JButton _generateKeys;

    public static JPanel createECCTab() {
        JPanel eccPanel = new JPanel();
        eccPanel.setLayout(null);

        _ellipticCurveParameter = new JLabel("Curve Parameters");
        _ellipticCurveParameter.setBounds(40, 15, 200, 20);
        eccPanel.add(_ellipticCurveParameter);

        _ellipticCurveALable = new JLabel("A: ");
        _ellipticCurveALable.setBounds(40, 40, 20, 20);
        eccPanel.add(_ellipticCurveALable);

        _ellipticCurveA = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        _ellipticCurveA.setBounds(60, 40, 100, 20);
        eccPanel.add(_ellipticCurveA);

        _ellipticCurveBLable = new JLabel("B: ");
        _ellipticCurveBLable.setBounds(40, 70, 20, 20);
        eccPanel.add(_ellipticCurveBLable);

        _ellipticCurveB = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        _ellipticCurveB.setBounds(60, 70, 100, 20);
        eccPanel.add(_ellipticCurveB);

        _ellipticCurvePoint = new JLabel("Curve Point");
        _ellipticCurvePoint.setBounds(400, 15, 200, 20);
        eccPanel.add(_ellipticCurvePoint);

        _curvePointXLabel = new JLabel("x: ");
        _curvePointXLabel.setBounds(400, 40, 20, 20);
        eccPanel.add(_curvePointXLabel);

        SpinnerModel nodel = new SpinnerModel() {

            @Override
            public void setValue(Object value) {
                // TODO Auto-generated method stub

            }

            @Override
            public void removeChangeListener(ChangeListener l) {
                // TODO Auto-generated method stub

            }

            @Override
            public Object getValue() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Object getPreviousValue() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Object getNextValue() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void addChangeListener(ChangeListener l) {
                // TODO Auto-generated method stub

            }
        };
        _curvePointX = new JSpinner(nodel);
        _curvePointX.setBounds(420, 40, 100, 20);
        eccPanel.add(_curvePointX);

        _curvePointYLabel = new JLabel("y: ");
        _curvePointYLabel.setBounds(400, 70, 20, 20);
        eccPanel.add(_curvePointYLabel);

        _curvePointY = new JSpinner();
        _curvePointY.setBounds(420, 70, 100, 20);
        eccPanel.add(_curvePointY);

        _ellipticCurvePrimLabel = new JLabel("prim number p:");
        _ellipticCurvePrimLabel.setBounds(200, 15, 100, 25);
        eccPanel.add(_ellipticCurvePrimLabel);
        _ellipticCurvePrim = new JTextArea();
        _ellipticCurvePrim.setBounds(200, 35, 55, 22);
        eccPanel.add(_ellipticCurvePrim);

        _ellipticCurveLabel = new JLabel("<html>y<sup>2</sup> = x<sup>3</sup> + 1x + 0 mod 23</html>");
        _ellipticCurveLabel.setBounds(200, 80, 200, 30);
        eccPanel.add(_ellipticCurveLabel);

        _keyPrivateAliceLabel = new JLabel("Geheimer Schlüssel Alice:");
        _keyPrivateAliceLabel.setBounds(40, 120, 200, 20);
        eccPanel.add(_keyPrivateAliceLabel);

        _keyPrivateBobLabel = new JLabel("Geheimer Schlüssel Bob:");
        _keyPrivateBobLabel.setBounds(400, 120, 200, 20);
        eccPanel.add(_keyPrivateBobLabel);

        _keyPublicAliceLabel = new JLabel("Öffentlicher Schlüssel Alice:");
        _keyPublicAliceLabel.setBounds(40, 200, 200, 20);
        eccPanel.add(_keyPublicAliceLabel);

        _keyPublicBobLabel = new JLabel("Öffentlicher Schlüssel Bob:");
        _keyPublicBobLabel.setBounds(400, 200, 200, 20);
        eccPanel.add(_keyPublicBobLabel);

        _keySharedLabel = new JLabel("Gemeinsamer Schlüssel:");
        _keySharedLabel.setBounds(220, 280, 200, 20);
        eccPanel.add(_keySharedLabel);

        _keyPrivateAlice = new JLabel("");
        _keyPrivateAlice.setBounds(40, 140, 200, 20);
        eccPanel.add(_keyPrivateAlice);

        _keyPrivateBob = new JLabel("");
        _keyPrivateBob.setBounds(400, 140, 200, 20);
        eccPanel.add(_keyPrivateBob);

        _keyPublicAlice = new JLabel("");
        _keyPublicAlice.setBounds(40, 220, 200, 20);
        eccPanel.add(_keyPublicAlice);

        _keyPublicBob = new JLabel("");
        _keyPublicBob.setBounds(400, 220, 200, 20);
        eccPanel.add(_keyPublicBob);

        _keyShared = new JLabel("");
        _keyShared.setBounds(220, 300, 200, 20);
        eccPanel.add(_keyShared);

        _generateKeys = new JButton("Schlüssel berechnen");
        _generateKeys.setBounds(400, 330, 80, 50);
        _generateKeys.addActionListener(generateKeys());
        eccPanel.add(_generateKeys);

        return eccPanel;
    }

    private static ActionListener generateKeys() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(Conductor._mainFrame, "Illegal argument \n" + ex.getMessage(),
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }
}

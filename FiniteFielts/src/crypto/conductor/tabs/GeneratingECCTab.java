package crypto.conductor.tabs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import crypto.conductor.Conductor;
import crypto.ecc.Configuration;

public class GeneratingECCTab {

	private static JLabel _ellipticCurveParameter;
	private static JLabel _ellipticCurveALable;
	private static JSpinner _ellipticCurveASpinner;
	private static JLabel _ellipticCurveBLable;
	private static JSpinner _ellipticCurveBSpinner;

	private static JLabel _ellipticCurvePrimLabel;
	private static JSpinner _ellipticCurvePSpinner;

	private static JLabel _ellipticCurvePoint;
	private static JLabel _ellipticCurvePointXLabel;
	private static JSpinner _ellipticCurvePointXSpinner;
	private static JLabel _ellipticCurvePointYLabel;
	private static JSpinner _ellipticCurvePointYSpinner;
	private static JLabel _ellipticCurveLabel;
	private static JLabel _keyPublicAliceLabel;
	private static JLabel _keyPrivateAliceLabel;
	private static JLabel _keyPublicBobLabel;
	private static JLabel _keyPrivateBobLabel;
	private static JLabel _keySharedLabel;
	private static JLabel _ellipticCurve_Output;
	private static JLabel _keyPublicAlice_Output;
	private static JLabel _keyPrivateAlice_Output;
	private static JLabel _keyPublicBob_Output;
	private static JLabel _keyPrivateBob_Output;
	private static JLabel _keyShared_Output;
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

		_ellipticCurveBLable = new JLabel("B: ");
		_ellipticCurveBLable.setBounds(40, 70, 20, 20);
		eccPanel.add(_ellipticCurveBLable);

		_ellipticCurvePoint = new JLabel("Curve Point");
		_ellipticCurvePoint.setBounds(400, 15, 200, 20);
		eccPanel.add(_ellipticCurvePoint);

		_ellipticCurvePointXLabel = new JLabel("x: ");
		_ellipticCurvePointXLabel.setBounds(400, 40, 20, 20);
		eccPanel.add(_ellipticCurvePointXLabel);

		_ellipticCurvePointYLabel = new JLabel("y: ");
		_ellipticCurvePointYLabel.setBounds(400, 70, 20, 20);
		eccPanel.add(_ellipticCurvePointYLabel);

		_ellipticCurvePrimLabel = new JLabel("prim number p:");
		_ellipticCurvePrimLabel.setBounds(200, 15, 100, 25);
		eccPanel.add(_ellipticCurvePrimLabel);

		_ellipticCurve_Output = new JLabel("<html>y<sup>2</sup> = x<sup>3</sup> + 1x + 0 mod 23</html>");
		_ellipticCurve_Output.setBounds(200, 80, 200, 30);
		eccPanel.add(_ellipticCurve_Output);

		_ellipticCurveASpinner = new JSpinner(InteractionTabECC.modelCurveParamA);
		_ellipticCurveASpinner.setBounds(60, 40, 100, 20);
		_ellipticCurveASpinner.setValue(1);
		eccPanel.add(_ellipticCurveASpinner);

		_ellipticCurveBSpinner = new JSpinner(InteractionTabECC.modelCurveParamB);
		_ellipticCurveBSpinner.setBounds(60, 70, 100, 20);
		eccPanel.add(_ellipticCurveBSpinner);

		_ellipticCurvePointXSpinner = new JSpinner(InteractionTabECC.modelCurvePointX);
		_ellipticCurvePointXSpinner.setBounds(420, 40, 100, 20);
		eccPanel.add(_ellipticCurvePointXSpinner);

		_ellipticCurvePointYSpinner = new JSpinner(InteractionTabECC.modelCurvePointY);
		_ellipticCurvePointYSpinner.setBounds(420, 70, 100, 20);
		eccPanel.add(_ellipticCurvePointYSpinner);

		_ellipticCurvePSpinner = new JSpinner(InteractionTabECC.modelFieldParamP);
		_ellipticCurvePSpinner.setBounds(200, 35, 55, 22);
		eccPanel.add(_ellipticCurvePSpinner);

		_keyPrivateAliceLabel = new JLabel("Geheimer Schl�ssel Alice:");
		_keyPrivateAliceLabel.setBounds(40, 120, 200, 20);
		eccPanel.add(_keyPrivateAliceLabel);

		_keyPrivateBobLabel = new JLabel("Geheimer Schl�ssel Bob:");
		_keyPrivateBobLabel.setBounds(400, 120, 200, 20);
		eccPanel.add(_keyPrivateBobLabel);

		_keyPublicAliceLabel = new JLabel("�ffentlicher Schl�ssel Alice:");
		_keyPublicAliceLabel.setBounds(40, 200, 200, 20);
		eccPanel.add(_keyPublicAliceLabel);

		_keyPublicBobLabel = new JLabel("�ffentlicher Schl�ssel Bob:");
		_keyPublicBobLabel.setBounds(400, 200, 200, 20);
		eccPanel.add(_keyPublicBobLabel);

		_keySharedLabel = new JLabel("Gemeinsamer Schl�ssel:");
		_keySharedLabel.setBounds(220, 280, 200, 20);
		eccPanel.add(_keySharedLabel);

		_keyPrivateAlice_Output = new JLabel("???");
		_keyPrivateAlice_Output.setBounds(40, 140, 200, 20);
		// why does this nothing!?
		_keyPrivateAlice_Output.setBackground(Color.black);
		_keyPrivateAlice_Output.setForeground(Color.black);
		eccPanel.add(_keyPrivateAlice_Output);

		_keyPrivateBob_Output = new JLabel("");
		_keyPrivateBob_Output.setBounds(400, 140, 200, 20);
		eccPanel.add(_keyPrivateBob_Output);

		_keyPublicAlice_Output = new JLabel("");
		_keyPublicAlice_Output.setBounds(40, 220, 200, 20);
		eccPanel.add(_keyPublicAlice_Output);

		_keyPublicBob_Output = new JLabel("");
		_keyPublicBob_Output.setBounds(400, 220, 200, 20);
		eccPanel.add(_keyPublicBob_Output);

		_keyShared_Output = new JLabel("");
		_keyShared_Output.setBounds(220, 300, 200, 20);
		eccPanel.add(_keyShared_Output);

		_generateKeys = new JButton("Schl�ssel berechnen");
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

				}
				catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(Conductor._mainFrame, "Illegal argument \n"
							+ ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}

	public static void updateCurveEquationInGUI() {
		_ellipticCurve_Output.setText(String.format("<html>y<sup>2</sup> = x<sup>3</sup> + %s x + %s mod %s</html>", Configuration._ellipticCurveParamA.toString(), Configuration._ellipticCurveParamB.toString(), Configuration._ellipticCurveParamP.toString()));
		// Code unreachable -> error in line above?
		_ellipticCurve_Output.repaint();

	}

	public static void SetSpinnerValues() {
		_ellipticCurveASpinner.setValue(Configuration._ellipticCurveParamA);
		_ellipticCurveBSpinner.setValue(Configuration._ellipticCurveParamB);
		_ellipticCurvePSpinner.setValue(Configuration._ellipticCurveParamP);
		_ellipticCurvePointXSpinner.setValue(Configuration._ellipticCurvePointX);
		_ellipticCurvePointYSpinner.setValue(Configuration._ellipticCurvePointY);
	}
}

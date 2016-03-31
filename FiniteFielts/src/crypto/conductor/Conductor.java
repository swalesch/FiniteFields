package crypto.conductor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import crypto.conductor.tabs.GeneratingECCTab;
import crypto.conductor.tabs.GeneratingFieldTab;
import crypto.conductor.tabs.GeneratingPolyTab;

public class Conductor extends JFrame {

    private static final long serialVersionUID = 3532667781001479319L;

    public static JFrame _mainFrame;

    public static void main(String[] args) {
        _mainFrame = new JFrame();
        _mainFrame.setTitle("Irreduzible Polynoms");
        _mainFrame.setSize(800, 600);
        _mainFrame.setResizable(false);

        JTabbedPane allTabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

        allTabs.addTab("Generatingpolynom", GeneratingPolyTab.createGeneratorPolynomTab());
        allTabs.addTab("Field", GeneratingFieldTab.createFieldTab());
        allTabs.addTab("Ecc Keys", GeneratingECCTab.createECCTab());

        _mainFrame.add(allTabs);
        _mainFrame.setVisible(true);
        _mainFrame.addWindowListener(getWindowListener());
    }

    private static WindowListener getWindowListener() {
        return new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(JFrame.DISPOSE_ON_CLOSE);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }
        };
    }

}

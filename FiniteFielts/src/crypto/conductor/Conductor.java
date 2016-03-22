package crypto.conductor;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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

        _mainFrame.add(allTabs);
        _mainFrame.setVisible(true);
    }

}

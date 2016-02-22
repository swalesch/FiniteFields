package crypto.conductor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import crypto.polynom.Polynom;
import crypto.time.measurement.TimeIt;
//-----------------------------------------------------------------------------------------
public class Conductor extends javax.swing.JFrame {
	private static final long serialVersionUID = 3532667781001479319L;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private Toolkit t;
	private int _x=0 ,y=0 ,width=800, height=600;
	
// Komponenten--------------------------------------------------------------
	private JTextArea _TextBox;
	private JTextArea _TextBoxDim;
	private JTextArea _XEingabe;
	
	private JTextField _Feld;
	private JTextArea _FeldDim;
	private JTextField _Generatorpoly;
	
	private JButton _Knopf;
	private JButton _KnopfDim;
	
// Menü Komponenten---------------------------------------------------------
	
	private JMenuBar _menubar1;
	
	private JMenu    _menubar1_menu1;
	private JMenu    _menubar1_menu2;
	private JMenu    _menubar1_menu3;
	
	private JMenuItem _menubar1_menu1_menuitem1;
	private JMenuItem _menubar1_menu1_menuitem2;
	private JMenuItem _menubar1_menu1_menuitem3;
	
	private JMenuItem _menubar1_menu2_menuitem1;
	private JMenuItem _menubar1_menu2_menuitem2;
	private JMenuItem _menubar1_menu2_menuitem3;
	
	private JMenuItem _menubar1_menu3_menuitem1;
	private JMenuItem _menubar1_menu3_menuitem2;
	private JMenuItem _menubar1_menu3_menuitem3;
//--------------------------------------------------------------------------	
	public Conductor(){
		t = Toolkit.getDefaultToolkit();
		Dimension d=t.getScreenSize();
		_x = (int) ((d.getWidth() - width) / 2);
		y = (int) ((d.getHeight() - height) /2);
		
		
		setTitle("Irreduzibles Polynom");
		setBounds(_x,y,width,height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.initComponents();
		setVisible(true);
	}
//------------------------------------------------------------------------------------------	
	private void initComponents(){
		this.getContentPane().setLayout(null);
		
// J Komponenten-----------------------------------------------------------------------------
		_TextBox = new JTextArea("Eingabe N");
		_TextBox.setBounds(50,50,100,20);
		
		_TextBoxDim = new JTextArea("Eingabe P");
		_TextBoxDim.setBounds(50,100,100,20);
		
		_XEingabe = new JTextArea("Eingabe X");
		_XEingabe.setBounds(50,150,100,20);
		
		_Generatorpoly = new JTextField();
		_Generatorpoly.setBounds(50,25,100,20);
		_Generatorpoly.setEditable(false);
		
		_Feld = new JTextField();
		_Feld.setBounds(200,50,100,20);
		_Feld.setEditable(false);
		
		_FeldDim = new JTextArea();
		_FeldDim.setBounds(200,100,250,100);
		//_FeldDim.setEditable(false);
		
		_Knopf = new JButton("XGenPoly ");
		_Knopf.setBounds(310,50,100,20);
		
		_KnopfDim = new JButton("All Poly");
		_KnopfDim.setBounds(450, 100, 100, 20);
//-----Menu--------------------------------------------------------------------------------------		
		
		_menubar1 = new JMenuBar();
		
		_menubar1_menu1 = new JMenu("Menü1");
		_menubar1_menu2 = new JMenu("Menü2");
		_menubar1_menu3 = new JMenu("Menü3");
		
		_menubar1_menu1_menuitem1 = new JMenuItem("Item 1/1");
		_menubar1_menu1_menuitem2 = new JMenuItem("Item 1/2");
		_menubar1_menu1_menuitem3 = new JMenuItem("Item 1/3");
		
		_menubar1_menu2_menuitem1 = new JMenuItem("Item 2/1");
		_menubar1_menu2_menuitem2 = new JMenuItem("Item 2/2");
		_menubar1_menu2_menuitem3 = new JMenuItem("Item 2/3");
		
		_menubar1_menu3_menuitem1 = new JMenuItem("Item 3/1");
		_menubar1_menu3_menuitem2 = new JMenuItem("Item 3/2");
		_menubar1_menu3_menuitem3 = new JMenuItem("Item 3/3");
//--------------Anzeige der Komponenten------------------------------------------------------                             
		this.getContentPane().add(_TextBox);
		//this.getContentPane().add(_Generatorpoly);
		this.getContentPane().add(_Feld);
	    this.getContentPane().add(_Knopf);
	    this.getContentPane().add(_TextBoxDim);
	    this.getContentPane().add(_KnopfDim);
	    this.getContentPane().add(_FeldDim);
	    this.getContentPane().add(_XEingabe);

//--------------------------------------------------------------------------------------------
	    _menubar1 = new JMenuBar();
	    
	    _menubar1_menu1 = new JMenu("Men1");
	    _menubar1_menu2 = new JMenu("Men2");
	    _menubar1_menu3 = new JMenu("Men3");
	    
	   _menubar1_menu1_menuitem1 = new JMenuItem("MenuItem 1/1");
	   _menubar1_menu1_menuitem2 = new JMenuItem("MenuItem 1/2");
	   _menubar1_menu1_menuitem3 = new JMenuItem("MenuItem 1/3");
	   
	   _menubar1_menu2_menuitem1 = new JMenuItem("MenuItem 2/1");
	   _menubar1_menu2_menuitem2 = new JMenuItem("MenuItem 2/2");
	   _menubar1_menu2_menuitem3 = new JMenuItem("MenuItem 2/3");
	   
	   _menubar1_menu3_menuitem1 = new JMenuItem("MenuItem 3/1");
	   _menubar1_menu3_menuitem2 = new JMenuItem("MenuItem 3/2");
	   _menubar1_menu3_menuitem3 = new JMenuItem("MenuItem 3/3");
	   
	   _menubar1_menu1.add(_menubar1_menu1_menuitem1);
	   _menubar1_menu1.add(_menubar1_menu1_menuitem2);
	   _menubar1_menu1.add(_menubar1_menu1_menuitem3);
	   
	   _menubar1_menu2.add(_menubar1_menu2_menuitem1);
	   _menubar1_menu2.add(_menubar1_menu2_menuitem2);
	   _menubar1_menu2.add(_menubar1_menu2_menuitem3);
	   
	   _menubar1_menu3.add(_menubar1_menu3_menuitem1);
	   _menubar1_menu3.add(_menubar1_menu3_menuitem2);
	   _menubar1_menu3.add(_menubar1_menu3_menuitem3);
	   
	   _menubar1.add(_menubar1_menu1);
	   _menubar1.add(_menubar1_menu2);
	   _menubar1.add(_menubar1_menu3);
	   
	   this.setJMenuBar(_menubar1);
	   
	   
	    
	     
	    _Knopf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int n=Integer.parseInt(_TextBox.getText());
				int p=Integer.parseInt(_TextBoxDim.getText());
				int x=Integer.parseInt(_TextBoxDim.getText());
				
				_Feld.setText(Polynom.createXGeneratingPolynome(p, n, x).get(0).toString());
			}
		});
	  
	    _KnopfDim.addActionListener(new ActionListener() {
	    	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int p=Integer.parseInt(_TextBoxDim.getText());
				int n=Integer.parseInt(_TextBox.getText());
				
				_FeldDim.setText(Polynom.createAllPolynomes(p, n).get(0).toString());	
				

				
			}	
		});
	}
	
		
//--------------------------------------------------------------------------------------------	

	public static void main(String[] args) {
		// Fi = p^n
		int n=1;
		
		//Swing fenster erstellen
		new Conductor();
		// get generating Polynoms
		List<Integer> prim = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
				73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181,
				191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307,
				311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433,
				439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571,
				577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701,
				709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809);
		for (Integer integer : prim) {

			System.out.print("For p = " + integer + " it took ");

			//List<Polynom> polys = Polynom.createXGeneratingPolynome(integer,n, 100);
			
			List<Polynom> polys = TimeIt.printTime(() -> Polynom.createGeneratingPolynomes(integer, n));
			System.out.print(" to find " + polys.size());
			
			// polys.stream().forEach(System.out::print);
			System.out.println();
		}

		// List<Polynom> generatingPolynoms =
		// Polynom.createGeneratingPolynomes(p, n);
		// System.out.println("Generierungspolynom:");
		// Polynoms.printPolys(generatingPolynoms);
		// Zahlenmenge erzeugen
		// Rechentabelle erstellen

	}
}


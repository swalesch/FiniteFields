package crypto.conductor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import crypto.polynom.Polynom;
import crypto.time.measurement.TimeIt;
//-----------------------------------------------------------------------------------------
public class Conductor extends javax.swing.JFrame {
	private static final long serialVersionUID = 3532667781001479319L;

	private Toolkit t;
	private int x=0 ,y=0 ,width=800, height=600;
	
// Komponenten--------------------------------------------------------------
	private JTextArea TextBox;
	private JTextField Feld;
	private JButton Knopf;
	private JButton Knopf2;
//--------------------------------------------------------------------------	
	public Conductor(){
		t = Toolkit.getDefaultToolkit();
		Dimension d=t.getScreenSize();
		x = (int) ((d.getWidth() - width) / 2);
		y = (int) ((d.getHeight() - height) /2);
		
		
		setTitle("Irreduzibles Polynom");
		setBounds(x,y,width,height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.initComponents();
		setVisible(true);
	}
//------------------------------------------------------------------------------------------	
	private void initComponents(){
		this.getContentPane().setLayout(null);
		
// J Komponenten-----------------------------------------------------------------------------
		this.TextBox = new JTextArea();
		this.TextBox.setBounds(50,50,100,200);
		
		this.Feld = new JTextField();
		this.Feld.setBounds(200,50,100,200);
		
		this.Knopf = new JButton("Knopf ");
		this.Knopf.setBounds(300,50,100,50);
		
		this.Knopf2 = new JButton("Knopf ");
		this.Knopf2.setBounds(300,100,100,50);
//-------------------------------------------------------------------------------------------		

//--------------Anzeige der Komponenten------------------------------------------------------                             
		this.getContentPane().add(this.TextBox);
		this.getContentPane().add(this.Feld);
	    this.getContentPane().add(this.Knopf);
	    this.getContentPane().add(this.Knopf2);
	    
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

			// List<Polynom> polys = Polynom.createXGeneratingPolynome(integer,
			// n, 100);
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


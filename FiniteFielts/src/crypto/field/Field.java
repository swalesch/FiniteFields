package crypto.field;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import crypto.polynom.Polynom;
import crypto.polynom.VectorPolynom;

public class Field {

	private final Polynom GENERATING_POLYNOM;
	private List<Polynom> _allElements;
	private List<Polynom> _multiplyElements;
	private List<List<Polynom>> _addTable;
	private List<List<Polynom>> _multiTable;

	/**
	 * @param generatingPolynom
	 *            has to be a polynom without 0 points
	 */
	public static Field createField(Polynom generatingPolynom) {
		Preconditions.checkArgument(!generatingPolynom.hasNullpoints(), "generatingPolynom has at least one null point");
		List<Polynom> allElements = Polynom.createAllPolynomes(generatingPolynom.getModulo(),
				generatingPolynom.getVector().getDegree());
		Field field = new Field(allElements, generatingPolynom);

		return field;

	}

	public boolean isPointInField(Polynom a) {
		int i = findElementInAll(a);
		if (i == -1) {
			return false;
		}
		return true;
	}

	public Polynom addPoints(Polynom a, Polynom b) {
		int i = findElementInAll(a);
		int j = findElementInAll(b);
		Preconditions.checkArgument(i != -1, "Point " + a + " is not in Field.");
		Preconditions.checkArgument(j != -1, "Point " + b + " is not in Field.");
		return _addTable.get(i).get(j);
	}

	public Polynom multiplyPoints(Polynom a, Polynom b) {
		int i = findElementInMulti(a);
		int j = findElementInMulti(b);
		Preconditions.checkArgument(i != -1, "Point " + a + " is not in Field.");
		Preconditions.checkArgument(j != -1, "Point " + b + " is not in Field.");
		return _multiTable.get(i).get(j);
	}

	@Override
	public String toString() {
		String string = "all Elements:\n" + _allElements.toString();
		string += "\n\nadd Table:\n";
		int Genlength = GENERATING_POLYNOM.toString().length();
		for (int i = 0; i < _addTable.get(0).size(); i++) {
			for (int j = 0; j < _addTable.size(); j++) {
				String vector = _addTable.get(j).get(i).toString();
				if(vector.length()<=Genlength-2){
					String help="";
					int neededSpaces = Genlength-2-vector.length();
					for (int k = 0; k < neededSpaces; k++) {
						help+=" ";
					}
					vector = help+vector;
				}
				string += vector + "  ";
			}
			string += "\n";
		}

		string += "\nmulti Elements:\n";
		string += _multiplyElements.toString();
		string += "\n\nmulti Table:\n";
		for (int i = 0; i < _multiTable.get(0).size(); i++) {
			for (int j = 0; j < _multiTable.size(); j++) {
				
				String vector = _multiTable.get(j).get(i).toString();
				if(vector.length()<=Genlength-2){
					String help="";
					int neededSpaces = Genlength-2-vector.length();
					for (int k = 0; k < neededSpaces; k++) {
						help+=" ";
					}
					vector = help+vector;
				}
				string += vector + "  ";
			}
			string += "\n";
		}
		return string;
	}

	private static List<List<Polynom>> initTable(int size) {
		ArrayList<List<Polynom>> table = new ArrayList<List<Polynom>>();

		for (int i = 0; i < size; i++) {
			table.add(new ArrayList<Polynom>());
		}
		return table;
	}

	private int findElementInAll(Polynom ele) {
		for (int i = 0; i < _allElements.size(); i++) {
			if (_allElements.get(i).equals(ele)) {
				return i;
			}
		}
		return -1;
	}

	private int findElementInMulti(Polynom ele) {
		VectorPolynom vector = ele.getVector();
		Preconditions.checkArgument(!vector.equals(_allElements.get(0)), " Zero ist not part of multiply operation.");
		for (int i = 0; i < _multiplyElements.size(); i++) {
			if (_multiplyElements.get(i).equals(ele)) {
				return i;
			}
		}
		return -1;
	}

	private void createAddTable() {
		_addTable = initTable(_allElements.size());
		int i = 0;
		for (Polynom a : _allElements) {
			for (Polynom b : _allElements) {
				_addTable.get(i).add(a.calculateAddPolynom(b));
				i++;
			}
			i = 0;
		}
	}

	private void createMultiTable() {
		_multiTable = initTable(_multiplyElements.size());
		int i = 0;
		for (Polynom a : _multiplyElements) {
			for (Polynom b : _multiplyElements) {
				_multiTable.get(i).add(a.calculateMultiplyPolynom(b).calculateDividePolynomRest(GENERATING_POLYNOM));
				i++;
			}
			i = 0;
		}
	}

	private Field(List<Polynom> allElements, Polynom generatingPolynom) {
		Preconditions.checkArgument(!allElements.isEmpty());

		GENERATING_POLYNOM = generatingPolynom;
		_allElements = allElements;
		createAddTable();

		_multiplyElements = allElements.subList(1, allElements.size());
		createMultiTable();
	}

}

import java.util.*;
import java.io.*;

public class Day11 {
	public static void main(String[] args) {
		CorrectLounge wait = new CorrectLounge("./Day11-input.txt");
		// CorrectLounge wait = new CorrectLounge("./gija.txt");
		// CorrectLounge wait = new CorrectLounge("./zeno.txt");
		wait.stabilise();
		System.out.printf("# people sitting: <<%d>>\n",wait.numOccupied());
	}
}

class Lounge {
	protected ArrayList<ArrayList<Character>> seats;
	protected String start;
	protected ArrayList<ArrayList<Character>> prevSeats;
	
	public Lounge(String path) {
		start = path;
		seats = new ArrayList<ArrayList<Character>>();
		prevSeats = new ArrayList<ArrayList<Character>>();
		try {
			File fp = new File(path);
			Scanner sc = new Scanner(fp);
			int row = 0;
			String line;
			do {
				line = sc.nextLine();
				seats.add(new ArrayList<Character>());
				for(int i=0; i<line.length(); ++i)
					seats.get(row).add(line.charAt(i));
				++row;
			} while(sc.hasNextLine());
		} catch(FileNotFoundException e) {
			System.out.println("file not found!!");
			System.exit(1);
		}
	}
	
	public void print() {
		for(int i=0; i< seats.size(); ++i) {
			for(int j=0; j<seats.get(0).size(); ++j) {
				System.out.print(seats.get(i).get(j));
			}
			System.out.println("");
		}
	}
	
	public void printPrev() {
		for(int i=0; i< prevSeats.size(); ++i) {
			for(int j=0; j<prevSeats.get(0).size(); ++j) {
				System.out.print(prevSeats.get(i).get(j));
			}
			System.out.println("");
		}
	}
	
	public void update() {
		ArrayList<ArrayList<Character>> seatsCopy = new ArrayList<ArrayList<Character>>();
		prevSeats = new ArrayList<ArrayList<Character>>();
		for(int i=0; i<seats.size(); ++i) {
			seatsCopy.add(new ArrayList<Character>());
			prevSeats.add(new ArrayList<Character>());
			for(int j=0; j<seats.get(0).size(); ++j) {
				seatsCopy.get(i).add(seats.get(i).get(j));
				prevSeats.get(i).add(seats.get(i).get(j));
			}
		}
		int nbhd;
		for(int i=0; i<seats.size(); ++i) {
			for(int j=0; j<seats.get(0).size(); ++j) {
				if(seats.get(i).get(j) == '.')
					continue;
				nbhd = numNeighbours(i,j);
				if(nbhd == 0)
					seatsCopy.get(i).set(j,'#');
				else if(nbhd > 3)
					seatsCopy.get(i).set(j,'L');
			}
		}
		seats = seatsCopy;
	}
	
	public int numNeighbours(int i, int j) {
		int result = 0;
		int[] adjArr = new int[] {-1,0,1};
		for(int di: adjArr) {
			for(int dj: adjArr) {
				if(di == 0 && dj == 0)
					continue;
				try {
					result += seats.get(i+di).get(j+dj) == '#' ? 1:0;
				} catch(IndexOutOfBoundsException e) {}
			}
		}
		return result;
	}
	
	public boolean equalsPrev() {
		if(prevSeats.size() != this.seats.size() ||
				prevSeats.get(0).size() != this.seats.get(0).size())
			return false;
		return this.seats.equals(prevSeats);
	}
	
	public int numOccupied() {
		int result = 0;
		for(int i=0; i<seats.size(); ++i)
			for(int j=0; j<seats.get(0).size(); ++j)
				if(seats.get(i).get(j) == '#')
					++result;
		return result;
	}
	
	public void stabilise() {
		this.update();
		while(!this.equalsPrev()) {
			this.update();
		}
	}
}

class CorrectLounge extends Lounge {
	public CorrectLounge(String path) {
		super(path);
	}
	
	public int numNeighbours(int i, int j) {
		int result = 0;
		int[][] directions = new int[][] {
			{-1,-1},{0,-1},{1,-1},
			{-1,0}		,{1,0},
			{-1,1},{0,1},{1,1}
		};
		int tmpi, tmpj;
		char space;
		for(int[] dir: directions) {
			tmpi = i + dir[0];
			tmpj = j + dir[1];
			while(
					0 <= tmpi &&
					0 <= tmpj &&
					tmpi < seats.size() &&
					tmpj < seats.get(0).size()
			) {
				space = seats.get(tmpi).get(tmpj);
				if(space == 'L')
					break;
				if(space == '#') {
					++result;
					break;
				}
				tmpi += dir[0];
				tmpj += dir[1];
			}
		}
		return result;
	}
	
	public void update() {
		ArrayList<ArrayList<Character>> seatsCopy = new ArrayList<ArrayList<Character>>();
		prevSeats = new ArrayList<ArrayList<Character>>();
		for(int i=0; i<seats.size(); ++i) {
			seatsCopy.add(new ArrayList<Character>());
			prevSeats.add(new ArrayList<Character>());
			for(int j=0; j<seats.get(0).size(); ++j) {
				seatsCopy.get(i).add(seats.get(i).get(j));
				prevSeats.get(i).add(seats.get(i).get(j));
			}
		}
		int nbhd;
		for(int i=0; i<seats.size(); ++i) {
			for(int j=0; j<seats.get(0).size(); ++j) {
				if(seats.get(i).get(j) == '.')
					continue;
				nbhd = numNeighbours(i,j);
				if(nbhd == 0)
					seatsCopy.get(i).set(j,'#');
				else if(nbhd > 4)
					seatsCopy.get(i).set(j,'L');
			}
		}
		seats = seatsCopy;
	}
}

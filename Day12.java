import java.util.*;
import java.io.*;
import java.lang.Math;
import java.util.regex.*;

class Day12 {
	public static void main(String[] args) {
		Ship b = new Ship();
		b.runFile("./zeno.txt");
		System.out.println(b.manhattan());
	}
	
	public static int mod(int n, int d) {
		while(n < 0)
			n += d;
		return n%d;
	}
}

class Boat {
	public int dir;
	public int x;
	public int y;
	public final char[] dirToCompass = new char[] {'E','S','W','N'};
	
	public Boat() {
		x = 0;
		y = 0;
		dir = 0;
	}
	
	public void print() {
		System.out.printf(
			"Position: (%d,%d), Orientation: %c\n",
			x,y,
			dirToCompass[dir]
		);
	}
	
	public void runCommand(char type,int value) {
		switch(type) {
			case 'F':
				runCommand(dirToCompass[dir],value);
				break;
			case 'L':
				dir = Day12.mod(dir-(value/90),4);
				break;
			case 'R':
				dir = Day12.mod(dir+(value/90),4);
				break;
			case 'N':
				y += value;
				break;
			case 'E':
				x -= value;
				break;
			case 'S':
				y -= value;
				break;
			case 'W':
				x += value;
				break;
			default:
				System.out.println("oops!");
		}
	}
	
	public void runFile(String path) {
		try {
			File fp = new File(path);
			Scanner scan = new Scanner(fp);
			Pattern p = Pattern.compile("([A-Z]{1})(\\d+)");
			Matcher m;
			do {
				m = p.matcher(scan.nextLine());
				if(m.matches()) {
					runCommand(
						m.group(1).charAt(0),
						Integer.parseInt(m.group(2))
					);
				}
			} while(scan.hasNextLine());
		} catch(FileNotFoundException e) {
			System.out.println("oops.");
		}
	}
	
	public int manhattan() {
		return Math.abs(x) + Math.abs(y);
	}
}


class Vec {
	public int x;
	public int y;
	
	public Vec(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(Vec other, int n) {
		x += other.x * n;
		y += other.y * n;
	}
	
	public void left(int n) {
		int tmp;
		switch(Day12.mod(n,4)) {
			case 1:
				tmp = x;
				x = -y;
				y = tmp;
				break;
			case 2:
				x *= -1;
				y *= -1;
				break;
			case 3:
				tmp = x;
				x = y;
				y = -tmp;
			default:
				break;
		}
	}
	
	public void right(int n) {
		this.left(3*n);
	}
	
	public String toString() {
		return String.format("(%d,%d)",x,y);
	}
}

class Ship {
	public Vec position;
	public Vec waypoint;
	
	public Ship() {
		position = new Vec(0,0);
		waypoint = new Vec(10,1);
	}
	
	public void runCommand(char type, int value) {
		switch(type) {
			case 'F':
				position.add(waypoint,value);
				break;
			case 'L':
				waypoint.left(value);
				break;
			case 'R':
				waypoint.right(value);
				break;
			case 'E':
				waypoint.x += value;
				break;
			case 'S':
				waypoint.y -= value;
				break;
			case 'W':
				waypoint.x -= value;
				break;
			case 'N':
				waypoint.y += value;
				break;
			default:
				System.out.println("oops!");
		}
		System.out.println(position.toString()); // debug
	}
	
	public void runFile(String path) {
		try {
			File fp = new File(path);
			Scanner scan =new Scanner(fp);
			Pattern p = Pattern.compile("([A-Z]{1})(\\d+)");
			Matcher m;
			do {
				m = p.matcher(scan.nextLine());
				if(m.matches())
					runCommand(m.group(1).charAt(0),Integer.parseInt(m.group(2)));
			} while(scan.hasNextLine());
		} catch(FileNotFoundException e) {
			System.out.println("oops!");
			System.exit(1);
		}
	}
	
	public int manhattan() {
		return Math.abs(position.x) + Math.abs(position.y);
	}
}
	

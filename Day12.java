import java.lang.Math;
import java.util.*;
import java.io.*;
import java.util.regex.*;

class Day12 {
	public static void main(String[] args) throws FileNotFoundException {
		Boat b = new Boat();
		b.voyage("./Day12-input.txt");
		System.out.println(b.manhattan());
	}
}

class Boat {
	private int x;
	private int y;
	private int angle;
		// anti-clockwise angle the boat's direction makes with East
	private final Pattern p = Pattern.compile("([A-Z])(\\d+)");
	private Matcher m;
	
	public Boat() {
		x = 0;
		y = 0;
		angle = 0;
	}
	
	public int manhattan() {
		return Math.abs(x)+Math.abs(y);
	}
	
	public void move(String instruction) {
		m = p.matcher(instruction);
		if(!m.matches())
			return;
		int arg = Integer.parseInt(m.group(2));
		switch(m.group(1)) {
			case "L":
				angle -= arg;
				break;
			case "R":
				angle += arg;
				break;
			case "N":
				y += arg;
				break;
			case "E":
				x += arg;
				break;
			case "S":
				y -= arg;
				break;
			case "W":
				x -= arg;
				break;
			case "F":
				switch(angle%360) {
					case 0:
						x += arg;
						break;
					case 90:
						y += arg;
						break;
					case 180:
						x -= arg;
					case 270:
						y-= arg;
						break;
				}
				break;
			default:
				System.out.println("that's not good");
		}
	}
	
	public void voyage(String path) {
		try {
			File fp = new File(path);
			Scanner scan = new Scanner(fp);
			do {
				move(scan.nextLine());
			} while(scan.hasNextLine());
		} catch(FileNotFoundException e) {}
	}
}

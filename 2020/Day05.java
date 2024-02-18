import java.io.*;
import java.util.Scanner;


public class Day5 {
	public static void main(String[] args) {
		// System.out.println("Hello!");
		System.out.println(mySeatID());
	}

	public static int seatID(String passCode) {
		int lbound, ubound, middle;
		int index = 0;
		int row = -1;
		lbound = 0;
		ubound = 127;
		while(index<7) {
			middle = (lbound+ubound) / 2;
			if(passCode.charAt(index) == 'B') {
				lbound = middle + 1;
			} else if(passCode.charAt(index) == 'F') {
				ubound = middle;
			}
			if(ubound == lbound) {
				row = ubound;
				break;
			}
			++index;
		}
		++index;
		int column = -1;
		lbound = 0;
		ubound = 7;
		while(index<10) {
			middle = (lbound+ubound) / 2;
			if(passCode.charAt(index) == 'R') {
				lbound = middle + 1;
			} else if(passCode.charAt(index) == 'L') {
				ubound = middle;
			}
			if(ubound == lbound) {
				column = ubound;
				break;
			}
			++index;
		}
		return 8*row + column;
	}

	public static int maxSeatID() {
		try {
			File fp = new File("./Day5-input.txt");
			Scanner scan = new Scanner(fp);
			int currentMax  = -5;
			int tmpID;
			while(scan.hasNextLine()) {
				tmpID = seatID(scan.nextLine());
				if(tmpID > currentMax) {
					currentMax = tmpID;
				}
			}
			return currentMax;
		} catch(FileNotFoundException e) {
			System.out.println("No file buddy!");
			return -1;
		}
	}

	public static int mySeatID() {
		try {
			File fp = new File("./Day5-input.txt");
			Scanner scan = new Scanner(fp);
			int tmpID;
			boolean[] isAccountedFor = new boolean[1024];
			while(scan.hasNextLine()) {
				isAccountedFor[seatID(scan.nextLine())] = true;
			}
			int myID = 0;
			while(!isAccountedFor[myID]) {
				++myID;
			}
			while(isAccountedFor[myID]) {
				++myID;
			}
			return myID;
		} catch(FileNotFoundException e) { return -1; }
	}
}

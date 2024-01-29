import java.util.*;
import java.util.stream.*;
import java.io.*;

class Day13 {
	public static void main(String[] args) {
		System.out.println(part2("Day13-input.txt"));
	}
	
	public static Scanner getResource(String path) {
		try {
			return new Scanner(new File(path));
		} catch(FileNotFoundException e) {
			System.out.println("Well, that's bad");
		}
		return null;
	}
	
	public static void part1(String path) {
		Scanner scan = getResource(path);
		int min = Integer.parseInt(scan.nextLine());
		int[] periods = Arrays.stream(scan.nextLine().split(","))
			.filter(x -> !x.equals("x"))
			.mapToInt(Integer::parseInt)
			.toArray();
		int minWait = waitTime(periods[0],min);
		int minWaitPeriod = periods[0];
		for(int i=1; i<periods.length; i++) {
			int wait = waitTime(periods[i],min);
			if(wait < minWait) {
				minWait = wait;
				minWaitPeriod = periods[i];
			}
		}
		System.out.println(minWaitPeriod * minWait);
	}
	
	public static int waitTime(int id, int time) {
		int mod = time%id;
		if(mod == 0)
			return 0;
		else
			return id - mod;
	}

	public static long remainder(long cond,long modulus) {
		if(cond == 0) {
			return 0;
		} else {
			return modulus - (cond%modulus);
		}
	}
	
	public static long part2(String path) {
		long result = 0, jump = 1;
		Scanner fp = getResource(path);
		fp.nextLine();
		String[] data = fp.nextLine().split(",");
		long busID;
		for(int i=0; i<data.length; i++) {
			if(data[i].equals("x")) {
				continue;
			}
			busID = Long.parseLong(data[i]);
			while(result%busID != remainder(i,busID)) {
				result += jump;
			}
			jump *= busID;
		}
		return result;
	}
}

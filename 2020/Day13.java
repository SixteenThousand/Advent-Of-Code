// for anyone reading this, I hate this code too. I really do.
// the reason it's like this is that the only "nice" way to solve part 2
// would be to create a general modular congruences solver, which is way more
// work than is reasonable for an exercise
// I'm sorry
import java.util.*;
import java.util.stream.*;
import java.io.*;

class Day13 {
	public static void main(String[] args) {
		System.out.println(part2("./Day13-input.txt"));
	}
	
	public static void part1() {
		try {
			File fp = new File("./Day13-input.txt");
			Scanner scan = new Scanner(fp);
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
		} catch(FileNotFoundException e) {
			System.out.println("darn.");
			System.exit(1);
		}
	}
	
	public static int waitTime(int id, int time) {
		int mod = time%id;
		if(mod == 0)
			return 0;
		else
			return id - mod;
	}
	
	public static int getID(String s) {
		return s.equals("x") ? -1 : Integer.parseInt(s);
	}
	
	public static long part2_v2(String path) {
		try {
			// get the data!
			File fp = new File(path);
			Scanner scan = new Scanner(fp);
			scan.nextLine();
			String[] rawData = scan.nextLine().split(",");
			List<Long> ids = new ArrayList<Long>();
			List<Long> times = new ArrayList<Long>();
			ids.add(Long.parseLong(rawData[0]));
			final long ZERO = 0;
			times.add(ZERO);
			long tmpTime = 1, tmpID;
			for(int i=1; i<rawData.length; i++) {
				if(!rawData[i].equals("x")) {
					tmpID = Long.parseLong(rawData[i]);
					ids.add(tmpID);
					times.add(tmpID - tmpTime);
				}
				tmpTime++;
			}
			
			long maxTime = 1;
			for(long id: ids) {
				maxTime *= id;
			}
			
			List<Long> sieve = new ArrayList<Long>();
			tmpTime = 0;
			while(tmpTime <= maxTime) {
				sieve.add(tmpTime);
				tmpTime += ids.get(0);
				System.out.println("processing");
			}
			
			List<Long> toKeep = new ArrayList<Long>();
			System.out.println(times.get(1));
			
			for(int i=1; i<ids.size(); i++) {
				for(long x: sieve) {
					if(x%ids.get(i) == times.get(i))
						toKeep.add(x);
				}
				sieve.retainAll(toKeep);
				toKeep.clear();
			}
			return sieve.get(0);
		} catch(FileNotFoundException e) {
			System.out.println("oops");
			System.exit(1);
		}
		return -1;
	}
	
	public static long part2(String path) {
		try {
			File fp =new File(path);
			Scanner scan = new Scanner(fp);
			scan.nextLine();
			String[] data = scan.nextLine().split(",");
			List<Cong> buses = new ArrayList<Cong>();
			for(int i=0; i<data.length; i++) {
				if(!data[i].equals("x")) {
					long id = Long.parseLong(data[i]);
					buses.add(new Cong(id,i == 0 ? 0 : id-i));
				}
			}
			// %$!& it, let's just do it by hand
			System.out.println(buses.toString());
			return 0;
		} catch(FileNotFoundException e) {
			System.out.println("darn.");
			System.exit(1);
		}
		return -1;
	}
	
	public static long crt(List<Long> moduli, List<Long> residues) {
		long minSoln = residues.get(0);
		long solnsGap = moduli.get(0);
		for(int i=1; i<moduli.size(); i++) {
			while(minSoln%moduli.get(i) != residues.get(i)) {
				minSoln += solnsGap;
			}
			solnsGap *= moduli.get(i);
		}
		return minSoln;
	}
}


class Cong implements Comparable<Cong> {
	public long modulus;
	public long residue;
	
	public Cong(long modulus, long residue) {
		this.modulus = modulus;
		this.residue = residue;
	}
	
	public String toString() {
		return String.format("Mod: %d, Res: %d");
	}
	
	public int compareTo(Cong that) {
		if(this.modulus > that.modulus) {
			return -1;
		}
		if(this.modulus == that.modulus && this.residue > that.residue) {
			return -1;
		}
		if(this.modulus == that.modulus && this.residue == that.residue) {
			return 0;
		}
		return 1;
	}
}


class CRT {
	public Cong[] congruences;
	
	public CRT(List<Cong> congruences) {
		Cong[] congs = new Cong[congruences.size()];
		Arrays.sort(congruences.toArray(congs));
		this.congruences = congs;
	}
	
	public long solve() {
		long minSoln = congruences[0].residue;
		long solnsGap = congruences[0].modulus;
		for(int i=1; i<congruences.length; i++) {
			while(minSoln%congruences[i].modulus != congruences[i].residue) {
				minSoln += solnsGap;
			}
			solnsGap *= congruences[i].modulus;
		}
		return minSoln;
	}
}

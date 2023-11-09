import java.util.*;
import java.util.stream.*;
import java.io.*;

class Bus implements Comparable<Bus> {
	public int id;
	public int time;
	
	public Bus(int id, int time) {
		this.id = id;
		this.time = time;
	}
	
	public int compareTo(Bus that) {
		int tmp = this.id - that.id;
		return tmp == 0 ? this.time - that.time : tmp;
	}
	
	public String toString() {
		return String.format("{id=%d, time=%d}",id,time);
	}
}


class Day13 {
	public static void main(String[] args) {
		System.out.println(part2());
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
	
	public static long part2() {
		try {
			File fp = new File("./zeno.txt");
			Scanner scan = new Scanner(fp);
			scan.nextLine();
			int[] data = Arrays.stream(scan.nextLine().split(","))
				.mapToInt(Day13::getID)
				.toArray();
			List<Bus> busesList = new ArrayList<Bus>();
			for(int i=0; i<data.length; i++) {
				if(data[i] != -1)
					busesList.add(new Bus(data[i],i));
			}
			Bus[] buses = new Bus[busesList.size()];
			busesList.toArray(buses);
			Arrays.sort(buses);
			System.out.println(Arrays.toString(buses));
			
			List<Long> times = new ArrayList<Long>();
			long maxTime = 1;
			for(int i=0; i<buses.length; i++) {
				maxTime *= buses[i].id;
			}
			long time = 0;
			while(time <= maxTime) {
				times.add(time);
				time += buses[buses.length-1].id;
			}
			
			boolean isResult;
			for(int i=0; i<times.size(); i++) {
				isResult = true;
				for(int j=0; j<buses.length-1; j++) {
					if(times.get(i)%buses[j].id != buses[j].time) {
						isResult = false;
						break;
					}
				}
				if(isResult)
					return times.get(i);
			}
		} catch(FileNotFoundException e) {
			System.exit(1);
		}
		return -1;
	}
}

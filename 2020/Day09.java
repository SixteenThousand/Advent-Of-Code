import java.util.*;
import java.io.*;


class Day9 {
	private static final int N = 32321523;
	
	public static void main(String[] args) {
		System.out.println(part2().toString());
		long sum = 0;
		for(long x: part2())
			sum += x;
		System.out.println(sum);
		List<Long> arr = part2();
		long min = arr.get(0), max = arr.get(0);
		for(long x: arr) {
			if(x < min)
				min = x;
			if(x > max)
				max = x;
		}
		System.out.println(min+max);
	}
	
	public static int part1() {
		try {
			File fp = new File("./Day9-input.txt");
			Scanner scan = new Scanner(fp);
			List<Integer> last25 = new ArrayList<Integer>();
			int current;
			boolean isValid;
			for(int i=0; i<25; ++i) {
				last25.add(Integer.parseInt(scan.nextLine()));
			}
			do {
				current = Integer.parseInt(scan.nextLine());
				isValid = false;
				for(int x: last25) {
					if(last25.contains(current-x)) {
						isValid = true;
						break;
					}
				}
				if(!isValid) {
					return current;
				}
				last25.remove(0);
				last25.add(current);
			} while(scan.hasNextLine());
		} catch(FileNotFoundException e) {}
		return -1;
	}
	
	public static List<Long> part2() {
		try {
			File fp = new File("./Day9-input.txt");
			Scanner scan = new Scanner(fp);
			List<Long> nums = new ArrayList<Long>();
			do {
				nums.add(Long.parseLong(scan.nextLine()));
			} while(scan.hasNextLine());
			
			int lowIndex = 0, highIndex = 0;
			long sum = 0;
			for(int i=0; i<nums.size(); ++i) {
				sum += nums.get(i);
				++highIndex;
				while(sum > N) {
					sum -= nums.get(lowIndex);
					++lowIndex;
				}
				if(sum == N) {
					return nums.subList(lowIndex,highIndex);
				}
			}
		} catch(FileNotFoundException e) {}
		return new ArrayList<Long>();
	}
}

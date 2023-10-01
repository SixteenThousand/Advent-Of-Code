import java.util.*;
import java.io.*;
import java.math.BigInteger;

class Day10 {
	public static void main(String[] args) {
		String s = "./Day10-input.txt";
		String t = "./gija.txt";
		String u = "./zeno.txt";
		part2 p2 = new part2(s);
		System.out.println(p2.numCombinations(0));
	}
	
	public static int[] parseFile() {
		try {
			String s = "./Day10-input.txt";
			String t = "./gija.txt";
			String u = "./zeno.txt";
			File fp = new File(t);
			Scanner scan = new Scanner(fp);
			List<Integer> values = new ArrayList<Integer>();
			do {
				values.add(Integer.parseInt(scan.nextLine()));
			} while(scan.hasNextLine());
			int[] result = new int[values.size()];
			for(int i=0; i<values.size(); ++i) {
				result[i] = values.get(i);
			}
			Arrays.sort(result);
			return result;
		} catch(FileNotFoundException e) {}
		return new int[] {-1,-1,-1,-1};
	}
	
	public static int[] part1() {
		int[] adapters = parseFile();
		int[] result = new int[] {0,0,0,0};
		++result[adapters[0] - 0];
		int diff;
		for(int i=1; i<adapters.length; ++i) {
			diff = adapters[i] - adapters[i-1];
			if(diff > 3) {
				System.out.println("oops I'm doing this very wrong!");
			}
			++result[diff];
		}
		++result[3];
		return result;
	}
}

class part2 {
	private Integer[] adapters;
	private Map<Integer,Long> memo;
	
	public part2(String path) {
		memo = new HashMap<Integer,Long>();
		try {
			File fp = new File(path);
			Scanner data = new Scanner(fp);
			List<Integer> tmpAdapters = new ArrayList<Integer>();
			tmpAdapters.add(0);
			do {
				tmpAdapters.add(Integer.parseInt(data.nextLine()));
			} while(data.hasNextLine());
			adapters = new Integer[tmpAdapters.size()];
			tmpAdapters.toArray(adapters);
			Arrays.sort(adapters);
		} catch(FileNotFoundException e) {}
	}
	
	public long numCombinations(int startIndex) {
		if(startIndex == adapters.length-1) {
			memo.add(startIndex,1);
			return 1;
		}
		if(memo.containsKey(startIndex)) {
			return memo.get(startIndex);
		}
		int i = startIndex+1;
		long result = 0;
		while(i<adapters.length &&
				adapters[i] - adapters[startIndex] < 4
		) {
			result += numCombinations(i);
			++i;
		}
		memo.add(startIndex,result);
		return result;
	}

	public boolean hasAdapter(int joltage) {
		int low = 0, high = adapters.length-1;
		int mid;
		while(low <= high) {
			mid = (low + high) / 2;
			if(adapters[mid] < joltage) {
				low = mid+1;
			} else if(adapters[mid] > joltage) {
				high = mid-1;
			} else if(adapters[mid] == joltage) {
				return true;
			}
		}
		return false;
	}

	// does not work; fundamental idea flawed
	public long numCombs() {
		System.out.printf("debug; adapters: %s\n",Arrays.toString(adapters));
		long result = 1;
		int maxDiv = 1;
		//maximum diversion length
		int index = 0;
		while(index < adapters.length-1) {
			while(
					index+maxDiv < adapters.length &&
					adapters[index+maxDiv] - adapters[index] < 4
			) {
				++maxDiv;
			}
			--maxDiv;
			index += maxDiv;
			--maxDiv;
			result *= Math.pow(2,maxDiv);
			maxDiv = 0;
		}
		return result;
	}
}

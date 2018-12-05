package dag4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dag4 {
	static List<String> log = new ArrayList<String>();
	static GuardSleep onDuty = null;
	static HashMap<Integer, GuardSleep> GuardTeam = new HashMap<Integer, GuardSleep>();
	static int i = 0;
	static int startklocka = 0;
	static int stoppklocka = 0;
	static int maxSleep = 0;
	static int maxMinute = 0;
	static int idTimesMaxSleep = 0;
	static int idTimesBestMinute = 0;
	static int guard = 0;

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag4\\src\\dag4\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((str = br.readLine()) != null) {
			log.add((str));
		}
		br.close();
		del1();
		del2();
	}

	public static void del1() {
		log.sort(Comparator.naturalOrder());

		do {
//			System.out.println("start do");
			if (log.get(i).charAt(25) == '#') { // next elf
				guard = Integer.parseInt(log.get(i).substring(26).split(" ")[0]);
				if ((onDuty = GuardTeam.get(guard)) == null) {
					onDuty = new GuardSleep();
					GuardTeam.put(guard, onDuty);
//					System.out.println("newguard " + guard);
				} else {
//					System.out.println("oldguard " + guard);
				}
			} else { // get sleep cycle
				startklocka = Integer.parseInt(log.get(i).substring(15, 17));
				stoppklocka = Integer.parseInt(log.get(++i).substring(15, 17));
				for (int j = startklocka; j < stoppklocka; j++) {
					onDuty.addSleep(j);
				}
//				System.out.println("sleepcycle");
//				onDuty.printSleep();
			}
		} while (++i < log.size() - 1);

		for (Map.Entry<Integer, GuardSleep> entry : GuardTeam.entrySet()) {
			Integer key = entry.getKey();
			GuardSleep value = entry.getValue();
//			System.out.println("key=" + key);
//			value.printSleep();
//			System.out.println("");

			if (value.sumSleep() > maxSleep) {
				maxSleep = value.sumSleep();
				// System.out.println("maxs" + maxSleep + "id " + key);
				idTimesMaxSleep = value.bestMinute() * key;
			}
			if (value.bestMinute() >= maxMinute) {
				// value.printSleep();
				maxMinute = value.bestMinute();
				// System.out.println("maxm" + maxMinute + "id " + key);
				idTimesBestMinute = maxMinute * key;
			}
		}
		System.out.println("ID * best minute: max sum sleep " + idTimesMaxSleep);
	}

	public static void del2() {
		System.out.println("ID * best minute: max best minute " + idTimesBestMinute);
	}
}

class GuardSleep {
	int[] minuter = new int[60];

	public void addSleep(int minut) {
		minuter[minut]++;
	}

	public int sumSleep() {
		int s = 0;
		for (int i = 0; i < 60; i++) {
			s += minuter[i];
		}
		return s;
	}

	public void printSleep() {
		for (int i = 0; i < 60; i++) {
			System.out.print("[" + i + ":" + minuter[i] + "]");
		}
		System.out.println("");
	}

	public int bestMinute() {
		int s = 0;
		int max = 0;
		for (int i = 0; i < 60; i++) {
			if (minuter[i] > max) {
				max = minuter[i];
				s = i;
			}
		}
		return s;
	}
}

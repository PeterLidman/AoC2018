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

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag4\\src\\dag4\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((str = br.readLine()) != null) {
			log.add((str));
		}
		br.close();
		del1();
	}

	public static void del1() {
		GuardSleep onDuty = null;
		HashMap<Integer, GuardSleep> guardTeam = new HashMap<Integer, GuardSleep>();
		int i = 0;
		int guard = 0;
		int startklocka = 0;
		int stoppklocka = 0;
		int maxSleep = 0;
		int mostMinute = 0;
		int idTimesMaxSleep = 0;
		int idTimesBestMinute = 0;

		log.sort(Comparator.naturalOrder());

		do {
			if (log.get(i).charAt(25) == '#') { // next elf guard
				guard = Integer.parseInt(log.get(i).substring(26).split(" ")[0]);
				if ((onDuty = guardTeam.get(guard)) == null) {
					onDuty = new GuardSleep();
					guardTeam.put(guard, onDuty);
				}
			} else { // get sleep cycle
				startklocka = Integer.parseInt(log.get(i).substring(15, 17));
				stoppklocka = Integer.parseInt(log.get(++i).substring(15, 17));
				for (int j = startklocka; j < stoppklocka; j++) {
					onDuty.addSleep(j);
				}
			}
		} while (++i < log.size() - 1);

		for (Map.Entry<Integer, GuardSleep> entry : guardTeam.entrySet()) {
			Integer guardNr = entry.getKey();
			GuardSleep guardSleep = entry.getValue();

			if (guardSleep.sumSleep() > maxSleep) {
				maxSleep = guardSleep.sumSleep();
				idTimesMaxSleep = guardSleep.bestMinute() * guardNr;
			}
			if (guardSleep.mostSleepMinute() >= mostMinute) {
				mostMinute = guardSleep.mostSleepMinute();
				idTimesBestMinute = guardSleep.bestMinute() * guardNr;
			}
		}
		System.out.println("Sleepiest guard: " + idTimesMaxSleep);
		System.out.println("Sleepiest minute: " + idTimesBestMinute);
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

	public int mostSleepMinute() {
		int max = 0;
		for (int i = 0; i < 60; i++) {
			if (minuter[i] > max) {
				max = minuter[i];
			}
		}
		return max;
	}

}

package dag12;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Dag12 {
	public static void main(final String[] args) throws IOException {
		String str;
		String a[];
		HashMap<String, String> hm = new HashMap<>();

		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag12\\src\\dag12\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));

		str = br.readLine();
		str = br.readLine();

		while ((str = br.readLine()) != null) {
			a = str.split(" ");
			hm.put(a[0], a[2]);
		}
		br.close();
		String pre = "....................................................................................................";
		String state = "###.......##....#.#.#..###.##..##.....#....#.#.....##.###...###.#...###.###.#.###...#.####.##.#....#";
		state = pre + state + pre +pre +pre +pre +pre;
		String state2 = "..........";
		String plant = "";
		int sum = 0;
		System.out.println(state);
		for (int j = 0; j < 200; j++) {
			state2 = "............";
			for (int i = 10; i < 690; i++) {
				if ((plant = hm.get(state.substring(i, i + 5))) != null) {
					state2 += plant;
				} else {
					state2 += ".";
				}
			}
			state2 += "........";
			System.out.println(state2);
			state = state2;
			sum=0;
			for (int i = 0; i < 600; i++) {
				if (state.substring(i, i + 1).equals("#")) {
					sum += (i - 100);
				}
			}
			System.out.println("sum  not 6240= " + sum + " j " +j );
		}
		long l = 50_000_000_000L;
		l=(l - 199) * 52 + 12220;
		System.out.println("long " +l );
	}
}

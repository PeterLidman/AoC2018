package dag1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Dag1 {
	public static void main(String[] args) {
		del1();
		del2();
	}

	public static void del1() {
		String str;
		int sum = 0;
		FileInputStream in;

		try {
			in = new FileInputStream("C:\\git\\AoC2018\\Dag1\\src\\dag1\\input.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			while ((str = br.readLine()) != null) {
				sum = sum + Integer.parseInt(str);
			}
			System.out.println("Frekvens: " + sum);
			br.close();
		} catch (IOException e) {
			System.out.println("Hade problem med att läsa filen.");
		}
	}

	public static void del2() {
		String str;
		int sum = 0;
		HashMap<Integer, Integer> h = new HashMap<Integer, Integer>();
		boolean found = false;
		FileInputStream in;
		h.put(0, 0);

		try {
			in = new FileInputStream("C:\\git\\AoC2018\\Dag1\\src\\dag1\\input.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			br.mark(10000);

			do {
				while ((str = br.readLine()) != null) {
					sum = sum + Integer.parseInt(str);
					if (h.get(sum) == null) {
						h.put(sum, sum);
					} else {
						found = true;
						System.out.println("Första dubbletten: " + sum);
						break;
					}
				}
				br.reset();
			} while (!found);
			br.close();
		} catch (IOException e) {
			System.out.println("Hade problem med att läsa filen.");
		}
	}

}

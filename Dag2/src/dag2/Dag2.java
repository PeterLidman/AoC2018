package dag2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dag2 {
	static List<String> paketId = new ArrayList<String>();

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag2\\src\\dag2\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((str = br.readLine()) != null) {
			paketId.add((str));
		}
		br.close();
		del1();
		del2();
	}

	public static void del1() {
		int two = 0;
		int three = 0;
		
		for (final String paket : paketId) {
			if (isAnyCharRepeatedNrTimesInString(paket, 2)) {
				two++;
			}
			if (isAnyCharRepeatedNrTimesInString(paket, 3)) {
				three++;
			}
		}
		System.out.println("Checksum: " + (two * three));
	}

	public static void del2() {
		String cs;
		for (int i = 0; i < paketId.size() - 1; i++) {
			for (int j = 1; j < paketId.size(); j++) {
				cs = commonString(paketId.get(i), paketId.get(j));
				if (paketId.get(i).length() - cs.length() == 1) {
					System.out.println("One diff: " + paketId.get(i) + " : " + cs);
				}
			}
		}
	}

	public static String commonString(final String s1, final String s2) {
		String rs = "";

		for (int i = 0; i < s1.length(); i++) {
			if (s1.substring(i, i + 1).equals(s2.substring(i, i + 1))) {
				rs += s1.substring(i, i + 1);
			}
		}
		return rs;
	}

	public static String reduceStringOfFirstChar(final String s) {
		String rs = "";

		for (int i = 1; i < s.length(); i++) {
			if (!s.substring(0, 1).equals(s.substring(i, i + 1))) {
				rs += s.substring(i, i + 1);
			}
		}
		return rs;
	}

	public static boolean isAnyCharRepeatedNrTimesInString(String s, final int nr) {
		String r = null;

		while (s.length() > 1) {
			r = reduceStringOfFirstChar(s);
			if (s.length() - r.length() == nr) {
				return true;
			}
			s = r;
		}
		return false;
	}

}

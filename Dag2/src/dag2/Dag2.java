package dag2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dag2 {
	static List<String> paketId = new ArrayList<String>();
	static int twice = 0;

	public static void main(String[] args) throws IOException {
		String str;
		FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag2\\src\\dag2\\input.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
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
		for (String paket : paketId) {
			if (isNooInId(paket, 2)) {
				two++;
			}
			if (isNooInId(paket, 3)) {
				three++;
			}
		}
		System.out.println("checksum: " + (two * three));
	}

	public static void del2() {
		String cs;
		for (int i = 0; i < paketId.size() - 1; i++) {
			for (int j = 1; j < paketId.size(); j++) {
				cs = commonString(paketId.get(i), paketId.get(j));
				if (paketId.get(i).length() - cs.length() == 1) {
					System.out.println("one diff: " + paketId.get(i) + " : " + cs);
					break;
				}
			}
		}
	}

	public static String commonString(String s1, String s2) {
		String rs = "";

		for (int i = 0; i < s1.length(); i++) {
			if (s1.substring(i, i + 1).equals(s2.substring(i, i + 1))) {
				rs += s1.substring(i, i + 1);
			}
		}
		return rs;
	}

	public static String reduceString(String s) {
		String rs = "";

		for (int i = 1; i < s.length(); i++) {
			if (!s.substring(0, 1).equals(s.substring(i, i + 1))) {
				rs += s.substring(i, i + 1);
			}
		}
		return rs;
	}

	public static boolean isNooInId(String s, int noo) {
		String r = null;

		while (s.length() > 1) {
			r = reduceString(s);
			if (s.length() - r.length() == noo) {
				return true;
			}
			s = r;
		}
		return false;
	}

}

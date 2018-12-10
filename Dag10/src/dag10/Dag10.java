package dag10;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dag10 {
	static List<Integer> x = new ArrayList<Integer>();
	static List<Integer> y = new ArrayList<Integer>();
	static List<Integer> vx = new ArrayList<Integer>();
	static List<Integer> vy = new ArrayList<Integer>();

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag10\\src\\dag10\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((str = br.readLine()) != null) {
			x.add(Integer.parseInt(str.split(",")[0].split("<")[1].trim()));
			y.add(Integer.parseInt(str.split(",")[1].split(">")[0].trim()));
			vx.add(Integer.parseInt(str.split(",")[1].split("<")[1].trim()));
			vy.add(Integer.parseInt(str.split(",")[2].split(">")[0].trim()));
		}
		br.close();
		del1();
	}

	public static void del1() {
		int seconds = 10159;
		print(seconds);
	}

	public static void print(int seconds) {
		List<Integer> rx = new ArrayList<Integer>();
		List<Integer> ry = new ArrayList<Integer>();
		for (int i = 0; i < x.size(); i++) {
			rx.add(x.get(i) + vx.get(i) * seconds);
			ry.add(y.get(i) + vy.get(i) * seconds);
		}
		int maxx = Collections.max(rx);
		int maxy = Collections.max(ry);
		int minx = Collections.min(rx);
		int miny = Collections.min(ry);
		System.out.println(
				maxx + " " + maxy + " " + minx + " " + miny + " diff x " + (maxx - minx) + " diff y " + (maxy - miny));
		int xo = maxx - minx;
		int yo = maxy - miny;
		int[][] heaven = new int[xo + 1][yo + 1];
		for (int i = 0; i < x.size(); i++) {
			heaven[rx.get(i) - minx][ry.get(i) - miny] = 1;
		}
		for (int yv = 0; yv <= yo; yv++) {
			for (int xv = 0; xv <= xo; xv++) {
				System.out.print(heaven[xv][yv] == 1 ? "#" : ".");
			}
			System.out.println();
		}
	}
}

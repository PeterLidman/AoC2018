package dag6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dag6 {
	static List<String> koord = new ArrayList<String>();

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag6\\src\\dag6\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((str = br.readLine()) != null) {
			koord.add((str));
		}
		br.close();
		del1();
	}

	public static void del1() {
		int x[] = new int[51];
		int y[] = new int[51];
		int xx[] = new int[51];
		int i = 0;
		int j = 0;
		int k = 0;
		int maxx = 0;
		int maxy = 0;
		int matrix[][] = new int[400][400];
		int manhattanDist = 0;
		int bestManhattanDist = 0;
		boolean equal = false;
		boolean infinite = false;

		for (String s : koord) {
			x[i] = Integer.parseInt(s.split(", ")[0]);
			y[i] = Integer.parseInt(s.split(", ")[1]);

			if (maxx < x[i]) {
				maxx = x[i];
			}
			if (maxy < y[i]) {
				maxy = y[i];
			}

			i++;
			// System.out.println("s" + s + "i" + i);
		}

		// System.out.println("maxx " + maxx + " maxy " + maxy);

		for (i = 0; i < 400; i++) {
			for (j = 0; j < 400; j++) {
				bestManhattanDist = 800;
				for (k = 0; k < 50; k++) {
					manhattanDist = Math.abs(i - x[k]) + Math.abs(j - y[k]);
					if (bestManhattanDist == manhattanDist) {
						equal = true;
					} else if (bestManhattanDist > manhattanDist) {
						equal = false;
						bestManhattanDist = manhattanDist;
						matrix[i][j] = k + 1;
					}
				}
				if (equal) {
					matrix[i][j] = 51;
				}
			}
		}

		for (i = 0; i < 400; i++) {
			for (j = 0; j < 400; j++) {
				xx[matrix[i][j] - 1]++;
			}
		}

		// ta bort infinite och hitta största
		maxx = 0;
		for (k = 1; k < 51; k++) {
			infinite = false;
			for (i = 0; i < 400; i++) {
				if (matrix[0][i] == k || matrix[399][i] == k || matrix[i][0] == k || matrix[i][399] == k) {
					infinite = true;
				}
			}
			if (infinite) {
				xx[k - 1] = 0;
			} else {
				if (xx[k - 1] > maxx) {
					maxx = xx[k - 1];
				}
			}
		}
		System.out.println("Max area " + maxx);

		maxy = 0;
		for (i = 0; i < 400; i++) {
			for (j = 0; j < 400; j++) {
				matrix[i][j] = 0;

				for (k = 0; k < 50; k++) {
					matrix[i][j] += Math.abs(i - x[k]) + Math.abs(j - y[k]);
				}

				if (matrix[i][j] < 10_000) {
					maxy++;
				}
			}
		}
		System.out.println("less than 10000 areas: " + maxy);
	}

}

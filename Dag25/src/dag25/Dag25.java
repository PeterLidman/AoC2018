package dag25;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dag25 {
	static List<String> input = new ArrayList<>();
	static List<Point> points = new ArrayList<>();
	static List<Point> constellations = new ArrayList<>();

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag25\\src\\dag25\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));

		while ((str = br.readLine()) != null) {
			input.add(str.trim());
		}
		br.close();

		for (String s : input) {
			points.add(new Point(Integer.parseInt(s.split(",")[0]), Integer.parseInt(s.split(",")[1]),
					Integer.parseInt(s.split(",")[2]), Integer.parseInt(s.split(",")[3])));
		}

		int nrOfconstellation = 0;
		boolean found;
		do {
			points.get(0).belongToConstellation = ++nrOfconstellation;
			constellations.add(points.get(0));
			points.remove(0);
			do {
				found = false;
				for (int i = 0; i < points.size() && !found; i++) {
					for (int j = 0; j < constellations.size() && !found; j++) {
						if (points.get(i).distance(constellations.get(j)) <= 3) {
							points.get(i).belongToConstellation = constellations.get(j).belongToConstellation;
							constellations.add(points.get(i));
							points.remove(i);
							found = true;
						}
					}
				}
			} while (found);
		} while (points.size() > 0);

		for (Point l : constellations) {
			System.out.println("Constellation nr " + l.belongToConstellation);
		}
	}
}

class Point {
	int x, y, z, t;
	int belongToConstellation = -1;

	public Point(int x, int y, int z, int t) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = t;
	}

	int distance(Point p) {
		return Math.abs(x - p.x) + Math.abs(y - p.y) + Math.abs(z - p.z) + Math.abs(t - p.t);
	}
}
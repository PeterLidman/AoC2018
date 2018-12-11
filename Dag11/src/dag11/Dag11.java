package dag11;

public class Dag11 {
	static int[][] fuel = new int[301][301];

	public static void main(final String[] args) {
		int gridSerialNumber = 8444;
		int pl = 0;
		String pls = "";
		int maxp = 0;
		int maxx = 0;
		int maxy = 0;
		int maxs = 0;
		for (int i = 1; i < 301; i++) {
			for (int j = 1; j < 301; j++) {
				pl = ((i + 10) * j + gridSerialNumber) * (i + 10);
				if (pl > 99) {
					pls = String.valueOf(pl);
					fuel[i][j] = Integer.parseInt(pls.substring(pls.length() - 3, pls.length() - 2)) - 5;
				} else {
					fuel[i][j] = 0;
				}
			}
		}
		for (int s = 1; s <= 300; s++) {
			System.out.println("running size " + s);
			for (int i = 1; i < 301 - s; i++) {
				for (int j = 1; j < 301 - s; j++) {
					pl = getPower(i, j, s);

					if (pl > maxp) {
						maxp = pl;
						maxx = i;
						maxy = j;
						maxs = s;
					}

				}
			}
		}
		System.out.println("max fuel " + maxp + " at " + maxx + "," + maxy + " size " + maxs);
	}

	public static int getPower(int x, int y, int squareSize) {
		int sum = 0;
		for (int i = x; i < x + squareSize; i++) {
			for (int j = y; j < y + squareSize; j++) {
				sum += fuel[i][j];
			}
		}
		return sum;
	}
}
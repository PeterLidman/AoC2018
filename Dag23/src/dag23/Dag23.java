package dag23;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Dag23 {
	static List<String> nano = new ArrayList<>();
	static List<Nano> nanoCloud = new ArrayList<>(), sphereTouches = new ArrayList<>();

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag23\\src\\dag23\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));

		while ((str = br.readLine()) != null) {
			nano.add(str);
		}
		br.close();
		int maxStrength = 0, currentStrenght = 0, x, y, z, maxx = 0, maxy = 0, maxz = 0, inrange = 0;
		for (String s : nano) {
			currentStrenght = Integer.parseInt(s.split("=")[2]);
			if (maxStrength < currentStrenght) {
				maxStrength = currentStrenght;
				maxx = Integer.parseInt(s.split("=")[1].split(",")[0].substring(1));
				maxy = Integer.parseInt(s.split("=")[1].split(",")[1]);
				maxz = Integer.parseInt(s.split("=")[1].split(",")[2].split(">")[0]);
			}
		}
		for (String s : nano) {
			currentStrenght = Integer.parseInt(s.split("=")[2]);
			x = Integer.parseInt(s.split("=")[1].split(",")[0].substring(1));
			y = Integer.parseInt(s.split("=")[1].split(",")[1]);
			z = Integer.parseInt(s.split("=")[1].split(",")[2].split(">")[0]);
			nanoCloud.add(new Nano(x, y, z, currentStrenght));

			if (Math.abs(x - maxx) + Math.abs(y - maxy) + Math.abs(z - maxz) <= maxStrength) {
				inrange++;
			}
		}
		System.out.println("strongest has " + inrange + " in range");

//		Optional<Nano> maxX = nanoCloud.stream().max(Comparator.comparingInt(Nano::getX));
//		Optional<Nano> minX = nanoCloud.stream().min(Comparator.comparingInt(Nano::getX));
//		System.out.println("max" + maxX.get().x);
//		System.out.println("min" + minX.get().x);
//		Optional<Nano> maxY = nanoCloud.stream().max(Comparator.comparingInt(Nano::getY));
//		Optional<Nano> minY = nanoCloud.stream().min(Comparator.comparingInt(Nano::getY));
//		System.out.println("max" + maxY.get().y);
//		System.out.println("min" + minY.get().y);
//		Optional<Nano> maxZ = nanoCloud.stream().max(Comparator.comparingInt(Nano::getZ));
//		Optional<Nano> minZ = nanoCloud.stream().min(Comparator.comparingInt(Nano::getZ));
//		System.out.println("max" + maxZ.get().z);
//		System.out.println("min" + minZ.get().z);

		TreeMap<Integer, Integer> ranges = new TreeMap<>();
		for (Nano n : nanoCloud) {
			int distFromZero = n.distanceTo(new Nano(0, 0, 0, 0));
			ranges.put(Math.max(0, distFromZero - (int) n.r), 1);
			ranges.put(distFromZero + (int) n.r, -1);
		}
		int count = 0;
		int result = 0;
		int maxCount = 0;
		for (Map.Entry<Integer, Integer> each : ranges.entrySet()) {
			count += each.getValue();
			if (count > maxCount) {
				result = each.getKey();
				maxCount = count;
			}
		}
		System.out.println(" fusk " + result);

		System.exit(0);
		// idé kanske följa omkretsen på nanobotar?
		// skärningspunkter mellan andra?
		int sphere = 0;
		int sphereMax = 0;
		for (Nano s : nanoCloud) {
			sphere = 0;
			for (Nano t : nanoCloud) {
				if (s.sphereTouch(t)) {
					sphere++;
					sphereTouches.add(s.getNanoInMiddle(t));
				}
			}
			if (sphereMax < sphere) {
				sphereMax = sphere;
			}
			System.out.println("spheretouch " + sphere);
		}
		System.out.println("spheretouch max" + sphereMax);
		int hits = 0, maxHits = 0;
		Nano origo = new Nano(0, 0, 0, 0);
		for (Nano s : sphereTouches) {
			hits = 0;
			for (Nano t : nanoCloud) {
				if (t.distanceTo(s) <= t.r) {
					hits++;
				}
			}
			if (maxHits <= hits) {
				maxHits = hits;
				System.out.println(
						"spheretouches" + s + " with " + hits + " hits, distance to 000 " + s.distanceTo(origo));
			}
		}
		System.out.println("done <152897478");
	}
}

class Nano {
	int x;
	int y;
	int z;
	int r;

	public Nano(int x, int y, int z, int r) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int distanceTo(Nano b) {
		return Math.abs(x - b.x) + Math.abs(y - b.y) + Math.abs(z - b.z);
	}

	public boolean inRange(Nano b) {
		return (Math.abs(x - b.x) + Math.abs(y - b.y) + Math.abs(z - b.z) - r) >= 0;
	}

	public boolean sphereTouch(Nano b) {
		return (distanceTo(b) - r - b.r) == 0;
	}

	public Nano getNanoInMiddle(Nano b) {
		int lx, ly, lz, d;

		d = ((Math.abs(x) - Math.abs(b.x)) * r / (r + b.r));
		lx = b.x > x ? x + d : x - d;
		d = ((Math.abs(y) - Math.abs(b.y)) * r / (r + b.r));
		ly = b.y > y ? y + d : y - d;
		d = ((Math.abs(z) - Math.abs(b.z)) * r / (r + b.r));
		lz = b.z > z ? z + d : z - d;
		return new Nano(lx, ly, lz, 0);
	}

	@Override
	public String toString() {
		return "Nano [x=" + x + ", y=" + y + ", z=" + z + ", r=" + r + "]";
	}

}
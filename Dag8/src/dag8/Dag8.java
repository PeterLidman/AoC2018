package dag8;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Dag8 {
	static Queue<Integer> data = new ArrayDeque<>();

	public static void main(final String[] args) throws IOException {
		String str = new String(Files.readAllBytes(Paths.get("C:\\git\\AoC2018\\Dag8\\src\\dag8\\input.txt")),
				StandardCharsets.UTF_8);
		data.addAll(Arrays.stream(str.trim().split(" ")).map(Integer::valueOf).collect(Collectors.toList()));

		Node root = parseData();
		System.out.println("sum meta " + root.recursiveMeta());
		System.out.println("sum meta " + root.metaIndexSum());
	}

	private static Node parseData() {
		Integer nrChildren = data.poll();
		Integer nrMeta = data.poll();
		List<Node> childs = new ArrayList<>();
		for (int i = 0; i < nrChildren; i++) {
			childs.add(parseData());
		}
		List<Integer> meta = new ArrayList<>();
		for (int i = 0; i < nrMeta; i++) {
			meta.add(data.poll());
		}
		return new Node(childs, meta);
	}

}

class Node {
	List<Node> _children;
	List<Integer> _meta;

	public Node(List<Node> children, List<Integer> meta) {
		_children = children;
		_meta = meta;
	}

	public int metaIndexSum() {
		int sum = 0;
		if (_children.size() == 0) {
			sum = localMeta();
		} else {
			for (Integer n : _meta) {
				if (_children.size() >= n && n > 0) {
					sum += _children.get(n - 1).metaIndexSum();
				}
			}
		}
		return sum;
	}

	public int recursiveMeta() {
		int sum = localMeta();
		for (Node n : _children) {
			sum += n.recursiveMeta();
		}
		return sum;
	}

	public int localMeta() {
		int s = 0;
		for (int a : _meta) {
			s += a;
		}
		return s;
	}

}

package dag14;

import java.util.ArrayList;
import java.util.List;

public class Dag14 {
	public static void main(final String[] args) {
		List<Integer> recipes = new ArrayList<>();
		recipes.add(3);
		recipes.add(7);
		int elf1 = 0;
		int elf2 = 1;
		int sum = 0;
		int goal = 286051 * 100;
		do {
			sum = recipes.get(elf1) + recipes.get(elf2);
			if (sum > 9) {
				recipes.add(1);
//				if(recipes.get(recipes.size()-1)==1 && recipes.get(recipes.size()-2)==5 && recipes.get(recipes.size()-3)==0 && recipes.get(recipes.size()-4)==6 && recipes.get(recipes.size()-5)==8 && recipes.get(recipes.size()-6)==2) {
//					System.out.println("Found "  + (recipes.size()-6));
//					break;
//				}
				recipes.add(sum - 10);
			} else {
				recipes.add(sum);
			}
			elf1 = (elf1 + recipes.get(elf1) + 1) % recipes.size();
			elf2 = (elf2 + recipes.get(elf2) + 1) % recipes.size();
//			for (int i = 0; i < recipes.size(); i++) {
//				if (i == elf1) {
//					System.out.print("(" + recipes.get(i) + ")");
//				} else if (i == elf2) {
//					System.out.print("[" + recipes.get(i) + "]");
//				} else {
//					System.out.print(" " + recipes.get(i) + " ");
//				}
//			}
//			System.out.println();
		} while (recipes.size() < (goal + 11));
//		for (int i = 0; i < 10; i++) {
//			System.out.print(recipes.get(goal + i));
//		}
//		System.out.println();
		for (int i = 0; i < (recipes.size() -7); i++) {
			if(recipes.get(i) == 2 && recipes.get(i+1) == 8 &&recipes.get(i+2) == 6 &&recipes.get(i+3) == 0 &&recipes.get(i+4) == 5 &&recipes.get(i+5) == 1 ) {
				System.out.print("Hitta "  + i);
				break;
			}
		}
	}
}

package dag24;

import java.util.ArrayList;
import java.util.List;

public class Dag24Del2 {
	static List<CombatUnit> immuneSystem = new ArrayList<>();
	static List<CombatUnit> infection = new ArrayList<>();

	public static void runMain() {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		immuneSystem.add(new CombatUnit(17, 5390, 4507, 2, DamageType.FIRE, DamageType.RADIATION,
//				DamageType.BLUDGEONING, null, null, 1));
//		immuneSystem.add(new CombatUnit(989, 1274, 25, 3, DamageType.SLASHING, DamageType.BLUDGEONING,
//				DamageType.SLASHING, DamageType.FIRE, null, 2));
//		infection.add(
//				new CombatUnit(801, 4706, 116, 1, DamageType.BLUDGEONING, DamageType.RADIATION, null, null, null, 1));
//		infection.add(new CombatUnit(4485, 2961, 12, 4, DamageType.SLASHING, DamageType.FIRE, DamageType.COLD,
//				DamageType.RADIATION, null, 2));
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		immuneSystem.add(new CombatUnit(4647, 7816, 13, 1, DamageType.FIRE, null, null, null, null, 1));
		immuneSystem.add(new CombatUnit(301, 3152, 104, 3, DamageType.FIRE, DamageType.FIRE, null, null, null, 2));
		immuneSystem.add(new CombatUnit(1508, 8344, 50, 9, DamageType.COLD, null, null, null, null, 3));
		immuneSystem.add(new CombatUnit(2956, 5021, 13, 15, DamageType.SLASHING, DamageType.SLASHING, null,
				DamageType.BLUDGEONING, null, 4));
		immuneSystem.add(new CombatUnit(898, 11545, 100, 2, DamageType.COLD, null, null, null, null, 5));
		immuneSystem.add(new CombatUnit(207, 6235, 242, 17, DamageType.SLASHING, DamageType.COLD, null, null, null, 6));
		immuneSystem.add(new CombatUnit(7550, 8773, 11, 11, DamageType.RADIATION, DamageType.FIRE, DamageType.SLASHING,
				DamageType.RADIATION, null, 7));
		immuneSystem
				.add(new CombatUnit(1057, 3791, 27, 5, DamageType.BLUDGEONING, null, null, DamageType.COLD, null, 8));
		immuneSystem
				.add(new CombatUnit(5086, 3281, 5, 19, DamageType.COLD, DamageType.BLUDGEONING, null, null, null, 9));
		immuneSystem.add(new CombatUnit(330, 4136, 91, 6, DamageType.COLD, null, null, null, null, 10));

		infection.add(new CombatUnit(1755, 6886, 6, 4, DamageType.FIRE, null, null, DamageType.SLASHING,
				DamageType.RADIATION, 1));
		infection.add(new CombatUnit(2251, 33109, 29, 7, DamageType.COLD, null, null, null, null, 2));
		infection.add(new CombatUnit(298, 18689, 123, 13, DamageType.SLASHING, DamageType.RADIATION,
				DamageType.SLASHING, null, null, 3));
		infection.add(new CombatUnit(312, 15735, 99, 8, DamageType.COLD, DamageType.BLUDGEONING, DamageType.SLASHING,
				null, null, 4));
		infection.add(
				new CombatUnit(326, 16400, 98, 20, DamageType.RADIATION, DamageType.BLUDGEONING, null, null, null, 5));
		infection.add(new CombatUnit(4365, 54947, 22, 14, DamageType.COLD, null, null, null, null, 6));
		infection.add(new CombatUnit(1446, 51571, 63, 18, DamageType.FIRE, DamageType.COLD, null, null, null, 7));
		infection.add(new CombatUnit(8230, 12331, 2, 12, DamageType.FIRE, DamageType.BLUDGEONING, null,
				DamageType.SLASHING, null, 8));
		infection.add(new CombatUnit(4111, 17381, 7, 10, DamageType.COLD, null, null, null, null, 9));
		infection.add(new CombatUnit(366, 28071, 150, 16, DamageType.FIRE, DamageType.COLD, DamageType.SLASHING, null,
				null, 10));
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		int maxDamage, dmg, defdmg, initative, maxInitative;
		int chosen, receiver = 0, maxdefendingEffectivePower;
		final List<CombatUnit> temp = new ArrayList<>();
		
		do {

			// re-init
			for (final CombatUnit cu : immuneSystem) {
				cu.setTargeted(false);
				cu.setTargetSelected(-1);
			}
			for (final CombatUnit cu : infection) {
				cu.setTargeted(false);
				cu.setTargetSelected(-1);
			}

			System.out.println("---------------------------------------");
			for (final CombatUnit c : immuneSystem) {
				System.out
						.println("immune    group " + c.getGroupNr() + " contains " + c.getNumberOfUnits() + " units");
			}
			for (final CombatUnit c : infection) {
				System.out
						.println("infection group " + c.getGroupNr() + " contains " + c.getNumberOfUnits() + " units");
			}
			System.out.println("---------------------------------------");

			// target selection

			// infection attacks immune system
			do {
				chosen = -1;
				maxDamage = 0;
				maxInitative = 0;
				for (int i = 0; i < infection.size(); i++) {
					dmg = infection.get(i).getNumberOfUnits() * infection.get(i).getAttackDamage();
					initative = infection.get(i).getInitiative();
					if ((infection.get(i).getTargetSelected() < 0)
							&& ((maxDamage < dmg) || ((maxDamage == dmg) && (maxInitative < initative)))) {
						maxDamage = dmg;
						maxInitative = initative;
						chosen = i;
					}
				}
				if (chosen < 0) {
					continue;// no more valid attackers found
				}
				receiver = -1;
				maxDamage = 0;
				maxInitative = 0;
				maxdefendingEffectivePower = 0;
				for (int i = 0; i < immuneSystem.size(); i++) {
					if (immuneSystem.get(i).isTargeted()) {
						continue;
					}
					if (immuneSystem.get(i).getImmune1() == infection.get(chosen).getAttackType()
							|| immuneSystem.get(i).getImmune2() == infection.get(chosen).getAttackType()) {
						dmg = 0;// this is important not to finish loop to early, need to select something
					} else if (immuneSystem.get(i).getWeakness1() == infection.get(chosen).getAttackType()
							|| immuneSystem.get(i).getWeakness2() == infection.get(chosen).getAttackType()) {
						dmg = infection.get(chosen).getNumberOfUnits() * infection.get(chosen).getAttackDamage() * 2;
					} else {
						dmg = infection.get(chosen).getNumberOfUnits() * infection.get(chosen).getAttackDamage();
					}
					initative = immuneSystem.get(i).getInitiative();
					defdmg = immuneSystem.get(i).getNumberOfUnits() * immuneSystem.get(i).getAttackDamage();
					System.out.println("Infection group " + infection.get(chosen).getGroupNr() + " -> "
							+ immuneSystem.get(i).getGroupNr() + " (" + dmg + " damage) (" + defdmg
							+ " effective power) (" + initative + " initiative)");
					if ((maxDamage < dmg) || ((maxDamage == dmg) && (maxdefendingEffectivePower < defdmg))
							|| ((maxDamage == dmg) && (maxdefendingEffectivePower == defdmg)
									&& (maxInitative < initative))) {
						maxDamage = dmg;
						maxdefendingEffectivePower = defdmg;
						maxInitative = initative;
						receiver = i;
					}
				}
				if (receiver < 0) {
					continue;// no matching valid defenders found
				}
				infection.get(chosen).setTargetSelected(receiver);
				immuneSystem.get(receiver).setTargeted(true);
			} while (chosen >= 0 && receiver >= 0);

			// immune system attacks infection
			do {
				maxDamage = 0;
				maxInitative = 0;
				chosen = -1;
				for (int i = 0; i < immuneSystem.size(); i++) {
					dmg = immuneSystem.get(i).getNumberOfUnits() * immuneSystem.get(i).getAttackDamage();
					initative = immuneSystem.get(i).getInitiative();
					if ((immuneSystem.get(i).getTargetSelected() < 0)
							&& ((maxDamage < dmg) || ((maxDamage == dmg) && (maxInitative < initative)))) {
						maxDamage = dmg;
						maxInitative = initative;
						chosen = i;
					}
				}
				if (chosen < 0) {
					continue;// no more valid attackers found
				}
				receiver = -1;
				maxDamage = 0;
				maxInitative = 0;
				maxdefendingEffectivePower = 0;
				for (int i = 0; i < infection.size(); i++) {
					if (infection.get(i).isTargeted()) {
						continue;
					}
					if (infection.get(i).getImmune1() == immuneSystem.get(chosen).getAttackType()
							|| infection.get(i).getImmune2() == immuneSystem.get(chosen).getAttackType()) {
						dmg = 0;// this is important not to finish loop to early, need to select something
					} else if (infection.get(i).getWeakness1() == immuneSystem.get(chosen).getAttackType()
							|| infection.get(i).getWeakness2() == immuneSystem.get(chosen).getAttackType()) {
						dmg = immuneSystem.get(chosen).getNumberOfUnits() * immuneSystem.get(chosen).getAttackDamage()
								* 2;
					} else {
						dmg = immuneSystem.get(chosen).getNumberOfUnits() * immuneSystem.get(chosen).getAttackDamage();
					}
					initative = infection.get(i).getInitiative();
					defdmg = infection.get(i).getNumberOfUnits() * infection.get(i).getAttackDamage();
					System.out.println("Immune    group " + immuneSystem.get(chosen).getGroupNr() + " -> "
							+ infection.get(i).getGroupNr() + " (" + dmg + " damage) (" + defdmg + " effective power) ("
							+ initative + " initiative)");
					if ((maxDamage < dmg) || ((maxDamage == dmg) && (maxdefendingEffectivePower < defdmg))
							|| ((maxDamage == dmg) && (maxdefendingEffectivePower == defdmg)
									&& (maxInitative < initative))) {
						maxDamage = dmg;
						maxdefendingEffectivePower = defdmg;
						maxInitative = initative;
						receiver = i;
					}
				}
				if (receiver < 0) {
					continue;// no matching valid defenders found
				}
				immuneSystem.get(chosen).setTargetSelected(receiver);
				infection.get(receiver).setTargeted(true);
			} while (chosen >= 0 && receiver >= 0);

			System.out.println("---------------------------------------");

			// attacking
			for (int i = 20; i >= 0; i--) {
				for (final CombatUnit c : immuneSystem) {
					if (c.getInitiative() == i && c.getTargetSelected() >= 0) {
						System.out.print("immune -> infection (initiative=" + i + ") group " + c.getGroupNr()
								+ " -> group " + infection.get(c.getTargetSelected()).getGroupNr());
						immuneAttacks(c);
					}
				}
				for (final CombatUnit c : infection) {
					if (c.getInitiative() == i && c.getTargetSelected() >= 0) {
						System.out.print("infection -> immune (initiative=" + i + ") group " + c.getGroupNr()
								+ " -> group " + immuneSystem.get(c.getTargetSelected()).getGroupNr());
						infectionAttacks(c);
					}
				}
			}
			// weed out dead units
			temp.clear();
			for (final CombatUnit c : immuneSystem) {
				if (c.getNumberOfUnits() > 0) {
					temp.add(c);
				}
			}
			immuneSystem.clear();
			for (final CombatUnit c : temp) {
				immuneSystem.add(c);
			}

			temp.clear();
			for (final CombatUnit c : infection) {
				if (c.getNumberOfUnits() > 0) {
					temp.add(c);
				}
			}
			infection.clear();
			for (final CombatUnit c : temp) {
				infection.add(c);
			}
		} while (!((immuneSystem.size() == 0) || (infection.size() == 0))); // continue until either side is gone

		System.out.println("---------------------------------------");

		// Determine outcome
		int immuneSum = 0;
		for (final CombatUnit c : immuneSystem) {
			System.out.println("immune left " + c.getNumberOfUnits());
			immuneSum += c.getNumberOfUnits();
		}
		int infectionSum = 0;
		for (final CombatUnit c : infection) {
			System.out.println("infection left " + c.getNumberOfUnits());
			infectionSum += c.getNumberOfUnits();
		}
		System.out.println("<20491 >19714 Sum immune " + immuneSum + " sum infection " + infectionSum);
	}

	private static void infectionAttacks(final CombatUnit i) {
		int dmg, kills;
		final int targetSelected = i.getTargetSelected();
		if (targetSelected >= 0) {
			final int AttackingNumberOfUnits = i.getNumberOfUnits();
			final int hitPointsEach = immuneSystem.get(targetSelected).getHitPointsEach();
			int defendingNumberOfUnits = immuneSystem.get(targetSelected).getNumberOfUnits();
			if (immuneSystem.get(targetSelected).getWeakness1() == i.getAttackType()
					|| immuneSystem.get(targetSelected).getWeakness2() == i.getAttackType()) {
				dmg = i.getNumberOfUnits() * i.getAttackDamage() * 2;
			} else {
				dmg = i.getNumberOfUnits() * i.getAttackDamage();
			}
			if (AttackingNumberOfUnits > 0) {
				kills = 0;
				while ((defendingNumberOfUnits > 0) && (dmg >= hitPointsEach)) {
					dmg -= hitPointsEach;
					kills++;
					defendingNumberOfUnits--;
				}
				System.out.println(
						", (unit count=" + immuneSystem.get(targetSelected).getNumberOfUnits() + ") killing " + kills);
				immuneSystem.get(targetSelected).setNumberOfUnits(defendingNumberOfUnits);
			} else {
				System.out.println(" attackers was killed");
			}
		} else {
			System.out.println(" no attack");
		}
	}

	private static void immuneAttacks(final CombatUnit i) {
		int dmg, kills;
		final int targetSelected = i.getTargetSelected();
		if (targetSelected >= 0) {
			final int AttackingNumberOfUnits = i.getNumberOfUnits();
			final int hitPointsEach = infection.get(targetSelected).getHitPointsEach();
			int defendingNumberOfUnits = infection.get(targetSelected).getNumberOfUnits();
			if (infection.get(targetSelected).getWeakness1() == i.getAttackType()
					|| infection.get(targetSelected).getWeakness2() == i.getAttackType()) {
				dmg = i.getNumberOfUnits() * i.getAttackDamage() * 2;
			} else {
				dmg = i.getNumberOfUnits() * i.getAttackDamage();
			}
			if (AttackingNumberOfUnits > 0) {
				kills = 0;
				while ((defendingNumberOfUnits > 0) && (dmg >= hitPointsEach)) {
					dmg -= hitPointsEach;
					kills++;
					defendingNumberOfUnits--;
				}
				System.out.println(
						", (unit count=" + infection.get(targetSelected).getNumberOfUnits() + ") killing " + kills);
				infection.get(targetSelected).setNumberOfUnits(defendingNumberOfUnits);
			} else {
				System.out.println(" attackers was killed");
			}
		} else {
			System.out.println(" no attack");
		}
	}

}

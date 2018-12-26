package dag24;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

enum DamageType {
	RADIATION, BLUDGEONING, FIRE, SLASHING, COLD;
}

class CombatUnit {
	int numberOfUnits, hitPointsEach, attackDamage, initiative;
	DamageType attackType, weakness1 = null, weakness2 = null, immune1 = null, immune2 = null;
	int targetSelected = -1;
	boolean targeted = false;
	int groupNr;

	public CombatUnit(int numberOfUnits, int hitPointsEach, int attackDamage, int initiative, DamageType attackType,
			DamageType weakness1, DamageType weakness2, DamageType immune1, DamageType immune2, int groupNr) {
		super();
		this.numberOfUnits = numberOfUnits;
		this.hitPointsEach = hitPointsEach;
		this.attackDamage = attackDamage;
		this.initiative = initiative;
		this.attackType = attackType;
		this.weakness1 = weakness1;
		this.weakness2 = weakness2;
		this.immune1 = immune1;
		this.immune2 = immune2;
		this.groupNr = groupNr;
	}

	@Override
	public String toString() {
		return "CombatUnit [numberOfUnits=" + numberOfUnits + ", hitPointsEach=" + hitPointsEach + ", attackDamage="
				+ attackDamage + ", initiative=" + initiative + ", attackType=" + attackType + ", weakness1="
				+ weakness1 + ", weakness2=" + weakness2 + ", immune1=" + immune1 + ", immune2=" + immune2
				+ ", targetSelected=" + targetSelected + ", targeted=" + targeted + ", groupNr=" + groupNr + "]";
	}
}

public class Dag24 {
	static List<CombatUnit> immuneSystem = new ArrayList<>();
	static List<CombatUnit> infection = new ArrayList<>();

	public static void main(final String[] args) throws IOException {
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
		List<CombatUnit> temp = new ArrayList<>();
		do {

			// re-init
			for (CombatUnit cu : immuneSystem) {
				cu.targeted = false;
				cu.targetSelected = -1;
			}
			for (CombatUnit cu : infection) {
				cu.targeted = false;
				cu.targetSelected = -1;
			}

			System.out.println("---------------------------------------");
			for (CombatUnit c : immuneSystem) {
				System.out.println("immune    group " + c.groupNr + " contains " + c.numberOfUnits + " units");
			}
			for (CombatUnit c : infection) {
				System.out.println("infection group " + c.groupNr + " contains " + c.numberOfUnits + " units");
			}
			System.out.println("---------------------------------------");

			// target selection

			// infection attacks immune system
			do {
				chosen = -1;
				maxDamage = 0;
				maxInitative = 0;
				for (int i = 0; i < infection.size(); i++) {
					dmg = infection.get(i).numberOfUnits * infection.get(i).attackDamage;
					initative = infection.get(i).initiative;
					if (infection.get(i).targetSelected < 0) {// ignore already considered
						if (maxDamage < dmg) {
							maxDamage = dmg;
							maxInitative = initative;
							chosen = i;
						} else if (maxDamage == dmg && maxInitative < initative) {
							maxDamage = dmg;
							maxInitative = initative;
							chosen = i;
						}
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
					if (immuneSystem.get(i).immune1 == infection.get(chosen).attackType
							|| immuneSystem.get(i).immune2 == infection.get(chosen).attackType
							|| immuneSystem.get(i).targeted == true) {

					} else {
						if (immuneSystem.get(i).weakness1 == infection.get(chosen).attackType
								|| immuneSystem.get(i).weakness2 == infection.get(chosen).attackType) {
							dmg = infection.get(chosen).numberOfUnits * infection.get(chosen).attackDamage * 2;
						} else {
							dmg = infection.get(chosen).numberOfUnits * infection.get(chosen).attackDamage;
						}
						initative = immuneSystem.get(i).initiative;
						defdmg = immuneSystem.get(i).numberOfUnits * immuneSystem.get(i).attackDamage;
						System.out.println("Infection group " + infection.get(chosen).groupNr + " -> "
								+ immuneSystem.get(i).groupNr + " (" + dmg + " damage) (" + defdmg
								+ " effective power) (" + initative + " initiative)");
						// if (dmg > immuneSystem.get(i).hitPointsEach) {// FML
						if (maxDamage < dmg) {
							maxDamage = dmg;
							maxdefendingEffectivePower = defdmg;
							maxInitative = initative;
							receiver = i;
						} else if (maxDamage == dmg && maxdefendingEffectivePower < defdmg) {
							maxDamage = dmg;
							maxdefendingEffectivePower = defdmg;
							maxInitative = initative;
							receiver = i;
						} else if (maxDamage == dmg && maxdefendingEffectivePower == defdmg
								&& maxInitative < initative) {
							//System.out.println("initative matters");
							maxDamage = dmg;
							maxdefendingEffectivePower = defdmg;
							maxInitative = initative;
							receiver = i;
						}
						// }
					}
				}
				if (receiver < 0) {
					continue;// no matching valid defenders found
				}
				infection.get(chosen).targetSelected = receiver;
				immuneSystem.get(receiver).targeted = true;
			} while (chosen >= 0 && receiver >= 0);

			// immune system attacks infection
			do {
				maxDamage = 0;
				maxInitative = 0;
				chosen = -1;
				for (int i = 0; i < immuneSystem.size(); i++) {
					dmg = immuneSystem.get(i).numberOfUnits * immuneSystem.get(i).attackDamage;
					initative = immuneSystem.get(i).initiative;
					if (immuneSystem.get(i).targetSelected < 0) {// ignore already considered
						if (maxDamage < dmg) {
							maxDamage = dmg;
							maxInitative = initative;
							chosen = i;
						} else if (maxDamage == dmg && maxInitative < initative) {
							maxDamage = dmg;
							maxInitative = initative;
							chosen = i;
						}
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
					if (infection.get(i).immune1 == immuneSystem.get(chosen).attackType
							|| infection.get(i).immune2 == immuneSystem.get(chosen).attackType
							|| infection.get(i).targeted == true) {

					} else {
						if (infection.get(i).weakness1 == immuneSystem.get(chosen).attackType
								|| infection.get(i).weakness2 == immuneSystem.get(chosen).attackType) {
							dmg = immuneSystem.get(chosen).numberOfUnits * immuneSystem.get(chosen).attackDamage * 2;
						} else {
							dmg = immuneSystem.get(chosen).numberOfUnits * immuneSystem.get(chosen).attackDamage;
						}
						initative = infection.get(i).initiative;
						defdmg = infection.get(i).numberOfUnits * infection.get(i).attackDamage;
						System.out.println("Immune    group " + immuneSystem.get(chosen).groupNr + " -> "
								+ infection.get(i).groupNr + " (" + dmg + " damage) (" + defdmg + " effective power) ("
								+ initative + " initiative)");
						// if (dmg > infection.get(i).hitPointsEach) {// if useless just panic
						if (maxDamage < dmg) {
							maxDamage = dmg;
							maxdefendingEffectivePower = defdmg;
							maxInitative = initative;
							receiver = i;
						} else if (maxDamage == dmg && maxdefendingEffectivePower < defdmg) {
							maxDamage = dmg;
							maxdefendingEffectivePower = defdmg;
							maxInitative = initative;
							receiver = i;
						} else if (maxDamage == dmg && maxdefendingEffectivePower == defdmg
								&& maxInitative < initative) {
							//System.out.println("initative matters");
							maxDamage = dmg;
							maxdefendingEffectivePower = defdmg;
							maxInitative = initative;
							receiver = i;
						}
						// }
					}
				}
				if (receiver < 0) {
					continue;// no matching valid defenders found
				}
				immuneSystem.get(chosen).targetSelected = receiver;
				infection.get(receiver).targeted = true;
			} while (chosen >= 0 && receiver >= 0);
			System.out.println("---------------------------------------");
			// attacking
			for (int i = 20; i >= 0; i--) {
				for (CombatUnit c : immuneSystem) {
					if (c.initiative == i && c.targetSelected >= 0) {
						System.out.print("immune -> infection (initiative=" + i + ") group " + c.groupNr + " -> group "
								+ infection.get(c.targetSelected).groupNr);
						immuneAttacks(c);
					}
				}
				for (CombatUnit c : infection) {
					if (c.initiative == i && c.targetSelected >= 0) {
						System.out.print("infection -> immune (initiative=" + i + ") group " + c.groupNr + " -> group "
								+ immuneSystem.get(c.targetSelected).groupNr);
						infectionAttacks(c);
					}
				}
			}
			// remove dead units, correctly
			temp.clear();
			for (CombatUnit c : immuneSystem) {
				if (c.numberOfUnits > 0) {
					temp.add(c);
				}
			}
			immuneSystem.clear();
			for (CombatUnit c : temp) {
				immuneSystem.add(c);
			}

			temp.clear();
			for (CombatUnit c : infection) {
				if (c.numberOfUnits > 0) {
					temp.add(c);
				}
			}
			infection.clear();
			for (CombatUnit c : temp) {
				infection.add(c);
			}
			// System.out.println("imm " + immuneSystem.size() + " inf" + infection.size());
		} while (!(immuneSystem.size() == 0 || infection.size() == 0)); // continue until either side is gone
		System.out.println("---------------------------------------");
		// Determine outcome
		int immuneSum = 0;
		for (CombatUnit c : immuneSystem) {
			System.out.println("immune left " + c.numberOfUnits);
			immuneSum += c.numberOfUnits;
		}
		int infectionSum = 0;
		for (CombatUnit c : infection) {
			System.out.println("infection left " + c.numberOfUnits);
			infectionSum += c.numberOfUnits;
		}
		System.out.println("<20491 >19714 Sum immune " + immuneSum + " sum infection " + infectionSum);
	}

	private static void infectionAttacks(CombatUnit i) {
		int dmg, kills;
		int targetSelected = i.targetSelected;
		if (targetSelected >= 0) {
			int AttackingNumberOfUnits = i.numberOfUnits;
			int hitPointsEach = immuneSystem.get(targetSelected).hitPointsEach;
			int defendingNumberOfUnits = immuneSystem.get(targetSelected).numberOfUnits;
			if (immuneSystem.get(targetSelected).weakness1 == i.attackType
					|| immuneSystem.get(targetSelected).weakness2 == i.attackType) {
				dmg = i.numberOfUnits * i.attackDamage * 2;
			} else {
				dmg = i.numberOfUnits * i.attackDamage;
			}
			if (AttackingNumberOfUnits > 0) {
				kills = 0;
				while (defendingNumberOfUnits > 0 && dmg >= hitPointsEach) {
					dmg -= hitPointsEach;
					kills++;
					defendingNumberOfUnits--;
				}
				System.out.println(
						", (unit count=" + immuneSystem.get(targetSelected).numberOfUnits + ") killing " + kills);
				immuneSystem.get(targetSelected).numberOfUnits = defendingNumberOfUnits;
			} else {
				System.out.println(" attackers was killed");
			}
		} else {
			System.out.println(" no attack");
		}
	}

	private static void immuneAttacks(CombatUnit i) {
		int dmg, kills;
		int targetSelected = i.targetSelected;
		if (targetSelected >= 0) {
			int AttackingNumberOfUnits = i.numberOfUnits;
			int hitPointsEach = infection.get(targetSelected).hitPointsEach;
			int defendingNumberOfUnits = infection.get(targetSelected).numberOfUnits;
			if (infection.get(targetSelected).weakness1 == i.attackType
					|| infection.get(targetSelected).weakness2 == i.attackType) {
				dmg = i.numberOfUnits * i.attackDamage * 2;
			} else {
				dmg = i.numberOfUnits * i.attackDamage;
			}
			if (AttackingNumberOfUnits > 0) {
				kills = 0;
				while (defendingNumberOfUnits > 0 && dmg >= hitPointsEach) {
					dmg -= hitPointsEach;
					kills++;
					defendingNumberOfUnits--;
				}
				System.out
						.println(", (unit count=" + infection.get(targetSelected).numberOfUnits + ") killing " + kills);
				infection.get(targetSelected).numberOfUnits = defendingNumberOfUnits;
			} else {
				System.out.println(" attackers was killed");
			}
		} else {
			System.out.println(" no attack");
		}
	}
}
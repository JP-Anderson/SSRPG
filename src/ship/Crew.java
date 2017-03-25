package ship;

import characters.Crewmember;
import characters.skills.abilities.Ability;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Crew {

	private ArrayList<Crewmember> crew;
	private int crewCapacity;

	public Crew() {
		crew = new ArrayList<>();
	}

	public int size() {
		return crew.size();
	}

	public boolean addCrewmember(Crewmember crewmember) {
		if (size() + 1 > crewCapacity) return false;
		crew.add(crewmember);
		return true;
	}

	public ArrayList<Crewmember> getCrew() {
		return crew;
	}

	public void setCrew(ArrayList<Crewmember> newCrew) {
		crew = newCrew;
	}

	public Crewmember getCrewmemberAtIndex(int crewIndex) {
		try {
			return crew.get(crewIndex);
		} catch (IndexOutOfBoundsException indexException) {
			return null;
		}
	}

	public List<Crewmember> getCrewmembersOfClass(String crewmemberClass) {
		Predicate<Crewmember> matchClass =
				c ->
						c.crewmemberClass._className.equals(crewmemberClass);
		return getCrewmembersMatchingPredicate(matchClass);
	}

	//TODO make these use predicate
	public boolean checkAnyCrewmemberHasAbility(String abilityName) {
		for (Crewmember crewmember : crew) {
			if (crewmember.hasAbility(abilityName)) {
				return true;
			}
		}
		return false;
	}

	//TODO make these use predicate
	public Ability getAbilityIfUnlockedForAnyCrewmember(String abilityName) {
		Ability ability = null;
		for (Crewmember crewmember : crew) {
			ability = crewmember.tryAndGetAbility(abilityName);
			if (ability != null) return ability;
		}
		return null;
	}

	protected List<Crewmember> getCrewmembersMatchingPredicate(Predicate<Crewmember> predicate) {
		ArrayList<Crewmember> matchingCrew = new ArrayList<>();
		for (Crewmember crewmember : crew) {
			if (predicate.test(crewmember)) matchingCrew.add(crewmember);
		}
		return matchingCrew;
	}

	// TODO: remove crewmembers when they die. Could have a turn timer to get crewmembers to a hospital in X turns?
	public void removeCrewmemberAtIndex(int crewIndex) {
		crew.remove(crewIndex);
	}

	public int getCrewCapacity() {
		return crewCapacity;
	}

	public void setCrewCapacity(int crewCapacity) {
		this.crewCapacity = crewCapacity;
	}

}

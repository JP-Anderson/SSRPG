package events;

import arch.session.interaction.TextInteraction;
import arch.session.interaction.TextInteractionDecision;
import arch.view.ConsoleIOHandler;
import characters.skills.abilities.ValueAbility;

import java.util.ArrayList;

public class CargoCheckEvent extends Event {

	public CargoCheckEvent(ConsoleIOHandler injectedView) {
		super(injectedView);
	}

	@Override
	protected String getEventIntroductionMessage() {
		return "You have encountered a Police Cruiser.";
	}

	@Override
	void initializeOutcome() {
		outcome = new EmptyEventOutcome();
	}

	@Override
	void initializeInteractionTree() {

		ArrayList<String> interaction1Options = new ArrayList<>();
		interaction1Options.add("You may come aboard.");
		interaction1Options.add("[Arm Weapons] We won't be getting searched today.");

		TextInteractionDecision interaction1_requestToCargoCheck = TextInteractionDecision.createAdditionalInteraction(
				rootInteraction,"The Cruiser politely requests permission to search your cargo bay for contraband goods.",
				interaction1Options);

		TextInteraction interaction3_cargoBaySearch = TextInteraction.createAdditionalInteraction(
				interaction1_requestToCargoCheck, "A large team of droids and officers begin searching through your hold...");
		//TextInteraction interaction3_fight = TextInteraction.createAdditionalInteraction()

		TextInteraction todo = TextInteraction.createAdditionalInteraction(
				interaction1_requestToCargoCheck, "TODO: add a fight here");

		double contrabandCargoRatio = playerShip.getContrabandCargoRatio();
		double baseDetectionChance = 0;
		if (contrabandCargoRatio > 0) {
			baseDetectionChance = contrabandCargoRatio - 0.1;
		}

		ValueAbility smuggler = (ValueAbility) playerShip.getAbilityIfUnlockedForAnyCrewmember("Smuggler");
		if (smuggler != null) {
			baseDetectionChance = baseDetectionChance * (double) smuggler.getAbilityValue();
		}
		TextInteraction nextInteraction = null;
		if (rand.randZeroToOne() > baseDetectionChance) {
			TextInteraction interaction4_checkOkay = TextInteraction.createAdditionalInteraction(
					interaction3_cargoBaySearch, "You are free to go.");
		} else {
			TextInteraction interaction4_contrabandFound = TextInteraction.createAdditionalInteraction(
					interaction3_cargoBaySearch, "Contraband detected! //Do some more stuff here...");
		}

	}

	@Override
	EventOutcome getOutcome() {
		return new EmptyEventOutcome();
	}
}

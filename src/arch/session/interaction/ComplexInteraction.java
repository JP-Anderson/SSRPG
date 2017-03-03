package arch.session.interaction;

import arch.view.InputHandler;

import java.util.function.Function;

public class ComplexInteraction extends Interaction {

	Runnable r;

	public static ComplexInteraction createComplexInteraction(Interaction previousInteraction, Runnable r) {
		return new ComplexInteraction(previousInteraction.view, previousInteraction, r);
	}

	protected ComplexInteraction(InputHandler injectedView, Interaction previousInteraction, Runnable r) {
		super(injectedView, previousInteraction);
		this.r = r;
	}

	@Override
	public void run() {
		r.run();
		next(0);
	}
}

package events;

public abstract class Event {

    abstract void initialize();
    abstract void displayEvent();
    abstract EventOutcome generateOutcome();

    protected EventOutcome outcome;

    public final EventOutcome transpire(){
            initialize();
            displayEvent();
            return generateOutcome();
    }

}

package robotvacuum.robot;

public class ProposedMovement<T extends Movement> {
    private final T mov;

    public ProposedMovement(T mov) {
        this.mov = mov;
    }

    public T getMov() {
        return mov;
    }
}

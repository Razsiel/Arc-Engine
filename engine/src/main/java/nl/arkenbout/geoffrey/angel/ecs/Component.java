package nl.arkenbout.geoffrey.angel.ecs;

public interface Component {
    default void cleanup() {
    }

    boolean isEnabled();

    void setEnabled(boolean enable);
}

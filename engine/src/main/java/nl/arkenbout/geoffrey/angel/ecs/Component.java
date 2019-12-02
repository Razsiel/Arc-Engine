package nl.arkenbout.geoffrey.angel.ecs;

public interface Component {
    default void cleanup() {
    }
}

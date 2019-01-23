package nl.arkenbout.geoffrey.arc.ecs;

public interface Component {
    default void cleanup() {}
}

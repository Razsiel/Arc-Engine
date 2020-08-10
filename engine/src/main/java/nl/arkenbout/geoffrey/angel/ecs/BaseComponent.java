package nl.arkenbout.geoffrey.angel.ecs;

public abstract class BaseComponent implements Component {
    protected boolean isEnabled = true;

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean enable) {
        this.isEnabled = enable;
    }
}

package nl.arkenbout.geoffrey.angel.engine.options;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderMethodName = "of", buildMethodName = "create")
public class WindowOptions {
    private int width;
    private int height;
    private String title;
}

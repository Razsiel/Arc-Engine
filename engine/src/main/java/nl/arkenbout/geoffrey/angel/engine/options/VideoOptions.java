package nl.arkenbout.geoffrey.angel.engine.options;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderMethodName = "of", buildMethodName = "create")
public class VideoOptions {
    private boolean vSync;
}

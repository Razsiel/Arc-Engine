package nl.arkenbout.geoffrey.angel.ecs.match;

import nl.arkenbout.geoffrey.angel.ecs.Component;

import java.util.*;

public class ComponentMatcher {

    private HashMap<Class<? extends Component>, List<Component>> matchedComponents = new HashMap<>();

    @SafeVarargs
    public ComponentMatcher(Class<? extends Component>... componentsToMatch) {
        for (var componentClass : componentsToMatch) {
            matchedComponents.put(componentClass, new ArrayList<>());
        }
    }

    public Set<Class<? extends Component>> getMatchTypes() {
        return matchedComponents.keySet();
    }
}



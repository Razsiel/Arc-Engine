package nl.arkenbout.geoffrey.angel.ecs.match;

import nl.arkenbout.geoffrey.angel.ecs.Component;

import java.util.*;

public class ComponentMatcher {

    private HashMap<Class<? extends Component>, List<Component>> matchedComponents = new HashMap<>();

    public ComponentMatcher(Class... componentsToMatch) {
        for (var componentClass : componentsToMatch) {
            if (!Component.class.isAssignableFrom(componentClass))
                throw new ClassCastException("Trying to use " + componentClass.getName() + " to match while it is not a Component type.");

            matchedComponents.put(componentClass, new ArrayList<>());
        }
    }

    public Set<Class<? extends Component>> getMatchTypes() {
        return matchedComponents.keySet();
    }
}



package nl.arkenbout.geoffrey.arc.ecs;

import java.util.*;

public class ComponentMatcher {

    private HashMap<Class<Component>, List<Component>> matchedComponents = new HashMap<>();

    public ComponentMatcher(Class... componentsToMatch) {
        for (var componentClass : componentsToMatch) {
            if (!Component.class.isAssignableFrom(componentClass))
                throw new ClassCastException("Trying to use " + componentClass.getName() + " to match while it is not a Component type.");

            matchedComponents.put(componentClass, new ArrayList<>());
        }
    }

    public <T extends Component> boolean hasMatch(Class<T> type) {
        return matchedComponents.containsKey(type);
    }

    /*
    public <T extends Component> List<T> getMatchedComponents(Class<T> type) {
        if (!matchedComponents.containsKey(type))
            return new ArrayList<>();
        return (List<T>) matchedComponents.getComponent(type);
    }
    */

    public Set<Class<Component>> getMatchTypes() {
        return matchedComponents.keySet();
    }
}



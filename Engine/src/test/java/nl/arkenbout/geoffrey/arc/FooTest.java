package nl.arkenbout.geoffrey.arc;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class FooTest {

    @Test
    public void fooCollectionTest() {
        List<Object> collection = Arrays.asList(1, "a", new Object());
        List<Class> match = Arrays.asList(Integer.class, String.class);

        List<Object> matched = collection.stream().filter(a -> match.contains(a.getClass())).collect(Collectors.toList());

        for (var o : matched) {
            System.out.println(o);
        }

        assertThat(matched, containsInAnyOrder("a", 1));
    }

    @Test
    public void fooDoubleCollectionTest() {
        List<Object> listA = Arrays.asList(1, "a", new Object());
        List<Object> listB = Arrays.asList(3);
        List<Object> listC = Arrays.asList("b", 10);
        List<List<Object>> collection = Arrays.asList(listA, listB, listC);

        List<Class> match = Arrays.asList(Integer.class, String.class);

        List<List<Object>> matched = new ArrayList<>();
        var matchSize = match.size();
        for (var list : collection) {
            boolean matchesAll = false;
            List<Object> matchedElement = new ArrayList<>();
            for (var element : list) {
                var matches = match.contains(element.getClass());
                if (matches) {
                    matchedElement.add(element);
                    if (matchedElement.size() == matchSize) {
                        matchesAll = true;
                        break;
                    }
                }

            }
            if (matchesAll)
                matched.add(matchedElement);
        }

        for (var m : matched) {
            for (var o : m) {
                System.out.println(o);
            }
            System.out.println();
        }

        assertThat(matched, containsInAnyOrder(
                containsInAnyOrder(1, "a"),
                containsInAnyOrder(10, "b")
        ));
    }
}

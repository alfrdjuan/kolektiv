import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class main {
    LinkedList<String> givenCommand = new LinkedList<String>();
    LinkedList<String> whenCommand = new LinkedList<String>();
    LinkedList<String> thenCommand = new LinkedList<String>();

    public static LinkedList<String> getGivenCommand(String testSteps) {
        LinkedList<String> givenCommand = new LinkedList<String>();
        Matcher data = Pattern.compile("(?<=[^@]*Given )[^@]*").matcher(testSteps);
        while (data.find()) {
            givenCommand.add(yameroooo(data.group()));
        }
        return givenCommand;
    }

    public static LinkedList<String> getWhenCommand(String testSteps) {
        LinkedList<String> givenCommand = new LinkedList<String>();
        Matcher data = Pattern.compile("(?<=[^@]*When )[^@]*").matcher(testSteps);
        while (data.find()) {
            givenCommand.add(data.group());
        }
        return givenCommand;
    }

    public static LinkedList<String> getThenCommand(String testSteps) {
        LinkedList<String> givenCommand = new LinkedList<String>();
        Matcher data = Pattern.compile("(?<=[^@]*Then )[^@]*").matcher(testSteps); // there are bugs!
        while (data.find()) {
            givenCommand.add(data.group());
        }
        return givenCommand;
    }

    public static void main(String[] args) throws Exception {
        String testSteps = "@Given data is hehe with $hehe command and BOOM with $boom sound!!!!!!!!!! @Given data is hehe2 @Given data is hehe3 @Given data is hehe4 @Given data is hehe5 @Given data is hehe6 @Then cyka blyat @Then isi zzz @Then null";

        LinkedList<String> data = getGivenCommand(testSteps);
        data.forEach(givenCommand -> {
        });
        getThenCommand(testSteps).forEach(System.out::println);
        getWhenCommand(testSteps).forEach(System.out::println);

        Class<?> userClass = ClassUtils.getUserClass(main.class);

        List<Method> annotatedMethods = Arrays.stream(userClass.getMethods())
                .filter(method -> AnnotationUtils
                        .getAnnotation(method, Given.class) != null)
                .collect(Collectors.toList());
        for (int i = 0; i < data.size() - 1; i++) {
            String cleanSteps = data.get(i).substring(0, data.get(i).length() - 1);
        }
        annotatedMethods.forEach(_annotatedMethod -> {
            for (int i = 0; i < data.size() - 1; i++) {
                String cleanSteps = data.get(i).substring(0, data.get(i).length() - 1);
                System.out.println("a " + cleanSteps);
                System.out.println("b " + _annotatedMethod.getAnnotation(Given.class).value());
                if (_annotatedMethod.getAnnotation(Given.class).value().equals(cleanSteps)) {
                    try {
                        _annotatedMethod.invoke(main.class.newInstance());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
//            System.out.println("_annotatedMethod: " + _annotatedMethod);
//            System.out.println(data.get(0));
//            System.out.println(data.get(0).getClass());
//            System.out.println(_annotatedMethod.getClass());
//            if (_annotatedMethod.getAnnotation(Given.class).value().equals(data.get(0).substring(0, data.get(0).length() - 1))) {
//                try {
//                    _annotatedMethod.invoke(main.class.newInstance(), "hello method");
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                } catch (InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                } catch (InstantiationException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            System.out.println(data.get(0).substring(0, data.get(0).length() - 1).equalsIgnoreCase(_annotatedMethod));
        });
//
//        Class c = Class.forName(main.class.getName());
//        Object t = c.newInstance();
//        annotatedMethods.forEach(_method -> {
//            try {
//                _method.invoke(c.newInstance());
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            } catch (InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (InstantiationException e) {
//                throw new RuntimeException(e);
//            }
//        });

    }

    @Given(value = "data is hehe")
    public static void testGiven() {
        System.out.println("test given is run!!!");
    }


    @Given(value = "data is hehe2")
    public static void testGiven2() {
        System.out.println("ambatukammmmmmm");
    }

    @Given(value = "data is hehe with {dataItems} command and BOOM with {dataItems} sound!!!!!!!!!!")
    public static void testGiven3() {
        System.out.println("serangan TITANNNN");
    }

    public static String yameroooo(String wat) {
        String yamerosenpai = wat;
        System.out.println(wat);
        Matcher data = Pattern.compile("\\$[a-zA-Z0-9]+\\s+").matcher(wat);
        while (data.find()) {
            String dataItems = data.group();
            System.out.println(dataItems);
            yamerosenpai = yamerosenpai.replace(dataItems, "{dataItems}" + " ");
        }
        System.out.println(yamerosenpai);
        return yamerosenpai;
    }
}

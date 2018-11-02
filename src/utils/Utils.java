package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Hayden Fields
 * Will contain utility functions until it makes sense to segregate the
 * utility functions
 */
public class Utils
{
    public static String charListToString(List<Character> characters)
    {
        StringBuilder result = new StringBuilder();
        for (Character ch : characters) {
            result.append(ch);
        }
        return result.toString();
    }
    
    public static <T> List<T> generateFiniteSequence(Function<Integer, T> f, int limit)
    {
        return range(limit).
                    stream().
                        map(f).
                        collect(Collectors.toList());
    }
    
    public static List<Integer> range(int end)
    {
        return range(0, end);
    }
    
    public static List<Integer> range(int start, int end)
    {
        return range(start, end, 1);
    }
    
    public static List<Integer> range(int start, int end, int increment)
    {
        List<Integer> result = new ArrayList<>();
        for (int i = start; i < end; i += increment)
            result.add(i);
        return result;
    }
}

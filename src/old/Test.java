package old;



import java.util.Collection;

/**
 *
 * @author Hayden Fields
 */
public class Test
{
    @SuppressWarnings("empty-statement")
    public static void main(String[] args)
    {
        String a = "asdf";
        
        for (char value : iterable(a))
            System.out.println(value);
        for(int i =0; i < a.length(); i++)
            System.out.println(a.charAt(i));
    }
    public static char[] iterable(String input)
    {
        java.lang.reflect.Field f;
        try
        {
            (f = String.class.getDeclaredField("value")).setAccessible(true);
            return (char[])f.get(input);
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex)
        {
            // this is stupid
        }
        return null;
    }
    public Collection a()
    {
        return null;
    }
}
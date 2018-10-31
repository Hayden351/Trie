package old;



import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hayden Fields
 */
public class NewClass<T> implements WhyDoesItWorkLikeThis<List<T>>
{
    public static void main(String[] args)
    {
        ArrayList<Integer> test = new ArrayList<>();
        test.add(1);
        // for trie must check that both removing an element not in the collection
        // is irrelevant and that removing an element in the collection would
        // cause problems ????
        for (Integer x : test)
            test.remove(new Integer(1));
        
        
        NewClass<Integer> newClass = new NewClass<>();
        Integer[] x = new Integer[3];
        List<Integer[]>[] y = null; // this is stupid
        // why is what we want
        newClass.doThings(x);
        newClass.doThings(y);
        // ehhh, maybe its all right...
        // generic type inference from the generic parameter
        // although what on earth is the use case where you want to
        // specifiy the array that you are passing in but not the type of
        // the collection
        
        
    }

    @Override
    public <T> T[] doThings(T[] e) {
        return e;
    }

}

class NewClass1 implements WhyDoesItWorkLikeThis<List<Integer>>
{
    @Override
    public <T> T[] doThings(T[] e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

interface WhyDoesItWorkLikeThis<T>
{
    public <T> T[] doThings(T[] e);
    
}
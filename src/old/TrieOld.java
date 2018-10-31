package old;




import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * A Trie is a data structure that holds strings.
 * A terminal character is the last character of a string.
 * @author Hayden
 */
public class TrieOld
{
    
    public static void main(String[] args)
    {
        Random generate = new Random(1337);
        TrieOld test = new TrieOld();
        
        test.add("A");
        test.add("to");
        test.add("tea");
        test.add("ted");
        test.add("ten");
        test.add("i");
        test.add("in");
        test.add("inn");
        test.add("inn");
        test.preorderPrint();
        System.out.printf("in : %d\n", test.query("in"));
    }
    // each vertex represents a distinct string in the trie
    class TrieVertex
    {
        // number of elements in this collection that have the current
        // vertex in a prefix of the element
        int prefixCount;
        int isFinal;
        ArrayList<TrieEdge> edges;
        public TrieVertex()
        {
            prefixCount = 0;
            isFinal = 0;
            edges = new ArrayList<>();
        }
    }
    
    // each edge represents appending a character to the current string
    class TrieEdge
    {
        TrieVertex consequent;
        char symbol;
        
        public TrieEdge(TrieVertex con, char sym)
        {
            consequent = con;
            symbol = sym;
        }
    }
    
    // the root represents matching the empty string
    TrieVertex root;
    
    // that the trie is basically a deterministic finite automta
    public TrieOld()
    {
        // initialize to the empty string
        root = new TrieVertex();
    }
    
    // adds a string to the trie
    public void add(String input)
    {
        TrieVertex it = root;
        // increment vertex number
        it.prefixCount++;
        for (int i = 0; i < input.length(); i++)
            (it = addAux(it, input.charAt(i))).prefixCount++;
        it.isFinal++;
    }
    
    // if there is a transition on the symbol then transition else
    // generate the state first then transition
    public TrieVertex addAux(TrieVertex vv, char symbol)
    {
        // if found traverse to corresponding vertex
        TrieVertex rr = transition(vv, symbol);
        if (rr != null)
            return rr;
        
        // if not found create vertex and treverse to it
        TrieVertex uu = new TrieVertex();
        vv.edges.add(new TrieEdge(uu, symbol));
        return uu;
    }
    
    // given a state and a symbol will transition to a new state or return
    // null if no such state is found
    public TrieVertex transition(TrieVertex vv, char symbol)
    {
        for (TrieEdge ee : vv.edges)
            if (ee.symbol == symbol)
                return ee.consequent;
        return null;
    }
    
    // determines if the string is in the trie
    public boolean exists(String find)
    {
        TrieVertex it = root;
        for (int ii = 0; ii < find.length(); ii++)
            if ((it = transition(it, find.charAt(ii))) == null)
                return false;
        return it.isFinal > 0;
    }
    
    // determines how many strings have partial as a prefix
    public int query(String partial)
    {
        TrieVertex it = root;
        // traverse using the 
        for (int ii = 0; ii < partial.length(); ii++)
            if ((it = transition(it, partial.charAt(ii))) == null)
                return 0;
        
        // return the number of terminal characters are in this subtree
        return it.prefixCount;
    }
    
    // will count how many final states there are starting at the given
    // vertex
    public int finalStatesRecursive(TrieVertex vv)
    {
        int total = 0;

        total += vv.isFinal;
        
        for (TrieEdge ee : vv.edges)
            total += finalStatesRecursive(ee.consequent);
        
        return total;
    }

    /**
     *
     * @param vv
     * @return 
     */
    public int finalStatesIterative(TrieVertex vv)
    {
        int total = 0;
        Stack<TrieVertex> stack = new Stack<>();
        
        stack.push(vv);
        while (!stack.isEmpty())
        {
            TrieVertex u = stack.pop();
            total += u.isFinal;
        
            // add all neighbors to stack
            for (TrieEdge e : u.edges)
                stack.push(e.consequent);
        }
        return total;
    }
    
    // will recursively print out each element of the trie

    /**
     *
     */
    public void preorderPrint()
    {
        preorderPrintAux(root, "");
    }
    
    private void preorderPrintAux(TrieVertex v, String str)
    {
        System.out.printf("%s %d\n", str, v.prefixCount);
        for (TrieEdge e : v.edges)
            preorderPrintAux(e.consequent, str + e.symbol);

    }
}
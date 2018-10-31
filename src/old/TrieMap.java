package old;



import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Hayden
 */
public class TrieMap 
{
    public static void main(String[] args)
    {
        TrieMap test = new TrieMap();
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
    }
            
    class Vertex
    {
        int isFinal;
        Map<Character, Edge> edges;
        public Vertex()
        {
            isFinal = 0;
            edges = new HashMap<>();
        }
    }
    class Edge
    {
        Vertex consequent;
        char symbol;
        
        public Edge(Vertex con ,char sym)
        {
            consequent = con;
            symbol = sym;
        }
    }
    
    Vertex root;
    
    public TrieMap()
    {
        root = new Vertex();
    }
    public void add(String input)
    {
        Vertex it = root;
        for (int i = 0; i < input.length(); i++)
            it = addAux(it, input.charAt(i));
        it.isFinal++;
    }
    private Vertex addAux(Vertex v, char symbol)
    {
        Vertex r = transition(v, symbol);
        if (r != null)
            return r;
        
        Vertex u = new Vertex();
        v.edges.put(symbol, new Edge(u, symbol));
        return u;
    }
    
    private Vertex transition(Vertex v, char symbol)
    {
        if (v.edges.containsKey(symbol))
            return v.edges.get(symbol).consequent;
        return null;
    }
    public int query(String partial)
    {
        Vertex it = root;
        for (int i = 0; i < partial.length(); i++)
        {
            if ((it = transition(it, partial.charAt(i))) == null)
                return 0;
        }
        return finalStates(it);
    }
    
    public int finalStates(Vertex v)
    {
        int total = 0;
        Stack<Vertex> stack = new Stack<>();
        stack.push(v);
        while (!stack.isEmpty())
        {
            Vertex u = stack.pop();
            total += u.isFinal;
            
            for (Edge e : u.edges.values())
                stack.push(e.consequent);
        }
        return total;
    }
    
    private void preorderPrint()
    {
        preorderPrintAux(root, "");
    }
    private void preorderPrintAux(Vertex v, String str)
    {
        for (int i = 0; i < v.isFinal; i++)
            System.out.printf("\"%s\"\n", str);
        for (Edge e : v.edges.values())
            preorderPrintAux(e.consequent, str + e.symbol);
    }
            
}

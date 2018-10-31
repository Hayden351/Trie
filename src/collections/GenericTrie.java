package collections;


import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;

/**
 *
 * @author Hayden
 * @param <T> 
 */
public class GenericTrie<T extends Comparable<T>> implements Collection<List<T>>
{
    public static void main(String[] args)
    {
        GenericTrie<Character> test = new GenericTrie<>();
        test.add(asList('A'));
        test.add(asList('t', 'o'));
        test.add(asList('t', 'e','a'));
        test.add(asList('t', 'e','d'));
        test.add(asList('t', 'e','n'));
        test.add(asList('i'));
        test.add(asList('i', 'n'));
        test.add(asList('i', 'n','n'));
        System.out.printf("result of GenericTrie.add when adding null to the list : %s\n", test.add(null));
        ArrayList<Character> listWithNull = new ArrayList<>();
        listWithNull.add(null);
//        test.add(listWithNull);
        
        
        Iterator<List<Character>> testIt = test.iterator();
        
//        test.preorderPrint();
        
        for (List<Character> str : test)
            System.out.println(charListToString(str));
    }
    
    public static String charListToString(List<Character> characters)
    {
        StringBuilder result = new StringBuilder();
        for (Character ch : characters)
            result.append(ch);
        return result.toString();
    }

    @Override
    public int size()
    {
        return root.prefixCount;
    }

    @Override
    public boolean isEmpty()
    {
        return size() == 0;
    }

    @Override
    public boolean contains(Object obj) {
        if (!(obj instanceof Collection))
            return false;
        //TODO: find out what happens when it is a collection not of T
        Iterable<T> find = (Iterable<T>)obj;
        
        Vertex it = root;
        for (T element : find)
            if ((it = transition(it, element)) == null)
                return false;
        return it.isFinal > 0;
    }

    
    @Override
    public Iterator<List<T>> iterator()
    {
        class Pair
        {
            public Pair(Vertex v, List<T> state) {this.v = v; this.state = state;}
            Vertex v; List<T> state;
        }
        return new Iterator<List<T>>()
        {
            int size;
            {
                size = size();
            }
            int currentPos = 0;
            Stack<Pair> stack = new Stack<>();
            {
                stack.push(new Pair(root, new ArrayList<>()));
            }
            
            @Override
            public boolean hasNext()
            {
                return currentPos < size;
            }
            
            @Override
            public List<T> next()
            {
                // is consistent with other collections that will not allow
                // objects to be added while iterating
                // creates ambiguitiy because any element added raises the
                // question on whether you should iterate across it or not
                // example: in the current implementation sometimes the new
                // element would be reached sometimes it would not be
                if (size != size())
                    throw new ConcurrentModificationException();
                currentPos++;
                
                // depth first traversal of a tree, when we find an element
                // we return it and the traveral state is maintained in the
                // stack for subsequent interations
                while (!stack.isEmpty())
                {
                    Pair p = stack.pop();
                    Vertex u = p.v;
                    List<T> currentState = p.state;
                    

                    // add all neighbors to stack
                    for (Edge e : u.edges)
                    {
                        List<T> newState = new ArrayList<>(currentState);
                        newState.add(e.symbol);
                        stack.push(new Pair(e.consequent, newState));
                    }
                    
                    if (u.isFinal > 0) // return a copy of the current path
                        return new ArrayList<>(currentState);
                }
                
                // if next is called after hasNext is false then null is returned
                return null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] list = new Object[size()];
        int i = 0;
        for (List<T> element : this)
            list[i++] = element;
        return list;
    }

    @Override
    public <U> U[] toArray(U[] acc)
    {
        if (acc.length >= size())
        {
            int i = 0;
            for (List<T> element : this)
                acc[i++] = (U)element;
        }
        else
            acc = (U[])toArray();
        return acc;
    }

    // TODO: collection of actions we can perform if we detect that the list
    //       we are trying to add contains a null element
    /* option #1 adding a null into a list and inserting it into a trie
                 is a programming error and programmer can be notified using an 
                 exception
    */
    /* option #2
        
     
    */
    
    @Override
    public boolean add(List<T> input)
    {
        // does not make sense to add null values into a trie state is unchaged
        // and false is returned
        if (input == null)
            return false;
        
        Vertex it = root;
        it.prefixCount++;
        for (T element : input)
            (it = transition(it, element)).prefixCount++;
        it.isFinal++;
        return true;
    }

    @Override
    public boolean remove(Object obj) {
        if (!(obj instanceof Collection))
            return false;
        //TODO: find out what happens when it is a collection not of T
        Iterable<T> find = (Iterable<T>)obj;

        Vertex it = root;
        for (T element : find)
            if ((it = transition(it, element)) == null)
                return false;
        // found the specified spot but the element is not in the trie
        if (it.isFinal == 0)
            return false;
        
        it.isFinal--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        for (Object obj : c)
            if (!contains(obj))
                return false;
        return true;
    }

    // returns true if the trie was changed as a result of the call
    @Override
    public boolean addAll(Collection<? extends List<T>> c) {
        boolean result = true;
        for (List<T> element : c)
            result |= add(element);
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        boolean result = true;
        for (Object element : c)
            result |= remove(element);
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear()
    {
        root = new Vertex();
    }
    class Vertex
    {
        // number of elements in this collection that have the current
        // vertex in a prefix of the element
        int prefixCount;
        
        // if 0 the specified sequence is not in the trie
        // if x then there are x of the specified sequence in the trie
        int isFinal;
        ArrayList<Edge> edges;
        public Vertex()
        {
            prefixCount= 0;
            isFinal = 0;
            edges = new ArrayList<>();
        }
    }

    class Edge
    {
        Vertex consequent;
        T symbol;

        public Edge(Vertex con, T sym)
        {
            consequent = con;
            symbol = sym;
        }
    }

    
    public GenericTrie()
    {
        root = new Vertex();
    }
    
    Vertex root;
    
    private Vertex transition(Vertex v, T element)
    {
        // if found traverse to corresponding vertex
        for (Edge e : v.edges)
            if (e.symbol.compareTo(element) == 0)
                return e.consequent;
        
        // if not found create vertex and treverse to it
        Vertex u = new Vertex();
        v.edges.add(new Edge(u, element));
        return u;
    }
    
    public void preorderPrint()
    {
        preorderPrintAux(root, new ArrayList<>(), x -> System.out.printf("%s\n", x));
    }

    private void preorderPrintAux(Vertex node, String str)
    {
        if (root != null)
        {
            System.out.printf("\"%s\"\n", str);
            for (Edge e : node.edges)
                preorderPrintAux(e.consequent, str + e.symbol);
        }
    }
    private void preorderPrintAux(Vertex node, List<T> sequence, Function<List<T>, Object> f)
    {
        if (root != null)
        {
            f.apply(sequence);
            
            for (Edge e : node.edges)
            {
                List<T> newSequence = new ArrayList<>(sequence);
                newSequence.add(e.symbol);
                preorderPrintAux(e.consequent, newSequence, f);
            }
        }
    }
}


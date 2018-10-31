package collections;

import java.io.PrintStream;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 42 45
 * @author Hayden
 */
public class Trie<T> implements Collection<List<T>>
{
    public static void main(String[] args)
    {
        Trie<Character> trie = new Trie<>();
        trie.add(asList('A'));
        trie.add(asList('t', 'o'));
        trie.add(asList('t', 'e','a'));
        trie.add(asList('t', 'e','d'));
        trie.add(asList('t', 'e','n'));
        trie.add(asList('i'));
        trie.add(asList('i', 'n'));
        trie.add(asList('i', 'n','n'));
        trie.preorderToStream(System.out);
    }

    static class Vertex
    {
        // number of elements in this collection that have the current
        // vertex in a prefix of the element
        int prefixCount;
        
        // is 0 if the word is not contained in the collection
        // otherwise the value indicates how many times the element is in the collection
        int isFinal; 
        
        // the edges that lead to other vertices in the trie
        List<Edge> edges;
        
        public Vertex()
        {
            edges = new ArrayList<>();
        }
    }

    static class Edge<T>
    {
        Vertex consequent;
        T value;


        public Edge(Vertex con, T sym)
        {
            consequent = con;
            value = sym;
        }
    }
    
    // the root represents matching the empty sequence
    Vertex root;
    
    public Trie()
    {
        // initialize to the empty string
        root = new Vertex();
    }
    
    /**
     * The numver of elements 
     * @return the number of elements in the list that start with an empty
     * sequence which is all elements 
     */
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
    public boolean contains(Object o)
    {
        if (!(o instanceof List))
            return false;
        // TODO: when does this fail
        List<T> find = (List<T>)o;
        
        Vertex it = root;
        for (T element : find)
            if ((it = transition(it, element)) == null)
                    return false;
        return it.isFinal > 0;
    }

    @Override
    public Iterator<List<T>> iterator()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(List<T> elements)
    {
        Vertex it = root;
        it.prefixCount++;
        for (T element : elements)
            (it = addAux(it, element)).prefixCount++;
        it.isFinal++;
        return true;
    }
    
//    // if there is a transition on the symbol then transition else
//    // generate the state first then transition
//    private Vertex addAux(Vertex v, T symbol)
//    {
//        // if found traverse to corresponding vertex
//        Vertex r = transition(v, symbol);
//        if (r != null)
//            return r;
//        else 
//            v.edges.add(new Edge(r = new Vertex(), symbol));
//        // if not found create vertex and treverse to it
//        Vertex u = new Vertex();
//        v.edges.add(new Edge(u, symbol));
//        return u;
//    }
    // if there is a transition on the symbol then transition else
    // generate the state first then transition
    private Vertex addAux(Vertex v, T symbol)
    {
        // if found traverse to corresponding vertex
        Vertex r = transition(v, symbol);
        // if not found create vertex and treverse to it
        if (r == null)
            v.edges.add(new Edge(r = new Vertex(), symbol));
        return r;
    }
    // given a state and a symbol will transition to a new state or return
    // null if no such state is found
    private Vertex transition(Vertex v, T symbol)
    {
        for (Edge e : v.edges)
            if (e.value.equals(symbol))
                return e.consequent;
        return null;
    }

    @Override
    public boolean remove(Object o)
    {
        if (!(o instanceof List))
            return false;
        List<T> element = (List<T>)o;
        for (T item : element)
        {
            
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        for (Object element : c)
            if (!contains(element))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends List<T>> c)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeIf(Predicate<? super List<T>> filter)
    {
        return Collection.super.removeIf(filter); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear()
    {
        root = new Vertex();
    }

    @Override
    public void forEach(Consumer<? super List<T>> action)
    {
        Collection.super.forEach(action); //To change body of generated methods, choose Tools | Templates.
    }
    public static Collection<Character> toCollection(String input)
    {
        Collection<Character> result = new ArrayList<Character>();
        for (int i = 0; i < input.length(); i++)
            ;
        return result;
    }
    
    /**
     *
     * @param out
     */
    public void preorderToStream(PrintStream out)
    {
        out.printf("[");
        preorderToStreamAux(root, true, "", out);
        out.printf("]");
    }

    private void preorderToStreamAux(Vertex node, boolean first, String str, PrintStream out)
    {
        if (root != null)
        {
            out.printf("%s%s", first?"":", ", str);
            for (Edge e : node.edges)
                preorderToStreamAux(e.consequent, false, str + e.value.toString(), out);
            
        }
    }
}

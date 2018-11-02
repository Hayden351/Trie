package collections;

import java.util.ArrayList;
import static java.util.Arrays.asList;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.Utils;

/**
 *
 * @author Hayden Fields
 */
public class GenericTrieTest
{    
    public GenericTrieTest()
    {
    }

    @Test
    public void testSize()
    {
    }

    @Test
    public void testIsEmpty()
    {
    }

    @Test
    public void testContains()
    {
    }

    @Test
    public void testIterator()
    {
        GenericTrie<Integer> test = new GenericTrie();
        test.add(Utils.generateFiniteSequence(x -> 2*x, 8));
        
    }

    @Test
    public void testToArray_0args() {
    }

    @Test
    public void testToArray_GenericType() {
    }

    @Test
    public void testAdd() {
    }

    @Test
    public void testRemove() {
    }

    @Test
    public void testContainsAll() {
    }

    @Test
    public void testAddAll() {
    }

    @Test
    public void testRemoveAll() {
    }

    @Test
    public void testRetainAll() {
    }

    @Test
    public void testClear() {
    }

    @Test
    public void testPreorderPrint() {
    }
    
}

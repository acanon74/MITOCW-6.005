/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    /**
     * Preconditions:
     * Labels type must be immutable
     *
     * Special cases:
     *
     * empty graph
     *
     * Test strategy:
     *
     * We test every method of the class against an empty and non-empty
     * graph. We also, check whether they mutated the object.
     *
     * Breakdown of every test used:
     *
     *
     * testVerticesMethod()
     *
     * We instantiate TestGraph as empty and then add some labels to it, then we test whether the object was mutated.
     *
     *
     * testAddMethod()
     *
     * We instantiate EmptyGraph, we will check addition in the following states:
     * - When EmptyGraph is empty.
     * - When EmptyGraph already contains some labels
     * - Try to add() a pre-existing label, we should not modify and return false
     *
     * In every step we check for mutability; whether the object was modified or not.
     *
     *
     * testRemoveMethod()
     *
     * We instantiate TestGraph as empty. And add() some labels.
     * To test the constructor we test whether we have initial edges.
     *
     * Giving the specification, we should test for the following inputs:
     *
     * - Add an edge connecting existing vertices.
     * - Changing an edge connecting existing vertices.
     * - Add an edge connecting missing vertices, creating the vertices and edge.
     * - The input weight is zero, we delete the edge.
     *
     * In every case we check for mutability, whether the object was modified or not.
     *
     *
     * testEmptySourcesTargetsMethods()
     *
     * We instantiate TestGraph as an empty Graph.
     * We test sources() and targets() on the empty object, both of which should return and empty map.
     *
     */


    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testVerticesMethod() {

        Graph<String> TestGraph = emptyInstance();

        HashSet<String> ExpectedVertices = new HashSet<String>();

        assertEquals(TestGraph.vertices(), Collections.emptySet());

        TestGraph.add("FirstLabel");
        TestGraph.add("SecondLabel");

        ExpectedVertices.add("FirstLabel");
        ExpectedVertices.add("SecondLabel");

        assertEquals(TestGraph.vertices(), ExpectedVertices);
    }

    @Test
    public void testAddMethod() {
        //Empty Graph
        Graph<String> EmptyGraph = emptyInstance();

        assertTrue(EmptyGraph.add("NewLabel"));

        HashSet<String> ExpectedEmptyAddition = new HashSet<String>();
        ExpectedEmptyAddition.add("NewLabel");

        assertEquals(EmptyGraph.vertices(), ExpectedEmptyAddition);

        //NonEmpty Graph
        Graph<String> NonEmptyGraph = emptyInstance();
        NonEmptyGraph.add("AlreadyInLabel");

        assertTrue(NonEmptyGraph.add("TestLabel"));

        HashSet<String> ExpectedAddition = new HashSet<String>();
        ExpectedAddition.add("AlreadyInLabel");
        ExpectedAddition.add("TestLabel");

        assertEquals(NonEmptyGraph.vertices(), ExpectedAddition);

        //If already in, return false
        assertFalse(NonEmptyGraph.add("AlreadyInLabel"));
        //Check that it did not mutate the graph
        assertEquals(NonEmptyGraph.vertices(), ExpectedAddition);
    }

    @Test
    public void testRemoveMethod() {
        //Empty Graph
        Graph<String> EmptyGraph = emptyInstance();

        assertFalse(EmptyGraph.remove("MissingLabel"));

        //NonEmpty Graph
        Graph<String> NonEmptyGraph = emptyInstance();
        NonEmptyGraph.add("AlreadyInLabel");
        NonEmptyGraph.add("AnotherLabel");

        assertTrue(NonEmptyGraph.remove("AlreadyInLabel"));

        HashSet<String> ExpectedAddition = new HashSet<String>();
        ExpectedAddition.add("AnotherLabel");

        assertEquals(NonEmptyGraph.vertices(), ExpectedAddition);

        //If it doesn't contain it, return false
        assertFalse(NonEmptyGraph.add("NotInGraph"));
        //Check that it did not mutate the graph
        assertEquals(NonEmptyGraph.vertices(), ExpectedAddition);

    }

    @Test
    public void testSetSSourcesTargetsMethods() {

        //This method tests the set method. However, as a byproduct of testing whether it mutates the object,
        //we also have a pretty good test of the sources() and targets() methods.

        Graph<String> TestGraph = emptyInstance();

        TestGraph.add("FirstLabel");
        TestGraph.add("SecondLabel");

        //Test that there are no edges
        assertEquals(TestGraph.sources("SecondLabel"), Collections.emptyMap());

        //There is not such edge. source, target, weight
        assertEquals(TestGraph.set("FirstLabel", "SecondLabel", 10),0);

        HashMap<String, Integer> ExpectedSources = new HashMap<String, Integer>();
        ExpectedSources.put("FirstLabel", 10);
        assertEquals(TestGraph.sources("SecondLabel"), ExpectedSources);

        HashMap<String, Integer> ExpectedTargets = new HashMap<String, Integer>();
        ExpectedTargets.put("SecondLabel", 10);
        assertEquals(TestGraph.sources("FirstLabel"), ExpectedTargets);

        //There IS such an edge and we change it.
        assertEquals(TestGraph.set("FirstLabel", "SecondLabel", 4),10);

        HashMap<String, Integer> ExpectedEditedSources = new HashMap<String, Integer>();
        ExpectedEditedSources.put("FirstLabel", 4);
        assertEquals(TestGraph.sources("SecondLabel"), ExpectedEditedSources);

        HashMap<String, Integer> ExpectedEditedTargets = new HashMap<String, Integer>();
        ExpectedEditedTargets.put("SecondLabel", 4);
        assertEquals(TestGraph.sources("FirstLabel"), ExpectedEditedTargets);

        //There is not such vertices, we create them
        assertEquals(TestGraph.set("NewLabel", "AnotherLabel", 99),0);

        HashMap<String, Integer> ExpectedCreatedSources = new HashMap<String, Integer>();
        ExpectedCreatedSources.put("NewLabel", 99);
        assertEquals(TestGraph.sources("AnotherLabel"), ExpectedCreatedSources);

        HashMap<String, Integer> ExpectedCreatedTargets = new HashMap<String, Integer>();
        ExpectedCreatedTargets.put("AnotherLabel", 99);
        assertEquals(TestGraph.sources("NewLabel"), ExpectedCreatedTargets);

        //Weight is 0 so we delete the edge
        assertEquals(TestGraph.set("FirstLabel", "SecondLabel", 0),4);

        assertEquals(TestGraph.sources("SecondLabel"), Collections.emptyMap());

        assertEquals(TestGraph.sources("FirstLabel"), Collections.emptyMap());

    }

    @Test
    public void testEmptySourcesTargetsMethods() {

        Graph<String> TestGraph = emptyInstance();

        assertEquals(TestGraph.sources("MissingLabel"), Collections.emptyMap());
        assertEquals(TestGraph.targets("MissingLabel"), Collections.emptyMap());

    }
    
}
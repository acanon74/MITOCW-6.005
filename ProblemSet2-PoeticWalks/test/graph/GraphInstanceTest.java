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
//TODO getTargets and getSources must have package access
/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 *
 * PS: I did add the method utilMutabilityTest(), this was only to follow DRY. We could
 * have done without but mindless code repetition goes against the course's teachings :D
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    /**
     * Preconditions:
     * Labels type must be immutable.
     * Weight must be >= 1.
     *
     * Special cases:
     * empty graph.
     *
     * Test strategy:
     * We test every method of the class against an empty and non-empty graph.
     * We also, check whether they mutated the object.
     *
     * Breakdown of every test used:
     *
     * testVerticesMethod()
     * We instantiate TestGraph as empty and then add some labels to it, then we test whether the object was mutated.
     *
     * testAddMethod()
     * We instantiate EmptyGraph, we will check addition in the following states:
     * - When EmptyGraph is empty.
     * - When EmptyGraph already contains some vertices.
     * - Try to add() a pre-existing label, we should not modify and return false.
     *
     * In every step we check whether the object was modified or not.
     *
     * testRemoveMethod()
     * We instantiate TestGraph as empty. And add() some labels.
     * To test the constructor we test whether we have initial edges.
     *
     *
     * Giving the specification, we should test for the following inputs:
     *
     * - Add an edge connecting existing vertices.
     * - Changing an edge connecting existing vertices.
     * - Add an edge connecting missing vertices, therefore creating the vertices and edge.
     * - The inputted weight is zero, we delete the edge.
     *
     * In every case we check whether the object was modified or not.
     *
     * testEmptySourcesTargetsMethods()
     * We instantiate TestGraph as an empty Graph.
     * We test sources() and targets() on the empty object, both of which should return and empty map.
     * We add an edge connecting existing vertices.
     * We change an existent edge.
     * We add an edge connecting missing vertices, we add such vertices.
     * We set() using weight = 0. We delete the given edge.
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
        assertEquals("TEST INCLUDED IN COURSE: expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testVerticesMethod() {

        Graph<String> TestGraph = emptyInstance();

        assertEquals(TestGraph.vertices(), Collections.emptySet());


        HashSet<String> ExpectedVertices = new HashSet<String>();

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

        //It added and mutated the object
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
        //Check that last test did not mutate the graph
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

        //It removed and mutated the object
        assertEquals(NonEmptyGraph.vertices(), ExpectedAddition);


        //If it doesn't contain it, return false
        assertFalse(NonEmptyGraph.remove("NotInGraph"));
        //Check that it did not mutate the graph
        assertEquals(NonEmptyGraph.vertices(), ExpectedAddition);
    }

    static void utilMutabilityTest(Graph<String> graph, String source, String target, int weight){

        if(weight != 0) {
            HashMap<String, Integer> ExpectedSources = new HashMap<>();
            ExpectedSources.put(source, weight);
            assertEquals(ExpectedSources, graph.sources(target));

            HashMap<String, Integer> ExpectedTargets = new HashMap<>();
            ExpectedTargets.put(target, weight);
            assertEquals(ExpectedTargets, graph.targets(source));
        }
    }

    @Test
    public void testSetSSourcesTargetsMethods() {

        //This method tests the set method. However, as a byproduct of testing whether it mutates the object,
        //we also have a pretty good test of the sources() and targets() methods.

        Graph<String> TestGraph = emptyInstance();
        TestGraph.add("FirstLabel");
        TestGraph.add("SecondLabel");

        //Test that there are no edges
        assertEquals(TestGraph.targets("FirstLabel"), Collections.emptyMap());
        assertEquals(TestGraph.sources("SecondLabel"), Collections.emptyMap());

        //Initially there is not such edge. We add it.
        assertEquals(TestGraph.set("FirstLabel", "SecondLabel", 10),0);

        utilMutabilityTest(TestGraph, "FirstLabel", "SecondLabel", 10);

        //There IS such an edge and we change it.
        assertEquals(TestGraph.set("FirstLabel", "SecondLabel", 4),10);

        utilMutabilityTest(TestGraph, "FirstLabel", "SecondLabel", 4);

        //There is not such vertices, we create them
        assertEquals(TestGraph.set("NewLabel", "AnotherLabel", 99),0);

        utilMutabilityTest(TestGraph, "NewLabel", "AnotherLabel", 99);

        //TODO write cyclic graphs test and two edges in bilateral direction s>t t>s.
        //Create an Edge between a used vertex and a free vertex.
        assertEquals(TestGraph.set("FirstLabel", "NewLabel", 2), 0);

        HashMap<String, Integer> ExpectedSources = new HashMap<>();
        ExpectedSources.put("FirstLabel", 2);
        assertEquals(TestGraph.sources("NewLabel"), ExpectedSources);

        HashMap<String, Integer> ExpectedTargets = new HashMap<>();
        ExpectedTargets.put("SecondLabel", 4);
        ExpectedTargets.put("NewLabel", 2);
        assertEquals(TestGraph.targets("FirstLabel"), ExpectedTargets);

        //Weight is 0 so we delete the edge
        assertEquals(TestGraph.set("FirstLabel", "SecondLabel", 0),4);
        assertEquals(TestGraph.set("FirstLabel", "NewLabel", 0),2);
        assertEquals(TestGraph.sources("SecondLabel"), Collections.emptyMap());
        assertEquals(TestGraph.targets("FirstLabel"), Collections.emptyMap());
    }
    @Test
    public void testSelfMutualReference() {

        //Test a bilateral reference. this points to that and that points to this.
        Graph<String> testGraph = emptyInstance();
        testGraph.add("FirstLabel");
        testGraph.add("SecondLabel");

        testGraph.set("FirstLabel", "SecondLabel", 1);
        testGraph.set("SecondLabel", "FirstLabel", 1);

        utilMutabilityTest(testGraph, "FirstLabel", "SecondLabel", 1);

        testGraph.remove("FirstLabel");

        Set<String> expectedOutput = new HashSet<>();
        expectedOutput.add("SecondLabel");

        assertEquals(expectedOutput, testGraph.vertices());

        //Test a cyclic reference. A cycle of 3 Vertex.
        Graph<String> cycleGraph = emptyInstance();
        cycleGraph.add("FirstLabel");
        cycleGraph.add("SecondLabel");
        cycleGraph.add("ThirdLabel");

        cycleGraph.set("FirstLabel", "SecondLabel", 1);
        cycleGraph.set("SecondLabel", "ThirdLabel", 1);
        cycleGraph.set("ThirdLabel", "FirstLabel", 1);

        utilMutabilityTest(cycleGraph, "FirstLabel", "SecondLabel", 1);
        utilMutabilityTest(cycleGraph, "SecondLabel", "ThirdLabel", 1);
        utilMutabilityTest(cycleGraph, "ThirdLabel", "FirstLabel", 1);

        cycleGraph.remove("FirstLabel");
        cycleGraph.remove("SecondLabel");
        cycleGraph.remove("ThirdLabel");

        assertEquals(Collections.emptySet(), cycleGraph.vertices());
    }

    @Test
    public void testMultipleEdges() {

        Graph<String> testGraph = emptyInstance();
        String firstLabel = "FirstLabel";
        testGraph.add("FirstLabel");
        String secondLabel = "SecondLabel";
        testGraph.add("SecondLabel");
        String thirdLabel = "ThirdLabel";
        testGraph.add("ThirdLabel");
        String fourthLabel = "FourthLabel";
        testGraph.add("FourthLabel");

        testGraph.set(firstLabel, secondLabel, 1);
        testGraph.set(firstLabel, thirdLabel, 2);
        testGraph.set(firstLabel, fourthLabel, 3);

        testGraph.set(fourthLabel, firstLabel, 5);
        testGraph.set(fourthLabel, secondLabel, 6);
        testGraph.set(fourthLabel, thirdLabel, 7);

        testGraph.remove(firstLabel);

        utilMutabilityTest(testGraph, firstLabel, secondLabel, 0);
        utilMutabilityTest(testGraph, firstLabel, thirdLabel, 0);
        utilMutabilityTest(testGraph, firstLabel, fourthLabel, 0);

        Set<String> expectedOutput = new HashSet<>();
        expectedOutput.add(secondLabel);
        expectedOutput.add(thirdLabel);
        expectedOutput.add(fourthLabel);

        assertEquals(expectedOutput, testGraph.vertices());
    }
}
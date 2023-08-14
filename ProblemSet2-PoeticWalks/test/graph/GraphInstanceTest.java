/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 * <p>
 * PS: I did add the method utilMutabilityTest(), this was only to follow DRY. We could
 * have done without but mindless code repetition goes against the course's teachings :D
 */
public abstract class GraphInstanceTest {

    /**
     * Preconditions:
     * Labels type must be immutable.
     * A vertex cannot be a source or target of itself.
     * Weight must be >= 1.
     * <p>
     * Special cases:
     * empty graph.
     * <p>
     * Test strategy:
     * We test every method of the class against an empty and non-empty graph.
     * We also, check whether they mutated the object.
     * <p>
     * Breakdown of every test used:
     * <p>
     * verticesMethodTest()
     * We instantiate TestGraph as empty and then add some labels to it, then we test whether the object was mutated.
     * <p>
     * addMethodTest()
     * We instantiate EmptyGraph, we will check addition in the following states:
     * - When EmptyGraph is empty.
     * - When EmptyGraph already contains some vertices.
     * - Try to add() a pre-existing label, we should not modify and return false.
     * <p>
     * In every step we check whether the object was modified or not.
     * <p>
     * removeMethodTest()
     * We instantiate TestGraph as empty. And add() some labels.
     * To test the constructor we test whether we have initial edges.
     * <p>
     * In every step we check whether the object was modified or not.
     * <p>
     * Giving the specification, fot the method set() we should test for the following inputs:
     * <p>
     * - Add an edge connecting existing vertices.
     * - Changing an edge connecting existing vertices.
     * - Add an edge connecting missing vertices, therefore creating the vertices and edge.
     * - The inputted weight is zero, we delete the edge.
     * <p>
     * In every case we check whether the object was modified or not.
     * <p>
     * testEmptySourcesTargetsMethods()
     * We instantiate TestGraph as an empty Graph.
     * We test sources() and targets() on the empty object, both of which should return and empty map.
     * We add an edge connecting existing vertices.
     * We change an existent edge.
     * We add an edge connecting missing vertices, we add such vertices.
     * We set() using weight = 0. We delete the given edge.
     * <p>
     * selfMutualReferenceTest()
     * We test a graph in which to vertices point at each other.
     * We add the two edges.
     * We remove() a vertex.
     * remove() should remove the vertex and its edges. We check whether this
     * modified the object.
     * <p>
     * - We test a cycle in the graph.
     * We instantiate a cycle consisting of 3 vertices.
     * We add edges pointing at each other.
     * We remove one vertex.
     * We test whether this modified the object, and removed its edges.
     * This test is important for my implementation of ConcreteVerticesGraph. Given the fact
     * that each vertex stores its edges, cycles could lead to transversing the cycle and
     * getting into an infinity loop or trying to remove() a null reference.
     * <p>
     * multipleEdgesTest()
     * We test instantiating a vertex with multiple outgoing and incoming edges.
     * remove() should delete the vertex and all its edges properly.
     * This test is important since both of my implementations depend on hashing to guarantee
     * that there is only one edge per pair of vertices, but multiple edges for different pairs.
     * vertices are still allowed.
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
    public void verticesMethodTest() {

        Graph<String> testGraph = emptyInstance();

        assertEquals(Collections.emptySet(), testGraph.vertices());


        HashSet<String> expectedVertices = new HashSet<>();

        testGraph.add("FirstLabel");
        testGraph.add("SecondLabel");
        expectedVertices.add("FirstLabel");
        expectedVertices.add("SecondLabel");

        assertEquals(expectedVertices, testGraph.vertices());
    }

    @Test
    public void addMethodTest() {
        //Empty Graph
        Graph<String> emptyGraph = emptyInstance();

        assertTrue(emptyGraph.add("NewLabel"));

        HashSet<String> expectedEmptyAddition = new HashSet<>();
        expectedEmptyAddition.add("NewLabel");

        //It added and mutated the object
        assertEquals(expectedEmptyAddition, emptyGraph.vertices());


        //NonEmpty Graph
        Graph<String> nonEmptyGraph = emptyInstance();
        nonEmptyGraph.add("AlreadyInLabel");

        assertTrue(nonEmptyGraph.add("TestLabel"));

        HashSet<String> expectedAddition = new HashSet<>();
        expectedAddition.add("AlreadyInLabel");
        expectedAddition.add("TestLabel");

        assertEquals(expectedAddition, nonEmptyGraph.vertices());


        //If already in, return false
        assertFalse(nonEmptyGraph.add("AlreadyInLabel"));
        //Check that last test did not mutate the graph
        assertEquals(expectedAddition, nonEmptyGraph.vertices());
    }

    @Test
    public void removeMethodTest() {
        //Empty Graph
        Graph<String> emptyGraph = emptyInstance();

        assertFalse(emptyGraph.remove("MissingLabel"));


        //NonEmpty Graph
        Graph<String> nonEmptyGraph = emptyInstance();
        nonEmptyGraph.add("AlreadyInLabel");
        nonEmptyGraph.add("AnotherLabel");

        assertTrue(nonEmptyGraph.remove("AlreadyInLabel"));

        HashSet<String> expectedAddition = new HashSet<>();
        expectedAddition.add("AnotherLabel");

        //It removed and mutated the object
        assertEquals(expectedAddition, nonEmptyGraph.vertices());


        //If it doesn't contain it, return false
        assertFalse(nonEmptyGraph.remove("NotInGraph"));
        //Check that it did not mutate the graph
        assertEquals(expectedAddition, nonEmptyGraph.vertices());
    }

    static void utilMutabilityTest(Graph<String> graph, String source, String target, int weight){

        if(weight != 0) {
            HashMap<String, Integer> expectedSources = new HashMap<>();
            expectedSources.put(source, weight);
            assertEquals(expectedSources, graph.sources(target));

            HashMap<String, Integer> expectedTargets = new HashMap<>();
            expectedTargets.put(target, weight);
            assertEquals(expectedTargets, graph.targets(source));
        }
    }

    @Test
    public void setSourcesTargetsMethodsTest() {

        //This method tests the set method. However, as a byproduct of testing whether it mutates the object,
        //we also have a pretty good test of the sources() and targets() methods.

        Graph<String> testGraph = emptyInstance();
        testGraph.add("FirstLabel");
        testGraph.add("SecondLabel");

        //Test that there are no edges
        assertEquals(Collections.emptyMap(), testGraph.targets("FirstLabel"));
        assertEquals(Collections.emptyMap(), testGraph.sources("SecondLabel"));

        //Initially there is not such edge. We add it.
        assertEquals(0, testGraph.set("FirstLabel", "SecondLabel", 10));

        utilMutabilityTest(testGraph, "FirstLabel", "SecondLabel", 10);

        //There IS such an edge and we change it.
        assertEquals(10, testGraph.set("FirstLabel", "SecondLabel", 4));

        utilMutabilityTest(testGraph, "FirstLabel", "SecondLabel", 4);

        //There is not such vertices, we create them
        assertEquals(0, testGraph.set("NewLabel", "AnotherLabel", 99));

        utilMutabilityTest(testGraph, "NewLabel", "AnotherLabel", 99);

        //Create an Edge between a used vertex and a free vertex.
        assertEquals(0, testGraph.set("FirstLabel", "NewLabel", 2));

        HashMap<String, Integer> expectedSources = new HashMap<>();
        expectedSources.put("FirstLabel", 2);
        assertEquals(expectedSources, testGraph.sources("NewLabel"));

        HashMap<String, Integer> expectedTargets = new HashMap<>();
        expectedTargets.put("SecondLabel", 4);
        expectedTargets.put("NewLabel", 2);
        assertEquals(expectedTargets, testGraph.targets("FirstLabel"));

        //Weight is 0 so we delete the edge
        assertEquals(4, testGraph.set("FirstLabel", "SecondLabel", 0));
        assertEquals(2, testGraph.set("FirstLabel", "NewLabel", 0));
        assertEquals(Collections.emptyMap(), testGraph.sources("SecondLabel"));
        assertEquals(Collections.emptyMap(), testGraph.targets("FirstLabel"));
    }
    @Test
    public void selfMutualReferenceTest() {

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
    public void multipleEdgesTest() {

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

//TODO Update docs to reflect object L, K, etc.
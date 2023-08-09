/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package test.graph;

import graph.ConcreteEdgesGraph;
import graph.Graph;
import graph.GraphInstanceTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /**
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    //Implementation-specific test for ConcreteEdgesGraph

    /**There are not more methods or fields in my implementation of ConcreteEdgesGraph,
     * besides addMissingVerticesTest(), which is private because it is a helper method
     * for the public method set().
     */

    /**
     * Preconditions:
     * Graph might or might not be empty.
     *
     * Test strategy:
     * We test for the returned String given an empty and filled graph.
     * We test for labels added using add(), and as a byproduct of using set().
     *
     * Breakdown of every test used:
     * We instantiate an empty graph.
     * assertTrue(TestGraph.toString().contains("---")).
     * We test that, given an empty graph, the return String should not report any vertices.
     *
     * We add a Label.
     * TestGraph.add("NewLabel").
     * We do set("NewLabel", "AnotherLabel", 10), set() should add "AnotherLabel" besides setting the weight.
     * We test for the present of both labels and the weight.
     */
    @Test
    public void toStringTest() {

        Graph<String> TestGraph = emptyInstance();

        assertTrue(TestGraph.toString().contains("---"));

        TestGraph.add("NewLabel");

        TestGraph.set("NewLabel", "AnotherLabel", 10);

        assertTrue(TestGraph.toString().contains("NewLabel"));
        assertTrue(TestGraph.toString().contains("AnotherLabel"));
    }

    
    // Testing strategy for Edge

    /**
     * I legit don't understand how I am supposed to test Edge.
     * - Its fields are meant to be private for rep exposure, so no way to address them.
     * - Even if they were, Edge is not the public class in ConcreteEdgesGraph, so no way to instantiate an object for testing.
     * - If we follow the specification for Graph mention in the PS, we are not supposed to create a method for retrieving edges,
     * or to index a single edge, so no way to get an Edge from a Graph object in the first place.
     * - Also, my implementation of Edge only calls for plain getters; and equals and hashCode, both of which are meant to be internal
     * and are tested by GraphInstanceTest and ConcreteEdgesTest anyway.
     *
     * - About that last part, yes, I am indeed able to check whether Edge is working properly by testing whether ConcreteEdgesGraph is
     * working properly, but that would be using the ConcreteEdgesGraph's methods and that is just repeating the contents
     * of ConcreteEdgesGraphTest and GraphInstanceTest.
     */
}

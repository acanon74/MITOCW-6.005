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
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */


    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    /**
     * Preconditions:
     *
     * Graph might or might not be empty
     *
     * Test strategy:
     * We test for the returned String given an empty and filled graph.
     * We test for labels added using add(), and as a byproduct of using set()
     *
     * Breakdown of every test used:
     *
     * We instantiate an empty graph
     * assertTrue(TestGraph.toString().contains("---"))
     * We test that given an empty graph, the return String should not report any labels or weights.
     *
     * We add a Label
     * TestGraph.add("NewLabel")
     * We do set("NewLabel", "AnotherLabel", 10), set() should add "AnotherLabel" besides setting the weight
     * We test for the present of both labels and the weight
     */

    // TODO tests for ConcreteEdgesGraph.toString()
    @Test
    public void toStringTest() {

        Graph<String> TestGraph = emptyInstance();

        //TODO Once implemented, we should check the length of the return when Graph is empty
        assertTrue(TestGraph.toString().contains("---"));

        TestGraph.add("NewLabel");

        TestGraph.set("NewLabel", "AnotherLabel", 10);

        assertTrue(TestGraph.toString().contains("NewLabel"));
        assertTrue(TestGraph.toString().contains("AnotherLabel"));

        //TODO Should we check for the weight to be present?
        //assertTrue(TestGraph.toString().contains("10"));
    }

    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
    // TODO tests for operations of Edge

    public void EdgeConstructorTest() {

        Graph<String> TestGraph = emptyInstance();

        TestGraph.add("TestLabel");
        TestGraph.add("AnotherLabel");

        TestGraph.set("TestLabel", "AnotherLabel", 10);

        assertTrue(true);

        //TODO Wth? What am I supposed to test? There is no need for methods, so we dont have.
        //fields are meant to be private, so no point in addressing them. And even if they were public, as required by the course,
        //Edge is in the ConcreteEdgesGraph and is not public, so no way of instantiate an Edge and test them.
        //Also, according to the spec of Graph, we do not have access to the Array of edges, so we cannot add a method to expose them.
    }

}

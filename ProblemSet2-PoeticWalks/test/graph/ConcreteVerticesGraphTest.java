/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package test.graph;
import graph.ConcreteVerticesGraph;
import graph.Graph;
import graph.GraphInstanceTest;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    /**
     * Only graph methods? for now at least...
     */


    // TODO tests for ConcreteVerticesGraph.toString()
    @Test
    public void toStringTest() {

        Graph<String> TestGraph = emptyInstance();

        assertTrue(TestGraph.toString().contains("---"));

        TestGraph.add("NewLabel");

        TestGraph.set("NewLabel", "AnotherLabel", 10);

        System.out.println(TestGraph.toString());

        assertTrue(TestGraph.toString().contains("NewLabel"));
        assertTrue(TestGraph.toString().contains("AnotherLabel"));
    }


    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    
    // TODO tests for operations of Vertex
    
}

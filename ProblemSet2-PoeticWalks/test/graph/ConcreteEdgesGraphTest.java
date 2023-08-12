/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import org.junit.Test;
import static org.junit.Assert.*;

import graph.ConcreteEdgesGraph;
import graph.Graph;
import graph.GraphInstanceTest;

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
     * besides addMissingVertices(), which is private because it is a helper method
     * for the public method set().
     */

    /**ConcreteEdgesGraph Tests
     *
     * Preconditions:
     * Graph might or might not be empty.
     *
     * Test strategy:
     * We test for the returned String given an empty and filled graph.
     * We test for labels added using add(), and as a byproduct of using set().
     *
     * Breakdown of every test used:
     * toStringTest()
     * We instantiate an empty graph.
     * assertTrue(TestGraph.toString().contains("---")).
     * We test that, given an empty graph, the return String should not report any vertices.
     *
     */
    @Test
    public void toStringTest() {

        Graph<String> testGraph = emptyInstance();

        assertTrue(testGraph.toString().contains("---"));

        testGraph.add("NewLabel");

        testGraph.set("NewLabel", "AnotherLabel", 10);

        assertTrue(testGraph.toString().contains("NewLabel"));
        assertTrue(testGraph.toString().contains("AnotherLabel"));
    }

    /**Edge Tests
     *
     * Preconditions:
     * source and target vertices must be different.
     * weight must be >= 1.
     *
     * Test strategy:
     * We test the behavior of every method of the class Edge. Using objects
     * constructed with and without the weight parameter.
     *
     * Breakdown of every test used:
     *
     * edgeConstructorTest()
     * We test the constructor without the weight parameter.
     * We test the constructor with the weight parameter.
     *
     * edgeGetSourceTest()
     * We test the getSource() method in Edge constructed with and without the weight parameter.
     *
     * edgeGetTargetTest()
     * We test the getTarget() method in Edge constructed with and without the weight parameter.
     *
     * edgeGetWeightTest()
     * We test the getWeight() method in Edge constructed with and without the weight parameter.
     *
     * edgeToStringTest()
     * We use both constructors and test for specific outputs given certain vertices.
     *
     * edgeEqualsTest()
     * We test that the equals() method is independent of the weight field. As long as
     * the source and target vertices do coincide.
     * We test both constructors.
     *
     * edgeHashCodeTest()
     * We test that the hashCode() method is independent of the weight field. As long as
     * the source and target vertices do coincide.
     * We test both constructors.
     */

    @Test
    public void edgeConstructorTest() {
        ConcreteEdgesGraph.Edge edgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");

        assertEquals("SourceLabel", edgeNoWeight.getSource());
        assertEquals("TargetLabel", edgeNoWeight.getTarget());
        assertEquals(1, edgeNoWeight.getWeight());


        ConcreteEdgesGraph.Edge edgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);

        assertEquals("SourceLabel", edgeWeight.getSource());
        assertEquals("TargetLabel", edgeWeight.getTarget());
        assertEquals(99, edgeWeight.getWeight());
    }

    @Test
    public void edgeGetSourceTest() {
        ConcreteEdgesGraph.Edge edgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        assertEquals("SourceLabel", edgeNoWeight.getSource());

        ConcreteEdgesGraph.Edge edgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        assertEquals("SourceLabel", edgeWeight.getSource());
    }

    @Test
    public void edgeGetTargetTest() {
        ConcreteEdgesGraph.Edge edgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        assertEquals("TargetLabel", edgeNoWeight.getTarget());

        ConcreteEdgesGraph.Edge edgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        assertEquals("TargetLabel", edgeWeight.getTarget());
    }

    @Test
    public void edgeGetWeightTest() {
        ConcreteEdgesGraph.Edge edgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        assertEquals(1, edgeNoWeight.getWeight());

        ConcreteEdgesGraph.Edge edgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        assertEquals(99, edgeWeight.getWeight());
    }

    @Test
    public void edgeToStringTest() {
        ConcreteEdgesGraph.Edge edgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        assertEquals("(SourceLabel-->TargetLabel,1)", edgeNoWeight.toString());

        ConcreteEdgesGraph.Edge edgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        assertEquals("(SourceLabel-->TargetLabel,99)", edgeWeight.toString());

    }

    @Test
    public void edgeEqualsTest() {
        ConcreteEdgesGraph.Edge edgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge equalEdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge diffEdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "DifferentLabel");

        assertTrue(edgeNoWeight.equals(equalEdgeNoWeight));
        assertFalse(edgeNoWeight.equals(diffEdgeNoWeight));


        ConcreteEdgesGraph.Edge edgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge equalEdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge diffEdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "DifferentLabel");

        assertTrue(edgeWeight.equals(equalEdgeWeight));
        assertFalse(edgeWeight.equals(diffEdgeWeight));
    }

    @Test
    public void edgeHashCodeTest() {
        ConcreteEdgesGraph.Edge edgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge equalEdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge diffEdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "DifferentLabel");

        assertTrue(edgeNoWeight.hashCode() == equalEdgeNoWeight.hashCode());
        assertFalse(edgeNoWeight.hashCode() == diffEdgeNoWeight.hashCode());


        ConcreteEdgesGraph.Edge edgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        ConcreteEdgesGraph.Edge equalEdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        ConcreteEdgesGraph.Edge diffEdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "DifferentLabel", 99);

        assertTrue(edgeWeight.hashCode() == equalEdgeWeight.hashCode());
        assertFalse(edgeWeight.hashCode() == diffEdgeWeight.hashCode());
    }
}
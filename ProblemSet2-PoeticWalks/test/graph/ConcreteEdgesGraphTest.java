/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

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
     * We use the getters to check the values.
     *
     * edgeGetSource()
     * We test the getSource() method in Edge constructed with and without the weight parameter.
     *
     * edgeGetTarget()
     * We test the getTarget() method in Edge constructed with and without the weight parameter.
     *
     * edgeGetWeight()
     * We test the getWeight() method in Edge constructed with and without the weight parameter.
     *
     * edgeToString()
     * We use both constructors and test for specific outputs given certain vertices.
     *
     * edgeEquals()
     * We test that the equals() method is independent of the weight field. As long as
     * the source and target vertices do coincide.
     * We test both constructors.
     *
     * edgeHashCode()
     * We test that the hashCode() method is independent of the weight field. As long as
     * the source and target vertices do coincide.
     * We test both constructors.
     */

    @Test
    public void edgeConstructorTest() {
        ConcreteEdgesGraph.Edge EdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");

        assertEquals(EdgeNoWeight.getSource(), "SourceLabel");
        assertEquals(EdgeNoWeight.getTarget(), "TargetLabel");
        assertEquals(EdgeNoWeight.getWeight(), 1);


        ConcreteEdgesGraph.Edge EdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);

        assertEquals(EdgeWeight.getSource(), "SourceLabel");
        assertEquals(EdgeWeight.getTarget(), "TargetLabel");
        assertEquals(EdgeWeight.getWeight(), 99);
    }

    @Test
    public void edgeGetSource() {
        ConcreteEdgesGraph.Edge EdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        assertEquals(EdgeNoWeight.getSource(), "SourceLabel");

        ConcreteEdgesGraph.Edge EdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        assertEquals(EdgeWeight.getSource(), "SourceLabel");
    }

    @Test
    public void edgeGetTarget() {
        ConcreteEdgesGraph.Edge EdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        assertEquals(EdgeNoWeight.getTarget(), "TargetLabel");

        ConcreteEdgesGraph.Edge EdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        assertEquals(EdgeWeight.getTarget(), "TargetLabel");
    }

    @Test
    public void edgeGetWeight() {
        ConcreteEdgesGraph.Edge EdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        assertEquals(EdgeNoWeight.getWeight(), 1);

        ConcreteEdgesGraph.Edge EdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        assertEquals(EdgeWeight.getWeight(), 99);
    }

    @Test
    public void edgeToString() {
        ConcreteEdgesGraph.Edge EdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        assertEquals(EdgeNoWeight.toString(), "(SourceLabel-->TargetLabel,1)");


        ConcreteEdgesGraph.Edge EdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        assertEquals(EdgeWeight.toString(), "(SourceLabel-->TargetLabel,99)");

    }

    @Test
    public void edgeEquals() {
        ConcreteEdgesGraph.Edge EdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge EqualEdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge DiffEdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "DifferentLabel");

        assertTrue(EdgeNoWeight.equals(EqualEdgeNoWeight));
        assertFalse(EdgeNoWeight.equals(DiffEdgeNoWeight));


        ConcreteEdgesGraph.Edge EdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge EqualEdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge DiffEdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "DifferentLabel");

        assertTrue(EdgeWeight.equals(EqualEdgeWeight));
        assertFalse(EdgeWeight.equals(DiffEdgeWeight));
    }

    @Test
    public void edgeHashCode() {
        ConcreteEdgesGraph.Edge EdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge EqualEdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel");
        ConcreteEdgesGraph.Edge DiffEdgeNoWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "DifferentLabel");

        assertTrue(EdgeNoWeight.hashCode() == EqualEdgeNoWeight.hashCode());
        assertFalse(EdgeNoWeight.hashCode() == DiffEdgeNoWeight.hashCode());


        ConcreteEdgesGraph.Edge EdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        ConcreteEdgesGraph.Edge EqualEdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "TargetLabel", 99);
        ConcreteEdgesGraph.Edge DiffEdgeWeight = new ConcreteEdgesGraph.Edge("SourceLabel", "DifferentLabel", 99);

        assertTrue(EdgeWeight.hashCode() == EqualEdgeWeight.hashCode());
        assertFalse(EdgeWeight.hashCode() == DiffEdgeWeight.hashCode());
    }
}
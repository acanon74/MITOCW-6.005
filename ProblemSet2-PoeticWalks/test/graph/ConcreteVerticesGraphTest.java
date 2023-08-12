/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;
import graph.ConcreteVerticesGraph;
import graph.Graph;
import graph.GraphInstanceTest;

import static org.junit.Assert.*;
import org.junit.Test;
import poet.Main;

import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /**
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
    //TODO ConcreteVerticesGraph methods

    // TODO tests for ConcreteVerticesGraph.toString()
    //TODO interchange expected and actual values in the test for ConcreteEdgesGraph.
    @Test
    public void toStringTest() {

        Graph<String> TestGraph = emptyInstance();

        assertTrue(TestGraph.toString().contains("---"));

        TestGraph.add("NewLabel");

        TestGraph.set("NewLabel", "AnotherLabel", 10);

        assertTrue(TestGraph.toString().contains("NewLabel"));
        assertTrue(TestGraph.toString().contains("AnotherLabel"));
    }


    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    /**Vertex Tests
     *
     * Preconditions:
     * source and target must be different.
     * A vertex can't be source or target of itself.
     * Label must be immutable.
     * weight >= 1.
     *
     * Test strategy:
     * Breakdown of every test used:
     *
     * VertexGetLabel()
     * We instantiate an object using constructors, then we check for the
     * correctness of the label.
     *
     * VertexGetConnectedVertices()
     * We instantiate a main object. We add some Edge objects, with
     * the main object acting both as source and as target.
     * We check that all the objects report their connections properly.
     *
     * VertexIsSourceOf()
     * We instantiate a main object. We add some Edge objects, with
     * the main object acting both as source and as target.
     * We test that the main object is source only of those object for which
     * it is a source and not target.
     * It should be a source of a missing vertex.
     *
     * VertexIsTargetOf()
     * We instantiate a main object. We add some Edge objects, with
     * the main object acting both as source and as target.
     * We test that the main object is target only of those object for which
     * it is a target and not source.
     * It should be a target of a missing vertex.
     *
     * VertexHasEdgeWith()
     * We instantiate a main object. We add some Edge objects, with
     * the main object acting both as source and as target.
     * We test that the main object reports as connected to all the vertices.
     * We test that it reports not connected with non-connected vertices.
     * Cannot have an Edge with itself.
     *
     * VertexHashCode()
     * We test that hashCode() computes the same hash for equal objects, and
     * does not include the weight, source, target fields in the hash
     * computation, only the label field.
     *
     * VertexEquals()
     * Vertices should test equal() if their labels are the same.
     * Otherwise, test false.
     *
     * VertexHashCode()
     * Vertices should have the same hashCode() if their labels are the same.
     * Otherwise, test false.
     */
    
    // TODO tests for operations of Vertex
    //ConcreteEdgesGraph.Edge Edge = new ConcreteEdgesGraph.Edge();
    @Test
    public void VertexGetLabelTest() {

        ConcreteVerticesGraph.Vertex TestVertex = new ConcreteVerticesGraph.Vertex("TestLabel");
        assertEquals("TestLabel", TestVertex.getLabel());
    }
    @Test
    public void VertexGetConnectedVertices() {

        ConcreteVerticesGraph.Vertex MainVertex = new ConcreteVerticesGraph.Vertex("MainVertex");

        ConcreteVerticesGraph.Vertex SourceVertex = new ConcreteVerticesGraph.Vertex("SourceVertex");
        SourceVertex.setEdge(MainVertex);

        ConcreteVerticesGraph.Vertex TargetVertex = new ConcreteVerticesGraph.Vertex("TargetVertex");
        MainVertex.setEdge(TargetVertex);


        Set<ConcreteVerticesGraph.Vertex> ExpectedSet = new HashSet<>();
        ExpectedSet.add(TargetVertex);
        ExpectedSet.add(SourceVertex);

        //Main has 2 connections
        assertEquals(ExpectedSet, MainVertex.getConnectedVertices().keySet());


        Set<ConcreteVerticesGraph.Vertex> ExpectedSingle = new HashSet<ConcreteVerticesGraph.Vertex>();
        ExpectedSingle.add(MainVertex);

        //Source has 1 connection
        assertEquals(ExpectedSingle, SourceVertex.getConnectedVertices().keySet());
        //Target has 1 connection
        assertEquals(ExpectedSingle, TargetVertex.getConnectedVertices().keySet());
    }

    @Test
    public void VertexIsSourceOf() {

        ConcreteVerticesGraph.Vertex MainVertex = new ConcreteVerticesGraph.Vertex("MainVertex");

        ConcreteVerticesGraph.Vertex SourceVertex = new ConcreteVerticesGraph.Vertex("SourceVertex");
        SourceVertex.setEdge(MainVertex);

        ConcreteVerticesGraph.Vertex TargetVertex = new ConcreteVerticesGraph.Vertex("TargetVertex");
        MainVertex.setEdge(TargetVertex);

        ConcreteVerticesGraph.Vertex MissingVertex = new ConcreteVerticesGraph.Vertex("MissingVertex");


        assertTrue(MainVertex.isSourceOf(TargetVertex));
        assertFalse(MainVertex.isSourceOf(SourceVertex));
        //It is not source of a missing vertex
        assertFalse(MainVertex.isSourceOf(MissingVertex));
    }

    @Test
    public void VertexIsTargetOf() {

        ConcreteVerticesGraph.Vertex MainVertex = new ConcreteVerticesGraph.Vertex("MainVertex");

        ConcreteVerticesGraph.Vertex SourceVertex = new ConcreteVerticesGraph.Vertex("SourceVertex");
        SourceVertex.setEdge(MainVertex);

        ConcreteVerticesGraph.Vertex TargetVertex = new ConcreteVerticesGraph.Vertex("TargetVertex");
        MainVertex.setEdge(TargetVertex);

        ConcreteVerticesGraph.Vertex MissingVertex = new ConcreteVerticesGraph.Vertex("MissingVertex");


        assertTrue(MainVertex.isTargetOf(SourceVertex));
        assertFalse(MainVertex.isTargetOf(TargetVertex));
        //It is not target of a missing vertex
        assertFalse(MainVertex.isTargetOf(MissingVertex));
    }

    @Test
    public void VertexHasEdgeWith() {

        ConcreteVerticesGraph.Vertex MainVertex = new ConcreteVerticesGraph.Vertex("MainVertex");

        ConcreteVerticesGraph.Vertex SourceVertex = new ConcreteVerticesGraph.Vertex("SourceVertex");
        SourceVertex.setEdge(MainVertex);

        ConcreteVerticesGraph.Vertex TargetVertex = new ConcreteVerticesGraph.Vertex("TargetVertex");
        MainVertex.setEdge(TargetVertex);

        ConcreteVerticesGraph.Vertex MissingVertex = new ConcreteVerticesGraph.Vertex("MissingVertex");


        assertTrue(MainVertex.hasEdgeWith(SourceVertex));
        assertTrue(MainVertex.hasEdgeWith(TargetVertex));
        //It doesn't have an Edge with a missing vertex
        assertFalse(MainVertex.hasEdgeWith(MissingVertex));
        //Cannot have an Edge with itself.
        assertFalse(MainVertex.hasEdgeWith(MainVertex));
    }

    @Test
    public void VertexEquals() {

        ConcreteVerticesGraph.Vertex Vertex = new ConcreteVerticesGraph.Vertex("Vertex");
        ConcreteVerticesGraph.Vertex EqualVertex = new ConcreteVerticesGraph.Vertex("Vertex");
        ConcreteVerticesGraph.Vertex DiffVertex = new ConcreteVerticesGraph.Vertex("DiffVertex");

        assertTrue(Vertex.equals(EqualVertex));
        assertFalse(Vertex.equals(DiffVertex));
    }

    @Test
    public void VertexHashCode() {

        ConcreteVerticesGraph.Vertex Vertex = new ConcreteVerticesGraph.Vertex("Vertex");
        ConcreteVerticesGraph.Vertex EqualVertex = new ConcreteVerticesGraph.Vertex("Vertex");
        ConcreteVerticesGraph.Vertex DiffVertex = new ConcreteVerticesGraph.Vertex("DiffVertex");

        assertTrue(Vertex.hashCode() == EqualVertex.hashCode());
        assertFalse(Vertex.hashCode() == DiffVertex.hashCode());
    }

    @Test
    public void VertexToString() {

        String mainLabel = "MainVertex";
        ConcreteVerticesGraph.Vertex MainVertex = new ConcreteVerticesGraph.Vertex(mainLabel);

        String sourceLabel = "SourceVertex";
        ConcreteVerticesGraph.Vertex SourceVertex = new ConcreteVerticesGraph.Vertex(sourceLabel);
        SourceVertex.setEdge(MainVertex);

        String targetLabel = "TargetVertex";
        ConcreteVerticesGraph.Vertex TargetVertex = new ConcreteVerticesGraph.Vertex(targetLabel);
        MainVertex.setEdge(TargetVertex);

        ArrayList<String> expectedSources = new ArrayList<>();
        expectedSources.add(sourceLabel);

        ArrayList<String> expectedTargets = new ArrayList<>();
        expectedTargets.add(targetLabel);


        String expectedString = "[" + mainLabel + "]" + " Sources: " + expectedSources.toString()
                + " Targets: " + expectedTargets.toString();

        assertEquals(expectedString, MainVertex.toString());
    }
}
//TODO add test for the private methods addTarget, removeTarget, addSource, removeSource.

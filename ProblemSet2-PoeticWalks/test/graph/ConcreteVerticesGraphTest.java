/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import graph.ConcreteEdgesGraph;
import graph.Graph;
import graph.GraphInstanceTest;

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

    /**There are not more methods or fields in my implementation of ConcreteVerticesGraph,
     * besides getEdges(), which is private because it is a helper method
     * for the public method toString();
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

    /*
     * Testing Vertex...
     */

    /**Vertex Tests
     *
     * Preconditions:
     * source and target must be different.
     * A vertex can't be source or target of itself.
     * Label must be immutable.
     * weight >= 1.
     *
     * Test strategy:
     *
     *
     * Breakdown of every test used:
     *
     * VertexGetLabelTest()
     * We instantiate an object using constructors, then we check for the
     * correctness of the label.
     *
     * VertexGetConnectedVerticesTest()
     * We instantiate a main object. We add some Edge objects, with
     * the main object acting both as source and as target.
     * We check that all the objects report their connections properly.
     *
     * SetEdgeTest()
     * We test setting an Edge with and without the weight parameter.
     * The method must change the edge's weight if weight >= 1. And
     * delete the edge if weight = 0.
     *
     * VertexIsSourceOfTest()
     * We instantiate a main object. We add some Edge objects, with
     * the main object acting both as source and as target.
     * We test that the main object is source only of those object for which
     * it is a source and not target.
     * It should be a source of a missing vertex.
     *
     * VertexIsTargetOfTest()
     * We instantiate a main object. We add some Edge objects, with
     * the main object acting both as source and as target.
     * We test that the main object is target only of those object for which
     * it is a target and not source.
     * It should be a target of a missing vertex.
     *
     * VertexHasEdgeWithTest()
     * We instantiate a main object. We add some Edge objects, with
     * the main object acting both as source and as target.
     * We test that the main object reports as connected to all the vertices.
     * We test that it reports not connected with non-connected vertices.
     * Cannot have an Edge with itself.
     *
     * VertexHashCodeTest()
     * We test that hashCode() computes the same hash for equal objects, and
     * does not include the weight, source, target fields in the hash
     * computation, only the label field.
     *
     * VertexEqualsTest()
     * Vertices should test equal() if their labels are the same.
     * Otherwise, test false.
     *
     */

    @Test
    public void vertexGetLabelTest() {

        ConcreteVerticesGraph.Vertex TestVertex = new ConcreteVerticesGraph.Vertex("TestLabel");
        assertEquals("TestLabel", TestVertex.getLabel());
    }

    @Test
    public void vertexSetEdgeTest() {
        //With default method, set weight to 1.
        ConcreteVerticesGraph.Vertex sourceObject = new ConcreteVerticesGraph.Vertex("sourceObject");
        ConcreteVerticesGraph.Vertex targetObject = new ConcreteVerticesGraph.Vertex("targetObject");

        sourceObject.setEdge(targetObject);

        HashMap<ConcreteVerticesGraph.Vertex, Integer> expectedTargets = new HashMap<>();
        expectedTargets.put(targetObject, 1);

        HashMap<ConcreteVerticesGraph.Vertex, Integer> expectedSources = new HashMap<>();
        expectedSources.put(sourceObject, 1);

        assertEquals(expectedSources, targetObject.getSources());
        assertEquals(expectedTargets, sourceObject.getTargets());

        //Change the weight to 77.
        sourceObject.setEdge(targetObject, 77);

        expectedSources.put(sourceObject, 77);
        expectedTargets.put(targetObject, 77);

        assertEquals(expectedTargets, sourceObject.getTargets());
        assertEquals(expectedSources, targetObject.getSources());

        //Delete edge using weight = 0.
        sourceObject.setEdge(targetObject, 0);

        expectedSources.remove(sourceObject);
        expectedTargets.remove(targetObject);

        assertEquals(expectedTargets, sourceObject.getTargets());
        assertEquals(expectedSources, targetObject.getSources());
    }

    @Test
    public void vertexGetConnectedVerticesTest() {

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
    public void vertexIsSourceOfTest() {

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
    public void vertexIsTargetOfTest() {

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
    public void vertexHasEdgeWithTest() {

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
    public void vertexEqualsTest() {

        ConcreteVerticesGraph.Vertex Vertex = new ConcreteVerticesGraph.Vertex("Vertex");
        ConcreteVerticesGraph.Vertex EqualVertex = new ConcreteVerticesGraph.Vertex("Vertex");
        ConcreteVerticesGraph.Vertex DiffVertex = new ConcreteVerticesGraph.Vertex("DiffVertex");

        assertTrue(Vertex.equals(EqualVertex));
        assertFalse(Vertex.equals(DiffVertex));
    }

    @Test
    public void VertexHashCodeTest() {

        ConcreteVerticesGraph.Vertex Vertex = new ConcreteVerticesGraph.Vertex("Vertex");
        ConcreteVerticesGraph.Vertex EqualVertex = new ConcreteVerticesGraph.Vertex("Vertex");
        ConcreteVerticesGraph.Vertex DiffVertex = new ConcreteVerticesGraph.Vertex("DiffVertex");

        assertTrue(Vertex.hashCode() == EqualVertex.hashCode());
        assertFalse(Vertex.hashCode() == DiffVertex.hashCode());
    }

    @Test
    public void VertexToStringTest() {

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


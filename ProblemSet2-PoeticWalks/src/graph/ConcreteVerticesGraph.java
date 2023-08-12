/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import javax.swing.plaf.synth.SynthTextAreaUI;
import javax.swing.text.TableView;
import java.util.*;

/**
 * An implementation of Graph.
 */
public class ConcreteVerticesGraph implements Graph<String> {

    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO

    /**
     * Abstraction function:
     * Represents a graph; storing mutable object Vertex. Vertex objects store edges themselves.
     *
     * Representation invariant:
     * A Vertex object is stored only once in vertices.
     *
     * Safety from rep exposure:
     * all fields are private and final.
     */

    /**
     * TODO specification
     * Represents a vertex from Graph, with the given label.
     * It stores the vertices it is connected with (both as source and as target) in two maps.
     * Where the key is the connected vertex, and the value is the weight of the edge.
     * It is mutable.
     *
     * @param label The label of this vertex.
     * @param sourcesMap HashMap with the source vertex as key, and as value the edge's weight.
     * @param targetsMap HashMap with the target vertex as key, and as value the edge's weight.
     *
     * @return A mutable object representing a vertex from the data structure Graph.
     */
    static class Vertex {

        private final String label;
        private final HashMap<Vertex, Integer> sourcesMap = new HashMap<>();
        private final HashMap<Vertex, Integer> targetsMap = new HashMap<>();

        /**
         * We could use a 2x2 hashmap as we did with the other implementation, this would give constant
         * access to weights and checking for edges. Here it makes sense to implement it as
         * a static field in the vertex class. However, this pretty much offloads everything from the
         * objects themselves, this is kind of pointless for the PS, which requires vertex to be mutable, so we should
         * address it to make changes and stuff rather than just getting its label. So I have decided to implement vertex by
         * caching sources and targets directly into every object. This gives constant access to sources and
         * targets, but it's much worse in storage, because now each of both vertices in an edge store
         * this relationship rather than being shared knowledge. But as you can see this solution is way more interesting
         * for the given PS.
         *
         * HashMap<hashFromBothObjects, Weight>;
         * */

        /**
         * Abstraction function:
         * Given a label, represents a mutable Vertex in the Graph implementation ConcreteVerticesGraph.
         * A vertex also stores its edges.
         *
         * Representation invariant:
         * label must be immutable.
         * A vertex cannot be a source or target of itself.
         * all weights must be >= 1.
         *
         * Safety from rep exposure:
         * All fields are private and final.
         * getConnectedVertices() returns a defensive copy.
         */

        public Vertex(String label) {
            this.label = label;
            checkRep();
        }


        private void checkRep() {

            HashMap<Vertex, Integer> copyOfAll = new HashMap<>();
            copyOfAll.putAll(sourcesMap);
            copyOfAll.putAll(targetsMap);

            for(Vertex vertex : copyOfAll.keySet()) {
                assert !this.equals(vertex);
                assert copyOfAll.get(vertex) >= 1;
            }
        }


        public String getLabel() {
            return this.label;
        }


        /**
         * Instantiates a HashMap containing shallow copies of sourcesMap and targetsMap and returns it.
         *
         * @return A Map containing shallow deep copies of sourcesMap and targetsMap.
         */
        //TODO this method doing a shallow copy breaks rep exposure, we must implement deep copy.
        public Map<Vertex, Integer> getConnectedVertices() {
            HashMap<Vertex, Integer> copyOfVertices = new HashMap<>();
            copyOfVertices.putAll(sourcesMap);
            copyOfVertices.putAll(targetsMap);
            return copyOfVertices;
        }


        /**
         * This method performs bilateral mutation of vertices connected by an Edge.
         * This method supports addition, change and deletion of Edges depending on
         * the value of weight.
         * If there was no such Edge, we created it. If there was an Edge, we update the value.
         * If weight = 0 then it performs deletion of the edge.
         * This method mutates both objects.
         *
         * this object is the source vertex, target is the target vertex.
         * The use overloading of the method to get set the default weight to be 1.
         *
         * @param target Vertex object to be connected with this.
         * @param weight weight of the Edge to be added, changed or deleted.
         * @return 0 if there was not such an Edge. Otherwise, it returns the old weight.
         */
        public int setEdge(Vertex target, int weight) {

            if(this.equals(target)) {
                throw new RuntimeException("A vertex cannot be a source or target of itself");
            }

            if(weight >= 1) {
                target.addSource(this, weight);
                checkRep();
                return this.addTarget(target, weight);

            } else if (weight == 0) {
                target.removeSource(this);
                checkRep();
                return this.removeTarget(target);

            } else {
                throw new RuntimeException("weight must be >= 1");
            }
        }
        public int setEdge(Vertex target) {
            return this.setEdge(target, 1);
        }


        /**
         * This method put() the given vertex in targetsMap if it is not already in.
         * If it is already, we change the weight. Minimum weight is 1, and we construct with it
         * as default by overloading the addTarget() method.
         *
         * weight should be >= 1.
         *
         * @param vertex a Vertex object to be added to targetsMap.
         * @param weight the weight of the edge to be represented.
         * @return 0 if there was not such an Edge. Otherwise, it returns the old weight.
         */
        private int addTarget(Vertex vertex, Integer weight) {

            Integer originalWeight = targetsMap.get(vertex);
            targetsMap.put(vertex, weight);
            checkRep();
            return (originalWeight == null) ? 0 : originalWeight;
        }


        /**
         * This method remove() the given vertex in targetsMap.
         * returns whether there was such am target in targetsMap.
         *
         * @param vertex The Vertex object to be removed from targetsMap.
         * @return 0 if there was not such an Edge. Otherwise, it returns the old weight.
         */
        private int removeTarget(Vertex vertex) {

            Integer originalWeight = targetsMap.get(vertex);
            targetsMap.remove(vertex);
            checkRep();
            return (originalWeight == null) ? 0 : originalWeight;
        }

        //TODO test for addTarget, addSource, removeTarget, removeSource, addEdge, removeEdge, setEdge.
        /**
         * This method put() the given vertex in sourcesMap if it is not already in.
         * If it is already, we change the weight. Minimum weight is 1, and we construct with it
         * as default by overloading the addSource() method.
         *
         * weight should be >= 1.
         *
         * @param vertex a Vertex object to be added to sourcesMap.
         * @param weight the weight of the edge to be represented.
         * @return true 0 if there was not such an Edge. Otherwise, it returns the old weight.
         */
        private int addSource(Vertex vertex, Integer weight) {

            Integer originalWeight = sourcesMap.get(vertex);
            sourcesMap.put(vertex, weight);
            checkRep();
            return (originalWeight == null) ? 0 : originalWeight;
        }


        /**
         * This method remove() the given vertex in sourcesMap.
         * returns whether there was such am target in sourcesMap.
         *
         * @param vertex The Vertex object to be removed from sourcesMap.
         * @return true 0 if there was not such an Edge. Otherwise, it returns the old weight.
         */
        private int removeSource(Vertex vertex) {

            Integer originalWeight = sourcesMap.get(vertex);
            sourcesMap.remove(vertex);
            checkRep();
            return (originalWeight == null) ? 0 : originalWeight;
        }


        /**
         * Given a vertex, we check whether this is a source of vertex.
         *
         * @param vertex Vertex object we checked whether it is a target of this.
         * @return true if vertex is a target of this, otherwise false.
         */
        public boolean isSourceOf(Vertex vertex) {
            return targetsMap.containsKey(vertex);
        }

        /**
         * Given a vertex, we check whether this is a target of vertex.
         *
         *  @param vertex Vertex we checked whether it is a source of this.
         * @return ture if vertex is a source of this, otherwise false.
         */
        public boolean isTargetOf(Vertex vertex) {
            return sourcesMap.containsKey(vertex);
        }

        /**
         * Given a vertex, we check whether there is an edge connecting this and vertex.
         * If there is an edge, this method returns true, regardless of whether this is a
         * source or target in the edge. Otherwise, returns false.
         *
         * @param vertex Vertex we checked whether it is connected with this.
         * @return true if there is an edge, otherwise false. It doesn't matter if this is source or target.
         */
        public boolean hasEdgeWith(Vertex vertex) {
            return getConnectedVertices().containsKey(vertex);
        }


        /**
         * Following the specification of Graph, labels must be unique, that is,
         * the label field is an identifier for Vertex objects. Therefore, we shall
         * compare Vertex using its label exclusively. Also, ConcreteVerticesGraph
         * implementation does not allow to store multiple objects with the same label.
         *
         * @param obj Vertex object to compared with.
         * @return true if obj and this label field are the same. Otherwise, false.
         */
        @Override
        public boolean equals(Object obj){

            if(obj == null) {
                return false;
            }

            if(obj.getClass() != this.getClass()) {
                return false;
            }
            return Objects.equals(this.label, ((Vertex) obj).label);
        }


        /**
         * Following the specification of Graph, labels must be unique, that is,
         * the label field is an identifier of a Vertex object. Therefore, we shall
         * hash Vertex using label and no other field.
         *
         * Calculates hashCode() using this object label.
         *
         * @return int which correspond with the unique identifier of this object.
         */
        @Override
        public int hashCode() {
            return this.label.hashCode();
        }


        public String toString() {

            List<String> test = new ArrayList<>();

            List<String> testy = new ArrayList<>();


            for(Vertex vertex : sourcesMap.keySet()) {
                test.add(vertex.getLabel());
            }
            for(Vertex vertex : targetsMap.keySet()) {
                testy.add(vertex.getLabel());
            }

            return "[" + this.label + "]" + " Sources: " + test.toString() + " Targets: " + testy.toString();
        }
    }

    private final List<Vertex> vertices = new ArrayList<>();

    // TODO constructor
    public ConcreteVerticesGraph() {}
    
    // TODO checkRep
    private void checkRep() {
        //If every vertex is only once, then hashSet length and ArrayList length must be equal.
        HashSet<Vertex> uniqueVertices = new HashSet<>();
        uniqueVertices.addAll(vertices);
        assert uniqueVertices.size() == vertices.size();
    }


    @Override public boolean add(String vertex) {
        return vertices.add(new Vertex(vertex));
    }


    @Override public int set(String source, String target, int weight) {

        int sourceIndex = vertices.indexOf(source);
        int targetIndex = vertices.indexOf(target);

        if(sourceIndex == -1) {
            vertices.add(new Vertex(source));
        }
        if(targetIndex == -1) {
            vertices.add(new Vertex(target));
        }

        Vertex sourceVertex = vertices.get(sourceIndex);
        Vertex targetVertex = vertices.get(targetIndex);

        int originalWeight = sourceVertex.setEdge(targetVertex, weight);
        checkRep();
        return originalWeight;
    }
    
    @Override public boolean remove(String vertex) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Set<String> vertices() {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> sources(String target) {
        throw new RuntimeException("not implemented");
    }
    
    @Override public Map<String, Integer> targets(String source) {
        throw new RuntimeException("not implemented");
    }
    
    // TODO toString()
    @Override public String toString() {
        throw new RuntimeException("not implemented");
    }
    
}

/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph.
 */
public class ConcreteVerticesGraph implements Graph<String> {

    /**
     * Abstraction function:
     * Represents a graph; storing mutable object Vertex. Vertex objects store its edges themselves.
     *
     * Representation invariant:
     * A Vertex object is stored only once in vertices.
     *
     * Safety from rep exposure:
     * all fields are private and final.
     */

    /**
     * Represents a vertex from Graph, with the given label.
     * It stores the vertices it is connected with (both as source and as target) in two maps,
     * where the key is the connected vertex, and the value is the weight of the edge.
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
         * A vertex also stores a representation of its edges.
         *
         * Representation invariant:
         * label must be immutable.
         * A vertex cannot be a source or target of itself.
         * all weights must be >= 1.
         *
         * Safety from rep exposure:
         * All fields are private and final.
         * getConnectedVertices() returns a defensive copy.
         * getSources and getTargets allow package-level access and return a shallow copy.
         *
         * !!! getConnectedVertices(), getSources() and getTargets() should return a deep copy to prevent rep exposure. I did not implement that.
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
         * This method implements a shallow copy, but it really should implement a deep copy instead.
         *
         * @return A Map containing shallow deep copies of sourcesMap and targetsMap joined.
         */
        public Map<Vertex, Integer> getConnectedVertices() {
            HashMap<Vertex, Integer> copyOfVertices = new HashMap<>();
            copyOfVertices.putAll(sourcesMap);
            copyOfVertices.putAll(targetsMap);
            return copyOfVertices;
        }

        /**
         * Instantiates a HashMap containing shallow copies of sourcesMap and returns it.
         * This method implements a shallow copy, but it really should implement a deep copy instead.
         *
         * @return A Map containing shallow deep copies of sourcesMap.
         */
        Map<Vertex, Integer> getSources() {
            HashMap<Vertex, Integer> copyOfSources = new HashMap<>();
            copyOfSources.putAll(sourcesMap);
            return copyOfSources;
        }

        /**
         * Instantiates a HashMap containing shallow copies of targetsMap and returns it.
         * This method implements a shallow copy, but it really should implement a deep copy instead.
         *
         * @return A Map containing shallow deep copies of targetsMap.
         */
        Map<Vertex, Integer> getTargets() {
            HashMap<Vertex, Integer> copyOfTargets = new HashMap<>();
            copyOfTargets.putAll(targetsMap);
            return copyOfTargets;
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
         * The use method overloading to get set the default weight to be 1.
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
         * as default by overloading addTarget().
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

        /**
         * This method put() the given vertex in sourcesMap if it is not already in.
         * If it is already, we change the weight. Minimum weight is 1, and we construct with it
         * as default by overloading addSource().
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

    public ConcreteVerticesGraph() {}

    private void checkRep() {
        //If every vertex is only once, then hashSet length and ArrayList length must be equal.
        HashSet<Vertex> uniqueVertices = new HashSet<>();
        uniqueVertices.addAll(vertices);
        assert uniqueVertices.size() == vertices.size();
    }

    @Override public boolean add(String vertex) {

        boolean wasInside = vertices.contains(new Vertex(vertex));

        if(wasInside) {
            checkRep();
            return false;
        } else {
            vertices.add(new Vertex(vertex));
            checkRep();
            return true;
        }
    }

    @Override
    public int set(String source, String target, int weight) {

        int sourceIndex = vertices.indexOf(new Vertex(source));
        int targetIndex = vertices.indexOf(new Vertex(target));

        if(sourceIndex == -1) {
            this.add(source);
        }
        if(targetIndex == -1) {
            this.add(target);
        }

        sourceIndex = vertices.indexOf(new Vertex(source));
        targetIndex = vertices.indexOf(new Vertex(target));

        Vertex sourceVertex = vertices.get(sourceIndex);
        Vertex targetVertex = vertices.get(targetIndex);

        int originalWeight = sourceVertex.setEdge(targetVertex, weight);
        checkRep();
        return originalWeight;
    }
    
    @Override
    public boolean remove(String vertex) {

        int vertexIndex = vertices.indexOf(new Vertex(vertex));

        if(vertexIndex != -1) {
            Vertex vertexObject = vertices.get(vertexIndex);

            for(Vertex source : vertexObject.getSources().keySet()) {
                source.setEdge(vertexObject, 0);
            }
            for(Vertex target : vertexObject.getTargets().keySet()) {
                vertexObject.setEdge(target, 0);
            }

            vertices.remove(vertexIndex);
            checkRep();
            return true;
        } else {
            checkRep();
            return false;
        }
    }
    
    @Override
    public Set<String> vertices() {

        Set<String> result = new HashSet<>();
        for(Vertex vertex : vertices) {
            result.add(vertex.getLabel());
        }
        return result;
    }
    
    @Override
    public Map<String, Integer> sources(String target) {

        int targetIndex = vertices.indexOf(new Vertex(target));
        Vertex targetVertex = vertices.get(targetIndex);
        Map<String, Integer> result = new HashMap<>();

        for(Map.Entry<Vertex, Integer> entry : targetVertex.getSources().entrySet()) {
            result.put(entry.getKey().getLabel(), entry.getValue());
        }
        return result;
    }

    @Override public Map<String, Integer> targets(String source) {

        int sourceIndex = vertices.indexOf(new Vertex(source));
        Vertex sourceVertex = vertices.get(sourceIndex);
        Map<String, Integer> result = new HashMap<>();

        for(Map.Entry<Vertex, Integer> entry : sourceVertex.getTargets().entrySet()) {
            result.put(entry.getKey().getLabel(), entry.getValue());
        }
        return result;
    }

    /**
     * his method retrieves all the edges from every vertex in vertices.
     * And adds them to the List only once, after checking connected vertices.
     *
     * @return a List containing all the edges in printable version.
     */
    private List<String> getEdges() {

        /**
         * By building the strings and using them as a form of hashing, and then
         * using a HashSet we guarantee that we will obtain a single string per
         * edge even if we check connected vertices.
         */

        HashSet<String> uniqueEdges = new HashSet<>();
        //Build edges
        for(Vertex vertex : vertices) {
            //build targets
            for(Map.Entry<Vertex, Integer> entry : vertex.getTargets().entrySet()) {
                String targetLabel = entry.getKey().getLabel();
                int edgeWeight = entry.getValue();
                uniqueEdges.add("(" + vertex.getLabel() + "-->" + targetLabel + ", " + edgeWeight + ")");
            }
            //build sources
            for(Map.Entry<Vertex, Integer> entry : vertex.getSources().entrySet()) {
                String sourceLabel = entry.getKey().getLabel();
                int edgeWeight = entry.getValue();
                uniqueEdges.add("(" + vertex.getLabel() + "-->" + sourceLabel + ", " + edgeWeight + ")");
            }
        }
        return new ArrayList<>(uniqueEdges);
    }

    @Override
    public String toString() {
        return this.vertices().toString() + " --- " + this.getEdges().toString();
    }
}

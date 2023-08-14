/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;
/**
 * An implementation of Graph.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

    /**
     * Given source, target and weight, Edge represents a triplet that contains the given parameters. Edge is immutable.
     * source and target are stored in a Pair object for hashing and indexing purposes.
     *
     * @param source The label corresponding to the source vertex.
     * @param target The label corresponding to the target vertex.
     * @param weight The weight of the edge, must be >= 1.
     * <p>
     * Read <a href="https://en.wikipedia.org/wiki/Directed_graph">...</a>
     * @return An immutable triplet containing the source, target and weight.
     */
    static class Edge<K> {

        private final Pair<K> ConnectedVertices;
        private final int weight;

        /**
         * Abstraction function:
         * represents the triplet (source, target, weight).
         * <p>
         * Representation invariant:
         * source and target must be different vertices.
         * weight must be an integer >= 1.
         * <p>
         * Safety from rep exposure:
         * Pair, which contains source and target, is private and immutable.
         * weight is private and immutable.
         * */

        public Edge(K source, K target){
            this(source, target, 1);
        }

        public Edge(K source, K target, int weight) {

            if(weight >= 1) {
                this.weight = weight;
            } else {
                throw new RuntimeException("weight must be >= 1");
            }

            if(source != target) {
                ConnectedVertices = new Pair<>(source, target);
            } else {
                throw new RuntimeException("source and target vertices must be different");
            }
            checkRep();
        }

        private void checkRep(){
            assert !Objects.equals(this.getSource(), this.getTarget());
            assert this.weight >= 1;
        }

        public Object getSource(){
            return ConnectedVertices.a;
        }
        public Object getTarget(){
            return ConnectedVertices.b;
        }
        public int getWeight(){
            return this.weight;
        }

        public String toString() {
            return "(" + this.getSource() + "-->" + this.getTarget() + "," + this.getWeight() + ")";
        }

        /**
         * We check that the objects are of the sane type, and finally that their connectedVertices fields have the
         * same source, target data.
         *
         * @param obj Another Edge object to compare connectedVertices
         * @return whether the field connectedVertices reports the same hash as the obj
         */
        @Override
        public boolean equals(Object obj){

            if(obj == null) {
                return false;
            }

            if(obj.getClass() != this.getClass()) {
                return false;
            }

            final Edge<K> other = (Edge<K>) obj;
            return this.ConnectedVertices.equals(other.ConnectedVertices);
        }

        /**
         * Due to how ConcreteEdgesGraph.set() works, which replaces weight if there is a
         * pre-existing edge and adds it otherwise, we must not include weight in our hash function in order to index
         * Edges based on their connected vertices only.
         * Therefore, we index an Edge object based on the hash of its Pair, which hashes the pair (source, target).
         *
         * @return the hash corresponding to the Pair of the Edge object.
         */
        @Override
        public int hashCode(){
            return ConnectedVertices.hashCode();
        }
    }

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    public ConcreteEdgesGraph(){}

    private void checkRep() {
        for(Edge<L> edge : edges) {
            assert (!Objects.equals(edge.getSource(), edge.getTarget()));
            assert (vertices.contains(edge.getSource()) && vertices.contains(edge.getTarget()));
            assert (edge.getWeight() >= 1);
        }
    }

    @Override public boolean add(L vertex) {

        if(!vertices.contains(vertex)) {
            vertices.add(vertex);
            checkRep();
            return true;
        } else {
            checkRep();
            return false;
        }
    }

    /**
     * Checks whether there are vertices with the given source and target labels.
     * The method mutates the object by adding the missing labels,
     * otherwise doesn't modify the vertices object.
     * <p>
     * The method mutates the vertices object.
     *
     * @param source label to be checked for or added.
     * @param target label to be checked for or added.
     */
    private void addMissingVertices(L source, L target) {
        if(!vertices.contains(source)) {
            vertices.add(source);
        }
        if(!vertices.contains(target)) {
            vertices.add(target);
        }
        checkRep();
    }

    @Override public int set(L source, L target, int weight) {

        Edge<L> keyObject = new Edge<>(source, target);
        int indexSearch = edges.indexOf(keyObject);

        if(weight >= 1) {

            addMissingVertices(source, target);

            //check for existence of edge
            if(indexSearch != -1) {

                Edge<L> OriginalEdge = edges.get(indexSearch);
                int originalWeight = OriginalEdge.getWeight();

                edges.remove(keyObject);
                edges.add(new Edge<>(source, target, weight));

                checkRep();
                return originalWeight;

            } else {
                edges.add(new Edge<>(source, target, weight));

                checkRep();
                return 0;
            }

        } else if(weight == 0) {

            //check for existence of edge
            if(indexSearch != -1) {

                Edge<L> OriginalEdge = edges.get(indexSearch);
                int originalWeight = OriginalEdge.getWeight();

                edges.remove(keyObject);

                checkRep();
                return originalWeight;

            } else {
                throw new RuntimeException("There is no such edge");
            }
        } else {
            throw new RuntimeException("Weight must be non-negative");
        }
    }

    @Override public boolean remove(L vertex) {

        boolean wasInside = vertices.contains(vertex);
        //if vertex existed, check and remove edges
        if(wasInside) {

            ArrayList<Edge<L>> copyEdges = new ArrayList<>(edges);

            for(Edge<L> pair : copyEdges) {
                if((Objects.equals(pair.getSource(), vertex)) || (Objects.equals(pair.getTarget(), vertex))) {
                    edges.remove(pair);
                }
            }
            vertices.remove(vertex);
        }
        checkRep();
        return wasInside;
    }
    
    @Override public Set<L> vertices() {
        return Set.copyOf(vertices);
    }

    @Override public Map<L, Integer> sources(L target) {

        HashMap<L, Integer> foundEdges = new HashMap<>();

        for(Edge<L> edge : edges) {

            if(edge.getTarget() == target) {
                foundEdges.put((L) edge.getSource(), edge.getWeight());
            }
        }
        checkRep();
        return foundEdges;
    }
    
    @Override public Map<L, Integer> targets(L source) {

        HashMap<L, Integer> foundEdges = new HashMap<>();

        for(Edge<L> edge : edges) {

            if(edge.getSource() == source) {
                foundEdges.put((L) edge.getTarget(), edge.getWeight());
            }
        }
        checkRep();
        return foundEdges;
    }

    @Override
    public String toString() {
        return (vertices + " --- " + edges);
    }
}
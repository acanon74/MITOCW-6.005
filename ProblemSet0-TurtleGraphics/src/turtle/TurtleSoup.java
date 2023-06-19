/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {

        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {

        if (sides <= 2) {
            throw new RuntimeException("Expected sides > 2");
        } else {
            return 180.0-(360.0/sides);
        }
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {

        if (angle < 60) {
            throw new RuntimeException("Cannot get a regular polygon from given angle");
        } else {
            return (int) java.lang.Math.ceil(360.0 / (180.0 - angle));
        }
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {

        final double turnAngle = calculateRegularPolygonAngle(sides);

        for(int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(turnAngle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {

        if ((targetX == currentX) && (targetY == currentY)) {
            return currentHeading;
        } else {
            double currentHeadingRadians = java.lang.Math.PI/2 - java.lang.Math.toRadians(currentHeading);
            double headingCorrection = java.lang.Math.atan2((targetY-currentY), (targetX-currentX));
            double newHeading = java.lang.Math.toDegrees(currentHeadingRadians - headingCorrection);

            //Handle corrections where we calculate a counter-clockwise turn.
            return (newHeading >= 0) ? newHeading : 360.0 + newHeading;
        }
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {

        if (xCoords.size() != yCoords.size()) {
            throw new RuntimeException("The length of both coordinates lists must be the same");

        } else if (xCoords.isEmpty()) {
            return new ArrayList<Double>(0);
        } else {

            ArrayList<Double> listHeadings = new ArrayList<Double>();

            int currentX = xCoords.get(0);
            int currentY = yCoords.get(0);
            double currentHeading = 0.0;

            for(int i = 1; i < xCoords.size(); i++) {

                currentHeading = calculateHeadingToPoint(currentHeading, currentX, currentY, xCoords.get(i), yCoords.get(i));

                listHeadings.add(currentHeading);
                currentX = xCoords.get(i);
                currentY = yCoords.get(i);
            }

            return listHeadings;
        }
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {

        int stepSize = 7;
        turtle.color(PenColor.RED);

        for(int i = 0; i < 300; i++) {
            if (i % 2 == 0) {
                drawSquare(turtle, 6*stepSize);
                stepSize = 5;
                turtle.color(PenColor.RED);
            } else if (i % 5 == 0) {
                stepSize = 2;
                turtle.turn(30);
                turtle.forward(stepSize);
                turtle.color(PenColor.GREEN);
            } else if (i % 3 == 0) {
                stepSize = 7;
                turtle.turn(15);
                drawRegularPolygon(turtle, 9, stepSize);
                turtle.color(PenColor.MAGENTA);
            }

            else {
                turtle.forward(stepSize);
                turtle.turn(90+ i);
                turtle.color(PenColor.BLUE);
            }
            }
        }




    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);
        drawPersonalArt(turtle);
        // draw the window
        turtle.draw();

    }

}

//Name: Ekachai Suriyakriengkri
//USC NetID: suriyakr@usc.edu
//CS 455 PA3
//Spring 2018

import java.util.LinkedList;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).

   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls

 */

public class Maze {

    // Variable declaration
    public static final boolean FREE = false;
    public static final boolean WALL = true;

    private static LinkedList<MazeCoord> path;
    private static boolean[][] mazeData;
    private static boolean[][] visitedLoc;
    private static MazeCoord startLoc;
    private static MazeCoord exitLoc;
    /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments above for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param exitLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

     */
    public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) {
        this.mazeData = mazeData;
        this.startLoc = startLoc;
        this.exitLoc = exitLoc;
        this.path = new LinkedList<MazeCoord>();
    }


    /**
      Returns the number of rows in the maze
      @return number of rows
     */
    public int numRows() {
        return this.mazeData.length;
    }


    /**
      Returns the number of columns in the maze
      @return number of columns
     */   
    public int numCols() {
        return this.mazeData[0].length;
    } 


    /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
     */
    public boolean hasWallAt(MazeCoord loc) {
        if (this.mazeData[loc.getRow()][loc.getCol()] == true)
            return true;
        return false;
    }


    /**
      Returns the entry location of this maze.
     */
    public MazeCoord getEntryLoc() {
        return this.startLoc;
    }


    /**
     Returns the exit location of this maze.
     */
    public MazeCoord getExitLoc() {
        return this.exitLoc;
    }


    /**
      Returns the path through the maze. First element is start location, and
      last element is exit location.  If there was not path, or if this is called
      before a call to search, returns empty list.

      @return the maze path
     */
    public LinkedList<MazeCoord> getPath() {
        return this.path;
    }


    /**
      Find a path from start location to the exit location (see Maze
      constructor parameters, startLoc and exitLoc) if there is one.
      Client can access the path found via getPath method.
      - This not a recursion, just set up reference matrix for validating the visited path
      - Get entry location and call pathFinder method for finding the path recursively .
      @return whether a path was found.
     */
    public boolean search()  {

        this.path.clear();

        // Creating reference matrix for checking already visited location
        // Setting every element to false (never visited before)
        visitedLoc = new boolean[numRows()][numCols()];
        for (int i = 0; i < numRows(); i++)
        {
            for (int j = 0; j < numCols(); j++)
            {
                visitedLoc[i][j] = false;
            }
        }
        
        MazeCoord currentLoc = new MazeCoord (startLoc.getRow(), startLoc.getCol()); // Get entry location

        return pathFinder(currentLoc);

    }
    
    // Recursion method for finding the path
    // Base on 3 base cases, finding the path by moving left, right, up, down from entry location
    // Moving on each direction until hit the wall, hit the visited path, or hit the exit
    // return false for otherwise cases (no path)
    
    private boolean pathFinder (MazeCoord currentLoc) {
        // Base cases
        // If currentLoc is wall set reference matrix with currentLoc to true (marking as visited)
        if (this.hasWallAt(currentLoc))
        {
            this.visitedLoc[currentLoc.getRow()][currentLoc.getCol()] = true;
            return false;
        }
        // If reference matrix with currentLoc location have value as true (This location had already visited)
        if (this.visitedLoc[currentLoc.getRow()][currentLoc.getCol()] == true)
            return false;
        // Final position, currentLoc has the same value as exit location
        if (currentLoc.equals(this.getExitLoc()))
        {
            path.addFirst(currentLoc);
            return true;
        }

        // Recursive Cases
        else
        {
            this.visitedLoc[currentLoc.getRow()][currentLoc.getCol()] = true; // Marking this currentLoc is visited then begin searching

            // Search through top to bottom row
            if (currentLoc.getRow() < this.numRows() - 1)
            {
                MazeCoord adjacentLoc = new MazeCoord (currentLoc.getRow() + 1, currentLoc.getCol()); 
                if (pathFinder(adjacentLoc))
                {
                    this.path.addFirst(currentLoc);
                    return true;
                }
            }

            // Search through right to left column
            if (currentLoc.getCol() > 0)
            {
                MazeCoord adjacentLoc = new MazeCoord (currentLoc.getRow(), currentLoc.getCol()-1);
                if (pathFinder(adjacentLoc))
                {
                    this.path.addFirst(currentLoc);
                    return true;
                }
            }
            // Search through bottom to top row
            if (currentLoc.getRow() > 0)
            {
                MazeCoord adjacentLoc = new MazeCoord (currentLoc.getRow()-1, currentLoc.getCol());        
                if (pathFinder(adjacentLoc))
                {
                    this.path.addFirst(currentLoc);
                    return true;
                }
            }
            // Search through left to right column
            if (currentLoc.getCol() < this.numCols() - 1)
            {
                MazeCoord adjacentLoc = new MazeCoord (currentLoc.getRow(), currentLoc.getCol() + 1); 
                if (pathFinder(adjacentLoc))
                {
                    this.path.addFirst(currentLoc);
                    return true;
                }
            }
        }
        return false;
    }
}

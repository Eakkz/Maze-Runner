//Name: Ekachai Suriyakriengkri
//USC NetID: suriyakr@usc.edu
//CS 455 PA3
//Spring 2018

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ListIterator;

import javax.swing.JComponent;

/**
   MazeComponent class

   A component that displays the maze and path through it if one has been found.
 */
public class MazeComponent extends JComponent {

    private static final int START_X = 10; // top left of corner of maze in frame
    private static final int START_Y = 10;
    private static final int BOX_WIDTH = 20;  // width and height of one maze "location"
    private static final int BOX_HEIGHT = 20;
    private static final int INSET = 2;  
    private static final int SHIFT_FOR_ENTRY_AND_EXIT = 2; // for shifting inset (for drawing entry and exit square)
    private static final Color COLOR_OF_WALL = Color.BLACK;
    private static final Color COLOR_OF_SPACE = Color.WHITE;
    private static final Color COLOR_OF_ENTRY = Color.YELLOW;
    private static final Color COLOR_OF_EXIT = Color.GREEN;
    private static final Color COLOR_OF_PATH = Color.BLUE;
    // how much smaller on each side to make entry/exit inner box

    private static Maze maze;

    /**
      Constructs the component.
      @param maze   the maze to display
     */
    public MazeComponent(Maze maze) 
    {   
        this.maze = maze;
    }


    /**
     Draws the current state of maze including the path through it if one has
     been found.
     - Draw the maze by drawing black square for wall and white for path
     - Draw entry and exit location by drawing a small square inside the white path (yellow for entry and green for exit)
     - Draw the path if linked list that contain path from entry to exit is not empty by using blue line
     @param g the graphics context
     */
    public void paintComponent(Graphics g)
    {
        // Recover Graphics2D
        Graphics2D g2 = (Graphics2D) g;

        // Loop through 2D Maze array to build the GUI maze
        // Start with row on the top to bottom
        // Each row loop through left to right column
        for (int i = 0; i< this.maze.numRows(); i++)
        {
            for (int j = 0; j < this.maze.numCols(); j++)
            {
                MazeCoord currentLoc = new MazeCoord(i,j);
                // Fill the black color space for a wall
                if(this.maze.hasWallAt(currentLoc))
                {
                    // Set wall to black color
                    g2.setColor(COLOR_OF_WALL);
                    Rectangle wall = new Rectangle(j * BOX_WIDTH + START_X, i * BOX_HEIGHT + START_Y, BOX_WIDTH, BOX_HEIGHT);
                    g2.fill(wall);

                }
                // Fill the white color space for a path
                else
                {
                    // Set path to white color
                    g2.setColor(COLOR_OF_SPACE);
                    Rectangle space = new Rectangle(j * BOX_WIDTH + START_X, i * BOX_HEIGHT + START_Y, BOX_WIDTH, BOX_HEIGHT);
                    g2.fill(space);
                }
                // Fill yellow color for entry location
                if (currentLoc.equals(this.maze.getEntryLoc()))
                {
                    g2.setColor(COLOR_OF_ENTRY);
                    Rectangle entry = new Rectangle(j * BOX_WIDTH + START_X + INSET, i * BOX_HEIGHT + START_Y + INSET, BOX_WIDTH - (SHIFT_FOR_ENTRY_AND_EXIT * INSET), BOX_HEIGHT - (SHIFT_FOR_ENTRY_AND_EXIT * INSET));
                    g2.fill(entry);
                }
                // Fill green color for entry location
                if (currentLoc.equals(this.maze.getExitLoc()))
                {
                    g2.setColor(COLOR_OF_EXIT);
                    Rectangle exit = new Rectangle((j * BOX_WIDTH) + START_X + INSET, (i * BOX_HEIGHT) + START_Y + INSET, BOX_WIDTH - (SHIFT_FOR_ENTRY_AND_EXIT  * INSET), BOX_HEIGHT - (SHIFT_FOR_ENTRY_AND_EXIT * INSET));
                    g2.fill(exit);
                }
            }
        }

        // Draw a black rectangle border
        g2.setColor(COLOR_OF_WALL);
        Rectangle border = new Rectangle(START_X, START_Y, BOX_WIDTH * this.maze.numCols(), BOX_HEIGHT * this.maze.numRows());
        g2.draw(border);

        // Draw a path from entry to exit
        if (!this.maze.getPath().isEmpty()) // If the maze have path from entry to exit
        {
            // Declare variables, two position for drawing lines.
            int x1 = 0;
            int y1 = 0;
            int x2 = 0;
            int y2 = 0;

            ListIterator<MazeCoord> it = this.maze.getPath().listIterator();
            MazeCoord currentLoc = it.next();
            MazeCoord adjacentLoc;

            while (it.hasNext())
            {   
                // Get coordinate for currentLoc
                x1 = currentLoc.getCol() * BOX_WIDTH + START_X + BOX_WIDTH/INSET;
                y1 = currentLoc.getRow() * BOX_HEIGHT + START_Y + BOX_HEIGHT/INSET;

                adjacentLoc = it.next();

                // Get coordinate for adjacentLoc
                x2 = adjacentLoc.getCol() * BOX_WIDTH + START_X + BOX_WIDTH/INSET;
                y2 = adjacentLoc.getRow() * BOX_HEIGHT + START_Y + BOX_HEIGHT/INSET;

                // Draw a blue line between currentLoc and adjacentLoc
                Line2D.Double path = new Line2D.Double(x1, y1, x2, y2);
                g2.setColor(COLOR_OF_PATH);
                g2.draw(path);

                currentLoc = adjacentLoc;
            }
        }
    }
}




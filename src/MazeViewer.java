//Name: Ekachai Suriyakriengkri
//USC NetID: suriyakr@usc.edu
//CS 455 PA3
//Spring 2018

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;


/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 *      java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start location, 
 * and ending with the exit location. Each maze location is
 * either a wall (1) or free (0). Here is an example of contents of a file for
 * a 3x4 maze, with start location as the top left, and exit location as the bottom right
 * (we count locations from 0, similar to Java arrays):
 * 
 * 3 4 
 * 0111
 * 0000
 * 1110
 * 0 0
 * 2 3
 * 
 */

public class MazeViewer {

    private static final char WALL_CHAR = '1';
    private static final char FREE_CHAR = '0';

    public static void main(String[] args)  {

        String fileName = "";

        try {

            if (args.length < 1)
            {   // args.length < 1 no file name from input
                System.out.println("ERROR: missing file name command line argument");
            }
            else
            {
                fileName = args[0];

                JFrame frame = readMazeFile(fileName);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setVisible(true);
            }

        }
        catch (FileNotFoundException exc) {
            System.out.println("ERROR: File not found: " + fileName);
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
    readMazeFile reads in maze from the file whose name is given and 
    returns a MazeFrame created from it.

   @param fileName
             the name of a file to read from (file format shown in class comments, above)
   @returns a MazeFrame containing the data from the file.

   @throws FileNotFoundException
              if there's no such file (subclass of IOException)
   @throws IOException
              (hook given in case you want to do more error-checking --
               that would also involve changing main to catch other exceptions)
     */
    private static MazeFrame readMazeFile(String fileName) throws IOException {
        
        // Variable declaration
        File inputFile = new File (fileName);
        Scanner in = new Scanner (inputFile);

        // Read number of rows and columns from the first line of file
        int numRows = in.nextInt();
        int numCols = in.nextInt();
        
        boolean[][] mazeData = new boolean[numRows][numCols];
        
        // Skip to start new line with maze data (0, 1)
        in.nextLine();
        
        // Read each maze data and store in 2D array
        
        // Read the first to down row
        for (int i = 0; i < numRows; i++)
        {
            String rowString = in.nextLine();
            // Read from left to right column
            for (int j = 0; j < numCols; j++)
            {
                if (rowString.charAt(j) == FREE_CHAR)
                    mazeData[i][j] = false;
                else
                    mazeData[i][j] = true;
            }
        }
        // Read last 2 line from file to get entry location and exit location
        int startX = in.nextInt();
        int startY = in.nextInt();
        int exitX = in.nextInt();
        int exitY = in.nextInt();
        
        return new MazeFrame(mazeData, new MazeCoord(startX, startY), new MazeCoord(exitX, exitY));

    }

}

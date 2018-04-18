
public class MazeTester {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //boolean[][] mazeData = new boolean [5][8];
        boolean[][] mazeData = {
                                {false, false, true, true, false, false, false, false},
                                {true, false, true, true, false, true, true, false},
                                {true, false, true, true, false, true, true, false},
                                {true, false, true, true, true, true, true, false},
                                {true, true, true, true, true, true, true, true},
                               };
        MazeCoord start = new MazeCoord (2, 4);
        MazeCoord end = new MazeCoord (0, 0);
        Maze maze = new Maze (mazeData, start, end);
        System.out.println("Rows: " + maze.numRows());
        System.out.println("Columns: " + maze.numCols());
        System.out.println("Start: " + maze.getEntryLoc().toString());
        System.out.println("Exit: " + maze.getExitLoc().toString());
        
    }

}

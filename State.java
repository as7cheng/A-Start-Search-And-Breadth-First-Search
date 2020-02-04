import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

  private Square square;
  private State parent;

  // Maintain the gValue (the distance from start)
  // You may not need it for the BFS but you will
  // definitely need it for AStar
  private int gValue;

  // States are nodes in the search tree, therefore each has a depth.
  private int depth;

  /**
   * @param square current square
   * @param parent parent state
   * @param gValue total distance from start
   */
  public State(Square square, State parent, int gValue, int depth) {
    this.square = square;
    this.parent = parent;
    this.gValue = gValue;
    this.depth = depth;
  }

  /**
   * @param visited explored[i][j] is true if (i,j) is already explored
   * @param maze    initial maze to get find the neighbors
   * @return all the successors of the current state
   */
  public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {
    // Create a new arraylist to store all valid successors
    ArrayList<State> successors = new ArrayList<State>();

    int x = this.getSquare().X;// x coordinate for current node
    int y = this.getSquare().Y; // y coordinate for current node

    // Check left first, if it is not wall and not been explored, add it in to
    // successors
    if (maze.getSquareValue(x, y - 1) != '%' && explored[x][y - 1] != true) {
      successors.add(new State(new Square(x, y - 1), this, this.getGValue() + 1,
          this.getDepth() + 1));
    }

    // Check down side, if it is not wall and not been explored, add it in to
    // successors
    if (maze.getSquareValue(x + 1, y) != '%' && explored[x + 1][y] != true) {
      successors.add(new State(new Square(x + 1, y), this, this.getGValue() + 1,
          this.getDepth() + 1));
    }

    // Check right side, if it is not wall and not been explored, add it in to
    // successors
    if (maze.getSquareValue(x, y + 1) != '%' && explored[x][y + 1] != true) {
      successors.add(new State(new Square(x, y + 1), this, this.getGValue() + 1,
          this.getDepth() + 1));
    }

    // Check up side, if it is not wall and not been explored, add it in to
    // successors
    if (maze.getSquareValue(x - 1, y) != '%' && explored[x - 1][y] != true) {
      successors.add(new State(new Square(x - 1, y), this, this.getGValue() + 1,
          this.getDepth() + 1));
    }
    // TODO check all four neighbors in left, down, right, up order
    // TODO remember that each successor's depth and gValue are
    // +1 of this object.
    return successors;
  }

  /**
   * @return x coordinate of the current state
   */
  public int getX() {
    return square.X;
  }

  /**
   * @return y coordinate of the current state
   */
  public int getY() {
    return square.Y;
  }

  /**
   * @param maze initial maze
   * @return true is the current state is a goal state
   */
  public boolean isGoal(Maze maze) {
    if (square.X == maze.getGoalSquare().X
        && square.Y == maze.getGoalSquare().Y)
      return true;

    return false;
  }

  /**
   * @return the current state's square representation
   */
  public Square getSquare() {
    return square;
  }

  /**
   * @return parent of the current state
   */
  public State getParent() {
    return parent;
  }

  /**
   * You may not need g() value in the BFS but you will need it in A-star
   * search.
   * 
   * @return g() value of the current state
   */
  public int getGValue() {
    return gValue;
  }


  /**
   * @return depth of the state (node)
   */
  public int getDepth() {
    return depth;
  }
  
  public boolean matchState(State m) {
    if (this.getX() == m.getX() && this.getY() == m.getY()) {
      return true;
    }
    return false;
  }
}

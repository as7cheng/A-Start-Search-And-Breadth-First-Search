import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

  /**
   * Calls the parent class constructor.
   * 
   * @see Searcher
   * @param maze initial maze.
   */
  public BreadthFirstSearcher(Maze maze) {
    super(maze);
  }


  /**
   * Main breadth first search algorithm.
   * 
   * @return true if the search finds a solution, false otherwise.
   */
  public boolean search() {
    // Explored list is a 2D Boolean array that indicates if a state associated
    // with a given position in the maze has already been explored.
    boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
    LinkedList<State> queue = new LinkedList<State>(); // The frontier
    ArrayList<State> successors = new ArrayList<State>();

    // Create the start node
    State first = new State(this.maze.getPlayerSquare(), null, 0, 0);
    // Add them in the frontier
    queue.add(first);
    this.maxDepthSearched = 0;
    this.maxSizeOfFrontier = 1;
    this.noOfNodesExpanded = 0;

    while (!queue.isEmpty()) {

      // Maintain maxSizeOfFrontier
      if (this.getMaxSizeOfFrontier() < queue.size()) {
        this.maxSizeOfFrontier = queue.size();
      }

      // If current expanded node is goal, return true
      if (queue.peek().isGoal(this.maze)) {
        State temp = queue.pop(); // Get the goal
        this.cost = temp.getDepth(); // get cost
        this.noOfNodesExpanded += 1; // Maintain noOfNodesExpanded
        // Draw the maze with solution path
        for (int i = 0; i < cost - 1; i++) {
          temp = temp.getParent();
          this.maze.setOneSquare(temp.getSquare(), '.');
        }
        return true;
      }

      // Maintain maxDepthSearched
      this.maxDepthSearched = queue.getLast().getDepth();

      // Get current node to expand
      State goal = queue.pop();

      // Otherwise expand current node
      if (explored[goal.getX()][goal.getY()] != true) {
        // Mark current square is explored
        explored[goal.getX()][goal.getY()] = true;
        // Maintain noOfNodesExpanded
        this.noOfNodesExpanded += 1;

        successors = goal.getSuccessors(explored, this.maze);

        // If current node has successors
        if (!successors.isEmpty()) {
          // Loop to check all successors
          for (State s : successors) {
            // If frontier is empty, add the successor in
            if (queue.isEmpty()) {
              queue.add(s);
            } else { // Else, check if the successor is duplicated
              // Counter for counting how many nodes in frontier differs current
              // node
              int counter = 0;
              for (State r : queue) {
                if (r.matchState(s) == true) {
                  counter += 1;
                }
              }
              // Once it differs all nodes in queue, insert it in
              if (counter == 0) {
                queue.add(s);
              }
            }
          }
        }
      }

    }
    return false;
  }

}

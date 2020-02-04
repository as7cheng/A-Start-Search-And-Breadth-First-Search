import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

  /**
   * Calls the parent class constructor.
   * 
   * @see Searcher
   * @param maze initial maze.
   */
  public AStarSearcher(Maze maze) {
    super(maze);
  }

  /**
   * Main a-star search algorithm.
   * 
   * @return true if the search finds a solution, false otherwise.
   */
  public boolean search() {

    // Explored list is a Boolean array that indicates if a state associated
    // with a given position in the maze has already been explored.
    boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];


    PriorityQueue<StateFValuePair> frontier =
        new PriorityQueue<StateFValuePair>();


    // Initialize the root state and add to frontier list
    double x = this.maze.getGoalSquare().X;
    double y = this.maze.getGoalSquare().Y;

    State first = new State(this.maze.getPlayerSquare(), null, 0, 0);
    StateFValuePair start = new StateFValuePair(first,
        Math.sqrt(Math.pow(maze.getPlayerSquare().X - x, 2)
            + Math.pow(maze.getPlayerSquare().Y - y, 2)));
    frontier.add(start);


    while (!frontier.isEmpty()) {

      // First check if current node is the goal
      if (frontier.peek().getState().isGoal(maze)) {
        // If so, get the goal, and update all information
        StateFValuePair temp = frontier.poll();
        State curr = temp.getState();
        this.cost = temp.getState().getDepth();
        this.noOfNodesExpanded += 1;
        // Update the maze
        for (int i = 0; i < cost - 1; i++) {
          curr = curr.getParent();
          this.maze.setOneSquare(curr.getSquare(), '.');
        }
        return true;
      }

      // If not, maintain maxSizeOfFrontier
      if (this.maxSizeOfFrontier < frontier.size()) {
        this.maxSizeOfFrontier = frontier.size();
      }

      // Get current node
      StateFValuePair goal = frontier.poll();

      // First check if current node has already been explored
      if (explored[goal.getState().getX()][goal.getState().getY()] != true) {
        // If not, mark current node is explored and maintain other information
        explored[goal.getState().getX()][goal.getState().getY()] = true;
        this.noOfNodesExpanded += 1;

        // Get the list of all successors of current node
        ArrayList<State> list = goal.getState().getSuccessors(explored, maze);

        // Maintain maxDepthSearched
        for (State s : list) {
          if (this.maxDepthSearched < s.getDepth()) {
            this.maxDepthSearched = s.getDepth();
          }
        }
        // If current node has successors
        if (!list.isEmpty()) {
          // Loop to check all of them
          for (int i = 0; i < list.size(); i++) {

            // Calculate the h(n) for current state
            Double hValue = Math.sqrt(Math.pow(list.get(i).getX() - x, 2)
                + Math.pow(list.get(i).getY() - y, 2));
            // Create a new StateFValuePair for current node
            StateFValuePair node = new StateFValuePair(list.get(i),
                hValue + list.get(i).getGValue());

            // Now, start to maintain the frontier
            // If frontier is empty, add the new StateFValuePair node in
            if (frontier.isEmpty()) {
              frontier.add(node);
            } else { // If not, check if the node is valid
              // Counter for counting how many nodes in frontier differs current
              // node
              int counter = 0;
              // A temporary list to store frontier avoiding
              // ConcurrentModificationException()
              PriorityQueue<StateFValuePair> temp =
                  new PriorityQueue<StateFValuePair>();

              // Copy all from frontier
              for (StateFValuePair copy : frontier) {
                temp.add(copy);
              }

              // Start the loop to compare with current node
              for (StateFValuePair s : frontier) {
                // Once there is one nodes in frontier differs current node,
                // counter increase 1
                if (!s.getState().matchState(node.getState())) {
                  counter += 1;
                } else { // Else current node has same state with node s
                  // If g(s) >= g(current node), replace it
                  if (s.getState().getGValue() >= node.getState().getGValue()) {
                    temp.remove(s); // Remove s
                    temp.add(node); // Insert current node in temporary list
                  }
                }
              }
              // If all nodes in frontier differs current node, insert it in
              if (counter == temp.size()) {
                temp.add(node);
              }
              frontier.clear(); // Clear frontier and copy all nodes from the
                                // temporary list
              for (StateFValuePair paste : temp) {
                frontier.add(paste);
              }
            }
          }
        }
      }
    }
    return false;
  }

}


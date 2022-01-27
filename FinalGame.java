import java.util.*;

class FinalGame {

    public static int[] startLocation(String s) {
        int[] result = new int[2];
        String[] splitedString = s.split(";");
        result[0] = Integer.parseInt(splitedString[1].split(",")[0]);
        result[1] = Integer.parseInt(splitedString[1].split(",")[1]);
        return result;
    }

    public static int[] endLocation(String s) {
        int[] result = new int[2];
        String[] splitedString = s.split(";");
        result[0] = Integer.parseInt(splitedString[3].split(",")[0]);
        result[1] = Integer.parseInt(splitedString[3].split(",")[1]);
        return result;
    }

    public static int[] getGridSize(String s) {
        int[] result = new int[2];
        String[] splitedString = s.split(";");
        result[0] = Integer.parseInt(splitedString[0].split(",")[0]);
        result[1] = Integer.parseInt(splitedString[0].split(",")[1]);
        return result;
    }

    public static int[] getObstacles(String s) {
        String[] splitedString = s.split(";");
        String[] splitedString2 = splitedString[2].split(",");
        int[] result = new int[splitedString2.length];
        for (int i = 0; i < splitedString2.length; i++)
            result[i] = Integer.parseInt(splitedString2[i]);
        return result;
    }

    public static int[][] getGrid(int[] obstacles, int rows, int cols) {
        int[][] result = new int[rows][cols];
        for (int i = 0; i < obstacles.length; i++) {
            result[obstacles[i]][obstacles[i + 1]] = 1;
            i++;
        }
        return result;
    }

    public static boolean[][] getblocked(int[][] grid) {
        boolean[][] result = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1)
                    result[i][j] = true;
            }
        }
        return result;
    }

    public static Stack<String> dfsHelper(int[][] grid, int[] startLocation, int[] endLocation) {
        boolean runLoop = true;
        Stack<String> resultStack = new Stack<>();
        Stack<String> stack = new Stack<>();
        boolean[][] blocked = getblocked(grid);
        int x = startLocation[0];
        int y = startLocation[1];
        stack.push(x + "," + y);
        while (runLoop) {
            String currentCell = stack.peek();
            x = Integer.parseInt(currentCell.split(",")[0]);
            y = Integer.parseInt(currentCell.split(",")[1]);
            blocked[x][y] = true;
            if (x == endLocation[0] && y == endLocation[1])
                return resultStack;
            else {
                if (x + 1 < grid.length && !blocked[x + 1][y]) {// down
                    stack.push((x + 1) + "," + y);
                    resultStack.push("down");
                } else {
                    if (x - 1 >= 0 && !blocked[x - 1][y]) {// up
                        stack.push((x - 1) + "," + y);
                        resultStack.push("up");
                    } else {
                        if (y + 1 < grid[0].length && !blocked[x][y + 1]) { // right
                            stack.push(x + "," + (y + 1));
                            resultStack.push("right");
                        } else {
                            if (y - 1 >= 0 && !blocked[x][y - 1]) {// left
                                stack.push(x + "," + (y - 1));
                                resultStack.push("left");
                            } else {
                                if (resultStack.isEmpty())
                                    return resultStack;
                                else {
                                    resultStack.pop();
                                    stack.pop();
                                }
                            }
                        }
                    }
                }
            }
        }
        return resultStack;

    }

    public static String bfs(String grid) {
        String result = "";
        int[] startLocation = startLocation(grid);
        int[] endLocation = endLocation(grid);
        int[] gridSize = getGridSize(grid);
        int[][] matrix = getGrid(getObstacles(grid), gridSize[0], gridSize[1]);
        if (startLocation[0] == endLocation[0] && startLocation[1] == endLocation[1])
            return ";0";

        Stack<String> afterDFS = dfsHelper(matrix, startLocation, endLocation);
        if (afterDFS.isEmpty())
            return "No Solution";
        else {
            int size = 0;
            while (!afterDFS.isEmpty()) {
                String temp = afterDFS.pop();
                if (size == 0)
                    result = temp;
                else
                    result = temp + "," + result;
                size++;
            }
            result += ";" + size;
        }
        return result;
    }

    public static String dfs(String grid) {
        String result = "";
        int[] startLocation = startLocation(grid);
        int[] endLocation = endLocation(grid);
        int[] gridSize = getGridSize(grid);
        int[][] matrix = getGrid(getObstacles(grid), gridSize[0], gridSize[1]);
        Stack<String> afterDFS = dfsHelper(matrix, startLocation, endLocation);
        if (startLocation[0] == endLocation[0] && startLocation[1] == endLocation[1])
            return ";0";
        if (afterDFS.isEmpty())
            return "No Solution";
        else {
            int size = 0;
            while (!afterDFS.isEmpty()) {
                String temp = afterDFS.pop();
                if (size == 0)
                    result = temp;
                else
                    result = temp + "," + result;
                size++;
            }
            result += ";" + size;
        }
        return result;
    }

    public static void main(String[] args) {
        
    }
}
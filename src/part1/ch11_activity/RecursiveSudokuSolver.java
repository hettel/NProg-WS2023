package part1.ch11_activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import part1.ch11_activity.SudokuBoard.FieldPosition;
import part1.ch11_activity.SudokuBoard.FieldValue;


public class RecursiveSudokuSolver
{

  public static void main(String[] args) throws Exception
  {
    File file = new File("board01.txt");
    SudokuBoard board = SudokuBoard.load(file);
    board.print();

    System.out.println("Solve game: ");

    long startTime = System.currentTimeMillis();
    List<SudokuBoard> boards = solve(board);
    long endTime = System.currentTimeMillis();
    System.out.println("Elapsed time " + (endTime - startTime) + " [ms]");
    System.out.println("Found " + boards.size() + " solutions");
    boards.get(0).print();

    System.out.println("done sequential");
  }

  public static List<SudokuBoard> solve(SudokuBoard board)
  {
    if (board.isComplete())
    {
      return List.of(board);
    }

    FieldPosition nextFreeField = board.getFreePosition();
    //FieldPosition nextFreeField = board.getFreePositionWithLeastCandidates();
    Set<FieldValue> candidates = board.getValueCandidates(nextFreeField.row, nextFreeField.col);

    if (candidates.isEmpty())
    {
      return Collections.emptyList();
    }

    List<SudokuBoard> resultList = new ArrayList<>();
    for (FieldValue field : candidates)
    {
      SudokuBoard newBoard = SudokuBoard.copy(board);
      newBoard.setValue(nextFreeField.row, nextFreeField.col, field);
      newBoard.pack();
      
      List<SudokuBoard> result = solve(newBoard);
      resultList.addAll(result);
    }

    return resultList;
  }
}

package part1.ch11_activity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Soduko board
 * 
 * A 9 x 9 soduko board. Rows are numbered from top to bottom 
 * Cols are numbered from right to left. Positions are between
 * 0 and 8 (inclusive).
 * 
 * Example:
 * <pre>
 *{@code
 * - - - 5 4 6 - - 9
 * - 2 - - - - - - 7
 * - - 3 9 - - - - 4
 * 9 - 5 - - - - 7 -
 * 7 - - - - - - 2 -
 * - - - - 9 3 - - -
 * - 5 6 - - 8 - - -
 * - 1 - - 3 9 - - -
 * - - - - - - 8 - 6
 * }
 * </pre>
 * 
 * The method <code>getFieldValue(0, 3)</code> gets the enum value 5.
 */
public class SudokuBoard
{
  private static final int SIZE = 9;
  private static final int SUB_SIZE = 3;
  
  /**
   * Represents the possible values of the Soduko board
   */
  public static enum FieldValue
  {
    NONE("_"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9");

    private String value;

    FieldValue(String value)
    {
      this.value = value;
    }
    
    public static FieldValue get(String value)
    {
      switch (value)
      {
      case "1":
        return ONE;
      case "2":
        return TWO;
      case "3":
        return THREE;
      case "4":
        return FOUR;
      case "5":
        return FIVE;
      case "6":
        return SIX;
      case "7":
        return SEVEN;
      case "8":
        return EIGHT;
      case "9":
        return NINE;
      default:
        return NONE;
      }
    }

    public String toString()
    {
      return this.value;
    }
  }
  
  /**
   * Represents a position on the Sudoku board
   * Rows are numbered from top to bottom 
   * Cols are numbered from right to left
   */
  public static class FieldPosition
  {
    public final int row;
    public final int col;
    
    private FieldPosition(int row, int col)
    {
      this.row = row;
      this.col = col;
    }
  }

  private final Set<FieldValue> allValues = Set.of(FieldValue.ONE, FieldValue.TWO, FieldValue.THREE, 
                                                   FieldValue.FOUR, FieldValue.FIVE, FieldValue.SIX, 
                                                   FieldValue.SEVEN, FieldValue.EIGHT, FieldValue.NINE);
  private FieldValue[][] board;

  
  /**
   * Factory method that creates a new board. All fields are set to NONE
   * @return
   */
  public static SudokuBoard empty()
  {
    FieldValue[][] board = new FieldValue[SIZE][SIZE];
    for (int row = 0; row < SIZE; row++)
      for (int col = 0; col < SIZE; col++)
        board[row][col] = FieldValue.NONE;

    return new SudokuBoard(board);
  }

  /**
   * Factory method that makes a copy the board
   * 
   * @param board
   * @return
   */
  public static SudokuBoard copy(SudokuBoard board)
  {
    return new SudokuBoard(board.board);
  }

  
  /**
   * Created a board from a text file. The text file has to have the format:
   * <pre>
   *{@code
   * - - - 5 4 6 - - 9
   * - 2 - - - - - - 7
   * - - 3 9 - - - - 4
   * 9 - 5 - - - - 7 -
   * 7 - - - - - - 2 -
   * - - - - 9 3 - - -
   * - 5 6 - - 8 - - -
   * - 1 - - 3 9 - - -
   * - - - - - - 8 - 6
   * }
   * </pre>
   * numbers [1-9], an arbitrary letter for none separated by spaces.
   * 
   * @param file
   * @return
   * @throws IOException
   */
  public static SudokuBoard load(File file) throws IOException
  {
    SudokuBoard board = empty();
    
    List<String> lines = Files.lines(Paths.get(file.getName())).collect( Collectors.toList() );
    int row = 0;
    for(String line : lines )
    {
      String[] values = line.split("\\s+");
      for(int col=0; col < values.length; col++)
      {
        board.setValue(row, col, FieldValue.get( values[col].trim() ) );
      }
      row++;
    }
    
    return board;
  }

  // copy constructor
  private SudokuBoard(FieldValue[][] board)
  {
    this.board = new FieldValue[SIZE][SIZE];
    for (int row = 0; row < SIZE; row++)
    {
      for (int col = 0; col < SIZE; col++)
      {
        this.board[row][col] = board[row][col];
      }
    }
  }

  
  /**
   * Sets the field value at the specified position
   * 
   * @param row
   * @param col
   * @param value
   * @return
   */
  public FieldValue setValue(int row, int col, FieldValue value)
  {
    assert value != null;
    assert row >= 0 & row < SIZE;
    assert col >= 0 & col < SIZE;

    return this.board[row][col] = value;
  }
  
  

  /**
   * Gets the field value at the specified position
   * 
   * @param row
   * @param col
   * @return
   */
  public FieldValue getValue(int row, int col)
  {
    assert row >= 0 & row < SIZE;
    assert col >= 0 & col < SIZE;

    return this.board[row][col];
  }
  
  
  
  public FieldPosition getFreePositionWithLeastCandidates()
  {
    FieldPosition position = null;
    int minCandidates = SIZE;
    for(int row = 0; row < SIZE; row ++)
    {
      for( int col = 0; col < SIZE; col++)
      {
        if( this.getValue(row, col) == FieldValue.NONE )
        {
          int candidates = getValueCandidates(row, col).size();
          if( candidates < minCandidates )
          {
            minCandidates = candidates;
            position = new FieldPosition(row, col);
          }
        }  
      }
    }
    
    return position;
  }
  
  
  
  /**
   * Gets a free field. The first field found searching from top to bottom
   * and left to right.
   * 
   * @return
   */
  public FieldPosition getFreePosition()
  {
    for(int row = 0; row < SIZE; row ++)
    {
      for( int col = 0; col < SIZE; col++)
      {
        if( this.getValue(row, col) == FieldValue.NONE )
        {
          return new FieldPosition(row, col);
        }  
      }
    }
    
    return null;
  }
  

  /**
   * Gets all possible number values for the speficied field
   * 
   * @param row
   * @param col
   * @return
   */
  public Set<FieldValue> getValueCandidates(int row, int col)
  {
    assert row >= 0 & row < SIZE;
    assert col >= 0 & col < SIZE;
    assert this.board[row][col] == FieldValue.NONE;

    Set<FieldValue> valueCandiadates = new HashSet<>(this.allValues);
    for (int k = 0; k < SIZE; k++)
    {
      valueCandiadates.remove(this.board[row][k]);
      valueCandiadates.remove(this.board[k][col]);
    }

    int rowStart = SUB_SIZE * (row / SUB_SIZE);
    int colStart = SUB_SIZE * (col / SUB_SIZE);
    for (int k = 0; k < 3; k++)
    {
      for (int l = 0; l < SUB_SIZE; l++)
      {
        valueCandiadates.remove(this.board[rowStart + k][colStart + l]);
      }
    }

    return valueCandiadates;
  }

  /**
   * Checks if the board is completely filled with number values
   * 
   * @return
   */
  public boolean isComplete()
  {
    for (int row = 0; row < SIZE; row++)
    {
      for (int col = 0; col < SIZE; col++)
      {
        if (board[row][col] == FieldValue.NONE)
          return false;
      }
    }

    return true;
  }
  
  
  /**
   * Fills all fields if only one possibility exists
   */
  public void pack()
  {
    restart:
    for(int row = 0; row < SIZE; row ++)
    {
      for( int col = 0; col < SIZE; col++)
      {
        if( this.getValue(row,col) == FieldValue.NONE )
        {
          Set<FieldValue> valueCandiadates = this.getValueCandidates(row, col);
          if( valueCandiadates.size() ==  1)
          {
            for( FieldValue field : valueCandiadates )
            {
              this.setValue(row, col, field );
            }
            
            // restart pack
            continue restart;
          }
        }
      }
    }
  }

  
  /**
   * Prints the board on the console
   */
  public void print()
  {
    for (int row = 0; row < SIZE; row++)
    {
      if (row % SUB_SIZE == 0)
        System.out.println("------------------------");
      System.out.format("| %s %s %s |", this.board[row][0], this.board[row][1], this.board[row][2]);
      System.out.format(" %s %s %s |", this.board[row][3], this.board[row][4], this.board[row][5]);
      System.out.format(" %s %s %s |", this.board[row][6], this.board[row][7], this.board[row][8]);
      System.out.println();
    }
    System.out.println("------------------------");
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((allValues == null) ? 0 : allValues.hashCode());
    result = prime * result + Arrays.deepHashCode(board);
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SudokuBoard other = (SudokuBoard) obj;
    if (allValues == null)
    {
      if (other.allValues != null)
        return false;
    } else if (!allValues.equals(other.allValues))
      return false;
    if (!Arrays.deepEquals(board, other.board))
      return false;
    return true;
  }
}

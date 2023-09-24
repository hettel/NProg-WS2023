package part1.ch04_activity;


public class MatrixMultiplication
{

   public static void main(String[] args)
   {
      final int SIZE = 700;

      double[][] A = Util.getRandomMatrix(SIZE, SIZE);
      double[][] B = Util.getRandomMatrix(SIZE, SIZE);


      long startTime = System.currentTimeMillis();
      double[][] C = mult(A, B, SIZE);
      long endTime = System.currentTimeMillis();

      System.out.println("Elapsed time : " + (endTime - startTime) + "[ms]");
      System.out.println("The first element C[0][0] = " + C[0][0]);
      System.out.println("done");
   }

   // Multiplication of two square matrices
   // The arguments are not checked for validity
   public static double[][] mult(double[][] A, double[][] B, int size)
   {
      double[][] C = new double[size][size];

      for (int i = 0; i < size; i++)
      {
         for (int j = 0; j < size; j++)
         {
            for (int k = 0; k < size; k++)
            {
               C[i][j] += A[i][k] * B[k][j];
            }
         }
      }

      return C;
   }

}

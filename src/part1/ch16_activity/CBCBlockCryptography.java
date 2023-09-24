package part1.ch16_activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CBCBlockCryptography
{

   public static void main(String[] args) throws Exception
   {
      String inputFileName = "Homer-Odyssey-UTF8-Coding.txt";
      String encryptedFileName = "Homer-Odyssey-UTF8-Coding_encrypted.txt";
      String decryptedFileName = "Homer-Odyssey-UTF8-Coding_decrypted.txt";

      System.out.println("Encrypt " + inputFileName);
      System.out.println("to file " + encryptedFileName );

      long startTime = System.currentTimeMillis();
      encrypt(inputFileName, encryptedFileName);
      long endTime = System.currentTimeMillis();

      System.out.println("Elapsed time for encryption " + (endTime - startTime) + "[ms]");
      
      
      System.out.println("Decrypt " + encryptedFileName);
      System.out.println("to file " + decryptedFileName );
      
      startTime = System.currentTimeMillis();
      decrypt(encryptedFileName, decryptedFileName);
      endTime = System.currentTimeMillis();
      
      System.out.println("Elapsed time for decryption " + (endTime - startTime) + "[ms]");
      
      System.out.println("done");
   }

   // **********************************************************************
   //
   // ==== Encrypt and decrypt methods  ===
   //
   // **********************************************************************
   
   private static final int CaesarShift = 42;
   private static final int InitVector = 47011;
   
   public static void encrypt(String inputFile, String outputFile ) throws IOException
   {
      List<Integer> inputData = Util.readFromFileAsByte4(inputFile);

      List<Integer> encryptedInputData = new ArrayList<>();
      final int initVector = InitVector;
      int salt = initVector;
      for (Integer item : inputData)
      {
         // XOR operation
         int value = (salt ^ item); //xor operation

         // encrypt value using a simple Caesar cipher (shift 42)
         value = (value + CaesarShift);
         salt = value;
         
         encryptedInputData.add(value);
      }

      Util.writeToFile(outputFile, encryptedInputData);
   }
   
   public static void decrypt(String inputFile, String outputFile ) throws IOException
   {
      List<Integer> encryptedInputData = Util.readFromFileAsByte4(inputFile);

      List<Integer> decryptedOutputData = new ArrayList<>();
      final int initVector = InitVector;
      int salt = initVector;
      for (Integer item : encryptedInputData)
      {
         // decrypt using a simple Caesar cipher (shift 42)
         int value = (item - CaesarShift);
 
         // XOR operation
         value = (salt ^ value);
         salt = item;
         
         decryptedOutputData.add(value);
      }

      Util.writeToFile(outputFile, decryptedOutputData);
   }
}


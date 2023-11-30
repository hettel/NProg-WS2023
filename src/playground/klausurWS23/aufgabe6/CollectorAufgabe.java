package playground.klausurWS23.aufgabe6;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class CollectorAufgabe
{

	public static void main(String[] args)
	{
		String[]  randomStrings = getRandomStringArray(200);
		
		Predicate<String> strContains3a = (str) -> str.chars().filter( c -> c == 'a').count() == 3L;
		List<String> list1 = Arrays.stream(randomStrings)
		                          .filter( strContains3a )
		                          .toList();
		
		list1.forEach( System.out::println );
		System.out.println("----------------------------------");
		
		// List<String> list2 = Arrays.stream(randomStrings).collect(  new MyCollector() );
		// list2.forEach( System.out::println );
	}
	
	private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	private static String getRandomString(int len)
	{
		StringBuilder strBuilder = new StringBuilder();
		
		for(int i=0; i<len; i++)
		{
			int idx = ThreadLocalRandom.current().nextInt( alphabet.length );
			strBuilder.append( alphabet[idx] );
		}
		
		return strBuilder.toString();
	}
	
	private static String[] getRandomStringArray(int size)
	{
		String[] array = new String[size];
		
		IntStream.range(0, size)
		         .forEach( i -> array[i] = getRandomString(20) );
		
		return array;
	}

}

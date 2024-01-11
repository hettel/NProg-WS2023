package klausur2022.aufgabe6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class CollectorAufgabe_Solution
{
	static class MyCollector implements Collector<String, List<String>, List<String> >
	{
		@Override
		public Supplier<List<String>> supplier()
		{
			return () -> new ArrayList<>();
		}

		@Override
		public BiConsumer<List<String>, String> accumulator()
		{
			return (liste, element) -> { if( element.chars().filter( c -> c == 'a').count() == 3 ) {liste.add( element );} };
		}

		@Override
		public BinaryOperator<List<String>> combiner()
		{
			return (list1, list2) -> { list1.addAll( list2 ); return list1; };
		}

		@Override
		public Function<List<String>, List<String>> finisher()
		{
			return Function.identity();
		}

		@Override
		public Set<Characteristics> characteristics()
		{
			return Set.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED );
		}
	}

	public static void main(String[] args)
	{
		String[]  randomStrings = getRandomStringArray(200);
		
		Predicate<String> strContains3a = (str) -> str.chars().filter( c -> c == 'a').count() == 3L;
		
		List<String> list1 = Arrays.stream(randomStrings)
		                          .filter( strContains3a )
		                          .toList();
		
		list1.forEach( System.out::println );
		System.out.println("----------------------------------");
		
		List<String> list2 = Arrays.stream(randomStrings).parallel().collect(  new MyCollector() );
		list2.forEach( System.out::println );
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

package playground;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class WordCollection
{
    private static class LinkedListCollector<E> implements Collector<E, List<E>, List<E> >
    {

        @Override
        public Supplier<List<E>> supplier()
        {
            return () -> {
                System.out.println("List created");
                return new LinkedList<>(); };
        }

        @Override
        public BiConsumer<List<E>, E> accumulator()
        {
            return (list,element) -> list.add(element);
        }

        @Override
        public BinaryOperator<List<E>> combiner()
        {
            return (list1, list2) -> {
                System.out.println("Combiner called"); list1.addAll(list2); return list1; };
        }

        @Override
        public Function<List<E>, List<E>> finisher()
        {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics()
        {
            return Set.of( Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT );
        }
    }


    public static void main(String[] args) throws Exception
    {
        Path path = Paths.get("Homer-Odyssey-UTF8-Coding.txt");

        String content = Files.readString(path, UTF_8);
        content = content.replaceAll("[\",;!?\\.]"," ");
        String[] words = content.split("\\s+");
        System.out.println("Number of words " + words.length);

        List<String> words17 = Arrays.stream( words )
                .parallel()
                .filter( s -> s.length() == 17  )
                .collect( new LinkedListCollector<String>() );

        System.out.println( words17.getClass().getCanonicalName() );

    }
}

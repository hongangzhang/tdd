package geektime.tdd.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {


    // TODO:multi options: -l -p 8080 -d /usr/logs
    @Test
    public void should_parse_multi_option() {
        MultiOptions multiOptions = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(multiOptions.logging());
        assertEquals(8080, multiOptions.port());
        assertEquals("/usr/logs", multiOptions.directory());
    }

    static record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }



    // -g this is a list -d 1 2 -3 5

    @Test
    @Disabled
    public void should_example_2() {
        ListOptions listOptions = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, listOptions.group());
        assertArrayEquals(new int[]{1, 2, -3, 5}, listOptions.decimals());
    }


    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }

}

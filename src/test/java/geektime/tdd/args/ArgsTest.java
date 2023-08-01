package geektime.tdd.args;

import geektime.tdd.args.exceptions.IllegalOptonException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {

    @Test
    public void should_parse_multi_option() {

        // SUT Args.parse

        // exercise
        MultiOptions multiOptions = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");

        // verify
        assertTrue(multiOptions.logging());
        assertEquals(8080, multiOptions.port());
        assertEquals("/usr/logs", multiOptions.directory());

        // teardown
    }

    // setup
    static record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }


    @Test
    public void should_raise_exception_if_annotation_not_present()   {
        IllegalOptonException e = /*verify*/ assertThrows(IllegalOptonException.class,
                                               () -> /*exercise*/ Args.parse(OptionsWithoutAnnotation.class, "-l", "-p", "8080",
                                                                "-d", "/usr/logs"));
        // verify
        assertEquals("port", e.getParameter());

        // teardown
    }

    // setup
    static record OptionsWithoutAnnotation(@Option("l") boolean logging, int port, @Option("d") String directory) {
    }

    @Test
    public void should_example_2() {
        ListOptions listOptions = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, listOptions.group());
        assertArrayEquals(new Integer[]{1, 2, -3, 5}, listOptions.decimals());
    }


    static record ListOptions(@Option("g") String[] group, @Option("d") Integer[] decimals) {
    }

}

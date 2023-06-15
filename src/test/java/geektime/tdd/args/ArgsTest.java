package geektime.tdd.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {

    // -l -p 8080 -d /usr/logs
    // [-l],[-p,8080],[-d,/usr/logs]
    // {-l:{},-p:[8080],-d:[/usr/logs]}
    // Single Option:

    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");

        assertTrue(option.logging());
    }

    @Test
    public void should_set_boolean_option_to_false_if_flag_not_present() {
        BooleanOption option = Args.parse(BooleanOption.class);

        assertFalse(option.logging());
    }

    static record BooleanOption(@Option("l") boolean logging) {
    }

    @Test
    public void should_parse_int_as_option_value() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");

        assertEquals(option.port(), 8080);
    }

    static record IntOption(@Option("p") int port) {}


    @Test
    public void should_parse_string_as_option_value() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");

        assertEquals("/usr/logs", option.directory());
    }

    static record StringOption(@Option("d")String directory) {}

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

    // sad path:
    // TODO: - bool -l t \ -l t f
    // TODO: - int -p \ -p 8080 8081
    // TODO: - string -d \ -d /usr/logs /usr/vars
    // default value:
    // TODO: - bool : false
    // TODO: - int : 0
    // TODO: - string : ""



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

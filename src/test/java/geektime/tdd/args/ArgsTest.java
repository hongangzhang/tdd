package geektime.tdd.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {

    // -l -p 8080 -d /usr/logs
    // [-l],[-p,8080],[-d,/usr/logs]
    // {-l:{},-p:[8080],-d:[/usr/logs]}
    // Single Option:
    // TODO:     - Bool -l
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

    // TODO:     - Integer -p 8080
    // TODO:     - String -d /usr/logs
    // TODO:multi options: -l -p 8080 -d /usr/logs
    // sad path:
    // TODO: - bool -l t \ -l t f
    // TODO: - int -p \ -p 8080 8081
    // TODO: - string -d \ -d /usr/logs /usr/vars
    // default value:
    // TODO: - bool : false
    // TODO: - int : 0
    // TODO: - string : ""

    @Test
    @Disabled
    public void should_example_1() {
//        Arguments args = Args.parse("l:b,p:d,d:s", "-l", "-p", "8080", "-d", "/usr/logs");
//        args.getBool("l");
//        arg.getInt("d");

        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    // -g this is a list -d 1 2 -3 5
    @Test
    @Disabled
    public void should_example_2() {
        ListOptions listOptions = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, listOptions.group());
        assertArrayEquals(new int[]{1, 2, -3, 5}, listOptions.decimals());
    }


    static record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }

}

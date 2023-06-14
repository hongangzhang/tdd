package geektime.tdd.args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {

    // -l -p 8080 -d /usr/logs
    // [-l],[-p,8080],[-d,/usr/logs]
    // {-l:{},-p:[8080],-d:[/usr/logs]}
    // Single Option:
    //      - Bool -l
    //      - Integer -p 8080
    //      - String -d /usr/logs
    // multi options: -l -p 8080 -d /usr/logs
    // sad path:
    //  - bool -l t \ -l t f
    //  - int -p \ -p 8080 8081
    //  - string -d \ -d /usr/logs /usr/vars
    // default value:
    //  - bool : false
    //  - int : 0
    //  - string : ""

    @Test
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

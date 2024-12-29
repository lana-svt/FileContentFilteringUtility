package filefilter;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FilterUtilityTest {
    @BeforeEach
    public void setup() throws IOException {
        Files.writeString(Paths.get("test1.txt"), "123\n45.67\nHello");
        Files.writeString(Paths.get("test2.txt"), "9999999999\n-56.78\nWorld");
    }

    @AfterEach
    public void teardown() throws IOException {
        Files.deleteIfExists(Paths.get("test1.txt"));
        Files.deleteIfExists(Paths.get("test2.txt"));
        Files.deleteIfExists(Paths.get("integers.txt"));
        Files.deleteIfExists(Paths.get("floats.txt"));
        Files.deleteIfExists(Paths.get("strings.txt"));
        Files.deleteIfExists(Paths.get("test-integers.txt"));
        Files.deleteIfExists(Paths.get("test-floats.txt"));
        Files.deleteIfExists(Paths.get("test-strings.txt"));
    }

    @Test
    public void testProcessFiles() throws IOException {
        String[] args = {"-o", ".", "-p", "test-", "test1.txt", "test2.txt"};
        CommandLineParser parser = new CommandLineParser(args);
        assertTrue(parser.isValid());

        FileProcessor processor = new FileProcessor(parser);
        processor.processFiles();

        assertTrue(Files.exists(Paths.get("test-integers.txt")));
        assertTrue(Files.exists(Paths.get("test-floats.txt")));
        assertTrue(Files.exists(Paths.get("test-strings.txt")));

        List<String> integers = Files.readAllLines(Paths.get("test-integers.txt"));
        assertEquals(List.of("123", "9999999999"), integers);

        List<String> floats = Files.readAllLines(Paths.get("test-floats.txt"));
        assertEquals(List.of("45.67", "-56.78"), floats);

        List<String> strings = Files.readAllLines(Paths.get("test-strings.txt"));
        assertEquals(List.of("Hello", "World"), strings);
    }

    @Test
    public void testPrintStatisticsShort() {
        String[] args = {"-s", "-o", ".", "test1.txt"};
        CommandLineParser parser = new CommandLineParser(args);
        assertTrue(parser.isValid());

        FileProcessor processor = new FileProcessor(parser);
        processor.processFiles();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        processor.printStatistics();

        String output = outContent.toString();
        assertTrue(output.contains("Number of entries: 3"));
    }

    @Test
    public void testPrintStatisticsFull() {
        String[] args = {"-f", "-o", ".", "test1.txt"};
        CommandLineParser parser = new CommandLineParser(args);
        assertTrue(parser.isValid());

        FileProcessor processor = new FileProcessor(parser);
        processor.processFiles();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        processor.printStatistics();

        String output = outContent.toString();
        assertTrue(output.contains("Number of entries: 3"));
        assertTrue(output.contains("Minimum line length : 5; Maximum line length: 5"));
        assertTrue(output.contains("Minimum fractional number: 45,67; Maximum fractional number: 45,67; Sum: 45,67; Average: 45,67"));
        assertTrue(output.contains("Minimum integer number: 123; Maximum integer number: 123; Sum: 123; Average: 123,00"));
    }

    @Test
    public void testAppendOption() throws IOException {
        String[] args1 = {"-o", ".", "-p", "test-", "test1.txt"};
        CommandLineParser parser1 = new CommandLineParser(args1);
        assertTrue(parser1.isValid());

        FileProcessor processor1 = new FileProcessor(parser1);
        processor1.processFiles();

        List<String> integers = Files.readAllLines(Paths.get("test-integers.txt"));
        List<String> floats = Files.readAllLines(Paths.get("test-floats.txt"));
        List<String> strings = Files.readAllLines(Paths.get("test-strings.txt"));

        assertEquals(List.of("123"), integers);
        assertEquals(List.of("45.67"), floats);
        assertEquals(List.of("Hello"), strings);

        String[] args2 = {"-o", ".", "-p", "test-", "-a", "test2.txt"};
        CommandLineParser parser2 = new CommandLineParser(args2);
        assertTrue(parser2.isValid());

        FileProcessor processor2 = new FileProcessor(parser2);
        processor2.processFiles();

        integers = Files.readAllLines(Paths.get("test-integers.txt"));
        floats = Files.readAllLines(Paths.get("test-floats.txt"));
        strings = Files.readAllLines(Paths.get("test-strings.txt"));

        assertEquals(List.of("123", "9999999999"), integers);
        assertEquals(List.of("45.67", "-56.78"), floats);
        assertEquals(List.of("Hello", "World"), strings);
    }

}

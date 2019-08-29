package mycode.log.parser;

import mycode.log.parse.exception.ParsingException;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogParserImplTest {
    public static LogParser logParser = new LogParserImpl();

    //Please use the right format of path depending on the OS.this one has been used per windows
    static String logFileName = "C:\\Users\\rashmibh\\Desktop\\ACT\\fileA.log";
    static String emptyLogFile = "C:\\Users\\rashmibh\\Desktop\\ACT\\fileEmpty.log";
    static String noContentFile = "C:\\Users\\rashmibh\\Desktop\\ACT\\nocontent.log";

    @Before
    public void prePopulateData() {
        List<String> lines = Arrays.asList("177.71.128.213 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"", "168.41.191.40 - - [09/Jul/2018:10:11:30 +0200] \"GET http://example.net/faq/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\"");
        Path file = Paths.get(logFileName);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File fileN = new File(noContentFile);
            fileN.createNewFile();
         } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @After
    public void cleanupFile() {

        Path file = Paths.get(logFileName);
        try {
            Files.deleteIfExists(file);
            System.out.println("Done cleaning up....");
        } catch (IOException e) {
            e.printStackTrace();

       }

    }

    /**
     * get Number Of Unique Ip address (Success case)
     */
    @Test
    public void getNumberOfUniqueIpsSuccess() {

        try {
            int x = logParser.getNumberOfUniqueIps(logFileName);
            assertEquals(2, x);

        } catch (ParsingException e) {
        }
    }


    /**
     * get Number Of Unique Ip address when there are repeated IP address
     * API is expected to find only the unique Ip addresses(Success case)
     */
    @Test
    public void getNumberOfUniqueIpsWithRepeatedLines() {

        try {
            addSameLinesToFile();
            int z = logParser.getNumberOfUniqueIps(logFileName);
            assertEquals(2, z);


        } catch (ParsingException e) {
        }
    }

    /**
     * get Number Of Unique Ip address when there are repeated IP address and new set of IPs
     * API is expected to find only the unique Ip addresses; repeated lines should not get counted(Success case)
     */
    @Test
    public void getNumberOfUniqueIpsAfterNewlyAddedLines() {

        try {

            addSameLinesToFile();
            addSomeNewLinesTofile();
            int y = logParser.getNumberOfUniqueIps(logFileName);
            assertEquals(4, y);
        } catch (ParsingException e) {
        }
    }

    /**
     * when there is no file present. (Failure case)
     */
    @Test(expected = ParsingException.class)
    public void getNumberOfUniqueIpsWhenNoFilePresent() throws ParsingException {

        int y = logParser.getNumberOfUniqueIps(emptyLogFile);

    }

    /**
     * when there is an empty file . (Failure case)
     */
    @Test
    public void getNumberOfUniqueIpsWhenNoContent() throws ParsingException {

        int y = logParser.getNumberOfUniqueIps(noContentFile);
        assertEquals(0, y);

    }

    /**
     * get the Most hit IP addresses. (success case)
     */
    @Test
    public void getTopXActiveIpsSuccess() {
        try {

            String top[] = logParser.getTopXActiveIps(logFileName, 2);
            assertEquals(2, top.length);


        } catch (ParsingException e) {
        }
    }

    /**
     * get the Most hit IP addresses when there is no file. (failure case)
     */

    @Test(expected = ParsingException.class)
    public void getTopXActiveIpsWhenNoFile() throws ParsingException {

        String top[] = logParser.getTopXActiveIps(emptyLogFile, 2);

    }

    /**
     * get the Most hit IP addresses when there
     * is a request for listing more than the number of lines in log itself.. (failure case)
     */
    @Test
    public void getTopXMoreThanLinesinLogSuccessCase() throws ParsingException {

        String top[] = logParser.getTopXActiveIps(logFileName, 12);
        assertTrue(top.length < 12);
    }


    @Test
    public void getTopXActiveIpsWhenEmptyFile() throws ParsingException {

        String top[] = logParser.getTopXActiveIps(noContentFile, 2);
        assertEquals(0, top.length);
    }

    @Test
    public void getTopXURLs() {
        try {

            String top[] = logParser.getTopXURLs(logFileName, 2);
            assertEquals(2, top.length);

            assertTrue(top[0].contains("GET http://example.net/faq/ HTTP/1.1"));
        } catch (ParsingException e) {
        }
    }

    @Test
    public void getTopXURLsWithNewAdditions() {
        try {
            addSomeNewLinesWithNewURLs();
            String top[] = logParser.getTopXURLs(logFileName, 2);
            assertEquals(2, top.length);
            assertFalse(top[0].contains("GET http://example.net/faq/ HTTP/1.1"));
            ;

        } catch (ParsingException e) {
        }
    }


    /*
    Internal utility methods
     */

    private void addSameLinesToFile() {
        List<String> lines = Arrays.asList("177.71.128.213 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"", "168.41.191.40 - - [09/Jul/2018:10:11:30 +0200] \"GET http://example.net/faq/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\"");
        Path file = Paths.get(logFileName);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void addSomeNewLinesTofile()

    {
        List<String> lines = Arrays.asList("177.71.128.112 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"", "168.41.191.134 - - [09/Jul/2018:10:11:30 +0200] \"GET http://example.net/faq/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\"");
        Path file = Paths.get(logFileName);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addSomeNewLinesWithNewURLs()

    {
        List<String> lines = Arrays.asList("177.71.128.112 - - [10/Jul/2018:22:21:28 +0200] \"POST /intranet-analytics-xyz/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\"", "168.41.191.134 - - [09/Jul/2018:10:11:30 +0200] \"GET http://intranet-analytics-xyz/faq/ HTTP/1.1\" 200 3574 \"-\" \"Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\",177.71.128.112 - - [10/Jul/2018:22:21:28 +0200] \\\"POST /intranet-analytics/ HTTP/1.1\\\" 200 3574 \\\"-\\\" \\\"Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7\\\"\", \"168.41.191.134 - - [09/Jul/2018:10:11:30 +0200] \\\"GET http://intranet-analytics-xyz HTTP/1.1\\\" 200 3574 \\\"-\\\" \\\"Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\\\"");
        Path file = Paths.get(logFileName);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
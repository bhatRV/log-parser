package mycode.log.parser;

import mycode.log.parse.exception.ParsingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map.Entry;

/**
 * This class reads from the log file creates 2 maps
 * Ip and the Request URLs Maps created are then sorted *
 */

public class LogParserImpl implements LogParser {



    /**
     * Gets the Total count of unique Ips addresses from the Given Log file
     * @param logfilePath
     * @return
     * @throws ParsingException
     */
    public int getNumberOfUniqueIps(String logfilePath) throws ParsingException {
        Map<String, Integer> urlMap = new HashMap<>();
        Map<String, Integer> ipMap = new HashMap<>();
        //log file to Map conversion
        processLogFileAndPopulateMap(logfilePath, urlMap, ipMap);
        int numberOFUniqueIps = ipMap.size();
        return numberOFUniqueIps;
    }

    /**
     * Fetches the Top N Ips that are hit most number of times
     * @param logfilePath
     * @param top
     * @return
     * @throws ParsingException
     */
    public String[] getTopXActiveIps(String logfilePath, int top) throws ParsingException {
        Map<String, Integer> urlMap = new HashMap<>();
        Map<String, Integer> ipMap = new HashMap<>();
        //log file to Map conversion
        processLogFileAndPopulateMap(logfilePath, urlMap, ipMap);
        //sort to get the top X items
        List ipList = sortMapToList(ipMap);
        int numberOFUniqueIps = ipList.size();
        String topIps[] = new String[numberOFUniqueIps > (top - 1) ? top : numberOFUniqueIps];
        // can do some String operations to remove the counter.
        for (int i = 0; i < (numberOFUniqueIps > (top - 1) ? top : numberOFUniqueIps); i++) {
           //topIps[i] = ipList.get(i).toString();
             topIps[i] = (ipList.get(i).toString()).substring(0,ipList.get(i).toString().indexOf("="));

        }
        return topIps;

    }

    /**
     * Get the List of Top N urls based on the Hit count
     * @param logfilePath
     * @param top
     * @return
     * @throws ParsingException
     */
    public String[] getTopXURLs(String logfilePath, int top) throws ParsingException {
        Map<String, Integer> urlMap = new HashMap<>();
        Map<String, Integer> ipMap = new HashMap<>();
        //log file to Map conversion
        processLogFileAndPopulateMap(logfilePath, urlMap, ipMap);

        //sort to get the top X items
        List urlList = sortMapToList(urlMap);


        int numberOFUniqueIps = urlList.size();
        String topurls[] = new String[numberOFUniqueIps > (top - 1) ? top : numberOFUniqueIps];
        //System.out.println("2. Top " + top + "most active IPs => \n ");
        for (int i = 0; i < (numberOFUniqueIps > (top - 1) ? top : numberOFUniqueIps); i++) {
            // topurls[i] = urlList.get(i).toString();
            topurls[i] = (urlList.get(i).toString()).substring(0,urlList.get(i).toString().indexOf("="));
        }
        return topurls;

    }

    /**
     * Process the file to populate internal Map ,
     *
     * @param logfilePath
     * @param urlMap
     * @param ipMap
     */
    private void processLogFileAndPopulateMap(String logfilePath, Map<String, Integer> urlMap, Map<String, Integer> ipMap) throws ParsingException {


        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(logfilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //no file found throw some exception..
            throw new ParsingException("No log file found for processing ");
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();


            String pattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"";

            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(line);
            if (m.find()) {
                String ipAddress = m.group(1);
                String requestUrl = m.group(5);

                //add Ip to IP MAP
                if (ipMap.get(ipAddress) == null) {
                    ipMap.put(ipAddress, 1);
                } else {
                    int value = ipMap.get(ipAddress).intValue();
                    value++;
                    ipMap.put(ipAddress, value);
                }

                //add URL to urlMap
                if (urlMap.get(requestUrl) == null) {
                    urlMap.put(requestUrl, 1);
                } else {
                    int value = urlMap.get(requestUrl).intValue();
                    value++;
                    urlMap.put(requestUrl, value);
                }

            } else {
                System.out.println("NO MATCH.SKIP to next line");
            }

        }
        scanner.close();

       // System.out.println("Done processing file");
    }

    /**
     * Sort the map by value
     *
     * @param inputMap
     * @return
     */
    private List sortMapToList(Map<String, Integer> inputMap) {
        Set<Entry<String, Integer>> entries = inputMap.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<>(entries);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        return list;
    }
}



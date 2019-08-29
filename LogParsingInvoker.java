package mycode.log;

import mycode.log.parse.exception.ParsingException;
import mycode.log.parser.LogParser;
import mycode.log.parser.LogParserImpl;

public class LogParsingInvoker {
static String logFileName="C:\\Users\\rashmibh\\Desktop\\ACT\\fileA.log";

public static LogParser logParser = new LogParserImpl();

    /**
     * This can be later extended to add a REST controller if REST needs to be exposed.
     * For now keeping it simple with main
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: pass <fileName>  <HowManyTopElements(optional)>");
            System.exit(0);
        }
        try {
            int ips = logParser.getNumberOfUniqueIps(args[0]);
            System.out.println("IPS: " + ips);
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        if (args.length > 1)

        { //fetch Top URLS
            try {
                String ips[] = logParser.getTopXURLs(args[0], Integer.parseInt(args[1]));
                System.out.println("TOP URLS  : " );
                for (int i = 0; i < ips.length; i++) {
                    System.out.println( ips[i]);

                }


            } catch (ParsingException e) {
                e.printStackTrace();
            }


            //fetch top IPs
            try {
                String ips[] = logParser.getTopXActiveIps(args[0], Integer.parseInt(args[1]));


             System.out.println("\nIPS top : " );
                for (int i = 0; i < ips.length; i++) {
                    System.out.println( ips[i]);

                }


            } catch (ParsingException e) {
                e.printStackTrace();
            }


    }
    }
}

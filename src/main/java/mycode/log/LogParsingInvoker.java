package mycode.log;

import mycode.log.parse.exception.ParsingException;
import mycode.log.parser.LogParser;
import mycode.log.parser.LogParserImpl;

public class LogParsingInvoker {

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

        //cunt of Unique IP addresses
        try {
            int ips = logParser.getNumberOfUniqueIps(args[0]);
            System.out.println("Number of Unique IP addresses: " + ips);
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        if (args.length > 1)

        { //fetch Top URLS
            try {
                String ips[] = logParser.getTopXURLs(args[0], Integer.parseInt(args[1]));
                System.out.println("\n"+Integer.parseInt(args[1])+ " Most visited URLS  : " );
                for (int i = 0; i < ips.length; i++) {
                    System.out.println( ips[i]);

                }


            } catch (ParsingException e) {
                e.printStackTrace();
            }


            //fetch top IPs
            try {
                String ips[] = logParser.getTopXActiveIps(args[0], Integer.parseInt(args[1]));


             System.out.println("\n"+Integer.parseInt(args[1]) +" Most visited IPs  : " );
                for (int i = 0; i < ips.length; i++) {
                    System.out.println( ips[i]);

                }


            } catch (ParsingException e) {
                e.printStackTrace();
            }


    }
    }
}

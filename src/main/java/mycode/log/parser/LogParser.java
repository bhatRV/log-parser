package mycode.log.parser;

import mycode.log.parse.exception.ParsingException;

public interface LogParser {
    public int getNumberOfUniqueIps(String logfilePath) throws ParsingException;
    public String[] getTopXActiveIps(String logfilePath,int top) throws ParsingException;
    public String[] getTopXURLs(String logfilePath,int top) throws ParsingException ;
    }

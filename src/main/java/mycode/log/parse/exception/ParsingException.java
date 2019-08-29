package mycode.log.parse.exception;

/**
 * Log file Parser Related exceptions
 */
public class ParsingException extends Exception  {


        public ParsingException() {
            super();
        }


        public ParsingException(String message, Throwable cause) {
            super(message, cause);
        }

        public ParsingException(String message) {
            super(message);
        }



}

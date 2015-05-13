/* exception raised when an unimplemented HTTP action is called 
 * as for now, only GET is implemented */
public class UndefinedActionException extends Exception {
    public UndefinedActionException(String message) {
        super(message);
    }
}

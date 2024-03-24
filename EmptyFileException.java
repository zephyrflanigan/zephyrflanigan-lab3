import java.io.*;
import java.util.*;

public class EmptyFileException extends IOException{
        public EmptyFileException(String exceptionMessage) {
        super(exceptionMessage);
    }

}
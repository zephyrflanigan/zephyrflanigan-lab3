import java.io.*;
import java.util.*;

public class TooSmallText extends Exception{
    public TooSmallText(String exceptionMessage) {
        super(exceptionMessage);
    }
}
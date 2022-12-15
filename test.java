import java.util.*;

public class test {
    public static void main(String[] args) {
        if ("abcc".contains("abc")){
            System.out.println("true");
        }
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("h");
        strings.add("e");
        strings.add("l");
        strings.add("l");
        strings.add("o");
        String listString = String.join("", strings);
        System.out.println(listString);
        System.out.println(listString.length());
    }
}

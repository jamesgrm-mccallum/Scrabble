
public class test {
    public static String[] tester(){
        return new String[]{"hello", "world"};
    }
    public static void main(String[] args) {
        for (String word : tester()){
            System.out.println(word);
        }
    
    }
}
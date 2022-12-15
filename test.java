public class test {
    public static void main(String[] args) {
        String word = "hello";
        String[] wordArray = word.split("");
        for (String letter : wordArray){
            System.out.println(letter);
        }
        
    }
}

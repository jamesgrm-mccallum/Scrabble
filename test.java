public class test {
    public static void main(String[] args) {
        String row_accum = "";
        String cell;
        for (int r = 0; r < 14; r++){
            row_accum = " ";
            System.out.println("-------------------------------");
            for (int c = 0; c < 14; c++){
                cell = String.format("|%s|", " ");
                row_accum += cell;
                
            }
            System.out.println(row_accum);
        }
    }
}

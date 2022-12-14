public class test {
    public static void main(String[] args) {
        String accum = "";
        String row_accum = "";
        String cell = "";
        for (int h = 0; h < 15; h++){
            char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            cell = ""; 
            cell += String.format("| %s ", alphabet[h]);
            row_accum += cell;
        }
        
        accum += "   " + row_accum + "|" + "\n";
        for (int r = 0; r < 15; r++){
            row_accum = "";
            row_accum += "---------------------------------------------------------------\n";
            for (int c = 0; c < 15; c++){
                if (c == 0){
                    row_accum += String.valueOf(r + 1);
                    if (r < 9){
                        row_accum += " ";
                    }
                }
                
                cell = String.format(" |%s ", " ");
                row_accum += cell;
                if (c == 14){
                    row_accum += " |";
                }
                
            }
            row_accum += "\n";
            accum += row_accum;
        }
        System.out.println(accum);
    }
}

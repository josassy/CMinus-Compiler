import java.io.*;

public class ScanDaemon {
    public static void main(String[] args) {
        FileReader in = null;

        try {
            in = new FileReader(args[0]);
        }
        catch(FileNotFoundException e) {
            System.out.println("Big problemo, boss!");
            return;
        }

        BufferedReader inProg = new BufferedReader(in);
        CMinusScanner scanner = new CMinusScanner(inProg);

        
    }
}

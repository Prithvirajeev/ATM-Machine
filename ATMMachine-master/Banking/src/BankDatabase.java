import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * This class is the handling the communication with database(here using the file storage for making the model simple
 * and concentrating on the design
 * @author ARCHIT
 *
 */
public class BankDatabase {
    private static HashMap<String, String[]> bankDB = new HashMap<String, String[]>();
    /**
     * Loading the whole database to the server and creating a HashMap for faster Access
     * @param fileName
     * @return
     */
    public HashMap<String, String[]> readTextFile(String fileName) {
        int n = 4;
        String cardNumber = "";
        String values[] = new String[3];
        FileReader file = null;
        try {
            file = new FileReader(System.getProperty("user.dir") + "/storage/BankCustomerDB");
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < n; i++) {
                    if (i != 0) {
                        values[i - 1] = line;
                    } else {
                        cardNumber = line;
                    }
                    line = reader.readLine();
                }
                bankDB.put(cardNumber, values);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    // Ignore issues during closing
                }
            }
        }
        return bankDB;
    }
    /**
     * After Some changes to the database saving and reloading the database
     * @param bankDB2
     * @return
     * @throws IOException
     */
    public boolean updateEntries(HashMap<String, String[]> bankDB2) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/storage/BankCustomerDB");
        FileWriter fw = new FileWriter(file, false);
        String db = "";
        Iterator it = bankDB2.entrySet().iterator();
        while (it.hasNext()) {
            
            Map.Entry pairs = (Map.Entry)it.next();
            db += pairs.getKey() + "\n";
            String[] values = (String[]) pairs.getValue();
            for(int i=0;i<3;i++) {
                db += values[i] + "\n";
            }
        }
        fw.write(db);
        fw.close();
        return true;
    }
};

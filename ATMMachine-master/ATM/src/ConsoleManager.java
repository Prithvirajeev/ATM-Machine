

import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleManager {
    private Scanner reader;

    public ConsoleManager() {
        reader = new Scanner(new InputStreamReader(System.in));

    }
    /**
     * This class is reponsible for Handling with the screen(in real implementation it will interact with the hardware).
     * @param text
     * @param isInputnNeeded
     * @return
     */
    public int setScreen(String text, boolean isInputnNeeded) {
        System.out.println(text);
        if (isInputnNeeded) {
            int option = reader.nextInt();
            return option;
        } else {
            return -1;
        }

    }
};

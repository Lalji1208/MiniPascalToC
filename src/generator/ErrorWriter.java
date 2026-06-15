package generator;

import java.io.FileWriter;

public class ErrorWriter {

    public static void writeError(
            String message) {

        try {

            FileWriter writer =
                    new FileWriter(
                            "output/error.invalid"
                    );

            writer.write(message);

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
import jssc.SerialPortException;
import pl.edu.agh.kis.visca.ViscaResponseReader;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, ViscaResponseReader.TimeoutException, InstantiationException, SerialPortException, IllegalAccessException {
        WebApp.main(args);
        ConsoleApp.main(args);
        Common.setPort("/dev/ttyUSB0");
    }
}

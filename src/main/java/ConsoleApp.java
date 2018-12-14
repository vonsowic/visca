import jssc.SerialPortException;
import pl.edu.agh.kis.visca.ViscaResponseReader;
import pl.edu.agh.kis.visca.cmd.PanTiltHomeCmd;
import pl.edu.agh.kis.visca.cmd.ViscaCommand;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException, ViscaResponseReader.TimeoutException, SerialPortException {
//        Common.setPort("/dev/ttyUSB0");

        Scanner reader = new Scanner(System.in);  // Reading from System.in

        while (true) {
            System.out.print("> ");
            String res = reader.nextLine();

            // cmd camera pan tilt
            String[] ress = res.split(" ");

            if (ress[0].equals("set")) {
                System.out.println(ress[1]);
                Common.setPort(ress[1]);
            } else {
                System.out.println(handleCommand(
                        ress[0],
                        ress.length > 1 ? Byte.parseByte(ress[1]) : 1,
                        ress.length > 2 ? ress[2] : "",
                        ress.length > 3 ? ress[3] : ""
                ));
            }
        }
    }

    private static String handleCommand(String command, byte camera, String panSpeed, String tiltSpeed) throws IllegalAccessException, IOException, InstantiationException, SerialPortException, ViscaResponseReader.TimeoutException {
        switch (command) {
            case "left":
                Common.panTiltLeft(panSpeed, tiltSpeed, camera);
                break;
            case "right":
                Common.panTiltRight(panSpeed, tiltSpeed, camera);
                break;
            case "up":
                Common.sendPanTiltUp(panSpeed, tiltSpeed, camera);
                break;
            case "down":
                Common.panTiltDown(panSpeed, tiltSpeed, camera);
                break;
            case "clear":
                Common.clearAll(camera);
                break;
            case "home":
                Common.panTiltHome(camera);
                break;
            case "address":
                Common.address(camera, panSpeed);
                break;
            case "pos":
                Common.panTiltAbsolutePos(camera);
                break;
            case "speed":
                Common.getPanTiltMaxSpeed(camera);
                break;
            case "zoomtele":
                Common.sendZoomTeleStd(camera);
                break;
            case "zoomwide":
                Common.sendZoomWideStd(camera);
                break;
            default:
                return "Unknown command " + command;
        }

        return Common.readResponse();
    }
}

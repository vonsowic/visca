import jssc.SerialPortException;

import jssc.SerialPort;
import pl.edu.agh.kis.visca.ViscaResponseReader;
import pl.edu.agh.kis.visca.cmd.Cmd;
import pl.edu.agh.kis.visca.cmd.*;


public class Common {

    public static void panTiltLeft(String panSpeed, String tiltSpeed, byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(PanTiltLeftCmd.class, panSpeed, tiltSpeed, camera);
    }

    public static void panTiltRight(String panSpeed, String tiltSpeed, byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(PanTiltRightCmd.class, panSpeed, tiltSpeed, camera);
    }

    public static void panTiltDown(String panSpeed, String tiltSpeed, byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(PanTiltDownCmd.class, panSpeed, tiltSpeed, camera);
    }

    public static void sendPanTiltUp(String panSpeed, String tiltSpeed, byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(PanTiltUpCmd.class, panSpeed, tiltSpeed, camera);
    }

    public static void panTiltHome(byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(PanTiltHomeCmd.class, camera);
    }

    public static void clearAll(byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(ClearAllCmd.class, camera);
    }

    public static void panTiltAbsolutePos(byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(PanTiltAbsolutePosCmd.class, camera);
    }

    public static void address(byte camera, String newCameraAddress) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommandAddress(camera, Byte.valueOf(newCameraAddress));
    }

    public static void getPanTiltMaxSpeed(byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(GetPanTiltMaxSpeedCmd.class, camera);
    }

    public static void sendZoomTeleStd(byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(ZoomTeleStdCmd.class, camera);
    }

    public static void sendZoomWideStd(byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(ZoomWideStdCmd.class, camera);
    }


    public static String readResponse() throws ViscaResponseReader.TimeoutException, SerialPortException {
        String res = byteArrayToString(ViscaResponseReader.readResponse(getSerialPort()));
        String formatedRes = res.substring(3);
        formatedRes = formatedRes.replace(" FF", "");


        System.out.println("Original response: " + res);

        if (formatedRes.equals("60 41")) {
            return "Command not executable";
        } else if (formatedRes.startsWith("4")) {
            return "ACK";
        } else if (formatedRes.startsWith("50")) {
            return "Information return";
        } else if (formatedRes.startsWith("5")) {
            return "Command completion";
        } else if (formatedRes.startsWith("60 ")){
            return "error";
        }


        return res;
    }

    public static String byteArrayToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        byte[] var5 = bytes;
        int var4 = bytes.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            byte b = var5[var3];
            sb.append(String.format("%02X ", b));
        }

        return sb.toString();
    }

    public static SerialPort port=null;

    public static SerialPort getSerialPort() {
        return port;
    }

    public static void setPort(String portName) throws SerialPortException {
        if (port != null) {
            port.closePort();
        }

        port = createSerialPort(portName);
    }

    public static SerialPort createSerialPort(String portName) {
        //"/dev/ttyUSB0"
        SerialPort serialPort = new SerialPort(portName);
        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, 0);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }

        return serialPort;
    }

    public static void sendCommand(Class<? extends Cmd> cmd, byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        sendCommand(cmd, "", "", camera);
    }

    public static void sendCommand(Class<? extends Cmd> cmd, String panSpeed, String tiltSpeed, byte camera) throws IllegalAccessException, InstantiationException, SerialPortException {
        byte[] cmdData = cmd.newInstance().createCommandData();

        if (!panSpeed.isEmpty()) {
            cmdData[3] = Byte.parseByte(panSpeed);
        }

        if (!tiltSpeed.isEmpty()) {
            cmdData[4] = Byte.parseByte(tiltSpeed);
        }


        ViscaCommand vCmd = new ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;

        if (camera == 8) {
            vCmd.destinationAdr = (byte) 0x88;
        } else {
            vCmd.destinationAdr = camera;
        }

	    cmdData = vCmd.getCommandData();
        getSerialPort().writeBytes(cmdData);
    }

    public static void sendCommandAddress(byte camera, byte newCamera) throws IllegalAccessException, InstantiationException, SerialPortException {
        byte[] cmdData = new AddressCmd().createCommandData();

        ViscaCommand vCmd = new ViscaCommand();
        cmdData[1] = newCamera;
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = 0;

        if (camera == 8) {
            vCmd.destinationAdr = (byte) 0x88;
        } else {
            vCmd.destinationAdr = camera;
        }


        cmdData = vCmd.getCommandData();
        getSerialPort().writeBytes(cmdData);
    }

}

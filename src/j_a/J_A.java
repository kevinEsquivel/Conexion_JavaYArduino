/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j_a;

import comunicacionserial.ArduinoExcepcion;
import comunicacionserial.ComunicacionSerial_Arduino;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.JOptionPane.showInputDialog;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author kevin
 */
public class J_A {

    static ComunicacionSerial_Arduino conn = new ComunicacionSerial_Arduino();
    //static ComunicacionSerial_Arduino connTX = new ComunicacionSerial_Arduino();
    static String cad = "";

    static SerialPortEventListener listen = new SerialPortEventListener() {
        @Override

        public void serialEvent(SerialPortEvent spe) {
            try {
                if (conn.isMessageAvailable()) {
                    //System.out.println( conn.printMessage()+"\n\n");
                    cad = conn.printMessage();
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArduinoExcepcion ex) {
                Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String nombre = "";

    public static void conectarAlArduino() {
        try {
            conn.arduinoRXTX("COM3", 9600, listen);
            //connTX.arduinoTX("COM3", 9600);

        } catch (ArduinoExcepcion ex) {
            Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void enviar(String e) throws SerialPortException {
        try {
            conn.sendData(e);
        } catch (ArduinoExcepcion ex) {
            Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static double dist(String sensor, String medida) {
        String arg[] = cad.split(":");
        double m = 0;
        if (arg[0].equalsIgnoreCase(sensor)) {
            m = Double.parseDouble(arg[1]);
            if(medida.equals("cm")) {
                return m;
            } else if (medida.equals("m")) {
                return (m / 100);
            } else if (medida.equals("ft")) {
                return (m / 30.48);
            }
        }

        return -1;
    }

    public static void agarrar() {

        try {
            conn.sendData("0");
        } catch (ArduinoExcepcion ex) {
            Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void soltar() throws SerialPortException {

        try {
            conn.sendData("1");
        } catch (ArduinoExcepcion ex) {
            Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void girar(int n, String s) throws SerialPortException {
//************GIRAR BRAZO COMPLETO***************************************
        if (n == 90 && s.equalsIgnoreCase("Servo1")) {
            enviar("2");
        }
        if (n == 0 && s.equalsIgnoreCase("Servo1")) {
            enviar("3");
        }
//***************GUIRAR ARTICULAZIÓN*************************************
        if (n == 0 && s.equalsIgnoreCase("Servo2")) {
            enviar("4");
        }
        if (n == 90 && s.equalsIgnoreCase("Servo2")) {
            enviar("5");
        }
        if (n == 180 && s.equalsIgnoreCase("Servo2")) {
            enviar("6");
        }
//***********GIRAR BASE**************************************************
        if (n == 90 && s.equalsIgnoreCase("Servo3")) {
            enviar("7");
        }
        if (n == 0 && s.equalsIgnoreCase("Servo3")) {
            enviar("8");
        }
    }

    public static void reset() throws SerialPortException {
        //**PONER EN 90 GRADOS TODO
        enviar("2");
        enviar("5");
        enviar("7");
    }

    public static void main(String[] args) {
        try {
            conectarAlArduino();
            while (true) {
                Thread.sleep(1000);
                System.out.print(dist("Norte", "m") + "\n");
            }
            /*
            for(int i=0;i<3;i++){
            Thread.sleep(3000);
            agarrar();
        
            Thread.sleep(3000);
        
            soltar();
            Thread.sleep(3000);
            }
            
            girar(0, "Servo2");
            Thread.sleep(3000);*/

        } catch (InterruptedException ex) {
            Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
        }/* catch (SerialPortException ex) {
            Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}

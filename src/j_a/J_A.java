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

/**
 *
 * @author kevin
 */
public class J_A {
    static ComunicacionSerial_Arduino conn = new ComunicacionSerial_Arduino();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String nombre ="";

    private static void conectarAlArduino() {
        try {
            conn.arduinoTX("COM3",9600);
        } catch (ArduinoExcepcion ex) {
            Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void enviar(String e){
        try {
            conn.sendData(e);
        } catch (ArduinoExcepcion ex) {
            Logger.getLogger(J_A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void girar(int n,String s){
        if(n==0 && s.equalsIgnoreCase("Servo1")){
        enviar("1");
        }
        if(n==10 && s.equalsIgnoreCase("Servo1")){
        enviar("1.1");
        }
        if(n==90 && s.equalsIgnoreCase("Servo1")){
        enviar("2");
        }
        if(n==180 && s.equalsIgnoreCase("Servo1")){
        enviar("3");
        }
//***************SERVO 2*************************************************************
        if(n==0 && s.equalsIgnoreCase("Servo2")){
        enviar("4");
        }
        if(n==90 && s.equalsIgnoreCase("Servo2")){
        enviar("5");
        }
        if(n==180 && s.equalsIgnoreCase("Servo2")){
        enviar("6");
        }
//***************SERVO 3*************************************************************
        if(n==0 && s.equalsIgnoreCase("Servo3")){
        enviar("7");
        }
        if(n==90 && s.equalsIgnoreCase("Servo3")){
        enviar("8");
        }
        if(n==180 && s.equalsIgnoreCase("Servo3")){
        enviar("9");
        }
        
        
    }
  
    public static void main(String[] args) throws IOException {
        
        conectarAlArduino();
          while(true){
            String Servo1 = "Servo1";
              girar(0,Servo1);
              girar(90,Servo1);
              girar(180,Servo1);
       }
    }

    
       
    
}

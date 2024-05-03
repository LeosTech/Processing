  // Need G4P library
import g4p_controls.*;
// You can remove the PeasyCam import if you are not using
// the GViewPeasyCam control or the PeasyCam library.
import peasy.*;

import processing.serial.*;


int lf = '\n';    // Linefeed in ASCII
String myString = null;
int i = 0;
Serial myPort;  // The serial port
String[] serial_string;
boolean controllo_rele1;
boolean controllo_rele2;
boolean controllo_rele3;
boolean controllo_rele4;
boolean controllo_rele5;
boolean controllo_rele6;
boolean controllo_rele7;
boolean controllo_rele8;

public void setup(){
  size(1000, 600, JAVA2D);
  createGUI();
  customGUI();
  
  printArray(Serial.list());
  //myPort = new Serial(this, Serial.list()[1], 9600);
  //myPort.clear();
  //myString = myPort.readStringUntil(lf);
  //myString = null;
  //Salvataggio delle COM:
  saveStrings("data/list_650212", Serial.list());  
  serial_string = new String[3];
  
  controllo_rele1 = false;
  controllo_rele2 = false;
  controllo_rele3 = false;
  controllo_rele4 = false;
  controllo_rele5 = false;
  controllo_rele6 = false;
  controllo_rele7 = false;
  controllo_rele8 = false;

  

}

public void draw(){
  background(230);
  //delay(200);
  
  
 if (myPort != null) {
   while (myPort.available() > 0) {
     myString = myPort.readStringUntil(lf);
    if (myString != null) {
      serial_string[i] = myString;  
      printArray(serial_string);
      println("===== end of read"+ i + " =======");
      if(i<2) i++;
      else i=0;
    }
    for(int j=0;j<3;j=j+1){
      if(serial_string[j] != null && serial_string[j].length() == 20){
        String[] rele = split(serial_string[j],';');
        //printArray(rele);
        //printArray(rele[1]);
        if(rele[1].equals("1") == true){
          rele1.setLocalColorScheme(GCScheme.GREEN_SCHEME);
          controllo_rele1 = true;
        }else{
          rele1.setLocalColorScheme(GCScheme.RED_SCHEME);
          controllo_rele1 = false;
        }
        if(rele[2].equals("1") == true){
          rele2.setLocalColorScheme(GCScheme.GREEN_SCHEME);
          controllo_rele2 = true;
        }else{
          rele2.setLocalColorScheme(GCScheme.RED_SCHEME);
          controllo_rele2 = false;
        }
        if(rele[3].equals("1") == true){
          rele3.setLocalColorScheme(GCScheme.GREEN_SCHEME);
          controllo_rele3 = true;
        }else{
          rele3.setLocalColorScheme(GCScheme.RED_SCHEME);
          controllo_rele3 = false;
        }
        if(rele[4].equals("1") == true){
          rele4.setLocalColorScheme(GCScheme.GREEN_SCHEME);
          controllo_rele4 = true;
        }else{
          rele4.setLocalColorScheme(GCScheme.RED_SCHEME);
          controllo_rele4 = false;
        }
        if(rele[5].equals("1") == true){
          rele5.setLocalColorScheme(GCScheme.GREEN_SCHEME);
          controllo_rele5 = true;
        }else{
          rele5.setLocalColorScheme(GCScheme.RED_SCHEME);
          controllo_rele5 = false;
        }
        if(rele[6].equals("1") == true){
          rele6.setLocalColorScheme(GCScheme.GREEN_SCHEME);
          controllo_rele6 = true;
        }else{
          rele6.setLocalColorScheme(GCScheme.RED_SCHEME);
          controllo_rele6 = false;
        }
        if(rele[7].equals("1") == true){
          rele7.setLocalColorScheme(GCScheme.GREEN_SCHEME);
          controllo_rele7 = true;
        }else{
          rele7.setLocalColorScheme(GCScheme.RED_SCHEME);
          controllo_rele7 = false;
        }
        if(rele[8].equals("1") == true){
          rele8.setLocalColorScheme(GCScheme.GREEN_SCHEME);
          controllo_rele8 = true;
        }else{
          rele8.setLocalColorScheme(GCScheme.RED_SCHEME);
          controllo_rele8 = false;
        }
      }

      
      
    }
    for(int j=0;j<3;j=j+1){
      if (serial_string[j] != null && serial_string[j].length() >= 45){
          String[] list = split(serial_string[j],';');
          printArray(list);
           //delay(200);
          if(list[3].equals("Movimento: 1") == true){
            movimento.setText("Movimento Rilevato!");
          }else{
            movimento.setText("Nessun Movimento!");
          }
          
          
          soil_humidity.setText(list[2]);
          
          temperatura.setText(list[1]);
          //delay(200);
          humidity.setText(list[0]);
          //delay(200);
          //println(myString);
          //delay(1000);
        }
    }
  
   }
 }
}

// Use this method to add additional statements
// to customise the GUI controls
public void customGUI(){

}

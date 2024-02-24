// Need G4P library
import g4p_controls.*;
// You can remove the PeasyCam import if you are not using
// the GViewPeasyCam control or the PeasyCam library.
import peasy.*;

import processing.serial.*;


int[] numbers = new int[3];
int lf = '\n';    // Linefeed in ASCII
String myString = null;
Serial myPort;  // The serial port

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
  for(int i=0;i<3;i++){
    numbers[i]=0;
  }
  

}

public void draw(){
  background(230);
  String joinedNumbers = join(nf(numbers,0),";");
  //delay(200);
  
  
 if (myPort != null) {
   while (myPort.available() > 0) {
    myString = myPort.readStringUntil(lf);
    
    if (myString != null  && myString.length()>20) {
      String[] list = split(myString,';');
      //delay(200);
      
      temperatura.setText(list[1]);
      //delay(200);
      humidity.setText(list[0]);
      //delay(200);
      heat_index.setText(list[2]);
      println(myString);
      ///delay(200);
      println(joinedNumbers+';');
      delay(100);
      myPort.write(joinedNumbers+';');
    }
  }
 }
}

// Use this method to add additional statements
// to customise the GUI controls
public void customGUI(){

}

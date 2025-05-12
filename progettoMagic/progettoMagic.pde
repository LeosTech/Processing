// Librerie necessarie
import peasy.*;
import g4p_controls.*;
import processing.serial.*;
import java.awt.Font;
import java.io.FileWriter;
import grafica.*;


//setup
void setup() {
  size(900, 650, JAVA2D);
 //controlla le seriali disponibili
 starting();
  // Prima crea la GUI
  createGUI();
}

void draw() {
  background(50);
}

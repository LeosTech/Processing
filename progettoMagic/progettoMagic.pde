// Librerie necessarie
import g4p_controls.*;
import processing.serial.*;
import java.util.ArrayList;
import java.util.Collections;

Serial myPort;  // Oggetto seriale per comunicazione
String myString = null;  // Stringa per ricevere i dati da Arduino

// Variabili per l'interfaccia
GButton saveButton, startButton, stopButton, connectButton, saveGaussButton;
GTextField fileNameField, minValueInput, gaussValueInput, personalNameField, personalSurnameField;
GLabel temperatura;
GDropList portList;
ArrayList<Float> values = new ArrayList<Float>();  // Lista dei valori letti
String customFileName = ""; // Nome personalizzato del file
float minGaussMeasured = Float.MAX_VALUE;
float minTargetValue = 0;
boolean isDemagnetizing = false;
String[] availablePorts;

int lf = '\n';  // Linefeed in ASCII

void setup() {
  size(900, 650, JAVA2D);

  // Prima crea la GUI
  createGUI();
  
  // Poi popola la lista delle porte (se esistono)
  availablePorts = Serial.list();
  
  if (availablePorts.length > 0) {
    portList.setItems(availablePorts, 0); // Imposta il menu delle porte
  } else {
    portList.setItems(new String[] {"Nessuna porta trovata"}, 0);
  }
}

void draw() {
  background(255);

  if (myPort != null) {
    while (myPort.available() > 0) {
      myString = myPort.readStringUntil(lf);
      if (myString != null) {
        myString = trim(myString);
        temperatura.setText(myString);
        try {
          float val = float(myString);
          values.add(val);

          if (val < minGaussMeasured) {
            minGaussMeasured = val;
          }
        }
        catch (Exception e) {
          println("Errore parsing valore ricevuto: " + myString);
        }
      }
    }
  }
  
  // Scrive i dati a schermo
  fill(0);
  textSize(16);
  textAlign(LEFT, TOP);
  text("Minimo misurato: " + nf(minGaussMeasured, 0, 2) + " Gauss", 50, 150);
  text("Target minimo: " + nf(minTargetValue, 0, 2) + " Gauss", 500, 150);
  
  // Disegna il grafico
  drawGraph();
}

// Creazione GUI G4P
public void createGUI() {
  portList = new GDropList(this, 50, 50, 300, 200);
  connectButton = new GButton(this, 370, 50, 120, 30, "Connetti");

  startButton = new GButton(this, 50, 570, 150, 40, "Start Smagnetizzazione");
  stopButton = new GButton(this, 250, 570, 150, 40, "Stop Smagnetizzazione");
  minValueInput = new GTextField(this, 500, 570, 150, 40, G4P.SCROLLBARS_NONE);
  gaussValueInput = new GTextField(this, 500, 120, 150, 40, G4P.SCROLLBARS_NONE);
  temperatura = new GLabel(this, 650, 50, 200, 40, "Valore Ricevuto");
  
  saveButton = new GButton(this, 650, 500, 120, 40, "Salva Grafico");
  saveGaussButton = new GButton(this, 650, 450, 120, 40, "Salva Gauss");
  fileNameField = new GTextField(this, 650, 400, 120, 30);
  personalNameField = new GTextField(this, 650, 350, 120, 30);
  personalSurnameField = new GTextField(this, 650, 300, 120, 30);
  fileNameField.setText("Nome file");
  personalNameField.setText("Nome");
  personalSurnameField.setText("Cognome");

  // Imposta il colore di sfondo e testo dei componenti
  temperatura.setBgColor(color(0));  // Sfondo nero per l'etichetta
  temperatura.setTextColor(color(255));      // Testo bianco
  
  // Imposta il colore di sfondo per i bottoni
  startButton.setBgColor(color(255, 255, 255)); // Bianco
  stopButton.setBgColor(color(255, 255, 255));  // Bianco
  connectButton.setBgColor(color(255, 255, 255)); // Bianco
  saveButton.setBgColor(color(255, 255, 255));  // Bianco
  saveGaussButton.setBgColor(color(255, 255, 255));  // Bianco
  
  // Imposta il colore del testo per i bottoni
  startButton.setTextColor(color(0));  // Testo nero
  stopButton.setTextColor(color(0));   // Testo nero
  connectButton.setTextColor(color(0)); // Testo nero
  saveButton.setTextColor(color(0));   // Testo nero
  saveGaussButton.setTextColor(color(0));   // Testo nero

  saveButton.addEventHandler(this, "handleButtonEvents");
  saveGaussButton.addEventHandler(this, "handleButtonEvents");
  startButton.addEventHandler(this, "handleButtonEvents");
  stopButton.addEventHandler(this, "handleButtonEvents");
  connectButton.addEventHandler(this, "handleButtonEvents");
  minValueInput.addEventHandler(this, "minValueChanged");
  saveButton.addEventHandler(this, "handleButtonEvents");
  saveGaussButton.addEventHandler(this, "handleButtonEvents");
}

// Eventi bottoni Start/Stop/Connetti
public void handleButtonEvents(GButton button, GEvent event) {
  if (button == startButton) {
    isDemagnetizing = true;
    if (myPort != null) {
      myPort.write("START\n");
    }
  }
  
  if (button == stopButton) {
    isDemagnetizing = false;
    if (myPort != null) {
      myPort.write("STOP\n");
    }
  }
  
  if (button == connectButton) {
    connectToSelectedPort();
  }
  
  if (button == saveButton) {
    customFileName = fileNameField.getText();
    if (customFileName != null && customFileName.length() > 0) {
      saveGraph(customFileName);
    } else {
      println("Nome file non valido");
    }
  }

  if (button == saveGaussButton) {
    String name = personalNameField.getText();
    String surname = personalSurnameField.getText();
    if (name.length() > 0 && surname.length() > 0) {
      saveGaussData(name, surname);
    } else {
      println("Nome e Cognome sono richiesti per salvare i dati");
    }
  }
}

// Evento cambiamento valore minimo
public void minValueChanged(GTextField source, GEvent event) {
  try {
    minTargetValue = float(source.getText());
  }
  catch (Exception e) {
    println("Errore parsing input minimo");
  }
}

// Connessione porta seriale selezionata
void connectToSelectedPort() {
  int selectedIndex = portList.getSelectedIndex();
  if (availablePorts != null && selectedIndex >= 0 && selectedIndex < availablePorts.length) {
    try {
      if (myPort != null) {
        myPort.stop();
      }
      myPort = new Serial(this, availablePorts[selectedIndex], 9600);
      myPort.bufferUntil(lf);
      println("Connesso a: " + availablePorts[selectedIndex]);
    }
    catch (Exception e) {
      println("Errore nella connessione seriale: " + e.getMessage());
    }
  } else {
    println("Nessuna porta valida selezionata.");
  }
}

// Funzione per disegnare il grafico
void drawGraph() {
  stroke(0);
  noFill();
  rect(50, 200, 800, 300);

  if (values.size() > 1) {
    float maxVal = -Float.MAX_VALUE;
    float minVal = Float.MAX_VALUE;

    for (float v : values) {
      if (v > maxVal) maxVal = v;
      if (v < minVal) minVal = v;
    }

    if (maxVal == minVal) maxVal += 1; // Previeni divisione per zero

    beginShape();
    noFill();
    stroke(0, 100, 250);

    for (int i = 0; i < values.size(); i++) {
      float x = map(i, 0, values.size(), 50, 850);
      float y = map(values.get(i), minVal, maxVal, 500, 200);
      vertex(x, y);
    }
    endShape();
  }
}

// Funzione per salvare il grafico con il nome scelto
void saveGraph(String filename) {
  PGraphics pg = createGraphics(800, 300);
  
  pg.beginDraw();
  pg.background(255);
  pg.stroke(0, 200, 150);
  pg.strokeWeight(2);
  pg.noFill();
  
  if (values.size() > 1) {
    float maxVal = -Float.MAX_VALUE;
    float minVal = Float.MAX_VALUE;
    
    for (float v : values) {
      if (v > maxVal) maxVal = v;
      if (v < minVal) minVal = v;
    }
    if (maxVal == minVal) maxVal += 1;

    pg.beginShape();
    for (int i = 0; i < values.size(); i++) {
      float x = map(i, 0, values.size(), 0, 800);
      float y = map(values.get(i), minVal, maxVal, 300, 0);
      pg.vertex(x, y);
    }
    pg.endShape();
  }
  
  pg.endDraw();
  pg.save(filename + ".png");
  println("Grafico salvato come: " + filename + ".png");
}

// Funzione per salvare i dati in Gauss
void saveGaussData(String name, String surname) {
  String filename = name + "_" + surname + "_gauss_data.txt";
  String data = "Valore Gauss: " + nf(minGaussMeasured, 0, 2) + " Gauss\n";
  
  saveStrings(filename, new String[] {data});
  println("Dati salvati come: " + filename);
}

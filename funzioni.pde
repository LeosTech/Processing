
//funzione per la connessione seriale con arduino
void connessioneSeriale() {
  if (myPort==null) {
    String comDaUsare= serial_list.getSelectedText();
    myPort = new Serial(this, comDaUsare, 9600);
    serial_button.setText("Disconnect");
  } else {
    serial_button.setText("Connect");
    myPort.stop();
    myPort=null;
  }
  
}

//funzione di starting
void starting()
{
  saveStrings("data/list_915007", Serial.list());
}

//funzione per scelta file mappatura
void sciegliFile() {
  selectInput("Scegli un file", "fileSelected");
}

//funzione per la scelta del file di mappa
void fileSelected(File selection) {
  if (selection == null) {
    println(noFile);
    selectedFile = noFile;
  } else {
    println("File selezionato: " + selection.getAbsolutePath());
    selectedFile = selection.getAbsolutePath();
    caricamentoDatiSensore(selectedFile, arrayVolt, arrayGauss);
  }
}


//button_selezionaFile da salvare
void selezionaFile(){
  selectOutput("Scegli un file per salvare", "fileSelezionato"); 
}

//button_salvaDato funziona di salvataggio
void salvaDato(){
if (!selectedFilePath.equals(noFile)) {
      String dataOrari = day()+ "/" + month() + "/" + year() +", " + hour() + ":" + minute() + ":" + second();
      if (myString != null) {
      String riga = dataOrari + ", " + display1.getText();
      scriviDati(selectedFilePath, riga);
      }
    } else {
      println("Nessun file selezionato.");
    }
}


void fileSelezionato(File selection) {
  if (selection == null) {
    println("Nessun file selezionato.");
    selectedFilePath = noFile;
  } else {
    println("File selezionato: " + selection.getAbsolutePath());
    selectedFilePath = selection.getAbsolutePath();
  }
  Font mioFont = new Font("Arial", Font.BOLD, 10);
  dataLocation_label.setFont(mioFont);
  dataLocation_label.setText("Location:"+selectedFilePath);
}

//scrittura dei dati
void scriviDati(String path, String dati) {
  try {
    PrintWriter writer = new PrintWriter(new FileWriter(path, true)); // true = append mode
    writer.print(dati);
    writer.close();
    println("Dati scritti: " + dati.trim());
  }
  catch (IOException e) {
    e.printStackTrace();
    println("Errore nella scrittura del file.");
  }
}




//stampa del valore in gauss
void stampaDisplay() {
  Font mioFont = new Font("Arial", Font.BOLD, 30);
  display1.setFont(mioFont);
  Vin=float(myString);
  if (!selectedFile.equals(noFile)) {
    GausOut = interpolazioneLineare(Vin, arrayVolt, arrayGauss);
    println("Per Vin = " + Vin + " il valore in Gauss è: " + GausOut);
  } else {
    GausOut = Vin;
  }

  display1.setText(String.format("%.2f", GausOut)+" G");
  aggiungiDato(GausOut);//è nel file in Grafico
}

float interpolazioneLineare(float vin, int[] xVals, int[] yVals) {
  for (int i = 0; i < xVals.length - 1; i++) {
    if (vin >= xVals[i] && vin <= xVals[i+1]) {
      float x0 = xVals[i];
      float x1 = xVals[i+1];
      float y0 = yVals[i];
      float y1 = yVals[i+1];
      return y0 + (vin - x0) * (y1 - y0) / (x1 - x0);
    }
  }
  println("Vin fuori dal range dei dati!");
  return Float.NaN;
}

//lettura file di mappatura
void caricamentoDatiSensore(String file, int [] dati1, int[] dati2) {
  // Carica le righe dal file
  String[]   righe = loadStrings(file);
  // Inizializza l'array 2D con il numero di righe
  for (int i = 0; i < righe.length; i++) {
    String[] valori = split(righe[i], ',');  // Divide la riga con la virgola
    dati1[i] = int(trim(valori[0]));
    dati2[i] = int(trim(valori[1]));
  }
}

//funzione on off
void onOffGrafico() {
  if (on_off_Grafico==0) {
    on_off_Grafico=1;
    sensorActivation_button.setText("OFF");
    sensorActivation_button.setLocalColorScheme(GCScheme.RED_SCHEME);
  } else {
    on_off_Grafico=0;
    sensorActivation_button.setText("ON");
    sensorActivation_button.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  }
}

void onOff() {
  if (myPort!=null) {
    if (sensorActivation_button.getText().equals("ON")) {
      myPort.write('a');
      println("Inviato: a");
      stato_on_off=1;
      sensorActivation_button.setText("OFF");
      sensorActivation_button.setLocalColorScheme(GCScheme.RED_SCHEME);
    } else {
      myPort.write('s');
      println("Inviato: s");
      stato_on_off=0;
      sensorActivation_button.setText("ON");
      sensorActivation_button.setLocalColorScheme(GCScheme.GREEN_SCHEME);
    }
  }
}

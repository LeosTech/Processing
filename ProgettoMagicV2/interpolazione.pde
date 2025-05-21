
void sciegliFile() {
  selectInput("Scegli un file", "fileSelected");
}


void fileSelected(File selection) {
  if (selection == null) {
    println(noFile);
    selectedFile = noFile;
  } else {
    println("File selezionato: " + selection.getAbsolutePath());
    selectedFile = selection.getAbsolutePath();
    String[] righe = loadStrings(selectedFile);
    N_valori = righe.length;
    arrayGauss= new int[N_valori];
    arrayIN= new int[N_valori];
    arrayVolt= new int[N_valori];
    caricamentoDatiSensore(selectedFile, arrayIN, arrayGauss, arrayVolt);
    
  }
}

void stampaDisplay() {
  Font mioFont = new Font("Arial", Font.BOLD, 25);
  valueGauss.setFont(mioFont);
  Vin=float(myString);
  if (!selectedFile.equals(noFile)) {
    GausOut = interpolazioneLineareGauss(Vin, arrayIN, arrayGauss);
    VoltOut = interpolazioneLineareVolt(Vin, arrayIN, arrayVolt)/1000;
    println("Per BIT = " + Vin + " il valore in Gauss è: " + GausOut);
    println("Per BIT = " + Vin + " il valore in VOLT è: " + VoltOut);
  } else {
    GausOut = Vin;
    VoltOut = Vin;
  }

  valueGauss.setText(String.format("%.2f", GausOut)+" G \n"+String.format("%.2f", VoltOut)+" V");
  aggiungiDato(GausOut);//è nel file in Grafico
}

float interpolazioneLineareGauss(float vin, int[] xVals, int[] yVals) {
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

float interpolazioneLineareVolt(float vin, int[] xVals, int[] yVals) {
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

void caricamentoDatiSensore(String file, int [] dati1, int[] dati2, int [] dati3) {
  // Carica le righe dal file
  String[]   righe = loadStrings(file);
  // Inizializza l'array 2D con il numero di righe
  for (int i = 0; i < righe.length; i++) {
    String[] valori = split(righe[i], ',');  // Divide la riga con la virgola
    dati1[i] = int(trim(valori[0]));
    dati2[i] = int(trim(valori[1]));
    dati3[i] = int(trim(valori[2]));
  }
}

//void stampaArray(int [] dati) {
//    //  Mostra i dati letti nella console
//  for (int i = 0; i < dati.length; i++) {
//    println("Riga " + i + ": " + str(dati[i]));
//  }
//}

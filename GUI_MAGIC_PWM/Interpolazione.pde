
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
    caricamentoDatiSensore(selectedFile, arrayVolt, arrayGauss);
  }
}

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

//void stampaArray(int [] dati) {
//    //  Mostra i dati letti nella console
//  for (int i = 0; i < dati.length; i++) {
//    println("Riga " + i + ": " + str(dati[i]));
//  }
//}

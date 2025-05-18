//button_selezionaFile
void selezionaFile(){
  selectOutput("Scegli un file per salvare", "fileSelezionato"); 
}

//button_salvaDato
void salvaDato(){
if (!selectedFilePath.equals(noFile)) {
      String dataOrari = day()+ "/" + month() + "/" + year() +", " + hour() + ":" + minute() + ":" + second();
      if (myString != null) {
      String riga = dataOrari + ", " + valueGauss.getText();
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
  dataLocation.setFont(mioFont);
  dataLocation.setText("save in:"+selectedFilePath);
}

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

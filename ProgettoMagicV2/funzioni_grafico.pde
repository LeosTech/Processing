void setupGrafico() {
  plot = new GPlot(this);
  plot.setPos(20, 190);
  plot.setDim(650, 210);
  plot.setTitleText("Real time Value");
  plot.getXAxis().setAxisLabelText("Time");
  plot.getYAxis().setAxisLabelText("Value");
  plot.setPoints(punti);
}
void aggiungiDato(float valore) {
  if (on_off_Grafico==1) {
    dati.add(valore);
    // Mantieni al massimo N punti
    if (dati.size() > maxPunti) {
      dati.remove(0);
    }

    // Ricostruisci GPointsArray
    punti = new GPointsArray();
    for (int i = 0; i < dati.size(); i++) {
      punti.add(i, dati.get(i));
    }
    plot.setPoints(punti);
  }
}

void salvaGrafico(){
selectOutput("Scegli un file per salvare", "fileSelezionatoGrafico"); 
}

void fileSelezionatoGrafico(File selection) {
  if (selection == null) {
    println("Nessun file selezionato.");
    selectedFileGrafico = noFile;
  } else {
    println("File selezionato: " + selection.getAbsolutePath());
    selectedFileGrafico = selection.getAbsolutePath();
    salvaArrayList(selectedFileGrafico);
  }
  selectedFileGrafico= noFile;
}


void salvaArrayList(String nomeFile) {
  String[] righe = new String[dati.size()];
  for (int i = 0; i < dati.size(); i++) {
    righe[i] = str(dati.get(i));
  }

  saveStrings(nomeFile, righe);
}

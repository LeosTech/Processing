//inizializzazione del grafico
void setupGrafico() {
  plot = new GPlot(this);
  plot.setPos(5, 270);
  plot.setDim(370, 100);
  plot.setTitleText("Real time value");
  plot.getXAxis().setAxisLabelText("Time");
  plot.getYAxis().setAxisLabelText("Value");
  plot.setPoints(punti);
}

//funzione che controlla l'accensione del grafico 
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

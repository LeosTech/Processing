void inizializzazione() {
  saveStrings("data/list_706015", Serial.list());
  // Inizializza struttura dati
  dati = new ArrayList<Float>();
  punti = new GPointsArray();
  setupGrafico();
}

void inizializzazione() {
  saveStrings("data/list_371820", Serial.list());
  // Inizializza struttura dati
  dati = new ArrayList<Float>();
  punti = new GPointsArray();
  setupGrafico();
}

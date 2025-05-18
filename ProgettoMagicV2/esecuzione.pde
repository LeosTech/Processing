void esecuzione() {
  if (myPort!=null) {
    while (myPort.available() > 0) {
      myString = myPort.readStringUntil(lf);
      if (myString != null) {
        print(myString);
        stampaDisplay();// e nel file Intermolazione
        livelloSoglia();
      }
    }
  }
  led(stato_led);
  plot.defaultDraw();//aggiorna grafico
  smagnete_PWM();
}

/* =========================================================
 * ====                   WARNING                        ===
 * =========================================================
 * The code in this tab has been generated from the GUI form
 * designer and care should be taken when editing this file.
 * Only add/edit code inside the event handlers i.e. only
 * use lines between the matching comment tags. e.g.

 void myBtnEvents(GButton button) { //_CODE_:button1:12356:
     // It is safe to enter your event code here  
 } //_CODE_:button1:12356:
 
 * Do not rename this tab!
 * =========================================================
 */

public void button_com_click1(GButton source, GEvent event) { //_CODE_:button_com:474049:
  println("button1 - GButton >> GEvent." + event + " @ " + millis());
  connessioneSeriale();
} //_CODE_:button_com:474049:

public void button_selezionaFile_click1(GButton source, GEvent event) { //_CODE_:button_selezionaFile:278221:
  println("button2 - GButton >> GEvent." + event + " @ " + millis());
selezionaFile();
} //_CODE_:button_selezionaFile:278221:

public void button__salvaDato_click1(GButton source, GEvent event) { //_CODE_:button_salvaDato:353478:
  println("button3 - GButton >> GEvent." + event + " @ " + millis());
salvaDato();
} //_CODE_:button_salvaDato:353478:

public void Lista_Com1_click1(GDropList source, GEvent event) { //_CODE_:Lista_Com:706015:
  println("dropList1 - GDropList >> GEvent." + event + " @ " + millis());
} //_CODE_:Lista_Com:706015:

public void button_on_off_click1(GButton source, GEvent event) { //_CODE_:button_on_off:914820:
  println("button4 - GButton >> GEvent." + event + " @ " + millis());
  onOff();
} //_CODE_:button_on_off:914820:

public void button_mapFile_click1(GButton source, GEvent event) { //_CODE_:button_mapFile:707413:
  println("button5 - GButton >> GEvent." + event + " @ " + millis());
  sciegliFile();
} //_CODE_:button_mapFile:707413:

public void slider_soglia_change1(GSlider source, GEvent event) { //_CODE_:slider_soglia:605718:
  println("slider1 - GSlider >> GEvent." + event + " @ " + millis());
  livelloSoglia();

} //_CODE_:slider_soglia:605718:

public void button_resetGrafico_click1(GButton source, GEvent event) { //_CODE_:button_resetGrafico:887583:
  println("button6 - GButton >> GEvent." + event + " @ " + millis());
  dati.clear();
} //_CODE_:button_resetGrafico:887583:

public void button_saveGrafico_click1(GButton source, GEvent event) { //_CODE_:button_saveGrafico:976862:
  println("button7 - GButton >> GEvent." + event + " @ " + millis());
  salvaGrafico();
} //_CODE_:button_saveGrafico:976862:

public void button_onoffGrafico_click1(GButton source, GEvent event) { //_CODE_:button_onoffGrafico:558797:
  println("button8 - GButton >> GEvent." + event + " @ " + millis());
  onOffGrafico();
} //_CODE_:button_onoffGrafico:558797:

public void slider_puntiGrafico_change1(GSlider source, GEvent event) { //_CODE_:slider_puntiGrafico:929665:
  println("slider2 - GSlider >> GEvent." + event + " @ " + millis());
  maxPunti=slider_puntiGrafico.getValueI();
} //_CODE_:slider_puntiGrafico:929665:



// Create all the GUI controls. 
// autogenerated do not edit
public void createGUI(){
  G4P.messagesEnabled(true);
  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
  G4P.setCursor(CROSS);
  GButton.useRoundCorners(false);
  G4P.setDisplayFont("Arial", G4P.PLAIN, 14);
  G4P.setInputFont("Arial", G4P.PLAIN, 14);
  G4P.setSliderFont("Arial", G4P.PLAIN, 11);
  surface.setTitle("MAGIC");
  label1 = new GLabel(this, 4, 7, 197, 59);
  label1.setTextAlign(GAlign.LEFT, GAlign.TOP);
  label1.setText("CONNESSIONE:");
  label1.setLocalColorScheme(GCScheme.PURPLE_SCHEME);
  label1.setOpaque(true);
  button_com = new GButton(this, 7, 35, 104, 26);
  button_com.setText("CONNETTI");
  button_com.addEventHandler(this, "button_com_click1");
  label_salvaDato = new GLabel(this, 204, 6, 270, 61);
  label_salvaDato.setTextAlign(GAlign.LEFT, GAlign.BOTTOM);
  label_salvaDato.setText("save in:");
  label_salvaDato.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  label_salvaDato.setOpaque(true);
  button_selezionaFile = new GButton(this, 212, 12, 130, 26);
  button_selezionaFile.setText("Salva con nome");
  button_selezionaFile.addEventHandler(this, "button_selezionaFile_click1");
  button_salvaDato = new GButton(this, 372, 11, 80, 26);
  button_salvaDato.setText("Salva");
  button_salvaDato.addEventHandler(this, "button__salvaDato_click1");
  Lista_Com = new GDropList(this, 115, 11, 81, 104, 3, 10);
  Lista_Com.setItems(loadStrings("list_706015"), 0);
  Lista_Com.addEventHandler(this, "Lista_Com1_click1");
  label3 = new GLabel(this, 3, 70, 225, 73);
  label3.setTextAlign(GAlign.LEFT, GAlign.TOP);
  label3.setText("ATTIVAZIONE SENSORE:");
  label3.setLocalColorScheme(GCScheme.ORANGE_SCHEME);
  label3.setOpaque(true);
  label4 = new GLabel(this, 4, 149, 306, 61);
  label4.setTextAlign(GAlign.LEFT, GAlign.TOP);
  label4.setText("LIVELLO DI SOGLIA:");
  label4.setLocalColorScheme(GCScheme.CYAN_SCHEME);
  label4.setOpaque(true);
  label5 = new GLabel(this, 4, 216, 471, 47);
  label5.setTextAlign(GAlign.LEFT, GAlign.TOP);
  label5.setText("GRAFICO:");
  label5.setLocalColorScheme(GCScheme.RED_SCHEME);
  label5.setOpaque(true);
  button_on_off = new GButton(this, 11, 95, 80, 30);
  button_on_off.setText("ON/OFF");
  button_on_off.addEventHandler(this, "button_on_off_click1");
  button_mapFile = new GButton(this, 117, 90, 97, 38);
  button_mapFile.setText("File di Mappatura");
  button_mapFile.addEventHandler(this, "button_mapFile_click1");
  slider_soglia = new GSlider(this, 136, 159, 153, 40, 10.0);
  slider_soglia.setShowValue(true);
  slider_soglia.setShowLimits(true);
  slider_soglia.setLimits(1.0, 0.0, 1000.0);
  slider_soglia.setNumberFormat(G4P.DECIMAL, 2);
  slider_soglia.setOpaque(false);
  slider_soglia.addEventHandler(this, "slider_soglia_change1");
  button_resetGrafico = new GButton(this, 80, 221, 80, 30);
  button_resetGrafico.setText("Reset");
  button_resetGrafico.addEventHandler(this, "button_resetGrafico_click1");
  button_saveGrafico = new GButton(this, 305, 222, 170, 30);
  button_saveGrafico.setTextAlign(GAlign.CENTER, GAlign.CENTER);
  button_saveGrafico.setText("Salvatagio dati Grafico");
  button_saveGrafico.addEventHandler(this, "button_saveGrafico_click1");
  button_onoffGrafico = new GButton(this, 170, 222, 123, 30);
  button_onoffGrafico.setText("on/off Grafico");
  button_onoffGrafico.addEventHandler(this, "button_onoffGrafico_click1");
  slider_puntiGrafico = new GSlider(this, 156, 471, 310, 50, 10.0);
  slider_puntiGrafico.setShowValue(true);
  slider_puntiGrafico.setShowLimits(true);
  slider_puntiGrafico.setLimits(10, 10, 1000);
  slider_puntiGrafico.setNbrTicks(10);
  slider_puntiGrafico.setShowTicks(true);
  slider_puntiGrafico.setNumberFormat(G4P.INTEGER, 0);
  slider_puntiGrafico.setOpaque(true);
  slider_puntiGrafico.addEventHandler(this, "slider_puntiGrafico_change1");
  label7 = new GLabel(this, 1, 464, 474, 62);
  label7.setText("MAX PUNTI GRAFICO:");
  label7.setOpaque(false);
  display1 = new GLabel(this, 246, 77, 223, 60);
  display1.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  display1.setText("DISPLAY");
  display1.setOpaque(true);
}

// Variable declarations 
// autogenerated do not edit
GLabel label1; 
GButton button_com; 
GLabel label_salvaDato; 
GButton button_selezionaFile; 
GButton button_salvaDato; 
GDropList Lista_Com; 
GLabel label3; 
GLabel label4; 
GLabel label5; 
GButton button_on_off; 
GButton button_mapFile; 
GSlider slider_soglia; 
GButton button_resetGrafico; 
GButton button_saveGrafico; 
GButton button_onoffGrafico; 
GSlider slider_puntiGrafico; 
GLabel label7; 
GLabel display1; 

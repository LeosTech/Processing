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

synchronized public void win_draw1(PApplet appc, GWinData data) { //_CODE_:window1:932808:
  appc.background(230);
} //_CODE_:window1:932808:

public void button_PWM_start_click1(GButton source, GEvent event) { //_CODE_:button_PWM_start:909368:
  println("button_PWM_start - GButton >> GEvent." + event + " @ " + millis());
  start_PWM();
} //_CODE_:button_PWM_start:909368:

public void button_PWM_stop_click1(GButton source, GEvent event) { //_CODE_:button_PWM_stop:629137:
  println("button_PWM_stop - GButton >> GEvent." + event + " @ " + millis());
  stop_PWM();
} //_CODE_:button_PWM_stop:629137:

public void slider_PWM_change1(GSlider source, GEvent event) { //_CODE_:slider_PWM:510040:
  println("slider1 - GSlider >> GEvent." + event + " @ " + millis());
} //_CODE_:slider_PWM:510040:

public void slider_tempoPWM_change1(GSlider source, GEvent event) { //_CODE_:slider_tempoPWM:549662:
  println("slider_tempo - GSlider >> GEvent." + event + " @ " + millis());
} //_CODE_:slider_tempoPWM:549662:

public void slider_Smagnete_change1(GSlider source, GEvent event) { //_CODE_:slider_Smagnete:931395:
  println("slider_Smagnete - GSlider >> GEvent." + event + " @ " + millis());
} //_CODE_:slider_Smagnete:931395:

public void button_resetPWM_click1(GButton source, GEvent event) { //_CODE_:button_resetPWM:432140:
  println("button_resetPWM - GButton >> GEvent." + event + " @ " + millis());
  reset_PWM();
} //_CODE_:button_resetPWM:432140:

public void button_Smagnete_click1(GButton source, GEvent event) { //_CODE_:button__Smagnete:248730:
  println("button__Smagnete - GButton >> GEvent." + event + " @ " + millis());
  if (myPort!=null) { stato_s_magnete=1;}
} //_CODE_:button__Smagnete:248730:

public void timer1_Action1(GTimer source) { //_CODE_:timer1:420940:
  println("timer1 - GTimer >> an event occured @ " + millis());
  invioPWM();
} //_CODE_:timer1:420940:



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
  window1 = GWindow.getWindow(this, "Window title", 0, 0, 300, 200, P2D);
  window1.noLoop();
  window1.setActionOnClose(G4P.KEEP_OPEN);
  window1.addDrawHandler(this, "win_draw1");
  button_PWM_start = new GButton(window1, 212, 50, 80, 28);
  button_PWM_start.setText("START");
  button_PWM_start.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  button_PWM_start.addEventHandler(this, "button_PWM_start_click1");
  button_PWM_stop = new GButton(window1, 213, 90, 80, 30);
  button_PWM_stop.setText("STOP");
  button_PWM_stop.setLocalColorScheme(GCScheme.RED_SCHEME);
  button_PWM_stop.addEventHandler(this, "button_PWM_stop_click1");
  slider_PWM = new GSlider(window1, 3, 22, 204, 44, 10.0);
  slider_PWM.setShowValue(true);
  slider_PWM.setLimits(0, -255, 255);
  slider_PWM.setNbrTicks(5);
  slider_PWM.setShowTicks(true);
  slider_PWM.setNumberFormat(G4P.INTEGER, 0);
  slider_PWM.setLocalColorScheme(GCScheme.GOLD_SCHEME);
  slider_PWM.setOpaque(false);
  slider_PWM.addEventHandler(this, "slider_PWM_change1");
  slider_tempoPWM = new GSlider(window1, 3, 85, 205, 46, 10.0);
  slider_tempoPWM.setShowValue(true);
  slider_tempoPWM.setShowLimits(true);
  slider_tempoPWM.setLimits(100, 100, 2000);
  slider_tempoPWM.setNbrTicks(10);
  slider_tempoPWM.setShowTicks(true);
  slider_tempoPWM.setNumberFormat(G4P.INTEGER, 0);
  slider_tempoPWM.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  slider_tempoPWM.setOpaque(true);
  slider_tempoPWM.addEventHandler(this, "slider_tempoPWM_change1");
  label_PWM = new GLabel(window1, 214, 8, 80, 30);
  label_PWM.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label_PWM.setText("PWM");
  label_PWM.setLocalColorScheme(GCScheme.GOLD_SCHEME);
  label_PWM.setOpaque(true);
  slider_Smagnete = new GSlider(window1, 3, 151, 206, 48, 10.0);
  slider_Smagnete.setShowValue(true);
  slider_Smagnete.setShowLimits(true);
  slider_Smagnete.setLimits(1, 1, 50);
  slider_Smagnete.setNbrTicks(10);
  slider_Smagnete.setShowTicks(true);
  slider_Smagnete.setNumberFormat(G4P.INTEGER, 0);
  slider_Smagnete.setOpaque(true);
  slider_Smagnete.addEventHandler(this, "slider_Smagnete_change1");
  button_resetPWM = new GButton(window1, 214, 129, 80, 30);
  button_resetPWM.setText("RESET");
  button_resetPWM.setLocalColorScheme(GCScheme.ORANGE_SCHEME);
  button_resetPWM.addEventHandler(this, "button_resetPWM_click1");
  button__Smagnete = new GButton(window1, 215, 163, 80, 30);
  button__Smagnete.setText("S.magnete");
  button__Smagnete.addEventHandler(this, "button_Smagnete_click1");
  label2 = new GLabel(window1, 2, 1, 207, 20);
  label2.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label2.setText("SET VALORE PWM");
  label2.setLocalColorScheme(GCScheme.GOLD_SCHEME);
  label2.setOpaque(false);
  label6 = new GLabel(window1, 3, 65, 206, 20);
  label6.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label6.setText("TEMPO UP/DOWN");
  label6.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  label6.setOpaque(false);
  label8 = new GLabel(window1, 3, 131, 207, 20);
  label8.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label8.setText("DELTA SMAGNETIZZAZIONE");
  label8.setOpaque(false);
  timer1 = new GTimer(this, this, "timer1_Action1", 1000);
  window1.loop();
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
GWindow window1;
GButton button_PWM_start; 
GButton button_PWM_stop; 
GSlider slider_PWM; 
GSlider slider_tempoPWM; 
GLabel label_PWM; 
GSlider slider_Smagnete; 
GButton button_resetPWM; 
GButton button__Smagnete; 
GLabel label2; 
GLabel label6; 
GLabel label8; 
GTimer timer1; 

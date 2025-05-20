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

public void serial_list_click1(GDropList source, GEvent event) { //_CODE_:serial_list:371820:
  println("serial_list - GDropList >> GEvent." + event + " @ " + millis());
} //_CODE_:serial_list:371820:

public void serial_button_click1(GButton source, GEvent event) { //_CODE_:serial_button:402234:
  println("serial_button - GButton >> GEvent." + event + " @ " + millis());
  connessioneSeriale();
} //_CODE_:serial_button:402234:

public void fileSelection_click1(GButton source, GEvent event) { //_CODE_:fileSelection:865783:
  println("fileSelection - GButton >> GEvent." + event + " @ " + millis());
  selezionaFile();
} //_CODE_:fileSelection:865783:

public void saveButton_click1(GButton source, GEvent event) { //_CODE_:saveButton:580249:
  println("saveButton - GButton >> GEvent." + event + " @ " + millis());
  salvaDato();
} //_CODE_:saveButton:580249:

public void on_off_button_click1(GButton source, GEvent event) { //_CODE_:on_off_button:556459:
  println("on_off_button - GButton >> GEvent." + event + " @ " + millis());
  onOff();
} //_CODE_:on_off_button:556459:

public void fileMap_button_click1(GButton source, GEvent event) { //_CODE_:fileMap_button:561195:
  println("fileMap_button - GButton >> GEvent." + event + " @ " + millis());
  sciegliFile();
} //_CODE_:fileMap_button:561195:

public void controllValue_change1(GTextField source, GEvent event) { //_CODE_:controllValue:215607:
  println("textfield2 - GTextField >> GEvent." + event + " @ " + millis());
  livelloSoglia();
} //_CODE_:controllValue:215607:

public void graphicOnOff_click1(GButton source, GEvent event) { //_CODE_:graphicOnOff:407735:
  println("button1 - GButton >> GEvent." + event + " @ " + millis());
  onOffGrafico();
} //_CODE_:graphicOnOff:407735:

public void graphicSaveData_click1(GButton source, GEvent event) { //_CODE_:graphicSaveData:276503:
  println("button2 - GButton >> GEvent." + event + " @ " + millis());
  salvaGrafico();
} //_CODE_:graphicSaveData:276503:

public void graphicReset_click1(GButton source, GEvent event) { //_CODE_:graphicReset:727966:
  println("button3 - GButton >> GEvent." + event + " @ " + millis());
  dati.clear();
} //_CODE_:graphicReset:727966:

public void graphicPoint_change1(GSlider source, GEvent event) { //_CODE_:graphicPoint:695926:
  println("graphicPoint - GSlider >> GEvent." + event + " @ " + millis());
  maxPunti=graphicPoint.getValueI();
} //_CODE_:graphicPoint:695926:

synchronized public void win_draw1(PApplet appc, GWinData data) { //_CODE_:window1:318226:
  appc.background(230);
} //_CODE_:window1:318226:

public void PWM_value_slider_change1(GSlider source, GEvent event) { //_CODE_:PWM_value_slider:561243:
  println("slider1 - GSlider >> GEvent." + event + " @ " + millis());
} //_CODE_:PWM_value_slider:561243:

public void PWM_time_slider_change1(GSlider source, GEvent event) { //_CODE_:PWM_time_slider:324828:
  println("slider2 - GSlider >> GEvent." + event + " @ " + millis());
} //_CODE_:PWM_time_slider:324828:

public void PWM_delta_slider_change1(GSlider source, GEvent event) { //_CODE_:PWM_delta_slider:256706:
  println("slider3 - GSlider >> GEvent." + event + " @ " + millis());
} //_CODE_:PWM_delta_slider:256706:

public void PWM_start_click1(GButton source, GEvent event) { //_CODE_:PWM_start:882456:
  println("PWM_start - GButton >> GEvent." + event + " @ " + millis());
  start_PWM();
} //_CODE_:PWM_start:882456:

public void PWM_stop_click1(GButton source, GEvent event) { //_CODE_:PWM_stop:765107:
  println("PWM_stop - GButton >> GEvent." + event + " @ " + millis());
  stop_PWM();
} //_CODE_:PWM_stop:765107:

public void PWM_reset_click1(GButton source, GEvent event) { //_CODE_:PWM_reset:667534:
  println("PWM_reset - GButton >> GEvent." + event + " @ " + millis());
  reset_PWM();
} //_CODE_:PWM_reset:667534:

public void smagnete_button_click1(GButton source, GEvent event) { //_CODE_:smagnete_button:825305:
  println("smagnete_button - GButton >> GEvent." + event + " @ " + millis());
  if (myPort!=null) { stato_s_magnete=1;};
} //_CODE_:smagnete_button:825305:

public void timer1_Action1(GTimer source) { //_CODE_:timer1:844011:
  println("timer1 - GTimer >> an event occured @ " + millis());
  invioPWM();
} //_CODE_:timer1:844011:



// Create all the GUI controls. 
// autogenerated do not edit
public void createGUI(){
  G4P.messagesEnabled(false);
  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
  G4P.setMouseOverEnabled(false);
  surface.setTitle("Magic GUI");
  serial_label = new GLabel(this, 20, 20, 140, 80);
  serial_label.setTextAlign(GAlign.LEFT, GAlign.TOP);
  serial_label.setText("Serial port");
  serial_label.setLocalColorScheme(GCScheme.PURPLE_SCHEME);
  serial_label.setOpaque(true);
  serial_list = new GDropList(this, 110, 20, 50, 80, 3, 10);
  serial_list.setItems(loadStrings("list_371820"), 0);
  serial_list.addEventHandler(this, "serial_list_click1");
  serial_button = new GButton(this, 24, 53, 70, 30);
  serial_button.setText("CONNECT");
  serial_button.addEventHandler(this, "serial_button_click1");
  valueGauss = new GLabel(this, 180, 20, 140, 80);
  valueGauss.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  valueGauss.setText("Gauss");
  valueGauss.setOpaque(true);
  dataLocation = new GLabel(this, 340, 20, 240, 30);
  dataLocation.setText("Location --> ");
  dataLocation.setLocalColorScheme(GCScheme.ORANGE_SCHEME);
  dataLocation.setOpaque(true);
  fileSelection = new GButton(this, 370, 70, 80, 30);
  fileSelection.setText("Sel file");
  fileSelection.addEventHandler(this, "fileSelection_click1");
  saveButton = new GButton(this, 460, 70, 80, 30);
  saveButton.setText("Save");
  saveButton.addEventHandler(this, "saveButton_click1");
  sensor_controller = new GLabel(this, 20, 120, 300, 50);
  sensor_controller.setText("Sensor Controller");
  sensor_controller.setOpaque(true);
  on_off_button = new GButton(this, 140, 130, 80, 30);
  on_off_button.setText("On - Off");
  on_off_button.addEventHandler(this, "on_off_button_click1");
  fileMap_button = new GButton(this, 230, 130, 80, 30);
  fileMap_button.setText("File Map");
  fileMap_button.addEventHandler(this, "fileMap_button_click1");
  soglia_label = new GLabel(this, 340, 120, 90, 50);
  soglia_label.setText("Controll Value:");
  soglia_label.setLocalColorScheme(GCScheme.CYAN_SCHEME);
  soglia_label.setOpaque(true);
  controllValue = new GTextField(this, 429, 121, 90, 50, G4P.SCROLLBARS_NONE);
  controllValue.setLocalColorScheme(GCScheme.ORANGE_SCHEME);
  controllValue.setOpaque(true);
  controllValue.addEventHandler(this, "controllValue_change1");
  graphic_label = new GLabel(this, 650, 20, 220, 120);
  graphic_label.setTextAlign(GAlign.CENTER, GAlign.TOP);
  graphic_label.setText("Graphic control");
  graphic_label.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  graphic_label.setOpaque(true);
  graphicOnOff = new GButton(this, 670, 55, 80, 30);
  graphicOnOff.setText("On - Off");
  graphicOnOff.addEventHandler(this, "graphicOnOff_click1");
  graphicSaveData = new GButton(this, 770, 55, 80, 30);
  graphicSaveData.setText("Save Data");
  graphicSaveData.addEventHandler(this, "graphicSaveData_click1");
  graphicReset = new GButton(this, 720, 100, 80, 30);
  graphicReset.setText("Reset");
  graphicReset.addEventHandler(this, "graphicReset_click1");
  graphicPoint_label = new GLabel(this, 800, 160, 100, 20);
  graphicPoint_label.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  graphicPoint_label.setText("Graphic Point");
  graphicPoint_label.setLocalColorScheme(GCScheme.ORANGE_SCHEME);
  graphicPoint_label.setOpaque(true);
  graphicPoint = new GSlider(this, 900, 190, 310, 100, 10.0);
  graphicPoint.setShowValue(true);
  graphicPoint.setShowLimits(true);
  graphicPoint.setRotation(PI/2, GControlMode.CORNER);
  graphicPoint.setLimits(10.0, 0.0, 1000.0);
  graphicPoint.setShowTicks(true);
  graphicPoint.setNumberFormat(G4P.DECIMAL, 2);
  graphicPoint.setOpaque(true);
  graphicPoint.addEventHandler(this, "graphicPoint_change1");
  window1 = GWindow.getWindow(this, "PWM Controller", 0, 0, 420, 260, JAVA2D);
  window1.noLoop();
  window1.setActionOnClose(G4P.KEEP_OPEN);
  window1.addDrawHandler(this, "win_draw1");
  PWM_value_slider = new GSlider(window1, 20, 20, 150, 60, 10.0);
  PWM_value_slider.setShowValue(true);
  PWM_value_slider.setShowLimits(true);
  PWM_value_slider.setLimits(0.0, -255.0, 255.0);
  PWM_value_slider.setShowTicks(true);
  PWM_value_slider.setNumberFormat(G4P.DECIMAL, 2);
  PWM_value_slider.setOpaque(true);
  PWM_value_slider.addEventHandler(this, "PWM_value_slider_change1");
  PWM_time_slider = new GSlider(window1, 20, 100, 150, 60, 10.0);
  PWM_time_slider.setShowValue(true);
  PWM_time_slider.setShowLimits(true);
  PWM_time_slider.setLimits(100.0, 100.0, 2000.0);
  PWM_time_slider.setShowTicks(true);
  PWM_time_slider.setNumberFormat(G4P.DECIMAL, 2);
  PWM_time_slider.setOpaque(true);
  PWM_time_slider.addEventHandler(this, "PWM_time_slider_change1");
  PWM_delta_slider = new GSlider(window1, 20, 180, 150, 60, 10.0);
  PWM_delta_slider.setShowValue(true);
  PWM_delta_slider.setShowLimits(true);
  PWM_delta_slider.setLimits(1.0, 1.0, 50.0);
  PWM_delta_slider.setShowTicks(true);
  PWM_delta_slider.setNumberFormat(G4P.DECIMAL, 2);
  PWM_delta_slider.setOpaque(true);
  PWM_delta_slider.addEventHandler(this, "PWM_delta_slider_change1");
  PWM_controll = new GLabel(window1, 180, 20, 100, 60);
  PWM_controll.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  PWM_controll.setText("PWM controll");
  PWM_controll.setLocalColorScheme(GCScheme.GOLD_SCHEME);
  PWM_controll.setOpaque(true);
  PWM_time = new GLabel(window1, 180, 100, 100, 60);
  PWM_time.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  PWM_time.setText("Time Up/Down");
  PWM_time.setLocalColorScheme(GCScheme.GOLD_SCHEME);
  PWM_time.setOpaque(true);
  PWM_Delta = new GLabel(window1, 180, 180, 100, 60);
  PWM_Delta.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  PWM_Delta.setText("Delta Demagnetization");
  PWM_Delta.setLocalColorScheme(GCScheme.GOLD_SCHEME);
  PWM_Delta.setOpaque(true);
  PWM_value = new GLabel(window1, 300, 20, 100, 40);
  PWM_value.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  PWM_value.setText("PWM Value");
  PWM_value.setLocalColorScheme(GCScheme.PURPLE_SCHEME);
  PWM_value.setOpaque(true);
  PWM_start = new GButton(window1, 310, 80, 80, 30);
  PWM_start.setText("START");
  PWM_start.addEventHandler(this, "PWM_start_click1");
  PWM_stop = new GButton(window1, 310, 125, 80, 30);
  PWM_stop.setText("STOP");
  PWM_stop.addEventHandler(this, "PWM_stop_click1");
  PWM_reset = new GButton(window1, 310, 170, 80, 30);
  PWM_reset.setText("RESET");
  PWM_reset.addEventHandler(this, "PWM_reset_click1");
  smagnete_button = new GButton(window1, 310, 215, 80, 30);
  smagnete_button.setText("DEMAGNETIZATION");
  smagnete_button.addEventHandler(this, "smagnete_button_click1");
  timer1 = new GTimer(this, this, "timer1_Action1", 1000);
  window1.loop();
}

// Variable declarations 
// autogenerated do not edit
GLabel serial_label; 
GDropList serial_list; 
GButton serial_button; 
GLabel valueGauss; 
GLabel dataLocation; 
GButton fileSelection; 
GButton saveButton; 
GLabel sensor_controller; 
GButton on_off_button; 
GButton fileMap_button; 
GLabel soglia_label; 
GTextField controllValue; 
GLabel graphic_label; 
GButton graphicOnOff; 
GButton graphicSaveData; 
GButton graphicReset; 
GLabel graphicPoint_label; 
GSlider graphicPoint; 
GWindow window1;
GSlider PWM_value_slider; 
GSlider PWM_time_slider; 
GSlider PWM_delta_slider; 
GLabel PWM_controll; 
GLabel PWM_time; 
GLabel PWM_Delta; 
GLabel PWM_value; 
GButton PWM_start; 
GButton PWM_stop; 
GButton PWM_reset; 
GButton smagnete_button; 
GTimer timer1; 

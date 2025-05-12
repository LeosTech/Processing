void connessioneSeriale() {
  if (myPort==null) {
    String comDaUsare= Lista_Com.getSelectedText();
    myPort = new Serial(this, comDaUsare, 9600);
    button_com.setText("Disconnect");
  } else {
    button_com.setText("Connect");
    myPort.stop();
    myPort=null;
  }
}

void led(int stato) {
  switch(stato) {
  case 0:
    fill(250, 0, 0);  // Does not execute
    break;
  case 1:
    fill(0, 250, 0);  // Prints "One"
    break;
  case 2:
    fill(150);  // Prints "One"
    break;
  }
  circle(400, 178, 70);
}

void onOff() {
  if (myPort!=null) {
    if (button_on_off.getText().equals("ON")) {
      myPort.write('a');
      println("Inviato: a");
      stato_on_off=1;
      button_on_off.setText("OFF");
      button_on_off.setLocalColorScheme(GCScheme.RED_SCHEME);
    } else {
      myPort.write('s');
      println("Inviato: s");
      stato_on_off=0;
      button_on_off.setText("ON");
      button_on_off.setLocalColorScheme(GCScheme.GREEN_SCHEME);
    }
  }
}

void livelloSoglia() {
  float valore =  slider_soglia.getValueF();
  if (valore> GausOut) {
    if (stato_led!=1) {
      stato_led=1;
      if (myPort!=null) {
        myPort.write('v');
        println("Inviato: v");
      }
    }
  } else {
    if (stato_led!=0) {
      stato_led=0;
      if (myPort!=null) {
        myPort.write('r');
        println("Inviato: r");
      }
    }
  }
}



void onOffGrafico() {
  if (on_off_Grafico==0) {
    on_off_Grafico=1;
    button_onoffGrafico.setText("OFF");
    button_onoffGrafico.setLocalColorScheme(GCScheme.RED_SCHEME);
  } else {
    on_off_Grafico=0;
    button_onoffGrafico.setText("ON");
    button_onoffGrafico.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  }
}

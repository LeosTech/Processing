void connessioneSeriale() {
  if (myPort==null) {
    String comDaUsare= serial_list.getSelectedText();
    myPort = new Serial(this, comDaUsare, 9600);
    serial_button.setText("DISCONNETTI");
  } else {
    serial_button.setText("CONNETTI");
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
  circle(580, 145, 60);
}

void onOff() {
  if (myPort!=null) {
    if (on_off_button.getText().equals("ON")) {
      myPort.write('a');
      println("Inviato: a");
      stato_on_off=1;
      on_off_button.setText("OFF");
      on_off_button.setLocalColorScheme(GCScheme.RED_SCHEME);
    } else {
      myPort.write('s');
      println("Inviato: s");
      stato_on_off=0;
      on_off_button.setText("ON");
      on_off_button.setLocalColorScheme(GCScheme.GREEN_SCHEME);
    }
  }
}

void livelloSoglia() {
  float valore =  controllValue.getValueF();
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
    graphicOnOff.setText("OFF");
    graphicOnOff.setLocalColorScheme(GCScheme.RED_SCHEME);
  } else {
    on_off_Grafico=0;
    graphicOnOff.setText("ON");
    graphicOnOff.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  }
}

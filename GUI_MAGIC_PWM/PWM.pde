void  invioPWM() {
  if (myPort!=null) {
    myPort.write(valoreDiInvio_PWM);
    println("valoreDiInvio_PWM: "+valoreDiInvio_PWM);
    if (valoreDiInvio_PWM=='u') {
      valorePWM++;
    }
    if (valoreDiInvio_PWM=='d') {
      valorePWM--;
    }
    label_PWM.setText(String.format("%d", valorePWM));
  }
}

void start_PWM() {
  set_invio_PWM=slider_PWM.getValueI();
  tenpo_invio_PWM=slider_tempoPWM.getValueI();
  if (myPort!=null) {
    timer1.setDelay(tenpo_invio_PWM);
    println("Tempo_invio: "+tenpo_invio_PWM);
    if (valorePWM < set_invio_PWM ) {
      int delta_valorePWM=set_invio_PWM-valorePWM;
      valoreDiInvio_PWM='u';
      println("delta_PWM: "+delta_valorePWM);
      timer1.start(delta_valorePWM);
    }
    if (valorePWM > set_invio_PWM ) {
      int delta_valorePWM=valorePWM-set_invio_PWM;
      valoreDiInvio_PWM='d';
      println("delta_PWM: "+delta_valorePWM);
      timer1.start(delta_valorePWM);
    }
  }
}

void stop_PWM() {
  timer1.stop();
  stato_s_magnete=0;
}

void reset_PWM() {
  if (myPort!=null) {
    timer1.stop();
    valorePWM=0;
    valoreDiInvio_PWM='x';
    myPort.write(valoreDiInvio_PWM);
    label_PWM.setText(String.format("%d", valorePWM));
    stato_s_magnete=0;
  }
}
void smagnete_PWM() {

  if ( stato_s_magnete==1) {

    if (!timer1.isRunning()) {

      int decremento_Smagnete=slider_Smagnete.getValueI();
      println("decremento_PWM: "+decremento_Smagnete);
      if (valorePWM>0) {
        nuovo_slider_PWM= -(valorePWM-decremento_Smagnete);
      }
      if (valorePWM<0) {
        nuovo_slider_PWM= -(valorePWM+decremento_Smagnete);
      }
      if (abs(nuovo_slider_PWM)<decremento_Smagnete) {
        nuovo_slider_PWM=0;
      }
      slider_PWM.setValue(nuovo_slider_PWM);
      start_PWM();
      if (valorePWM==0) {
        stato_s_magnete=0;
      }
    }
  }
}

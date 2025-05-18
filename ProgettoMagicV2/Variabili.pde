//librerire e variabili codice
import g4p_controls.*;
import peasy.*;
import processing.serial.*;
import java.awt.Font;
import java.io.FileWriter;
import grafica.*;


GPlot plot;
GPointsArray punti;
ArrayList<Float> dati;
int maxPunti = 10;
int valore= 11 ;
int lf = '\n';    // Linefeed in ASCII
String myString = null;
Serial myPort;  // The serial port
int stato_on_off=2;
int stato_led=2;
String noFile = "Nessun file selezionato.";
String selectedFilePath = noFile;
String selectedFileGrafico = noFile;
String selectedFile = noFile;
float[] volt;
float[] gauss;
float Vin = 4.75;
float GausOut;
final int N_valori = 5;

// Array di righe lette dal file
int[] arrayVolt= new int[N_valori];           // Array 2D per contenere i numeri
int[] arrayGauss= new int[N_valori];
int on_off_Grafico=0;
int valorePWM=0;
int tenpo_invio_PWM=100;
int set_invio_PWM=0;
int s_magnete=0;
int stato_s_magnete=0;
char valoreDiInvio_PWM='x';
int nuovo_slider_PWM=0;

package progettotlc;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.Color;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Finestra extends JFrame implements ActionListener
{
    // Elementi grafici
    private JPanel pannello;
    private JButton bCarica;
    private JButton bSalva;
    private JButton bReset;
    private JButton bEsponenziale; // Nuovo pulsante per funzione esponenziale
    private ChartPanel panelGrafico;
    
    // Elementi per la tabella e il modello
    private JTable tabellaDati;
    private TabellaDatiModel modelloTabella;
    private JScrollPane scrollPane;
    
    // Nuovi campi di input
    private JTextField tfPunti;
    private JTextField tfBit;
    private JTextField tfMinGauss;
    private JTextField tfMaxGauss;
    private JTextField tfMinVolt;
    private JTextField tfMaxVolt;
    private JLabel lblPunti;
    private JLabel lblBit;
    private JLabel lblMinGauss;
    private JLabel lblMaxGauss;
    private JLabel lblMinVolt;
    private JLabel lblMaxVolt;

    Finestra()
    {
        initFinestra();
        setVisible(true);
    }
    
    // Modifichiamo il metodo initFinestra per includere i nuovi campi
    private void initFinestra()
    {
        // Impostazioni della finestra
        setName("Progetto");
        setSize(1300, 800); // Aumentata la larghezza per la colonna binario
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Setto il pannello come contenuto del JFrame
        pannello = new JPanel(null);
        setContentPane(pannello);

        // Aggiunta componenti al pannello
        pannello.add(bCarica = new JButton("Carica"));
        pannello.add(bSalva = new JButton("Salva"));
        pannello.add(bReset = new JButton("Reset (Lineare)"));
        pannello.add(bEsponenziale = new JButton("Esponenziale")); // Nuovo pulsante

        // Inizializzazione dei nuovi campi di input e relative etichette
        pannello.add(lblPunti = new JLabel("Punti"));
        pannello.add(tfPunti = new JTextField("40"));
        
        pannello.add(lblBit = new JLabel("Bit"));
        pannello.add(tfBit = new JTextField("12"));
        
        pannello.add(lblMinGauss = new JLabel("Min Gauss"));
        pannello.add(tfMinGauss = new JTextField("-1000"));
        
        pannello.add(lblMaxGauss = new JLabel("Max Gauss"));
        pannello.add(tfMaxGauss = new JTextField("1000"));
        
        pannello.add(lblMinVolt = new JLabel("Min MilliVolt"));
        pannello.add(tfMinVolt = new JTextField("0"));
        
        pannello.add(lblMaxVolt = new JLabel("Max MilliVolt"));
        pannello.add(tfMaxVolt = new JTextField("5000"));

        // Inizializzo modello e tabella
        modelloTabella = new TabellaDatiModel();
        tabellaDati = new JTable(modelloTabella);
        
        // Imposta la larghezza delle colonne
        tabellaDati.getColumnModel().getColumn(0).setPreferredWidth(120); // Binario (più largo)
        tabellaDati.getColumnModel().getColumn(1).setPreferredWidth(80);  // Gauss
        tabellaDati.getColumnModel().getColumn(2).setPreferredWidth(120); // Volt (più preciso)
        
        // Aggiungi listener per aggiornare il numero di bit quando cambia
        tfBit.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
        });
        
        // Aggiungi listener per i campi Gauss
        tfMinGauss.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
        });
        
        tfMaxGauss.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
        });
        
        // Aggiungi listener per i campi Volt
        tfMinVolt.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
        });
        
        tfMaxVolt.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateParametri(); }
        });
        
        // Aggiungi listener per aggiornare il grafico quando i dati cambiano
        modelloTabella.addTableModelListener(e -> aggiornaGrafico());
        
        scrollPane = new JScrollPane(tabellaDati);
        pannello.add(scrollPane);
        
        // Inizializzo il grafico vuoto
        XYSeriesCollection dataset = new XYSeriesCollection();
        JFreeChart grafico = ChartFactory.createScatterPlot(
            "Gauss - Volt", "Gauss", "Volt", dataset,
            PlotOrientation.VERTICAL, true, true, false);

        // Personalizzazione del grafico
        XYPlot plot = (XYPlot) grafico.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer(renderer);

        panelGrafico = new ChartPanel(grafico);
        panelGrafico.setMouseWheelEnabled(true);
        pannello.add(panelGrafico);

        // Aggiungo ActionListener ai pulsanti
        bCarica.addActionListener(this);
        bReset.addActionListener(this);
        bSalva.addActionListener(this);
        bEsponenziale.addActionListener(this); // Aggiungi listener per il nuovo pulsante

        // Aggiungo DocumentListener ai campi di testo per aggiornare il valore quando cambia
        tfPunti.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updatePuntiLabel(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updatePuntiLabel(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updatePuntiLabel(); }
            
            private void updatePuntiLabel() {
                // Aggiorna l'etichetta con il numero corrente di punti
                lblPunti.setText("Punti (" + modelloTabella.getRowCount() + ")");
            }
        });

        // Posizionamento (modificato per includere il nuovo pulsante)
        bCarica.setBounds(30, 30, 100, 30);
        bSalva.setBounds(160, 30, 100, 30);
        bReset.setBounds(290, 30, 120, 30); // Leggermente più largo per il testo
        bEsponenziale.setBounds(430, 30, 120, 30); // Nuovo pulsante
        
        // Posizionamento campi di testo e relative etichette
        int startY = 80;
        int spacing = 50;
        
        lblPunti.setBounds(30, startY, 100, 20);
        tfPunti.setBounds(30, startY + 20, 100, 30);
        
        lblBit.setBounds(160, startY, 100, 20);
        tfBit.setBounds(160, startY + 20, 100, 30);
        
        lblMinGauss.setBounds(30, startY + spacing, 100, 20);
        tfMinGauss.setBounds(30, startY + spacing + 20, 100, 30);
        
        lblMaxGauss.setBounds(160, startY + spacing, 100, 20);
        tfMaxGauss.setBounds(160, startY + spacing + 20, 100, 30);
        
        lblMinVolt.setBounds(30, startY + 2*spacing, 100, 20);
        tfMinVolt.setBounds(30, startY + 2*spacing + 20, 100, 30);
        
        lblMaxVolt.setBounds(160, startY + 2*spacing, 100, 20);
        tfMaxVolt.setBounds(160, startY + 2*spacing + 20, 100, 30);
        
        // Sposta la tabella più in basso per fare spazio ai nuovi campi e aumenta la larghezza
        scrollPane.setBounds(30, startY + 3*spacing + 20, 500, 400); // Aumentata larghezza per binario
        panelGrafico.setBounds(560, 80, 650, 600); // Spostato a destra
        
        // Aggiorna l'etichetta con il numero di punti iniziale
        lblPunti.setText("Punti (" + modelloTabella.getRowCount() + ")");
    }
    
    // Metodo per aggiornare i parametri del modello quando cambiano i campi di testo
    private void updateParametri() {
        try {
            int numeroBit = Integer.parseInt(tfBit.getText().trim());
            double minGauss = Double.parseDouble(tfMinGauss.getText().trim());
            double maxGauss = Double.parseDouble(tfMaxGauss.getText().trim());
            double minVolt = Double.parseDouble(tfMinVolt.getText().trim());
            double maxVolt = Double.parseDouble(tfMaxVolt.getText().trim());
            
            // Verifica validità dei parametri
            if (numeroBit > 0 && numeroBit <= 32 && minGauss < maxGauss && minVolt < maxVolt) {
                modelloTabella.setNumeroBit(numeroBit);
                modelloTabella.aggiornaParametri(minGauss, maxGauss, minVolt, maxVolt);
            }
        } catch (NumberFormatException e) {
            // Ignora valori non validi
        }
    }

    // Poi implementiamo il metodo actionPerformed per gestire il click sul pulsante
    @Override 
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == bCarica) 
        {
            caricaFile();
            aggiornaGrafico();
            // Aggiorna l'etichetta con il numero di punti
            lblPunti.setText("Punti (" + modelloTabella.getRowCount() + ")");
        }
        else if (e.getSource() == bReset)
        {
            modelloTabella.svuotaTabella();
            // Imposta la funzione lineare prima di generare i punti
            modelloTabella.setTipoFunzioneVolt(TabellaDatiModel.TipoFunzione.LINEARE);
            generaPuntiLineari();
            // Aggiorna l'etichetta con il numero di punti
            lblPunti.setText("Punti (" + modelloTabella.getRowCount() + ")");
        }
        else if (e.getSource() == bEsponenziale)
        {
            modelloTabella.svuotaTabella();
            // Imposta la funzione esponenziale prima di generare i punti
            modelloTabella.setTipoFunzioneVolt(TabellaDatiModel.TipoFunzione.ESPONENZIALE);
            generaPuntiLineari(); // Usa lo stesso metodo ma con funzione esponenziale
            // Aggiorna l'etichetta con il numero di punti
            lblPunti.setText("Punti (" + modelloTabella.getRowCount() + ")");
        }
        else if (e.getSource() == bSalva)
        {
            salvaFile();
        }
    }

    // Metodo per gestire la selezione e il caricamento del file
    private void caricaFile() 
    {
        JFileChooser fileChooser = new JFileChooser();
        int risultato = fileChooser.showOpenDialog(this);

        if (risultato == JFileChooser.APPROVE_OPTION) 
        {
            try 
            {
                // Otteniamo il file selezionato
                java.io.File file = fileChooser.getSelectedFile();
                // Qui puoi implementare la logica per leggere e elaborare il file
                leggiFile(file);
            } 
            catch (Exception ex) 
            {
                JOptionPane.showMessageDialog(this, 
                    "Errore durante il caricamento del file: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Metodo per leggere il file e estrapolare i dati
    private void leggiFile(File file) 
    {
        java.io.BufferedReader reader = null;
        try 
        {
            reader = new java.io.BufferedReader(new java.io.FileReader(file));
            String linea;

            // Svuotiamo prima la tabella
            modelloTabella.svuotaTabella();

            // Lettura linea per linea
            while ((linea = reader.readLine()) != null) 
            {
                // Verifica se la linea è vuota
                if (linea.trim().isEmpty()) continue;
                
                // Dividi la linea in base al carattere "_"
                String[] parti = linea.split(",");
                
                 // Controlla se è la prima linea con i parametri (6 o 7 valori)
                if ((parti.length == 6 || parti.length == 7) && linea.matches("^[0-9.,\\-A-Z]+$")) {
                    // È la riga dei parametri, aggiorna i campi di testo
                    try {
                        tfPunti.setText(parti[0]);
                        tfBit.setText(parti[1]);
                        tfMinGauss.setText(parti[2]);
                        tfMaxGauss.setText(parti[3]);
                        tfMinVolt.setText(parti[4]);
                        tfMaxVolt.setText(parti[5]);
                        
                        // Se presente, imposta anche il tipo di funzione
                        if (parti.length == 7) {
                            String tipoFunzione = parti[6].trim().toUpperCase();
                            if ("LINEARE".equals(tipoFunzione)) {
                                modelloTabella.setTipoFunzioneVolt(TabellaDatiModel.TipoFunzione.LINEARE);
                            } else if ("ESPONENZIALE".equals(tipoFunzione)) {
                                modelloTabella.setTipoFunzioneVolt(TabellaDatiModel.TipoFunzione.ESPONENZIALE);
                            }
                        }
                        
                        // Aggiorna i parametri del modello
                        updateParametri();
                    } catch (NumberFormatException ex) {
                        System.err.println("Parametri non validi nella prima riga");
                    }
                    continue;
                }
                
                // Formato con 3 valori: gauss_volt_binario
                if (parti.length == 3) 
                {
                    try 
                    {
                        double gauss = Double.parseDouble(parti[1].trim());
                        double volt = Double.parseDouble(parti[2].trim());
                        String binario = parti[0].trim();

                        // Aggiungi il dato alla tabella
                        modelloTabella.aggiungiDato(gauss, volt, binario);
                    } 
                    catch (NumberFormatException ex) 
                    {
                        System.err.println("Formato numero non valido nella linea: " + linea);
                    }
                }
            }

            JOptionPane.showMessageDialog(this, 
                "File caricato con successo: " + file.getName(),
                "Successo", JOptionPane.INFORMATION_MESSAGE);
        } 
        catch (java.io.IOException e) 
        {
            JOptionPane.showMessageDialog(this, 
                "Errore nella lettura del file: " + e.getMessage(),
                "Errore", JOptionPane.ERROR_MESSAGE);
        }
        finally 
        {
            if (reader != null) 
            {
                try 
                {
                    reader.close();
                } 
                catch (java.io.IOException e) 
                {
                    System.err.println("Errore durante la chiusura del reader: " + e.getMessage());
                }
            }
        }
    }
    
    // Metodo per salvare i dati su file
    private void salvaFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        int risultato = fileChooser.showSaveDialog(this);

        if (risultato == JFileChooser.APPROVE_OPTION)
        {
            // Crea array parametri con i valori correnti
            String[] parametri = {
                tfPunti.getText().trim(),
                tfBit.getText().trim(),
                tfMinGauss.getText().trim(),
                tfMaxGauss.getText().trim(),
                tfMinVolt.getText().trim(),
                tfMaxVolt.getText().trim()
            };
            
            // Ottieni il tipo di funzione corrente per salvarlo
            String tipoFunzione = modelloTabella.getTipoFunzioneVolt().toString();
            
            TabellaDatiModel.salvaDati(this, modelloTabella, fileChooser.getSelectedFile(), parametri, tipoFunzione);
        }
    }
    
    // Metodo per generare punti in configurazione lineare
    private void generaPuntiLineari()
    {
        try {
            // Leggi i valori dai campi di testo
            int numeroPunti = Integer.parseInt(tfPunti.getText().trim());
            int numeroBit = Integer.parseInt(tfBit.getText().trim());
            double minGauss = Double.parseDouble(tfMinGauss.getText().trim());
            double maxGauss = Double.parseDouble(tfMaxGauss.getText().trim());
            double minVolt = Double.parseDouble(tfMinVolt.getText().trim());
            double maxVolt = Double.parseDouble(tfMaxVolt.getText().trim());

            // Verifica validità dei parametri
            if (numeroPunti <= 0 || numeroBit <= 0 || 
                minGauss >= maxGauss || minVolt >= maxVolt) {
                JOptionPane.showMessageDialog(this, 
                    "Parametri non validi. Assicurati che:\n" +
                    "- Numero punti > 0\n" +
                    "- Numero bit > 0\n" +
                    "- Min Gauss < Max Gauss\n" +
                    "- Min Volt < Max Volt",
                    "Errore Parametri", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Aggiorna i parametri del modello
            modelloTabella.setNumeroBit(numeroBit);
            modelloTabella.aggiornaParametri(minGauss, maxGauss, minVolt, maxVolt);

            // Svuota la tabella prima di aggiungere nuovi punti
            modelloTabella.svuotaTabella();

            // Verifica se 0 è nel range specificato
            boolean zeroNelRange = (minGauss <= 0 && maxGauss >= 0);

            

            // Crea una lista per raccogliere tutti i punti
            List<Double> puntiDaAggiungere = new ArrayList<>();

            if (numeroPunti == 1) {
                // Se c'è un solo punto richiesto, mettilo al centro del range
                double gauss = (minGauss + maxGauss) / 2.0;
                puntiDaAggiungere.add(gauss);
            } else {
                // Distribuzione uniforme dei punti richiesti
                for (int i = 0; i < numeroPunti; i++) {
                    double progresso = (double) i / (numeroPunti - 1);
                    double gauss = minGauss + progresso * (maxGauss - minGauss);
                    puntiDaAggiungere.add(gauss);
                }
            }

            // Se 0 è nel range, aggiungilo come punto extra
            if (zeroNelRange) {
                // Verifica se 0 è già presente tra i punti generati (con tolleranza)
                boolean zeroGiaPresente = false;
                double tolleranza = 1e-10; // Tolleranza per confronto floating point

                for (double punto : puntiDaAggiungere) {
                    if (Math.abs(punto) < tolleranza) {
                        zeroGiaPresente = true;
                        break;
                    }
                }

                // Se 0 non è già presente, aggiungilo
                if (!zeroGiaPresente) {
                    puntiDaAggiungere.add(0.0);
                }
            }

            // Ordina tutti i punti in ordine crescente
            Collections.sort(puntiDaAggiungere);

            // Aggiungi i punti ordinati alla tabella
            for (double punto : puntiDaAggiungere) {
                modelloTabella.aggiungiDato(punto);
            }

            // Piccola pausa per l'animazione dopo aver generato tutti i punti
            int puntiTotali = puntiDaAggiungere.size();
            try {
                Thread.sleep(30 * puntiTotali);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Aggiorna il grafico finale
            aggiornaGrafico();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Errore nel formato dei parametri: " + e.getMessage(),
                "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo per aggiornare il grafico con i dati correnti
    private void aggiornaGrafico()
    {
        // Crea una nuova serie per i dati
        XYSeries series = new XYSeries("Gauss-Volt");

        // Aggiungi tutti i punti dalla tabella
        java.util.List<TabellaDatiModel.DatiGaussVoltBin> dati = modelloTabella.getDati();
        for (TabellaDatiModel.DatiGaussVoltBin dato : dati)
        {
            series.add(dato.getGauss(), dato.getVolt());
        }

        // Aggiorna il dataset del grafico
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Aggiorna il grafico
        JFreeChart grafico = ChartFactory.createScatterPlot(
            "Gauss - Volt", "Gauss", "Volt", dataset,
            PlotOrientation.VERTICAL, true, true, false);

        // Personalizzazione del grafico
        XYPlot plot = (XYPlot) grafico.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        plot.setRenderer(renderer);

        // Aggiorna il pannello del grafico
        panelGrafico.setChart(grafico);
    }
}
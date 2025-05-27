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

public class Finestra extends JFrame implements ActionListener
{
    // Elementi grafici
    private JPanel pannello;
    private JButton bCarica;
    private JButton bSalva;
    private JButton bReset;
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
        pannello.add(bReset = new JButton("Reset"));

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

        // Posizionamento
        bCarica.setBounds(30, 30, 100, 30);
        bSalva.setBounds(160, 30, 100, 30);
        bReset.setBounds(290, 30, 100, 30);
        
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
            generaPuntiLineari();
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

            // Variabile per tracciare se è la prima linea (parametri)
            boolean primaLinea = true;

            // Lettura linea per linea
            while ((linea = reader.readLine()) != null) 
            {
                // Verifica se la linea è vuota
                if (linea.trim().isEmpty()) continue;
                
                // Dividi la linea in base alla virgola ","
                String[] parti = linea.split(",");
                
                // Controlla se è la prima linea con i parametri (6 o 7 valori: parametri + eventuale tipo funzione)
                if (primaLinea && parti.length >= 6) {
                    try {
                        // È la riga dei parametri, aggiorna i campi di testo
                        tfPunti.setText(parti[0].trim());
                        tfBit.setText(parti[1].trim());
                        tfMinGauss.setText(parti[2].trim());
                        tfMaxGauss.setText(parti[3].trim());
                        tfMinVolt.setText(parti[4].trim());
                        tfMaxVolt.setText(parti[5].trim());
                        
                        // Se presente, leggi anche il tipo di funzione
                        if (parti.length >= 7) {
                            String tipoFunzione = parti[6].trim();
                            if ("LINEARE".equals(tipoFunzione)) {
                                modelloTabella.setTipoFunzioneVolt(TabellaDatiModel.TipoFunzione.LINEARE);
                            } else if ("ESPONENZIALE".equals(tipoFunzione)) {
                                modelloTabella.setTipoFunzioneVolt(TabellaDatiModel.TipoFunzione.ESPONENZIALE);
                            }
                        }
                        
                        // Aggiorna i parametri del modello
                        updateParametri();
                        primaLinea = false;
                        continue;
                        
                    } catch (NumberFormatException ex) {
                        // Se non riesce a parsare i parametri, tratta la linea come dati normali
                        System.out.println("Errore nel parsing dei parametri: " + ex.getMessage());
                    }
                }
                
                // Processa le righe dei dati: formato "binario,gauss,volt"
                if (parti.length >= 3) {
                    try {
                        String binario = parti[0].trim();
                        double gauss = Double.parseDouble(parti[1].trim());
                        double volt = Double.parseDouble(parti[2].trim());
                        
                        // Aggiungi il punto dati alla tabella
                        modelloTabella.aggiungiPunto(binario, gauss, volt);
                        
                    } catch (NumberFormatException ex) {
                        System.out.println("Errore nel parsing della linea: " + linea + " - " + ex.getMessage());
                    }
                } else if (parti.length == 2) {
                    // Formato alternativo: "gauss,volt" (senza binario)
                    try {
                        double gauss = Double.parseDouble(parti[0].trim());
                        double volt = Double.parseDouble(parti[1].trim());
                        
                        // Genera il binario automaticamente basandosi sui parametri correnti
                        String binario = modelloTabella.calcolaBinario(gauss, volt);
                        modelloTabella.aggiungiPunto(binario, gauss, volt);
                        
                    } catch (NumberFormatException ex) {
                        System.out.println("Errore nel parsing della linea: " + linea + " - " + ex.getMessage());
                    }
                } else {
                    System.out.println("Formato linea non riconosciuto: " + linea);
                }
                
                primaLinea = false;
            }
            
            System.out.println("File caricato con successo. Punti caricati: " + modelloTabella.getRowCount());
            
        } 
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(this, 
                "Errore durante la lettura del file: " + ex.getMessage(),
                "Errore", JOptionPane.ERROR_MESSAGE);
        } 
        finally 
        {
            // Chiudiamo il BufferedReader
            if (reader != null) 
            {
                try 
                {
                    reader.close();
                } 
                catch (IOException ex) 
                {
                    System.out.println("Errore nella chiusura del file: " + ex.getMessage());
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
            try 
            {
                java.io.File file = fileChooser.getSelectedFile();
                java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(file));
                
                // Scrivi prima la riga dei parametri
                writer.write(tfPunti.getText() + "," +
                           tfBit.getText() + "," +
                           tfMinGauss.getText() + "," +
                           tfMaxGauss.getText() + "," +
                           tfMinVolt.getText() + "," +
                           tfMaxVolt.getText() + "," +
                           modelloTabella.getTipoFunzioneVolt().toString());
                writer.newLine();
                
                // Poi scrivi tutti i dati della tabella
                for (int i = 0; i < modelloTabella.getRowCount(); i++) {
                    String binario = (String) modelloTabella.getValueAt(i, 0);
                    String gauss = modelloTabella.getValueAt(i, 1).toString();
                    String volt = modelloTabella.getValueAt(i, 2).toString();
                    
                    writer.write(binario + "," + gauss + "," + volt);
                    writer.newLine();
                }
                
                writer.close();
                JOptionPane.showMessageDialog(this, "File salvato con successo!", 
                                            "Salvataggio", JOptionPane.INFORMATION_MESSAGE);
                
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(this, 
                    "Errore durante il salvataggio del file: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Metodo per generare punti lineari (chiamato dal pulsante Reset)
    private void generaPuntiLineari() 
    {
        try {
            int numPunti = Integer.parseInt(tfPunti.getText().trim());
            modelloTabella.generaPuntiLineari(numPunti);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Errore: Il numero di punti deve essere un valore numerico valido.",
                "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo per aggiornare il grafico
    private void aggiornaGrafico() 
    {
        XYSeries serie = new XYSeries("Dati");
        
        // Aggiungi tutti i punti dalla tabella
        for (int i = 0; i < modelloTabella.getRowCount(); i++) {
            Double gauss = (Double) modelloTabella.getValueAt(i, 1);
            Double volt = (Double) modelloTabella.getValueAt(i, 2);
            serie.add(gauss, volt);
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection(serie);
        
        // Ricrea il grafico con i nuovi dati
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

        // Aggiorna il pannello del grafico
        panelGrafico.setChart(grafico);
        panelGrafico.repaint();
    }
}

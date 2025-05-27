package progettotlc;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.List;

public class TabellaDatiModel extends AbstractTableModel 
{
    public java.util.List<DatiGaussVoltBin> dati = new java.util.ArrayList<>();
    private final String[] nomiColonne = {"Binario", "Gauss", "MilliVolt"};
    private int numeroBit = 12; // Default a 12 bit
    private int minGauss = -1000;
    private int maxGauss = 1000;
    private int minVolt = 0;
    private int maxVolt = 5000;

    private void fireTableRowUpdated(int rowIndex) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // Enum per i tipi di funzione
    public enum TipoFunzione {
        LINEARE,
        ESPONENZIALE
    }
    
    private TipoFunzione tipoFunzioneVolt = TipoFunzione.LINEARE; // Default esponenziale
    
    public void setTipoFunzioneVolt(TipoFunzione tipo) {
        this.tipoFunzioneVolt = tipo;
        ricalcolaValori();
    }
    
    public TipoFunzione getTipoFunzioneVolt() {
        return tipoFunzioneVolt;
    }
    
    public void setNumeroBit(int numeroBit) {
        this.numeroBit = numeroBit;
        ricalcolaValori();
    }
    
    public void setRangeGauss(int minGauss, int maxGauss) {
        this.minGauss = minGauss;
        this.maxGauss = maxGauss;
        ricalcolaValori();
    }
    
    public void setRangeVolt(int minVolt, int maxVolt) {
        this.minVolt = minVolt;
        this.maxVolt = maxVolt;
        ricalcolaValori();
    }
    
    private void ricalcolaValori() {
        // Ricalcola tutti i valori binari e volt quando cambiano i parametri
        for (int i = 0; i < dati.size(); i++) {
            DatiGaussVoltBin dato = dati.get(i);
            double nuoviVolt = calcolaVolt(dato.getGauss());
            long nuovoBinario = calcolaBinario(dato.getGauss());
            dati.set(i, new DatiGaussVoltBin(nuovoBinario,dato.getGauss(), nuoviVolt));
        }
        fireTableDataChanged();
    }
    
    public void aggiungiDato(double gauss) 
    {
        double volt = calcolaVolt(gauss);
        long binario = calcolaBinario(gauss);
        dati.add(new DatiGaussVoltBin(binario,gauss, volt));
        fireTableDataChanged();
    }
    
    public void aggiungiDato(double gauss, double volt, String binarioStr) 
    {
        // Converte la stringa binario in long per consistenza
        long binario;
        try {
            binario = Long.parseLong(binarioStr);
        } catch (NumberFormatException e) {
            binario = calcolaBinario(gauss);
        }
        dati.add(new DatiGaussVoltBin(binario, gauss, volt));
        fireTableDataChanged();
    }
    
    private double calcolaVolt(double gauss) {
        return switch (tipoFunzioneVolt) {
            case LINEARE -> calcolaVoltLineare(gauss);
            case ESPONENZIALE -> calcolaVoltEsponenziale(gauss);
            default -> calcolaVoltEsponenziale(gauss);
        };
    }
    
    private double calcolaVoltLineare(double gauss) {
        // Funzione lineare: mappa linearmente il range gauss sul range volt
        double rangeGauss = maxGauss - minGauss;
        double rangeVolt = maxVolt - minVolt;
        
        if (rangeGauss == 0) return minVolt;
        
        // Normalizza gauss nel range [0, 1]
        double normalizzato = (gauss - minGauss) / rangeGauss;
        // Limita il valore tra 0 e 1
        normalizzato = Math.max(0, Math.min(1, normalizzato));
        
        // Mappa sul range volt
        return minVolt + normalizzato * rangeVolt;
    }
    
    private double calcolaVoltEsponenziale(double gauss) {
        // Formula esponenziale originale: V = 1000 * (1,0013^GAUSS + 1,25)
        return 1000 * (Math.pow(1.0013, gauss) + 1.25);
    }
    
    private long calcolaBinario(double gauss) {
        // Normalizza il valore gauss nel range [0, 2^numeroBit - 1]
        // Formula: binario = (gauss - minGauss) / (maxGauss - minGauss) * (2^numeroBit - 1)
        
        double rangeGauss = maxGauss - minGauss;
        if (rangeGauss == 0) return 0;
        
        double normalizzato = (gauss - minGauss) / rangeGauss;
        // Limita il valore tra 0 e 1
        normalizzato = Math.max(0, Math.min(1, normalizzato));
        
        long maxValoreBinario = (1L << numeroBit) - 1; // 2^numeroBit - 1
        return Math.round(normalizzato * maxValoreBinario);
    }
    
    public List<DatiGaussVoltBin> getDati()
    {
        return new java.util.ArrayList<>(dati);
    }
    
    public void svuotaTabella() 
    {
        dati.clear();
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() 
    {
        return dati.size();
    }
    
    @Override
    public int getColumnCount() 
    {
        return nomiColonne.length;
    }
    
    @Override
    public String getColumnName(int column) 
    {
        return nomiColonne[column];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) 
    {
        // Solo la colonna MilliVolt (colonna 2) è editabile
        // Le colonne Binario (colonna 0) e Gauss (colonna 1) non sono editabili
        return columnIndex == 2;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
    {
        if (rowIndex < 0 || rowIndex >= dati.size()) 
        {
            return;
        }
        
        try {
            DatiGaussVoltBin datoCorrente = dati.get(rowIndex);
            
            // Solo la colonna MilliVolt (colonna 2) può essere modificata
            if (columnIndex == 2) { // Modifica Volt
                double nuoviVolt = Double.parseDouble(aValue.toString().trim());
                
                // Mantieni i Gauss correnti e ricalcola solo il binario
                long nuovoBinario = calcolaBinario(datoCorrente.getGauss());
                
                dati.set(rowIndex, new DatiGaussVoltBin(nuovoBinario,datoCorrente.getGauss(), nuoviVolt));
                
                // Notifica il cambiamento delle colonne Volt e Binario
                fireTableCellUpdated(rowIndex, 0); // Binario
                fireTableCellUpdated(rowIndex, 2); // Volt
            }
        } catch (NumberFormatException e) {
            // Ignora valori non validi
            System.err.println("Valore non valido inserito: " + aValue);
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        if (rowIndex < 0 || rowIndex >= dati.size()) 
        {
            return null;
        }
        
        DatiGaussVoltBin dato = dati.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return dato.getBinario();
            }
            case 1 -> {
                return String.format("%.2f", dato.getGauss());
            }
            case 2 -> {
                return String.format("%.2f", dato.getVolt());
            }
            default -> {
            }
        }
        return null;
    }
    
    // Classe interna per rappresentare i dati
    public class DatiGaussVoltBin 
    {
        private final double gauss;
        private final double volt;
        private final long binario;
        
        // Costruttore con tutti i parametri
        public DatiGaussVoltBin(long binario, double gauss, double volt) 
        {
            this.gauss = gauss;
            this.volt = volt;
            this.binario = binario;
        }
        
        public double getGauss() 
        {
            return gauss;
        }
        
        public double getVolt() 
        {
            return volt;
        }
        
        public long getBinario() 
        {
            return binario;
        }
    }
    
    // Metodi per aggiornare i range dai campi di input
    public void aggiornaParametri(double minGauss, double maxGauss, double minVolt, double maxVolt) {
        this.minGauss = (int) minGauss;
        this.maxGauss = (int) maxGauss;
        this.minVolt = (int) minVolt;
        this.maxVolt = (int) maxVolt;
        ricalcolaValori();
    }
    
    public static void salvaDati(JFrame padre, TabellaDatiModel modelloTabella, File file, String[] parametri, String tipoFunzione)
    {
        PrintWriter writer = null;

        try
        {
            writer = new java.io.PrintWriter(new java.io.FileWriter(file));
            
             // Ottieni tutti i dati dalla tabella
            List<TabellaDatiModel.DatiGaussVoltBin> dati = modelloTabella.getDati();
            
            // Scrivi la riga di parametri nel formato punti_bit_minGauss_maxGauss_minVolt_maxVolt_tipoFunzione
            if (parametri != null && parametri.length == 6) {
                StringBuilder parametriString = new StringBuilder();
                for (int i = 0; i < parametri.length; i++) {
                    parametriString.append(parametri[i]);
                    if (i < parametri.length - 1) {
                        parametriString.append(",");
                    }
                }
                // Aggiungi il tipo di funzione
                parametriString.append(",").append(tipoFunzione);
                writer.println(parametriString.toString());
            }
            
            // Scrivi ogni record nel formato [valore gauss]_[valore volt]_[valore binario]
            for(TabellaDatiModel.DatiGaussVoltBin dato : dati)
            {
                writer.println(dato.getBinario() + "," + String.format("%d", (int) dato.getGauss()) + "," + 
                             String.format("%d", (int) dato.getVolt()));
            }

           

            JOptionPane.showMessageDialog(padre, 
                "File salvato con successo: " + file.getName(),
                "Successo", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(padre, 
                "Errore durante il salvataggio del file: " + e.getMessage(),
                "Errore", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            if(writer != null)
            {
                writer.close();
            }
        }
    }
    
    // Sovraccarico del metodo salvaDati per includere il tipo di funzione
    public static void salvaDati(JFrame padre, TabellaDatiModel modelloTabella, File file, String[] parametri)
    {
        // Usa la funzione esponenziale come default per retrocompatibilità
        salvaDati(padre, modelloTabella, file, parametri, "ESPONENZIALE");
    }
    
    // Aggiungi il vecchio metodo per retrocompatibilità
    public static void salvaDati(JFrame padre, TabellaDatiModel modelloTabella, File file)
    {
        // Crea un array di parametri vuoti per retrocompatibilità
        salvaDati(padre, modelloTabella, file, new String[]{"40", "12", "-1000", "1000", "0", "5000"},"ESPONENZIALE");
    }
}
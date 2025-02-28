package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import orodent.clientrelationmanager.todelete.Contatto;
import orodent.clientrelationmanager.todelete.Operatori;
import orodent.clientrelationmanager.todelete.TipoCliente;
import orodent.clientrelationmanager.todelete.Interessamento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class ReportController implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
        Contatto newEntryFromFile;
        String in;
        try{
            File file = new File("Importa.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            in = br.readLine();
            do{
                newEntryFromFile = new Contatto();
                int subStringStart = 0;
                int subStringEnd = in.indexOf(";");
                String subString;
                for (int i = 0; i <= 24; i++) {
                    subString = in.substring(subStringStart, subStringEnd);
                    fillAttribute(i, newEntryFromFile, subString);

                    subStringStart = subStringEnd + 1;
                    subStringEnd = in.indexOf(";", subStringStart);
                }
                System.out.println("   Inserimento di " + newEntryFromFile);
                System.err.println(generateInsertSQL(newEntryFromFile));
            }while((in = br.readLine()) != null );
            br.close();
        } catch (IOException e){
            System.out.println("Errore nell'importazione da file txt");
        }
    }

    private void fillAttribute (int index, Contatto bean, String attribute){
        switch (index){
            case 0:
                bean.setRagioneSociale(attribute);
                break;
            case 1:
                bean.setPersonaRiferimento(attribute);
                break;
            case 2:
                bean.setEmailReferente(attribute);
                break;
            case 3:
                bean.setTelefono(attribute);
                break;
            case 4:
                bean.setPaese(attribute);
                break;
            case 5:
                bean.setRegione(attribute);
                break;
            case 6:
                bean.setCitta(attribute);
                break;
            case 7:
                bean.setIndirizzo(attribute);
                break;
            case 8:
                bean.setNumeroCivico(attribute);
                break;
            case 9:
                bean.setProvincia(attribute);
                break;
            case 10:
                bean.setCap(attribute);
                break;
            case 11:
                if(attribute.isEmpty()) {
                    bean.setInteressamento(Interessamento.InteressamentoStatus.BLANK);
                } else{
                    try{
                        bean.setInteressamento(Interessamento.InteressamentoStatus.valueOf(attribute));
                    } catch (Exception ignored){
                    }
                }
                break;
            case 12:
                if (attribute.isEmpty()){
                    bean.setTipoCliente(TipoCliente.BLANK);
                } else {
                    try{
                        bean.setTipoCliente(TipoCliente.valueOf(attribute));
                    } catch (Exception ignored){}
                }
                break;
            case 13:
                bean.setPartitaIva(attribute);
                break;
            case 14:
                bean.setCodiceFiscale(attribute);
                break;
            case 15:
                bean.setTitolare(attribute);
                break;
            case 16:
                bean.setEmailGenereica(attribute);
                break;
            case 17:
                bean.setEmailCertificata(attribute);
                break;
            case 18:
                bean.setSitoWeb(attribute);
                break;
            case 19:
                if(!attribute.isBlank()){
                    try {
                        System.out.println(UUID.fromString(attribute));
                        bean.setNoteId(UUID.fromString(attribute));
                    } catch (Exception ignored) {}
                }
                break;
            case 20:
                if (attribute.isEmpty()) {
                    bean.setOperator(Operatori.BLANK);
                } else {
                    try {
                        bean.setOperator(Operatori.valueOf(attribute));
                    } catch (Exception ignored) {}
                }
                break;
        }
    }

    //Email titolare -> pec
    //cellulare titolare -> telefono 2
    private String generateInsertSQL(Contatto contatto){
        String sql = "INSERT INTO CUSTOMERS (ID, RAGIONE_SOCIALE, PERSONA_RIFERIMENTO, EMAIL_RIFERIMENTO, CELLULARE_RIFERIMENTO, TELEFONO_AZIENDALE, EMAIL_AZIENDALE, PAESE, CITTA, NOME_TITOLARE, CELLULARE_TITOLARE, EMAIL_TITOLARE, SITO_WEB, VOLTE_CONTATTATI, ULTIMA_CHIAMATA, PROSSIMA_CHIAMATA, DATA_ACQUISIZIONE, BUSINESS, OPERATORE_ASSEGNATO, INFORMATION, CATALOG, SAMPLE, PVU) VALUES(";

        sql += "'" + UUID.randomUUID() + "', ";
        sql += "'" + escape(contatto.getRagioneSociale()) + "', ";
        sql += "'" + escape(contatto.getPersonaRiferimento()) + "', ";
        sql += "'" + escape(contatto.getEmailReferente()) + "', ";
        sql += "'" + escape(contatto.getCellulare()) + "', ";
        sql += "'" + escape(contatto.getTelefono()) + "', ";
        sql += "'" + escape(contatto.getEmailGenereica()) + "', ";
        sql += "'" + escape(contatto.getPaese()) + "', ";
        sql += "'" + escape(contatto.getCitta()) + "', ";
        sql += "'" + escape(contatto.getTitolare()) + "', ";
        sql += "'" + escape(contatto.getTelefono2()) + "', ";
        sql += "'" + escape(contatto.getEmailCertificata()) + "', ";
        sql += "'" + escape(contatto.getSitoWeb()) + "', ";
        sql += contatto.getVolteContattati() + ", ";
        sql += (contatto.getUltimaChiamata() != null ? "'" + contatto.getUltimaChiamata() + "'" : "NULL") + ", ";
        sql += (contatto.getProssimaChiamata() != null ? "'" + contatto.getProssimaChiamata() + "'" : "NULL") + ", ";
        sql += (contatto.getAcquisizione() != null ? "'" + contatto.getAcquisizione() + "'" : "CURRENT_DATE") + ", ";
        sql += "'" + contatto.getTipoCliente() + "', ";
        sql += "'" + contatto.getOperator() + "', ";
        sql += "FALSE, FALSE, FALSE, NULL"; // Default per INFORMATION, CATALOG, SAMPLE e PVU

        sql += ");";

        return sql;
    }

    private String escape(String value) {
        return value != null ? value.replace("'", "''") : "";
    }
}

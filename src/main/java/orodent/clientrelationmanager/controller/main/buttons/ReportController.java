package orodent.clientrelationmanager.controller.main.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import orodent.clientrelationmanager.controller.database.DBManagerInterface;
import orodent.clientrelationmanager.controller.main.MainController;
import orodent.clientrelationmanager.todelete.Contatto;
import orodent.clientrelationmanager.todelete.Operatori;
import orodent.clientrelationmanager.todelete.TipoCliente;
import orodent.clientrelationmanager.todelete.Interessamento.InteressamentoStatus;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import java.time.format.DateTimeFormatter;

public class ReportController implements EventHandler<ActionEvent> {
    private final DocumentBuilder docBuilder;

    public ReportController(DBManagerInterface dbManagerInterface, MainController mainController) {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void handle(ActionEvent event) {

        printImportedClient();
    }

    private void printImportedClient(){
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
                for (int i = 0; i <= 26; i++) {
                    subString = in.substring(subStringStart, subStringEnd);
                    fillAttribute(i, newEntryFromFile, subString);

                    subStringStart = subStringEnd + 1;
                    subStringEnd = in.indexOf(";", subStringStart);
                }
                newEntryFromFile.setId(UUID.randomUUID());
                //System.out.println("   Inserimento di " + newEntryFromFile);
                System.out.println(generateInsertSQL(newEntryFromFile));
                printImportedNotes(newEntryFromFile);
            }while((in = br.readLine()) != null );
            br.close();
        } catch (IOException e){
           // System.out.println("Errore nell'importazione da file txt");
        }
    }

    private void printImportedNotes(Contatto newEntryFromFile) {
        manageNote(newEntryFromFile.getNoteId().toString(), newEntryFromFile.getId().toString());
    }

    public Document readXml(String input) throws IOException, SAXException {
        return docBuilder.parse("bin\\Note\\" + input + ".xml");
    }

    private void manageNote(String noteId, String clientId){
        try {
            Document document = readXml(noteId);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("chiamata");
            for (int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element e = (Element) node;
                    if (!Boolean.parseBoolean(e.getAttribute("cancelled"))) {
                        LocalDate date = LocalDate.parse(e.getAttribute("data"));
                        String operator = e.getAttribute("operatore");
                        LocalDate nextCall = LocalDate.parse("1953-12-11");
                        String sql = "INSERT INTO ANNOTATIONS(ID, CUSTOMER_ID, DATA_CHIAMATA, PROSSIMA_CHIAMATA, OPERATORE, CONTENUTO, INFORMATION, CATALOG, SAMPLE) VALUES (";
                        sql += "'" + UUID.randomUUID() + "', ";
                        sql += "'" + clientId + "', ";
                        sql += "'" + date + "', ";
                        sql += "'" + nextCall + "', ";
                        sql += "'" + operator + "', ";
                        String content = e.getTextContent();

                        sql += "'" + content.replaceAll("'", " ")+ "', ";
                        switch (e.getAttribute("newInterest")){
                            case "INFO" -> {
                                sql += true + ", ";
                                sql += false + ", ";
                                sql += "" + false;
                            }
                            case "LISTINO" -> {
                                sql += false + ", ";
                                sql += true + ", ";
                                sql += "" + false;
                            }

                            case "CAMPIONE" -> {
                                sql += false + ", ";
                                sql += false + ", ";
                                sql += "" + true;
                                }
                            default -> {
                                sql += false + ", ";
                                sql += false + ", ";
                                sql += false + "";
                            }
                        }
                        sql += ");";
                        String uga = sql.replaceAll("[\\r\\n]+", " ");
                        System.out.println(uga);
                    }
                }
            }
        } catch (IOException | SAXException ignored) {
        }
    }

    private void fillAttribute (int index, Contatto bean, String attribute){
        switch (index) {
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
                    bean.setInteressamento(InteressamentoStatus.BLANK);
                } else{
                    bean.setInteressamento(InteressamentoStatus.valueOf(attribute));
                }
                break;
            case 12:
                if (attribute.isEmpty()){
                    bean.setTipoCliente(TipoCliente.BLANK);
                } else {
                    bean.setTipoCliente(TipoCliente.valueOf(attribute));
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
                    bean.setNoteId(UUID.fromString(attribute));
                }
                break;
            case 20:
                if (attribute.isEmpty()) {
                    bean.setOperator(Operatori.BLANK);
                } else {
                    bean.setOperator(Operatori.valueOf(attribute));
                }
                break;
            case 21:
                bean.setVolteContattati(Integer.parseInt(attribute));
                break;
            case 22:
                LocalDate t;
                try{
                    t = LocalDate.parse(attribute);
                } catch (Exception e){
                    t = null;
                }
                bean.setUltimaChiamata(t);
                break;
            case 23:
                LocalDate j;
                try{
                    j = LocalDate.parse(attribute);
                }catch (Exception e){
                    j = null;
                }
                bean.setProssimaChiamata(j);
                break;
            case 24:
                double c;
                if (attribute.isEmpty() || Double.parseDouble(attribute) == 0.0){
                    c = 1;
                }else {
                    c = Double.parseDouble(attribute);
                }
                bean.setCoinvolgimento(c);
                break;
            case 25:
                LocalDate o = LocalDate.now();
                if (attribute.isEmpty()){
                    o = o.minusMonths(1);
                }else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
                    o = LocalDate.parse(attribute, formatter);
                }
                bean.setAcquisizione(o);
                break;


            case 26:
                bean.setCheckpoint(Integer.parseInt(attribute));
                break;
        }
    }

    //Email titolare -> pec
    //cellulare titolare -> telefono 2
    private String generateInsertSQL(Contatto contatto){
        String sql = "INSERT INTO CUSTOMERS (ID, RAGIONE_SOCIALE, PERSONA_RIFERIMENTO, EMAIL_RIFERIMENTO, CELLULARE_RIFERIMENTO, TELEFONO_AZIENDALE, EMAIL_AZIENDALE, PAESE, CITTA, NOME_TITOLARE, CELLULARE_TITOLARE, EMAIL_TITOLARE, SITO_WEB, VOLTE_CONTATTATI, ULTIMA_CHIAMATA, PROSSIMA_CHIAMATA, DATA_ACQUISIZIONE, BUSINESS, OPERATORE_ASSEGNATO, INFORMATION, CATALOG, SAMPLE, PVU) VALUES(";

        sql += "'" + contatto.getId() + "', ";
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

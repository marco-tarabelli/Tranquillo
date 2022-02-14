package it.unicam.ids.tranquillo.views;

import it.unicam.ids.tranquillo.entities.AttivitaSportiva;
import it.unicam.ids.tranquillo.entities.Attrezzatura;
import it.unicam.ids.tranquillo.entities.Prenotazione;
import it.unicam.ids.tranquillo.entities.ProdottoBar;
import it.unicam.ids.tranquillo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Component
public class CliView {

    @Autowired
    PrenotazioneService prenotazioneService;
    @Autowired
    AttrezzaturaService attrezzaturaService;
    @Autowired
    OrdinazioneService ordinazioneService;
    @Autowired
    ProdottoBarService prodottoBarService;
    @Autowired
    RegisterUserService registerUserService;
    @Autowired
    RiservazioneAttivitaService riservazioneAttivitaService;
    @Autowired
    AttivitaSportivaService attivitaSportivaService;

    public void start(){
      int a;
    do{
    System.out.println("\n"+"MENU PER PRENOTAZIONE ATTREZZATURA SPIAGGIA"+
            "\n digita:" +
            "\n 1- per prenotare un'attrezzatura " +//prenotare per noi=creazione ombrellone
            "\n 2- per prenotare un prodotto del bar" +
            "\n 3- per prenotare un'attivita sportiva" +
            "\n 4- per ricapitolare le prorpie prenotazioni" +
            "\n 0- per uscire dal menu");
    Scanner input= new Scanner(System.in);
    a = input.nextInt();
    String back = null;
    switch (a){
        case 1:
           do {
               List<Attrezzatura> listaAttrezzatura = this.attrezzaturaService.getAttrezzaturaNonPrenotaate();
               System.out.println("Lista attrezzatura disponibili" + "\n" + listaAttrezzatura);
               if (listaAttrezzatura.size() <= 0) {
                   System.out.println("Lista attrezzatura vuota");
                   break;
               }
               System.out.println("inserisci il numero dell'attrezzatura da selezionare");//controllo personale
               Scanner selezioneNumero = new Scanner(System.in);
               int inpNum = selezioneNumero.nextInt() ;
               Attrezzatura attrezzatura= this.attrezzaturaService.selectAttrezzatura(inpNum);

               System.out.println("Inserire date per la prenotazione: checkin -- checkout [gg/mm/yyyy] :");
               Date checkinDate =null;
               Scanner checkinInp = new Scanner(System.in);
               String checkin = checkinInp.next();
               try{
                   DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
                   //imposta che i calcoli di conversione della data siano rigorosi
                   formatoData.setLenient(false);
                   checkinDate = formatoData.parse(checkin);
               } catch (ParseException e) {
                   System.out.println("Formato data non valido.");
               }
               Date checkOutDate =null;
               Scanner checkOutInp = new Scanner(System.in);
               String checkout = checkOutInp.next();
               try{
                   DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
                   //imposta che i calcoli di conversione della data siano rigorosi
                   formatoData.setLenient(false);
                   checkOutDate = formatoData.parse(checkout);
               } catch (ParseException e) {
                   System.out.println("Formato data non valido.");
               }
               List<Prenotazione>listaPrenotazioni =this.prenotazioneService.getListaPrenotazioni();
               if(!listaPrenotazioni.equals(checkinDate)&&!listaPrenotazioni.equals(checkout)){
               this.prenotazioneService.createPrenotazione(attrezzatura,checkinDate,checkOutDate);
               System.out.println("Attrezzatura prenotata");
               }
               System.out.println("proseguire con la prenotazione di un'attrezzatura? Digitare no per uscire dal menu");
               Scanner inputBack = new Scanner(System.in);
               back = inputBack.next();

           }while(!back.startsWith("no"));
            break;
        case 2:
            if(this.prenotazioneService.puoPrenotare()==true){
                do{
            List<ProdottoBar> listaProdottiBar = this.prodottoBarService.getProdottiBar();
            System.out.println("Lista prodotti bar disponibili nello chalet" + "\n" + listaProdottiBar);
            System.out.println("inserisci il numero del prodotto da selezionare" );
            Scanner selezioneProd = new Scanner(System.in);
            int inpProd = selezioneProd.nextInt();
            ProdottoBar prodottoBar = this.prodottoBarService.selectProdottoBar(inpProd);
            System.out.println("inserisci la quantita per il prodotto  selezionato");
            Scanner qtaProd = new Scanner(System.in);
            int qta = qtaProd.nextInt();
            this.ordinazioneService.createOrdinazioneProdotti(prodottoBar, qta);
            System.out.println("\n" + "prodotto" + prodottoBar + "prenotato");
            System.out.println("proseguire con la prenotazione di un prodotto del bar? Digitare no per uscire dal menu");
            Scanner inputBack= new Scanner(System.in);
            back = inputBack.next();

                  }while(!back.startsWith("no"));
    }
            break;
    
        case 3:
            do{
                List<AttivitaSportiva> listaAttivitaSportiva = this.attivitaSportivaService.getAttivitaSportiva();
                System.out.println("Lista attivita sportive disponibili" + "\n" + listaAttivitaSportiva);
                if (listaAttivitaSportiva.size() <= 0) {
                    System.out.println("Lista attività vuota");
                    break;
                }
                System.out.println("inserisci il numero dell'attivitao da selezionare" + listaAttivitaSportiva.size());
                Scanner selezioneAtt = new Scanner(System.in);
                int inpAtt = selezioneAtt.nextInt() - 1;
                AttivitaSportiva attivitaSportiva = listaAttivitaSportiva.get(inpAtt);
                System.out.println("inserisci il numero di posti da riservare per l'attivita");
                Scanner qtaPosti = new Scanner(System.in);
                int qta = qtaPosti.nextInt();
                this.riservazioneAttivitaService.createRiservazioneAttivita(attivitaSportiva, qta);
                System.out.println("\n"+"attivita" + attivitaSportiva + "prenotata");
                System.out.println("Proseguire con la prenotazione di un'altra attivita? Digitare no per uscire dal menu");
                Scanner inputBack= new Scanner(System.in);
                back = inputBack.next();
            }while(!back.startsWith("no"));
            break;
        case 4:
    }
    }
    while (a!=0);
    }



}

//aggiunta attrezzatura da parte del titolare
 /*try {
         this.attrezzaturaService.createAttrezzatura("Ombrellone");
         } catch (IllegalArgumentException e) {
         System.out.println("Tipo non presente");
         break;
         }
         System.out.println("Ombrellone inserito");
         break;*/
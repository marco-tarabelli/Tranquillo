package it.unicam.ids.tranquillo.services;

import it.unicam.ids.tranquillo.entities.Attrezzatura;

import it.unicam.ids.tranquillo.entities.Prenotazione;
import it.unicam.ids.tranquillo.repositories.AttrezzaturaRepository;
import it.unicam.ids.tranquillo.repositories.PrenotazioneRepository;
import it.unicam.ids.tranquillo.repositories.Tipo_AttrezzaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public class PrenotazioneService {
    @Autowired
    PrenotazioneRepository prenotazioneRepository;
    @Autowired
    AttrezzaturaRepository attrezzaturaRepository;
    @Autowired
    Tipo_AttrezzaturaRepository tipo_attrezzaturaRepository;
    public void createPrenotazione(Attrezzatura attrezzatura, Date checkin,Date checkout) {
        if (attrezzatura.isPrenotato() == false) {
            Prenotazione prenotazione = new Prenotazione(attrezzatura,checkin,checkout);
            attrezzatura.setPrenotato(true);
            SessioneService sessione = SessioneService.getInstance(); //CI DA UN' ISTANZA SESSIONE SU CUI LAVORARE
            prenotazione.setCliente(sessione.getCliente());
            this.attrezzaturaRepository.save(attrezzatura);
            this.prenotazioneRepository.save(prenotazione);
        }
    }

    public List<Prenotazione> getListaPrenotazioni(){
        return this.prenotazioneRepository.findAll();
    }

 public boolean puoPrenotare(){
        SessioneService sessione = SessioneService.getInstance();
        int id= sessione.getCliente().getId();
       if(this.prenotazioneRepository.existsByCliente_Id(id)){
           return true;
       }
       System.out.println("Per poter ordinare prodotti al bar bisogna aver prenotato un ombrellone");
       return false;
 }
}

 /* public void createPrenotazione(Prenotazione prenotazione){
        this.prenotazioneRepository.save(prenotazione);
    }

    public void createPrenotazione(Attrezzatura attrezzatura) { //da non toccare
        Prenotazione prenotazione = new Prenotazione(attrezzatura);
        this.prenotazioneRepository.save(prenotazione);
    }
*/


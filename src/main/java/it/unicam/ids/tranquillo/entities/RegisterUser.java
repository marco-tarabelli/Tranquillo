package it.unicam.ids.tranquillo.entities;



import javax.persistence.*;


@Entity
public class RegisterUser {

   @Id @GeneratedValue(strategy=GenerationType.TABLE) private int id;
    private String email;
    private String password;



    public RegisterUser(String email,String password,Cliente cliente) {
       this.email = email;
       this.password = password;
       this.cliente=cliente;
    }

    public RegisterUser(String email,String password,Dipendente dipendente) {
        this.email = email;
        this.password = password;
        this.dipendente = dipendente;
    }

    public RegisterUser() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @OneToOne
    private Cliente cliente;

    @OneToOne
    private Dipendente dipendente;

    public Dipendente getDipendente() {
        return dipendente;
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }
}

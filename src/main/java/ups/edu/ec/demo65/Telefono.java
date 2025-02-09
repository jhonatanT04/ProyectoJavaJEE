package ups.edu.ec.demo65;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Telefono implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_telefono")
	private int id;
	
	@Column(name="operadora")
	private String tipo;
	
	@Column(name="numero")
	private String numero;
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String operadora) {
        this.tipo = operadora;
    }

    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}

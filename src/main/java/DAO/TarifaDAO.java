package DAO;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ups.edu.ec.demo65.Tarifa;

@Stateless
public class TarifaDAO {
	@PersistenceContext
	EntityManager em ;
	
	public void agregarTarifa(Tarifa tarifa) {
		em.persist(tarifa);
    }


    public Tarifa encontrarTarifa(int id) {
        return em.find(Tarifa.class, id);
    }


    public Tarifa modificarTarifa(Tarifa tarifa) {
        return em.merge(tarifa);
    }


    public void eliminarTarifa(int id) {
        Tarifa tarifa = em.find(Tarifa.class, id);
        if (tarifa != null) {
            em.remove(tarifa);
        }
    }


    public List<Tarifa> listarTarifa() {
        TypedQuery<Tarifa> query = em.createQuery("SELECT t FROM Tarifa t", Tarifa.class);
        return query.getResultList();
    }
}

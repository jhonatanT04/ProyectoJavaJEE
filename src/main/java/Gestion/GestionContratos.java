package Gestion;

import java.util.List;

import DAO.ContratoDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ups.edu.ec.demo65.Contrato;

@Stateless
public class GestionContratos {
	@EJB
	private ContratoDAO contratoDAO;	
	
	public void agregarContrato(Contrato contrato) {
		contratoDAO.agregarContrato(contrato);
	}
	public List<Contrato> listHorario() {
		return contratoDAO.listarContrato();
	}
	
	public List<Contrato> buscarContratosIdPersona(int id) {
		return contratoDAO.buscarContratosPorPersonaId(id);
	}
	
	public Contrato readHorario(int id) {
		return contratoDAO.buscarContrato(id);
	}
	public Contrato modificarContrato(Contrato contrato) {
		return contratoDAO.modificarContrato(contrato);
	}
	public void eliminarContrato(int id ) {
		contratoDAO.eliminarContrato(id);
	}
}
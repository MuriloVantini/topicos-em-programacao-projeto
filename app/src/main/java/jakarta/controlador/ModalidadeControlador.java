package jakarta.controlador;

import java.io.Serializable;
import java.util.List;

import jakarta.beans.Modalidade;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Named
@SessionScoped
@Transactional
public class ModalidadeControlador implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	public void testar1() {
		try {
			List<Modalidade> mods = em.createNativeQuery("select * from modalidade", Modalidade.class).getResultList();
			mods.forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testar2() {
		try {
			Modalidade m1 = new Modalidade();
			m1.setCodigo(70);
			m1.setNome("Esportes de neve");
			em.persist(m1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

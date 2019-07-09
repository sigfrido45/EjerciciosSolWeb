package persistencia.com;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

public class TestJpa {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
		EntityManager em = emf.createEntityManager();
		String opc = JOptionPane.showInputDialog("[1] ingresar, [2] buscar");

		if (opc.equals("1")) {
			String param = JOptionPane.showInputDialog("Ingrese parametros separados por comas [nomb,precio]");
			String[] params = param.split(",");
			addProduct(new Producto(params[0], Double.parseDouble(params[1])), em);
		}
		if (opc.equals("2")) {
			String param = JOptionPane.showInputDialog("Ingrese codigo a cosultar");
			Producto pro = getProducto(Integer.parseInt(param), em);
			 JOptionPane.showMessageDialog(null, pro.toString());
		}

	}

	public static void addProduct(Producto product, EntityManager entityManager) {
		EntityTransaction transaccion = entityManager.getTransaction();
		transaccion.begin();
		entityManager.persist(product);
		transaccion.commit();
	}

	public static Producto getProducto(int codigo, EntityManager entityManager) {
		TypedQuery<Producto> consulta = entityManager.createNamedQuery("getProducts", Producto.class);
		List<Producto> productos = consulta.getResultList();
		Producto prod = productos.stream().filter(pro -> pro.getCodigo() == codigo).findFirst().orElse(null);
		return prod;
	}

}

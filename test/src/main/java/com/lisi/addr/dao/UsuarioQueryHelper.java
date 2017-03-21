/*
 * I use DAO as query helpers ; passing EntityManager on constructor
 * and my bean classes (Stateless) control these DAOs and my controllers (actions) controls my beans
 * 
*/
package com.lisi.addr.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.lisi.addr.model.Usuario;

public class UsuarioQueryHelper {

	private EntityManager em;

	/*
	 * Only init me with the EntityManager! 
	 * @param EntityManager
	 * 
	 */
	public UsuarioQueryHelper(EntityManager em){
		this.em = em;
	}

	/*
	 * Get Usuario by user and password
	 * @param String user
	 * @param String pass
	 * @return Usuario 
	 */
	public Usuario getUsuario(String login, String pass){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> from = query.from(Usuario.class);
		TypedQuery<Usuario> typedQuery = em.createQuery(
				query.select(from )
				.where(
						builder.and(builder.equal(from.get("login"), login),  builder.equal(from.get("senha"), pass))
						)
				);

		try {
			return (Usuario) typedQuery.getSingleResult();
		}

		// OK , if these return NonUniqueResult ; you have 2 identical users! 
		catch (NoResultException | NonUniqueResultException x) { 
			return null; // thats it
		} 
	}

}

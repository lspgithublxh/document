package com.lisi.addr.business;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lisi.addr.dao.UsuarioQueryHelper;
import com.lisi.addr.model.Usuario;

@Stateless(mappedName = "LoginService")
public class LoginService {

	@PersistenceContext(unitName="primary")
	private EntityManager em;

	private UsuarioQueryHelper usuarioQueryHelper;

	// here I initialize the UsuarioQueryHelper with the EntityManager 
	// and guarantee it only create one instance 
	// per bean . I always execute ensureHelper first of all querys. The
	// idea is consume EntityManager in Helpers classes
	private UsuarioQueryHelper ensureHelper(){
		if (usuarioQueryHelper == null) {
			this.usuarioQueryHelper = new UsuarioQueryHelper(em);

		}
		return this.usuarioQueryHelper;
	}



	/*
	 * do login ; return not null for success
	 * @param String login
	 * @param String pass
	 * @return Usuario
	 */
	public Usuario doLogin(String login, String pass){
		Usuario usuario = ensureHelper().getUsuario(login, pass);
		if (usuario == null) {return null;}
		else {
			return usuario;
		}
	}

	/*
	 * **************************** DELETE ME ***************************
	 * SECURITY FLAW RISC ; THESE IS JUST FOR TESTING
	 * 
	 */
	public void createFirst(){
		System.out.println("\n\n\n CREATING USER TO CHECK ALL STUFFS!");
		Usuario usuario = new Usuario();
		usuario.setLogin("test"); // login
		usuario.setNome("Fulano Teste");
		usuario.setSenha("123"); // password
		getEm().persist(usuario);
		
		// deletar print em producao
		System.out.println("LoginService : createFirst : \n\n\n YOU CAN LOGIN FOR TEST "+usuario.getLogin() + " " + usuario.getSenha());
		System.out.println("********************************** IMPORTANT MESSAGE **********************************");
		System.out.println("*******************REMOVE createFirst() METHOD AND DELETE USER*******************");
		System.out.println("********************************** IMPORTANT MESSAGE **********************************");
	}


	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}



}

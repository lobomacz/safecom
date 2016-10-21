package com.ecom.safecom.services;

import java.util.List;

import com.ecom.safecom.entity.Usuario;


public interface IUserInfoService {
	
	//Devuelve una lista de Usuarios
	List<Usuario> getListaUsuarios();
	
	//Encontrar usuario por nombre de usuario
	Usuario getUsuario(String uname);
	
	//Actualizar datos del Usuario
	Usuario updateUsuario(Usuario user);
	
	//Crear/Guardar nuevo Usuario
	Usuario saveUsuario(Usuario user);
	
	//Eliminar Usuario
	void deleteUsuario(Usuario user);
}

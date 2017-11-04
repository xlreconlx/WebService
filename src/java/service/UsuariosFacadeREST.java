/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entities.Usuarios;
import clases.Usuario;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ander
 */
@Stateless
@Path("usuarios")
public class UsuariosFacadeREST extends AbstractFacade<Usuarios> {

    @PersistenceContext(unitName = "TestPU")
    private EntityManager em;

    public UsuariosFacadeREST() {
        super(Usuarios.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Usuarios entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Usuarios entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Usuarios find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("{email},{password}")
    @Produces({MediaType.APPLICATION_JSON})
    public Usuario Session(@PathParam("email") String email, @PathParam("password") String password) {
        Usuarios user = new Usuarios();
        Usuario usu = new Usuario();
        Gson gson = new Gson();
        try {
            user = (Usuarios) em.createNamedQuery("Usuarios.findByCorreo").setParameter("correo", email).getSingleResult();
            if (!user.getPassword().equals(password)) {
                return new Usuario();
            }
            usu.setEmail(user.getCorreo());
            usu.setPassword(user.getPassword());
            usu.setId(user.getIdusuarios());
            usu.setIdRol(user.getIdrol().getIdrol());
            usu.setNombreRol(user.getIdrol().getNombreRol());
            //System.out.println(gson.toJson(usu));
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            return new Usuario();
        }
        return usu;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuarios> findAll() {
        return super.findAll();
    }

    @GET
    @Path("/todos")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuario> findTodos() {

        List<Usuarios> lstUser = new ArrayList<>();
        List<Usuario> lstUsuario = new ArrayList<>();
        Gson gson = new Gson();
        try {
            lstUser = (List<Usuarios>) em.createNamedQuery("Usuarios.findAll").getResultList();

            for (Usuarios item : lstUser) {
                Usuario usu = new Usuario();
                usu.setEmail(item.getCorreo());
                usu.setPassword(item.getPassword());
                usu.setId(item.getIdusuarios());
                usu.setIdRol(item.getIdrol().getIdrol());
                usu.setNombreRol(item.getIdrol().getNombreRol());
                lstUsuario.add(usu);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            return null;
        }
        return lstUsuario;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuarios> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

package edu.upc.dsa.services;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;

import edu.upc.dsa.models.Objeto;
import edu.upc.dsa.models.Usuario;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/game", description = "Endpoint to GameManager Service")
@Path("/game")
public class GameManagerService {

    private GameManager gm;

    public GameManagerService(){
        this.gm = GameManagerImpl.getInstance();
        if(gm.consultarNumeroUsuarios() == 0){
            this.gm.anadirUsuario(new Usuario("12345", "Marc", "Cayuelas", "Lobato"));
            gm.addObjeto(new Objeto("0001", "espada"));
            gm.addObjeto(new Objeto("1000", "escudo"));

            gm.anadirObjetoAUsuario("12345", this.gm.getObjeto("0001"));

            gm.anadirUsuario(new Usuario("01010", "Toni", "Oller", "Arcas"));
            gm.anadirUsuario(new Usuario("10101", "Juan", "Lopez", "Rubio"));
        }
    }

    //CODIGOS: 200, 201, 400, 404, 507
    //@ApiResponse(code = 200, message = "Successful")
    //@ApiResponse(code = 201, message = "Successfully Created")
    //@ApiResponse(code = 400, message = "Error en el formato de los parámetros de entrada")
    //@ApiResponse(code = 404, message = "Not found")
    //@ApiResponse(code = 500, message = "Internal Server Error")


    @GET
    @ApiOperation(value = "Lista de Usuarios", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuario.class, responseContainer="List"),
            @ApiResponse(code = 404, message= "Lista de Usuarios no encontrada (está vacía)")
    })
    @Path("/getUsuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsrs() {

        //List<Usuario> usrs = this.gm.getListaUsuarios();

        //Probamos que funciona la consulta de Usuarios por orden alfabético
        List<Usuario> usrs = this.gm.getListadoUsuariosOrdAlfab();

        GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usrs) {};

        if(this.gm.getListaUsuarios() != null)
            return Response.status(200).entity(entity).build();
        else
            return Response.status(404).entity(entity).build();
    }


    @POST
    @ApiOperation(value = "Añadir Usuario", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully Created", response = Usuario.class),
            @ApiResponse(code = 400, message = "Error en el formato de los parámetros de entrada"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 507, message = "Tabla de Usuarios llena")
    })
    @Path("/addUsuario")
    public Response anadirUsuario(Usuario u){
        int err = gm.anadirUsuario(u);

        if(err == 201)
            return Response.status(201).entity(u).build();
        else if(err == 400)
            return Response.status(400).entity(u).build();
        else if(err == 507)
            return Response.status(507).entity(u).build();
        else
            return Response.status(500).entity(u).build();
    }


    @PUT
    @ApiOperation(value = "Modificar Usuario. ID NO MODIFICABLE (Si no habrá problema a la hora de indexar el Usuario en la tabla)", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully Created", response = Usuario.class),
            @ApiResponse(code = 400, message = "Error en el formato de los parámetros de entrada"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @Path("/modifUsuario/{id}")
    public Response modificarUsuario(@PathParam("id") String id, Usuario u){
        int err = gm.modificarUsuario(u.getIDusr(), u.getNombreusr(), u.getApellido1(), u.getApellido2());

        if(err == 201)
            return Response.status(201).entity(u).build();
        else if(err == 400)
            return Response.status(400).entity(u).build();
        else if(err == 404)
            return Response.status(404).entity(u).build();
        else
            return Response.status(500).entity(u).build();
    }

    @GET
    @ApiOperation(value = "Información de un Usuario", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuario.class),
            @ApiResponse(code = 404, message = "Lista de Usuarios no encontrada (está vacía)")
    })
    @Path("/getInfoUsuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfoUsr(@PathParam("id") String id) {
        Usuario u = this.gm.consultarUsuarioUSR(id);

        if (u == null) return Response.status(404).build();
        else  return Response.status(200).entity(u).build();
    }


    @GET
    @ApiOperation(value = "Objetos de un Usuario", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Objeto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Lista de Objetos del Usuario vacía (Inventario vacío) ó No se ha encontrado al Usuario")
    })
    @Path("/getObjetosUsuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjUsr(@PathParam("id") String id) {
        List<Objeto> objs = this.gm.consultarObjetosUsuario(id);
        GenericEntity<List<Objeto>> entity = new GenericEntity<List<Objeto>>(objs) {};

        if (objs.size() == 0) return Response.status(404).build();
        else  return Response.status(200).entity(objs).build();
    }

    @PUT //LO CONSIDERAMOS SERVICIO DE UPDATE (Modificar la lista de Objetos del Usuario: añadirle Objeto)
    @ApiOperation(value = "Añadir nuevo Objeto al Usuario introducido por parámetro", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully Created", response = Usuario.class),
            @ApiResponse(code = 400, message = "Error en el formato de los parámetros de entrada"),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),
            @ApiResponse(code = 507, message = "Inventario lleno (Lista de Objetos del Usuario llena)")
    })
    @Path("/anadirObjetoAUsuario/{id}")
    public Response modificarUsuario(@PathParam("id") String id, Objeto o){
        int err = gm.anadirObjetoAUsuario(id, o);

        if(err == 201)
            return Response.status(201).entity(o).build();
        else if(err == 400)
            return Response.status(400).entity(o).build();
        else if(err == 404)
            return Response.status(404).entity(o).build();
        else if(err == 507)
            return Response.status(404).entity(o).build();
        else
            return Response.status(500).entity(o).build();
    }

}

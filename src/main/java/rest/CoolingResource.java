/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dto.CoolingDTO;
import entities.Cooling;
import facades.MultiFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Rasmus2
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Sem3Exam",
                version = "0.1",
                description = "Backend of the Sem3 Exam project"
        ),
        tags = {
            @Tag(name = "Cooling endpoint", description = "API used for Cooling CRUD")
        },
        servers = {
            @Server(
                    description = "For Local host testing",
                    url = "http://localhost:8080/Sem3Exam"
            ),
            @Server(
                    description = "Server API",
                    url = "https://www.barfodpraetorius.dk/Sem3Exam"
            )

        }
)
@Path("cooling")
public class CoolingResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final MultiFacade<Cooling> MULTI = new MultiFacade(Cooling.class, EMF);

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Default display message when accessing the Cooling API successfully",
            tags = {"Cooling endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "The default Cooling endpoint message is dislayed"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public String demo() {
        return "{\"msg\":\"Cooling endpoint\"}";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetches Cooling components from hardware data by id",
            tags = {"Cooling endpoint"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CoolingDTO.class))),
                @ApiResponse(responseCode = "200", description = "The requested Cooling resource was returned by ID"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public CoolingDTO getCoolingById(@PathParam("id") Long id) {
        Cooling cooling = (Cooling) MULTI.find(id);
        return new CoolingDTO(cooling);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetches all Cooling components from hardware data",
            tags = {"Cooling endpoint"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CoolingDTO.class))),
                @ApiResponse(responseCode = "200", description = "The requested Cooling resources were returned"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public List<CoolingDTO> getAllCooling() {
        List<CoolingDTO> list = new ArrayList();
        List<Cooling> cooling = MULTI.findAll();
        cooling.forEach((c) -> {
            list.add(new CoolingDTO(c));
        });
        return list;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Cooling Deletion", tags = {"Cooling endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Cooling Edited"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided to delete")
            })
    public void deleteRest(@PathParam("id") Long id) {
        MULTI.remove(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @Operation(summary = "Cooling Creation", tags = {"Cooling endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Cooling Created"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided with the body")
            })
    public void createRest(Cooling entity) {
        MULTI.create(entity);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @Operation(summary = "Cooling Editing", tags = {"Cooling endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Cooling Edited"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided with the body to edit")
            })
    public void editRest(Cooling entity) {
        MULTI.edit(entity);
    }
}

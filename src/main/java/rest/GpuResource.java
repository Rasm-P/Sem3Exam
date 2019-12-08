/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dto.GpuDTO;
import entities.Gpu;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
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
            @Tag(name = "Gpu endpoint", description = "API used for Gpu CRUD")
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
@Path("gpu")
public class GpuResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final MultiFacade<Gpu> MULTI = new MultiFacade(Gpu.class, EMF);

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Default display message when accessing the Gpu API successfully",
            tags = {"Gpu endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "The default Gpu endpoint message is dislayed"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public String demo() {
        return "{\"msg\":\"Gpu endpoint\"}";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetches Gpu components from hardware data by id",
            tags = {"Gpu endpoint"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = GpuDTO.class))),
                @ApiResponse(responseCode = "200", description = "The requested Gpu resource was returned by ID"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public GpuDTO getGpuById(@PathParam("id") Long id) {
        Gpu gpu = (Gpu) MULTI.find(id);
        return new GpuDTO(gpu);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetches all Gpu components from hardware data",
            tags = {"Gpu endpoint"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = GpuDTO.class))),
                @ApiResponse(responseCode = "200", description = "The requested Gpu resources were returned"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public List<GpuDTO> getAllGpu() {
        List<GpuDTO> list = new ArrayList();
        List<Gpu> gpu = MULTI.findAll();
        gpu.forEach((c) -> {
            list.add(new GpuDTO(c));
        });
        return list;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "GPU Deletion", tags = {"Gpu endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "GPU deleted"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided to delete")
            })
    public void deleteRest(@PathParam("id") Long id) {
        MULTI.remove(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @Operation(summary = "GPU Creation", tags = {"Gpu endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "GPU Created"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided with the body")
            })
    public void createRest(Gpu entity) {
        MULTI.create(entity);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @Operation(summary = "GPU Editing", tags = {"Gpu endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "GPU Edited"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided with the body to edit")
            })
    public void editRest(Gpu entity) {
        MULTI.edit(entity);
    }
}

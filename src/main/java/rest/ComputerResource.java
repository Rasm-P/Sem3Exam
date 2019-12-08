/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dto.ComputerDTO;
import entities.Computer;
import entities.DiskDrive;
import entities.Gpu;
import entities.Ram;
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
import java.util.HashMap;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
            @Tag(name = "Computer endpoint", description = "API used for Computer CRUD")
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
@Path("computer")
public class ComputerResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final MultiFacade<Computer> MULTI = new MultiFacade(Computer.class, EMF);

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Operation(summary = "Default display message when accessing the Computer API successfully",
            tags = {"Computer endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "The default Computer endpoint message is dislayed"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public String demo() {
        return "{\"msg\":\"Computer endpoint\"}";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetches Computer components from hardware data by id",
            tags = {"Computer endpoint"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComputerDTO.class))),
                @ApiResponse(responseCode = "200", description = "The requested Computer resource was returned by ID"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public ComputerDTO getComputerById(@PathParam("id") Long id) {
        Computer computer = (Computer) MULTI.find(id);
        computer.getDiskDriveCollection().entrySet().stream().forEach(o->System.out.println(o.getValue()));
        return new ComputerDTO(computer);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetches all Computer components from hardware data",
            tags = {"Computer endpoint"},
            responses = {
                @ApiResponse(
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComputerDTO.class))),
                @ApiResponse(responseCode = "200", description = "The requested Computer resources were returned"),
                @ApiResponse(responseCode = "400", description = "The server cannot or will not process the request and no resources were returned")})
    public List<ComputerDTO> getAllComputer() {
        List<ComputerDTO> list = new ArrayList();
        List<Computer> computers = (List<Computer>) MULTI.findAll();
        for (Computer computer : computers) {
            list.add(new ComputerDTO(computer));
        }
        return list;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Computer Deletion", tags = {"Computer endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Computer Deleted"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided to delete")
            })
    public void deleteRest(@PathParam("id") Long id) {
        MULTI.remove(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @Operation(summary = "Computer Creation", tags = {"Computer endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Computer Created"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided with the body")
            })
    public void createRest(Computer entity) {

        entity.getRamList().stream().forEach(o -> System.out.println(o.getModelName()));

        HashMap<Ram, Integer> convertedRam = entity.ramListToCollection(entity.getRamList());
        entity.setRamCollection(convertedRam);

        HashMap<Gpu, Integer> convertedGpu = entity.gpuListToCollection(entity.getGpuList());
        entity.setGpuCollection(convertedGpu);

        HashMap<DiskDrive, Integer> convertedDiskDrive = entity.diskDriveListToCollection(entity.getDiskDriveList());
        entity.setDiskDriveCollection(convertedDiskDrive);

        MULTI.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @Operation(summary = "Computer Editing", tags = {"Computer endpoint"},
            responses = {
                @ApiResponse(responseCode = "200", description = "Computer Edited"),
                @ApiResponse(responseCode = "400", description = "Not all arguments provided with the body to edit")
            })
    public void editRest(@PathParam("id") Long id, Computer entity) {

        HashMap<Ram, Integer> convertedRam = entity.ramListToCollection(entity.getRamList());
        entity.setRamCollection(convertedRam);

        HashMap<Gpu, Integer> convertedGpu = entity.gpuListToCollection(entity.getGpuList());
        entity.setGpuCollection(convertedGpu);

        HashMap<DiskDrive, Integer> convertedDiskDrive = entity.diskDriveListToCollection(entity.getDiskDriveList());
        entity.setDiskDriveCollection(convertedDiskDrive);

        MULTI.edit(entity);
    }
}

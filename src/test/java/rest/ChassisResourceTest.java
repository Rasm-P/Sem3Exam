/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import dto.ComputerDTO;
import entities.User;
import entities.Role;
import facades.MultiFacade;
import entities.Chassis;
import entities.Computer;
import entities.Cooling;
import entities.Cpu;
import entities.DiskDrive;
import entities.Gpu;
import entities.Motherboard;
import entities.Psu;
import entities.Ram;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Rasmus2
 */
//@Disabled
public class ChassisResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final Gson GSON = new Gson();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    MultiFacade<Chassis> chas;
    MultiFacade<Gpu> gpu;
    MultiFacade<Cooling> cool;
    MultiFacade<Cpu> cpu;
    MultiFacade<DiskDrive> didr;
    MultiFacade<Motherboard> mother;
    MultiFacade<Psu> psu;
    MultiFacade<Ram> ram;
    MultiFacade<Computer> comp;

    public ChassisResourceTest() {
        gpu = new MultiFacade(Gpu.class, emf);
        chas = new MultiFacade(Chassis.class, emf);
        cool = new MultiFacade(Cooling.class, emf);
        cpu = new MultiFacade(Cpu.class, emf);
        didr = new MultiFacade(DiskDrive.class, emf);
        mother = new MultiFacade(Motherboard.class, emf);
        psu = new MultiFacade(Psu.class, emf);
        ram = new MultiFacade(Ram.class, emf);
        comp = new MultiFacade(Computer.class, emf);
    }

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    Gpu gp = new Gpu("modelname", "manu", 400.0, 5.0, 50, 45.0, 60.0, 100, "descr");
    Chassis ch = new Chassis("Chassisname", "ChassisManu", 20.0, 5.0, 150.0, 50.0, 30.0, "Descrip");
    Cooling co = new Cooling("Coolname", "coolmanu", 500.0, 5.0, "Fans", "Water");
    Cpu cp = new Cpu("proccessingname", "process manufact", 12, 45, 50.0, 75, "Pretty fast", 500.0, 5.0);
    DiskDrive dd = new DiskDrive("DDname", "DDManu", 444.4, 12, "ssd", "DDdesc", 2.5);
    Motherboard mb = new Motherboard("motherbname", "MotherMan", 50.0, 45, 800, 5, "KindaWeird", "WorstOption", 98, 20, 7.8);
    Psu ps = new Psu("Psuname", "PsuManu", 50.0, 600, "Descrpsu", 3.0);
    Ram rm = new Ram("RamName", "RamManu", 150.0, 12, 8.0, "Ramdesc", 5.5);
    List<Gpu> gl = new ArrayList();
    List<DiskDrive> dl = new ArrayList();
    List<Ram> rl = new ArrayList();
    HashMap<Ram, Integer> ramMap = new HashMap<>();
    Computer com = new Computer("Razzakield", new HashMap<>(), new HashMap<>(), new HashMap<>(), cp, ch, co, mb, ps);

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            em.createNamedQuery("Computer.deleteAll").executeUpdate();
            em.createNamedQuery("Gpu.deleteAll").executeUpdate();
            em.createNamedQuery("Chassis.deleteAll").executeUpdate();
            em.createNamedQuery("Cooling.deleteAll").executeUpdate();
            em.createNamedQuery("Cpu.deleteAll").executeUpdate();
            em.createNamedQuery("DiskDrive.deleteAll").executeUpdate();
            em.createNamedQuery("Motherboard.deleteAll").executeUpdate();
            em.createNamedQuery("Psu.deleteAll").executeUpdate();
            em.createNamedQuery("Ram.deleteAll").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);

            em.getTransaction().commit();
        } finally {
            em.close();
        }

        chas.create(ch);
        gpu.create(gp);
        cool.create(co);
        cpu.create(cp);
        didr.create(dd);
        mother.create(mb);
        psu.create(ps);
        ram.create(rm);
        com.addToRamCollection(rm);
        com.addToGpuCollection(gp);
        com.addToDiskDriveCollection(dd);
        comp.create(com);
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        System.out.println("Testing is server UP");
        given().when().get("/chassis").then().statusCode(200);
    }

    @Test
    public void restNoAuthenticationRequiredTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/chassis").then()
                .statusCode(200)
                .body("msg", equalTo("Chassis endpoint"));
    }

    @Test
    public void restForAdminTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/chassis/" + ch.getid()).then()
                .statusCode(200)
                .body("description", equalTo("Descrip"));
    }

    @Test
    public void restForUserTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/chassis/" + ch.getid()).then()
                .statusCode(200)
                .body("description", equalTo("Descrip"));
    }

//    @Test
//    public void fetchNotAuthenticated() {
//        logOut();
//        given()
//                .contentType("application/json")
//                .when()
//                .get("/chassis/" + ch.getid()).then()
//                .statusCode(403)
//                .body("code", equalTo(403))
//                .body("message", equalTo("Not authenticated - do login"));
//    }
    // ########### COOLING ###########
    @Test
    public void getChassisByIdTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/chassis/" + ch.getid()).then()
                .statusCode(200)
                .body("modelName", equalTo("Chassisname"))
                .body("manufacturer", equalTo("ChassisManu"))
                .body("price", equalTo(20.0f));
    }

    @Test
    public void getAllChassisTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/chassis/all")
                .then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].modelName", equalTo("Chassisname"))
                .body("[0].manufacturer", equalTo("ChassisManu"))
                .body("[0].price", equalTo(20.0f));
    }

    //Actual error: JSON input text must be neither null nor empty. Remove ".statuscode (200) to see"
    @Test
    public void createChassisTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .body("{\n"
                        + "  \"modelName\": \"string\",\n"
                        + "  \"manufacturer\": \"string\",\n"
                        + "  \"price\": 0,\n"
                        + "  \"height\": 0,\n"
                        + "  \"width\": 0,\n"
                        + "  \"depth\": 0,\n"
                        + "  \"rating\": 0,\n"
                        + "  \"description\": \"string\"\n"
                        + "}")
                .when()
                .post("/chassis")
                .then()
                .statusCode(204);
    }

    @Test
    public void editChassisTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body("{"
                        + "  \"id\": " + ch.getid() + ","
                        + "  \"modelName\": \"Keklord\","
                        + "  \"manufacturer\": \"asdf inc.\",\n"
                        + "  \"price\": 1000,"
                        + "  \"height\": 11,"
                        + "  \"width\": 13,"
                        + "  \"depth\": 12,"
                        + "  \"rating\": 3,"
                        + "  \"description\": \"Short answer: No. Long answer: Noooooooooooooooooooooooo\""
                        + "}")
                .when()
                .put("/chassis")
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteChassisTest() {
        login("admin", "test");
        Chassis deleteMe = new Chassis("CakeFace", "DerpMaster 6k", 2.1, 32.1, 23.1, 21.1, 12.1, "Aloha");
        chas.create(deleteMe);
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .delete("/chassis/" + deleteMe.getid())
                .then()
                .statusCode(204);
    }

    // ########### COMPUTER ###########
    @Test
    public void computerResourceTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/computer").then()
                .statusCode(200)
                .body("msg", equalTo("Computer endpoint"));
    }

    @Test
    public void getComputerByIdTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/computer/" + com.getid()).then()
                .statusCode(200)
                .body("modelName", equalTo("Razzakield"));
    }

    @Test
    public void getAllComputerTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/computer/all").then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].modelName", equalTo("Razzakield"));
    }

    @Disabled
    @Test
    public void createComputerTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .body("{\n"
                        + "  \"modelName\": \"string\",\n"
                        + "  \"gpuList\": [\n"
                        + "    {\n"
                        + "      \"modelName\": \"string\",\n"
                        + "      \"manufacturer\": \"string\",\n"
                        + "      \"price\": 0,\n"
                        + "      \"memory\": 0,\n"
                        + "      \"baseClockSpeed\": 0,\n"
                        + "      \"boostClockSpeed\": 0,\n"
                        + "      \"powerConsumption\": 0,\n"
                        + "      \"description\": \"string\",\n"
                        + "      \"rating\": 0\n"
                        + "    }\n"
                        + "  ],\n"
                        + "  \"diskDriveList\": [\n"
                        + "    {\n"
                        + "      \"modelName\": \"string\",\n"
                        + "      \"manufacturer\": \"string\",\n"
                        + "      \"price\": 0,\n"
                        + "      \"capacity\": 0,\n"
                        + "      \"type\": \"SSD\",\n"
                        + "      \"description\": \"string\",\n"
                        + "      \"rating\": 0\n"
                        + "    }\n"
                        + "  ],\n"
                        + "  \"ramList\": [\n"
                        + "    {\n"
                        + "      \"modelName\": \"string\",\n"
                        + "      \"manufacturer\": \"string\",\n"
                        + "      \"price\": 0,\n"
                        + "      \"capacity\": 0,\n"
                        + "      \"speed\": 0,\n"
                        + "      \"description\": \"string\",\n"
                        + "      \"rating\": 0\n"
                        + "    }\n"
                        + "  ],\n"
                        + "  \"chassisid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"height\": 0,\n"
                        + "    \"width\": 0,\n"
                        + "    \"depth\": 0,\n"
                        + "    \"rating\": 0,\n"
                        + "    \"description\": \"string\"\n"
                        + "  },\n"
                        + "  \"coolingid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"rating\": 0,\n"
                        + "    \"description\": \"string\",\n"
                        + "    \"type\": \"Water\"\n"
                        + "  },\n"
                        + "  \"motherboardid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"networkInterfaceController\": \"string\",\n"
                        + "    \"description\": \"string\",\n"
                        + "    \"rating\": 0,\n"
                        + "    \"compatibleCPUList\": [\n"
                        + "      {\n"
                        + "        \"modelName\": \"string\",\n"
                        + "        \"manufacturer\": \"string\",\n"
                        + "        \"cores\": 0,\n"
                        + "        \"threads\": 0,\n"
                        + "        \"clockSpeed\": 0,\n"
                        + "        \"powerConsumption\": 0,\n"
                        + "        \"description\": \"string\",\n"
                        + "        \"price\": 0,\n"
                        + "        \"rating\": 0\n"
                        + "      }\n"
                        + "    ],\n"
                        + "    \"compatibleGPUList\": [\n"
                        + "      {\n"
                        + "        \"modelName\": \"string\",\n"
                        + "        \"manufacturer\": \"string\",\n"
                        + "        \"price\": 0,\n"
                        + "        \"memory\": 0,\n"
                        + "        \"baseClockSpeed\": 0,\n"
                        + "        \"boostClockSpeed\": 0,\n"
                        + "        \"powerConsumption\": 0,\n"
                        + "        \"description\": \"string\",\n"
                        + "        \"rating\": 0\n"
                        + "      }\n"
                        + "    ],\n"
                        + "    \"ramslots\": 0,\n"
                        + "    \"hdmislots\": 0,\n"
                        + "    \"usbslots\": 0,\n"
                        + "    \"slots\": 0,\n"
                        + "    \"m2Slots\": 0,\n"
                        + "    \"sataslots\": 0\n"
                        + "  },\n"
                        + "  \"getpSUid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"watts\": 0,\n"
                        + "    \"description\": \"string\",\n"
                        + "    \"rating\": 0\n"
                        + "  },\n"
                        + "  \"cpuid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"cores\": 0,\n"
                        + "    \"threads\": 0,\n"
                        + "    \"clockSpeed\": 0,\n"
                        + "    \"powerConsumption\": 0,\n"
                        + "    \"description\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"rating\": 0\n"
                        + "  },\n"
                        + "  \"psuid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"watts\": 0,\n"
                        + "    \"description\": \"string\",\n"
                        + "    \"rating\": 0\n"
                        + "  }\n"
                        + "}")
                .when()
                .post("/computer")
                .then()
                .statusCode(204);
    }

    /*
    @Test
    public void deleteComputerTest() {
        login("admin", "test");
        Computer com = new Computer("King Kong's Banana Cocktail", gl, dl, rl, cp, ch, co, mb, ps);
        comp.create(com);
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .delete("/computer/" + com.getid())
                .then()
                .statusCode(204);
    }
     */
    @Disabled
    @Test
    public void editComputerTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body("{\n"
                        + "  \"modelName\": \"string\",\n"
                        + "  \"gpuList\": [\n"
                        + "    {\n"
                        + "      \"modelName\": \"string\",\n"
                        + "      \"manufacturer\": \"string\",\n"
                        + "      \"price\": 0,\n"
                        + "      \"memory\": 0,\n"
                        + "      \"baseClockSpeed\": 0,\n"
                        + "      \"boostClockSpeed\": 0,\n"
                        + "      \"powerConsumption\": 0,\n"
                        + "      \"description\": \"string\",\n"
                        + "      \"rating\": 0\n"
                        + "    }\n"
                        + "  ],\n"
                        + "  \"diskDriveList\": [\n"
                        + "    {\n"
                        + "      \"modelName\": \"string\",\n"
                        + "      \"manufacturer\": \"string\",\n"
                        + "      \"price\": 0,\n"
                        + "      \"capacity\": 0,\n"
                        + "      \"type\": \"SSD\",\n"
                        + "      \"description\": \"string\",\n"
                        + "      \"rating\": 0\n"
                        + "    }\n"
                        + "  ],\n"
                        + "  \"ramList\": [\n"
                        + "    {\n"
                        + "      \"modelName\": \"string\",\n"
                        + "      \"manufacturer\": \"string\",\n"
                        + "      \"price\": 0,\n"
                        + "      \"capacity\": 0,\n"
                        + "      \"speed\": 0,\n"
                        + "      \"description\": \"string\",\n"
                        + "      \"rating\": 0\n"
                        + "    }\n"
                        + "  ],\n"
                        + "  \"chassisid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"height\": 0,\n"
                        + "    \"width\": 0,\n"
                        + "    \"depth\": 0,\n"
                        + "    \"rating\": 0,\n"
                        + "    \"description\": \"string\"\n"
                        + "  },\n"
                        + "  \"coolingid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"rating\": 0,\n"
                        + "    \"description\": \"string\",\n"
                        + "    \"type\": \"Water\"\n"
                        + "  },\n"
                        + "  \"motherboardid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"networkInterfaceController\": \"string\",\n"
                        + "    \"description\": \"string\",\n"
                        + "    \"rating\": 0,\n"
                        + "    \"compatibleCPUList\": [\n"
                        + "      {\n"
                        + "        \"modelName\": \"string\",\n"
                        + "        \"manufacturer\": \"string\",\n"
                        + "        \"cores\": 0,\n"
                        + "        \"threads\": 0,\n"
                        + "        \"clockSpeed\": 0,\n"
                        + "        \"powerConsumption\": 0,\n"
                        + "        \"description\": \"string\",\n"
                        + "        \"price\": 0,\n"
                        + "        \"rating\": 0\n"
                        + "      }\n"
                        + "    ],\n"
                        + "    \"compatibleGPUList\": [\n"
                        + "      {\n"
                        + "        \"modelName\": \"string\",\n"
                        + "        \"manufacturer\": \"string\",\n"
                        + "        \"price\": 0,\n"
                        + "        \"memory\": 0,\n"
                        + "        \"baseClockSpeed\": 0,\n"
                        + "        \"boostClockSpeed\": 0,\n"
                        + "        \"powerConsumption\": 0,\n"
                        + "        \"description\": \"string\",\n"
                        + "        \"rating\": 0\n"
                        + "      }\n"
                        + "    ],\n"
                        + "    \"ramslots\": 0,\n"
                        + "    \"hdmislots\": 0,\n"
                        + "    \"usbslots\": 0,\n"
                        + "    \"slots\": 0,\n"
                        + "    \"m2Slots\": 0,\n"
                        + "    \"sataslots\": 0\n"
                        + "  },\n"
                        + "  \"getpSUid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"watts\": 0,\n"
                        + "    \"description\": \"string\",\n"
                        + "    \"rating\": 0\n"
                        + "  },\n"
                        + "  \"cpuid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"cores\": 0,\n"
                        + "    \"threads\": 0,\n"
                        + "    \"clockSpeed\": 0,\n"
                        + "    \"powerConsumption\": 0,\n"
                        + "    \"description\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"rating\": 0\n"
                        + "  },\n"
                        + "  \"psuid\": {\n"
                        + "    \"modelName\": \"string\",\n"
                        + "    \"manufacturer\": \"string\",\n"
                        + "    \"price\": 0,\n"
                        + "    \"watts\": 0,\n"
                        + "    \"description\": \"string\",\n"
                        + "    \"rating\": 0\n"
                        + "  }\n"
                        + "}")
                .when()
                .put("/computer")
                .then()
                .statusCode(204);
    }

    // ########### COOLING ###########
    @Test
    public void coolingResourceTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/cooling").then()
                .statusCode(200)
                .body("msg", equalTo("Cooling endpoint"));
    }

    @Test
    public void getCoolingByIdTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/cooling/" + co.getid()).then()
                .statusCode(200)
                .body("modelName", equalTo("Coolname"))
                .body("manufacturer", equalTo("coolmanu"))
                .body("price", equalTo(500.0f));
    }

    @Test
    public void getAllCoolingTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/cooling/all").then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].modelName", equalTo("Coolname"))
                .body("[0].manufacturer", equalTo("coolmanu"))
                .body("[0].price", equalTo(500.0f));
    }

    @Test
    public void createCoolingTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .body("{\n"
                        + "  \"modelName\": \"string\",\n"
                        + "  \"manufacturer\": \"string\",\n"
                        + "  \"price\": 0,\n"
                        + "  \"rating\": 0,\n"
                        + "  \"description\": \"string\",\n"
                        + "  \"type\": \"WATER\"\n"
                        + "}")
                .when()
                .post("/cooling")
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteCoolingTest() {
        login("admin", "test");
        Cooling cooler = new Cooling("CakeMake", "Derpface", 11.1, 12.1, "lul", "AIR");
        cool.create(cooler);
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .delete("/cooling/" + cooler.getid())
                .then()
                .statusCode(204);
    }

    @Test
    public void editCoolingTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body("{\n"
                        + "  \"id\": 1,\n"
                        + "  \"modelName\": \"string\",\n"
                        + "  \"manufacturer\": \"string\",\n"
                        + "  \"price\": 0.1,\n"
                        + "  \"rating\": 0.2,\n"
                        + "  \"description\": \"string\",\n"
                        + "  \"type\": \"WATER\"\n"
                        + "}")
                .when()
                .put("/cooling")
                .then()
                .statusCode(204);
    }

    // ############### CPU #################
    @Test
    public void cpuResourceTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/cpu").then()
                .statusCode(200)
                .body("msg", equalTo("Cpu endpoint"));
    }

    @Test
    public void getCpuByIdTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/cpu/" + cp.getid()).then()
                .statusCode(200)
                .body("modelName", equalTo("proccessingname"))
                .body("manufacturer", equalTo("process manufact"))
                .body("cores", equalTo(12));
    }

    @Test
    public void getAllCpuTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/cpu/all").then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].modelName", equalTo("proccessingname"))
                .body("[0].manufacturer", equalTo("process manufact"))
                .body("[0].cores", equalTo(12));
    }

    @Test
    public void createCpuTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .body("{\n"
                        + "  \"modelName\": \"string\",\n"
                        + "  \"manufacturer\": \"string\",\n"
                        + "  \"cores\": 0,\n"
                        + "  \"threads\": 0,\n"
                        + "  \"clockSpeed\": 0,\n"
                        + "  \"powerConsumption\": 0,\n"
                        + "  \"description\": \"string\",\n"
                        + "  \"price\": 0.3,\n"
                        + "  \"rating\": 0.1\n"
                        + "}")
                .when()
                .post("/cpu")
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteCpuTest() {
        login("admin", "test");
        Cpu deleteMe = new Cpu("DeleteMe", "raw fish", 23, 12, 1.1, 13, "asdf", 13.1, 12.1);
        cpu.create(deleteMe);
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .delete("/cpu/" + deleteMe.getid())
                .then()
                .statusCode(204);
    }

    @Test
    public void editCpuTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body("{\n"
                        + "  \"id\":1,\n"
                        + "  \"modelName\": \"string\",\n"
                        + "  \"manufacturer\": \"string\",\n"
                        + "  \"cores\": 0,\n"
                        + "  \"threads\": 0,\n"
                        + "  \"clockSpeed\": 0,\n"
                        + "  \"powerConsumption\": 0,\n"
                        + "  \"description\": \"string\",\n"
                        + "  \"price\": 0.2,\n"
                        + "  \"rating\": 0.3\n"
                        + "}")
                .when()
                .put("/cpu")
                .then()
                .statusCode(204);
    }

    // ############ DISK DRIVE #############
    @Test
    public void diskdriveResourceTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/diskdrive").then()
                .statusCode(200)
                .body("msg", equalTo("Disk Drive endpoint"));
    }

    @Test
    public void getDiskdriveByIdTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/diskdrive/" + dd.getid()).then()
                .statusCode(200)
                .body("modelName", equalTo("DDname"))
                .body("manufacturer", equalTo("DDManu"))
                .body("price", equalTo(444.4f));
    }

    @Test
    public void getAllDiskdriveTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/diskdrive/all").then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].modelName", equalTo("DDname"))
                .body("[0].manufacturer", equalTo("DDManu"))
                .body("[0].price", equalTo(444.4f));
    }

    @Test
    public void createDiskdriveTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .body("{\n"
                        + "  \"modelName\": \"asdf\",\n"
                        + "  \"manufacturer\": \"asdf\",\n"
                        + "  \"price\": 1.1,\n"
                        + "  \"capacity\": 123,\n"
                        + "  \"type\": \"SSD\",\n"
                        + "  \"description\": \"asdfasdf\",\n"
                        + "  \"rating\": 1.1\n"
                        + "}")
                .when()
                .post("/diskdrive")
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteDiskdriveTest() {
        login("admin", "test");
        DiskDrive dd = new DiskDrive("asdf", "asdfasdfop", 1.2, 3242, "SSD", "posakssdf", 1.1);
        didr.create(dd);
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .delete("/diskdrive/" + dd.getid())
                .then()
                .statusCode(204);
    }

    @Test
    public void editDiskdriveTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body("{\n"
                        + "  \"id\": 1,\n"
                        + "  \"modelName\": \"asdfasdf\",\n"
                        + "  \"manufacturer\": \"oijasdf\",\n"
                        + "  \"price\": 2.2,\n"
                        + "  \"capacity\": 123,\n"
                        + "  \"type\": \"SSD\",\n"
                        + "  \"description\": \"oiaisovj\",\n"
                        + "  \"rating\": 1.2\n"
                        + "}")
                .when()
                .put("/diskdrive")
                .then()
                .statusCode(204);
    }

    // ############# GPU ###############
    @Test
    public void gpuResourceTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/gpu").then()
                .statusCode(200)
                .body("msg", equalTo("Gpu endpoint"));
    }

    @Test
    public void getGpuByIdTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/gpu/" + gp.getid()).then()
                .statusCode(200)
                .body("modelName", equalTo("modelname"))
                .body("manufacturer", equalTo("manu"))
                .body("price", equalTo(400.0f));
    }

    @Test
    public void getAllGpuTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/gpu/all").then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].modelName", equalTo("modelname"))
                .body("[0].manufacturer", equalTo("manu"))
                .body("[0].price", equalTo(400.0f));
    }

    @Test
    public void createGpuTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .body("{\n"
                        + "  \"modelName\": \"string\",\n"
                        + "  \"manufacturer\": \"string\",\n"
                        + "  \"price\": 2.1,\n"
                        + "  \"memory\": 123,\n"
                        + "  \"baseClockSpeed\": 123.2,\n"
                        + "  \"boostClockSpeed\": 124.3,\n"
                        + "  \"powerConsumption\": 125,\n"
                        + "  \"description\": \"caake\",\n"
                        + "  \"rating\": 1.2\n"
                        + "}")
                .when()
                .post("/gpu")
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteGpuTest() {
        login("admin", "test");
        Gpu deleteMe = new Gpu("asdfasdf", "adsfasdv", 1.2, 124.1, 1231, 31.1, 21.1, 1231, "aspodkf");
        gpu.create(deleteMe);
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .delete("/gpu/" + deleteMe.getid())
                .then()
                .statusCode(204);
    }

    @Test
    public void editGpuTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body("{\n"
                        + "  \"id\": 1,\n"
                        + "  \"modelName\": \"posakdc\",\n"
                        + "  \"manufacturer\": \"aoijsadf\",\n"
                        + "  \"price\": 12.2,\n"
                        + "  \"memory\": 123,\n"
                        + "  \"baseClockSpeed\": 12.1,\n"
                        + "  \"boostClockSpeed\": 14.1,\n"
                        + "  \"powerConsumption\": 123,\n"
                        + "  \"description\": \"asdfsad\",\n"
                        + "  \"rating\": 12.1\n"
                        + "}")
                .when()
                .put("/gpu")
                .then()
                .statusCode(204);
    }

    // ########### MOTHERBOARD ############
    @Test
    public void motherboardResourceTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/motherboard").then()
                .statusCode(200)
                .body("msg", equalTo("Motherboard endpoint"));
    }

    @Test
    public void getMotherboardByIdTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/motherboard/" + mb.getid()).then()
                .statusCode(200)
                .body("modelName", equalTo("motherbname"))
                .body("manufacturer", equalTo("MotherMan"))
                .body("price", equalTo(50.0f));
    }

    @Test
    public void getAllMotherboardTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/motherboard/all").then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].modelName", equalTo("motherbname"))
                .body("[0].manufacturer", equalTo("MotherMan"))
                .body("[0].price", equalTo(50.0f));
    }

    @Test
    public void createMotherboardTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .body("{\n"
                        + "  \"modelName\": \"string\",\n"
                        + "  \"manufacturer\": \"string\",\n"
                        + "  \"price\": 12.1,\n"
                        + "  \"networkInterfaceController\": \"string\",\n"
                        + "  \"description\": \"string\",\n"
                        + "  \"rating\": 12.1,\n"
                        + "  \"HDMISlots\": 0,\n"
                        + "  \"USBSlots\": 0,\n"
                        + "  \"RAMSlots\": 0,\n"
                        + "  \"SATASlots\": 0,\n"
                        + "  \"M2Slots\": 0\n"
                        + "}")
                .when()
                .post("/motherboard")
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteMotherboardTest() {
        login("admin", "test");
        Motherboard deleteMe = new Motherboard("pasodkf", "asdf", 12.1, 123, 123, 124, "asdf", "asdf", 123, 124, 1.1);
        mother.create(deleteMe);
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .delete("/motherboard/" + deleteMe.getid())
                .then()
                .statusCode(204);
    }

    @Test
    public void editMotherboardTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body("{\n"
                        + " \"id\": 1,\n"
                        + "  \"modelName\": \"string\",\n"
                        + "  \"manufacturer\": \"string\",\n"
                        + "  \"price\": 12.1,\n"
                        + "  \"networkInterfaceController\": \"string\",\n"
                        + "  \"description\": \"string\",\n"
                        + "  \"rating\": 12.1,\n"
                        + "  \"HDMISlots\": 0,\n"
                        + "  \"USBSlots\": 0,\n"
                        + "  \"RAMSlots\": 0,\n"
                        + "  \"SATASlots\": 0,\n"
                        + "  \"M2Slots\": 0\n"
                        + "}")
                .when()
                .put("/motherboard")
                .then()
                .statusCode(204);
    }

    // ########### PSU ############
    @Test
    public void psuResourceTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/psu").then()
                .statusCode(200)
                .body("msg", equalTo("Psu endpoint"));
    }

    @Test
    public void getPsuByIdTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/psu/" + ps.getid()).then()
                .statusCode(200)
                .body("modelName", equalTo("Psuname"))
                .body("manufacturer", equalTo("PsuManu"))
                .body("price", equalTo(50.0f));
    }

    @Disabled
    @Test
    public void getAllPsuTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/psu/all").then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].modelName", equalTo("Psuname"))
                .body("[0].manufacturer", equalTo("PsuManu"))
                .body("[0].price", equalTo(50.0f));
    }

    @Test
    public void createPsuTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .body("{\n"
                        + "  \"modelName\": \"asdf\",\n"
                        + "  \"manufacturer\": \"asv\",\n"
                        + "  \"price\": 12.1,\n"
                        + "  \"watts\": 123,\n"
                        + "  \"description\": \"asdf\",\n"
                        + "  \"rating\": 12.1\n"
                        + "}")
                .when()
                .post("/psu")
                .then()
                .statusCode(204);
    }

    @Test
    public void deletePsuTest() {
        login("admin", "test");
        Psu deleteMe = new Psu("asdfasdf", "asdfasdf", 12.1, 12123, "asdf", 234.1);
        gpu.create(deleteMe);
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .delete("/psu/" + deleteMe.getid())
                .then()
                .statusCode(204);
    }

    @Test
    public void editPsuTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body("{\n"
                        + "  \"id\": 1,\n"
                        + "  \"modelName\": \"asdasd\",\n"
                        + "  \"manufacturer\": \"ascasc\",\n"
                        + "  \"price\": 12.1,\n"
                        + "  \"watts\": 123,\n"
                        + "  \"description\": \"ASDF\",\n"
                        + "  \"rating\": 12.1\n"
                        + "}")
                .when()
                .put("/psu")
                .then()
                .statusCode(204);
    }

    // ########## RAM ###########
    @Test
    public void ramResourceTest() {
        given()
                .contentType("application/json")
                .when()
                .get("/ram").then()
                .statusCode(200)
                .body("msg", equalTo("Ram endpoint"));
    }

    @Test
    public void getRamByIdTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/ram/" + rm.getid()).then()
                .statusCode(200)
                .body("modelName", equalTo("RamName"))
                .body("manufacturer", equalTo("RamManu"))
                .body("price", equalTo(150.0f));
    }

    @Test
    public void getAllRamTest() {
        login("user", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/ram/all").then()
                .statusCode(200)
                .body("size", is(1))
                .body("[0].modelName", equalTo("RamName"))
                .body("[0].manufacturer", equalTo("RamManu"))
                .body("[0].price", equalTo(150.0f));
    }

    @Test
    public void createRamTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .body("{\n"
                        + "  \"modelName\": \"asdfac\",\n"
                        + "  \"manufacturer\": \"asdcasd\",\n"
                        + "  \"price\": 123.1,\n"
                        + "  \"capacity\": 12,\n"
                        + "  \"speed\": 123.1,\n"
                        + "  \"description\": \"monsterCake\",\n"
                        + "  \"rating\": 13.1\n"
                        + "}")
                .when()
                .post("/ram")
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteRamTest() {
        login("admin", "test");
        Ram deleteMe = new Ram("asdfasdf", "asdfasdf", 123.1, 123, 123.1, "DSSAFASDF", 123.1);
        ram.create(deleteMe);
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .delete("/ram/" + deleteMe.getid())
                .then()
                .statusCode(204);
    }

    @Test
    public void editRamTest() {
        login("admin", "test");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body("{\n"
                        + "  \"id\": 1,\n"
                        + "  \"modelName\": \"asdf\",\n"
                        + "  \"manufacturer\": \"asdasd\",\n"
                        + "  \"price\": 12.1,\n"
                        + "  \"capacity\": 123,\n"
                        + "  \"speed\": 12.1,\n"
                        + "  \"description\": \"apsodjf\",\n"
                        + "  \"rating\": 12.1\n"
                        + "}")
                .when()
                .put("/ram")
                .then()
                .statusCode(204);
    }

}

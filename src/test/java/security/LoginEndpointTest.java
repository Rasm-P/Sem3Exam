/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import rest.ApplicationConfig;
import utils.EMF_Creator;

/**
 *
 * @author Rasmus2
 */
//@Disabled
public class LoginEndpointTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

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

    @Test
    public void testLoginAdmin() throws Exception {
        given()
                .contentType("application/json")
                .body("{\"username\":\"admin\", \"password\":\"test\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("username", equalTo("admin"));
    }

    @Test
    public void testLoginUser() throws Exception {
        given()
                .contentType("application/json")
                .body("{\"username\":\"user\", \"password\":\"test\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("username", equalTo("user"));
    }

    @Test
    public void testNoLogin1() throws Exception {
        given()
                .contentType("application/json")
                .body("{\"username\":\"user123\", \"password\":\"te123123st\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(403)
                .body("message", equalTo("Invalid user name or password"));
    }

    @Test
    public void testNoLogin2() throws Exception {
        given()
                .contentType("application/json")
                .body("{\"username\":\"user\", \"password\":\"te123123st\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(403)
                .body("message", equalTo("Invalid user name or password"));
    }

    @Test
    public void testNoLogin3() throws Exception {
        given()
                .contentType("application/json")
                .body("{\"username\":\"user123\", \"password\":\"test\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(403)
                .body("message", equalTo("Invalid user name or password"));
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Software;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Lukas Bjornvad
 */
public class SoftwareFacadeIT {

    public SoftwareFacadeIT() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    // all TESTs are subject to change as the api it pulls from is still being developed
    /**
     * Test of getAllSoftware method, of class SoftwareFacade.
     */
    @Test
    public void testGetAllSoftware() throws Exception {
        System.out.println("getAllSoftware");
        SoftwareFacade instance = new SoftwareFacade();
        Software[] results = instance.getAllSoftware();
        assertEquals("Visual Studio Code", results[0].getTitle());
    }

    /**
     * Test of getSoftwareById method, of class SoftwareFacade.
     */
    @Test
    public void testGetSoftwareById() throws Exception {
        System.out.println("getSoftwareById");
        SoftwareFacade instance = new SoftwareFacade();
        Software result = instance.getSoftwareById(5);
        assertEquals("Notepad++", result.getTitle());
    }

}

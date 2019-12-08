/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Software;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.persistence.EntityManagerFactory;
import utils.DataFetcher;
import utils.EMF_Creator;

/**
 *
 * @author Lukas Bjornvad
 */
public class SoftwareFacade {

    private static SoftwareFacade instance;
    private static ExecutorService ES;
    private static Queue<Future<Object>> futures;

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);

    //Private Constructor to ensure Singleton
    public SoftwareFacade() {
    }

    public static SoftwareFacade getFacade() {
        if (instance == null) {
            instance = new SoftwareFacade();
        }
        return instance;
    }

    public Software[] getAllSoftware() throws IOException {
        String url = "https://williamhuusfeldt.dk/softwarezoid/api/software/all";
        Software[] x = DataFetcher.fetchAsObject(Software[].class, url);
        return x;
    }

    public Software getSoftwareById(int id) throws IOException {
        String url = "https://williamhuusfeldt.dk/softwarezoid/api/software/" + id;
        Software x = DataFetcher.fetchAsObject(Software.class, url);
        return x;
    }
}

package io.perfecto.tests.utilities.perfectotokenstorage;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.perfecto.utilities.tokenstorage.*;

public class PerfectoTokenStorageTests {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testGetTokenForCloudString_with_StaticStograge() {
        try {
            assert(PerfectoTokenStorage.getTokenForCloud("dummy").equals("dummy"));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        try {
            assert(new PerfectoTokenStorage("/Users/hgenev/tokens.ini").getTokenForCloud("dummy").equals("dummy"));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetTokenForCloud_with_TokenStorageInitialization() {
        try {
            PerfectoTokenStorage storage = new PerfectoTokenStorage(System.getenv("PERFECTO_TOKEN_STORAGE"));
            assert(storage.getTokenForCloud("dummy").equals("dummy"));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}

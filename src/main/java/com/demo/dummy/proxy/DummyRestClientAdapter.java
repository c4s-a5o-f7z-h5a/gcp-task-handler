package com.demo.dummy.proxy;


import com.demo.dummy.config.DummyRestClient;
import com.demo.dummy.proxy.request.DummyRequest;
import com.demo.dummy.proxy.response.DummyResponse;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class DummyRestClientAdapter {

    private static final Logger logger = Logger.getLogger(DummyRestClientAdapter.class.getName());

    private final DummyRestClient dummyRestClient;

    @Autowired
    public DummyRestClientAdapter(DummyRestClient dummyRestClient) {
        this.dummyRestClient = dummyRestClient;
    }

    public void callToDummy(String name) {
        String dummyId = "f91238c8-9ef5-434b-b58c-3310d290cad6";
        DummyRequest request = new DummyRequest();
        request.setDummyId(dummyId);
        request.setStatus("ACTIVE");
        this.perform(dummyRestClient.createSession(dummyId, request));
    }

    private void perform(Call<DummyResponse> dummyResponseCall) {
        try {
            Response<DummyResponse> dummyResponseResponse = dummyResponseCall.execute();
            logger.info("response from dummy " + dummyResponseResponse);
            if (!dummyResponseResponse.isSuccessful()) {
                logger.info("Error executing call to Dummy Api");
                ResponseBody responseBody = dummyResponseResponse.errorBody();
                String errorBody = responseBody.string();
                String errorCode = String.valueOf(dummyResponseResponse.code());
                throw new RuntimeException("Error code " + errorCode + ", error body = " + errorBody);
            }
        } catch (IOException e) {
            logger.info("Serialization error, " + e);
            throw new RuntimeException("Serialization error");
        }
    }

}

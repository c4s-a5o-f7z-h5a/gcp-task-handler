package com.demo.dummy.config;

import com.demo.dummy.proxy.request.DummyRequest;
import com.demo.dummy.proxy.response.DummyResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DummyRestClient {

    @Headers("Content-Type: application/json")
    @PUT("/v1/dummy/{dummyId}")
    Call<DummyResponse> createSession(
            @Path("dummyId") String dummy,
            @Body DummyRequest createSession
    );

}

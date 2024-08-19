package app.rafit.madatour.api;

import java.util.List;

import app.rafit.madatour.model.TouristSite;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/api/attractions")
    Call<List<TouristSite>> getTouristSite();

    @GET("/api/attractions/{id}")
    Call<TouristSite> getTouristSiteById(@Path("id") String itemId);


    // @POST("path/to/endpoint")
    // Call<ResponseType> postEndpoint(@Body RequestType request);
    // 0335809194
}

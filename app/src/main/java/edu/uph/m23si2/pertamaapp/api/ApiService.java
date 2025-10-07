package edu.uph.m23si2.pertamaapp.api;

import edu.uph.m23si2.pertamaapp.model.Pasien;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("api/provinces.json")
    Call<ApiResponse> getProvinsi();

    @GET("api/regencies/{provinceCode}.json")
    Call<ApiResponseKabupaten> getKabupaten(@retrofit2.http.Path("provinceCode") String provinceCode);

    @POST("pasien")
    Call<ApiResponsePasien> createPasien(@Body Pasien pasien);

}

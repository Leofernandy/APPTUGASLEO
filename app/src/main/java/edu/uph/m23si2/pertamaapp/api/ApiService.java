package edu.uph.m23si2.pertamaapp.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/provinces.json")
    Call<ApiResponse> getProvinsi();

    @GET("api/regencies/{provinceCode}.json")
    Call<ApiResponseKabupaten> getKabupaten(@retrofit2.http.Path("provinceCode") String provinceCode);

}

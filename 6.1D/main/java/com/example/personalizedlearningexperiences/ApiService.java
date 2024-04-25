package com.example.personalizedlearningexperiences;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("endpoint")
    Call<List<Question>> getQuestions(); // Define your response model
}

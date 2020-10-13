package com.example.project1;

import android.content.Context;

import com.apollographql.apollo.ApolloClient;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiClient {

    private static ApolloClient apolloClient;

    private static String BASE_URL = "http://167.71.232.133/graphql";




    //curl --location --request POST 'http://167.71.232.133/graphql' \
    //--header 'Content-Type: application/json' \
    //--data-raw '{"query":"query FoodSearchQuery {  foodmenuSearch(searchCriteria: null)
    // {    __typename    id    description    activeFlag    effOn    name    nonVegFlag    version    diabeticFlag  }}",
    // "variables":{}}'



    public static ApolloClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);


        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    //builder.header("User-Agent", "Android Apollo Client");
                    builder.header("Content-Type", "application/json");
                    //builder.header("X-Shopify-Storefront-Access-Token", shopifyApiKey);
                    return chain.proceed(builder.build());
                })
                .build();


        if (apolloClient == null) {
            apolloClient = ApolloClient.builder()
                    .serverUrl(BASE_URL)
                    .okHttpClient(okHttpClient)
                    .build();
        }

        return apolloClient;
    }

    public static ApolloClient getClientSampleHeader(final String gdcToken) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                           // .header("gdc-token", gdcToken)
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }).build();

        if (apolloClient == null) {
            apolloClient = ApolloClient.builder()
                    .serverUrl(BASE_URL)
                    .okHttpClient(client)
                    .build();
        }

        return apolloClient;
    }

}
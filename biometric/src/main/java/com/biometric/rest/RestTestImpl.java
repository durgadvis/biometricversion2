package com.biometric.rest;

import com.biometric.forms.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import mmm.cogent.fpCaptureApi.MMMCogentCSD200DeviceImpl;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * Created by suvp on 12/6/2016.
 */
@RestController
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class RestTestImpl {

    @Autowired
    DataSource dataSource;

    @GET
    @Path("/device")
    public JSONObject getValue(){
        MMMCogentCSD200DeviceImpl lDevice = new MMMCogentCSD200DeviceImpl();
        JSONObject $JsonObject = new JSONObject();
        if(lDevice.isDeviceConnected()){
            lDevice.initDevice();
            $JsonObject.put("Connection Status", "Connected");
        }
        else{
            $JsonObject.put("Connection Status", "Not Connected");
        }
        return $JsonObject;
    }

    @GET
    @Path("/mysql")
    public JSONObject isSqlConnected(){
        JSONObject $JsonObject = new JSONObject();
        try {
            dataSource.getConnection();
            DriverManagerDataSource lDbDriver  = ((DriverManagerDataSource)dataSource);
            $JsonObject.put("Connection Status", "Connected");
            $JsonObject.put("URl", lDbDriver.getUrl());
            $JsonObject.put("UserName", lDbDriver.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
            $JsonObject.put("Connection Status", "Not Connected");
        }

        return $JsonObject;
    }
}

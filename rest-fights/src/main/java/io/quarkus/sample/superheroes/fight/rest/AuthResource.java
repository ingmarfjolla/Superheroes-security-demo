package io.quarkus.sample.superheroes.fight.rest;



import io.quarkus.sample.superheroes.fight.client.AuthRestClient;
import io.quarkus.sample.superheroes.fight.client.WebAuthnRegisterResponse;

import io.smallrye.mutiny.Uni;

import io.vertx.core.json.JsonObject;


import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import static jakarta.ws.rs.core.MediaType.*;

@Path("/")
public class AuthResource {
  private static final Logger LOG = Logger.getLogger(AuthResource.class);
  @RestClient
  AuthRestClient authClient;

  @Path("/q/webauthn/webauthn.js")
  @GET
  public Uni<Response> getJavascript(){
    LOG.info("Returning authservice javascript......");
    return authClient.javascript();
  }
  @POST
  @Path("/q/webauthn/register")
  @Consumes(APPLICATION_JSON)
  public Uni<Response> setUpObtainRegistrationChallenge(JsonObject jsonObject){
    LOG.info("Returning registration challenge....");
    LOG.info(jsonObject);
    return authClient.setUpObtainRegistrationChallenge(jsonObject);
  }
  @POST
  @Path("/register")
  public Uni<Response> register( @FormParam("userName") String userName,@FormParam("plan") String plan,@BeanParam WebAuthnRegisterResponse webAuthnResponse){
    return authClient.register(userName,plan,webAuthnResponse);
  }

  @POST
  @Path("/q/webauthn/login")
  @Consumes(APPLICATION_JSON)
  public Uni<Response> setUpObtainLoginChallenge(JsonObject jsonObject) {
    return authClient.setUpObtainLoginChallenge(jsonObject);
  }

  @POST
  @Path("/q/webauthn/callback")
  public Uni<Response> callback(JsonObject jsonObject) {
    return authClient.callback(jsonObject);
  }

  @GET
  @Path("/q/webauthn/logout")
  public Uni<Response> logout() {
    return authClient.logout();
  }


  @GET
  @Path("/verify-session")
  public Uni<Response> checkCookie(){ return authClient.verify();}

}

package com.felix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class DbService extends AbstractVerticle {


        /**
         * Users
         *      -Add user(s) to database
         *      -Fetch a user from the database
         *      -Fetch a list of users from the database
         */

    private MongoClient dbClient;
    private final Logger Logger = LoggerFactory.getLogger(DbService.class);

    private static final String USERS_TABLE = "user";

    @Override
    public void start() {

        new JsonObject()
        .put("db_name", "Promtech")
        .put("connectionn-string", "Mongo://localhost:27017");


        this.dbClient = MongoClient.create(this.vertx, config());

        Router r = Router.router(this.vertx);
        r.get("/").handler(this::ping);
        r.post("/createuser").handler(this::createUser);
        r.post("/getuser").handler(this::getUser);
        r.get("/listusers").handler(this::listUsers);

        this.vertx.createHttpServer()
            .requestHandler(r)
            .listen(8080, res->{
                if(res.succeeded()){
                    Logger.info("HTTP server running on port 8080");
                }else{
                    Logger.info("Could not start HTTP server", res.cause());
                }
            });

    }

    private void listUsers(final RoutingContext rc){
        JsonObject qry = new JsonObject();
        this.dbClient.find(USERS_TABLE, qry, res ->{
            if (res.succeeded()){
                JsonArray rst = new JsonArray(res.result());
                rc.response().end(rst.encode());


            }else{
                JsonObject err = new JsonObject()
                .put("error", rc.response().end(res.cause().getMessage()));

            rc.response().end(err.encode());
            }

        });

    }

    private void getUser(final RoutingContext rc) {
        rc.request().bodyHandler(b ->{
            JsonObject body = b.toJsonObject();
            dbClient.findOne(USERS_TABLE, body,
                 new JsonObject(), res -> {
                     if (res.succeeded()){
                         rc.response().end(res.result().encode());
                         Logger.info(body+"found");
                     }else {
                         
                        JsonObject err = new JsonObject()
                        .put("body", body)
                        .put("error", rc.response().end(res.cause().getMessage()));

                    rc.response().end(err.encode());
                     
                    }
                 });

        });
    }

    private void ping(final RoutingContext rc){
        rc.response().end("Welcome to PromTech");
    }

    private void createUser(final RoutingContext rc){
        rc.request().bodyHandler(b ->{
            JsonObject body = b.toJsonObject();
            dbClient.insert(USERS_TABLE, body, res ->{
                if(res.succeeded()){
                    rc.response().end(body.encode());
                    Logger.info(body+"created");
                }else{
                    JsonObject err = new JsonObject()
                        .put("body", body)
                        .put("error", rc.response().end(res.cause().getMessage()));
                    Logger.error("account not created");
                    rc.response().end(err.encode());


                }

            });


        });


    }

}

package specs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Spec {
    private static final Logger logger = LoggerFactory.getLogger(Spec.class);
    public static RequestSpecification reqSpec(String url, String path) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setBasePath(path)
                //.log(LogDetail.ALL)
                .build();
    }
    public static ResponseSpecification respSpec () {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                //.log(LogDetail.ALL)
                .build();
    }
    public static void InstallSpec (RequestSpecification request, ResponseSpecification responce) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = responce;
    }
}

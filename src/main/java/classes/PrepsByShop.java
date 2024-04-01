package classes;

import classes.adapter.PrepsInShop;
import classes.adapter.RequestPrepsInShop;
import interfaces.IPrepShop;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import specs.Spec;

import java.util.List;
import java.util.Set;

import static io.restassured.http.ContentType.JSON;

public class PrepsByShop extends Spec implements IPrepShop {
    private List<PrepsInShop> apiAvailable;

    public PrepsByShop(Set<RequestPrepsInShop> requestBody) {
        final String URL_BASE = "https://api.geoapteka.com.ua/get_prop";
        //initiate a constructor
        InstallSpec(reqSpec(URL_BASE, ""), respSpec());

        //request to Axioma
        this.apiAvailable = RestAssured
                .given()
                .header("Connection", "keep-alive")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip")
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .extract().response().as(new TypeRef<List<PrepsInShop>>() {});
    }
    @Override
    public List<PrepsInShop> getApiAvailable() {
        return apiAvailable;
    }

    public PrepsInShop getPrepByShopAndId(String idShop, String idDrug) {
        return this.apiAvailable.stream()
                .filter(api -> api.getId_shop().equalsIgnoreCase(idShop) && api.getId_drug().equalsIgnoreCase(idDrug))
                .findFirst().orElse(new PrepsInShop(idDrug, "0", idShop, "0", 0.0, 0.0, 0, 0, ""));
    }
}

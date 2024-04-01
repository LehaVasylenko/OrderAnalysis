package classes;

import classes.adapter.DrugInfo;
import interfaces.IDrugInfo;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import specs.Spec;

import java.util.List;
import java.util.Objects;

import static io.restassured.http.ContentType.JSON;

public class DrugsInfoMany extends Spec implements IDrugInfo {
    private static final Logger logger = LoggerFactory.getLogger(DrugsInfoMany.class);
    private List<DrugInfo> drugs;

    public DrugsInfoMany (List<String> idDrugs) {
        //initiate a path
        final String URL_BASE = "https://api.apteki.ua/get_item_many";

        //initiate a constructor
        InstallSpec(reqSpec(URL_BASE,""), respSpec());

        //request to api.apteki
        this.drugs = RestAssured
                .given()
                .header("Connection","keep-alive")
                .header("Accept","*/*")
                .header("Accept-Encoding","gzip")
                .contentType(JSON)
                .body(idDrugs)
                .when()
                .post()
                .then()
                .extract().response().as(new TypeRef<List<DrugInfo>>() {});
    }

    @Override
    public String getDrugData(String idDrug) {
        DrugInfo drugInfo = this.drugs
                .stream()
                .filter(Objects::nonNull)
                .filter(drug -> drug.getId().equalsIgnoreCase(idDrug))
                .findFirst()
                .orElse(new DrugInfo(idDrug,"","","","Unknown prep","","","","","","","","","","","","","","","","","","","","",0.0,0));

        String info = drugInfo.getName() + " " + drugInfo.getDose() + " " + drugInfo.getForm() + " " + drugInfo.getPack();
        info += " " + drugInfo.getPack_note() + ", №" + drugInfo.getNumb() + ", " + drugInfo.getMake();

        return info;
    }
}

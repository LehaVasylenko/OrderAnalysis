package classes.adapter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DrugInfo {
    private String id = "";
    private String id_make = "";
    private String id_info = "";
    private String id_info_ua = "";
    private String name = "";
    private String name_ua = "";
    private String form = "";
    private String form_ua = "";
    private String dose = "";
    private String dose_ua = "";
    private String pack = "";
    private String pack_ua = "";
    private String pack_name = "";
    private String pack_name_ua = "";
    private String pack_size = "";
    private String pack_unit = "";
    private String pack_unit_ua = "";
    private String pack_note = "";
    private String pack_note_ua = "";
    private String note = "";
    private String note_ua = "";
    private String numb = "";
    private String make = "";
    private String make_ua = "";
    private String pict = "";
    private double avgp;
    private int sale;

    public DrugInfo(String id, String id_make, String id_info, String id_info_ua, String name, String name_ua, String form, String form_ua, String dose, String dose_ua, String pack, String pack_ua, String pack_name, String pack_name_ua, String pack_size, String pack_unit, String pack_unit_ua, String pack_note, String pack_note_ua, String note, String note_ua, String numb, String make, String make_ua, String pict, double avgp, int sale) {
        this.id = id;
        this.id_make = id_make;
        this.id_info = id_info;
        this.id_info_ua = id_info_ua;
        this.name = name;
        this.name_ua = name_ua;
        this.form = form;
        this.form_ua = form_ua;
        this.dose = dose;
        this.dose_ua = dose_ua;
        this.pack = pack;
        this.pack_ua = pack_ua;
        this.pack_name = pack_name;
        this.pack_name_ua = pack_name_ua;
        this.pack_size = pack_size;
        this.pack_unit = pack_unit;
        this.pack_unit_ua = pack_unit_ua;
        this.pack_note = pack_note;
        this.pack_note_ua = pack_note_ua;
        this.note = note;
        this.note_ua = note_ua;
        this.numb = numb;
        this.make = make;
        this.make_ua = make_ua;
        this.pict = pict;
        this.avgp = avgp;
        this.sale = sale;
    }

    public String getId() {
        return id;
    }

    public String getPack_unit_ua() {
        return pack_unit_ua;
    }

    public void setPack_unit_ua(String pack_unit_ua) {
        this.pack_unit_ua = pack_unit_ua;
    }

    public String getPack_note_ua() {
        return pack_note_ua;
    }

    public void setPack_note_ua(String pack_note_ua) {
        this.pack_note_ua = pack_note_ua;
    }

    public String getNote_ua() {
        return note_ua;
    }

    public void setNote_ua(String note_ua) {
        this.note_ua = note_ua;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_make() {
        return id_make;
    }

    public void setId_make(String id_make) {
        this.id_make = id_make;
    }

    public String getId_info() {
        return id_info;
    }

    public void setId_info(String id_info) {
        this.id_info = id_info;
    }

    public String getId_info_ua() {
        return id_info_ua;
    }

    public void setId_info_ua(String id_info_ua) {
        this.id_info_ua = id_info_ua;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_ua() {
        return name_ua;
    }

    public void setName_ua(String name_ua) {
        this.name_ua = name_ua;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getForm_ua() {
        return form_ua;
    }

    public void setForm_ua(String form_ua) {
        this.form_ua = form_ua;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDose_ua() {
        return dose_ua;
    }

    public void setDose_ua(String dose_ua) {
        this.dose_ua = dose_ua;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getPack_ua() {
        return pack_ua;
    }

    public void setPack_ua(String pack_ua) {
        this.pack_ua = pack_ua;
    }

    public String getPack_name() {
        return pack_name;
    }

    public void setPack_name(String pack_name) {
        this.pack_name = pack_name;
    }

    public String getPack_name_ua() {
        return pack_name_ua;
    }

    public void setPack_name_ua(String pack_name_ua) {
        this.pack_name_ua = pack_name_ua;
    }

    public String getPack_size() {
        return pack_size;
    }

    public void setPack_size(String pack_size) {
        this.pack_size = pack_size;
    }

    public String getPack_unit() {
        return pack_unit;
    }

    public void setPack_unit(String pack_unit) {
        this.pack_unit = pack_unit;
    }

    public String getPack_note() {
        return pack_note;
    }

    public void setPack_note(String pack_note) {
        this.pack_note = pack_note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNumb() {
        return numb;
    }

    public void setNumb(String numb) {
        this.numb = numb;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getMake_ua() {
        return make_ua;
    }

    public void setMake_ua(String make_ua) {
        this.make_ua = make_ua;
    }

    public String getPict() {
        return pict;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }

    public double getAvgp() {
        return avgp;
    }

    public void setAvgp(double avgp) {
        this.avgp = avgp;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "DrugInfo{" +
                "id='" + id + '\'' +
                ", id_make='" + id_make + '\'' +
                ", id_info='" + id_info + '\'' +
                ", id_info_ua='" + id_info_ua + '\'' +
                ", name='" + name + '\'' +
                ", name_ua='" + name_ua + '\'' +
                ", form='" + form + '\'' +
                ", form_ua='" + form_ua + '\'' +
                ", dose='" + dose + '\'' +
                ", dose_ua='" + dose_ua + '\'' +
                ", pack='" + pack + '\'' +
                ", pack_ua='" + pack_ua + '\'' +
                ", pack_name='" + pack_name + '\'' +
                ", pack_name_ua='" + pack_name_ua + '\'' +
                ", pack_size='" + pack_size + '\'' +
                ", pack_unit='" + pack_unit + '\'' +
                ", pack_unit_ua='" + pack_unit_ua + '\'' +
                ", pack_note='" + pack_note + '\'' +
                ", pack_note_ua='" + pack_note_ua + '\'' +
                ", note='" + note + '\'' +
                ", note_ua='" + note_ua + '\'' +
                ", numb='" + numb + '\'' +
                ", make='" + make + '\'' +
                ", make_ua='" + make_ua + '\'' +
                ", pict='" + pict + '\'' +
                ", avgp=" + avgp +
                ", sale=" + sale +
                "}\n";
    }

    public DrugInfo() {
    }
}

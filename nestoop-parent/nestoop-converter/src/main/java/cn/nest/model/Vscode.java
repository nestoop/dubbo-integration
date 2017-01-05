package cn.nest.model;

import java.io.Serializable;

/**
 * Created by perk
 *
 * @DATE 2017/1/4
 * @DESC
 */
public class Vscode implements Serializable {

    private String visaCode;

    private String visaName;

    public String getVisaCode() {
        return visaCode;
    }

    public void setVisaCode(String visaCode) {
        this.visaCode = visaCode;
    }

    public String getVisaName() {
        return visaName;
    }

    public void setVisaName(String visaName) {
        this.visaName = visaName;
    }
}

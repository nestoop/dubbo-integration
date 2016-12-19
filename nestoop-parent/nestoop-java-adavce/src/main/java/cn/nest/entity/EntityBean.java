package cn.nest.entity;

import cn.nest.annotation.BeanTransfer;
import cn.nest.annotation.ColumTransfer;

/**
 * Created by perk on 2016/12/16.
 */
@BeanTransfer(type = "JSON", desc = "Bean Transfer JSON")
public class EntityBean {

    @ColumTransfer(value = "visa_code")
    private String visaCode;

    @ColumTransfer(value = "visa_name")
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

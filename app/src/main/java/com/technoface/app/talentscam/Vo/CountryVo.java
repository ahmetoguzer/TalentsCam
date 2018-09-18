package com.technoface.app.talentscam.Vo;

import java.io.Serializable;

/**
 * Created by Ahmet Oguzer on 15.01.2018.
 */

public class CountryVo implements Serializable {
    private String CountryName;
    private String CountryCode;

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }
}

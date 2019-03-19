package org.prime.internship.entity;

import java.io.Serializable;

public class City  implements Serializable {
    private static final long serialVersionUID = -7573825277866052905L;
    private int cityId;
    private String name;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

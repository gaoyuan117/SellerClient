package com.kaichaohulian.baocms.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyu on 2016/12/21.
 */

public class Province {
    private List<City> cities = new ArrayList<City>();

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}

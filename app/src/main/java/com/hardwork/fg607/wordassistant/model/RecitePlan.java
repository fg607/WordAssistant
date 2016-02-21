package com.hardwork.fg607.wordassistant.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

/**
 * Created by fg607 on 16-1-12.
 */
@Table
public class RecitePlan extends SugarRecord{

    @Unique
    String name;
    String range;
    String number;

    public RecitePlan(){

    }

    public RecitePlan(String name, String range,String number) {

        this.name = name;
        this.range = range;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

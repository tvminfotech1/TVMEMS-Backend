package com.tvm.internal.tvm_internal_project.model;

import java.util.Map;

public class ChartData {
    private Map<String, Object> pieData;
    private Map<String, Object> barData;

    public Map<String, Object> getPieData() {
        return pieData;
    }

    public void setPieData(Map<String, Object> pieData) {
        this.pieData = pieData;
    }

    public Map<String, Object> getBarData() {
        return barData;
    }

    public void setBarData(Map<String, Object> barData) {
        this.barData = barData;
    }
}

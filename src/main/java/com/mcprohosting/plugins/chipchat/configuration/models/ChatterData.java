package com.mcprohosting.plugins.chipchat.configuration.models;

import com.mcprohosting.plugins.chipchat.utils.configuration.ConfigObject;

import java.util.ArrayList;

public class ChatterData extends ConfigObject {

    public String name = "";
    public ArrayList<String> joined = new ArrayList<>();
    public ArrayList<String> muted = new ArrayList<>();
    public boolean mutedGlobally = false;

    public ChatterData() {}

    public ChatterData(String name) {
        this.name = name;
    }

}

package com.mcprohosting.plugins.chipchat.configuration.models;

import com.mcprohosting.plugins.chipchat.utils.configuration.ConfigObject;

public class ChannelData extends ConfigObject {

    public String name = "";
    public String password = "";
    public String color = "&2";
    public String format = "&8[%c%%n%&8] &7%p%&8: &7%m%";

    public ChannelData() {}

    public ChannelData(String name) {
        this.name = name;
    }

    public ChannelData(String name, String password) {
        this(name);

        this.password = password;
    }

}

package com.armaan.repgetter;

public class Repo {
    private String username;
    private String avatar;
    private String url;
    private String description;

    public Repo(String username, String avatar, String url, String description) {
        this.username = username;
        this.avatar = avatar;
        this.url = url;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}

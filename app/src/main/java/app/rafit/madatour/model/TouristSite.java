package app.rafit.madatour.model;

import java.util.List;

public class TouristSite {
    String _id;
    private String name;
    private String description;
    private String location;
    private List<String> images;
    private List<String> videos;

    public TouristSite(String id, String name, String description, String location, List<String> images) {
        this._id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.images = images;
    }

    public TouristSite(String _id, String name, String description, String location, List<String> images, List<String> videos) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.images = images;
        this.videos = videos;
    }

    // Constructeur
    public TouristSite(String name, String description, String location, List<String> images) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.images = images;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImages() {
        return images;
    }

    public String getLocation() {
        return location;
    }

    public String getId() {
        return _id;
    }

    public List<String> getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return "TouristSite{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", images=" + images +
                ", videos=" + videos +
                '}';
    }
}

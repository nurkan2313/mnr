package kg.sh.mnr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Breadcrumb {
    private String url;
    private String name;

    public Breadcrumb(String url, String name) {
        this.url = url;
        this.name = name;
    }

}
package lim.one.popovakazakova.domain;

import lim.one.popovakazakova.domain.helper.ISectionHelper;

public class Section {
    private String name;
    private ISectionHelper helper;
    private String type;

    public static final String TYPE_SOUNDS = "sounds";
    public static final String TYPE_PHONETIC_EXERCISES = "phonetic_exercises";

    public Section(String name, ISectionHelper helper, String type) {
        this.name = name;
        this.helper = helper;
        this.type = type;
    }

    public Section(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public ISectionHelper getHelper() {
        return helper;
    }

    public String getName() {
        return name;
    }

    public String getType(){
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }
}

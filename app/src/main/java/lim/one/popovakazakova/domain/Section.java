package lim.one.popovakazakova.domain;

import lim.one.popovakazakova.domain.helper.ISectionHelper;

public class Section {
    private String name;
    private ISectionHelper sectionHelper;

    public Section(String name, ISectionHelper sectionHelper) {
        this.name = name;
        this.sectionHelper = sectionHelper;
    }

    public ISectionHelper getSectionHelper() {
        return sectionHelper;
    }

    public Section(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package lim.one.popovakazakova.section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lim.one.popovakazakova.domain.Lesson;

public class SectionHelper {
    Map<ISection, Class> sectionActivityMap = new LinkedHashMap<>();

    public void registerSection(ISection helper, Class activityClass) {
        sectionActivityMap.put(helper, activityClass);
    }

    public List<ISection> getAllSections(Lesson lesson) {
        List<ISection> sections = new ArrayList<>();
        for (ISection section : sectionActivityMap.keySet()) {
            if (section.hasSection(lesson)) {
                sections.add(section);
            }
        }
        return sections;
    }

    public List<ISection> getAllSections() {
        List<ISection> sections = new ArrayList<>();
        for (ISection section : sectionActivityMap.keySet()) {
           //todo: check if there are elements in each section
            sections.add(section);
        }
        return sections;
    }
    public Class getActivityClass(ISection section){
        return sectionActivityMap.get(section);
    }
}

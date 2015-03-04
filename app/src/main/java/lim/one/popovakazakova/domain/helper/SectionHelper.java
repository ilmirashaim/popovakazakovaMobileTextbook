package lim.one.popovakazakova.domain.helper;

import java.util.ArrayList;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Section;

public class SectionHelper {
    List<ISectionHelper> helpers = new ArrayList<>();

    public void registerHelper(ISectionHelper helper){
        helpers.add(helper);
    }

    public List<Section> getAllSections(Lesson lesson){
        List<Section> sections = new ArrayList<>();
        for(ISectionHelper helper: helpers){
            sections.addAll(helper.getSections(lesson));
        }
        return sections;
    }
}

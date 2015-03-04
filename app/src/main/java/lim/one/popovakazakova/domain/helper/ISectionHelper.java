package lim.one.popovakazakova.domain.helper;

import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Section;

public interface ISectionHelper {
    public List<Section> getSections(Lesson lesson);
}

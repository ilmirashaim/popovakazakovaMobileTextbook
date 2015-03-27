package lim.one.popovakazakova.section;

import java.util.List;

import lim.one.popovakazakova.domain.Grammar;
import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.GrammarHelper;

public class GrammarSection implements ISection {

    GrammarHelper grammarHelper;

    public GrammarSection(GrammarHelper grammarHelper) {
        this.grammarHelper = grammarHelper;
    }

    @Override
    public boolean hasSection(Lesson lesson) {
        List<Grammar> grammars = grammarHelper.getGrammars(lesson);
        return (grammars.size() > 0);
    }
}

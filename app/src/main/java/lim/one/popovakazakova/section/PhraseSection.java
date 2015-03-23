package lim.one.popovakazakova.section;

import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.Phrase;
import lim.one.popovakazakova.domain.helper.PhraseHelper;

public class PhraseSection implements ISection {

    PhraseHelper phraseHelper;

    public PhraseSection(PhraseHelper phraseHelper) {
        this.phraseHelper = phraseHelper;
    }

    @Override
    public boolean hasSection(Lesson lesson) {
        List<Phrase> phrases = phraseHelper.getPhrases(lesson);
        return (phrases.size() > 0);
    }
}

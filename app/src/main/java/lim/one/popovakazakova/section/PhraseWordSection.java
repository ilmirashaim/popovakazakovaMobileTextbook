package lim.one.popovakazakova.section;

import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.PhraseWord;
import lim.one.popovakazakova.domain.helper.PhraseWordHelper;

public class PhraseWordSection implements ISection {

    PhraseWordHelper phraseWordHelper;

    public PhraseWordSection(PhraseWordHelper phraseWordHelper) {
        this.phraseWordHelper = phraseWordHelper;
    }

    @Override
    public boolean hasSection(Lesson lesson) {
        List<PhraseWord> phraseWords = phraseWordHelper.getPhraseWords(lesson);
        return (phraseWords.size() > 0);
    }
}

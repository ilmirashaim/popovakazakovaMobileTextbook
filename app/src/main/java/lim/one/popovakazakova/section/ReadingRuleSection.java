package lim.one.popovakazakova.section;

import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.ReadingRule;
import lim.one.popovakazakova.domain.helper.ReadingRuleHelper;

public class ReadingRuleSection implements ISection {

    ReadingRuleHelper readingRuleHelper;

    public ReadingRuleSection(ReadingRuleHelper readingRuleHelper) {
        this.readingRuleHelper = readingRuleHelper;
    }

    @Override
    public boolean hasSection(Lesson lesson) {
        List<ReadingRule> readingRules = readingRuleHelper.getReadingRules(lesson);
        return (readingRules.size() > 0);
    }
}

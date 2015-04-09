package lim.one.popovakazakova;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.PhoneticExercise;
import lim.one.popovakazakova.domain.Sound;
import lim.one.popovakazakova.domain.SoundUsage;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.domain.helper.PhoneticExerciseHelper;
import lim.one.popovakazakova.domain.helper.SoundHelper;
import lim.one.popovakazakova.domain.helper.SoundUsageHelper;
import lim.one.popovakazakova.util.PhoneticExerciseAdapter;
import lim.one.popovakazakova.util.view.ExpandableHeightGridView;

public class PhoneticExerciseActivity extends SecondaryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_phonetic_exercise);
        super.onCreate(savedInstanceState);

        EbookApplication application = ((EbookApplication) getApplication());

        LessonHelper lessonHelper = application.getHelper(LessonHelper.class);
        SoundHelper soundHelper = application.getHelper(SoundHelper.class);
        PhoneticExerciseHelper phoneticExerciseHelper = application.getHelper(PhoneticExerciseHelper.class);
        SoundUsageHelper soundUsageHelper = application.getHelper(SoundUsageHelper.class);


        Bundle b = getIntent().getExtras();
        Long lessonId = b.getLong("lesson_id");
        Lesson lesson = lessonHelper.getById(lessonId);
        List<Sound> sounds = soundHelper.getAllSounds(lesson);

        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        int soundId = 0;
        for (Sound sound : sounds) {

            List<SoundUsage> soundUsages = soundUsageHelper.getSoundUsages(sound);
            List<PhoneticExercise> exercises = phoneticExerciseHelper.getPhoneticExercises(sound);

            if (soundUsages.size() > 0 || exercises.size() > 0) {
                getLayoutInflater().inflate(
                        R.layout.activity_phonetic_exercise_item, container, true
                );
                ViewGroup soundView = (ViewGroup) container.findViewById(R.id.group_container);
                ViewGroup soundUsageList = (ViewGroup) soundView.findViewById(R.id.sound_usage_list);

                int soundUsageId = 0;

                if (soundUsages.size() > 0) {
                    LinkedHashMap<String, List<SoundUsage>> groupedByTitle = groupSoundUsages(soundUsages);
                    for (String t : groupedByTitle.keySet()) {
                        Log.e("sound", t);
                        getLayoutInflater().inflate(
                                R.layout.fragment_sound_usage, soundUsageList, true
                        );
                        ViewGroup soundUsageView = (ViewGroup) soundUsageList.findViewById(R.id.sound_usage);

                        ViewGroup soundUsageContainer = (ViewGroup) soundUsageView.findViewById(
                                R.id.sound_usage_container
                        );
                        final TextView title = (TextView) soundUsageView.findViewById(R.id.sound_title);
                        title.setText("Звук [" + t + "]");

                        for (SoundUsage soundUsage : groupedByTitle.get(t)) {
                            addSoundUsage(soundUsage, soundUsageContainer, soundId + "_" + soundUsageId);
                            Log.e("sound", soundId + "_" + soundUsageId);
                            soundUsageId++;
                        }
                        soundUsageView.setId(
                                getResources().getIdentifier(
                                        "sound_usage_" + t,
                                        "id", getPackageName()
                                ));

                    }
                }

                GridView gridview = (GridView) soundView.findViewById(R.id.gridview);
                gridview.setAdapter(new PhoneticExerciseAdapter(this, exercises));
                ((ExpandableHeightGridView) gridview).setExpanded(true);

                soundView.setId(
                        getResources().getIdentifier(
                                "group_container_" + soundId,
                                "id", getPackageName()
                        ));

                soundId++;
            }
        }


    }

    private LinkedHashMap<String, List<SoundUsage>> groupSoundUsages(List<SoundUsage> soundUsages) {
        LinkedHashMap<String, List<SoundUsage>> groupedByTitle = new LinkedHashMap<>();
        for (SoundUsage s : soundUsages) {
            String soundTitle = s.getSoundTitle();
            if (groupedByTitle.get(soundTitle) == null) {
                groupedByTitle.put(soundTitle, new ArrayList<SoundUsage>());
            }
            groupedByTitle.get(soundTitle).add(s);
        }
        return groupedByTitle;
    }

    private void addSoundUsage(SoundUsage soundUsage, ViewGroup container, String id) {
        getLayoutInflater().inflate(R.layout.fragment_sound_usage_item, container, true);
        ViewGroup item = (ViewGroup) container.findViewById(R.id.sound_usage_element);
        TextView spelling = (TextView) item.findViewById(R.id.spelling);
        TextView examples = (TextView) item.findViewById(R.id.examples);
        TextView pos = (TextView) item.findViewById(R.id.position);
        examples.setText(soundUsage.getExamples());
        spelling.setText(soundUsage.getSpelling());
        fillOrHideTextView(pos, soundUsage.getPosition());
        item.setId(
                getResources().getIdentifier(
                        "sound_usage_element_" + id,
                        "id", getPackageName()
                ));
    }

    private void fillOrHideTextView(TextView view, String text) {
        if (text == null || text.isEmpty()) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }

}




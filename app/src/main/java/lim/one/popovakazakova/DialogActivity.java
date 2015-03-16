package lim.one.popovakazakova;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import lim.one.popovakazakova.domain.Dialog;
import lim.one.popovakazakova.domain.DialogCue;
import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.DialogHelper;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.util.DialogListFragment;
import lim.one.popovakazakova.util.view.PlayButton;

public class DialogActivity extends SecondaryActivity implements
        DialogListFragment.DialogCueManager {

    private List<Dialog> dialogs;
    private List<DialogCue> cues;
    private Set<String> computerPart = new HashSet<>();
    private DialogListFragment listFragment;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("start", "dialog");
        setContentView(R.layout.activity_dialog);
        if (savedInstanceState != null) {
            return;
        }
        EbookApplication application = ((EbookApplication) getApplication());

        LessonHelper lessonHelper = application.getHelper(LessonHelper.class);
        DialogHelper dialogHelper = application.getHelper(DialogHelper.class);

        Bundle b = getIntent().getExtras();
        Long lessonId = b.getLong("lesson_id");
        Lesson lesson = lessonHelper.getById(lessonId);

        List<Dialog> dialogs = dialogHelper.getDialogs(lesson);
        this.dialogs = dialogs;
        Dialog dialog = dialogs.iterator().next();

        List<DialogCue> cues = dialogHelper.getDialogCues(dialog);
        this.cues = cues;

        PlayButton play_button = (PlayButton) findViewById(R.id.play_button);
        mp = new MediaPlayer();
        play_button.setOnStateChangeListener(new OnPlayClickListener());

        Map<String, List<DialogCue>> parts = new HashMap<>();
        for (DialogCue cue : cues) {
            String characterName = cue.getCharacterName();
            List<DialogCue> dialogCues = parts.get(characterName);
            if (dialogCues == null) {
                dialogCues = new ArrayList<>();
                parts.put(characterName, dialogCues);
            }
            dialogCues.add(cue);
        }
        computerPart.addAll(parts.keySet());

        listFragment = DialogListFragment.newInstance(cues);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.list_container, listFragment).commit();

        LinearLayout roleButtonLayout = (LinearLayout) findViewById(R.id.role_button_layout);
        int i = 0;
        for (String part : parts.keySet()) {
            ToggleButton button = new ToggleButton(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
            );
            button.setTextOff(part);
            button.setTextOn(part);
            button.setId(getResources().getIdentifier("role_button_" + i, "id", getPackageName()));
            button.setChecked(true);
            roleButtonLayout.addView(button);
            button.setOnCheckedChangeListener(new OnRoleCheckedListener());
            i++;
        }

    }

    private class OnRoleCheckedListener implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                computerPart.add(((ToggleButton) buttonView).getTextOn().toString());
            } else {
                computerPart.remove(((ToggleButton) buttonView).getTextOff().toString());
            }
            ((ArrayAdapter) getListFragment().getListAdapter()).notifyDataSetChanged();
        }
    }

    private class OnPlayClickListener implements PlayButton.OnStateChangeListener {
        Queue<DialogCue> forPlay;

        @Override
        public void onPlay() {
            stopPlaying();
            forPlay = new ConcurrentLinkedQueue<>();
            for (DialogCue cue : cues) {
                if (computerPart.contains(cue.getCharacterName())) {
                    forPlay.add(cue);
                }
            }
            playNext();
        }

        @Override
        public void onPause() {
            if (mp.isPlaying()) {
                mp.pause();
            }
        }
        private void playNext() {
            if (forPlay.isEmpty()) {
                return;
            }
            DialogCue next = forPlay.poll();
            String filename = next.getFilename();
            Log.d("playing", filename);
            try {
                stopPlaying();
                mp.reset();
                AssetFileDescriptor afd = getAssets().openFd(filename);
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepare();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        playNext();
                    }
                });
                mp.start();
            } catch (IOException e) {
                Log.e("media player exception", "in playing " + filename, e);
            }
        }
    }

    @Override
    public void onBackPressed() {
        stopPlaying();
        setResult(1);
        finish();
    }

    public DialogListFragment getListFragment() {
        return listFragment;
    }


    @Override
    public Set<String> getComputerPart() {
        return computerPart;
    }

    protected void stopPlaying() {
        if (mp == null) {
            return;
        }
        if (mp.isPlaying()) {
            mp.stop();
        }
    }
}
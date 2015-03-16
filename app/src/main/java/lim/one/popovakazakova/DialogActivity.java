package lim.one.popovakazakova;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lim.one.popovakazakova.domain.Dialog;
import lim.one.popovakazakova.domain.DialogCue;
import lim.one.popovakazakova.domain.Lesson;
import lim.one.popovakazakova.domain.helper.DialogHelper;
import lim.one.popovakazakova.domain.helper.LessonHelper;
import lim.one.popovakazakova.util.DialogListFragment;
import lim.one.popovakazakova.util.DialogPlayer;
import lim.one.popovakazakova.util.view.PlayButton;

public class DialogActivity extends SecondaryActivity implements
        DialogListFragment.DialogCueManager {

    private List<Dialog> dialogs;
    private List<DialogCue> cues;
    private Set<String> computerPart = new HashSet<>();
    private DialogListFragment listFragment;
    private DialogPlayer dialogPlayer;

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

        PlayButton playButton = (PlayButton) findViewById(R.id.play_button);
        dialogPlayer = new DialogPlayer(playButton, cues, computerPart);
        playButton.setOnStateChangeListener(dialogPlayer);

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

        insertRoleButtons(parts.keySet());
    }

    private void insertRoleButtons(Collection<String> roleNames) {
        LinearLayout roleButtonLayout = (LinearLayout) findViewById(R.id.role_button_layout);
        int i = 0;
        for (String roleName : roleNames) {
            ToggleButton button = new ToggleButton(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(toPixels(60), toPixels(60)));
            button.setTextOff(roleName);
            button.setTextOn(roleName);
            button.setId(getResources().getIdentifier("role_button_" + i, "id", getPackageName()));
            button.setChecked(true);
            button.setTextColor(getResources().getColor(R.color.text));
            roleButtonLayout.addView(button);
            button.setOnCheckedChangeListener(new OnRoleCheckedListener());
            i++;
        }
    }

    private int toPixels(int dps) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    private class OnRoleCheckedListener implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                computerPart.add(((ToggleButton) buttonView).getTextOn().toString());
            } else {
                computerPart.remove(((ToggleButton) buttonView).getTextOff().toString());
            }
            ((ArrayAdapter) getListFragment().getListAdapter()).notifyDataSetChanged();
            dialogPlayer.setComputerPart(computerPart);
        }
    }

    @Override
    public void onBackPressed() {
        if (dialogPlayer != null) {
            dialogPlayer.stopPlaying();
        }
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


}
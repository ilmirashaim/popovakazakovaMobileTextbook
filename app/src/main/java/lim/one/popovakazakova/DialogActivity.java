package lim.one.popovakazakova;

import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
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
import lim.one.popovakazakova.util.common.PlayButtonMultiPlayerConnector;

public class DialogActivity extends SecondaryActivity implements
        DialogListFragment.DialogCueManager {

    private List<Dialog> dialogs;
    private List<DialogCue> cues;
    private Set<String> computerPart = new HashSet<>();
    private DialogListFragment listFragment;
    private DialogPlayer dialogPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dialog);
        super.onCreate(savedInstanceState);
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

        listFragment = DialogListFragment.newInstance(cues);

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

        PlayButton playButton = (PlayButton) findViewById(R.id.play_button);
        dialogPlayer = new DialogPlayer(this, cues, computerPart);
        dialogPlayer.setOnPlayListener(listFragment);
        new PlayButtonMultiPlayerConnector(playButton, dialogPlayer);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.list_container, listFragment).commit();

        insertRoleButtons(parts.keySet());
    }

    private void insertRoleButtons(Collection<String> roleNames) {
        LinearLayout roleButtonLayout = (LinearLayout) findViewById(R.id.role_button_layout);
        int i = 0;
        for (String roleName : roleNames) {
            ViewGroup layout = (ViewGroup)getLayoutInflater().inflate(R.layout.button_role_dialog, null);

            CompoundButton button = (CompoundButton) layout.findViewById(R.id.checkBox);
            TextView textView = (TextView) layout.findViewById(R.id.textView);
            button.setId(getResources().getIdentifier("role_button_" + i, "id", getPackageName()));
            textView.setText(roleName);
            roleButtonLayout.addView(layout);
            button.setOnCheckedChangeListener(new OnRoleCheckedListener());
            i++;
        }
    }

    private class OnRoleCheckedListener implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            TextView textView = (TextView)((ViewGroup)buttonView.getParent()).findViewById(R.id.textView);
            if (isChecked) {
                computerPart.add(textView.getText().toString());
            } else {
                computerPart.remove(textView.getText().toString());
            }
            ((ArrayAdapter) getListFragment().getListAdapter()).notifyDataSetChanged();
            dialogPlayer.setComputerPart(computerPart);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (dialogPlayer != null) {
            dialogPlayer.onFinished();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialogPlayer != null) {
            dialogPlayer.onFinished();
        }
    }

    @Override
    public void onBackPressed() {
        if (dialogPlayer != null) {
            dialogPlayer.onFinished();
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
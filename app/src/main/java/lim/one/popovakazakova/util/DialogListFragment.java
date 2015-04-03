package lim.one.popovakazakova.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.DialogCue;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class DialogListFragment extends SimpleListFragment<DialogCue> implements DialogPlayer.OnPlayListener {
    private DialogCueManager dialogCueManager;
    private DialogCue playing = null;

    public static DialogListFragment newInstance(List<DialogCue> cues) {
        DialogListFragment f = new DialogListFragment();
        f.setElements(cues);
        f.setListViewId(R.layout.fragment_dialog_cues_list);
        f.setListElementViewId(R.layout.fragment_dialog_cues_list_item);
        return f;
    }

    @Override
    public void onPlay(DialogCue dialogCue) {
        playing = dialogCue;
        if (dialogCue != null) {
            getListView().smoothScrollToPosition(dialogCue.getPosition() - 1);
        }
        ((ArrayAdapter) getListAdapter()).notifyDataSetChanged();
    }

    public interface DialogCueManager {
        public Set<String> getComputerPart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.dialogCueManager = (DialogCueManager) activity;
    }

    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView computer = (TextView) row.findViewById(R.id.computer);
        TextView human = (TextView) row.findViewById(R.id.human);

        DialogCue dialogCue = getElements().get(position);
        Set<String> computerPart = dialogCueManager.getComputerPart();
        TextView selected;
        if (computerPart.contains(dialogCue.getCharacterName())) {
            computer.setText(dialogCue.getText());
            human.setText("");
            selected = computer;
        } else {
            human.setText(dialogCue.getText());
            computer.setText("");
            selected = human;
        }
        if (playing != null) {
            selected.setTextColor(getResources().getColor(
                    dialogCue.getPosition().equals(playing.getPosition()) ?
                            R.color.colorAccent :
                            R.color.text
            ));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

}

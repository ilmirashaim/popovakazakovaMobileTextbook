package lim.one.popovakazakova.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.DialogCue;

public class DialogListFragment extends ListFragment implements DialogPlayer.OnPlayListener {
    private List<DialogCue> cues;
    private DialogCueManager dialogCueManager;
    private DialogCue playing = null;

    public static DialogListFragment newInstance(List<DialogCue> cues) {
        DialogListFragment f = new DialogListFragment();
        f.setDialogCues(cues);
        return f;
    }

    public List<DialogCue> getDialogCues() {
        return cues;
    }

    public void setDialogCues(List<DialogCue> cues) {
        this.cues = cues;
    }

    @Override
    public void onPlay(DialogCue dialogCue) {
        playing = dialogCue;
        if(dialogCue != null) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_dialog_cues_list, container, false);

        setListAdapter(new DialogListAdapter(
                getActivity(), R.layout.fragment_dialog_cues_list_item
        ));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(null);
    }

    private class DialogListAdapter extends ArrayAdapter<DialogCue> {
        private Context context;
        private int layoutResourceId;

        public DialogListAdapter(Context context, int layoutResourceId) {
            super(context, layoutResourceId, getDialogCues());
            this.layoutResourceId = layoutResourceId;
            this.context = context;
        }

        @Override
        public int getCount() {
            return getDialogCues().size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if(row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
            }
            TextView computer = (TextView) row.findViewById(R.id.computer);
            TextView human = (TextView) row.findViewById(R.id.human);

            DialogCue dialogCue = getDialogCues().get(position);
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

            return row;
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

}

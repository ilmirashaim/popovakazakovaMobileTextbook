package lim.one.popovakazakova.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Sound;
import lim.one.popovakazakova.domain.SoundUsage;

public class SoundUsageFragment extends ListFragment {
    private List<SoundUsage> soundUsages;

    public static SoundUsageFragment newInstance(Sound sound, List<SoundUsage> soundUsages) {
        SoundUsageFragment f = new SoundUsageFragment();

        f.setSoundUsages(soundUsages);
        Bundle args = new Bundle();
        args.putString("sound", sound.getTitle());
        f.setArguments(args);

        return f;
    }

    public List<SoundUsage> getSoundUsages() {
        return soundUsages;
    }

    public void setSoundUsages(List<SoundUsage> soundUsages) {
        this.soundUsages = soundUsages;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_sound_usage, container, false);

        final TextView title = (TextView) rootView.findViewById(R.id.sound_title);
        title.setText(getArguments().getString("sound"));

        setListAdapter(new SoundUsageAdapter(
                getActivity(), R.layout.fragment_sound_usage_item
        ));

        return rootView;
    }

    private class SoundUsageAdapter extends ArrayAdapter<SoundUsage> {

        Context context;
        int layoutResourceId;

        public SoundUsageAdapter(Context context, int layoutResourceId) {
            super(context, layoutResourceId, getSoundUsages());
            this.layoutResourceId = layoutResourceId;
            this.context = context;
        }
        @Override
        public int getCount() {
            return getSoundUsages().size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                TextView spelling = (TextView) row.findViewById(R.id.spelling);
                TextView examples = (TextView) row.findViewById(R.id.examples);
                TextView pos = (TextView) row.findViewById(R.id.position);
                SoundUsage soundUsage = getSoundUsages().get(position);
                examples.setText(soundUsage.getExamples());
                spelling.setText(soundUsage.getSpelling());
                pos.setText(soundUsage.getPosition());
            }

            return row;
        }

    }

}

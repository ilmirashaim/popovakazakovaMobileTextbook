package lim.one.popovakazakova.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
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
    List<SoundUsage> soundUsages;

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
                getActivity(), R.layout.fragment_sound_usage_item, getSoundUsages()
        ));

        return rootView;
    }

    private class SoundUsageAdapter extends ArrayAdapter<SoundUsage> {

        Context context;
        int layoutResourceId;
        List<SoundUsage> soundUsages;

        public SoundUsageAdapter(Context context, int layoutResourceId, List<SoundUsage> soundUsages) {
            super(context, layoutResourceId, soundUsages);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.soundUsages = soundUsages;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            Log.d("get view ", position + "");

            if (row == null) {
                Log.d("row == null", "");
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                TextView spelling = (TextView) row.findViewById(R.id.spelling);
                TextView examples = (TextView) row.findViewById(R.id.examples);
                TextView pos = (TextView) row.findViewById(R.id.position);
                SoundUsage soundUsage = soundUsages.get(position);
                examples.setText(soundUsage.getExamples());
                Log.d("examples = ", soundUsage.getExamples());
                spelling.setText(soundUsage.getSpelling());
                Log.d("position = ", soundUsage.getPosition());
                pos.setText(soundUsage.getPosition());
            }

            return row;
        }

    }

}

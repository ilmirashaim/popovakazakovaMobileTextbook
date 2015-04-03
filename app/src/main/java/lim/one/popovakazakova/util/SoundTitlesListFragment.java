package lim.one.popovakazakova.util;

import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import lim.one.popovakazakova.R;
import lim.one.popovakazakova.domain.Sound;
import lim.one.popovakazakova.util.common.SimpleListFragment;

public class SoundTitlesListFragment extends SimpleListFragment<Sound> {
    int visible = -1;

    public static SoundTitlesListFragment newInstance(List<Sound> sounds) {
        SoundTitlesListFragment f = new SoundTitlesListFragment();
        f.setElements(sounds);
        f.setListViewId(R.layout.fragment_sound_list);
        f.setListElementViewId(R.layout.fragment_sound_title);
        return f;
    }


    @Override
    public void fillRow(int position, View row, ViewGroup parent) {
        TextView soundContent = (TextView) row.findViewById(R.id.sound_content);
        TextView soundTitle = (TextView) row.findViewById(R.id.sound_title);
        Sound sound = getElements().get(position);
        soundContent.setText(sound.getContent());
        soundTitle.setText(sound.getTitle());
        soundTitle.getBackground().setColorFilter(getResources().getColor(
                getResources().getIdentifier(sound.getType(), "color", getActivity().getPackageName())
        ), PorterDuff.Mode.SRC);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (visible == position) {
            visible = -1;
            setItemSelected(position, false);
        } else {
            if(visible >=0) {
                setItemSelected(visible, false);
            }
            visible = position;
            setItemSelected(position, true);
            getListView().smoothScrollToPosition(position); //todo: check it
        }
    }

    public void setItemSelected(int position, boolean selected){
        View v = getViewByPosition(position, getListView());
        TextView soundContent = (TextView) v.findViewById(R.id.sound_content);
        CardView cardView = (CardView) v.findViewById(R.id.card_view);
        RelativeLayout layout = (RelativeLayout) soundContent.getParent();
        if(selected){
            cardView.setCardElevation(getResources().getDimension(R.dimen.card_elevation));
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            applySingleLine(soundContent, false);
        }else{
            cardView.setCardElevation(0);
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.height = (int) getResources().getDimension(R.dimen.list_with_avatar_height);
            applySingleLine(soundContent, true);
        }
    }

    private void applySingleLine(TextView textView, boolean singleLine) {
        if (singleLine) {
            textView.setMaxLines(1);
            textView.setHorizontallyScrolling(true);
            textView.setTransformationMethod(SingleLineTransformationMethod.getInstance());
            textView.setEllipsize(TextUtils.TruncateAt.END);
        } else {
            textView.setMaxLines(Integer.MAX_VALUE);
            textView.setHorizontallyScrolling(false);
            textView.setTransformationMethod(null);
            textView.setEllipsize(null);
        }
    }

}

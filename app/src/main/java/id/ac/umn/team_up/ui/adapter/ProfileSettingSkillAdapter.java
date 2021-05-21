package id.ac.umn.team_up.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import id.ac.umn.team_up.R;
import id.ac.umn.team_up.ui.activity.MainActivity;
import id.ac.umn.team_up.ui.activity.ProfileSettingSkillActivity;

public class ProfileSettingSkillAdapter extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;

    public ProfileSettingSkillAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.list_skill, items);
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_skill, null);

            TextView tvIndex = convertView.findViewById(R.id.tvIndex);
            tvIndex.setText(position + 1 + ".");

            TextView tvSkill = convertView.findViewById(R.id.tvSkill);
            tvSkill.setText(list.get(position));

            ImageButton btnRemove = convertView.findViewById(R.id.btnRemove);
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("remove", String.valueOf(position));
                    ProfileSettingSkillActivity.removeSkill(position);
                }
            });
        }

        return convertView;
    }
}

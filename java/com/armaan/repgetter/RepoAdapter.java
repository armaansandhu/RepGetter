package com.armaan.repgetter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RepoAdapter extends ArrayAdapter<Repo> {

    public RepoAdapter(Context context) {
        super(context, -1, new ArrayList<Repo>());
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        if (converView == null) {
            converView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Repo currentRepo = getItem(position);
        TextView username =(TextView) converView.findViewById(R.id.username);
        username.setText(currentRepo.getUsername());
        TextView description = (TextView) converView.findViewById(R.id.description);
        description.setText(currentRepo.getDescription());
        ImageView avatar = (ImageView) converView.findViewById(R.id.avatar);
        String avatarURL = currentRepo.getAvatar();
        Picasso.get().load(avatarURL).into(avatar);
        return converView;
    }
}

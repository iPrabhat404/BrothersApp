package com.prabhat.brothers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by USER on 2/25/2018.
 */

public class MemberList extends ArrayAdapter<Users> {

    private Activity context;
    private List<Users> memberList;

    public MemberList(Activity context, List<Users> memberList){

        super(context, R.layout.listview_layout, memberList);
        this.context = context;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listview_layout, null, true);

        ImageView memberLogo = (ImageView) listViewItem.findViewById(R.id.member_logo);
        TextView memberName = (TextView) listViewItem.findViewById(R.id.member_name);
        TextView memberEmail = (TextView) listViewItem.findViewById(R.id.member_email);

        Users member = memberList.get(position);

        GlideApp.with(getContext()).load(member.getProfileImageUrl()).fitCenter().into(memberLogo);
        memberName.setText(member.getUserName());
        memberEmail.setText(member.getUserEmail());
        return listViewItem;
    }
}

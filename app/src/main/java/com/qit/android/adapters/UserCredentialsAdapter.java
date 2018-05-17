package com.qit.android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.rest.dto.UserCredentialDTO;

import java.util.List;

@Deprecated
public class UserCredentialsAdapter extends BaseAdapter {

    private static final String ENABLED = "Enabled: ";
    private static final String LOGIN = "Login: ";
    private static final String PASSWORD = "Password: ";

    private List<UserCredentialDTO> credentialDTOs;
    private LayoutInflater inflater;

    public UserCredentialsAdapter(Context context, List<UserCredentialDTO> credentialDTOs) {
        this.credentialDTOs = credentialDTOs;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return credentialDTOs.size();
    }

    @Override
    public UserCredentialDTO getItem(int position) {
        return credentialDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_user_credentials_list, viewGroup, false);
        }

        TextView tvLogin = view.findViewById(R.id.tvLogin);
        tvLogin.setTextColor(Color.WHITE);
        TextView tvPassword = view.findViewById(R.id.tvPassword);
        tvPassword.setTextColor(Color.WHITE);
        TextView tvEnabled = view.findViewById(R.id.tvEnabled);
        tvEnabled.setTextColor(Color.WHITE);

        try {
            tvLogin.setText(LOGIN + getItem(position).getUsername());
            tvPassword.setText(PASSWORD + getItem(position).getPassword());
            tvEnabled.setText(ENABLED + String.valueOf(getItem(position).isEnabled()));
        } catch (NullPointerException e) {}

        return view;
    }
}

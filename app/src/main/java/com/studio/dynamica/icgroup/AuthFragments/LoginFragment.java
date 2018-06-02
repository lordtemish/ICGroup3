package com.studio.dynamica.icgroup.AuthFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.studio.dynamica.icgroup.Activities.LoginActivity;
import com.studio.dynamica.icgroup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    ConstraintLayout constraintLayout;
    EditText login;
    EditText password;
    TextView loginSystem;
    TextView forgotPass;
    public LoginFragment() {
        // Required empty public constructor
    }
    public void logIn(String login, String password) throws NullPointerException{
        getActivity().finish();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        login=(EditText) view.findViewById(R.id.logintextLogin);
        login.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenir-light.ttf"));

        password=(EditText) view.findViewById(R.id.passwordLogin);
        password.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenir-light.ttf"));

       /* forgotPass=(TextView) view.findViewById(R.id.forgotPass);
        forgotPass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AVENIRLTSTD-BOOK.otf"));*/

        loginSystem=(TextView) view.findViewById(R.id.loginSystemTextView);
        loginSystem.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-MediumCn.ttf"));
        constraintLayout=(ConstraintLayout) view.findViewById(R.id.loginButton);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().length()>0 && password.getText().length()>0){
                    logIn(login.getText()+"",password.getText()+"");
                }
            }
        });
        return view;
    }

}

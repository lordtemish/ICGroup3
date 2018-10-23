package com.studio.dynamica.icgroup.ObjectFragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.ChooseAcceptServiceAdapter;
import com.studio.dynamica.icgroup.Adapters.FilesAdapter;
import com.studio.dynamica.icgroup.ExtraFragments.CalendarView;
import com.studio.dynamica.icgroup.ExtraFragments.TimePickView;
import com.studio.dynamica.icgroup.Forms.ChooseAcceptForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewServiceFragment extends Fragment {
    RecyclerView filesRecyclerView;
    CalendarView calendarView;
    TimePickView timePickView;
    FrameLayout commentLayout;
    EditText nameEditText, commentEditText;
    RadioGroup radioGroup;
    RadioButton todayRadio, dateRadio, mediaRadio, photoRadio;
    RadioButton[] priorityRadios={null,null,null};
    TextView ObjectTitle,priorityLabelTextView, DirectorObjectLabelTextView, placeLabelTextView, serviceTypeLabeLTextView, otvLabelTextView, dateLabelTextView, opisanieLabelTextView, cancelTextView, addTextView, filesTextView, needAcceptTextView;
    Spinner DirectorObjectLabelSpinner;
    List<Spinner> spinners;
    List<FrameLayout> spinnerButtonFrames;
    List<ArrayList<String>> spinnerLists;
    RecyclerView answerUserRecyclerView;
    boolean media=false, photo=false;
    View.OnClickListener photoListener;
    int[][] choseindexes={{0,0},{0,0}};
     int click=0, clickx=0, clicky=0, xmax=0, ymax=0;
    Calendar cal;
    public AddNewServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_new_service, container, false);

        click=0;
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        this.cal=new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1);
        createViews(view);
        setFonttype();
        setSpinners();
        mediaRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaClicked();
            }
        });
        photoListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                String[] mimeTypes = {"image/*", "video/*"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                ((MainActivity)getActivity()).startActivityForResult(intent,1);

            }
        };
        photoRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoclicked();
            }
        });
        todayRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioButtons();
                todayRadio.setChecked(true);
                timePickView.setVisibility(View.VISIBLE);
            }
        });

        dateRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioButtons();
                dateRadio.setChecked(true);
                calendarView.setVisibility(View.VISIBLE);
            }
        });


        ((MainActivity)getActivity()).setRecyclerViewOrientation(filesRecyclerView,LinearLayoutManager.HORIZONTAL);
        List<Bitmap> bitmaps=new ArrayList<Bitmap>();
        FilesAdapter filesAdapter=new FilesAdapter(bitmaps);
        filesRecyclerView.setAdapter(filesAdapter);
        ((MainActivity)getActivity()).setFileAdapterOs(bitmaps,filesAdapter);


        ((MainActivity) getActivity()).setRecyclerViewOrientation(answerUserRecyclerView, LinearLayoutManager.VERTICAL);
        List<ChooseAcceptForm> acceptForms=new ArrayList<>();
        acceptForms.add(new ChooseAcceptForm("Отдел производства","Темирлан Алмасович","ОПУ",false));
        acceptForms.add(new ChooseAcceptForm("Отдел производства","1Темирлан Алмасович","ОПУ",false));
        acceptForms.add(new ChooseAcceptForm("Отдел производства","2Темирлан Алмасович","ОПУ",true));
        acceptForms.add(new ChooseAcceptForm("Отдел производства","3Темирлан Алмасович","ОПУ",true));
        acceptForms.add(new ChooseAcceptForm("Отдел производства","4Темирлан Алмасович","ОПУ",false));
        ChooseAcceptServiceAdapter serviceAdapter=new ChooseAcceptServiceAdapter(acceptForms);
        answerUserRecyclerView.setAdapter(serviceAdapter);

        return view;
    }
    private void setSpinners(){
        for(int i=0;i<4;i++){
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item,spinnerLists.get(i)){
                public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));

                return v;
            }
                public View getDropDownView(int position, View convertView, ViewGroup parent) {

                    View v = super.getView(position,convertView,parent);
                    ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));

                    return v;

                }};
            spinnerButtonFrames.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinners.get(spinnerButtonFrames.indexOf(view)).performClick();
                }
            });
            spinners.get(i).setAdapter(adapter);
        }
    }
    private void setFonttype(){
        ObjectTitle.setTypeface(((MainActivity)getActivity()).getTypeFace("it"));
        priorityLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        nameEditText.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        commentEditText.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        filesTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        DirectorObjectLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        placeLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        serviceTypeLabeLTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        otvLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        cancelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        addTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        dateLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        todayRadio.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        dateRadio.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        opisanieLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        mediaRadio.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        photoRadio.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        needAcceptTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        for(RadioButton i:priorityRadios){
            i.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        }
    }
    private void createViews(View view){
        spinners=new ArrayList<>();
        spinnerButtonFrames=new ArrayList<>();
        spinnerLists=new ArrayList<>();for(int i=0;i<4;i++){
            spinnerLists.add(new ArrayList<String>());
            spinnerLists.get(i).add("Выберите объект");
        }
        spinners.add((Spinner) view.findViewById(R.id.DirectorObjectLabelSpinner));spinners.add((Spinner) view.findViewById(R.id.employeeChangeSpinner1));spinners.add((Spinner) view.findViewById(R.id.employeeChangeSpinner2));spinners.add((Spinner) view.findViewById(R.id.employeeChangeSpinner3));
        spinnerButtonFrames.add((FrameLayout) view.findViewById(R.id.spinnerFrameImage));spinnerButtonFrames.add((FrameLayout) view.findViewById(R.id.spinnerFrameImage1));spinnerButtonFrames.add((FrameLayout) view.findViewById(R.id.spinnerFrameImage2));spinnerButtonFrames.add((FrameLayout) view.findViewById(R.id.spinnerFrameImage3));
        filesRecyclerView=(RecyclerView) view.findViewById(R.id.filesRecyclerView);
        commentEditText=(EditText) view.findViewById(R.id.commentEditText);
        commentLayout=(FrameLayout) view.findViewById(R.id.commentLayout);
        nameEditText=(EditText) view.findViewById(R.id.nameEditText);

        radioGroup=(RadioGroup) view.findViewById(R.id.radioGroup);
        mediaRadio=(RadioButton) view.findViewById(R.id.mediaRadioButton);
        photoRadio=(RadioButton) view.findViewById(R.id.photoRadioButton);
        todayRadio=(RadioButton) view.findViewById(R.id.radioButton);
        dateRadio=(RadioButton) view.findViewById(R.id.radioButton1);

        calendarView=(CalendarView) view.findViewById(R.id.calendarView);
        timePickView=(TimePickView) view.findViewById(R.id.timePickView);
        ObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        priorityLabelTextView=(TextView) view.findViewById(R.id.priorityLabelTextView);
        DirectorObjectLabelTextView=(TextView) view.findViewById(R.id.DirectorObjectLabelTextView);
        placeLabelTextView=(TextView) view.findViewById(R.id.placeLabelTextView);
        serviceTypeLabeLTextView=(TextView) view.findViewById(R.id.serviceTypeLabelTextView);
        otvLabelTextView=(TextView) view.findViewById(R.id.otvLabelTextView);
        dateLabelTextView=(TextView) view.findViewById(R.id.dateLabelTextView);
        opisanieLabelTextView=(TextView) view.findViewById(R.id.opisanieLabelTextView);
        cancelTextView=(TextView) view.findViewById(R.id.cancelTextView);
        addTextView=(TextView) view.findViewById(R.id.addTextView);
        needAcceptTextView=(TextView) view.findViewById(R.id.needAcceptTextView);
        filesTextView=(TextView) view.findViewById(R.id.filesTextView);
        answerUserRecyclerView=(RecyclerView) view.findViewById(R.id.answerUserRecyclerView);

        priorityRadios[0]=(RadioButton) view.findViewById(R.id.priority1);
        priorityRadios[1]=(RadioButton) view.findViewById(R.id.priority2);
        priorityRadios[2]=(RadioButton) view.findViewById(R.id.priority3);
    }
    public void clearRadioButtons(){
        dateRadio.setChecked(false);
        todayRadio.setChecked(false);
        calendarView.setVisibility(View.GONE);
        timePickView.setVisibility(View.GONE);
    }
    private void photoclicked()
    {
        if(photo){
           photo=false;
           filesTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
           filesTextView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

               }
           });
        }
        else{
            photo=true;
            filesTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
            filesTextView.setOnClickListener(photoListener);
        }
        photoRadio.setChecked(photo);
    }
    public void mediaClicked(){
        if(media){
            mediaRadio.setChecked(false);
            commentEditText.setHintTextColor(getActivity().getResources().getColor(R.color.greyy));
            commentEditText.setFocusableInTouchMode(false);
            commentEditText.setFocusable(false);
            commentEditText.setText("");
            commentLayout.setBackgroundResource((R.drawable.grey_line));
            media=false;
        }
        else{
            mediaRadio.setChecked(true);
            commentEditText.setHintTextColor(getActivity().getResources().getColor(R.color.black));
            commentEditText.setFocusableInTouchMode(true);
            commentEditText.setFocusable(true);
            commentLayout.setBackgroundResource((R.drawable.black_line));
            media=true;
        }
    }

}

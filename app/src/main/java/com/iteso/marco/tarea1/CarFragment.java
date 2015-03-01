package com.iteso.marco.tarea1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by marco on 2/28/15.
 */
public class CarFragment  extends Fragment
{
    private TextView mCarTitleViewText;
    private TextView mCarDescViewText;
    private ImageView mCarImageView;

    public static final String ARG_CAR_NUMBER = "car_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.car_fragment, container, false);
        int car_index = getArguments().getInt(CarFragment.ARG_CAR_NUMBER);
        String car = getResources().getStringArray(R.array.car_list)[car_index];

        //Set car image
        int imageId = getResources().getIdentifier(car.toLowerCase(), "drawable", getActivity().getPackageName());
        ((ImageView)rootView.findViewById(R.id.imageCar)).setImageResource(imageId);

        //Set car name
        String car_name = getResources().getStringArray(R.array.car_list)[car_index];
        getActivity().setTitle(car_name);
        mCarTitleViewText = (TextView)rootView.findViewById(R.id.textCarName);
        mCarTitleViewText.setText(car_name);

        //Set car description
        String car_desc = getResources().getStringArray(R.array.car_desc_list)[car_index];
        mCarDescViewText = (TextView)rootView.findViewById(R.id.textDescription);
        mCarDescViewText.setText(car_desc);

        return rootView;
    }
}

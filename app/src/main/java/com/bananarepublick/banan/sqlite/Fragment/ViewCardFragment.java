package com.bananarepublick.banan.sqlite.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bananarepublick.banan.sqlite.R;
import com.bananarepublick.banan.sqlite.data.Card;
import com.bananarepublick.banan.sqlite.data.ViewCard;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewCardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCardFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textView;
    private EditText editTextName, editTextSale, editTextID;

    private ViewCard viewCard;

    private OnFragmentInteractionListener mListener;

    public ViewCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewCardFragment newInstance(String param1, String param2) {
        ViewCardFragment fragment = new ViewCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_card, container, false);

        textView = (TextView) view.findViewById(R.id.textViewTable);
        textView.setMovementMethod(new ScrollingMovementMethod());

        editTextName = (EditText) view.findViewById(R.id.editText1);
        editTextSale = (EditText) view.findViewById(R.id.editText2);
        editTextID = (EditText) view.findViewById(R.id.editTextID);

        Button buttonAdd = (Button) view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);

        Button buttonRead = (Button) view.findViewById(R.id.buttonRead);
        buttonRead.setOnClickListener(this);

        Button buttonDeleteAll = (Button) view.findViewById(R.id.buttonDeleteAll);
        buttonDeleteAll.setOnClickListener(this);

        Button buttonUpdate = (Button) view.findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(this);

        Button buttonDelete = (Button) view.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        List<ViewCard> allViewCard;
        List<Card> allCard;

        switch (v.getId()) {
            case R.id.buttonAdd:

                if (checkEditAdd()) {
                    viewCard = new ViewCard(editTextName.getText().toString(),
                            Integer.valueOf(editTextSale.getText().toString()));
                    viewCard.save();

                    allViewCard = ViewCard.listAll(ViewCard.class);
                    textView.setText(allViewCard.toString());
                }
                break;

            case R.id.buttonRead:

                allViewCard = ViewCard.listAll(ViewCard.class);

                if (allViewCard.toString() != "[]")
                    textView.setText(allViewCard.toString());
                else
                    textView.setText("Таблица пустая");
                break;

            case R.id.buttonDeleteAll:
                allCard = Card.find(Card.class, "view_card");

                if (allCard.toString() == "[]") {
                    ViewCard.deleteAll(ViewCard.class);
                    textView.setText("Таблица пустая");
                }
                break;

            case R.id.buttonUpdate:

                if (checkEditAdd() && checkEditID() && checkID()) {
                    viewCard.name = editTextName.getText().toString();
                    viewCard.sale = Integer.parseInt(editTextSale.getText().toString());
                    viewCard.save();

                    allViewCard = ViewCard.listAll(ViewCard.class);
                    textView.setText(allViewCard.toString());
                }
                break;

            case R.id.buttonDelete:

                if (checkEditID() && checkID() && checkIdCard()) {
                    viewCard.delete();
                    allViewCard = ViewCard.listAll(ViewCard.class);
                    textView.setText(allViewCard.toString());
                }
                break;
        }

    }

    // проверка на пустые едиты добюавления
    private boolean checkEditAdd() {
        if (editTextName.length() != 0 && editTextSale.length() != 0) {

            int value = Integer.parseInt(editTextSale.getText().toString());
            if (value > 0 && value <= 100) {
                return true;
            } else
                Toast.makeText(getActivity(), "Вы ввели недопустимую скидку",
                        Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getActivity(), "Вы не заполнили нужные поля",
                    Toast.LENGTH_SHORT).show();
        return false;
    }

    //проверка пустой ли edit id
    private boolean checkEditID() {
        if (editTextID.length() != 0) {
            return true;
        } else
            Toast.makeText(getActivity(), "Вы не заполнили ID",
                    Toast.LENGTH_SHORT).show();
        return false;
    }

    //проверка есть ли такая запись в БД
    public boolean checkID() {
        int id = Integer.parseInt(editTextID.getText().toString());
        viewCard = ViewCard.findById(ViewCard.class, id);

        if (viewCard != null)
            return true;
        else
            Toast.makeText(getActivity(), "Вы ввели неправильный ID",
                    Toast.LENGTH_SHORT).show();
        return false;
    }

    // проверка не используется ли данная карта
    public boolean checkIdCard() {
        List<Card> allCard = Card.find(Card.class, "view_card = ?",
                editTextID.getText().toString());
        if (allCard.toString() == "[]")
            return true;
        else
            Toast.makeText(getActivity(), "Вы ввели используемую карту",
                    Toast.LENGTH_SHORT).show();
        return false;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

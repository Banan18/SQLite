package com.bananarepublick.banan.sqlite.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bananarepublick.banan.sqlite.R;
import com.bananarepublick.banan.sqlite.data.Card;
import com.bananarepublick.banan.sqlite.data.Owner;
import com.bananarepublick.banan.sqlite.data.ViewCard;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textView;
    private EditText editTextCard, editTextIdView, editTextIdOwner, editTextIdCard;

    private Owner owner;
    private ViewCard viewCard;
    private Card card;

    private OnFragmentInteractionListener mListener;

    public CardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardFragment newInstance(String param1, String param2) {
        CardFragment fragment = new CardFragment();
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

        View view = inflater.inflate(R.layout.fragment_card, container, false);

        textView = (TextView) view.findViewById(R.id.textView2);

        editTextCard = (EditText) view.findViewById(R.id.editTextCard);
        editTextIdView = (EditText) view.findViewById(R.id.editTextIdView);
        editTextIdOwner = (EditText) view.findViewById(R.id.editTextIdOwner);
        editTextIdCard = (EditText) view.findViewById(R.id.editTextIdCard);

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
        List<Card> allCard;

        switch (v.getId()) {
            case R.id.buttonAdd:

                if (checkEditAdd() && checkIdView() && checkIdOwner()) {
                    card = new Card(Integer.valueOf(editTextCard.getText().toString()),
                            viewCard, owner);
                    card.save();

                    allCard = Card.listAll(Card.class);
                    textView.setText(allCard.toString());
                }
                break;

            case R.id.buttonRead:

                allCard = Card.listAll(Card.class);

                if (allCard.toString() != "[]")
                    textView.setText(allCard.toString());
                else
                    textView.setText("Таблица пустая");
                break;

            case R.id.buttonDeleteAll:
                Card.deleteAll(Card.class);
                textView.setText("Таблица пустая");

                break;

            case R.id.buttonUpdate:

                if (checkEditAdd() && checkIdView() && checkIdOwner() &&
                        checkEditID() && checkId()) {
                    card.number = Integer.parseInt(editTextCard.getText().toString());
                    card.viewCard = viewCard;
                    card.owner = owner;
                    card.save();

                    allCard = Card.listAll(Card.class);
                    textView.setText(allCard.toString());
                }
                break;

            case R.id.buttonDelete:

                if (checkEditID() && checkId()) {
                    card.delete();
                    allCard = Card.listAll(Card.class);
                    textView.setText(allCard.toString());
                }
                break;
        }

    }

    // проверка пустоты едитов добавления
    private boolean checkEditAdd() {
        if (editTextCard.length() != 0 && editTextIdView.length() != 0 &&
                editTextIdOwner.length() != 0) {
            return true;
        } else
            Toast.makeText(getActivity(), "Не все поля заполнены",
                    Toast.LENGTH_SHORT).show();
        return false;
    }

    // проверка есть ли вид карт
    private boolean checkIdView() {
        int id = Integer.parseInt(editTextIdView.getText().toString());
        viewCard = ViewCard.findById(ViewCard.class, id);
        if (viewCard != null)
            return true;
        else
            Toast.makeText(getActivity(), "Неправельный ID Вида карт",
                    Toast.LENGTH_SHORT).show();

        return false;
    }

    // проверка есть ли владелец
    private boolean checkIdOwner() {
        int id = Integer.parseInt(editTextIdOwner.getText().toString());
        owner = Owner.findById(Owner.class, id);
        if (owner != null)
            return true;
        else
            Toast.makeText(getActivity(), "Неправельный ID Владельца",
                    Toast.LENGTH_SHORT).show();

        return false;
    }

    // проверка едита id на пустоту
    private boolean checkEditID() {
        if (editTextIdCard.length() != 0) {
            return true;
        } else
            Toast.makeText(getActivity(), "Вы не заполнили ID",
                    Toast.LENGTH_SHORT).show();
        return false;
    }

    //проверка существует ли id
    public boolean checkId() {
        int id = Integer.parseInt(editTextIdCard.getText().toString());
        card = Card.findById(Card.class, id);

        if (card != null)
            return true;
        else
            Toast.makeText(getActivity(), "Вы ввели неправильный ID",
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

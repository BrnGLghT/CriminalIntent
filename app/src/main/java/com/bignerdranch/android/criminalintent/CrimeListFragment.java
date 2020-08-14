package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container,
                false);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button mCallPoliceButton;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

//        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) { //Конструктор по книге
//            super(inflater.inflate(R.layout.list_item_crime, parent, false));
//
//            itemView.setOnClickListener(this);
//
//            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
//            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
//        }

        public CrimeHolder(View view) { //Хуевый конструктор
            super(view);
            mCallPoliceButton = itemView.findViewById(R.id.call_police_button);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);

            itemView.setOnClickListener(this);

            if (mCallPoliceButton != null) {
                mCallPoliceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(itemView.getContext(),
                                "Calling police!", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }

        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;
        private final int TYPE_ITEM0 = 0;
        private final int TYPE_ITEM1 = 1;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isRequiresPolice())
                return TYPE_ITEM0;
            else
                return TYPE_ITEM1;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v;

            switch (viewType) {
                case TYPE_ITEM0:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crime_police,
                            parent, false);
                    break;
                case TYPE_ITEM1:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crime,
                            parent, false);
                default:
                    v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crime,
                            parent, false);
            }
            return new CrimeHolder(v);

//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}























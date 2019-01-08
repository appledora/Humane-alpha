package com.thegorgeouscows.team.finalrev;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BloodFragment extends Fragment {
    private RecyclerView bloods_list_view;
    List<Blood> bloodList;
    private FirebaseFirestore db;
    private BloodRecyclerAdapter bloodRecyclerAdapter;


    public BloodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_blood, container, false);
        bloodList = new ArrayList<>();
        bloods_list_view = view.findViewById(R.id.blood_list_view);
        bloodRecyclerAdapter = new BloodRecyclerAdapter(bloodList);
        bloods_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        bloods_list_view.setAdapter(bloodRecyclerAdapter);
        bloods_list_view.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();
        db.collection("BLOODLIST").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final QueryDocumentSnapshot document: task.getResult()) {
                        DocumentReference docRef = db.collection("BLOODLIST").document(document.getId());
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Blood blood = documentSnapshot.toObject(Blood.class);
                                bloodList.add(blood);
                                bloodRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                else {
                    Log.d("my", "Error getting documents: ", task.getException());
                }
            }
        });
        return view;
    }

}

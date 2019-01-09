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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
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
public class VolunteerFragment extends Fragment {

    private RecyclerView vols_list_view;
    List <Volunteer> volunteerList;
    VolunteerRecyclerAdapter volunteerRecyclerAdapter;
    FirebaseAuth firebaseAuth;
    String currentUserID,orgName;
    DatabaseReference ref;

    public VolunteerFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        orgName = getArguments().getString("org");
        final View view = inflater.inflate(R.layout.fragment_volunteer, container, false);
        volunteerList = new ArrayList<>();
        vols_list_view = view.findViewById(R.id.vol_list_view);
        volunteerRecyclerAdapter = new VolunteerRecyclerAdapter(volunteerList);
        vols_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        vols_list_view.setAdapter(volunteerRecyclerAdapter);
        vols_list_view.setHasFixedSize(true);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        final CollectionReference colRef = rootRef.collection("OrgList").document(orgName).collection("Volunteers");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (final QueryDocumentSnapshot document: task.getResult()){
                    DocumentReference docRef = colRef.document(document.getId());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Volunteer vol = documentSnapshot.toObject(Volunteer.class);
                            volunteerList.add(vol);
                            Log.i("my: ","added VOL");
                            volunteerRecyclerAdapter.notifyDataSetChanged();
                            Log.i("my","Notified Adapter");
                        }
                    });

                }
            }
        });
        return view;

    }

}


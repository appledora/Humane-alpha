package com.thegorgeouscows.team.finalrev;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
public class ClothFragment extends Fragment {
    private RecyclerView clothes_list_view;
    List<Clothes> clothes_list;
    private FirebaseFirestore db;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private String uid,tag;
    private ClothRecyclerAdapter clothRecyclerAdapter;
    private ClothRecyclerAdapterOrg clothRecyclerAdapterOrg;


    public ClothFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.fragment_cloth,container,false);
        clothes_list = new ArrayList<>();
        clothRecyclerAdapter = new ClothRecyclerAdapter(clothes_list);

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tag = dataSnapshot.child("ID").getValue(String.class);


                if(tag.equals("Organization")){

                    clothes_list_view = view.findViewById(R.id.cloth_list_view);
                    clothRecyclerAdapterOrg = new ClothRecyclerAdapterOrg(clothes_list);
                    clothes_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
                   clothes_list_view.setAdapter(clothRecyclerAdapterOrg);
                    clothes_list_view.setHasFixedSize(true);

                }
                else if (tag.equals("Donator")){

                    clothes_list_view = view.findViewById(R.id.cloth_list_view);
                    clothRecyclerAdapter = new ClothRecyclerAdapter(clothes_list);
                    clothes_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
                    clothes_list_view.setAdapter(clothRecyclerAdapter);
                    clothes_list_view.setHasFixedSize(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        db = FirebaseFirestore.getInstance();
        db.collection("ClothPosts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final QueryDocumentSnapshot document: task.getResult()){
                        DocumentReference docRef = db.collection("ClothPosts").document(document.getId());
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Clothes cloth = documentSnapshot.toObject(Clothes.class);
                                clothes_list.add(cloth);
                                clothRecyclerAdapter.notifyDataSetChanged();
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

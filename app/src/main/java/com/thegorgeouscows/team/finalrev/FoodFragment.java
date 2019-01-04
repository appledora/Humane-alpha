package com.thegorgeouscows.team.finalrev;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
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
public class FoodFragment extends Fragment {
    private RecyclerView post_list_view;
    List<Posts>posts_list;
    private FirebaseFirestore firebaseFirestore,db;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private String uid,tag;
    private FoodRecyclerAdapter foodRecyclerAdapter;
    private FoodRecyclerAdapterDon foodRecyclerAdapterDon;
    CardView cardView;


    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("my: ","FOOD FRAGMENT");
        final View view = inflater.inflate(R.layout.fragment_feed,container,false);
        posts_list = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        Log.i("my UID",uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("my TAG in single:", "got here");
                    tag = dataSnapshot.child("ID").getValue(String.class);


                    if(tag.equals("Organization")){
                        cardView = view.findViewById(R.id.main_blog_post);
                        post_list_view = view.findViewById(R.id.blog_list_view);
                        foodRecyclerAdapter = new FoodRecyclerAdapter(posts_list);
                        post_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
                        post_list_view.setAdapter(foodRecyclerAdapter);
                        post_list_view.setHasFixedSize(true);

                    }
                    else if (tag.equals("Donator")){
                        cardView = view.findViewById(R.id.main_blog_post_don);
                        post_list_view = view.findViewById(R.id.blog_list_view);
                        foodRecyclerAdapterDon = new FoodRecyclerAdapterDon(posts_list);
                        post_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
                        post_list_view.setAdapter(foodRecyclerAdapterDon);
                        post_list_view.setHasFixedSize(true);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("my TAG in single", "got here cancelled");
            }
        });



        /*post_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        post_list_view.setAdapter(foodRecyclerAdapter);
        post_list_view.setHasFixedSize(true);*/


        db = FirebaseFirestore.getInstance();
        db.collection("POSTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task <QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final QueryDocumentSnapshot document: task.getResult()){
                        DocumentReference docRef = db.collection("POSTS").document(document.getId());
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Posts post = documentSnapshot.toObject(Posts.class);
                                posts_list.add(post);
                                if (tag.equals("Organization")) {
                                    foodRecyclerAdapter.notifyDataSetChanged();
                                }
                                else if (tag.equals("Donator")){
                                    foodRecyclerAdapterDon.notifyDataSetChanged();
                                }
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

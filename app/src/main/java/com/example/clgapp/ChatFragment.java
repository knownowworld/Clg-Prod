package com.example.clgapp;

import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.library.bubbleview.BubbleTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int SIGN_IN_REQUEST_CODE = 1;
    RelativeLayout activity_main;
    //Add Emojicon
    EmojiconEditText emojiconEditText;
    ImageView emojiButton, submitButton;
    EmojIconActions emojIconActions;
    RecyclerView listOfMessage;
    Query query;
    FirebaseRecyclerOptions<ChatMessage> options;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    //Variables
    private FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder> adapter;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);


        activity_main = rootView.findViewById(R.id.activity_main);
        listOfMessage = rootView.findViewById(R.id.list_of_message);
        listOfMessage.setLayoutManager(new LinearLayoutManager(getContext()));

        query = FirebaseDatabase.getInstance().getReference("chat");
        options = new FirebaseRecyclerOptions.Builder<ChatMessage>()
                .setQuery(query, ChatMessage.class)
                .build();

        //Add Emoji
        emojiButton = rootView.findViewById(R.id.emoji_button);
        submitButton = rootView.findViewById(R.id.submit_button);
        emojiconEditText = rootView.findViewById(R.id.emojicon_edit_text);
        emojIconActions = new EmojIconActions(getContext(), activity_main, emojiButton, emojiconEditText);
        emojIconActions.ShowEmojicon();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("chat").push().setValue(new ChatMessage(emojiconEditText.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                emojiconEditText.setText("");
                emojiconEditText.requestFocus();
            }
        });


        displayChatMessage();

        return rootView;
    }


    private void displayChatMessage() {


        adapter = new FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull ChatMessage model) {
                holder.messageText.setText(model.getMessageText());
                holder.messageUser.setText(model.getMessageUser());
                holder.messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));

            }

            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new ChatViewHolder(LayoutInflater.from(getContext())
                        .inflate(R.layout.chat_items, viewGroup, false));
            }
        };


        listOfMessage.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
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

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, messageUser, messageTime;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = (BubbleTextView) itemView.findViewById(R.id.message_text);
            messageUser = itemView.findViewById(R.id.message_user);
            messageTime = itemView.findViewById(R.id.message_time);
        }
    }
}

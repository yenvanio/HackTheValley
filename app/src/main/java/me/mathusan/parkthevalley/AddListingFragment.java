package me.mathusan.parkthevalley;


import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddListingFragment extends Fragment {
    private AlertDialog dialog;


    public AddListingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_listing, container, false);
    }


    public void addListing (View view) {

        String phonenum = ((EditText) view.findViewById(R.id.adding_phonenum)).toString();
        String cost = ((EditText) view.findViewById(R.id.adding_cost)).toString();
        String moreinfo = ((EditText) view.findViewById(R.id.adding_moreinfo)).toString();

        //Check for no empty enties.
        boolean invalid = phonenum.isEmpty()||cost.isEmpty()||moreinfo.isEmpty();

        if (!invalid) {
            // do something



        }
//        else {
//            if (phonenum.isEmpty()) {
//                dialog.setTitle("Empty Phone Number");
//                dialog.setMessage("Phone Number is not suppose to be empty.");
//                dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                dialog.show();
//            } else if (cost.isEmpty()) {
//                dialog.setTitle("Empty Pricing");
//                dialog.setMessage("Cost is not suppose to be empty.");
//                dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                dialog.show();
//            }
//
//        }


    }

}

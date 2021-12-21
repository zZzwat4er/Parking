package com.example.parking.ui.user_info.fio_change;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.parking.LoginActivity;
import com.example.parking.R;
import com.example.parking.utility.AccountHolder;
import com.example.parking.utility.StringChecker;
import com.example.parking.utility.server_comunnication_api.ServerReqCodes;

public class FFIOChange extends Fragment {

    private TextView firstname;
    private TextView secondname;
    private TextView lastname;
    private Menu mMenu;
    private String[] initStrings = {
            AccountHolder.account.mFirstName,
            AccountHolder.account.mSecondName,
            AccountHolder.account.mLastName
    };
    private FIOCangeVM vm;

    private final String TAG = "FIO change";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.f_user_info_fio_change, container, false);

        firstname = root.findViewById(R.id.user_fio_edit_firstname_layout_text);
        secondname = root.findViewById(R.id.user_fio_edit_secondname_layout_text);
        lastname = root.findViewById(R.id.user_fio_edit_lastname_layout_text);

        vm = new ViewModelProvider(this).get(FIOCangeVM.class);
        vm.getOutPutCode().observe(getActivity(), new Observer<ServerReqCodes>() {
            @Override
            public void onChanged(ServerReqCodes serverReqCodes) {
                switch (serverReqCodes){
                    case SUC:
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();
                        break;

                    case ERR:
                        Integer err = vm.getErrorCode();
                        if(err == 2) {
                            AccountHolder.dataFlesh(getActivity().getApplication());
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        break;

                    case NONE:
                    default:
                        break;
                }
            }
        });

        firstname.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {updateApprove();}
        });
        secondname.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {updateApprove();}
        });
        lastname.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {updateApprove();}
        });

        firstname.setText(initStrings[0]);
        secondname.setText(initStrings[1]);
        lastname.setText(initStrings[2]);

        setHasOptionsMenu(true);

        updateApprove();

        return root;
    }


    private void updateApprove(){
        if(mMenu == null) return;
        if(!firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty()){
            Log.d(TAG, "first if");
            if(!initStrings[0].equals(firstname.getText().toString()) ||
                !initStrings[1].equals(secondname.getText().toString()) ||
                !initStrings[2].equals(lastname.getText().toString())){
                Log.d(TAG, "second if");
                if(!StringChecker.isName(firstname.getText().toString()) ||
                    !StringChecker.isName(lastname.getText().toString()) ||
                        (!StringChecker.isName(secondname.getText().toString()) &&
                                !secondname.getText().toString().isEmpty())){
                    Log.d(TAG, "third if");
                    mMenu.findItem(R.id.approve).setEnabled(false);
                    return;
                }
                mMenu.findItem(R.id.approve).setEnabled(true);
                return;
            }
        }
        mMenu.findItem(R.id.approve).setEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.action_bar_appr, menu);
        mMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.approve){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            if(!firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty()){
                vm.serverRequest(getActivity(), firstname.getText().toString(),
                        secondname.getText().toString(), lastname.getText().toString());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

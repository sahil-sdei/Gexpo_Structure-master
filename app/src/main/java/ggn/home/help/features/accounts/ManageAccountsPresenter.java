package ggn.home.help.features.accounts;


import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.regex.Pattern;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.utils.DialogHelper;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.AccountTransferRequest;
import ggn.home.help.web.request.ChildAccountsRequest;
import ggn.home.help.web.request.DeleteChildRequest;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.Child;
import ggn.home.help.web.response.ChildAccountsResponse;

public class ManageAccountsPresenter extends BasePresenter<ManageAccountsView> implements ManageAccountsAdapterBinder {

    public void getChildAccounts() {
        ChildAccountsRequest childAccountsRequest = new ChildAccountsRequest();
        childAccountsRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        childAccountsRequest.token = getView().getLocalData().getAuthToken();
        childAccountsRequest.page = 1;

        Gson gson = new Gson();

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getChildAccounts(gson.toJson(childAccountsRequest)), new CallBackG<ChildAccountsResponse>() {
            @Override
            public void callBack(ChildAccountsResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showChildAccounts(output);
                else
                    getView().displayError(output.message);
            }
        });
    }

    @Override
    public void onTransfer(final Child child) {
        DialogHelper.getInstance().showWithAction(getView().getActivityG(), "Do you want to transfer this child account?",
                new CallBackG<String>() {
                    @Override
                    public void callBack(String output) {
                        showEnterEmailDialog(child);
                    }
                });
    }

    private void showEnterEmailDialog(final Child child){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getView().getActivityG(), R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) getView().getActivityG().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_add_sub_category, null);
        final EditText editText = dialogView.findViewById(R.id.editTextName);
        final TextInputLayout textInputLayout= dialogView.findViewById(R.id.textInputLayout);
        textInputLayout.setHint("Email");
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setTitle("Enter your child's email");
        dialogBuilder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = editText.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    getView().displayError("Please enter email.");
                }if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    getView().displayError("Please enter valid email.");
                } else {
                    transferAccount(child, email);
                }
            }
        });
        dialogBuilder.setNegativeButton("CANCEL", null);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void transferAccount(Child child, String email) {
        AccountTransferRequest accountTransferRequest = new AccountTransferRequest();
        accountTransferRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        accountTransferRequest.token = getView().getLocalData().getAuthToken();
        accountTransferRequest.email = email;
        accountTransferRequest.childId = child.id;

        Gson gson = new Gson();

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .accountTransfer(gson.toJson(accountTransferRequest)), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().accountTransferSuccessfully(output);
                getView().displayError(output.message);
            }
        });
    }

    @Override
    public void onDelete(final Child child) {
        DialogHelper.getInstance().showWithAction(getView().getActivityG(), "Do you want to delete this child account?",
                new CallBackG<String>() {
                    @Override
                    public void callBack(String output) {
                        deleteAccount(child);
                    }
                });
    }

    private void deleteAccount(Child child) {
        DeleteChildRequest deleteChildRequest = new DeleteChildRequest();
        deleteChildRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        deleteChildRequest.token = getView().getLocalData().getAuthToken();
        deleteChildRequest.childId = Integer.parseInt(child.id);

        if(child.id.equalsIgnoreCase(getView().getLocalData().getChildId())){
            getView().getLocalData().clearChild();
        }

        Gson gson = new Gson();

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .deleteChildAccount(gson.toJson(deleteChildRequest)), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().accountDeletedSuccessfully(output);
                getView().displayError(output.message);
            }
        });
    }
}

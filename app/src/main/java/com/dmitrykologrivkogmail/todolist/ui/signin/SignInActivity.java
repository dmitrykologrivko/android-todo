package com.dmitrykologrivkogmail.todolist.ui.signin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.TodoApplication;
import com.dmitrykologrivkogmail.todolist.injection.component.SignInComponent;
import com.dmitrykologrivkogmail.todolist.ui.base.BaseActivity;
import com.dmitrykologrivkogmail.todolist.ui.tasks.TasksActivity;
import com.dmitrykologrivkogmail.todolist.util.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity<SignInView, SignInPresenter> implements SignInView {

    private static final int LAYOUT = R.layout.activity_sign_in;

    private final SignInComponent mSignInComponent =
            TodoApplication.getApplicationComponent().plusSignInComponent();

    ProgressDialog mProgressDialog;

    @BindView(R.id.edit_username)
    EditText mEditUsername;

    @BindView(R.id.edit_password)
    EditText mEditPassword;

    public static Intent getStartIntent(Context ctx) {
        return new Intent(ctx, SignInActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_submit)
    void submit() {
        getPresenter().onSubmitButtonClick();
    }

    @NonNull
    @Override
    public SignInPresenter createPresenter() {
        return mSignInComponent.signInPresenter();
    }

    public String getEditUsername() {
        return mEditUsername.getText().toString();
    }

    public String getEditPassword() {
        return mEditPassword.getText().toString();
    }

    @Override
    public void startTasksActivity() {
        startActivity(TasksActivity.getStartIntent(this));
    }

    @Override
    public void showProgress() {
        mProgressDialog =
                DialogFactory.createProgressDialog(this, R.string.msg_please_wait);
        mProgressDialog.show();
    }

    @Override
    public void dismissProgress() {
        mProgressDialog.cancel();
        mProgressDialog = null;
    }

    @Override
    public void showError(int messageResource) {
        DialogFactory.createSimpleOkErrorDialog(this,
                R.string.dialog_error_title,
                messageResource)
                .show();
    }

    @Override
    public void showError(String message) {
        DialogFactory.createSimpleOkErrorDialog(this,
                getString(R.string.dialog_error_title),
                message)
                .show();
    }
}

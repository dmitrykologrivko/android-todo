package com.dmitrykologrivkogmail.todolist.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.view.View;

import com.dmitrykologrivkogmail.todolist.R;

public final class DialogFactory {

    public static Dialog createSimpleOkErrorDialog(Context context, String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(R.string.dialog_action_ok, null);
        return alertDialog.create();
    }

    public static Dialog createSimpleOkErrorDialog(Context context,
                                                   @StringRes int titleResource,
                                                   @StringRes int messageResource) {

        return createSimpleOkErrorDialog(context,
                context.getString(titleResource),
                context.getString(messageResource));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    public static Dialog createGenericErrorDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.dialog_error_title))
                .setMessage(message)
                .setNeutralButton(R.string.dialog_action_ok, null);
        return alertDialog.create();
    }

    public static Dialog createGenericErrorDialog(Context context, @StringRes int messageResource) {
        return createGenericErrorDialog(context, context.getString(messageResource));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    public static ProgressDialog createProgressDialog(Context context,
                                                      @StringRes int messageResource) {
        return createProgressDialog(context, context.getString(messageResource));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    public static Dialog createDialog(Context context,
                                      String title,
                                      String message,
                                      String positive,
                                      DialogInterface.OnClickListener positiveClick) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive, positiveClick);
        return alertDialog.create();
    }

    public static Dialog createDialog(Context context,
                                      String title,
                                      String message,
                                      String negative,
                                      String positive,
                                      DialogInterface.OnClickListener negativeClick,
                                      DialogInterface.OnClickListener positiveClick) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(negative, negativeClick)
                .setPositiveButton(positive, positiveClick);
        return alertDialog.create();
    }

    public static Dialog createDialog(Context context,
                                      String title,
                                      String message,
                                      String neutral,
                                      String negative,
                                      String positive,
                                      DialogInterface.OnClickListener neutralClick,
                                      DialogInterface.OnClickListener negativeClick,
                                      DialogInterface.OnClickListener positiveClick) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(neutral, neutralClick)
                .setNegativeButton(negative, negativeClick)
                .setPositiveButton(positive, positiveClick);
        return alertDialog.create();
    }

    public static Dialog createDialog(Context context,
                                      @StringRes int titleResource,
                                      @StringRes int messageResource,
                                      @StringRes int positiveResource,
                                      DialogInterface.OnClickListener positiveClick) {

        return createDialog(context,
                context.getString(titleResource),
                context.getString(messageResource),
                context.getString(positiveResource),
                positiveClick);
    }

    public static Dialog createDialog(Context context,
                                      @StringRes int titleResource,
                                      @StringRes int messageResource,
                                      @StringRes int negativeResource,
                                      @StringRes int positiveResource,
                                      DialogInterface.OnClickListener negativeClick,
                                      DialogInterface.OnClickListener positiveClick) {

        return createDialog(context,
                context.getString(titleResource),
                context.getString(messageResource),
                context.getString(negativeResource),
                context.getString(positiveResource),
                negativeClick,
                positiveClick);
    }

    public static Dialog createDialog(Context context,
                                      @StringRes int titleResource,
                                      @StringRes int messageResource,
                                      @StringRes int neutralResource,
                                      @StringRes int negativeResource,
                                      @StringRes int positiveResource,
                                      DialogInterface.OnClickListener neutralClick,
                                      DialogInterface.OnClickListener negativeClick,
                                      DialogInterface.OnClickListener positiveClick) {

        return createDialog(context,
                context.getString(titleResource),
                context.getString(messageResource),
                context.getString(neutralResource),
                context.getString(negativeResource),
                context.getString(positiveResource),
                neutralClick,
                negativeClick,
                positiveClick);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    public static Dialog createViewDialog(Context context,
                                          View view,
                                          String title,
                                          String positive,
                                          DialogInterface.OnClickListener positiveClick) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setTitle(title)
                .setPositiveButton(positive, positiveClick);
        return alertDialog.create();
    }

    public static Dialog createViewDialog(Context context,
                                          View view,
                                          String title,
                                          String negative,
                                          String positive,
                                          DialogInterface.OnClickListener negativeClick,
                                          DialogInterface.OnClickListener positiveClick) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setTitle(title)
                .setNegativeButton(negative, negativeClick)
                .setPositiveButton(positive, positiveClick);
        return alertDialog.create();
    }

    public static Dialog createViewDialog(Context context,
                                          View view,
                                          String title,
                                          String neutral,
                                          String negative,
                                          String positive,
                                          DialogInterface.OnClickListener neutralClick,
                                          DialogInterface.OnClickListener negativeClick,
                                          DialogInterface.OnClickListener positiveClick) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setTitle(title)
                .setNeutralButton(neutral, neutralClick)
                .setNegativeButton(negative, negativeClick)
                .setPositiveButton(positive, positiveClick);
        return alertDialog.create();
    }

    public static Dialog createViewDialog(Context context,
                                          View view,
                                          @StringRes int titleResource,
                                          @StringRes int positiveResource,
                                          DialogInterface.OnClickListener positiveClick) {

        return createViewDialog(context,
                view,
                context.getString(titleResource),
                context.getString(positiveResource),
                positiveClick);
    }

    public static Dialog createViewDialog(Context context,
                                          View view,
                                          @StringRes int titleResource,
                                          @StringRes int negativeResource,
                                          @StringRes int positiveResource,
                                          DialogInterface.OnClickListener negativeClick,
                                          DialogInterface.OnClickListener positiveClick) {

        return createViewDialog(context,
                view,
                context.getString(titleResource),
                context.getString(negativeResource),
                context.getString(positiveResource),
                negativeClick,
                positiveClick);
    }

    public static Dialog createViewDialog(Context context,
                                          View view,
                                          @StringRes int titleResource,
                                          @StringRes int neutralResource,
                                          @StringRes int negativeResource,
                                          @StringRes int positiveResource,
                                          DialogInterface.OnClickListener neutralClick,
                                          DialogInterface.OnClickListener negativeClick,
                                          DialogInterface.OnClickListener positiveClick) {

        return createViewDialog(context,
                view,
                context.getString(titleResource),
                context.getString(neutralResource),
                context.getString(negativeResource),
                context.getString(positiveResource),
                neutralClick,
                negativeClick,
                positiveClick);
    }
}

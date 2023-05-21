package com.example.expansions.construcciones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


class ConstructionManager {
    static final int CARRETERA = 0, POBLADO = 1, CIUDAD = 2, MAGIA = 3;
    private static ConstructionManager[] managers = new ConstructionManager[4];

    private Construction construction;
    private int amount;
    private TextView uiNum;
    private ImageView img;
    private LinearLayout info;
    private Context context;

    private ConstructionManager(Construction construction, LinearLayout linearLayout, ImageView imageView, TextView textView, final View moreView, Context context) {
        this.uiNum = textView;
        this.construction = construction;
        this.img = imageView;
        this.context = context;
        this.info = linearLayout;
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        moreView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View pView) {
                // Do something when your hold starts here.
                info.setVisibility(View.VISIBLE);
                moreView.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View pView, MotionEvent pEvent) {
                        pView.onTouchEvent(pEvent);
                        // We're only interested in when the button is released.
                        if (pEvent.getAction() == MotionEvent.ACTION_UP) {
                            // We're only interested in anything if our speak button is currently pressed.
                            info.setVisibility(View.GONE);
                            moreView.setOnTouchListener(null);
                        }
                        return false;
                    }
                });
                return true;
            }
        });
    }

    static ConstructionManager iniConstructionManager(int type, LinearLayout linearLayout, ImageView imageView, TextView textView,
                                                      View moreView, Context context) {
        Construction construction;
        switch (type) {
            case 0:
                construction = new Construction(1, 1, 0, 0, 0);
                break;
            case 1:
                construction = new Construction(1, 1, 1, 0, 1);
                break;
            case 2:
                construction = new Construction(0, 0, 0, 3, 2);
                break;
            case 3:
                construction = new Construction(0, 0, 1, 1, 1);
                break;
            default:
                construction = new Construction(0, 0, 0, 0, 0);
        }
        managers[type] = new ConstructionManager(construction, linearLayout, imageView, textView, moreView, context);
        return managers[type];
    }

    static ConstructionManager getConstructionManager(int type) {
        return managers[type];
    }

    private void add() {
        if (construction.canBeConstructed()) {
            AlertDialog.Builder myBuild = new AlertDialog.Builder(context);
            myBuild.setMessage("Vas a realizar una construcción. Una vez hecho esto ya no podrás volver atrás");
            myBuild.setTitle(Html.fromHtml("<font color='#a32d47'>Construcción</font>"));
            myBuild.setPositiveButton(Html.fromHtml("<font color='#a32d47'>Construir</font>"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    amount++;
                    construction.construct();
                    refresh();
                    dialog.dismiss();
                }
            });
            myBuild.setNegativeButton(Html.fromHtml("<font color='#a32d47'>Cancelar</font>"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = myBuild.create();
            dialog.show();
        }

    }


    private void showInfo() {
        info.setVisibility(View.VISIBLE);
    }

    int getAmount() {
        return amount;
    }

    Construction getConstruction() {
        return construction;
    }

    ImageView getImageView() {
        return img;
    }

    private void setActivated(boolean mode) {
        if (mode) {
            getImageView().setAlpha((float) 255 / 255);
        } else {
            getImageView().setAlpha((float) 127 / 255);
        }
    }

    private void refresh() {
        uiNum.setText(String.valueOf(amount));

    }

    static void refreshAll() {
        for (ConstructionManager cM :
                managers) {
            cM.setActivated(cM.getConstruction().canBeConstructed());
        }
    }
}

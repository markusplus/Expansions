package com.example.expansions.construcciones;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import static com.example.expansions.construcciones.ConstructionManager.*;

class ResourceManager {
   static final int TRONCO = 0, LADRILLO = 1, OVEJA = 2, TRIGO = 3, ROCA = 4;
   private static ResourceManager[] managers = new ResourceManager[5];

   private int amount;
   private TextView uiNum;

   private ResourceManager(TextView textView, View moreView, View subView) {
      this.uiNum = textView;
      moreView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            add(1);
         }
      });
      subView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            substract(1);
         }
      });
   }

   static ResourceManager iniResourceManager(int type, TextView textView,
                                             View moreView, View subView) {
      managers[type] = new ResourceManager(textView, moreView, subView);
      return managers[type];
   }

   static ResourceManager getResourceManager(int type) {
      return managers[type];
   }

   private void add(int sum) {
      if (amount < 19) {
         amount += sum;
         refreshTextView();
         refreshAll();
      }
   }
   void substract(int sum) {
      if (amount > 0) {
         amount -= sum;
         refreshTextView();
         refreshAll();
      }
   }

   int getAmount() {
      return amount;
   }

   private void refreshTextView() {
      uiNum.setText(String.valueOf(amount));
      }


}

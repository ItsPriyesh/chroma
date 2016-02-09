package me.priyesh.chromasample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.priyesh.chroma.ChromaDialog;

public class MainActivity extends AppCompatActivity {

  private Toolbar mToolbar;
  private int mColor;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);

    mColor = ContextCompat.getColor(this, R.color.colorPrimary);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        showColorPickerDialog();
      }
    });
  }

  private void showColorPickerDialog() {
    ChromaDialog.Companion
        .with(this)
        .initialColor(mColor)
        .onColorSelected(new ChromaDialog.ColorSelectListener() {
          @Override public void onColorSelected(int color) {
            animateToolbarColor(mColor, color);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
              getWindow().setStatusBarColor(darkenColor(color));
            }
            mColor = color;
          }
        }).show();
  }

  private void animateToolbarColor(int start, int end) {
    final TransitionDrawable transition = new TransitionDrawable(new ColorDrawable[]{
        new ColorDrawable(start), new ColorDrawable(end)
    });

    mToolbar.setBackground(transition);
    transition.startTransition(300);
  }

  private int darkenColor(int color) {
    float[] hsv = new float[3];
    Color.colorToHSV(color, hsv);
    hsv[2] *= 0.8f;
    return Color.HSVToColor(hsv);
  }
}

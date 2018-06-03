package me.priyesh.chromasample;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;
import me.priyesh.chroma.ColorSelectListener;

public class SampleActivity extends AppCompatActivity {

    private static final String KEY_COLOR = "extra_color";
    private static final String KEY_COLOR_MODE = "extra_color_mode";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_view)
    TextView mColorTextView;
    @BindView(R.id.color_mode_spinner)
    Spinner mColorModeSpinner;

    private int mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        ButterKnife.bind(SampleActivity.this);

        setSupportActionBar(mToolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(SampleActivity.this, R.color.colorPrimaryDark));
        }

        mColor = savedInstanceState != null
                ? savedInstanceState.getInt(KEY_COLOR)
                : ContextCompat.getColor(this, R.color.colorPrimary);

        ColorMode colorMode = savedInstanceState != null
                ? ColorMode.valueOf(savedInstanceState.getString(KEY_COLOR_MODE))
                : ColorMode.RGB;


        ArrayAdapter<ColorMode> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, ColorMode.values());

        mColorModeSpinner.setAdapter(adapter);
        mColorModeSpinner.setSelection(adapter.getPosition(colorMode));

        updateTextView(mColor);
        updateToolbar(mColor, mColor);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_COLOR, mColor);
        outState.putString(KEY_COLOR_MODE, mColorModeSpinner.getSelectedItem().toString());
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        showColorPickerDialog();
    }

    private void showColorPickerDialog() {
        new ChromaDialog.Builder()
                .initialColor(mColor)
                .colorMode((ColorMode) mColorModeSpinner.getSelectedItem())
                .onColorSelected(new ColorSelectListener() {
                    @Override
                    public void onColorSelected(int color) {
                        updateTextView(color);
                        updateToolbar(mColor, color);
                        mColor = color;
                    }
                })
                .setPositiveButtonText(getString(R.string.button_positive))
                .setNegativeButtonText(getString(R.string.button_negative))
                .create()
                .show(getSupportFragmentManager(), "dialog");
    }

    private void updateTextView(int color) {
        mColorTextView.setText(String.format("#%06X", 0xFFFFFF & color));
    }

    private void updateToolbar(int oldColor, int newColor) {
        final TransitionDrawable transition = new TransitionDrawable(new ColorDrawable[]{
                new ColorDrawable(oldColor), new ColorDrawable(newColor)
        });

        mToolbar.setBackground(transition);
        transition.startTransition(300);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(newColor);
        }
    }
}

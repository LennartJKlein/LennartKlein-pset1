package nl.lennartklein.lennartklein_pset1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    // Set global variables for xml-elements
    int canvasId;
    RelativeLayout canvas;
    int formId1;
    LinearLayout form1;
    int formId2;
    LinearLayout form2;
    int imageCount;
    int boxCount1;
    int boxCount2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get id's of xml-elements
        canvasId = getResources().getIdentifier("canvas", "id", getPackageName());
        canvas = findViewById(canvasId);
        formId1 = getResources().getIdentifier("form1", "id", getPackageName());
        form1 = findViewById(formId1);
        formId2 = getResources().getIdentifier("form2", "id", getPackageName());
        form2 = findViewById(formId2);

        // Get amount of images on canvas and checkboxes in forms
        imageCount = canvas.getChildCount();
        boxCount1 = form1.getChildCount();
        boxCount2 = form2.getChildCount();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Loop through every imageView in the canvas (but not the first: 'body')
        for (int i = 1; i < imageCount; i++) {
            // Log the visibility of this image in the outState bundle
            View v = canvas.getChildAt(i);
            outState.putInt(Integer.toString(v.getId()), v.getVisibility());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        // Loop through every key in the saved bundle (but not the first)
        int counter = 0;
        for (String key : inState.keySet()) {
            if (counter != 0) {
                int visibility = inState.getInt(key);
                int keyInt = Integer.parseInt(key);

                ImageView clothing = findViewById(keyInt);

                clothing.setVisibility(visibility);
            }
            counter++;
        }
    }

    /**
     * Toggle the visibility of an ImageView, related to the ID of a clicked checkbox
     * @param box - Checkbox that invoked this function
     */
    public void changeAppearance(View box) {
        boolean checked = ((CheckBox) box).isChecked();

        // Get referenced name of the clothing
        String boxName = getResources().getResourceEntryName(box.getId());
        String[] boxNames = boxName.split("-");
        String clothingName = boxNames[1];

        // Find the clothing for this name
        int clothingId = getResources().getIdentifier(clothingName, "id", getPackageName());
        ImageView clothing = findViewById(clothingId);

        // Toggle the visibility of this clothing
        if (checked) {
            clothing.setVisibility(View.VISIBLE);
        } else {
            clothing.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Reset the canvas and checkboxes
     * @param button - Clicked button that invoked this function
     */
    public void resetAppearance(View button) {
        // Loop through every imageView in the canvas (but not the first: 'body')
        for (int i = 1; i < imageCount; i++) {
            View v = canvas.getChildAt(i);
            v.setVisibility(View.INVISIBLE);
        }

        // Loop through every checkbox in the two form fields
        for (int i = 0; i < boxCount1; i++) {
            // Uncheck this checkbox
            CheckBox box = (CheckBox) form1.getChildAt(i);
            box.setChecked(false);
        }
        for (int i = 0; i < boxCount2; i++) {
            // Uncheck this checkbox
            CheckBox box = (CheckBox) form2.getChildAt(i);
            box.setChecked(false);
        }
    }
}


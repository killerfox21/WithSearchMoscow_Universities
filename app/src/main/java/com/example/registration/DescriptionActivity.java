package com.example.registration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
public class DescriptionActivity extends AppCompatActivity {

    private ImageView logo_d;
    private ImageView imageview;
    private TextView descriptionTextView, textViewUniversityTitle;

    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private FloatingActionButton location_button;

    private Menu collapsedMenu;
    private boolean appBarExpanded = true;

    String title;
    String title_descr;
    String descr;
    String site;
    String logo;
    String image;
    Double x;
    Double y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_toolbar);

        logo_d = findViewById(R.id.logo_d);
        imageview = findViewById(R.id.image_d);
        descriptionTextView = findViewById(R.id.text_d);
        textViewUniversityTitle = findViewById(R.id.textViewUniversityTitle);
        location_button = findViewById(R.id.location_button);

        Intent fromMenuActivity = getIntent();
        title = fromMenuActivity.getStringExtra("title");
        title_descr = fromMenuActivity.getStringExtra("title_descr");
        descr = fromMenuActivity.getStringExtra("descr");
        logo = fromMenuActivity.getStringExtra("logo");
        image = fromMenuActivity.getStringExtra("image");
        site = fromMenuActivity.getStringExtra("site");
        x = fromMenuActivity.getDoubleExtra("x", 0);
        y = fromMenuActivity.getDoubleExtra("y", 0);

        Picasso.with(DescriptionActivity.this).load(image).into(imageview);
        Picasso.with(DescriptionActivity.this).load(logo).into(logo_d);
        descriptionTextView.setText(descr);

        final Toolbar toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = findViewById(R.id.appbar);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(title);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(DescriptionActivity.class.getSimpleName(), "onOffsetChanged: verticalOffset: " + verticalOffset);

                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });

        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocation(v);
            }
        });
    }

    private void openLocation() {
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 1)) {
            collapsedMenu.add("Add")
                    .setIcon(R.drawable.ic_message_black_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;

        }
        if (item.getTitle() == "Add") {
            openChat();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        collapsedMenu = menu;
        return true;
    }
    public void openChat() {
        Intent toChatActivity = new Intent(this, ChatActivity.class);
        toChatActivity.putExtra("title", title);
        startActivity(toChatActivity);
    }
    public void showBalls(View view) {
        Intent toBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
        toBrowser.putExtra("url", site);
        startActivity(Intent.createChooser(toBrowser, "Browser"));
    }
    public void showLocation(View view) {
        Intent toMapActivity = new Intent(this, MapActivity.class);
        toMapActivity.putExtra("title", title);
        toMapActivity.putExtra("x", x);
        toMapActivity.putExtra("y", y);
        toMapActivity.putExtra("logo", logo);
        toMapActivity.putExtra("descr", descr);
        toMapActivity.putExtra("image", image);
        toMapActivity.putExtra("site", site);
        startActivity(toMapActivity);
    }
    public void goBack() {
        Intent toMenuActivity = new Intent(this, MenuActivity.class);
        startActivity(toMenuActivity);
    }
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            hideSystemUI();
//        }
//    }
//    private void hideSystemUI() {
//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_IMMERSIVE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
//    }

}
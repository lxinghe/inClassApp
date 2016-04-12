package com.example.lu_xi.hw9_xinghe_lu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
//import com.firebase.client.core.view.View;


public class TaskTwoActivity extends AppCompatActivity
        implements RecyclerViewFragment.onListItemClickListener
{
    Fragment mContent;
    Toolbar mToolBar;
    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_two);

        if(savedInstanceState!=null){
            mContent=getSupportFragmentManager().getFragment(savedInstanceState,"mContent");
        }
        else {
            mContent=RecyclerViewFragment.newInstance();
        }


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mContent)
                .commit();

        mToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mActionBar=getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.task2_toolbar_menu,menu);
        return true;
    }



    public void onListItemClickListener(int position, String movieID, View sharedImage){
        MovieFragment fragment = MovieFragment.newInstance(position, movieID);
        fragment.setEnterTransition(new Slide(Gravity.LEFT));
        fragment.setSharedElementEnterTransition(new DetailsTransition());
        fragment.setSharedElementReturnTransition(new DetailsTransition());
        getSupportFragmentManager().beginTransaction().addSharedElement(sharedImage, sharedImage.getTransitionName())
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }
}

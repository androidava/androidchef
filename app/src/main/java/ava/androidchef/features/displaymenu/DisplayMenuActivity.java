package ava.androidchef.features.displaymenu;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ava.androidchef.R;
import ava.androidchef.features.addrecipe.EnterRecipeFragment;
import ava.androidchef.models.recipe.Recipe;

public class DisplayMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        DisplayMenuFragment displayMenuFragment = new DisplayMenuFragment();
        displayMenuFragment.setArguments(getIntent().getExtras());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container_display_menu, displayMenuFragment).commit();
    }

    public void recipeSelected(Recipe selectedRecipe) {
        DisplayRecipeFragment displayRecipeFragment = new DisplayRecipeFragment();

        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.selected_recipe), selectedRecipe);
        displayRecipeFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_display_menu, displayRecipeFragment);
        fragmentTransaction.commit();
    }

    public void editRecipeButtonClicked(Recipe recipe) {
        EnterRecipeFragment enterRecipeFragment = new EnterRecipeFragment();

        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.editing_recipe), recipe);
        enterRecipeFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_display_menu, enterRecipeFragment);
        fragmentTransaction.commit();
    }
}

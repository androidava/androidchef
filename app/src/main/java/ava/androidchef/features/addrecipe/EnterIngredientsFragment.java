package ava.androidchef.features.addrecipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import ava.androidchef.R;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.utils.AutoCompleteOnItemClickListener;
import ava.androidchef.utils.Unit;

public class EnterIngredientsFragment extends Fragment
        implements AdapterView.OnItemClickListener, View.OnFocusChangeListener {

    private EnterIngredientsPresenter presenter;
    private LinearLayout rows;

    public EnterIngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_ingredients, container, false);

        this.presenter = new EnterIngredientsPresenter(this);
        this.rows = (LinearLayout) view.findViewById(R.id.ingredient_input_wrapper);

        showIngredientInputLine();

        return view;
    }

    private void showIngredientInputLine() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout ingredientInputLine = (LinearLayout) inflater.inflate(R.layout.list_item_enter_ingredient, null);

        populateIngredientSuggestions(ingredientInputLine);

        Spinner unitSpinner = (Spinner) ingredientInputLine.findViewById(R.id.spinner_unit);
        populateUnitSpinner(unitSpinner);

        AutoCompleteTextView ingredientNameInput = (AutoCompleteTextView) ingredientInputLine.findViewById(R.id.input_ingredient_name);
        ingredientNameInput.setOnFocusChangeListener(this);

        rows.addView(ingredientInputLine);
    }

    private void populateIngredientSuggestions(LinearLayout ingredientInputLine) {
        ArrayList<Ingredient> ingredients = presenter.getIngredients();
        AutoCompleteTextView ingredientNameInput = (AutoCompleteTextView) ingredientInputLine.findViewById(R.id.input_ingredient_name);
        ArrayAdapter<Ingredient> ingredientAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ingredients);
        ingredientNameInput.setAdapter(ingredientAdapter);
        ingredientNameInput.setOnItemClickListener(new AutoCompleteOnItemClickListener(ingredientInputLine, this));
    }

    private void populateUnitSpinner(Spinner unitSpinner) {
        ArrayList<String> units = Unit.getUnits();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Ingredient selectedIngredient = (Ingredient) parent.getAdapter().getItem(position);
        Spinner unitSpinner = (Spinner) view.findViewById(R.id.spinner_unit);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) unitSpinner.getAdapter();
        adapter.clear();
        adapter.add(selectedIngredient.getUnit());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        v.setOnFocusChangeListener(null);
        showIngredientInputLine();
    }

    public ArrayList<ArrayList<String>> getIngredientInput() {
        ArrayList<ArrayList<String>> userIngredientsInput = new ArrayList<>();

        LinearLayout inputWrapper = (LinearLayout) getView().findViewById(R.id.ingredient_input_wrapper);

        for (int i = 0; i < (inputWrapper.getChildCount()-1); i++) {
            LinearLayout ll = (LinearLayout) inputWrapper.getChildAt(i);
            EditText inputName = (EditText) ll.findViewById(R.id.input_ingredient_name);
            Spinner inputUnit = (Spinner) ll.findViewById(R.id.spinner_unit);
            EditText inputAmount = (EditText) ll.findViewById(R.id.input_ingredient_amount);

            ArrayList<String> userIngredient = new ArrayList<>();
            userIngredient.add(inputName.getText().toString());
            userIngredient.add(inputUnit.getSelectedItem().toString());
            userIngredient.add(inputAmount.getText().toString());
            userIngredientsInput.add(userIngredient);
        }

        return userIngredientsInput;
    }

    public LinkedHashMap<Ingredient, Integer> onSaveButtonClick() {
        return presenter.saveIngredients();
    }
}
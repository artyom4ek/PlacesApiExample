package vasylenko.placesapiexample.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import vasylenko.placesapiexample.R;

/**
 * Главная активность проекта.
 * Created by Тёма on 02.10.2017.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Метод отображает поле автозаполения поиска мест.
     * @param view вьющка по дефолту для метода обработки события.
     */
    public void findPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e("ErrorTAG", e.getMessage());
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("ErrorTAG", e.getMessage());
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Метод возвращает результат в активити после получения данных выбранного места.
     * @param requestCode с какого активити пришел результат.
     * @param resultCode код возврата.
     * @param data Intent, в котором возвращаются данные.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("DataTAG", "Place: "
                        + place.getAddress()
                        + place.getPhoneNumber()
                        + place.getLatLng().latitude);

                Intent intent = new Intent(MainActivity.this,
                        GoogleMapActivity.class);
                intent.putExtra("latitude",place.getLatLng().latitude);
                intent.putExtra("longitute",place.getLatLng().longitude);
                intent.putExtra("name",place.getName());
                intent.putExtra("address",place.getAddress());
                startActivity(intent);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e("StatusTag", status.getStatusMessage());
                Toast.makeText(this, "Status: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "The user canceled the operation: RESULT CODE " + resultCode, Toast.LENGTH_LONG).show();
            }
        }
    }
}

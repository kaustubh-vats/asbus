package com.asbus101.asbus.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asbus101.asbus.R;
import com.asbus101.asbus.ServicesDisplayActivity;
import com.asbus101.asbus.models.ServiceModel;
import com.asbus101.asbus.recycleviewAdaptors.MyRecycleViewAdaptor;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView textViewMore;
    private HomeModel homeModel;
//    commented code for animation
//    int duration=3500;
//    int position = 0;
//    Handler handler;
//    Runnable scrollingRunnable;
    RecyclerView recyclerView;
    MyRecycleViewAdaptor myRecycleViewAdaptor;
    String[] title={"Village Tours","Educational Tours","Royal Train Bookings","Royal Bus Bookings","Royal Cabs Bookings","Bicycle Tour Packages","Bike Tour Packages","Premium Hotel Bookings","Covid 19 Trips","EMI Tour Package","Comedian Tour Package","New Year Tour Packages","Luxury Tour Package","Luxury Honeymoon Package","Live Music Tours","Sunbath Tour Package","Self Driving Card International","Award Show Tours","Award Show Tickets","Imagica Ticket Bookings","Kingdom of Dreams Booking","Money Transfer","Vegetarian (Jain) Tour Packages","International SIM Cards","Tour Guide Booking","Self Driving Cars India","Vegetarian (Jain) Tour Packages", "Adventure Tours ", "Bungy Jumping", "Hot Air Balloon Bookings", "River Rafting ", "Scuba Diving ", "Trekking ", "Wildlife Safari ", "Camel Safari Booking ", "Horse Safari", "Camping", "Canyoning", "Caving", "Jeep Safari", "Mountaineering", "Kayaking", "Aerosport", "Angling", "Birding ", "Mountaineering", "Off Roading", "Outdoor Photography", "Paragliding", "River Cruising", "River Rafting", "Rock Climbing", "Sailing", "Scuba Diving", "Skiing", "Sky Diving", "Trekking", "Wildlife Safari ", "Wind Surfing", "Zip Line", "Iskcon temple tours", "Chardham tour package", "Casino in Goa Bookings ", "Casino Tour Package ", "Women Group Tours ", "Girls Tour Packages ", "Boys Tour Packages ", "Bachelor Tour Packages ", "Tatkal Train Tickets ", "Rail Bookings", "Visa Services ", "Cruise Booking", "Family Cruises", "Meetings & Incentives", "Shopping Tours ", "Santa Claus Village tour ", "Star Cruise Booking", "Helicopter Bookings ", "Private Helicopter", "Pilgrimage Tours ", "School Tours", "College Tours ", "Passport Services ", "Passport Appointment ", "Student Flight Tickets ", "LTC Tour Packages ", "LFC Tour Packages ", "MICE Tour Packages ", "Domestic Flight Bookings ", "Flights Domestic ", "International Flights ", "Charter Flights ", "Vande Bharat Mission Flights ", "Air Bubble Flights ", "Hotels Domestic ", "International Hotels", "Home Stays", "Private Rooms", "Private Villas ", "Luxury Villas", "Holidays In India ", "International Holidays", "Travel Cabs ", "Cheap Flight Tickets to India ", "Bus Tickets ", "Route Planner", "Route Planner", "RSRTC Goa Packages", "Thailand Packages", "Hotels Near Me ", "Cheap Hotels ", "Corporate Flight Booking ", "Family packages ", "Travel Community ", "Group Tour Packages", "Candle Light Dinner ", "Birthday Tour Package ", "Luxury Tour Package", "Limousine car Booking ", "Eiffel tower Ticket Booking ", "Tirupati Darshan Booking ", "Trade Show Tickets", "Jewellery Shows Hotels ", "Jeweller Flight Bookings", "Flight Rescheduling Service ", "Cargo Booking ", "Anniversary Tour Packages ", "Destination Wedding Planner ", "Historical Tour Package"};
    int[] images={R.drawable.village_tour,R.drawable.educational,R.drawable.train,R.drawable.bus,R.drawable.travel_cabs,R.drawable.cycle,R.drawable.bike,R.drawable.hotel,R.drawable.covid,R.drawable.emi,R.drawable.standup,R.drawable.newyear,R.drawable.luxury,R.drawable.honeymoon,R.drawable.music,R.drawable.sunbath,R.drawable.driving,R.drawable.awardtour,R.drawable.awardticket,R.drawable.imagica,R.drawable.kingdom,R.drawable.money_transfer,R.drawable.jain,R.drawable.simcard,R.drawable.guide,R.drawable.driving,R.drawable.jain,R.drawable.adventure,R.drawable.bunjee,R.drawable.hotair,R.drawable.rafting,R.drawable.scoobadiving,R.drawable.trekking,R.drawable.wild_safari,R.drawable.canel_safari,R.drawable.horse_safari,R.drawable.camping,R.drawable.canyoning,R.drawable.caving,R.drawable.zeep_safari,R.drawable.mountaineering,R.drawable.kayaking,R.drawable.aerosport,R.drawable.angling,R.drawable.birding,R.drawable.mountaineering,R.drawable.offroading, R.drawable.outdoor_photography,R.drawable.paragliding,R.drawable.cruising,R.drawable.rafting,R.drawable.rock_climbing,R.drawable.sailing,R.drawable.scoobadiving,R.drawable.skiing,R.drawable.sky_diving,R.drawable.trekking,R.drawable.wild_safari,R.drawable.surfing,R.drawable.zipline,R.drawable.iscon,R.drawable.chardham, R.drawable.casino_goa,R.drawable.casino,R.drawable.women_group,R.drawable.girls_tour,R.drawable.boys_tour,R.drawable.bachelor,R.drawable.tatkal_train,R.drawable.rail_booking,R.drawable.visa,R.drawable.cruise_booking,R.drawable.cruise_family,R.drawable.meetings,R.drawable.shopping_tour,R.drawable.santa,R.drawable.cruising,R.drawable.helicopter,R.drawable.helicopter,R.drawable.iscon,R.drawable.school_trip,R.drawable.college_tour,R.drawable.passport,R.drawable.passport,R.drawable.charter_flights,R.drawable.register,R.drawable.travel,R.drawable.meetings,R.drawable.domestic_flight,R.drawable.international_flights,R.drawable.charter_flights,R.drawable.vande_bharat,R.drawable.air_bubble,R.drawable.hotel,R.drawable.international_hotels,R.drawable.private_rooms,R.drawable.villas,R.drawable.villas,R.drawable.india_holidays,R.drawable.international_holidays,R.drawable.travel_cabs,R.drawable.india,R.drawable.bus,R.drawable.route_planner,R.drawable.route_planner,R.drawable.india_goa,R.drawable.thailand,R.drawable.hotel,R.drawable.domestic_hotels,R.drawable.charter_flights,R.drawable.cruise_family,R.drawable.travel_community,R.drawable.group_tour,R.drawable.aniversary_tour,R.drawable.birthday_tour,R.drawable.travel,R.drawable.limo,R.drawable.eiffel_tower,R.drawable.iscon,R.drawable.trade_show_tickets,R.drawable.jewwllery,R.drawable.international_flights,R.drawable.cargo,R.drawable.aniversary_tour,R.drawable.wedding,R.drawable.history};
    List<ServiceModel> serviceModels;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeModel = new ViewModelProvider(this).get(HomeModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        recyclerView=root.findViewById(R.id.recyclerView);
        textViewMore = root.findViewById(R.id.textView16);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        handler = new Handler(Looper.getMainLooper());
//        scrollingRunnable = new Runnable() {
//            @Override
//            public void run() {
//                recyclerView.smoothScrollToPosition(position);
//                position++;
//                if(position>=serviceModels.size())
//                {
//                    position=0;
//                }
//                handler.postDelayed(this, duration);
//            }
//        };
        serviceModels=new ArrayList<ServiceModel>(images.length);
        for(int i=0;i<images.length;i++)
        {
            ServiceModel serviceModel = new ServiceModel(title[i],images[i]);
            serviceModels.add(serviceModel);
        }
        myRecycleViewAdaptor=new MyRecycleViewAdaptor(serviceModels,getActivity());
        recyclerView.setAdapter(myRecycleViewAdaptor);
        textViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ServicesDisplayActivity.class);
                startActivity(intent);
            }
        });
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                handler.removeCallbacks(scrollingRunnable);
//                return false;
//            }
//        });
        homeModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
//        handler.postDelayed(scrollingRunnable, duration);
        return root;
    }
}

package com.example.hungry.ordersummary;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hungry.HomePage;
import com.example.hungry.address.Address;
import com.example.hungry.databinding.PromocodeFragmentBinding;
import com.example.hungry.hotel.CartListner;
import com.example.hungry.ordersummary.adapter.OrderSummaryAdapter;
import com.example.hungry.ordersummary.model.TaxResult;
import com.example.hungry.ordersummary.viewmodel.OrderSummaryViewModel;
import com.example.hungry.R;
import com.example.hungry.databinding.FragmentOrderSummaryBinding;
import com.example.hungry.promocode.PromocodeFragment;

public class OrderSummary extends Fragment {
    private OrderSummaryViewModel orderSummaryViewModel;
    private FragmentOrderSummaryBinding binding;

    public OrderSummary() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OrderSummary newInstance(String param1, String param2) {
        OrderSummary fragment = new OrderSummary();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_summary, container, false);
        orderSummaryViewModel= ViewModelProviders.of(this).get(OrderSummaryViewModel.class);
        binding.setOrderSummaryViewModel(orderSummaryViewModel);
        binding.setLifecycleOwner(this);
        orderSummaryViewModel.setTaxResult(((HomePage) getActivity()).taxResultMutableLiveData.getValue());
        orderSummaryViewModel.setArrayMenu(((HomePage) getActivity()).cart);

        OrderSummaryAdapter ordeSummaryAdapter = new OrderSummaryAdapter(((HomePage)getActivity()).cart, getActivity());

        ordeSummaryAdapter.setCartListner(new CartListner() {
            @Override
            public void onChange() {
                orderSummaryViewModel.setArrayMenu(((HomePage) getActivity()).cart);
            }
        });
        binding.setAdapter(ordeSummaryAdapter);
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Address.class);
                startActivity(intent);


            }
        });
        binding.promoadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, PromocodeFragment.newInstance(((HomePage) getActivity()).cart.get(0).hotelID)).addToBackStack(null)
                        .commit();
            }
        });
        return binding.getRoot();
    }





}

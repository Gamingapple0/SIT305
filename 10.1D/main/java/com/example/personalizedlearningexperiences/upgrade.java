package com.example.personalizedlearningexperiences;

import static com.android.volley.VolleyLog.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.personalizedlearningexperiences.databinding.FragmentUpgradeBinding;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.button.PayButton;
import com.paypal.android.cardpayments.Card;
import com.paypal.android.cardpayments.CardClient;
import com.paypal.android.cardpayments.CardRequest;
import com.paypal.android.cardpayments.threedsecure.SCA;
import com.paypal.android.corepayments.CoreConfig;
import com.paypal.android.corepayments.Environment;
import com.paypal.android.paymentbuttons.PayPalButton;
import com.paypal.android.paypalnativepayments.PayPalNativeCheckoutClient;
import com.paypal.android.paypalnativepayments.PayPalNativeCheckoutRequest;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link upgrade#newInstance} factory method to
 * create an instance of this fragment.
 */
public class upgrade extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentUpgradeBinding binding;
    private PayPalButton payPalButton1;
    private PayPalButton payPalButton2;
    private PayPalButton payPalButton3;


    public static PaymentsClient paymentsClient;

    public upgrade() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment upgrade.
     */
    // TODO: Rename and change types and number of parameters
    public static upgrade newInstance(String param1, String param2) {
        upgrade fragment = new upgrade();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CoreConfig config = new CoreConfig("apikey", Environment.SANDBOX);


        PayPalNativeCheckoutClient payPalNativeClient = new PayPalNativeCheckoutClient(
                requireActivity().getApplication(),
                config,
                "com.name://paypalpay"
        );

        // Inflate the layout for this fragment


        binding = FragmentUpgradeBinding.inflate(inflater,container,false);

        payPalButton1 = binding.paypalButton1;
        payPalButton2 = binding.paypalButton2;
        payPalButton3 = binding.paypalButton3;

        payPalButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalNativeCheckoutRequest request = new PayPalNativeCheckoutRequest("5O190127TN364715T");
                payPalNativeClient.startCheckout(request);
            }
        });

        payPalButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalNativeCheckoutRequest request = new PayPalNativeCheckoutRequest("5O190127TN364715T");
                payPalNativeClient.startCheckout(request);
            }
        });


        payPalButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalNativeCheckoutRequest request = new PayPalNativeCheckoutRequest("5O190127TN364715T");
                payPalNativeClient.startCheckout(request);
            }
        });




        return binding.getRoot();
    }
}
package com.spundhan.pixsello.payment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;

import com.pixsello.management.R;
import com.pixsello.management.connectivity.IWebRequest;
import com.pixsello.management.connectivity.WebRequestPost;
import com.pixsello.management.util.Uttilities;

public class PaymentActivity extends Activity {

	EditText editSeviceName, editIdentity, editBillNum, editAmount;
	EditText editChequeNumber, editOtherDetail;
	String serviceName;
	String identity;
	String serviceId;
	String identityName;
	String billNumber;
	String amount;
	String chequeNumber;
	String othrDetail;
	String billDate;
	String dueDate;
	String identityID;
	String url;
	String type;

	RadioButton radioCash, radioCheque, radioOther;

	boolean cashStatus;
	boolean chequeStatus;
	boolean otherStatus;
	
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);

		editSeviceName = (EditText) findViewById(R.id.edit_service_name);
		editIdentity = (EditText) findViewById(R.id.edit_identity);
		editBillNum = (EditText) findViewById(R.id.edit_bill_num);
		editAmount = (EditText) findViewById(R.id.edit_amount);
		editChequeNumber = (EditText) findViewById(R.id.edit_cheque_number);
		editOtherDetail = (EditText) findViewById(R.id.edit_other_detail);
		radioCash = (RadioButton) findViewById(R.id.radio_cash);
		radioCheque = (RadioButton) findViewById(R.id.radio_cheque);
		radioOther = (RadioButton) findViewById(R.id.radio_other);
		
		dialog = new ProgressDialog(PaymentActivity.this);
		dialog.setMessage("Please Wait..");
		
		Intent intent = getIntent();
		
		billNumber = intent.getStringExtra("BillNo");
		billDate = intent.getStringExtra("BillDate");
		identityName= intent.getStringExtra("IdentityName");
		identityID = intent.getStringExtra("Identity");
		serviceName = intent.getStringExtra("ServiceName");
		serviceId = intent.getStringExtra("ServiceID");
		amount = intent.getStringExtra("Amount");
		dueDate = intent.getStringExtra("Billduedate");
		url = intent.getStringExtra("url");
		type = intent.getStringExtra("type");

		editSeviceName.setText(serviceName);                              
		editIdentity.setText(identityName);
		editBillNum.setText(billNumber);
		editAmount.setText(amount);

		radioCash.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					cashStatus = isChecked;
					chequeStatus = false;
					otherStatus = false;
					radioCheque.setChecked(false);
					radioOther.setChecked(false);
				}

			}
		});

		radioCheque.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					chequeStatus = isChecked;
					cashStatus = false;
					otherStatus = false;
					radioCash.setChecked(false);
					radioOther.setChecked(false);
				}
			}
		});

		radioOther.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					otherStatus = isChecked;
					chequeStatus = false;
					cashStatus = false;
					radioCheque.setChecked(false);
					radioCash.setChecked(false);
				}

			}
		});
	}

	public void doSubmit(View v) {

		serviceName = editSeviceName.getText().toString();
//		identity = editIdentity.getText().toString();
		billNumber = editBillNum.getText().toString();
		amount = editAmount.getText().toString();
		chequeNumber = editChequeNumber.getText().toString().isEmpty() ? "" : editChequeNumber.getText().toString();
		othrDetail = editOtherDetail.getText().toString().isEmpty() ? "" : editOtherDetail.getText().toString();

		String paymentMode = "";

		if (cashStatus) {
			paymentMode = "1";
		} else if (chequeStatus) {
			paymentMode = "2";
		} else if (otherStatus) {
			paymentMode = "3";
		}

		if (!serviceName.isEmpty() || !identity.isEmpty()
				|| !billNumber.isEmpty() || !amount.isEmpty()) {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
			
			if(type.equalsIgnoreCase("new")){

				nameValuePair.add(new BasicNameValuePair("PropertyID", Uttilities.PROPERTY_ID));
				nameValuePair.add(new BasicNameValuePair("ServiceID", serviceId));
				nameValuePair.add(new BasicNameValuePair("IdentityID", identityID));
				nameValuePair.add(new BasicNameValuePair("BillNo", billNumber));
				nameValuePair.add(new BasicNameValuePair("BillDate", billDate));
				nameValuePair.add(new BasicNameValuePair("Billduedate", dueDate));
				nameValuePair.add(new BasicNameValuePair("Amount", amount));
				nameValuePair
						.add(new BasicNameValuePair("Paymentmode",paymentMode));
				nameValuePair.add(new BasicNameValuePair("chequedetail",
						chequeNumber));
				nameValuePair
						.add(new BasicNameValuePair("otherdetail", othrDetail));
			
			}else{
				
				nameValuePair.add(new BasicNameValuePair("ServiceID", serviceId));
				nameValuePair.add(new BasicNameValuePair("Paymentmode",paymentMode));
				nameValuePair.add(new BasicNameValuePair("chequedetail",chequeNumber));
				nameValuePair.add(new BasicNameValuePair("otherdetail", othrDetail));
			}
	
			dialog.show();
			
			WebRequestPost post = new WebRequestPost(new IWebRequest() {

				@Override
				public void onDataArrived(String data) {

					dialog.cancel();
					Uttilities.showToast(getApplicationContext(), data);
					finish();

				}
			}, nameValuePair);

			post.execute(url);

		} else {

			Uttilities.showToast(getApplicationContext(), "Fill all fields.");

		}
	}

	public void goBack(View v) {
		finish();
	}
}

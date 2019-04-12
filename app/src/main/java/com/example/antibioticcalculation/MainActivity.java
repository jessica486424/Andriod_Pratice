package com.example.antibioticcalculation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.*;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CalculatorActivity";
    private EditText mCrea;
    private EditText mAge;
    private EditText mWeight;
    private EditText mDose;
    private EditText mFre;
    private EditText mGen;

    private TextView mResult1;
    private TextView mResult2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the calculator class and all the views.
        mCrea=findViewById(R.id.CreaInput);
        mWeight=findViewById(R.id.WeightInput);
        mDose=findViewById(R.id.DoseInput);
        mFre=findViewById(R.id.FreqInput);
        mResult1=findViewById(R.id.Result1Textview);
        mResult2=findViewById(R.id.Result2Textview);
        mAge=findViewById(R.id.AgeInput);
        mGen=findViewById(R.id.GenderInput);

    }

    private Double renal_funtion(double crea ,double age ,double weight)
    {
        double Ccr;
        if(crea<=0.8) {
            crea = 0.8;
        }
        Ccr=(140-age)*weight/72/crea-0.15*(140-age)*weight/72/crea*getOperand(mGen);
        return  Ccr;
    }


    public void oncal(View view) {
        double Vd_vanco;
        double Ccr;
        double k;
        double Ccs_peak;
        double Ccs_through;
        double opreandcrea;
        double opreandage;
        double opreandweight;
        double opreanddose;
        double opreandfreq;
        double Vd;
        Vd=0.7;

        try {
            opreandcrea = getOperand(mCrea);
            opreandage = getOperand(mAge);
            opreandweight=getOperand(mWeight);
            opreanddose=getOperand(mDose);
            opreandfreq=getOperand(mFre);
        } catch (NumberFormatException nfe) {
            Log.e(TAG, "NumberFormatException", nfe);
            mResult1.setText("Error");
            return;
        }

        Vd_vanco=Vd*opreandweight;
        Ccr=renal_funtion(opreandcrea,opreandage,opreandweight)/1000*60;
        k=Ccr/Vd_vanco;

        Ccs_peak=opreanddose/Vd_vanco*(1/(1-Math.pow(Math.E,(-k*opreandfreq))));

        Ccs_through=Ccs_peak*(Math.pow(Math.E,(-k*opreandfreq)));

        mResult1.setText(String.valueOf(Ccs_peak));
        mResult2.setText(String.valueOf(Ccs_through));
    }


    /**
     * @return the operand value entered in an EditText as double.
     */
    private static Double getOperand(EditText operandEditText) {
        String operandText = getOperandText(operandEditText);
        return Double.valueOf(operandText);
    }

    /**
     * @return the operand text which was entered in an EditText.
     */
    private static String getOperandText(EditText operandEditText) {
        String operandText = operandEditText.getText().toString();
        if (TextUtils.isEmpty(operandText)) {
            throw new NumberFormatException("Operand cannot be empty!");
        }
        return operandText;
    }
}

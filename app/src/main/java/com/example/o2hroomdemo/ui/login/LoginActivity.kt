package com.example.o2hroomdemo.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.o2hroomdemo.R
import com.example.o2hroomdemo.Utils.MyProgressDialog
import com.example.o2hroomdemo.ui.main.MainActivity
import com.example.o2hroomdemo.utils.ConnectivityDetector
import com.example.o2hroomdemo.utils.SessionManager
import com.google.android.gms.common.api.ApiException

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    var RC_SIGN_IN = 1;
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext = this
        setUpGoogleSignIn()
    }


    fun googleClick(view: View) {
        MyProgressDialog.showProgressDialog(mContext)
        googleSignIn()
    }

    var googleSignInOptions: GoogleSignInOptions? = null
    private fun setUpGoogleSignIn() {
        googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions!!)

        firebaseAuth = FirebaseAuth.getInstance()

    }

    public fun googleSignIn() {
        if (ConnectivityDetector.isConnectingToInternet(mContext)) {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        } else {
            MyProgressDialog.hideProgressDialog()
            //KeyboardUtility.hideKeyboard(mContext, bindingCheckEmail.edtEmailAS1)
            // CommonMethods.showInternetAlert(mContext)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                // firebaseAuthWithGoogle(account.idToken!!)

                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            } catch (e: ApiException) {
                MyProgressDialog.hideProgressDialog()
                /*loading(false)
                customtoast("Google Sign in failed")*/
                e.printStackTrace()
            }
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            //loading(false)
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!

            Log.e("displayName", "" + account.displayName)
            Log.e("id", "" + account.id)
            // Signed in successfully, show authenticated UI.
            // callSocialLoginApi(account.id, account.displayName, account.email)
            MyProgressDialog.hideProgressDialog()
            SessionManager.setIsUserLoggedin(mContext,true)
            SessionManager.setUserName(mContext, account.displayName!!)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", account.displayName)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            //updateUI(account)
        } catch (e: ApiException) {
            MyProgressDialog.hideProgressDialog()
//            loading(false)
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
            // updateUI(null)
            //customtoast("Authentication failed")
        }
    }

    private fun signOut() {
        firebaseAuth.signOut()
        googleSignInClient.signOut().addOnCompleteListener(this) {
            Log.e("logout : ", "logout from google")
        }
    }

    private fun revokeAccess() {
        firebaseAuth.signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener(this) {
            Log.e("revoke : ", "access revoked")
        }
    }
}
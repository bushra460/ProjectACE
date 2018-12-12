using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Preferences;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace ACE
{
    [Activity(Label = "Language")]
    public class Language : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            Window.RequestFeature(WindowFeatures.NoTitle);
            SetContentView(Resource.Layout.Language);

            string path = System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal);
            string filename = System.IO.Path.Combine(path, "localstorage.txt");
            // Create your application here


            ImageButton englishButton = FindViewById<ImageButton>(Resource.Id.englishBttn);
            englishButton.Click += (sender, e) =>
            {
                Context mContext = ApplicationContext;
                var onboarded = true;
                ISharedPreferences prefs = PreferenceManager.GetDefaultSharedPreferences(mContext);
                ISharedPreferencesEditor editor = prefs.Edit();
                editor.PutBoolean("onboardedBool", onboarded);
                editor.Apply();        // applies changes asynchronously on newer APIs


                var intentMain = new Intent(this, typeof(MainActivity));
                //intent.PutStringArrayListExtra("phone_numbers", phoneNumbers);
                StartActivity(intentMain);
            };

            ImageButton frenchButton = FindViewById<ImageButton>(Resource.Id.frenchBttn);
            frenchButton.Click += (sender, e) =>
            {
                Context mContext = ApplicationContext;
                var onboarded = true;
                ISharedPreferences prefs = PreferenceManager.GetDefaultSharedPreferences(mContext);
                ISharedPreferencesEditor editor = prefs.Edit();
                editor.PutBoolean("onboardedBool", onboarded);
                editor.Apply();        // applies changes asynchronously on newer APIs


                var intentMain = new Intent(this, typeof(MainActivity));
                //SET LANGUAGE TO FRENCH
                //intent.PutStringArrayListExtra("phone_numbers", phoneNumbers);
                StartActivity(intentMain);
            };

            ImageButton privacyPolicyEN = FindViewById<ImageButton>(Resource.Id.privacyBttn);
            privacyPolicyEN.Click += (sender, e) =>
            {
                var intentEN = new Intent(this, typeof(PrivacyPolicyEN));
                //intent.PutStringArrayListExtra("phone_numbers", phoneNumbers);
                StartActivity(intentEN);
            };

            ImageButton privacyPolicyFR = FindViewById<ImageButton>(Resource.Id.avisBttn);
            privacyPolicyFR.Click += (sender, e) =>
            {
                var intentFR = new Intent(this, typeof(PrivacyPolicyFR));
                //intent.PutStringArrayListExtra("phone_numbers", phoneNumbers);
                StartActivity(intentFR);
            };
        }
    }
}
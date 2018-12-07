
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
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
            // Create your application here


            Button englishButton = FindViewById<Button>(Resource.Id.englishBttn);
            englishButton.Click += (sender, e) =>
            {
                var intentMain = new Intent(this, typeof(MainActivity));
                //intent.PutStringArrayListExtra("phone_numbers", phoneNumbers);
                StartActivity(intentMain);
            };

            Button frenchButton = FindViewById<Button>(Resource.Id.frenchBttn);
            englishButton.Click += (sender, e) =>
            {
                var intentMain = new Intent(this, typeof(MainActivity));
                //SET LANGUAGE TO FRENCH
                //intent.PutStringArrayListExtra("phone_numbers", phoneNumbers);
                StartActivity(intentMain);
            };

            Button privacyPolicyEN = FindViewById<Button>(Resource.Id.privacyBttn);
            englishButton.Click += (sender, e) =>
            {
                var intentEN = new Intent(this, typeof(PrivacyPolicyEN));
                //intent.PutStringArrayListExtra("phone_numbers", phoneNumbers);
                StartActivity(intentEN);
            };

            Button privacyPolicyFR = FindViewById<Button>(Resource.Id.avisBttn);
            englishButton.Click += (sender, e) =>
            {
                var intentFR = new Intent(this, typeof(PrivacyPolicyFR));
                //intent.PutStringArrayListExtra("phone_numbers", phoneNumbers);
                StartActivity(intentFR);
            };
        }
    }
}
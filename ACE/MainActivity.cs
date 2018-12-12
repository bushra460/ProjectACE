using Android.App;
using Android.Widget;
using Android.OS;
using System;
using Android.Content;
using System.IO;
using Android.Preferences;

namespace ACE
{
    [Activity(Label = "ACE", MainLauncher = true, Icon = "@mipmap/icon", Theme = "@style/MyTheme.Splash")]
    public class MainActivity : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);
            Context mContext = ApplicationContext;
            ISharedPreferences prefs = PreferenceManager.GetDefaultSharedPreferences(mContext);
            ISharedPreferencesEditor editor = prefs.Edit();

            if (prefs.GetBoolean("onboardedBool", true) == true)
            {
                var onboarded = true;
                editor.PutBoolean("onboardedBool", onboarded);
                editor.Apply();        // applies changes asynchronously on newer APIs
                var intent = new Intent(this, typeof(Language));
                StartActivity(intent);
            }


            // Get our button from the layout resource,
            // and attach an event to it
            ImageButton SearchButton = FindViewById<ImageButton>(Resource.Id.SearchBttn);
            SearchButton.Click += (sender, e) =>
             {
                 var intent = new Intent(this, typeof(Search));
                 //intent.PutStringArrayListExtra("phone_numbers", phoneNumbers);
                 StartActivity(intent);
             };
        }
    }
}


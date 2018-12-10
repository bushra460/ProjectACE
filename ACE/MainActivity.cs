using Android.App;
using Android.Widget;
using Android.OS;
using System;
using Android.Content;
using System.IO;

namespace ACE
{
    [Activity(Label = "ACE", MainLauncher = true, Icon = "@mipmap/icon", Theme = "@android:style/Theme.Holo.NoActionBar.Fullscreen")]
    public class MainActivity : Activity
    {

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);

            var onboarded = false;

            if (onboarded == false)
            {
                onboarded = true;
                var intent = new Intent(this, typeof(Language));
                StartActivity(intent);
            }
            // Get our button from the layout resource,
            // and attach an event to it
            Button SearchButton = FindViewById<Button>(Resource.Id.SearchBttn);
            SearchButton.Click += (sender, e) =>
             {
                 //var intent = new Intent(this, typeof(Language));
                 ////intent.PutStringArrayListExtra("phone_numbers", phoneNumbers);
                 //StartActivity(intent);
             };
        }
    }
}


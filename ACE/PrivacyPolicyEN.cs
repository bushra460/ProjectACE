
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
    [Activity(Label = "PrivacyPolicyEN")]
    public class PrivacyPolicyEN : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.PrivacyPolicyEN);
            // Create your application here
        }
    }
}

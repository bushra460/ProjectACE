using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace ACE
{
    public partial class Language : ContentPage
    {
        public Language()
        {
            InitializeComponent();
        }
        public void EnglishClicked(object sender, EventArgs args)
        {
            Navigation.PushAsync(new PrivacyPolicy());
        }
        public void FrenchClicked(object sender, EventArgs args)
        {
            Navigation.PushAsync(new PrivacyPolicy());
        }
    }
}
